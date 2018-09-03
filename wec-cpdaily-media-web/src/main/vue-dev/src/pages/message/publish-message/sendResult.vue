<template>
    <section class="result-con">
        <div class="result">
            <div class="result-icon">
                <div class="res-success" v-if="sendSuccess">
                    <Icon type="checkmark-circled"></Icon>
                    <div class="tip">发布成功</div>
                </div>
                <div class="res-fail" v-if="!sendSuccess">
                    <Icon type="close-circled"></Icon>
                    <div class="tip">发布失败</div>
                    <div class="desc">对不起，您的消息发布失败，已将其存放至草稿箱，您可以继续编辑或重新发送。</div>
                </div>
            </div>
            <div class="title bh-mv-16">
                发布内容
            </div>
            <div class="message-content">
                <div class="msg-item">
                    <div class="msg-label">标题：</div>
                    <div class="msg-content">{{messageInfo.title}}</div>
                </div>
                <div class="msg-item">
                    <div class="msg-label">内容：</div>
                    <div class="msg-content html-content" v-html="messageInfo.content"></div>
                </div>
                <div class="msg-item">
                    <div class="msg-label">附件：</div>
                    <div class="msg-content">
                        <attachments v-model="cAttachments" :readonly="true"></attachments>
                    </div>
                </div>
                <div class="msg-item">
                    <div class="msg-label">发布时间：</div>
                    <div class="msg-content">{{messageInfo.cTime}}</div>
                </div>
                <div class="msg-item">
                    <div class="msg-label">送达状态：</div>
                    <div class="msg-content">{{cState}} （全部：{{messageInfo.sendOverCount}}  已读：{{messageInfo.readCount}}）</div>
                </div>
                <div class="bh-mv-16">
                    <Button type="primary" @click="toTable">返回列表</Button>
                    <Button v-if="sendSuccess" @click="copyMessage" type="ghost" style="margin-left: 8px">复制</Button>
                    <Button v-if="sendSuccess" @click="newMessage" type="ghost" style="margin-left: 8px">新建消息</Button>
                </div>
            </div>
        </div>
        <div class="message-view">
            <message-preview :info="cInfo"></message-preview>
        </div>
    </section>
</template>
<script>
import api from 'conf/api'
import attachments from 'com/attachments'
import messagePreview from './edit-message/messagePreview'
import util from 'conf/util.js'

export default {
    data () {
        return {
            msgId: '',
            sendSuccess: true
        }
    },
    components: {
        messagePreview,
        attachments
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
            if (this.messageInfo.sendOverCount === this.messageInfo.sendCount) {
                return '送达完成'
            } else {
                return '送达中'
            }
        },
        cAttachments () {
            var imgs = this.messageInfo.attachments ? this.messageInfo.attachments.split(',') : []
            return imgs.map((fileUrl) => { return {fileName: fileUrl.substr(fileUrl.lastIndexOf('/') + 1), fileUrl: fileUrl} })
        }
    },
    props: {
        messageInfo: {
            type: Object,
            default: () => ({})
        }
    },
    methods: {
        copyMessage () {
            this.$emit('copy-message')
        },
        newMessage () {
            this.$emit('new-message')
        },
        getResultInfo () {
            this.msgId = this.messageInfo.msgId
            var info = JSON.parse(JSON.stringify(this.messageInfo))
            var imgs = info.attachments ? info.attachments.split(',') : []
            info.attachments = imgs.map((fileUrl) => { return {fileName: fileUrl.substr(fileUrl.lastIndexOf('/') + 1), fileUrl: fileUrl} })
            this.message = this.info
            if (this.sendSuccess) {
                util.httpGet(api.msgView + '/' + this.msgId, {}, util.handler.DATAS).then((data) => {
                    this.readCount = data.readCount
                    this.receiveCount = data.receiveCount
                    this.sendCount = data.sendCount
                    this.sendOverCount = data.sendOverCount
                    this.cTime = data.cTime
                }).catch((err) => {
                    this.$Message.error('获取状态失败：' + err.errMsg)
                })
            }
        },
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
        display: flex;
        .result {
            width: 60%;
            padding: 0 20px;
            border-right: 1px solid #E3E8EE;
            .title {
                font-weight: bold;
                font-size: 14px;
            }
            .message-content {
                .msg-item {
                    display: flex;
                    min-height: 2rem;
                    .msg-label {
                        width: 80px;
                        min-width: 80px;
                        font-weight: bold;
                    }
                    .msg-content {
                        flex: 1;
                        max-width: e("calc(100% - 100px)");
                        // color: #C1CBD7;
                    }
                    .html-content {
                        overflow: hidden;
                        white-space: nowrap;
                        text-overflow: ellipsis;
                        padding-bottom: 0.5em;
                    }
                }
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
        .message-view {
            flex: 1;
            padding: 0 20px;
        }
    }
</style>
