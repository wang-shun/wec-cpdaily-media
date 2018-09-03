<template>
    <div class="upload-from-local">
        <Upload class="option-item"
                :action="uploadFileUrl"
                with-credentials
                type="select"
                :max-size="3072"
                :before-upload="beforeUpload"
                :on-success="onSuccess"
                :on-error="onError"
                :on-format-error="onFormatError"
                :on-exceeded-size="onExceededSize"
                :format="['jpg', 'jpeg', 'png']"
                accept=".jpg, .jpeg, .png">
            <Button type="ghost" icon="ios-cloud-upload-outline" style="background: white;">本地上传</Button>
        </Upload>
        <Modal title="裁剪图片" v-model="cropperOpt.visible" @on-ok="clipOk" :loading="cropperOpt.loading">
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
            uploadFileUrl: api.uploadFile,
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
                util.httpPost(api.convertBase64, {
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
