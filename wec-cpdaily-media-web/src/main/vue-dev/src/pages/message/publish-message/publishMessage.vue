<template>
    <div class="main-body">
        <breadcrumb :items="bcs"></breadcrumb>
        <div class="body-con">
            <div class="message-header bh-mb-8 bh-mt-4">
                <div class="message-title bh-mt-8 bh-mb-16">发布消息</div>
                <Steps :current="step">
                    <Step title="消息配置" content="填写消息发送内容"></Step>
                    <Step title="选择送达人员" content="哪些人员将收到消息"></Step>
                    <Step title="已发送" content="查看消息阅读进度"></Step>
                </Steps>
            </div>
            <div class="message-body" v-if="freshMessage">
                <edit-message ref="editmessage" v-show="step === 0" :messageInfo="messageInfo" @next="toSelectUsers"></edit-message>
                <select-user ref="selectuser" v-show="step === 1" :messageInfo="messageInfo" @publish="Publish('1')" @save="Publish('0')" @prev="toEditMessage"></select-user>
                <send-result2 ref="sendresult" v-show="step === 2" :messageInfo="messageInfo" @republish="Publish" @new-message="newMessage" @copy-message="copyMessage"></send-result2>
            </div>
            <div class="message-loading-con" v-else>
                <Spin size="large"></Spin>
            </div>
        </div>
    </div>
</template>

<script>
import breadcrumb from 'com/breadcrumb.vue'
import editMessage from './edit-message/editMessage.vue'
import selectUser from './select-user/selectUser.vue'
import sendResult2 from './sendResult2.vue'
import util from 'conf/util.js'
import api from 'conf/api'

export default {
    data () {
        return {
            step: 0,
            tab: 'custom',
            bcs: [{
                path: '/home/message',
                name: '消息送达'
            }, {
                // path: '/home/publishmessage/new/n',
                name: '发布消息'
            }],
            messageInfo: {},
            freshMessage: false
        }
    },
    methods: {
        copyMessage () {
            this.$router.push('/home/publishmessage/copy/' + this.messageInfo.msgId)
            
            this.init()
            this.step = 0
        },
        newMessage () {
            this.$router.push('/home/publishmessage/new/n')
            this.init()
            this.step = 0
        },
        toSelectUsers () {
            this.step = 1
        },
        toEditMessage () {
            this.step = 0
        },
        Publish (type) {
            var target = this.$refs.selectuser.getTarget()
            if (type === '1' && target.groups.length < 1 && target.users.length < 1) {
                this.$Message.error('请选择发送对象')
                return
            }
            var info = this.$refs.editmessage.getMessageContent()
            var msgId = ''
            if (this.$route.params.type === 'edit') {
                msgId = this.messageInfo.msgId
            }
            var pms = {
                title: info.title,
                content: info.content,
                attachments: info.attachments,
                img: info.img === 'static/default-cover.png' ? 'https://img.cpdaily.com/cpdaily-media/default-cover.png' : info.img,
                msgId: msgId,
                notSendUserIds: '',
                opType: type,
                originalLink: '',
                sendUserGroups: target.groups,
                sendUsers: target.users,
                summary: info.title,
                tenantId: ''
            }
            util.httpPost(api.msgSaveAndSend, pms, util.handler.DATAS).then((data) => {
                if (this.$route.params.type === 'new' || this.$route.params.type === 'copy') {
                    this.messageInfo.msgId = data
                }
                if (type === '0') {
                    this.$Message.success('保存成功')
                    this.$router.replace('/home/message')
                } else {
                    // 发布成功后获取结果信息
                    if (this.$route.path == '/home/publishmessage/new/n' || this.$route.path.indexOf('/publishmessage/copy/') > -1) {
                        this.getMessageInfo(this.messageInfo.msgId).then(() => {
                            // this.step = 2
                            this.$router.replace('/home/publishmessage/view/' + data)
                        })
                    }
                }
            }).catch((err) => {
                var opt = type === '1' ? '发布' : '保存'
                this.$Message.error(opt + '失败：' + err.errMsg)
            })
        },
        getMessageInfo (id) {
            var msgId = id || this.$route.params.messageId
            return util.httpGet(api.msgView + '/' + msgId, {}, util.handler.DATAS).then((data) => {
                this.messageInfo = data
            }).catch((err) => {
                this.$Message.error('获取消息信息异常：' + err.errMsg)
            })
        },
        init () {
            var type = this.$route.params.type
            var msgId = this.$route.params.messageId
            if (type !== 'new') {
                this.getMessageInfo(msgId).then(() => {
                    if (type === 'view') {
                        this.step = 2
                    }
                }).then(() => {
                    this.freshMessage = false
                    this.$nextTick(() => {
                        this.freshMessage = true
                    })
                })
            } else {
                this.messageInfo = {}
                this.freshMessage = true
            }
        }
    },
    mounted () {
        this.init()
    },
    watch: {
        '$route' () {
            this.init()
        }
    },
    components: {
        breadcrumb,
        selectUser,
        editMessage,
        sendResult2
    }
}
</script>

<style scoped lang="less" rel="stylesheet/less">
    .main-body{
        .body-con{
            margin: 20px;
            min-height: e("calc(100vh - 144px)");
            background: #FFFFFF;
            box-shadow: 0 0 12px 0 rgba(132,134,219,0.16);
            border-radius: 2px;
            display: flex;
            flex-direction: column;
            .message-header {
                padding: 20px;
                border-bottom: 1px solid #E3E8EE;
                .message-title {
                    font-size: 16px;
                    font-weight: bold;
                }
            }
            .message-body {
                flex: 1;
                display: flex;
            }
            .message-loading-con {
                margin-top: 50px;
                padding-left: 48%;
            }
        }
    }
</style>
