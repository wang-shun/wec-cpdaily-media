<template>
    <div>
        <div class="demo-upload-list" :style="{width: showWidth+'px', height: showHeight+'px'}" v-for="item in uploadList">
            <template v-if="item.status === 'finished'">
                <img :src="item.url" style="object-fit: cover;">
                <div class="demo-upload-list-cover" v-if="!uploadOpt.disable">
                    <Icon type="ios-eye-outline" @click.native="handleView(item)"></Icon>
                    <Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
                </div>
            </template>
            <template v-else>
                <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
            </template>
        </div>

        <Upload v-show="uploadVisible" ref="upload" :show-upload-list="false" :default-file-list="uploadOpt.defaultList" :on-success="handleSuccess"
            :format="format" :max-size="uploadOpt.maxSize" :on-format-error="handleFormatError" :before-upload="beforeUpload"
            :on-exceeded-size="handleMaxSize" :multiple="uploadOpt.multiple" type="drag" :action="uploadUrl" style="display: inline-block;" :style="{width: showWidth+'px', height: showHeight+'px'}">
            <div style="text-align:center;padding-top:17px;" :style="{width: showWidth+'px', height: showHeight+'px'}">
                <Icon type="plus-circled" size="22" color="#2E8CF0"></Icon>
                <div class="upload-tip" style="line-height:18px;">
                    <strong style="color: #747986;font-size:14px;">上传照片</strong>
                    <br>
                    <span style="color: #999999;font-size:12px;">小于5M</span>
                </div>
            </div>
        </Upload>
        <Modal title="查看图片" v-model="imageModal">
            <img :src="imageModalUrl" v-if="imageModal" style="width: 100%">
        </Modal>

        <Modal title="裁剪图片" v-model="cropperOpt.visible" @on-ok="clipOk" :loading="cropperOpt.loading">
            <cropper ref="cropper" v-if="cropperOpt.visible" :option="cropperOpt"></cropper>
        </Modal>

    </div>
</template>
<script>
    import Cropper from 'com/Cropper.vue'
    import util from 'conf/util'
    import api from 'conf/api'
    export default {
        props: {
            option: {
                type: Object
            },
            cropOption: {
                type: Object,
                default: () => ({enable: false})
            },
            showWidth: {
                type: Number,
                default: 100
            },
            showHeight: {
                type: Number,
                default: 100
            }
        },
        data () {
            return {
                imageModal: false,
                format: ['jpg', 'jpeg', 'png'],
                uploadUrl: api.uploadFile,
                imageModalUrl: '',
                uploadList: [],
                uploadOpt: JSON.parse(JSON.stringify(this.option)),
                cropperOpt: {
                    loading: true,
                    visible: false,
                    img: '',
                    autoCrop: true,
                    fixedBox:true,
                    autoCropWidth: 200,
                    autoCropHeight: 200
                },
            }
        },
        computed: {
            uploadVisible() {
                if (this.uploadList.length >= this.uploadOpt.limit || this.uploadOpt.disable) {
                    return false
                }
                return true
            }
        },
        methods: {
            beforeUpload(file) {
                const format = this.format.join('|')
                const reg = new RegExp("\.(" + format + ")$");
                if (!reg.test(file.type)) {
                    this.handleFormatError(file)
                    return false;
                }
                if (this.option.clip) {
                    const reader = new FileReader();
                    const vm = this
                    reader.onload = function (data) {
                        vm.showCropper(data.currentTarget.result)
                    }
                    reader.readAsDataURL(file); //读出文件的base64
                    return false
                }
            },
            showCropper(base64) {
                this.cropperOpt.autoCropWidth = this.cropOption.width || 200;
                this.cropperOpt.autoCropHeight = this.cropOption.height || 200;
                this.cropperOpt.visible = true;
                this.cropperOpt.img = base64
            },
            clipOk() {
                const vm = this
                this.$refs.cropper.getCropData((data) => {
                    // services.convertBase64({ data: data }).then(res => {
                    //     vm.$emit('success', { url: res.data.fileUrl })
                    //     const file = {
                    //         url: res.data.fileUrl,
                    //         status: 'finished',
                    //     }
                    //     vm.$refs.upload.fileList.push(file)
                    //     vm.cropperOpt.visible = false
                    //     vm.cropperOpt.loading = false
                    //     vm.$nextTick(() => {
                    //         vm.cropperOpt.loading = true
                    //     })
                    // }, (err) => {
                    //     console.log(err)
                    // })
                    util.httpPost(api.convertBase64, {data}, util.handler.DATAS).then(data => {
                        vm.$emit('success', { url: data.fileUrl })
                        const file = {
                            url: data.fileUrl,
                            status: 'finished',
                        }
                        vm.$refs.upload.fileList.push(file)
                        vm.cropperOpt.visible = false
                        vm.cropperOpt.loading = false
                        vm.$nextTick(() => {
                            vm.cropperOpt.loading = true
                        })
                    })
                })
            },
            handleView(file) {
                this.imageModal = true;
                this.imageModalUrl = file.url;
            },
            handleRemove(file) {
                const fileList = this.$refs.upload.fileList;
                fileList.splice(fileList.indexOf(file), 1);
                this.$emit('delete', file)
            },
            handleSuccess(res, file) {
                file.url = file.response.datas.fileUrl
                this.$emit('success', file)
            },
            handleFormatError(file) {
                this.$Notice.warning({
                    title: '文件格式不正确',
                    desc: '文件 ' + file.name + ' 格式不正确，请上传' + this.format.join('|') + '格式的图片。'
                });
            },
            handleMaxSize(file) {
                this.$Notice.warning({
                    title: '超出文件大小限制',
                    desc: '文件 ' + file.name + ' 太大，不能超过 ' + this.uploadOpt.maxSize + 'kb。'
                });
            }
        },
        mounted() {
            this.uploadList = this.$refs.upload.fileList
        },
        components: {
            Cropper
        },

    }
</script>
<style lang="less" rel="stylesheet/less" scoped>
    .demo-upload-list {
        display: inline-block;
        width: 100px;
        height: 100px;
        text-align: center;
        line-height: 100px;
        border-radius: 4px;
        overflow: hidden;
        background: #fff;
        position: relative;
        box-shadow: 0 1px 1px rgba(0, 0, 0, .2);
        margin:0 10px;
    }

    .demo-upload-list img {
        width: 100%;
        height: 100%;
    }

    .demo-upload-list-cover {
        display: none;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background: rgba(0, 0, 0, .6);
    }

    .demo-upload-list:hover .demo-upload-list-cover {
        display: block;
    }

    .demo-upload-list-cover i {
        color: #fff;
        font-size: 23px;
        cursor: pointer;
        margin: 0 8px;
    }
</style>