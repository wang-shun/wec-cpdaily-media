<template>
    <div>
        <Upload
            :action="uploadFileUrl"
            with-credentials
            :max-size="3072"
            :before-upload="beforeUpload"
            :on-success="onSuccess"
            :on-error="onError"
            :on-format-error="onFormatError"
            :on-exceeded-size="onExceededSize"
            :format="['jpg', 'jpeg', 'png']"
            accept=".jpg, .jpeg, .png"
            style="width:358px;">
            <Button type="ghost" icon="ios-cloud-upload-outline">本地上传</Button>
            <p style="font-size: 12px;color: #C1CBD7;letter-spacing: 0;line-height: 14px; margin-top:8px;">请上传jpg、png、gif格式文件建议尺寸670*330px，大小不超过10M</p>
        </Upload>
        <Modal title="裁剪图片" v-model="cropperOpt.visible" @on-ok="clipOk" :loading="cropperOpt.loading" :width="705">
            <cropper ref="cropper" v-if="cropperOpt.visible" :option="cropperOpt"></cropper>
        </Modal>
    </div>
</template>
<script>
import api from 'conf/api'
import cropper from 'com/Cropper'
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
                autoCropWidth: 670,
                autoCropHeight: 330,
                fixedNumber: [67, 33],
                fixed: true,
                fixedBox: false
            }
        }
    },
    components: {
        cropper
    },
    props: {
        uploadFileBase64Url: {
            type: String,
            default: ''
        }
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
                util.httpPost(this.uploadFileBase64Url || api.convertBase64Activity, {
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
