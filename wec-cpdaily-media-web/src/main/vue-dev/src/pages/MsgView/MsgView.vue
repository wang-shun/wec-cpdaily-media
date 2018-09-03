<template>
    <div class="msg-view">
        <div v-html="msgContent">
        </div>
        <div class="attachments">
            <div v-for="item in cAtts" class="attach-item">
                <div class="attach-icon">
                    <img width="30" :src="item.icon">
                </div>
                <div class="name">
                    {{item.name}}
                </div>
                <a @click.prevent="viewD(item)">查看</a>
            </div>
        </div>
    </div>
</template>

<script>
import util from 'conf/util'
import api from 'conf/api'
export default {
    data () {
        return {
            msgContent: '',
            attachments: ''
        }
    },
    computed: {
        cAtts () {
            if (this.attachments) {
                return this.attachments.split(',').map(url => {
                    let suffix = url.substr(url.lastIndexOf('.') + 1).toLowerCase()
                    let type = ['doc', 'jpg', 'jpeg', 'pdf', 'png', 'pptx', 'xls', 'zip'].find((item) => (item == suffix)) || 'default'
                    let icon = `static/attach_${type}@2x.png`
                    return {
                        type: suffix,
                        url: url,
                        icon: icon,
                        name: url.substr(url.lastIndexOf('/') + 1),
                        isImg: ['jpg','jpeg','png','gif','bmp'].find(item => item == suffix)
                    }
                })
            } else {
                return []
            }
        }
    },
    methods: {
        viewD (item) {
            // 预览文件
            BH_MOBILE_SDK.file.filePreview(item.url, item.type, item.name)
            // if (item.isImg) {
            //     BH_MOBILE_SDK.UI.preViewImages([
            //       {
            //         url: item.url,
            //         desc: '暂无'
            //       }], 1)
            // } else {
            //     window.open(item.url)
            //     // BH_MOBILE_SDK.UI.openWebView(item.url)
            // }
        },
        getMediaInfo () {
            // debugger
            BH_MOBILE_SDK.UI.toggleLoading(true)
            let msgId = this.$route.params.msgId
            util.httpGet(api.getMsgView + '/' + msgId, {}, util.handler.DATAS).then((data) => {
                console.log(data)
                BH_MOBILE_SDK.UI.toggleLoading(false)
                this.msgContent = data.content
                this.attachments = data.attachments
            }).catch((err) => {
                BH_MOBILE_SDK.UI.toggleLoading(false)
                this.$Message.error('获取消息内容失败：' + err.errMsg)
            })
        },
    },
    mounted () {
        BH_MOBILE.init(() => {
            this.getMediaInfo()
        })
    }
}
</script>

<style lang="less" scoped>
.attachments {
    border-top: 1px solid #ddd;

    .attach-item {
        padding: 10px 5px;
        border-bottom: 1px solid #ddd;
        display: flex;
        align-items: center;
        .attach-icon {
            width: 40px;
            min-width: 40px;
        }
        .name {
            flex: 1;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            // width: e("calc(100% - 120px)");
        }
        .viewD {
            width: 60px;
            min-width: 60px;
        }
    }
}
</style>

<style lang="less">
    .msg-view {
        word-break: break-all;
        padding: 10px;
        table {
          border-top: 1px solid #ccc;
          border-left: 1px solid #ccc;
        }
        table td,
        table th {
          border-bottom: 1px solid #ccc;
          border-right: 1px solid #ccc;
          padding: 3px 5px;
        }
        table th {
          border-bottom: 2px solid #ccc;
          text-align: center;
        }

        /* blockquote 样式 */
        blockquote {
          display: block;
          border-left: 8px solid #d0e5f2;
          padding: 5px 10px;
          margin: 10px 0;
          line-height: 1.4;
          font-size: 100%;
          background-color: #f1f1f1;
        }

        /* code 样式 */
        code {
          display: inline-block;
          *display: inline;
          *zoom: 1;
          background-color: #f1f1f1;
          border-radius: 3px;
          padding: 3px 5px;
          margin: 0 3px;
        }
        pre code {
          display: block;
        }

        /* ul ol 样式 */
        ul, ol {
          margin: 10px 0 10px 20px;
        }
    }
    
</style>