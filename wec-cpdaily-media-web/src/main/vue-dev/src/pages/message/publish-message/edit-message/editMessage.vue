<template>
    <section class="edit-con">
        <div class="message-edit">
            <div class="part bh-mv-16">发布内容编辑</div>
            <Form ref="form" :model="message" :rules="ruleValidate" :label-width="80">
                <FormItem label="标题" prop="title">
                    <Input v-model="message.title" placeholder="消息标题"></Input>
                </FormItem>
                <FormItem label="内容" prop="content">
                    <editor ref="editor" :value="message.content" :file-upload-url="uploadFile" @change="editorChange"></editor>
                    <!-- <a style="float:right" @click.prevent="openYiban">使用专业工具>></a> -->
                </FormItem>
                <FormItem label="上传附件">
                    <attachments v-model="message.attachments"></attachments>
                    <div class="attachments-desc bh-mt-4">附件类型只支持：压缩包、png、jpg、doc、excel、PDF格式
单个文件大小不得超过10M，不得超过4个附件</div>
                </FormItem>
            </Form>
            <div class="part bh-mv-16">发布样式编辑</div>
            <Form :model="message" :label-width="80">
                <FormItem label="封面" prop="img">
                    <div class="options">
                        <upload-from-local class="option-item" @on-done="doneChooseImg" :file-upload-url="uploadFile"></upload-from-local>
                    </div>
                    <div class="hint">
                        大图片建议尺寸：900像素*500像素
                    </div>
                    <div class="preview">
                        <img :src="message.img" alt="" height="100%">
                    </div>
                </FormItem>
                <FormItem>
                    <Button type="primary" @click="next">下一步</Button>
                    <Button type="ghost" style="margin-left: 8px" @click="cancel">取消</Button>
                </FormItem>
            </Form>
        </div>
        <div class="message-view">
            <message-preview :info="cInfo"></message-preview>
        </div>
    </section>
</template>
<script>
import api from 'conf/api'
import editor from 'com/editor'
import attachments from 'com/attachments'
import uploadFromLocal from 'com/UploadFromLocal'
import messagePreview from './messagePreview'
// import fileUpload from 'com/file-upload/fileUpload'
const HtmlValidator = () => (rule, value, callback) => {
    let errors = []
    let plainText = value.replace(/<[^img|>]+>/g, '').trim()
    plainText = plainText.replace(/&nbsp;/g, '').trim()
    if (!plainText) {
        errors.push(new Error('内容不能为空'))
    }
    if (value.length > 10000) {
        console.log('超出1万字')
        console.log(value)
        errors.push(new Error('内容不能超过1万个字符'))
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
            message: {
                title: '',
                content: '',
                attachments: [],
                img: 'static/default-cover.png'
            },
            keyword: '',
            ruleValidate: {
                title: [
                    { required: true, message: '标题不能为空', trigger: 'blur' },
                    { type: 'string', max: 50, message: '标题不能超过50个字符', trigger: 'blur' },
                    { validator: notEmptyValidator('标题前后不能有空格') }
                ],
                content: [
                    { required: true, validator: HtmlValidator() }
                ],
                img: [
                    { validator: notEmptyValidator('请选择封面图片') }
                ]
            },
            uploadFile: api.uploadFile
        }
    },
    components: {
        // fileUpload
        editor,
        messagePreview,
        attachments,
        uploadFromLocal
    },
    computed: {
        ...Vuex.mapState({
            index: state => state.index
        }),
        cInfo () {
            return {
                pic: this.message.img,
                title: this.message.title
            }
        }
    },
    props: {
        messageInfo: {
            type: Object,
            default: () => ({})
        }
    },
    methods: {
        editorChange (html) {
            this.message.content = html
            this.$refs.form.validateField('content')
        },
        doneChooseImg (img) {
            this.message.img = img
        },
        next () {
            this.$refs.form.validate(valid => {
                if (valid) {
                    this.$emit('next')
                } else {
                    this.$Message.error('请检查表单')
                }
            })
        },
        cancel () {
            sessionStorage.setItem('current-message', '')
            this.$router.push('/home/message')
        },
        getMessageContent () {
            var info = $.extend(true, {}, this.message)
            info.attachments = info.attachments.map((atm) => { return atm.fileUrl }).join(',')
            return info
        },
        openYiban () {
            window.open('https://q.yiban.cn')
        }
    },
    mounted () {
        var type = this.$route.params.type
        if (type && (type === 'copy' || type === 'edit') ) {
            var info = JSON.parse(JSON.stringify(this.messageInfo))
            var imgs = info.attachments ? info.attachments.split(',') : []
            info.attachments = imgs.map((fileUrl) => { return {fileName: fileUrl.substr(fileUrl.lastIndexOf('/') + 1), fileUrl: fileUrl} })
            this.message = info
            this.$refs.editor.setContent(info.content)
        }
    }
}
</script>

<style scoped lang="less">
    .edit-con {
        flex: 1;
        display: flex;
        .message-edit {
            width: 60%;
            padding: 0 20px;
            border-right: 1px solid #E3E8EE;
            .part {
                font-size: 14px;
                font-weight: bold;
            }
            .attachments-desc {
                color: #C1CBD7;
                font-size: 12px;
                line-height: 1.5em;
            }
        }
        .options {
            display: flex;
            .option-item {
                margin-right: 8px;
                button {
                    background-color: white !important;
                }
            }
        }
        .message-view {
            flex: 1;
            padding: 0 20px;
        }
    }
    .hint {

    }
    .preview {
        height: 180px;
    }
</style>
<style type="text/css">
    .w-e-text {
        word-break: break-all;
    }
</style>