<template>
    <div class="main-body">
        <breadcrumb :items="bcs"></breadcrumb>
        <div class="body-con">
            <div class="group-header bh-mb-8 bh-mt-4">
                被关注回复设置
            </div>
            <div class="apply-media">
                <div class="step-two">
                    <Form ref="form" :model="formValidate" :label-width="120" :rules="ruleValidate" class="form">
                        <FormItem label="标题" prop="name" label-for="name">
                            <i-input v-model="formValidate.name" placeholder="不超过100字" element-id="name" :maxlength="100"/>
                        </FormItem>
                        <FormItem label="内容" prop="content">
                            <editor ref="editor" :value="formValidate.content" :file-upload-url="uploadFile" @change="editorChange"></editor>
                        </FormItem>
                        <FormItem label="封面" prop="poster">
                            <poster-uploader @on-done="posterUploaded" :upload-file-base64-url="uploadFileBase64Url"></poster-uploader>
                            <div class="poster-img">
                                <img :src="formValidate.poster">
                            </div>
                        </FormItem>
                        <FormItem>
                            <Button type="primary" @click="handleSubmit">保存</Button>
                        </FormItem>
                    </Form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import uploadImg from 'com/UploadImg'
import util from 'conf/util'
import api from 'conf/api'
import breadcrumb from 'com/breadcrumb.vue'
import editor from 'com/editor'
import posterUploader from 'com/posterUploader'

const HtmlValidator = () => (rule, value, callback) => {
    let errors = []
    let plainText = value.replace(/<[^img|>]+>/g, '').trim()
    if (!plainText) {
        errors.push(new Error('内容不能为空'))
    }
    if (value.length > 500) {
        errors.push(new Error('内容不能超过500个字符'))
    }
    callback(errors)
}

const notEmptyValidator = (msg) => (rule, value, callback) => {
    let errors = []
    if (value === null || value === undefined) {
        errors.push(new Error(msg))
    }
    if (typeof value === 'string' && value.trim() === '') {
        errors.push(new Error(msg))
    }
    if (typeof value === 'string' && (/^\s/.test(value) || /\s$/.test(value))) {
        errors.push(new Error(msg))
    }
    if (Array.isArray(value) && value.length === 0) {
        errors.push(new Error(msg))
    }
    callback(errors)
}

export default {
    data () {
        return {
            bcs: [{
                path: '/home/replysetting',
                name: '回复设置'
            }],
            mode: 'view',
            formValidate: {
                name: '',
                content: '',
                poster: 'static/default-cover.png'
            },
            avatarOpt: {
                type: 'avatar',
                clip: true,
                maxSize: 5000,
                multiple: false,
                limit: 1,
                defaultList: []
            },
            certificateOpt: {
                visible: false,
                maxSize: 5000,
                multiple: false,
                limit: 3,
                defaultList: []
            },
            ruleValidate: {
                name: [
                    { required: true, validator: notEmptyValidator('名称不能为空'), trigger: 'blur' },
                    { type: 'string', max: 100, message: '名称不能超过100个字', trigger: 'blur' }
                ],
                content: [
                    { required: true, validator: HtmlValidator() }
                ],
                poster: [
                    { required: true, message: '封面不能为空'}
                ]
            },
            uploadFile: api.uploadFile,
            uploadFileBase64Url: api.convertBase64,
            isAgree: false,
            schools: []
        }
    },
    components: {
        uploadImg,
        breadcrumb,
        editor,
        posterUploader
    },
    methods: {
        posterUploaded (url) {
            this.formValidate.poster = url
        },
        editorChange (html) {
            this.formValidate.content = html
            this.$refs.form.validateField('content')
        },
        getReplyInfo () {
            util.httpGet(api.getReplySetting, {}, util.handler.DATAS).then((data) => {
                this.formValidate.name = data.title
                this.formValidate.poster = data.img
                this.formValidate.content = data.content
                this.$refs.editor.setContent(this.formValidate.content)
            }).catch((res) => {
                this.$Message.error('异常：' + res.message)
            })
        },
        deleteUploadAvatar (img) {
            this.formItem.avatar.splice(0, 1)
        },
        handleSubmit () {
            this.$refs.form.validate(valid => {
                if (valid) {
                    this.Save()
                }
            })
        },
        Save () {
            var info = this.formValidate
            var pms = {
                title: info.name,
                content: info.content,
                img: info.poster === 'static/default-cover.png' ? 'cpdaily-media/default-cover.png' : info.poster,
                originalLink: '',
                summary: info.name
            }
            util.httpPost(api.replySettingSave, pms, util.handler.CODE).then((res) => {
                this.$Message.success('保存成功！')
            }).catch((err) => {
                this.$Message.error('保存失败!')
            })
        }
    },
    mounted () {
        this.getReplyInfo()
    }
}
</script>

<style scoped lang="less" rel="stylesheet/less">
    .main-body{
        .body-con{
            margin: 20px;
            padding: 20px;
            min-height: e("calc(100vh - 144px)");
            background: #FFFFFF;
            box-shadow: 0 0 12px 0 rgba(132,134,219,0.16);
            border-radius: 2px;
            .group-header {
                font-size: 16px;
                font-weight: bold;
            }
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
