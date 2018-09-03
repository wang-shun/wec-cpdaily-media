<template>
    <div class="my-attachments">
        <div class="attachment-item" v-for="(item, index) in value" :key="index">
            <div class="file-meta">
                <div class="file-type">
                    <img :src="getFileTypeImgUrl(item.fileName)" alt="文件类型图片">
                </div>
                <div class="file-name">{{ item.fileName }}</div>
            </div>
            <div class="options">
                <a href="javascript: void 0;" v-if="!readonly" @click="delAttachment(index)">删除</a>
                <a href="javascript: void 0;" v-if="readonly" @click="downloadAttachment(index)">下载</a>
            </div>
        </div>
        <Upload class="add"
                v-show="value.length < 4 && !readonly"
                :show-upload-list="false"
                :action="uploadFileUrl"
                multiple
                with-credentials
                type="select"
                :max-size="10240"
                :before-upload="beforeUpload"
                :on-success="onSuccess"
                :on-error="onError"
                :on-format-error="onFormatError"
                accept=".jpg, .jpeg, .png, .doc, .xlsx, .xls, .xlsm, .xltm, .xlsb, .xlam, .pdf, .zip, .rar, .pptx"
                :format="['jpg', 'jpeg', 'png', 'doc', 'xlsx', 'xls', 'xlsm', 'xltm', 'xlsb', 'xlam', 'pdf', 'zip', 'rar', 'pptx']"
                :on-exceeded-size="onExceededSize">
            <Button type="ghost" icon="ios-cloud-upload-outline" :disabled="alreadyUploadNum>=maxNum">添加附件({{value.length}}/{{maxNum}})</Button>
        </Upload>
    </div>
</template>
<script>
import api from 'conf/api'

const fileTypeImgBaseUrl = 'https://feres.cpdaily.com/custom/forxyh/'
const fileTypeImg = {
    jpeg: fileTypeImgBaseUrl + 'jpeg.png',
    jpg: fileTypeImgBaseUrl + 'jpeg.png',
    png: fileTypeImgBaseUrl + 'png.png',
    doc: fileTypeImgBaseUrl + 'doc.png',
    xls: fileTypeImgBaseUrl + 'xls.png',
    xlsx: fileTypeImgBaseUrl + 'xls.png',
    pdf: fileTypeImgBaseUrl + 'pdf.png',
    pptx: fileTypeImgBaseUrl + 'pptx.png',
    zip: fileTypeImgBaseUrl + 'zip.png',
    rar: fileTypeImgBaseUrl + 'rar.png'
}
export default {
    props: {
        value: {
            type: Array,
            default: () => []
        },
        maxNum: {
            type: Number,
            default: 4
        },
        readonly: {
            type: Boolean,
            default: false
        }
    },
    data () {
        return {
            uploadFileUrl: api.uploadFile,
            alreadyUploadNum: this.value.length
        }
    },
    methods: {
        getFileType (fileName) {
            let pieces = fileName.split('.')
            return pieces[pieces.length - 1]
        },
        getFileTypeImgUrl (fileName) {
            return fileTypeImg[this.getFileType(fileName)]
        },
        beforeUpload (file) {
            Vue.prototype.$Loading.start()
            this.alreadyUploadNum += 1
            if (this.alreadyUploadNum === this.maxNum + 1) { // 只提示一次
                this.$Message.warning('最多只能上传4个附件~')
                return false
            } else if (this.alreadyUploadNum > this.maxNum + 1) {
                return false
            }
        },
        delAttachment (index) {
            this.value.splice(index, 1)
            this.alreadyUploadNum = this.value.length
        },
        downloadAttachment (index) {
            var at = this.value[index].fileUrl
            window.open(at)
        },
        onSuccess (res) {
            Vue.prototype.$Loading.finish()
            let fileType = this.getFileType(res.datas.name)
            let fileTypeUrl = fileTypeImg[fileType]
            this.value.push({
                fileId: res.datas.id,
                fileName: res.datas.name,
                fileUrl: res.datas.fileUrl,
                fileType,
                fileTypeUrl
            })
        },
        onError (err) {
            Vue.prototype.$Loading.error()
            this.alreadyUploadNum -= 1
            console.log(err)
        },
        onFormatError (err) {
            Vue.prototype.$Loading.error()
            this.alreadyUploadNum -= 1
            this.$Message.error('暂不支持该文件类型')
            console.log(err)
        },
        onExceededSize (err) {
            Vue.prototype.$Loading.error()
            this.alreadyUploadNum -= 1
            this.$Message.error('附件大小不得超过10M')
            console.log(err)
        }
    }
}
</script>
<style lang="less" scoped>
.my-attachments {
    display: flex;
    align-items: center;
    .attachment-item {
        width: 196px;
        height: 46px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-right: 8px;
        padding: 0 16px;
        border: 1px solid #D8D8D8;
        .file-meta {
            flex: 1;
            display: flex;
            align-items: center;
            .file-type {
                display: flex;
                align-items: center;
                max-width: 38px;
                max-height: 46px;
                overflow: hidden;
                img {
                    width: 60%;
                }
            }
            .file-name {
                max-width: 78px;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }
        }
        &.add {
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            .icon {
                font-size: 20px;
                color: #3186FF;
            }
        }
        .options {
            width: 30px;
        }
    }
}
</style>
