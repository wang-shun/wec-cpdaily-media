<template>
    <div class="media-logs">
        
        <breadcrumb :items="bcs"></breadcrumb>
        
        <div class="content-container">
            <div class="header">
                <p class="header-title">校园号信息</p>
                <Row class="header-item" style="margin-top: 20px;">
                    <Col span="8">
                        <span class="title">校园号昵称：</span>
                        <span class="content">{{mediaName}}</span>
                    </Col>
                    <Col span="8">
                        <span class="title">校园号ID：</span>
                        <span class="content">{{mediaId}}</span>
                    </Col>
                    <Col span="8">
                        <span class="title">所属学校：</span>
                        <span class="content">{{tenantName}}</span>
                    </Col>
                </Row>
                <Row class="header-item" style="margin-top: 10px;">
                    <Col span="8">
                        <span class="title">类型：</span>
                        <span class="content">{{mediaType === 'CLUB' ? '学生组织' : '职能部门'}}</span>
                    </Col>
                    <Col span="8">
                        <span class="title">拥有者：</span>
                        <span class="content">{{ownerName}}</span>
                    </Col>
                    <Col span="8">
                        <span class="title">状态：</span>
                        <span class="content">{{statusStr}}</span>
                    </Col>
                </Row>
            </div>
            <div class="list-container">
                <p class="header-title">操作日志</p>
                <div class="selection-container">
                    <span style="font-size: 12px;color: #464C5B;letter-spacing: 0;font-weight: 500;">选择日期</span>
                    <DatePicker style="margin-left:20px;width: 300px" type="daterange" v-model="addTime" placeholder="请选择日期" format="yyyy-MM-dd" @on-change="onAddTimeChange">
                    </DatePicker>
                    <Button style="margin-left: 16px;" type="primary" @click="getLogs(true)">搜索</Button>
                </div>

                <div class="log-data-list">
                    <Table border :columns="logsColumns" :data="logsData"></Table>
                </div>
                <div class="bottom-page">
                    <Page :total="totalSize" :current="pageNum" show-elevator show-sizer placement="top" :page-size="pageSize"
                          @on-page-size-change="onPageSizeChange" @on-change="onChange"></Page>
                </div>
            </div>
        </div>
        <Modal v-model="isAutoReplyShow" width="500">
            <p slot="header" style="color:rgb(73, 80, 96);text-align:center">
                <span>日志详情-配置自动回复</span>
            </p>
            <div class="auto-replay-content">
                <div class="title">
                    <Row>
                        <Col span="4"><span class="title-name">标题:</span></Col>
                        <Col span="20"><span class="title-title">{{modalObject.title}}</span></Col>
                    </Row>
                </div>
                <div class="content" style="margin-top:10px;">
                    <Row>
                        <Col span="4"><span class="title-name">内容:</span></Col>
                        <Col span="20">
                            <div class="content-content"  v-html="modalObject.content" style="max-height:300px; overflow:auto;"></div>
                        </Col>
                    </Row>
                </div>
                <div class="bg" style="margin-top:10px;">
                    <Row>
                        <Col span="4"><span class="title-name">封面:</span></Col>
                        <Col span="20"><img :src="modalObject.img" width="350" height="200" style="object-fit: cover;"></Col>
                    </Row>
                </div>
            </div>
            <div slot="footer">
                <Button type="primary" @click="isAutoReplyShow = false">确定</Button>
            </div>
        </Modal>

        <Modal v-model="isEditMedia" width="550">
            <p slot="header" style="color:rgb(73, 80, 96);text-align:center">
                <span>日志详情-编辑校园号</span>
            </p>
            <div class="edit-media-content">
                <div class="title">
                    <Row>
                        <Col span="5"><span class="title-name">名称:</span></Col>
                        <Col span="19"><span class="title-title">{{modalObject.name}}</span></Col>
                    </Row>
                </div>
                <div class="icon" style="margin-top:10px;">
                    <Row>
                        <Col span="5"><span class="title-name">图标:</span></Col>
                        <Col span="19">
                            <img :src="modalObject.icon" width="50" height="50" style="object-fit: cover;">
                        </Col>
                    </Row>
                </div>
                <div class="background-img" style="margin-top:10px;">
                    <Row>
                        <Col span="5"><span class="title-name">个人主页背景:</span></Col>
                        <Col span="19">
                            <img v-if="modalObject.backgroundImg" :src="modalObject.backgroundImg" width="375" height="108" style="object-fit: cover;">
                            <span v-if="!modalObject.backgroundImg">未设置</span>
                        </Col>
                    </Row>
                </div>
                <div class="tenant-id" style="margin-top:10px;">
                    <Row>
                        <Col span="5"><span class="title-name">租户:</span></Col>
                        <Col span="19"><span class="title-title">{{modalObject.tenantName}}</span></Col>
                    </Row>
                </div>
                <div class="media-type" style="margin-top:10px;">
                    <Row>
                        <Col span="5"><span class="title-name">类型:</span></Col>
                        <Col span="19"><span class="title-title">{{modalObject.mediaType === 'OFFICIAL' ? '学校职能部门' : '学生组织'}}</span></Col>
                    </Row>
                </div>
                <div class="media-type" style="margin-top:10px;">
                    <Row>
                        <Col span="5"><span class="title-name">简介:</span></Col>
                        <Col span="19"><span class="title-title">{{modalObject.description}}</span></Col>
                    </Row>
                </div>
                <div class="auth-materials" style="margin-top:10px;">
                    <Row>
                        <Col span="5"><span class="title-name">认证材料:</span></Col>
                        <Col span="19">
                            <img v-for="(item, index) in modalObject.authMaterials" v-bind:key="index" :src="item" width="100" height="100" style="object-fit: cover;">
                        </Col>
                    </Row>
                </div>
            </div>
            <div slot="footer">
                <Button type="primary" @click="isEditMedia = false">确定</Button>
            </div>
        </Modal>


        <Modal v-model="isPublishMessageShow" width="500">
            <p slot="header" style="color:rgb(73, 80, 96);text-align:center">
                <span>日志详情-发送消息</span>
            </p>
            <div class="publish-message-content">
                <div class="title">
                    <Row>
                        <Col span="4"><span class="title-name">标题:</span></Col>
                        <Col span="20"><span class="title-title">{{modalObject.title}}</span></Col>
                    </Row>
                </div>
                <div class="content" style="margin-top:10px;">
                    <Row>
                        <Col span="4"><span class="title-name">内容:</span></Col>
                        <Col span="20">
                            <div class="content-content"  v-html="modalObject.content" style="max-height:300px; min-height:100px;overflow:auto;"></div>
                        </Col>
                    </Row>
                </div>
                <div class="bg" style="margin-top:10px;">
                    <Row>
                        <Col span="4"><span class="title-name">封面:</span></Col>
                        <Col span="20"><img :src="modalObject.img" width="200" height="100" style="object-fit: cover;"></Col>
                    </Row>
                </div>
                <div class="groups" style="margin-top:10px;">
                    <Row>
                        <Col span="4"><span class="title-name">用户组:</span></Col>
                        <Col span="20"><span class="title-title">{{modalObject.groups}}</span></Col>
                    </Row>
                </div>
                <div class="groups" style="margin-top:10px;display: none;">
                    <Row>
                        <Col span="4"><span class="title-name">用户:</span></Col>
                        <Col span="20"><span class="title-title" style="    max-height: 20px;display: block;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;">{{modalObject.users}}</span></Col>
                    </Row>
                </div>
            </div>
            <div slot="footer">
                <Button type="primary" @click="isPublishMessageShow = false">确定</Button>
            </div>
        </Modal>
    </div>
</template>

<script type="text/ecmascript-6">
    import breadcrumb from '../components/breadcrumb.vue'
    import * as service from 'services/openService.js'
    import util from 'conf/util'
    import api from 'conf/api'
    export default {
        data () {
            return {
                bcs: [{
                    name: '操作日志'
                }],
                isEditMedia: false,
                isAutoReplyShow: false,
                isPublishMessageShow: false,
                modalObject: {},
                addTime: '',
                mediaName: '',
                tenantName: '',
                mediaType: '',
                ownerName: '',
                statusStr: '',
                beginDate: '',
                endDate: '',
                mediaId: '',
                totalSize: 0,
                pageNum: 1,
                pageSize: 10,
                logsColumns: [
                    {
                        title: '操作ID',
                        key: 'wid',
                        width: 150,
                    },
                    {
                        title: '操作类型',
                        key: 'operateType',
                        width: 250,
                        align: 'center',
                        ellipsis: true,
                        render: (h, params) => {
                            return h('span',{
                                attrs:{
                                    title: params.row.operateTypeName
                                }
                            }, params.row.operateTypeName);
                        }
                    },
                    {
                        title: '操作人',
                        key: 'opertatorName',
                        width: 100,
                        align: 'center',
                        ellipsis: true,
                        render: (h, params) => {
                            return h('span',{
                                attrs:{
                                    title: params.row.operatorName
                                }
                            }, params.row.operatorName);
                        }
                    },
                    {
                        title: '操作时间',
                        key: 'operateTime',
                        align: 'center'
                    },
                    {
                        title: '执行结果',
                        key: 'operateResult',
                        width: 100,
                        align: 'center',
                        ellipsis: true,
                        render: (h, params) => {
                            return h('span',{
                                attrs:{
                                    title: params.row.operateResult ? '成功' : '失败'
                                }
                            }, params.row.operateResult ? '成功' : '失败');
                        }
                    },
                    {
                        title: '操作',
                        key: 'wid',
                        width: 100,
                        align: 'center',
                        render: (h, params) => {
                            return h('Button', {
                                props: {
                                    type: 'text',
                                    size: 'small'
                                },
                                style: {
                                    padding: '0px',
                                    color: '#2D8CF0'
                                },
                                on: {
                                    click: () => {
                                        this.modalObject = JSON.parse(params.row.operateContent);
                                        if (params.row.operateType === 'CONFIG_AUTO_REPLY') {
                                            this.isAutoReplyShow = true
                                        } else if (params.row.operateType === 'EDIT_MEDIA_BASIC_INFO') {
                                            if (this.modalObject.authMaterials) {
                                                this.modalObject.authMaterials = this.modalObject.authMaterials.split(',')
                                            } else {
                                                this.modalObject.authMaterials = []
                                            }
                                            console.log(this.modalObject)
                                            this.isEditMedia = true
                                        } else if (params.row.operateType === 'PUSH_MESSAGE') {
                                            let showGroups = ''
                                            if (this.modalObject.groups) {
                                                this.modalObject.groups.forEach((item, index) => {
                                                    if (index != 0) {
                                                        showGroups += ',' + item.groupName
                                                    } else {
                                                        showGroups += item.groupName
                                                    }
                                                    
                                                })
                                            }
                                            this.modalObject.users = this.modalObject.sendTotalUserIds.join(',')
                                            this.modalObject.groups = showGroups
                                            this.isPublishMessageShow = true
                                        }
                                    }
                                }
                            }, '查看')
                        }
                    }
                ],
                logsData: []
            }
        },
        methods: {
            goBack(){
                this.$router.go(-1)
            },
            onPageSizeChange (size) {
                this.pageSize = size
                this.pageNum = 1
                this.getLogs()
            },
            onChange (size) {
                this.pageNum = size
                this.getLogs()    
            },
            getLogs (refresh) {
                this.logsData = []
                if (refresh) {
                    this.pageNum = 1
                }
                let param = {
                    beginDate: this.beginDate ? this.beginDate : null,
                    endDate: this.endDate ? this.endDate : null,
                    pageNum: this.pageNum,
                    pageSize: this.pageSize
                }
                service.getLogs(param)
                        .then((res) => {
                            console.log(res)
                            if (res && res.code == '0') {
                                res.datas.logs.forEach((item) => {
                                    if (item.operateType === 'EDIT_MEDIA_BASIC_INFO') {
                                        item.operateTypeName = '编辑校园号'
                                    } else if (item.operateType === 'PUBLISH_ACTIVITY') {
                                        item.operateTypeName = '发起活动'
                                    } else if (item.operateType === 'PUSH_MESSAGE') {
                                        item.operateTypeName = '发送消息'
                                    } else if (item.operateType === 'PUBLISH_FRESH') {
                                        item.operateTypeName = '发送新鲜事'
                                    } else if (item.operateType === 'CONFIG_AUTO_REPLY') {
                                        item.operateTypeName = '配置自动回复'
                                    }
                                });
                                this.logsData = res.datas.logs
                                this.totalSize = res.datas.total
                            }
                        }).catch((err) => {
                            this.$Message.error(mode + '失败：' + err.message)
                        })
            },
            getCurrentMediaInfo () {
                util.httpGet(api.mediaInfo + '/-1', {}, util.handler.DATAS).then((data) => {
                    console.log(data)
                    this.mediaId = data.wid
                    this.mediaName = data.name
                    this.tenantName = data.tenantName ? data.tenantName : '/'
                    this.mediaType = data.mediaType
                    this.ownerName = data.ownerName
                    this.statusStr = data.status === 'ENABLE' ? '启用中' : '已停用'
                 }).catch((err) => {
                     this.$Message.error('获取校园号信息失败：' + err.message)
                 })
            },
            onAddTimeChange (time) {
                if (time && time.length === 2 && time[1]) {
                    this.beginDate = time[0]
                    this.endDate = time[1]
                } else {
                    this.beginDate = ''
                    this.endDate = ''
                }
            }
        },
        mounted () {
            this.getLogs()
            this.getCurrentMediaInfo()
        },
        created () {
            
        },
        components:{
            breadcrumb
        }
    }
</script>

<style lang="less" rel="stylesheet/less">
    .ivu-modal-body{
        .auto-replay-content{
            font-size: 13px;
            .title-name{
                color: rgb(73, 80, 96);
                font-weight: 500;
            }
        }
        .edit-media-content{
            font-size: 13px;
            .title-name{
                color: rgb(73, 80, 96);
                font-weight: 500;
            }
        }
        .publish-message-content{
            font-size: 13px;
            .title-name{
                color: rgb(73, 80, 96);
                font-weight: 500;
            }
        }
    }
</style>

<style scoped lang="less" rel="stylesheet/less">
    .media-logs{
        .content-container{
            margin: 20px;
            min-height: e("calc(100vh - 144px)");
            background: #FFFFFF;
            padding: 26px 24px 26px 24px;
            box-shadow: 0 0 12px 0 rgba(132,134,219,0.16);
            border-radius: 2px;
            .header{
                .header-title{
                    position: relative;
                    font-size: 16px;
                    color: #464C5B;
                    letter-spacing: 0;
                    line-height: 16px;
                    font-weight: 500;
                    height: 50px;
                    line-height: 50px;
                    margin-top: -20px;
                    &:after{
                        content: '';
                        display: block;
                        background: #E3E8EE;
                        height: 1px;
                    }
                }
                .header-item{
                    .title{
                        font-size: 12px;
                        color: #495060;
                        letter-spacing: 0;
                        font-weight: 500;
                    }
                    .content{
                        font-size: 12px;
                        color: #98A1AE;
                        letter-spacing: 0;
                    }
                }
            }
            .list-container{
                .header-title{
                    position: relative;
                    font-size: 16px;
                    color: #464C5B;
                    letter-spacing: 0;
                    line-height: 16px;
                    font-weight: 500;
                    height: 50px;
                    line-height: 50px;
                    margin-top: 20px;
                    &:after{
                        content: '';
                        display: block;
                        background: #E3E8EE;
                        height: 1px;
                    }
                }
                .selection-container{
                    height: 60px;
                    line-height: 60px;
                }
                .log-data-list{
                    margin-top: 10px;
                }
                .bottom-page {
                    position: relative;
                    margin-top: 20px;
                    text-align: left;
                }
            }
        } 
    }
</style>