<template>
    <div class="main">
        <Layout>
            <Sider ref="leftSide" hide-trigger collapsible :collapsed-width="78" v-model="isCollapsed" class="left-sider" :style="{width: isCollapsed?'78px':'200px'}">
                <Menu :active-name="activeName" theme="dark" width="auto" :class="menuitemClasses" style="height: 100%;" @on-select="selectMenu">
                    <div class="logo-content" style="color: #fff;">
                        <span class="logo" style="background-image: url('static/logo.png');"></span>
                        <div class="left" :style="{display: isCollapsed?'none':'inline-block'}">
                            <span style="width: 84px;font-size: 14px;line-height: 14px;display: inline-block">今日校园</span>
                            <span style="width: 84px;font-size: 12px;line-height: 12px;display: inline-block">校园号平台</span>
                        </div>
                    </div>
                    <MenuItem name="message">
                        <Icon type="email" style="margin-left: 3px;"></Icon>
                        <span>消息送达</span>
                    </MenuItem>
                    <MenuItem name="activitylist">
                        <Icon type="ios-flag" size="28" style="margin-left: 5px;"></Icon>
                        <span style="margin-left: 3px;">校园活动</span>
                    </MenuItem>
                    <MenuItem name="group">
                        <Icon type="android-people" size="24" style="margin-left: 3px;"></Icon>
                        <span>用户组管理</span>
                    </MenuItem>
                    <MenuItem name="logs">
                        <Icon type="android-list" size="24" style="margin-left: 3px;"></Icon>
                        <span>操作日志</span>
                    </MenuItem>
<!--                     <MenuItem name="kuaida">
                        <Icon type="ios-briefcase-outline" size="24" style="margin-left: 3px;"></Icon>
                        <span>快搭</span>
                    </MenuItem> -->
                    <MenuItem name="replysetting" style="position: absolute;width: 100%;bottom: 80px;">
                        <Icon type="android-settings" size="24" style="margin-left: 3px;"></Icon>
                        <span>回复管理</span>
                    </MenuItem>
                </Menu>
            </Sider>
            <Layout :style="{left: isCollapsed?'78px':'200px'}" class="content-main">
                <Header class="layout-header-bar" :style="{'margin-right': isCollapsed?'78px':'200px'}">
                    <Icon @click.native="collapsedSider" :class="rotateIcon" :style="{margin: '20px 20px 0'}" type="navicon-round" size="24"></Icon>
                    <div class="right-content" :style="{right: isCollapsed?'78px':'200px'}">
                        <div v-if="mediaInfo.icon" @click="viewMediaInfo" class="head-img" :style="{'background-image': cAvatar}"></div>
                        <span @click="viewMediaInfo" style="font-size: 14px;color: #657180;line-height: 64px;float: left;cursor: pointer;">{{mediaInfo.name}}</span>
                        <a @click.prevent="Switch"><Icon type="arrow-swap" style="float: left;color: rgb(51, 153, 255);font-size: 19px;margin-top: 22px;margin-left: 24px;"></Icon></a>
                        <a @click.prevent="Exit"><Icon type="power" style="float: left;color: rgb(51, 153, 255);font-size: 19px;margin-top: 22px;margin-left: 24px;margin-right: 24px;"></Icon></a>
                    </div>
                </Header>
                <Content >
                    <div style="margin-top:60px;transition: margin-right .3s;" :style="{'margin-right': isCollapsed?'78px':'200px'}">
                        <router-view/>
                    </div>
                </Content>
            </Layout>
        </Layout>
    </div>
</template>

<script>
import util from 'conf/util'
import api from 'conf/api'
export default {
    data () {
        return {
            isCollapsed: false,
            activeName: 'message',
            mediaInfo: {}
        }
    },
    computed: {
        rotateIcon () {
            return [
                'menu-icon',
                this.isCollapsed ? 'rotate-icon' : ''
            ]
        },
        menuitemClasses () {
            return [
                'menu-item',
                this.isCollapsed ? 'collapsed-menu' : ''
            ]
        },
        cAvatar () {
            return `url(${this.mediaInfo.icon})`
        }
    },
    methods: {
        getCurrentMediaInfo () {
            util.httpGet(api.mediaInfo + '/-1', {}, util.handler.DATAS).then((data) => {
                this.mediaInfo = data
                sessionStorage.setItem('canSeeOrgStructure', this.mediaInfo.canSeeOrgStructure)
            }).catch((err) => {
                // this.$Message.error('获取校园号信息失败：' + err.message)
            })
        },
        selectMenu (name) {
            this.$router.push('/home/' + name)
        },
        collapsedSider () {
            this.$refs.leftSide.toggleCollapse()
        },
        Switch () {
            this.$router.push('/outer/medialist')
        },
        Exit () {
            window.location.href = window.location.origin + api.logout
        },
        viewMediaInfo () {
            this.$router.replace('/outer/mediainfo/' + this.mediaInfo.wid)
        }
    },
    mounted () {
        this.getCurrentMediaInfo()
    }
}
</script>

<style scoped lang="less" rel="stylesheet/less">
    .main{
        position: absolute;
        width: 100%;
        min-width: 1330px;
        height: 100%;
        overflow: hidden;
        .left-sider{
            position: fixed;
            height: 100%;
            top: 0;
            left: 0;
            z-index: 21;
            transition: width .3s;
            .logo-content{
                position: relative;
                height: 64px;
                .logo{
                    display: inline-block;
                    width: 38px;
                    height: 38px;
                    margin-top: 13px;
                    margin-left: 20px;
                    background: #0CDEDC;
                    background-size: cover;
                    border-radius: 3px;
                }
                .left{
                    width: 84px;
                    margin-left: 10px;
                    color: #fff;
                }
            }
            .menu-item span{
                display: inline-block;
                overflow: hidden;
                width: 84px;
                text-overflow: ellipsis;
                white-space: nowrap;
                vertical-align: bottom;
            }
            .menu-item i{
                vertical-align: middle;
                font-size: 26px;
            }
            .collapsed-menu span{
                display: none;
            }
        }
        .content-main{
            position: absolute;
            width: 100%;
            height: 100%;
            transition: left .3s;
            .layout-header-bar{
                padding: 0;
                position: fixed;
                width: 100%;
                z-index: 999;
                background: #FFFFFF;
                box-shadow: 0 4px 8px 0 rgba(70,76,91,0.16);
                border-radius: 1px;
                transition: margin-right .3s;
                .right-content{
                    position: absolute;
                    height: 64px;
                    top: 0;
                    transition: right .3s;
                    .head-img{
                        cursor: pointer;
                        display: inline-block;
                        width: 32px;
                        height: 32px;
                        float: left;
                        margin-right: 6px;
                        margin-top: 16px;
                        border-radius: 32px;
                        background-position: center;
                        background-size: cover;
                    }
                }
            }
        }
    }
</style>
