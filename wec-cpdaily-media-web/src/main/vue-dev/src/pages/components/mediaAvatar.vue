<template>
    <div>
        <Upload
            :action="uploadFileUrl"
            with-credentials
            type="drag"
            :max-size="3072"
            :before-upload="beforeUpload"
            :on-success="onSuccess"
            :on-error="onError"
            :on-format-error="onFormatError"
            :on-exceeded-size="onExceededSize"
            :format="['jpg', 'jpeg', 'png']"
            accept=".jpg, .jpeg, .png"
            style="width:358px;">
            <div style="padding: 20px 0">
                <Icon type="ios-plus-outline" size="30" style="color: #3399ff;font-weight: 500;"></Icon>
                <p style="font-size: 12px;color: #C1CBD7;letter-spacing: 0;width: 206px;margin: 0 auto;line-height: 14px;">请上传jpg、png、gif格式文件建议尺寸670*330px，大小不超过10M</p>
            </div>
        </Upload>
    </div>
</template>
<script>
import api from 'conf/api'
import util from 'conf/util.js'

export default {
    data () {
        return {
            uploadFileUrl: api.convertBase64Activity,
            fileName: '',
            cropperOpt: {
                loading: true,
                visible: false,
                img: '',
                autoCrop: true,
                fixedNumber: [9, 5],
                fixed: true,
                fixedBox: false
            }
        }
    },
    components: {
        cropper
    },
    methods: {
        beforeUpload (file) {
            this.fileName = file.fileName
            const reader = new FileReader()
            const vm = this
            reader.onload = function (data) {
                vm.cropperOpt.img = data.currentTarget.result
                vm.cropperOpt.visible = true
            }
            reader.readAsDataURL(file) // 读出文件的base64
            return false
        },
        onSuccess () {

        },
        onError () {

        },
        onFormatError () {
            this.$Message.error('暂不支持该格式')
        },
        onExceededSize () {
            this.$Message.error('图片大小不得超过3M')
        },
        clipOk () {
            const vm = this
            this.$refs.cropper.getCropData((data) => {
                // vm.$emit('on-success', { url: data })
                util.httpPost(api.convertBase64Activity, {
                    name: vm.fileName,
                    data
                }).then(res => {
                    vm.$emit('on-done', res.datas.fileUrl)
                    vm.cropperOpt.visible = false
                })
            })
        }
    }
}
</script>
