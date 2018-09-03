<template xmlns:v-bind="http://www.w3.org/1999/xhtml">
    <div class="media-list">
        <div class="top-fixed">
            <div class="top">
                <span class="title" :class="{'title-selected': isSchoolList}" @click="changeTab(true)">校内榜</span>
                <i class="divider-line"></i>
                <span class="title" :class="{'title-selected': !isSchoolList}" @click="changeTab(false)">全国榜</span>
            </div>
            <i style="display: block;width: 100%;height: 1px;background: #efefef;"></i>
            <div class="desc-header">
                <div class="left">
                    <p class="title">{{periodType === 'MONTH' ? '上月' : '上周'}}影响力排行</p>
                    <p class="desc">新增来访数*0.7+新增粉丝数*0.1+0.2*总粉丝数</p>
                </div>
                <Poptip v-model="isTipShow" placement="bottom-end" :width="150">
                    <div class="right">
                        <span class="change-btn">
                            <Icon type="arrow-swap" style="color:#CCCCCC;" :size="13"></Icon>
                        </span>
                    </div>
                    <div slot="title"></div>
                    <div slot="content">
                        <p @click="setType('WEEK')" style="height:40px;font-size: 14px;color: #1E2329;text-align: center;line-height: 40px;text-align:center;">上周</p>
                        <i style="display:block;height:1px;background:#EFEFEF;"></i>
                        <p @click="setType('MONTH')" style="height:40px;font-size: 14px;color: #1E2329;text-align: center;line-height: 40px;text-align:center;">上月</p>
                    </div>
                </Poptip>
            </div>
            <i style="display: block;width: 100%;height: 1px;background: #efefef;"></i>
        </div>
        <div class="list-container" style="margin-top:127px;">
            <mt-loadmore :bottom-method="loadBottom" :bottom-all-loaded="allLoaded" ref="loadmore" topPullText="" bottomPullText="" @bottom-status-change="bottomStatusChange">
                <div class="list-item" v-for="(item, index) in medias" v-bind:key="index">
                    <span class="index" :class="{'top-index':index<3}">{{index+1}}</span>
                    <Avatar style="width: 48px;height: 48px;border-radius: 48px;float: left;margin-top: 14px;margin-right: 10px;" icon="person" size="large" :src="item.icon"/>
                    <div class="content">
                        <p class="name">{{item.name}} <span style="background: rgba(82,199,202,0.1);border-radius: 2px;font-size: 12px;color: #52C7CA;letter-spacing: 0;text-align: center;line-height: 16px;padding:2px 3px 2px 3px;">{{item.score}}</span> </p>
                        <p class="tenant-name" v-if="!isSchoolList">{{item.tenantName}}</p>
                        <p class="desc" :class="{'desc-type-name':isSchoolList}">{{item.description}}</p>
                    </div>
                </div>
                <div slot="bottom" style="width:100%;height:40px;text-align: center;">
                    <span v-show="bottomStatus !== 'loading' && !allLoaded" style="color:#333333;line-height:40px;font-size:13px;">上拉加载更多</span>
                    <span v-show="bottomStatus !== 'loading' && allLoaded" style="color:#333333;line-height:40px;font-size:13px;">全部加载完毕</span>
                    <span v-show="bottomStatus === 'loading'" style="color:#333333;line-height:40px;font-size:13px;">加载中...</span>
                </div>
            </mt-loadmore>
        </div>
    </div>
</template>

<script type="text/ecmascript-6">
    import { Loadmore } from 'mint-ui';
    import util from 'conf/util'
    import api from 'conf/api'
    Vue.component(Loadmore.name, Loadmore);
    export default {
        data () {
            return {
                bottomStatus: '',
                allLoaded: false,
                isTipShow: false,
                periodType: 'WEEK',// WEEK, MONTH
                tenantId: '',
                mediaType: '',
                periodIndex: '-1',
                pageNum: 1,
                pageSize: 10,
                isSchoolList: true,
                isLoading: false,
                medias: []
            }
        },
        methods: {
            bottomStatusChange (status) {
                this.bottomStatus = status
            },
            loadBottom () {
                // 加载下一页
                this.getRankingList();
            },
            setType (type) {
                this.periodType = type
                this.isTipShow = false
                this.allLoaded = false
                this.medias = []
                this.pageNum = 1
                this.getRankingList(true)
            },
            changeTab (isSchoolList) {
                this.isSchoolList = isSchoolList
                this.allLoaded = false
                this.medias = []
                this.pageNum = 1
                this.getRankingList(true)
            },
            getRankingList (isFirst) {
                if (this.isLoading) {
                    return
                }
                let params = {
                    periodType: this.periodType,
                    tenantId: this.isSchoolList ? this.tenantId : '',
                    mediaType: this.mediaType,
                    periodIndex: this.periodIndex,
                    pageNum: this.pageNum,
                    pageSize: this.pageSize 
                }
                this.isLoading = true
                util.httpPost(api.RANKING_LIST, params, util.handler.DATAS).then(data => {
                    console.log(data)
                    this.isLoading = false
                    data.medias.forEach((item) => {
                        this.medias.push(item)
                    })
                    if(!isFirst){
                        this.$refs.loadmore.onBottomLoaded();
                    }
                    if (data.medias && data.medias.length > 0) {
                        this.pageNum += 1
                    } else {
                        this.allLoaded = true;// 若数据已全部获取完毕
                    }
                })
            }
        },
        mounted () {
            let vm = this
            document.addEventListener("deviceready", function(){
                // 不是编辑，就从今日校园里面获取tenantId
                if (window.mamp) {
                    mamp.cpdaily.getTenantInfo(function(tenantObject){
                        if (tenantObject) {
                            vm.tenantId = tenantObject.id
                            vm.getRankingList(true);
                        }
                    });
                } else {
                    vm.getRankingList(true);
                }
            }, false);
        }
    }
</script>

<style lang="less" rel="stylesheet/less">
    .media-list{
        .ivu-avatar{
            img{
                object-fit: cover;
            }
        }
        .ivu-poptip{
            .ivu-poptip-popper{
                right: 20px !important;
                left: auto !important;
                width: 100px !important;
                min-width: 100px;
                .ivu-poptip-title{
                    display: none;
                }
                .ivu-poptip-body{
                    padding: 0px;
                }
            }
        }
    }
</style>

<style scoped lang="less" rel="stylesheet/less">
    .media-list{
        .top-fixed{
            width: 100%;
            position: fixed;
            top: 0;
            background: #ffffff;
            z-index: 2;
            .top{
                display: flex;
                height: 42px;
                .title{
                    flex:1;
                    display: inline-block;
                    height: 42px;
                    text-align: center;
                    line-height: 42px;
                    font-size: 14px;
                    letter-spacing: 1px;
                    color: #999999;
                    cursor: pointer;
                }
                .title-selected{
                    color: #52C7CA;
                }
                .divider-line{
                    display: inline-block;
                    width: 1px;
                    background: #CCCCCC;
                    height: 15px;
                    margin-top: 13.5px;
                }
            }
            .desc-header{
                display: flex;
                height: 85px;
                .left{
                    flex: 1;
                    .title{
                        font-size: 20px;
                        color: #333333;
                        letter-spacing: 1.25px;
                        text-align: left;
                        font-weight: 600;
                        margin-top: 15px;
                        margin-left: 20px;
                    }
                    .desc{
                        height: 20px;
                        display: inline-block;
                        background: rgba(82,199,202,0.1);
                        border-radius: 2px;
                        margin: 5px 0px 0px 20px;
                        padding: 0 4px 0 4px;
                        font-size: 12px;
                        color: #52C7CA;
                        letter-spacing: 0;
                        text-align: left;
                        line-height: 20px;
                    }
                }
                .right{
                    width: 76px;
                    text-align: center;
                    .change-btn{
                        display: inline-block;
                        width: 36px;
                        height: 18px;
                        border: 1px solid #CCCCCC;
                        border-radius: 10px;
                        margin-top: 34px;
                    }
                }
            }
        }
        .list-container{
            .list-item{
                position: relative;
                height: 76px;
                width: 100%;
                .index{
                    width: 30px;
                    float: left;
                    display: inline-block;
                    font-size: 20px;
                    color: #999999;
                    letter-spacing: 1px;
                    text-align: center;
                    line-height: 76px;
                    margin-left: 20px;
                    margin-right: 14px;
                }
                .top-index{
                    color: #333333;
                    font-weight: blod;
                }
                .content{
                    padding-top: 16px;
                    .name{
                        font-size: 16px;
                        color: #111111;
                        letter-spacing: 1px;
                        text-align: left;
                        line-height: 16px;
                        font-weight: 500;
                    }
                    .tenant-name{
                        margin-top: 4px;
                        text-overflow: ellipsis;
                        overflow:hidden;
                        white-space:nowrap;
                        font-size: 12px;
                        color: #CCCCCC;
                        letter-spacing: 1px;
                        text-align: left;
                        line-height: 12px;
                    }
                    .desc{
                        margin-top: 2px;
                        text-overflow: ellipsis;
                        overflow:hidden;
                        white-space:nowrap;
                        font-size: 12px;
                        color: #CCCCCC;
                        letter-spacing: 1px;
                        text-align: left;
                        line-height: 12px;
                    }
                    .desc-type-name{
                       margin-top: 8px; 
                    }
                }
                &:after{
                    content: '';
                    display: block;
                    background: #EFEFEF;
                    height: 1px;
                    left: 20px;
                    right: 20px;
                    position: absolute;
                    top: 76px;
                }
            }
        }
    }
</style>