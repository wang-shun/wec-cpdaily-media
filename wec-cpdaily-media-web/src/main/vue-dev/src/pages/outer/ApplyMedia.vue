<template>
    <div class="apply-media">
        <p class="title">校园号申请</p>
        <div class="step">
            <Steps :current="current" >
                <Step title="选择类型"></Step>
                <Step title="填写申请资料"></Step>
            </Steps>
        </div>
        <p class="line"></p>
        <!-- step one-->
        <div v-if="current === 0" class="step-one">
            <div class="card-container">
                <div class="card-item-outer" style="margin-left: 288px;" @click="chooseFunction">
                    <div class="card" :class="typeFunctionClass">
                        <Icon v-if="type === 1" type="ios-checkmark-outline" class="check-icon" size="28"></Icon>
                        <Icon v-if="type === 2" type="checkmark-circled" class="check-icon" size="28"></Icon>
                        <img src="static/offical.png" style="width:120px;margin-top:50px;margin-left:80px;">
                        <div class="type bh-mt-8">学校职能部门</div>
                    </div>
                    <p class="title">适合由学校专职人员组成</p>
                    <p class="desc">旨在提供校内师生管理服务的职能部门申请</p>
                </div>
                <div class="card-item-outer" style="margin-right: 288px;margin-left: 56px;" @click="chooseStudent">
                    <div class="card" :class="typeStudentClass">
                        <Icon v-if="type === 2" type="ios-checkmark-outline" class="check-icon" size="28"></Icon>
                        <Icon v-if="type === 1" type="checkmark-circled" class="check-icon" size="28"></Icon>
                        <img src="static/personal.png" style="width:120px;margin-top:50px;margin-left:80px;">
                        <div class="type bh-mt-8">学生组织</div>
                    </div>
                    <p class="title">适合由学校学生组成</p>
                    <p class="desc">受学校党委领导、团委指导、自我服务、自我提高、自我管理、辅助教学的学生组织、社团组织等团体申请</p>
                </div>
            </div>
        </div>
        <!-- step two-->
        <div v-if="current === 1" class="step-two">
            <Form ref="form" :model="formItem" :label-width="120" :rules="ruleValidate" class="form">
                <FormItem label="所属学校" prop="school">
                    <Select v-if="schools.length > 0" v-model="formItem.school" >
                        <Option v-for="item in schools" :value="item.tenantId" :key="item.tenantId">{{ item.tenantName }}</Option>
                    </Select>
                </FormItem>
                <FormItem label="校园号名称" prop="name">
                    <Input v-model.trim="formItem.name" placeholder="请输入"></Input>
                    <p class="tip">2~10个字，请勿使用含特殊符号或含有明显营销推广意图的媒体名。</p>
                </FormItem>
                <FormItem label="校园号介绍" prop="description">
                    <Input v-model="formItem.description" type="textarea" :autosize="{minRows: 4,maxRows: 6}" placeholder="请输入..." :maxlength="100"></Input>
                    <p class="tip">0~100个字，要求内容完整通顺，请勿添加任何联系方式如微博、手机号、QQ</p>
                </FormItem>
                <FormItem label="校园号头像" prop="avatar">
                    <upload-img :option="avatarOpt" @success="uploadAvatarSucc" @delete="deleteUploadAvatar"></upload-img>
                    <p class="tip">要求清晰、健康、代表品牌形象
                        <br> 请勿使用二维码，最大5M，200x200像素</p>
                </FormItem>
                <FormItem label="个人主页背景" prop="backgroundImg">
                    <upload-img :showWidth="375" :showHeight="108"  :cropOption="bgImgCropOption" :option="bgImgOpt" @success="uploadBackgroundImgSucc" @delete="deleteUploadBackgroundImg"></upload-img>
                </FormItem>
                <FormItem label="校园号认证">
                    <upload-img :option="certificateOpt" @success="uploadCertificateSucc" @delete="deleteUploadCertificate"></upload-img>
                    <p class="tip">提供经学校核实的合法性证明材料，要求清晰的证明照片，最大5M，不得超过3张</p>
                </FormItem>
            </Form>
        </div>
        <div v-if="current === 0" class="bottom">
            <Button type="primary" class="btn" @click="nextStep">下一步</Button>
            <Button type="ghost" class="btn" style="margin-left: 8px;" @click="cancel">返回</Button>
        </div>
        <div v-if="current === 1" class="bottom">
            <Button type="primary" class="btn" @click="submit">提交</Button>
            <Button type="ghost" class="btn" style="margin-left: 8px;" @click="prev">上一步</Button>
            <div class="bh-mt-16">
                <Checkbox style="margin-left: 90px;" v-model="isAgree">我已阅读并同意<a @click.prevent="viewProtocol">《校园号用户注册协议》</a></Checkbox>
            </div>
        </div>
    </div>
</template>

<script>
import uploadImg from 'com/UploadImg'
import util from 'conf/util'
import api from 'conf/api'

const notEmptyArrayValidator = (msg) => (rule, value, callback) => {
    let errors = []
    if (!value || value.length < 1) {
        errors.push(new Error(msg))
    }
    callback(errors)
}

const nameValidator = (msg) => (rule, value, callback) => {
    let errors = []
    if (!value) {
        errors.push(new Error(msg))
    }
    if (/\s/.test(value)) {
        errors.push(new Error('名称不能包含空格'))
    }
    callback(errors)
}

const descValidator = (msg) => (rule, value, callback) => {
    let errors = []
    if (!value) {
        errors.push(new Error(msg))
    }
    if (/\s/.test(value)) {
        errors.push(new Error('介绍不能包含空格'))
    }
    callback(errors)
}

export default {
    data () {
        return {
            current: 0,
            type: 1,
            formItem: {
                school: '',
                name: '',
                description: '',
                backgroundImg: [],
                avatar: [],
                certificate: []
            },
            avatarOpt: {
                type: 'avatar',
                clip: true,
                maxSize: 5000,
                multiple: false,
                limit: 1,
                defaultList: []
            },
            bgImgOpt: {
                type: 'avatar',
                clip: true,
                maxSize: 5000,
                multiple: false,
                limit: 1,
                defaultList: []
            },
            bgImgCropOption: {
                width: 375,
                height: 108
            },
            certificateOpt: {
                visible: false,
                maxSize: 5000,
                multiple: false,
                limit: 3,
                defaultList: []
            },
            ruleValidate: {
                school: [
                    { required: true, message: '所属学校不能为空', trigger: 'blur' }
                ],
                name: [
                    { required: true, validator: nameValidator('名称不能为空'),  trigger: 'blur' },
                    { type: 'string', min: 2, max: 10, message: '名称必须在2-10字之间', trigger: 'blur' }
                ],
                description: [
                    { required: true, validator: descValidator('介绍不能为空'), trigger: 'blur' },
                    { type: 'string', min: 0, max: 100, message: '介绍必须在0-100字之间', trigger: 'blur' }
                ],
                avatar: [
                    { required: true, validator: notEmptyArrayValidator('头像不能为空') }
                ]
            },
            isAgree: false,
            schools: []
        }
    },
    components: {
        uploadImg
    },
    methods: {
        chooseFunction () {
            this.type = 2
        },
        chooseStudent () {
            this.type = 1
        },
        nextStep () {
            this.current = 1
        },
        submit() {
            if (!this.isAgree) {
                this.$Message.error('同意校园号注册协议后才能提交')
                return
            }
            this.$refs.form.validate((valid) => {
                if (valid) {
                    this.isSubmit()
                }
            })
        },
        isSubmit() {
            const vm = this
            if (this.formItem.certificate.length === 0) {
                this.$Modal.confirm({
                    title: '请确认',
                    content: '未提交认证申请将不能使用校园号平台功能，确定提交吗?',
                    okText: '确定',
                    cancelText: '取消',
                    loading: true,
                    onOk: function () {
                        vm.$Modal.remove()
                        vm.submitOk()
                    }
                })
            } else {
                vm.submitOk()
            }
        },
        submitOk() {
            const formValue = this.formItem
            let params = {
                name: formValue.name,
                wid: formValue.id,
                tenantId: formValue.school,
                description: formValue.description,
                icon: formValue.avatar.join(','),
                backgroundImg: formValue.backgroundImg.join(','),
                authMaterials: formValue.certificate.join(','),
                mediaType: this.type === 1 ? 'CLUB' : 'OFFICIAL'
            }
            util.httpPost(api.mediaApply, params, util.handler.CODE).then((res) => {
                this.$Message.success('校园号申请成功')
                this.$router.replace('/outer/medialist')
            }).catch((err) => {
                this.$Message.error('校园号申请失败：' + err.message)
            })
        },
        cancel () {
            this.$router.push('/outer/medialist')
        },
        prev () {
            this.current = 0
        },
        getAccountSchools () {
            util.httpGet(api.accountSchools, {}, util.handler.DATAS).then((data) => {
                this.schools = data
                if (this.schools.length === 1) {
                    this.formItem.school = this.schools[0].tenantId
                }
                if (this.$route.query.wid) {
                    this.getMediaInfo()
                }
            }).catch((res) => {
                this.$Message.error('异常：' + res.message)
            })
        },
        uploadAvatarSucc (img) {
            this.formItem.avatar.push(img.url)
            this.$refs.form.validateField('avatar')
        },
        uploadBackgroundImgSucc (img) {
            this.formItem.backgroundImg.push(img.url)
        },
        deleteUploadAvatar (img) {
            this.formItem.avatar.splice(0, 1)
        },
        deleteUploadBackgroundImg (img) {
            this.formItem.backgroundImg.splice(0, 1)
        },
        uploadCertificateSucc (img) {
            this.formItem.certificate.push(img.url)
        },
        deleteUploadCertificate (img) {
            const certificate = this.formItem.certificate
            certificate.splice(certificate.indexOf(img.url), 1)
        },
        viewProtocol () {
            window.open('#/outer/protocol')
        },
        getMediaInfo () {
            util.httpGet(api.mediaInfo + '/' + this.$route.query.wid, {}, util.handler.DATAS).then((data) => {

                console.log('getMediaInfo')

                if (data.mediaType === 'CLUB') {
                    this.type = 1
                } else {
                    this.type = 2
                }

                this.formItem = data
                this.formItem.school = this.formItem.tenantId

                this.formItem.avatar = []
                this.formItem.avatar.push(this.formItem.icon)
                this.avatarOpt.defaultList.push({url: this.formItem.icon})

                this.formItem.certificate = []

                this.avatarCheck = false
                this.$nextTick(() => {
                    this.avatarCheck = true
                })
            }).catch((err) => {
                this.$Message.error('获取校园号信息失败：' + err.errMsg)
            })
        }
    },
    computed: {
        typeFunctionClass () {
            return this.type === 2 ? 'card-selected' : 'card-un-select'
        },
        typeStudentClass () {
            return this.type === 1 ? 'card-selected' : 'card-un-select'
        }
    },
    mounted () {
        this.getAccountSchools()
    }
}
</script>

<style scoped lang="less" rel="stylesheet/less">
    .apply-media {
        margin: 20px;
        width: 1232px;
        padding: 26px 24px 26px 24px;
        box-sizing: border-box;
        min-height: e("calc(100vh - 130px)");
        background: #FFFFFF;
        box-shadow: 0 0 12px 0 rgba(132, 134, 219, 0.16);
        border-radius: 2px;
        .title{
            font-size: 16px;
            color: #464C5B;
            letter-spacing: 0;
            line-height: 16px;
            font-weight: 500;
        }
        .step{
            margin-top: 26px;
            margin-left: 240px;
            margin-right: 155px;
            width: 100%;
        }
        .line{
            width: 100%;
            height: 1px;
            background: #E3E8EE;
            margin: 30px auto 0px auto;
        }
        .step-one{
            box-sizing: border-box;
            .card-container{
                display: flex;
                margin: 40px auto 0px auto;
                box-sizing: border-box;
                .card-item-outer{
                    flex: none;
                    width: 278px;
                    cursor: pointer;
                    .card{
                        position: relative;
                        box-sizing: border-box;
                        width: 278px;
                        height: 222px;
                        background: #FFFFFF;
                        border: 1px solid #2D8CF0;
                        box-shadow: 0 5px 20px 0 rgba(70,76,91,0.10);
                        border-radius: 4px;
                        .check-icon{
                            position: absolute;
                            right: 16px;
                            top: 16px;
                            color: #2D8CF0;
                        }
                        .type {
                            text-align: center;
                            font-size: 16px;
                            color: #495060;
                            font-weight: bold;
                        }
                    }
                    .card-selected{
                        border: 1px solid #2D8CF0;
                    }
                    .card-un-select{
                        border: 1px solid rgba(227,232,238,0.68);
                    }
                    .title{
                        margin-top: 28px;
                        font-size: 14px;
                        color: #80848F;
                        letter-spacing: 0;
                        font-weight: 500;
                        text-align: center;
                        font-weight: bold;
                    }
                    .desc{
                        margin-top: 2px;
                        margin-left: 10px;
                        margin-left: 10px;
                        font-size: 12px;
                        color: #80848F;
                        letter-spacing: 0;
                        text-align: center;
                    }
                }
            }
        }
        .step-two {
            width: 640px;
            margin: 0 auto;
            margin-top: 20px;
        }
        .bottom{
            margin-top: 58px;
            text-align: center;
            .btn{
                width: 100px;
            }
        }
    }
    @media screen {
        /* 大于1300px的屏幕 */
        @media (min-width: 1280px){
            .apply-media{
                margin: 20px auto;
            }
        }
    }
</style>
