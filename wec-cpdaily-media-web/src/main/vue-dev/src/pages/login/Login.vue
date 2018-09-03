<template>
    <div class="wrap" style="background-image: url('static/bg.jpg')">
        <div class="logo-box">
            <img class="logo" src="static/logo.png" alt="">
            <span class="title">校园号平台</span>
        </div>
        <div class="slogan">
            <img :src="slogan" alt="">
        </div>
        <div class="qrcode-box">
            <div v-if="tipMediaDeny" class="media-deny" style="text-align:center;font-size:16px;font-weight:bold;padding-top:100px;">
                <div>校园号身份</div>
                <div>不能登录校园号平台</div>
                <Button style="margin-top:40px" type="primary" @click="freshWin">重新扫码</Button>
            </div>
            <div v-else>
                <span class="top">扫码登录</span>
                <div class="qrcode" id="qrcode"></div>
            </div>
        </div>
    </div>
</template>
<script>
import util from 'conf/util'
import api from 'conf/api'
let timer = null, dealy = 2000

export default {
    data: function () {
        return {
            tipMediaDeny: false
        }
    },
    computed: {
        slogan() {
            return 'static/slogan.png'
        },
        logo() {
            return 'static/logo.png'
        },
        ...Vuex.mapState({
            index: state => state.index
        })
    },
    mounted() {
        let type = this.$route.params.type
        if (type == 1) {
            this.tipMediaDeny = true
        } else {
            this.initQRCode()
        }
    },
    methods: {
        initQRCode () {
            let path = this.$route.path
            let pms = {
                id: 'qrcode',
                clientId: '10000000000000007',
                self_redirect: false,
                redirectUri: window.location.origin + api.login,
                topRedirect: true
            }
            cpdailyLogin(pms)
        },
        loginSucc() {
            this.$router.push('/outer')
        },
        freshWin () {
            window.location.href = window.location.origin + api.logout
            // this.$router.push('/login')
            // window.location.reload()
        }
    }
}
</script>
<style scoped lang="less" rel="stylesheet/less" scoped>
    .wrap {
        width: 100vw;
        height: 100vh;
        min-width:1100px;
        max-width:1920px;
        overflow: auto;
        background-repeat:  no-repeat;
        background-size: 100% 100%;
        position: relative;
        .logo-box {
            position: absolute;
            left: 2%;
            top: 2%;
            .logo {
                vertical-align: middle;
            }
            .title {
                font-size: 36px;
                margin-left: 15px;
                display: inline-block;
                vertical-align: middle;
            }
        }
        .slogan {
            width: 25vw;
            min-width:220px;
            max-width:441px;
            position: absolute;
            top: 3.5vw;
            left: 50%;
            transform: translateX(-50%);
            img{
                width:100%;
            }
        }

        .qrcode-box {
            position: absolute;
            left: 50%;
            transform: translateX(-50%);
            top: 13vw;
            padding: 2vw 6.5vw;
            background: #ffffff;
            text-align: center;
            overflow: hidden;
            box-shadow: 0px 0 20px #e4e4e4;

            .top {
                font-size: 20px;
                margin-bottom: 10px;
                display: inline-block;
            }
            .qrcode {
                width: 310px;
                min-width:310px;
                border: solid 1px #000000;
                img {
                    width: 100%;
                }
            }
            .bottom {
                font-size: 18px;
                background: #F9FFFF;
                border: 1px solid #D8F4F4;
                border-radius: 10px;
                padding: 8px 0;
                width:15vw;
                min-width:200px;
                max-width:272px;
                margin-top: 20px;
            }
            .media-deny {
                width: 200px;
                height: 337px;
            }
        }
    }
</style>
