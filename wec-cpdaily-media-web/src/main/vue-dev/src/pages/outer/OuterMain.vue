<template>
    <div class="outer-main">
        <Layout>
            <Header class="layout-header-bar">
                <span class="logo">
                    <img style="height: 38px;width: 38px;" src="static/logo.png">
                </span>
                <div class="right-content">
                    <!-- 用户名称 -->
                    <span v-if="!isInMedia" style="font-size: 14px;color: #657180;line-height: 64px;float: left;cursor: pointer;">{{userName}}</span>

                    <div v-if="isInMedia && mediaInfo.icon" class="head-img" @click="viewMediaInfo" :style="{'background-image': cAvatar}"></div>
                    <span v-if="isInMedia" @click="viewMediaInfo" style="font-size: 14px;color: #657180;line-height: 64px;float: left;cursor: pointer;">{{mediaInfo.name}}</span>
                    <a v-if="isInMedia" @click.prevent="Switch"><Icon type="arrow-swap" style="float: left;color: rgb(51, 153, 255);font-size: 19px;margin-top: 22px;margin-left: 24px;"></Icon></a>

                    <a @click.prevent="Exit"><Icon type="power" style="float: left;color: rgb(51, 153, 255);font-size: 19px;margin-top: 22px;margin-left: 24px;margin-right: 24px;"></Icon></a>
                </div>
            </Header>
            <Content class="content">
                <router-view/>
            </Content>
        </Layout>
    </div>
</template>

<script>
import util from 'conf/util'
import api from 'conf/api'
import Bus from 'services/Bus.js'
export default {
    data () {
        return {
            isInMedia: false,
            userName: '',
            mediaInfo: {}
        }
    },
    methods: {
        getLoginUser () {
            util.httpGet(api.accountInfo, {}, util.handler.DATAS).then((data) => {
                this.userName = data.name
            }).catch((err) => {
                this.$Message.error('获取用户信息失败：' + err.errMsg)
            })
        },
        getMediaInfo (wid) {
            util.httpGet(api.mediaInfo + '/' + wid, {}, util.handler.DATAS).then((data) => {
                this.mediaInfo = data
            }).catch((err) => {
                this.$Message.error('获取用户信息失败：' + err.errMsg)
            })
        },
        Switch () {
            this.isInMedia = false
            this.$router.push('/outer/medialist')
        },
        Exit () {
            // let href = window.location.href
            // let index = href.indexOf('index.html') > 0 ? href.indexOf('index.html') : href.indexOf('#/')
            window.location.href = window.location.origin + api.logout
        },
        viewMediaInfo () {
            if (this.isInMedia) {
                this.$router.replace('/outer/mediainfo/' + this.mediaInfo.wid)
            }
        },
        init () {
            // 解决登录跳转问题
            if (window != window.top) {
                window.top.location.href = window.location.href
            }
        }
    },
    computed: {
        cAvatar () {
            return `url(${this.mediaInfo.icon})`
        }
    },
    created () {
        Bus.$on('switchMedia', (wid) => {
            if (wid === -1) {
                this.isInMedia = false
            } else {
                this.isInMedia = true
                this.getMediaInfo(wid)
            }
        })
    },
    mounted () {
        this.init()
        this.getLoginUser()
    }
}
</script>


<style lang="less" rel="stylesheet/less">

    .outer-main{
        .ivu-layout-header {
            padding: 0 0px;
        }
    }

</style>

<style scoped lang="less" rel="stylesheet/less">
    .outer-main{
        .layout-header-bar{
            width: 100%;
            position: fixed;
            background: #FFFFFF;
            box-shadow: 0 4px 8px 0 rgba(70,76,91,0.16);
            border-radius: 1px;
            z-index: 20;
            .logo{
                display: inline-block;
                width: 38px;
                height: 38px;
                margin-top: 13px;
                margin-left: 20px;
                background: #0CDEDC;
                border-radius: 3px;
            }
            .right-content{
                position: absolute;
                height: 64px;
                top: 0;
                right: 0;
                .head-img{
                    cursor: pointer;
                    display: inline-block;
                    width: 32px;
                    height: 32px;
                    float: left;
                    margin-right: 6px;
                    margin-top: 16px;
                    border-radius: 32px;
                    background-size: cover;
                    background-position: center;
                }
            }
        }
        .content{
            margin-top: 70px;
            background: #F7F8FC;
        }
    }
</style>
