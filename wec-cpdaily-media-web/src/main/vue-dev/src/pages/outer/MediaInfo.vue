<template xmlns:v-bind="">
    <div class="apply-media">
        <p class="title">校园号资料</p>
        <div class="step-two">
            <Form ref="form" :model="formItem" :label-width="120" :rules="ruleValidate" class="form">
                <FormItem label="所属学校" prop="school">
                    <Input v-model="tenantInfo.tenantName" placeholder="请输入" disabled></Input>
                </FormItem>
                <FormItem label="校园号名称" prop="name">
                    <Input v-model.trim="formItem.name" placeholder="请输入" :disabled="formItem.authStatus === 'AUTHED'" :maxlength="10"></Input>
                    <p class="tip">2~10个字，请勿使用含特殊符号或含有明显营销推广意图的媒体名。</p>
                </FormItem>
                <FormItem label="校园号介绍" prop="description">
                    <Input v-model.trim="formItem.description" type="textarea" :autosize="{minRows: 4,maxRows: 6}" placeholder="请输入..." :maxlength="100"></Input>
                    <p class="tip">0~100个字，要求内容完整通顺，请勿添加任何联系方式如微博、手机号、QQ</p>
                </FormItem>
                <FormItem label="校园号头像" prop="avatar">
                    <upload-img v-if="avatarCheck" :option="avatarOpt" @success="uploadAvatarSucc" @delete="deleteUploadAvatar"></upload-img>
                    <p class="tip">要求清晰、健康、代表品牌形象
                        <br> 请勿使用二维码，最大5M，200x200像素</p>
                </FormItem>
                <FormItem label="个人主页背景" prop="bgImg">
                    <upload-img v-if="avatarCheck" :showWidth="375" :showHeight="108" :cropOption="bgImgCropOption" :option="bgImgOpt" @success="uploadBackgroundImgSucc" @delete="deleteUploadBackgroundImg"></upload-img>
                </FormItem>
                <FormItem label="校园号认证">
                    <upload-img v-if="avatarCheck" :option="certificateOpt" @success="uploadCertificateSucc" @delete="deleteUploadCertificate"></upload-img>
                    <p class="tip">提供经学校核实的合法性证明材料，要求清晰的证明照片，最大5M，不得超过3张</p>
                </FormItem>
            </Form>
        </div>
        <div v-if="mode === 'edit'" class="bottom">
            <Button type="primary" class="btn" @click="submit">提交</Button>
            <Button type="ghost" class="btn" style="margin-left: 8px;" @click="cancel">取消</Button>
        </div>
        <div v-if="mode === 'view'" class="bottom">
            <Button type="primary" class="btn" @click="submit">提交</Button>
            <Button type="ghost" class="btn" style="margin-left: 8px;" @click="cancel">取消</Button>
        </div>
    </div>
</template>

<script>
import uploadImg from 'com/UploadImg'
import util from 'conf/util'
import api from 'conf/api'
import Bus from 'services/Bus.js'

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
        errors.push(new Error('校园号名称不能包含空格'))
    }
    callback(errors)
}

export default {
    data () {
        return {
            tenantInfo: {},
            avatarCheck: true,
            mode: 'view',
            formItem: {
                school: '',
                name: '',
                description: '',
                bgImg: [],
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
                    { required: true, validator: nameValidator('名称不能为空'),trigger: 'blur' },
                    { type: 'string', min: 2, max: 10, message: '名称必须在2-10字之间', trigger: 'blur' }
                ],
                description: [
                    { required: true, message: '介绍不能为空', trigger: 'blur' },
                    { type: 'string', min: 0, max: 100, message: '介绍必须在0-100字之间', trigger: 'blur' }
                ],
                avatar: [
                    { required: true, validator: notEmptyArrayValidator('头像不能为空') }
                ]
            },
            schools: []
        }
    },
    components: {
        uploadImg
    },
    methods: {
        isNameDisable () {
            if (this.formItem.authStatus === 'AUTHED') {
                return 'true'
            } else {
                return 'false'
            }

        },
        chooseFunction () {
            this.type = 1
        },
        chooseStudent () {
            this.type = 2
        },
        submit() {
            this.$refs.form.validate((valid) => {
                if (valid) {
                    this.isSubmit()
                }
            })
        },
        cancel(){
            if ( this.formItem && this.formItem.authStatus === 'AUTHED') {
                this.$router.replace('/home')
            } else {
                Bus.$emit('switchMedia', -1)
                this.$router.replace('/outer/medialist')
            }
        },
        isSubmit() {
            const vm = this
            if (this.formItem.authStatus !== 'AUTHED' && this.formItem.certificate.length === 0) {
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
                wid: formValue.wid,
                tenantId: formValue.school,
                description: formValue.description,
                backgroundImg: formValue.bgImg.join(','),
                icon: formValue.avatar.join(','),
                authMaterials: formValue.certificate.join(','),
                mediaType: this.type === 1 ? 'CLUB' : 'OFFICIAL'
            }
            console.log(params)
            util.httpPost(api.updateMedia, params, {}).then((data) => {
                console.log(data)
                if (data.code == '0') {
                    this.$Message.success('校园号更新成功')
                    if ( this.formItem && this.formItem.authStatus === 'AUTHED') {
                        this.$router.replace('/home')
                    } else {
                        this.$router.replace('/outer/medialist')
                        Bus.$emit('switchMedia', -1)
                    }
                } else {
                    this.$Message.error('校园号更新失败:' + data.message)
                }
            }).catch((err) => {
                this.$Message.error('校园号更新失败：' + err.message)
            })
        },
        getAccountSchools () {
            util.httpGet(api.accountSchools, {}, util.handler.DATAS).then((data) => {
                this.schools = data
            }).catch((res) => {
                this.$Message.error('异常：' + res.message)
            })
        },
        uploadAvatarSucc (img) {
            this.formItem.avatar.push(img.url)
            this.$refs.form.validateField('avatar')
        },
        uploadBackgroundImgSucc (img) {
            this.formItem.bgImg.push(img.url)
        },
        deleteUploadAvatar (img) {
            this.formItem.avatar.splice(0, 1)
        },
        deleteUploadBackgroundImg (img) {
            this.formItem.bgImg.splice(0, 1)
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
            util.httpGet(api.mediaInfo + '/' + this.$route.params.mediaId, {}, util.handler.DATAS).then((data) => {
                console.log(data)

                this.formItem = data
                this.formItem.school = this.formItem.tenantId

                if (data.authStatus === 'AUTHED') {
                    this.certificateOpt.disable = true
                }

                this.formItem.avatar = []
                if (this.formItem.icon) {
                   this.formItem.avatar.push(this.formItem.icon)
                   this.avatarOpt.defaultList.push({url: this.formItem.icon}) 
                }
                
                this.formItem.bgImg = []
                if (this.formItem.backgroundImg) {
                    this.formItem.bgImg.push(this.formItem.backgroundImg)
                    this.bgImgOpt.defaultList.push({url: this.formItem.backgroundImg})
                }
                
                this.formItem.certificate = []
                if (this.formItem.authMaterials) {
                    let tempMaterials = this.formItem.authMaterials.split(',')
                    if (tempMaterials.length > 0) {
                        for (let i = 0; i < tempMaterials.length ; i ++) {
                            this.formItem.certificate.push(tempMaterials[i])
                            this.certificateOpt.defaultList.push({url: tempMaterials[i]})
                        }
                    }
                }
                this.avatarCheck = false
                this.$nextTick(() => {
                    this.avatarCheck = true
                })
            }).catch((err) => {
                this.$Message.error('获取校园号信息失败：' + err.errMsg)
            })
        },
        getMediaTenant () {
            util.httpGet(api.mediaTenant + '/' + this.$route.params.mediaId, {}, util.handler.DATAS).then((data) => {
                if (data) {
                    this.tenantInfo = data
                }
            }).catch((err) => {
                console.log(err)
            })
        }
    },
    mounted () {
        this.getAccountSchools()
        let mediaId = this.$route.params.mediaId
        if(mediaId){
            this.getMediaInfo()
            this.getMediaTenant()
            Bus.$emit('switchMedia', mediaId)
        }
    }
}
</script>

<style scoped lang="less" rel="stylesheet/less">
    .apply-media {
        margin: 20px;
        width: 1232px;
        padding: 26px 24px 26px 24px;
        box-sizing: border-box;
        min-height: 656px;
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
