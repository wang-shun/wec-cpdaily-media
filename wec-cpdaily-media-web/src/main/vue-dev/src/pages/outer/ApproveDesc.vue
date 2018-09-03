<template>
    <div class="approve-desc">
        <div class="top">
            <Breadcrumb>
                <BreadcrumbItem>认证说明</BreadcrumbItem>
            </Breadcrumb>
        </div>
        <div class="content">
            <div class="top-container">
                <div class="icon">
                    <i class="school-icon"></i>
                </div>
                <span class="title">校园号认证</span>
                <Button v-if="authStatus === 'UN_AUTH'" type="primary" class="btn" @click="approve">申请认证</Button>
            </div>
            <div v-if="authStatus === 'AUTHING'" class="status">
                <span class="title">认证说明</span>
                <Button type="ghost" class="btn">待审核</Button>
            </div>
            <div class="desc">
                <p style="font-size: 12px;color: #80848F;letter-spacing: 0;margin-top: 2px;">您好，欢迎登录校园号平台！赶快去通过实名认证解锁功能吧～</p>
                <p class="title">一、实名认证后能解锁的功能</p>
                <p class="content-desc">(1) 群发管理：支持群发消息，快速选择用户对象，编辑预览消息内容（可带附件），一键发送～</p>
                <p class="content-desc">(2) 用户管理：支持用户搜索和打标签分组功能，轻松管理粉丝用户</p>
                <p class="content-desc">(3) 自动回复管理： 支持自定义设置自动回复消息：哈喽，你来啦～</p>
                <p class="content-desc">(4) 账户管理： 支持账户资料中校园号头像和介绍的再编辑，让更多用户关注你！</p>
                <p class="title" style="margin-top: 30px;">二、提交实名认证材料</p>
                <p class="content-desc">(1) 学校职能部门：需要提供所在职能部门的部门章。</p>
                <p class="content-desc">(2) 学生组织：需要提供学校核实的合法性证明材料。   社团：需要提供经学校或社团联合会组织核实的社团合法性证明材料</p>
            </div>
        </div>
        <Modal
            v-model="modalApprove"
            :mask-closable="false"
            @on-ok="ApproveOk"
            @on-cancel="cancelApprove"
            :loading="loading"
            :width="470">
            <div>
                <upload-img :option="certificateOpt" @success="uploadCertificateSucc" @delete="deleteUploadCertificate"></upload-img>
                <p class="tip">提供经学校核实的合法性证明材料，要求清晰的证明照片，最大5M，不得超过3张</p>
            </div>
        </Modal>
    </div>
</template>

<script>
import uploadImg from 'com/UploadImg'
import util from 'conf/util'
import api from 'conf/api'
import Bus from 'services/Bus.js'

export default {
    data () {
        return {
            loading: true,
            certificate: [],
            modalApprove: false,
            certificateOpt: {
                visible: false,
                maxSize: 5000,
                multiple: false,
                limit: 3,
                defaultList: []
            },
            authStatus: 'UN_AUTH'
        }
    },
    components: {
        uploadImg
    },
    methods: {
        approve () {
            this.modalApprove = true
        },
        ApproveOk() {
            const authMaterials = this.certificate.join(',')
            if (this.certificate.length === 0) {
                this.$Message.error('请至少上传一张材料照片');
                this.loading = false;
                this.$nextTick(() => {
                    this.loading = true
                })
                return
            }

            const params = {
                authMaterials: authMaterials,
                wid: this.mediaId
            }
            util.httpPost(api.materialApprove, params, util.handler.CODE).then((res) => {
                this.modalApprove = false
                this.$Message.success('认证信息提交成功')
                this.authStatus = 'AUTHING'
                sessionStorage.setItem('authStatus', 'AUTHING')
            }).catch((err) => {
                this.$Message.error('认证信息提交失败：' + err.message)
            }).then(() => {
                this.loading = false;
                this.$nextTick(() => {
                    this.loading = true
                })
            })
        },
        cancelApprove () {
            this.modalApprove = false
        },
        uploadCertificateSucc (img) {
            this.certificate.push(img.url)
        },
        deleteUploadCertificate (img) {
            const certificate = this.certificate
            certificate.splice(certificate.indexOf(img.url), 1)
        }
    },
    mounted () {
        this.mediaId = this.$route.params.mediaId
        Bus.$emit('switchMedia', this.mediaId)
        this.authStatus = sessionStorage.getItem('authStatus')
    }
}
</script>

<style scoped lang="less" rel="stylesheet/less">
    .approve-desc {
        width: 1232px;
        margin: 0 20px 0 20px;
        box-sizing: border-box;
        min-height: 656px;
        .top{
            width: 100%;
            height: 60px;
            padding-top: 20px;
        }
        .content{
            width: 100%;
            padding: 26px 24px 26px 24px;
            box-sizing: border-box;
            min-height: 656px;
            background: #FFFFFF;
            box-shadow: 0 0 12px 0 rgba(132,134,219,0.16);
            border-radius: 2px;
            .top-container{
                width: 1184px;
                height: 100px;
                background: #F8F8F8;
                border: 1px solid #E3E8EE;
                border-radius: 4px;
                .icon{
                    display: inline-block;
                    width: 50px;
                    height: 50px;
                    float: left;
                    border-radius: 50px;
                    margin-left: 40px;
                    margin-top: 25px;
                    text-align: center;
                    background-image: linear-gradient(-180deg, #57A9FF 0%, #2D8CF0 100%);
                    box-shadow: 0 5px 10px 0 rgba(87,169,255,0.24);
                    .school-icon{
                        display: inline-block;
                        width: 23px;
                        height: 23px;
                        margin-top: 13.5px;
                        background: #fff;
                    }
                }
                .title{
                    display: inline-block;
                    float: left;
                    line-height: 100px;
                    vertical-align: middle;
                    margin-left: 16px;
                    font-size: 16px;
                    color: #4B5154;
                    letter-spacing: 0;
                    font-weight: 500;
                }
                .btn{
                    float: right;
                    width: 80px;
                    margin-top: 34px;
                    margin-right: 40px;
                }
            }
            .status{
                clear: both;
                width: 1184px;
                height: 32px;
                margin-top: 24px;
                .title{
                    display: inline-block;
                    float: left;
                    vertical-align: middle;
                    font-size: 16px;
                    color: #4B5154;
                    letter-spacing: 0;
                    font-weight: 500;
                }
                .btn{
                    float: right;
                    width: 80px;
                    margin-right: 40px;
                    border-color: #FF9900;
                    color: #FF9900;
                }
            }
            .desc{
                .title{
                    font-size: 12px;
                    color: #4B5154;
                    letter-spacing: 0;
                    line-height: 24px;
                    font-weight: 500;
                }
                .content-desc{
                    font-size: 12px;
                    color: #80848F;
                    letter-spacing: 0;
                    line-height: 24px;
                }
            }
        }
    }
    @media screen {
        /* 大于1300px的屏幕 */
        @media (min-width: 1280px){
            .approve-desc{
                margin: 0 auto;
            }
        }
    }
</style>
