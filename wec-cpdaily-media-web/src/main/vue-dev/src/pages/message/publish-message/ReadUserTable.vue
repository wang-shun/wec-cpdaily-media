<template>
    <section>
        <div class="btns-con">
            <search-input @search="search" style="width: 320px;" placeholder="搜索学工号、姓名" v-model="options.queryParams.name"></search-input>
            <div v-if="enableSmsNotify && !emptyUnread" class="sms-con">
                <div>
                    <Button v-if="cNotifyDisabled" disabled type="primary" icon="android-notifications">未读短信提醒({{cCountTip}})</Button>
                    <Button v-else :loading="sendLoading" type="primary" icon="android-notifications" @click="Confirm">未读短信提醒</Button>
                </div>
                <div class="sms-tip">
                    <Icon type="heart"></Icon>小提示：未读人员将收到短信消息，
                    <Poptip title="短信内容预览" :content="'同学你好，' + media.name + '给你发送了一条重要通知，快上今日校园App查看！'" placement="top">
                        <a>内容预览</a>
                    </Poptip>
                     为避免提醒过于频繁，{{waitHour}}小时内仅可使用一次<br/>
                    剩余免费短信：{{media.smsRemain}} / {{media.smsTotal}}条 想增量？您可以 <span>联系我们：025-68755378</span>
                </div>
            </div>
        </div>
        <Row class="bh-mv-16">
            <span>阅读状态：</span>
            <RadioGroup v-model="options.queryParams.status" type="button" @on-change="search" size="small">
                <Radio v-for="s in cStatus" :key="s.id" :label="s.id">{{s.name}}</Radio>
            </RadioGroup>
        </Row>
        <div class="bh-mv-16">
            <UTable ref="table" :columns="columns" :options="options" @data-loaded="tableLoaded"></UTable>
        </div>
    </section>
</template>
<script>
import api from 'conf/api'
import util from 'conf/util.js'
import UTable from 'com/UTable'
import searchInput from 'com/searchInput'
import moment from 'moment'

export default {
    data () {
        return {
            sendLoading: false,
            dataLoaded: false,
            enableSmsNotify: false,
            emptyUnread: true,
            waitHour: 12,
            options: {
                url: api.getReadList,
                queryParams: {
                    name: '',
                    status: 'unread',
                    msgId: this.msg.msgId
                }
            },
            statuses: [{
                id: 'unread',
                name: '未读'
            },{
                id: 'read',
                name: '已读'
            }],
            columns: [
                {
                    type: 'selection',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '学工号',
                    key: 'openId'
                },
                {
                    title: '姓名',
                    key: 'userName'
                },
                {
                    title: '性别',
                    key: 'gender',
                    render: (h, params) => {
                        return h('div', params.row.gender === 'MALE' ? '男' : '女')
                    }
                },
                {
                    title: '类别',
                    key: 'userType',
                    render: (h, params) => {
                        return h('div', params.row.userType === 'TEACHER' ? '教师' : '学生')
                    }
                },
                {
                    title: '班级/部门',
                    key: 'className',
                    render: (h, params) => {
                        return h('div', params.row.userType === 'TEACHER' ? params.row.departName : params.row.className)
                    }
                }
            ],
            msgInfo: {},
            media: {}
        }
    },
    components: {
        searchInput,
        UTable
    },
    computed: {
        ...Vuex.mapState({
            index: state => state.index
        }),
        cNotifyDisabled () {
            // debugger
            let last = this.msgInfo.lastNotifyTimeFmt ? this.msgInfo.lastNotifyTimeFmt : this.msgInfo.cTime
            let lastDate = moment(last, "YYYY-MM-DD HH:mm:ss")
            lastDate.add(this.waitHour, 'hour')
            if (!lastDate.isBefore(new Date())) {
                return true
            }
            return false
        },
        cCountTip () {
            let last = this.msgInfo.lastNotifyTimeFmt ? this.msgInfo.lastNotifyTimeFmt : this.msgInfo.cTime
            let lastDate = moment(last, "YYYY-MM-DD HH:mm:ss")
            lastDate.add(this.waitHour, 'hour')
            let diff = moment(lastDate.diff(new Date())).utcOffset(0)
            return (diff.hour() > 0 ? diff.hour() + '时' : '') + diff.minute() + '分'
        },
        cStatus () {
            let statusCopy = $.extend(true, [], this.statuses)
            statusCopy[0].name = statusCopy[0].name + '(' + (this.msgInfo.receiveCount - this.msgInfo.readCount) + ')'
            statusCopy[1].name = statusCopy[1].name + '(' + this.msgInfo.readCount + ')'
            if (this.emptyUnread) {
                statusCopy.splice(0, 1)
            }
            return statusCopy
        }
    },
    props: {
        msg: {
            type: Object,
            default: {}
        },
        mediaId: {
            type: String,
            default: ''
        }
    },
    created () {
        this.msgInfo = this.msg
    },
    methods: {
        Confirm () {
            if (this.media.smsRemain < (this.msgInfo.receiveCount - this.msgInfo.readCount)) {
                this.$Modal.error({
                    title: '错误',
                    content: '对不起，您的剩余短息数量不够',
                    okText: '知道了'
                })
                return
            }
            let vm = this
            this.$Modal.confirm({
                title: '确认发送短信？',
                content: '未读同学将收到短信消息，为避免提醒过于频繁，' + this.waitHour + '小时内仅可使用一次',
                okText: '确定',
                cancelText: '取消',
                onOk: function () {
                    vm.Notify()
                }
            })
        },
        Notify () {
            this.sendLoading = true
            util.httpPost(api.msgNotify + '/' + this.msg.msgId, {}, util.handler.CODE).then((data) => {
                this.$Message.success('提醒成功')
            }).catch((err) => {
                this.$Message.error({content: '提醒失败' + err.message, duration: 2.5})
            }).then(() => {
                this.sendLoading = false
                this.getMediaInfo()
                this.getMsgInfo()
            })
        },
        previewMsg () {

        },
        search () {
            if (this.options.queryParams.name.indexOf('#') > -1) {
                this.$Message.error('搜索内容不能包含“#”号')
                return
            }
            this.$refs.table.reload(this.options.queryParams)
        },
        tableLoaded (rows) {
            if (this.dataLoaded == false) {
                if (rows.length < 1) {
                    this.hideUnreadAndReload()
                } else {
                    this.emptyUnread = false
                }
            }
            this.dataLoaded = true
        },
        hideUnreadAndReload () {
            this.emptyUnread = true
            this.options.queryParams.status = 'read'
            this.search()
        },
        getMediaInfo () {
            util.httpGet(api.mediaInfo + '/' + this.mediaId, {}, util.handler.DATAS).then((data) => {
                this.media = data
                if (this.media.enableSmsNotification == true) {
                    this.enableSmsNotify = true
                }
            }).catch((err) => {
                this.$Message.error('获取校园号信息失败：' + err.errMsg)
            })
        },
        getMsgInfo () {
            util.httpGet(api.msgView + '/' + this.msg.msgId, {}, util.handler.DATAS).then((data) => {
                this.msgInfo = data
            }).catch((err) => {
                this.$Message.error('刷新消息失败：' + err.errMsg)
            })
        },
        getWaitHour () {
            util.httpGet(api.msgNotifyWaitHour, {}, util.handler.DATAS).then((data) => {
                this.waitHour = data
            }).catch((err) => {
                this.$Message.error('获取等待时间异常：' + err.errMsg)
            })
        }
    },
    mounted () {
        this.getMediaInfo()
        this.getWaitHour()
    }
}
</script>

<style scoped lang="less">
    .btns-con {
        margin-top: 26px;
        display: flex;
        .sms-con {
            display: flex;
            .sms-tip {
                margin-left: 10px;
                color: #aaa;
                span {
                    color: #2d8cf0;
                }
            }
        }
    }
</style>
