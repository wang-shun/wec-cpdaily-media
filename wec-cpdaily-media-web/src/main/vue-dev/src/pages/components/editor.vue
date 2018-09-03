<template>
    <div>
        <div id="editor" class="edit-body"></div>
    </div>
</template>

<script>
import E from 'wangeditor'
import api from 'conf/api'

export default {
    name: 'editor',
    props: {
        value: {
            type: String,
            default: ''
        },
        fileUploadUrl: {
            type: String,
            default: ''
        }
    },
    data () {
        return {
            editor: null
        }
    },
    methods: {
        getContent () {
            return this.value
        },
        setContent (content) {
            this.editor.txt.html(content)
        }
    },
    mounted () {
        const vm = this
        const editor = new E('#editor')
        this.editor = editor

        editor.customConfig.menus = [
            'head', // 标题
            'bold', // 粗体
            'italic', // 斜体
            'underline', // 下划线
            'strikeThrough', // 删除线
            'foreColor', // 文字颜色
            'backColor', // 背景颜色
            'link', // 插入链接
            'list', // 列表
            'justify', // 对齐方式
            'quote', // 引用
            // 'emoticon', // 表情
            'image', // 插入图片
            'table', // 表格
            // 'video', // 插入视频
            'code'//, // 插入代码
            // 'undo', // 撤销
            // 'redo' // 重复
        ]

        // NOTE: remove this before build
        // editor.customConfig.debug = true

        editor.customConfig.zIndex = 100

        editor.customConfig.uploadImgServer = this.fileUploadUrl
        editor.customConfig.withCredentials = true
        editor.customConfig.uploadFileName = 'file'
        editor.customConfig.uploadImgMaxSize = 5 * 1000 * 1000
        editor.customConfig.uploadImgMaxLength = 1
        editor.customConfig.uploadImgHooks = {
            before: function (xhr, editor, files) {
                Vue.prototype.$Loading.start()
            },
            success: function (xhr, editor, result) {
                Vue.prototype.$Loading.finish()
            },
            fail: function (xhr, editor, result) {
                Vue.prototype.$Loading.error()
            },
            error: function (xhr, editor) {
                Vue.prototype.$Loading.error()
            },
            timeout: function (xhr, editor) {
                Vue.prototype.$Loading.error()
            },
            customInsert: function (insertImg, result, editor) {
                const url = result.datas.fileUrl
                insertImg(url)
            }
        }
        editor.customConfig.customAlert = function (info) {
            vm.$Message.error(info)
        }
        editor.customConfig.onchange = html => {
            vm.$emit('change', html)
            // setInterval(function() {
            //     vm.$emit('change', html)
            // }, 1000);
        }
        editor.create()
        editor.txt.html(this.value)
    }
}
</script>
<style type="text/css">
</style>
