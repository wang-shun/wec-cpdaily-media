<template>
    <section class="result-con">
        <div class="result">
            <!-- <div class="result-icon">
                <div class="res-success" v-if="sendSuccess">
                    <Icon type="checkmark-circled"></Icon>
                    <div class="tip">发布成功</div>
                </div>
                <div class="res-fail" v-if="!sendSuccess">
                    <Icon type="close-circled"></Icon>
                    <div class="tip">发布失败</div>
                    <div class="desc">对不起，您的消息发布失败，已将其存放至草稿箱，您可以继续编辑或重新发送。</div>
                </div>
            </div> -->
            <div class="message-content">
                <div>
                    <div class="bh-mv-16 msg-title-con"><span class="msg-title" :title="messageInfo.title">{{messageInfo.title}}</span><a class="msg-btn-preview" @click.prevent="previewMsg">预览正文</a></div>
                </div>
                <Row class="bh-mv-16">
                    <Col span="8">
                        <span>发起人：</span><span>{{messageInfo.cUserName}}</span>
                    </Col>
                    <Col span="8">
                        <span>阅读进度：</span>
                        <span>{{cState}}</span>
                    </Col>
                    <Col span="8">
                        <span>发送时间：</span><span>{{cSendTime}}</span>
                    </Col>
                </Row>
            </div>
            <read-user-table v-if="messageInfo.msgId" :msg="messageInfo" :media-id="messageInfo.mediaId"></read-user-table>
        </div>
        <Modal
            v-model="modalQRCode"
            title=""
            width="450"
            @on-ok="modalOk">
            <div class="qrcode-container" style="text-align: center;padding: 50px;background-color:white;">
                <div class="qrcode-con" style="width: 300px;height: 300px;display: inline-block;"></div>
            </div>
            <div style="text-align:center;font-size:16px;color:#000;">请使用今日校园扫码预览</div>
        </Modal>
    </section>
</template>
<script>
import api from 'conf/api'
import attachments from 'com/attachments'
import messagePreview from './edit-message/messagePreview'
import ReadUserTable from './ReadUserTable'
import util from 'conf/util.js'

export default {
    data () {
        return {
            msgId: '',
            sendSuccess: true,
            modalQRCode: false,
        }
    },
    components: {
        messagePreview,
        attachments,
        ReadUserTable
    },
    computed: {
        ...Vuex.mapState({
            index: state => state.index
        }),
        cInfo () {
            return {
                pic: this.messageInfo.img,
                title: this.messageInfo.title
            }
        },
        cState () {
            if (this.messageInfo.status == 'SEND_IN') {
                return '发送中'
            } else {
                var r = parseInt(this.messageInfo.readCount * 100 / this.messageInfo.receiveCount)
                return this.messageInfo.readCount + '/' + this.messageInfo.receiveCount + ' ' + r + '%'
            }
        },
        cAttachments () {
            var imgs = this.messageInfo.attachments ? this.messageInfo.attachments.split(',') : []
            return imgs.map((fileUrl) => { return {fileName: fileUrl.substr(fileUrl.lastIndexOf('/') + 1), fileUrl: fileUrl} })
        },
        cSendTime() {
            if(this.messageInfo.cTime) {
                return this.messageInfo.cTime.substr(0, this.messageInfo.cTime.length - 3)
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
        previewMsg (item) {
            let href = window.location.href
            let index = href.indexOf('#/')
            let url = href.substr(0, index) + '#/msgview/' + this.messageInfo.msgId
            console.log('url:' + url)
            this.modalQRCode = true
            this.$nextTick(() => {
                $('.qrcode-con').empty()
                $('.qrcode-con').qrcode({
                    render: "canvas",
                    text: url,
                    width: 300, //二维码的宽度
                    height: 300 //二维码的高度
                })
            })
        },
        modalOk () {
            this.modalQRCode = false
        },
        // getResultInfo () {
        //     this.msgId = this.messageInfo.msgId
        //     var info = JSON.parse(JSON.stringify(this.messageInfo))
        //     var imgs = info.attachments ? info.attachments.split(',') : []
        //     info.attachments = imgs.map((fileUrl) => { return {fileName: fileUrl.substr(fileUrl.lastIndexOf('/') + 1), fileUrl: fileUrl} })
        //     this.message = this.info
        //     if (this.sendSuccess) {
        //         util.httpGet(api.msgView + '/' + this.msgId, {}, util.handler.DATAS).then((data) => {
        //             this.readCount = data.readCount
        //             this.receiveCount = data.receiveCount
        //             this.sendCount = data.sendCount
        //             this.sendOverCount = data.sendOverCount
        //             this.cTime = data.cTime
        //         }).catch((err) => {
        //             this.$Message.error('获取状态失败：' + err.errMsg)
        //         })
        //     }
        // },
        toTable () {
            this.$router.push('/home/message')
        }
    },
    mounted () {
    }
}
</script>

<style scoped lang="less">
    .result-con {
        flex: 1;
        .result {
            padding: 0 20px;
            .title {
                font-weight: bold;
                font-size: 14px;
            }
            .message-content {
                .msg-title-con {
                    display: flex;
                    align-items: center;
                    .msg-title {
                        display: inline-block;
                        font-size: 18px;
                        margin-right: 20px;
                        max-width: 500px;
                        overflow: hidden;
                        text-overflow: ellipsis;
                        white-space: nowrap;
                    }
                    .msg-btn-preview {
                        display: inline-block;
                    }
                }
                border-bottom: 1px solid #E3E8EE;
            }
            .result-icon {
                padding: 50px;
                text-align: center;
                i {
                    font-size: 10em;
                }
                .tip {
                    font-size: 14px;
                    font-weight: bold;
                }
                .res-success {
                    i {
                        color: #19BE6B;
                    }
                }
                .res-fail {
                    i {
                        color: #ED3F14;
                    }
                    .desc {
                        color: #C1CBD7;
                        font-size: 0.5rem;
                    }
                }
            }
        }
    }
</style>
