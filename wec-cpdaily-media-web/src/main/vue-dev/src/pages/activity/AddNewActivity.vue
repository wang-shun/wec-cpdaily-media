<template>
    <div class="add-new-activity">
        <breadcrumb :items="bcs"></breadcrumb>
        <div class="content">
            <Form class="add-form" ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="100">
                <p class="title">新建活动</p>
                <FormItem label="活动主题" prop="name" label-for="name">
                    <i-input v-model="formValidate.name" placeholder="不超过100字" element-id="name" :maxlength="100"/>
                </FormItem>
                <FormItem label="举办时间" prop="time">
                    <Row>
                        <Col span="11">
                        <FormItem prop="activityStartTime">
                            <DatePicker style="width: 231px" type="datetime" placeholder="选择开始时间" format="yyyy-MM-dd HH:mm" @on-change="activityStartTimeChange" :value="formValidate.activityStartTime"></DatePicker>
                        </FormItem>
                        </Col>
                        <Col span="2" style="text-align: center">-</Col>
                        <Col span="11">
                        <FormItem prop="activityEndTime">
                            <DatePicker style="width: 231px" type="datetime" placeholder="选择结束时间" format="yyyy-MM-dd HH:mm" @on-change="activityEndTimeChange" :value="formValidate.activityEndTime"></DatePicker>
                        </FormItem>
                        </Col>
                    </Row>
                </FormItem>
                <FormItem label="举办地点" prop="address" label-for="hold-address">
                    <Input v-model="formValidate.address" placeholder="不超过100字" element-id="hold-address" :maxlength="100"/>
                </FormItem>
                <FormItem label="主办方" prop="host" label-for="host">
                    <Input v-model="formValidate.host" placeholder="不超过100字" element-id="host" :maxlength="100"/>
                </FormItem>
                <FormItem label="报名时间" prop="signTime">
                    <Row>
                        <Col span="11">
                            <FormItem prop="signStartTime">
                                <DatePicker style="width: 231px" type="datetime" placeholder="选择开始时间" format="yyyy-MM-dd HH:mm" @on-change="signStartTimeChange" :value="formValidate.signStartTime"></DatePicker>
                            </FormItem>
                        </Col>
                        <Col span="2" style="text-align: center">-</Col>
                        <Col span="11">
                            <FormItem prop="signStartTime">
                                <DatePicker style="width: 231px" type="datetime" placeholder="选择结束时间" format="yyyy-MM-dd HH:mm" @on-change="signEndTimeChange" :value="formValidate.signEndTime"></DatePicker>
                            </FormItem>
                        </Col>
                    </Row>
                </FormItem>
                <FormItem label="报名人数限制" prop="peopleNumber" label-for="peopleNumber">
                    <Input v-model="formValidate.peopleNumber" placeholder="请填写本次活动能够允许的最大参加人数（选填）" element-id="peopleNumber" type="number" min="0"/>
                </FormItem>
                <FormItem label="活动介绍" prop="introduction">
                    <editor ref="editor" :value="formValidate.introduction" :file-upload-url="uploadFile" @change="editorChange"></editor>
                    <!-- <a style="float:right" @click.prevent="openYiban">使用专业工具>></a> -->
                </FormItem>
                <FormItem label="类别">
                    <RadioGroup v-model="formValidate.checkWay">
                        <Radio :label="1">扫码签到</Radio>
                        <Radio :label="2">人员检票</Radio>
                        <Radio :label="0">两者皆可</Radio>
                    </RadioGroup>
                </FormItem>
                <FormItem v-if="formValidate.checkWay !== 1" label="" prop="checkWay">
                    <div class="check-people">
                        <span class="check-man" v-for="(man, index) in formValidate.tellers" :key="index">{{man.userName + ' ' + man.userId}} <span class="item-remove" @click="removeCheckMan(index)">x</span></span>
                    </div>
                    <Button v-if="formValidate.tellers && formValidate.tellers.length < 5" type="dashed" icon="android-add" style="width:100%;color:#2D8CF0;" @click="addCheckMan">添加检票人</Button>
                </FormItem>

                <FormItem label="发布至同学圈" prop="is_send">
                    <RadioGroup v-model="formValidate.is_send">
                        <Radio :label="0">否</Radio>
                        <Radio :label="1">是</Radio>
                    </RadioGroup>
                    <Select v-if="formValidate.is_send === 1" v-model="formValidate.circleId" filterable clearable>
                        <Option v-for="item in circles" :value="item.id" :key="item.id">{{ item.name }}</Option>
                    </Select>
                </FormItem>

                <p class="title">报名信息收集</p>
                <FormItem label="收集项（多选）">
                    <Checkbox v-model="formValidate.hasTelnumber">手机号</Checkbox>
                    <Checkbox style="margin-left: 10px;" v-model="formValidate.hasRemark">备注（用于收集其他信息）</Checkbox>
                </FormItem>
                <FormItem label="备注说明" prop="remark" label-for="remark">
                    <i-input v-model="formValidate.remark" placeholder="不超过100字" element-id="remark" :maxlength="100"/>
                </FormItem>

                <p class="title">图片上传</p>
                <FormItem label="上传海报" prop="banner">
                    <poster-uploader @on-done="posterUploaded"></poster-uploader>
                    <div class="poster-img" v-if="formValidate.poster">
                        <img :src="formValidate.poster">
                        <div class="opt-panel">
                            <div class="viewer" @click="delBanner">
                                <Icon type="trash-a" size="24"></Icon>
                                <div>删除</div>
                            </div>
                        </div>
                    </div>
                </FormItem>
                <FormItem v-if="!wid">
                    <Button type="primary" @click="handleSubmit">发布</Button>
                    <Button type="ghost" @click="handleSave" style="margin-left: 8px">保存</Button>
                    <Button type="ghost" @click="handleCancel" style="margin-left: 8px">取消</Button>
                </FormItem>
                <FormItem v-if="wid">
                    <Button type="primary" @click="handleSubmit">发布</Button>
                    <Button type="ghost" v-if="formValidate.publishStatus === 0" @click="handleSave" style="margin-left: 8px">保存</Button>
                    <Button type="ghost" @click="handleCancel" style="margin-left: 8px">取消</Button>
                </FormItem>
            </Form>
        </div>
        <Modal
            v-model="modalCheckMan"
            :mask-closable="false"
            @on-ok="addCheckManOk"
            @on-cancel="cancelCheckMan"
            :width="800">
            <choose
                class="bh-mt-32"
                v-if="ifChoose"
                ref="choose"
                keys="userId"
                searchKey="condition"
                :params="userOpt.params"
                :url="userOpt.url"
                :render-left="renderLeft"
                :render-right="renderRight"
                :pageableSetting="userOpt.pageableSetting"
                :max-select-num="5"
                :selected-list="tempTellers">
            </choose>
        </Modal>
    </div>
</template>

<script type="text/ecmascript-6">
import breadcrumb from 'com/breadcrumb.vue'
import choose from 'com/choose/choose.vue'
import * as service from 'services/openService.js'
import editor from 'com/editor'
import posterUploader from 'com/posterUploader'
import util from 'conf/util'
import api from 'conf/api'
const notEmptyHtmlValidator = (msg) => (rule, value, callback) => {
    let errors = []
    let plainText = value.replace(/<[^img|>]+>/g, '').trim()
    if (!plainText) {
        errors.push(new Error(msg))
    }
    callback(errors)
}
export default {
    data () {
        const holdTimeCheck = (rule, value, callback) => {
            if (!this.formValidate.activityStartTime || !this.formValidate.activityEndTime) {
                callback([new Error('请设置举办时间')])
            } else {
                // 1.活动举办时间要晚于当前时间。
                let holdStart = this.formValidate.activityStartTime + ':00'
                let holdEnd = this.formValidate.activityEndTime + ':00'

                let holdStartDate = new Date(holdStart)
                let holdEndDate = new Date(holdEnd)

                var curTime = new Date();
                if (curTime > holdStartDate) {
                    callback([new Error('活动举办时间要晚于当前时间')])
                    return
                }
                if (holdStartDate > holdEndDate) {
                    callback([new Error('活动举办开始时间早于举办结束时间')])
                    return
                }
                callback([])
            }
        }
        const signTimeCheck = (rule, value, callback) => {
            if (!this.formValidate.signStartTime || !this.formValidate.signEndTime) {
                callback([new Error('请设置报名时间')])
            } else {
                if (!this.formValidate.activityStartTime || !this.formValidate.activityEndTime) {
                    callback([new Error('请先设置举办时间')])
                    return
                }

                let signStartDate = new Date(this.formValidate.signStartTime)
                let signEndDate = new Date(this.formValidate.signEndTime)

                let holdStart = this.formValidate.activityStartTime + ':00'
                let holdStartDate = new Date(holdStart)

                if (signStartDate > holdStartDate) {
                    callback([new Error('报名开始时间要早于活动举办开始时间')])
                    return
                }

                if (signStartDate > signEndDate) {
                    callback([new Error('报名开始时间要早于报名结束时间')])
                    return
                }

                callback([])
            }
        }

        const nameCheck = (rule, value, callback) => {
            if (!this.formValidate.name || $.trim(this.formValidate.name) === '') {
                callback([new Error('活动主题不能为空')])
            } else if (this.formValidate.name.length > 100) {
                callback([new Error('活动主题超过100字')])
            } else {
                callback([])
            }
        }

        const addressCheck = (rule, value, callback) => {
            if (!this.formValidate.address || $.trim(this.formValidate.address) === '') {
                callback([new Error('举办地点不能为空')])
            } else if (this.formValidate.address.length > 100) {
                callback([new Error('举办地点超过100字')])
            }  else {
                callback([])
            }
        }

        const remarkCheck = (rule, value, callback) => {
            if (!this.formValidate.remark || $.trim(this.formValidate.remark) === '') {
                callback([])
            } else if (this.formValidate.remark.length > 100) {
                callback([new Error('备注说明超过100字')])
            } else {
                callback([])
            }
        }

        const hostCheck = (rule, value, callback) => {
            if (!this.formValidate.host || $.trim(this.formValidate.host) === '') {
                callback([new Error('举办方不能为空')])
            } else {
                callback([])
            }
        }

        const peopleNumberCheck = (rule, value, callback) => {
            if (this.formValidate.peopleNumber < 0 || this.formValidate.peopleNumber > 9999999 ) {
                callback([new Error('请输入正确的人数限制')])
            } else {
                callback([])
            }
        }

        const tellersCheck = (rule, value, callback) => {
            if (this.formValidate.checkWay === 2 && this.formValidate.tellers.length === 0 ) {
                callback([new Error('请添加检票人')])
            } else {
                callback([])
            }
        }

        const isSendCheck = (rule, value, callback) => {
            if (this.formValidate.is_send === 1 && !this.formValidate.circleId) {
                callback([new Error('请选择同学圈')])
            } else {
                callback([])
            }
        }

        const bannerCheck = (rule, value, callback) => {
            if (!this.formValidate.poster) {
                callback([new Error('请上传海报')])
            } else {
                callback([])
            }
        }

        return {
            ifChoose: true,
            userOpt: {
                url: api.allUsers,
                params: {},
                pageableSetting: {
                    totalRoot: 'datas>totalSize',
                    pageSizeRoot: 'datas>pageSize',
                    pageNumberRoot: 'datas>pageNumber',
                    root: 'datas>rows'
                }
            },
            bcs: [
                {
                    path: '/home/activitylist',
                    name: '校园活动'
                },
                {
                    // path: '/home/addnewactivity',
                    name: '新建活动'
                }
            ],
            uploadFile: api.uploadFileActivity,
            circles: [],
            tempTellers: [],
            formValidate: {
                name: '',
                address: '',
                host: '',
                peopleNumber: '',
                signStartTime: '',
                signEndTime: '',
                introduction: '',
                checkWay: 0,
                is_send: 0,
                circleId:'',
                posterType: 1, // 0 id , 1 url
                poster: '',
                labels: '',
                activityStartTime: '',
                activityEndTime: '',
                tellers: [],
                hasTelnumber: false,
                hasRemark: false,
                remark: ''
            },
            ruleValidate: {
                name: [
                    { required: true, validator: nameCheck, trigger: 'blur' }
                ],
                time: [
                    { required: true, validator: holdTimeCheck, trigger: 'blur' }
                ],
                address: [
                    { required: true, validator: addressCheck, trigger: 'blur' }
                ],
                remark: [
                    { validator: remarkCheck, trigger: 'blur' }
                ],
                host: [
                    { required: true, validator: hostCheck, trigger: 'blur' }
                ],
                signTime: [
                    { required: true, validator: signTimeCheck, trigger: 'blur' }
                ],
                introduction: [
                    { required: true, validator: notEmptyHtmlValidator('介绍不能为空') }
                ],
                peopleNumber: [
                    { validator: peopleNumberCheck, trigger: 'blur' }
                ],
                checkWay: [
                    { validator: tellersCheck, trigger: 'blur' }
                ],
                is_send: [
                    { required: true, validator: isSendCheck, trigger: 'blur' }
                ],
                banner: [
                    { required: true, validator: bannerCheck, trigger: 'blur' }
                ]
            },
            modalCheckMan: false,
            wid: ''
        }
    },
    methods: {
        posterUploaded (url) {
            this.formValidate.poster = url
        },
        editorChange (html) {
            this.formValidate.introduction = html
            this.$refs.formValidate.validateField('introduction')
        },
        handleSubmit () {
            this.Publish('1')
        },
        handleCancel () {
            sessionStorage.setItem('activity-mode', '')
            this.$router.push('/home/activitylist')
        },
        Publish (type) {
            this.$refs['formValidate'].validate((valid) => {
                if (valid) {
                    console.log(this.formValidate.activityStartTime)
                    console.log(this.formValidate.activityEndTime)
                    console.log(this.formValidate.signStartTime)
                    console.log(this.formValidate.signEndTime)
                    let data = {
                        wid: this.formValidate.wid,
                        name: $.trim(this.formValidate.name),
                        address: $.trim(this.formValidate.address),
                        host: $.trim(this.formValidate.host),
                        peopleNumber: this.formValidate.peopleNumber,
                        posterType: this.formValidate.posterType,
                        poster: this.formValidate.poster,
                        introduction: this.formValidate.introduction,
                        labels: this.formValidate.labels,
                        activityStartTime: this.formValidate.activityStartTime,
                        activityEndTime: this.formValidate.activityEndTime,
                        signStartTime: this.formValidate.signStartTime,
                        signEndTime: this.formValidate.signEndTime,
                        publishType: type,
                        checkWay: this.formValidate.checkWay,
                        isSend: this.formValidate.is_send,
                        tellers: this.formValidate.tellers,
                        circleId: this.formValidate.is_send === 0 ? '' : this.formValidate.circleId,
                        hasTelnumber: this.formValidate.hasTelnumber ? 1 : 0,
                        hasRemark: this.formValidate.hasRemark ? 1 : 0,
                        remark: this.formValidate.remark,
                    }
                    console.log(data)
                    var mode = sessionStorage.getItem('mode') === 'edit' ? '保存' : '添加'
                    if (sessionStorage.getItem('mode') === 'edit') {
                        service.editActivity(data)
                            .then(() => {
                                this.$Message.success(mode + '成功！')
                                this.$router.go(-1)
                            }).catch((err) => {
                            this.$Message.error(mode + '失败：' + err.message)
                        })
                    } else {
                        service.addActivity(data)
                            .then(() => {
                                this.$Message.success(mode + '成功！')
                                this.$router.go(-1)
                            }).catch((err) => {
                            this.$Message.error(mode + '失败：' + err.message)
                        })
                    }
                }
            })
        },
        delBanner () {
            this.formValidate.poster = ''
        },
        handleSave () {
            this.Publish('0')
        },
        activityStartTimeChange (time) {
            this.formValidate.activityStartTime = time
        },
        activityEndTimeChange (time) {
            this.formValidate.activityEndTime = time
        },
        signStartTimeChange (time) {
            this.formValidate.signStartTime = time
        },
        signEndTimeChange (time) {
            this.formValidate.signEndTime = time
        },
        removeCheckMan (index) {
            this.formValidate.tellers.splice(index, 1)
        },
        addCheckMan () {
            this.tempTellers = []
            this.tempTellers = this.tempTellers.concat(this.formValidate.tellers)
            this.ifChoose = false
            this.$nextTick(() => {
                this.modalCheckMan = true
                this.ifChoose = true
            })
        },
        addCheckManOk () {
            this.formValidate.tellers = []
            this.formValidate.tellers = this.formValidate.tellers.concat(this.tempTellers)
        },
        cancelCheckMan () {
            this.modalCheckMan = false
        },
        renderLeft (item) {
            return `<span>${item.userName}</span>`
        },
        renderRight (item) {
            return `<span>${item.userName}</span>`
        },
        getAllCircles () {
            util.httpGet(api.allCircles, util.handler.CODE).then((data) => {
                console.log(data)
                if (data && data.code === '0') {
                    this.circles = data.datas
                }
            }).catch((err) => {

            })
        },
        openYiban () {
            window.open('https://q.yiban.cn')
        }
    },
    mounted () {

        this.getAllCircles()

        var mode = sessionStorage.getItem('mode')
        var wid = sessionStorage.getItem('edit-activity-id')
        console.log('mode=>' + mode)
        console.log('wid=>' + wid)
        if (mode === 'edit' && wid) {
            this.bcs = [
                {
                    path: '/home/activitylist',
                    name: '校园活动'
                },
                {
                    path: '/home/addnewactivity',
                    name: '编辑活动'
                }
            ]

            this.wid = wid
            util.httpPost(api.viewActivity + '?wid=' + this.wid, {}, util.handler.DATAS).then((data) => {
                if (!data.tellers) {
                    data.tellers = []
                }
                this.formValidate.wid = data.wid
                this.formValidate.name = data.name
                this.formValidate.address = data.address
                this.formValidate.host = data.host
                this.formValidate.peopleNumber = data.peopleNumber
                this.formValidate.posterType = data.posterType
                this.formValidate.poster = data.poster
                this.formValidate.introduction = data.introduction
                this.formValidate.labels = data.labels
                this.formValidate.activityStartTime = data.activityStartTime
                this.formValidate.activityEndTime = data.activityEndTime
                this.formValidate.signStartTime = data.signStartTime
                this.formValidate.signEndTime = data.signEndTime
                this.formValidate.checkWay = data.checkWay
                this.formValidate.is_send = data.isSend
                this.formValidate.tellers = data.tellers
                this.formValidate.circleId = data.circleId
                this.formValidate.hasTelnumber = data.hasTelnumber === 1 ? true : false
                this.formValidate.hasRemark = data.hasRemark === 1 ? true : false
                this.formValidate.remark = data.remark
                this.formValidate.publishStatus = data.publishStatus
                this.$refs.editor.setContent(data.introduction)
            }).catch((res) => {
                this.$Message.error('异常：' + res.message)
            })
        }
    },
    components: {
        breadcrumb,
        choose,
        editor,
        posterUploader
    }
}
</script>

<style scoped lang="less" rel="stylesheet/less">
    .add-new-activity{
        .content{
            margin: 20px;
            min-height: e("calc(100vh - 144px)");
            background: #FFFFFF;
            padding: 26px 24px 26px 24px;
            box-shadow: 0 0 12px 0 rgba(132,134,219,0.16);
            border-radius: 2px;
            .add-form{
                width: 605px;
                .title{
                    font-size: 16px;
                    color: #464C5B;
                    margin-bottom: 24px;
                    letter-spacing: 0;
                    line-height: 16px;
                    font-weight: 500;
                }
                .check-man {
                    // line-height: 2em;
                    display: inline-block;
                    border: 1px solid #DDDEE1;
                    padding: 0 0.5em;
                    color: #495060;
                    margin-right: 5px;
                    margin-bottom: 5px;
                    span {
                        margin-left: 5px;
                        cursor: pointer;
                        color: #aaa;
                    }
                }
                .poster-img {
                    height: 165px;
                    width: 335px;
                    img {
                        height: 100%;
                        width: 100%;
                    }
                    .opt-panel {
                        text-align: center;
                        display: none;
                        position: absolute;
                        height: 165px;
                        width: 335px;
                        top: 86px;
                        line-height: 200px;
                        background: rgba(100, 100, 100, 0.8);
                        .viewer {
                            height: 50px;
                            line-height: 18px;
                            color: #ddd;
                            text-align: center;
                            cursor: pointer;
                            width: 60px;
                            display: inline-block;
                            font-weight: 700;

                            &:hover {
                                color: #fff;
                            }
                        }
                    }

                    &:hover .opt-panel {
                        display: block;
                    }
                }
            }
        }
    }
</style>
