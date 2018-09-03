<template xmlns:v-bind="http://www.w3.org/1999/xhtml">
    <div class="mobile-media-list">
        <div class="top">
            <p>申请通过的校园号在设置-切换身份页显示哦~</p>
        </div>
        <div class="content">
            <ul v-if="!isEmpty">
                <li class="list-item" v-for="(item,index) in mediaList" v-bind:key="index" @click="goToDetail(item.authStatus, item.wid)">
                    <Avatar style="width: 48px;height: 48px;border-radius: 48px;" icon="person" size="large" :src="item.icon"/>
                    <div class="middle">
                        <p class="name">{{item.name}}</p>
                        <p class="type">{{item.type}}</p>
                        <p class="type">{{item.tenantName}}</p>
                    </div>
                    <span class="status" :class="getStatusClass(item.status)" v-if="item.status">{{item.status}}</span>
                </li>
            </ul>
            <div class="empty-container" style="text-align: center;" v-if="isEmpty">
                <img src="static/empty_media.png" width="148" height="120" style="display: block;margin: 100px auto 10px auto;">
                <span style="font-size: 12px;color: #999999;letter-spacing: 3px;text-align: center;line-height: 14px;">申请列表为空</span>
            </div>
        </div>
        <div class="bottom">
            <span class="apply-btn" @click="goToApply">申请校园号</span>
        </div>

        <!-- 申请校验失败原因展示弹出框 -->
        <Modal v-model="showFail" :width="270" :closable="false" :styles="{top: '200px'}">
            <p slot="header" style="font-size: 17px;color: #030303;text-align:center">
                <span>对不起</span>
            </p>
            <div style="text-align:center">
                <p v-html="failReason" style="font-size: 13px;color: #030303;"></p>
            </div>
            <div class="custom-footer" slot="footer">
                <p style="height: 50px;line-height: 50px;cursor: pointer;" @click="showFail = false">我知道了</p>
            </div>
        </Modal>
    </div>
</template>

<script type="text/ecmascript-6">
    import util from 'conf/util'
    import api from 'conf/api'
    export default {
        data () {
            return {
                isEmpty: true,
                showFail: false,
                failReason: "",
                mediaList: []
            }
        },
        methods: {
            getStatusClass (status) {
                if (status === '待审核') {
                    return 'status-wait-audit'
                }
                if (status === '待认证') {
                    return 'status-wait-auth'
                }
                if (status === '已拒绝') {
                    return 'status-reject'
                }
            },
            goToApply () {
                if (window.mamp) {
                    mamp.UI.toggleLoading(true);
                }
                // 校验是否可以申请
                util.httpGet(api.APPLY_CHECK, {}).then((data) => {
                    if (window.mamp) {
                        mamp.UI.toggleLoading(false);
                    }
                    if (data.code === "0" ) {
                        this.$router.push({name:'mobileselectidentity'})
                    } else {
                        this.failReason = data.message
                        this.showFail = true
                    }
                }).catch((res) => {
                    if (window.mamp) {
                        mamp.UI.toggleLoading(false);
                    }
                })
            },
            goToDetail (status,wid) {
                this.$router.push({name:'mobileapply',query:{status: status, wid: wid}})
            },
            getMediaListUnAuth () {
                if (window.mamp) {
                    mamp.UI.toggleLoading(true);
                }
                let vm = this
                util.httpGet(api.MEDIA_LIST_UNAUTHED, {}, util.handler.DATAS).then((data) => {
                    if (window.mamp) {
                        mamp.UI.toggleLoading(false);
                    }
                    if (data && data.length > 0) {
                        vm.isEmpty = false
                        data.forEach((item) => {
                            item.type = vm.getTypeName(item.mediaType)
                            item.status = vm.getStatusName(item.authStatus)
                        })
                        vm.mediaList = data
                    } else {
                        vm.isEmpty = true
                    }
                }).catch((res) => {
                    if (window.mamp) {
                        mamp.UI.toggleLoading(false);
                    }
                    if (res.code == '98') {
                        this.$router.replace('mediadeny')
                    }
                })
            },
            getStatusName (status) {
                if (status === 'AUTHED') {
                    return '已审核'
                }
                if (status === 'AUTH_FAIL') {
                    return '已拒绝'
                }
                if (status === 'UN_AUTH') {
                    return '待认证'
                }
                if (status === 'AUTHING') {
                    return '待审核'
                }
                return '数据异常'
            },
            getTypeName (type) {
                if (type === 'OFFICIAL') {
                    return '职能部门'
                } else {
                    return '学生组织'
                }
            },
        },
        mounted () {
            this.getMediaListUnAuth()
            document.addEventListener("deviceready", function(){
                // 拦截返回按钮
                if (window.mamp) {
                    var config = {
                        left: {
                            left1: {
                                callFunction: function(){
                                    mamp.UI.toggleLoading(false);
                                    mamp.UI.closeWebView();
                                }
                            }
                        },
                        title: '申请校园号'
                    };
                    mamp.UI.setNavHeader(config);
                }
            }, false);
        }
    }
</script>

<style lang="less" rel="stylesheet/less">
    @media (max-width: 768px) {
        .ivu-modal {
            width: auto!important;
            margin: 35px;
        }
    }

    .ivu-modal{
        .ivu-modal-header{
            border-bottom: none;
        }
        .ivu-modal-body{
            padding: 0px 16px 16px 16px;
        }
        .ivu-modal-footer{
            padding: 0px;
        }
        .custom-footer{
            text-align: center;
            font-size: 17px;
            color: #0076FF;
        }
    }

    .mobile-media-list{
        .ivu-avatar{
            img{
                object-fit: cover;
            }
        }
    }
</style>

<style scoped lang="less" rel="stylesheet/less">
    .mobile-media-list{
        background: #fff;
        .top{
            width: 100%;
            height: 30px;
            text-align: center;
            font-size: 12px;
            vertical-align: middle;
            background: rgba(255,172,54,0.1);
            color: #ffac36;
            p{
                line-height: 30px;
            }
        }
        .content{
            position: relative;
            margin-bottom: 60px;
            .list-item{
                display: flex;
                position: relative;
                width: 100%;
                height: 76px;
                padding: 14px 20px 14px 20px;
                background: #ffffff;
                cursor: pointer;
                .middle{
                    display: inline-block;
                    flex: 1;
                    margin-left: 10px;
                    .name{
                        font-size: 16px;
                        color: #111111;
                        letter-spacing: 1px;
                        text-align: left;
                        line-height: 16px;
                    }
                    .type{
                        margin-top: 4px;
                        font-size: 13px;
                        color: #CCCCCC;
                        letter-spacing: 1.08px;
                        text-align: left;
                        line-height: 14px;
                    }
                }
                .status{
                    display: inline-block;
                    width: 45px;
                    height: 18px;
                    margin-top: 17px;
                    border-radius: 4px;
                    font-size: 12px;
                    letter-spacing: 0;
                    line-height: 18px;
                    text-align: center;
                    vertical-align: middle;
                }
                .status-wait-audit{
                    background: rgba(255,172,54,0.16);
                    color: #FFAC36;
                }
                .status-wait-auth{
                    background: rgba(82,199,202,0.16);
                    color: rgb(82,199,202);
                }
                .status-reject{
                    background: rgba(255,77,77,0.16);
                    color: rgb(255,77,77);
                }
                &:after{
                    content: '';
                    display: block;
                    position: absolute;
                    height: 1px;
                    background: #EFEFEF;
                    top: 76px;
                    right: 20px;
                    left: 20px;
                    z-index: 20;
                }
            }
        }
        .bottom{
            z-index: 21;
            width: 100%;
            height: 60px;
            position: fixed;
            bottom: 0;
            background: rgba(255,255,255,0.90);
            .apply-btn{
                display: block;
                height: 40px;
                margin: 10px 20px 10px 20px;
                background: #52C7CA;
                border-radius: 4px;
                text-align: center;
                line-height: 40px;
                font-size: 16px;
                font-weight: 500;
                color: #FFFFFF;
            }
        }
    }
</style>