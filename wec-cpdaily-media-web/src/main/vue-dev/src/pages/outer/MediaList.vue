<template xmlns:v-bind="http://www.w3.org/1999/xhtml">
    <div class="media-list">
        <p class="first-title">校园号列表</p>
        <p class="second-title">这是您可使用的所有校园号列表</p>
        <ul class="card-list">
            <li v-for="(item,index) in mediaList" v-bind:key="index" class="card-item">
                <Card :padding="8">
                    <div class="content">
                        <div class="status" :class="getStatusClass(item.authStatus)">
                            <i class="point" :class="getStatusClassForPoint(item.authStatus)"></i>
                            <span style="font-size: 12px;">{{getStatusName(item.authStatus)}}</span>
                            <span class="fans-count" v-show="item.fansCount > 0">被关注：{{item.fansCount}}</span>
                        </div>
                        <div class="type" :class="getTypeClass(item.mediaType)">
                            <span>{{getTypeName(item.mediaType)}}</span>
                        </div>
                        <p class="name">{{item.name}}</p>
                        <div class="managers" v-if="item.authStatus != 'AUTH_FAIL'">
                            <span class="managers-data">管理员:{{item.managers && item.managers.length > 0 ? item.managers.join(',') : '暂无'}}</span>
                            <span v-if="item.manageType === 'OWNER'" class="action-btn" @click="addManager(item.wid)">添加</span>
                            <span v-if="item.manageType != 'OWNER'" class="action-btn" @click="showManager(item.wid)">查看</span>
                        </div>
                        <div class="managers" v-if="item.authStatus === 'AUTH_FAIL'">
                            <span class="managers-data">审核意见:{{item.reviewOpinion}}</span>
                            <span class="action-btn" @click="viewMoreOpin(item.reviewOpinion)">查看</span>
                        </div>
                        <p class="desc">{{item.description}}</p>
                        <div class="bottom">
                            <Button @click="enter(item)" type="primary" shape="circle" class="btn-left" :class="{'btn-left-long' : item.manageType !== 'OWNER'}">{{getRightBtnShowTxt(item.authStatus)}}</Button>
                            <Button @click.stop="del(item)" v-if="item.manageType === 'OWNER'" type="ghost" shape="circle" class="btn-right">删除</Button>
                        </div>
                    </div>
                </Card>
            </li>
            <li class="add-media" @click="apply">
                <div>
                    <Icon type="ios-plus-outline" size="30" style="margin-top: 90px;color: #3399FF;"></Icon>
                    <p style="font-size: 12px;color: #3399FF;letter-spacing: 0;margin-top: 4px;">申请校园号</p>
                </div>
            </li>
        </ul>


        <!-- 查看审核失败原因弹出框-->
        <Modal v-model="isShowOpin" width="347" :styles="{top: '258px'}" >
            <p slot="header" style="color:#1C2438;text-align:left">
                <span style="font-weight: 500;">审核意见</span>
            </p>
            <div style="text-align:center;color: #495060;font-size: 12px;line-height: 16px;vertical-align: middle;">
                <p style="display: inline-block;width: 260px;text-align: left;line-height: 17px;">{{reviewOpinion ? reviewOpinion : '审核失败原因未填写'}}</p>
            </div>
            <div slot="footer">
                <Button type="primary" @click="isShowOpin = false">确定</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import util from 'conf/util'
import api from 'conf/api'
import Bus from 'services/Bus.js'

export default {
    data () {
        return {
            isShowOpin: false,
            reviewOpinion: '',
            mediaList: [],
            managerData:[],
            currentMediaId:'',
            addManagerShow: false
        }
    },
    methods: {
        viewMoreOpin (reviewOpinion) {
            this.reviewOpinion = reviewOpinion
            this.isShowOpin = true
        },
        addManager (mediaId) {
            this.$router.push({path: '/outer/viewadmins', query: {mediaId: mediaId, owner: true}})
        },
        showManager (mediaId) {
            this.$router.push({path: '/outer/viewadmins', query: {mediaId: mediaId, owner: false}})
        },
        getStatusName (status) {
            if (status === 'AUTHED') {
                return '已审核'
            }
            if (status === 'AUTH_FAIL') {
                return '已拒绝'
            }
            if (status === 'UN_AUTH') {
                return '未认证'
            }
            if (status === 'AUTHING') {
                return '待审核'
            }
            return '数据异常'
        },
        getTypeName (type) {
            if (type === 'OFFICIAL') {
                return '职能'
            } else {
                return '学生'
            }
        },
        getStatusClass (status) {
            if (status === 'AUTHED') {
                return 'status-success'
            }
            if (status === 'AUTH_FAIL') {
                return 'status-refuse'
            }
            if (status === 'UN_AUTH') {
                return 'status-audit'
            }
            if (status === 'AUTHING') {
                return 'status-audit'
            }
        },
        getStatusClassForPoint (status) {
            if (status === 'AUTHED') {
                return 'status-success-point'
            }
            if (status === 'AUTH_FAIL') {
                return 'status-refuse-point'
            }
            if (status === 'UN_AUTH') {
                return 'status-audit-point'
            }
            if (status === 'AUTHING') {
                return 'status-audit-point'
            }
        },
        getTypeClass (type) {
            if (type === 'OFFICIAL') {
                return 'type-function'
            } else {
                return 'type-student'
            }
        },
        getShowFollows (follow) {
            if (follow > 10000) {
                return (follow / 10000).toFixed(2) + 'W'
            } else {
                return follow
            }
        },
        getRightBtnShowTxt (status) {
            if (status === 'AUTHED') {
                return '进入'
            }
            if (status === 'UN_AUTH') {
                return '认证'
            }
            if (status === 'AUTH_FAIL') {
                return '重新申请'
            }
            if (status === 'AUTHING') {
                return '进入'
            }
        },
        apply () {
            let temp = this.mediaList.filter((item) => {
                // 自己创建的，并且不是认证失败的
                return item.authStatus != 'AUTH_FAIL' && item.manageType === 'OWNER'
            })
            if (temp && temp.length >=5) {
                this.$Message.error('每人最多只能拥有5个校园号～')
                return
            }
            this.$router.push('/outer/applymedia')
        },
        enter (media) {
                if (media.authStatus === 'UN_AUTH' || media.authStatus === 'AUTHING') {
                    sessionStorage.setItem('authStatus', media.authStatus)
                    this.$router.replace({path:'/outer/approvedesc/'+  media.wid})
                } else if (media.authStatus === 'AUTH_FAIL') {
                    this.$router.replace('/outer/mediainfo/' + media.wid)
                } else {
                    this.postSwitch(media).then((data) => {
                    })
                }
        },
        postSwitch (media) {
            console.log(media.wid)
            return util.httpGet(api.mediaSwitch + '/' + media.wid, {}, util.handler.CODE).then((data) => {
                if (data && data.code === '0') {
                    Bus.$emit('switchMedia', media.wid)
                    this.$router.replace('/home')
                } else {
                    this.$Message.error('切换失败：' + data.message)
                }
            }).catch((err) => {
                this.$Message.error('切换失败：' + err.errMsg)
            })
        },
        del (media) {
            let self = this
            self.$Modal.confirm({
                title: '请确认',
                content: '是否删除此校园号？',
                okText: '确定',
                cancelText: '取消',
                onOk: function() {
                    util.httpPost(api.mediaDel + '/' + media.wid, {}, util.handler.CODE).then((res) => {
                        self.$Message.success('校园号删除成功')
                        self.getMediaList()
                    }).catch((err) => {
                        self.$Message.error('校园号删除失败：' + err.errMsg)
                    })
                }
            })
        },
        getMediaList () {
            util.httpGet(api.mediaList, {}, util.handler.DATAS).then((data) => {
                console.log(data)
                this.mediaList = data
            }).catch((err) => {
                this.$Message.error('获取校园号列表失败：' + err.errMsg)
            })
        }
    },
    computed: {

    },
    mounted () {
        this.getMediaList()
    }
}
</script>

<style scoped lang="less" rel="stylesheet/less">
    .media-list{
        margin: 20px;
        width: 1232px;
        padding: 26px 24px 26px 24px;
        box-sizing: border-box;
        min-height: e("calc(100vh - 114px)");
        background: #FFFFFF;
        box-shadow: 0 0 12px 0 rgba(132,134,219,0.16);
        border-radius: 2px;
        .first-title{
            font-size: 16px;
            color: #464C5B;
            letter-spacing: 0;
            line-height: 16px;
            font-weight: 500;
        }
        .second-title{
            margin-top: 10px;
            font-size: 12px;
            color: #80848F;
            letter-spacing: 0;
            line-height: 16px;
        }
        .card-list{
            margin-top: 26px;
            display: flex;
            flex-wrap: wrap;
            .card-item{
                flex:none;
                width:278px;
                display: inline-block;
                margin-right: 24px;
                margin-bottom: 24px;
                &:nth-child(4n){
                    margin-right: 0px;
                }
                .content{
                    .status{
                        position: relative;
                    }
                    .status-success{
                        color: #19BE6B;
                    }
                    .status-refuse{
                        color: #FF655E;
                    }
                    .status-audit{
                        color: #FFAC36;
                    }
                    .fans-count{
                        position: absolute;
                        right: 10px;
                        font-size: 12px;
                        color: #495060;
                        letter-spacing: 0;
                    }
                    .point{
                        display: inline-block;
                        width: 8px;
                        height: 8px;
                        border-radius: 8px;
                        margin-right: 2px;
                    }
                    .status-success-point{
                        background: #19BE6B;
                    }
                    .status-refuse-point{
                        background: #FF655E;
                    }
                    .status-audit-point{
                        background: #FFAC36;
                    }
                    .type{
                        width: 50px;
                        height: 50px;
                        font-size: 12px;
                        text-align: center;
                        vertical-align: middle;
                        line-height: 50px;
                        margin: 0 auto;
                        border-radius: 50px;
                    }
                    .type-student{
                        color:#3399FF;
                        background: rgba(51,153,255,0.20);
                        box-shadow: 0 1px 4px 0 rgba(51,153,255,0.20);
                    }
                    .type-function{
                        color: #FF6100;
                        background: rgba(255,102,0,0.20);
                        box-shadow: 0 1px 4px 0 rgba(255,102,0,0.20);
                    }
                    .name{
                        margin-top: 8px;
                        font-weight: 500;
                        font-size: 14px;
                        color: #495060;
                        letter-spacing: 0;
                        text-align: center;
                    }
                    .managers{
                        margin-left: 10px;
                        margin-right: 10px;
                        .managers-data{
                            display: inline-block;
                            width: 200px;
                            font-size: 12px;
                            color: #BBBEC4;
                            letter-spacing: 0;
                            overflow: hidden;
                            text-overflow:ellipsis;
                            white-space: nowrap;
                            vertical-align: bottom;
                        }
                        .action-btn{
                            font-size: 12px;
                            color: #2D8CF0;
                            letter-spacing: 0;
                            cursor: pointer;
                        }
                    }
                    .desc{
                        width: 242px;
                        height: 34px;
                        margin: 16px auto;
                        font-size: 12px;
                        color: #80848F;
                        letter-spacing: 0;
                        overflow: hidden;
                        text-overflow: ellipsis;
                        white-space: nowrap;
                    }
                    .bottom{
                        text-align: center;
                        margin: 0 47px;
                        .btn-left{
                            width: 72px;
                            height: 24px;
                            padding: 0px;
                            margin-bottom: 8px;
                        }
                        .btn-left-long{
                            width: 144px;
                        }
                        .btn-right{
                            width: 72px;
                            height: 24px;
                            padding: 0px;
                            margin-left: 18px;
                            margin-bottom: 8px;
                            color: #2d8cf0;
                            border-color: #2d8cf0;
                        }
                    }
                }

            }
            .add-media{
                cursor: pointer;
                display: inline-block;
                width: 278px;
                height: 234px;
                margin-bottom: 24px;
                text-align: center;
                background: #FFFFFF;
                border: 1px dashed rgba(227,232,238,0.80);
                border-radius: 4px;
            }
        }
    }
    @media screen {
        /* 大于1300px的屏幕 */
        @media (min-width: 1280px){
            .media-list{
                margin: 20px auto;
            }
        }
    }
</style>
