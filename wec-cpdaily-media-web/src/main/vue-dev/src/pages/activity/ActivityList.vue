<template xmlns:v-bind="http://www.w3.org/1999/xhtml">
    <div class="activity-list">
        <breadcrumb :items="bcs"></breadcrumb>
        <div class="content">
            <p class="title">校园活动</p>
            <div class="search-input">
                <input type="text" class="search-content" placeholder="请输入活动名称" v-model="name" @keyup.13="queryActivities">
                <Icon class="search-icon" type="ios-search-strong" size="22"></Icon>
                <Button class="search-btn" slot="append" type="primary" style="background: #2D8CF0;border-radius: 0 4px 4px 0;" @click="queryActivities">搜索</Button>
            </div>
            <div class="screen">
                <ul>
                    <li style="padding: 0px;cursor: default;"><span style="font-size: 12px;color: #747986;letter-spacing: 0;">筛选:</span></li>
                    <li v-for="(item,index) in screens" v-bind:key="index" :class="item.type === publishStatus ? 'screen-selected' : ''" @click="clickType(item.type)">
                        <span >{{item.name}}</span>
                    </li>
                </ul>
            </div>
            <div>
                <Button type="primary" class="add-new-btn" @click="addActivity">新建活动</Button>
            </div>
            <div class="activity-data-list">
                <Table border :columns="activityListColumns" :data="activityListData"></Table>
            </div>
            <div class="bottom-page">
                <Page :total="totalSize" :current="pageNumber" show-elevator show-sizer placement="top" @on-page-size-change="onPageSizeChange" @on-change="onChange"></Page>
            </div>
        </div>
        <Modal
            v-model="modalQRCode"
            title=""
            width="450"
            @on-ok="modalOk">
            <div class="qrcode-container" style="text-align: center;padding: 50px;background-color:white;">
                <div class="qrcode-con" style="width: 300px;height: 300px;display: inline-block;"></div>
            </div>
            <div v-if="downloadButtonShow" style="text-align:center;"><a @click.prevent="downloadQRCodeImage">下载签到码</a></div>
        </Modal>
        <!-- 报名人数查看-->
        <Modal v-model="isJoinPeosShow" width="900">
            <p slot="header" style="color:#000;text-align:left;">
                <span>查看报名者信息</span>
            </p>
            <div style="text-align: right;">
                <Button  type="primary" @click="exportPeople(currentJoinActivityId)">导出名单</Button>
            </div>
            <div class="people-data-list">
                <Table border :columns="joinPeosColumns" :data="joinPeosDatas"></Table>
            </div>
            <div class="people-bottom-page">
                <Page :total="joinTotalSize" :current="joinPeoPageNum" show-elevator show-sizer placement="top" @on-page-size-change="onJoinPeosPageSizeChange" @on-change="onJoinPeosChange"></Page>
            </div>
            <div slot="footer">
            </div>
        </Modal>
        <!-- 确认暂停弹出框-->
        <Modal v-model="isConfirmPause" width="347" :styles="{top: '258px'}" >
            <p slot="header" style="color:#1C2438;text-align:left">
                <span style="font-weight: 500;">确认暂停</span>
            </p>
            <div style="text-align:center;color: #495060;font-size: 12px;line-height: 16px;vertical-align: middle;">
                <Icon type="information-circled" style="line-height: 30px;font-size: 30px;margin-right: 10px;color: #FF9901;line-height: 30px;vertical-align: top;"></Icon>
                <p style="display: inline-block;width: 260px;text-align: left;line-height: 30px;">暂停后活动将停止报名，确定暂停吗？</p>
            </div>
            <div slot="footer">
                <Button type="text" @click="isConfirmPause = false">取消</Button>
                <Button type="primary" @click="pauseActivity">确定</Button>
            </div>
        </Modal>
        <!-- 确认编辑弹出框-->
        <Modal v-model="isRecoverSign" width="347" :styles="{top: '258px'}" >
            <p slot="header" style="color:#1C2438;text-align:left">
                <span style="font-weight: 500;">确认编辑</span>
            </p>
            <div style="text-align:center;color: #495060;font-size: 12px;line-height: 16px;vertical-align: middle;">
                <Icon type="information-circled" style="line-height: 30px;font-size: 30px;margin-right: 10px;color: #FF9901;line-height: 30px;vertical-align: top;"></Icon>
                <p style="display: inline-block;width: 260px;text-align: left;line-height: 30px;">报名已结束，请重新编辑活动</p>
            </div>
            <div slot="footer">
                <Button type="text" @click="isRecoverSign = false">取消</Button>
                <Button type="primary" @click="recoverSignEdit">去编辑</Button>
            </div>
        </Modal>
        <!-- 确认取消发布弹出框-->
        <Modal v-model="isShowCancel" width="347" :styles="{top: '258px'}" >
            <p slot="header" style="color:#1C2438;text-align:left">
                <span style="font-weight: 500;">确认取消</span>
            </p>
            <div style="text-align:center;color: #495060;font-size: 12px;line-height: 16px;vertical-align: middle;">
                <Icon type="information-circled" style="line-height: 30px;font-size: 30px;margin-right: 10px;color: #FF9901;line-height: 30px;vertical-align: top;"></Icon>
                <p style="display: inline-block;width: 260px;text-align: left;line-height: 17px;">取消活动后将自动为成功报名的用户取消报名，并通知活动已取消，确定要取消吗？</p>
            </div>
            <div slot="footer">
                <Button type="text" @click="isShowCancel = false">取消</Button>
                <Button type="primary" @click="cancelActivity">确定</Button>
            </div>
        </Modal>
        <!-- 确认删除弹出框-->
        <Modal v-model="isShowDelete" width="347" :styles="{top: '258px'}" >
            <p slot="header" style="color:#1C2438;text-align:left">
                <span style="font-weight: 500;">确认删除</span>
            </p>
            <div style="text-align:center;color: #495060;font-size: 12px;line-height: 16px;vertical-align: middle;">
                <Icon type="information-circled" style="line-height: 30px;font-size: 30px;margin-right: 10px;color: #FF9901;line-height: 30px;vertical-align: top;"></Icon>
                <p style="display: inline-block;width: 260px;text-align: left;line-height: 30px;">确认删除活？确认之后活动将被删除</p>
            </div>
            <div slot="footer">
                <Button type="text" @click="isShowDelete = false">取消</Button>
                <Button type="primary" @click="delActivity">确定</Button>
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
            isShowDelete: false,
            deleteId: '',
            isShowCancel: false,
            cancelId: '',
            isRecoverSign: false,
            recoverId: '',
            isConfirmPause: false,
            pauseWid: '',
            currentJoinActivityId: '',
            joinPeoPageNum: 1,
            joinPeoPageSize: 10,
            joinTotalSize: 0,
            isJoinPeosShow: false,
            modalQRCode: false,
            downloadButtonShow: false,
            bcs: [{
                // path: '/home/activitylist',
                name: '校园活动'
            }],
            name: '',
            pageSize: 10,
            pageNumber: 1,
            totalSize: 0,
            screens: [
                {
                    type: '', // 全部
                    name: '全部'
                },
                {
                    type: 1, // 报名未开始
                    name: '报名未开始'
                },
                {
                    type: 2, // 报名中
                    name: '报名中'
                },
                // {
                //     type: 3, // 暂停报名
                //     name: '暂停报名'
                // },
                {
                    type: 4, // 报名结束
                    name: '报名结束'
                },
                {
                    type: 0, // 草稿
                    name: '草稿'
                }
            ],
            publishStatus: '',
            joinPeosColumns: [
                {
                    title: '姓名',
                    key: 'name'
                },
                {
                    title: '性别',
                    key: 'sex',
                    align: 'center',
                    width: 80
                },
                {
                    title: '学院',
                    key: 'college'
                },
                {
                    title: '专业',
                    key: 'major'
                },
                {
                    title: '是否已签到',
                    key: 'signInStatusStr',
                    align: 'center',
                    width: 100
                },
                {
                    title: '签到时间',
                    key: 'signInTime'
                },
                {
                    title: '手机号',
                    key: 'telnumber'
                },
                {
                    title: '备注',
                    key: 'remark',
                    width: 100,
                    ellipsis: true,
                    render: (h, params) => {
                        return h('span',{
                            attrs:{
                                title: params.row.remark
                            }
                        }, params.row.remark);
                    }
                }
            ],
            joinPeosDatas: [],
            activityListColumns: [
                {
                    title: '活动名称',
                    key: 'name',
                    width: 120,
                    ellipsis: true,
                    render: (h, params) => {
                        return h('span',{
                            attrs:{
                                title: params.row.name
                            }
                        }, params.row.name);
                    }
                },
                {
                    title: '状态',
                    width: 90,
                    key: 'publishStatusShow'
                },
                {
                    title: '检票方式',
                    key: 'checkWay',
                    align: 'center',
                    width: 80
                },
                {
                    title: '检票人',
                    key: 'tellers',
                    align: 'center',
                    width: 80,
                    ellipsis: true,
                    render: (h, params) => {
                        return h('span',{
                            attrs:{
                                title: params.row.tellers
                            }
                        }, params.row.tellers);
                    }
                },
                {
                    title: '举办时间',
                    key: 'timeForShow',
                    width: 170,
                },
                {
                    title: '报名时间',
                    key: 'signTime',
                    ellipsis: true,
                    render: (h, params) => {
                        return h('span',{
                            attrs:{
                                title: params.row.signTime
                            }
                        }, params.row.signTime);
                    }
                },
                {
                    title: '举办地点',
                    key: 'address',
                    ellipsis: true,
                    render: (h, params) => {
                        return h('span',{
                            attrs:{
                                title: params.row.address
                            }
                        }, params.row.address);
                    }
                },
                {
                    title: '报名人数',
                    key: 'peopleNumber',
                    align: 'center',
                    width: 90,
                    render: (h, params) => {
                        return h('div', {
                            style: {
                                'line-height': '20px'
                            }
                        }, [
                            h('span', {
                                props: {
                                },
                                style: {
                                    'vertical-align': 'middle'
                                },
                                on: {
                                    click: () => {
                                    }
                                }
                            }, params.row.peopleNumber + '/' + (params.row.maxPeopleNumber ? params.row.maxPeopleNumber : '~')),
                            h('Button', {
                                props: {
                                    type: 'text',
                                    size: 'small'
                                },
                                style: {
                                    padding: '0px',
                                    color: '#2D8CF0',
                                    width: '24px',
                                    height: '20px',
                                    'margin-left': '2px',
                                    'display': params.row.peopleNumber === 0 ? 'none' : 'inline-block'
                                },
                                on: {
                                    click: () => {
                                        this.showPeoModal(params.row)
                                    }
                                }
                            }, '查看')
                        ])
                    }
                },
                {
                    title: '操作',
                    key: 'action',
                    align: 'center',
                    width: 138,
                    render: (h, params) => {
                        return h('div', {
                            style: {
                                'line-height': '20px'
                            }
                        }, [
                            h('Button', {
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
                                        this.editActivity(params.row.wid)
                                    }
                                }
                            }, '编辑'),
                            h('span', {
                                style: {
                                    color: '#2D8CF0',
                                    'margin-left': '2px',
                                    'margin-right': '2px'
                                }
                            }, '|'),
                            h('Poptip', {
                                props: {
                                    placement: 'top'
                                },
                                style: {

                                },
                                on: {
                                    click: () => {
                                    }
                                }
                            }, [
                                h('Button', {
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
                                        }
                                    }
                                }, '更多'),
                                h('Icon', {
                                    props: {
                                        size: '16',
                                        type: 'ios-arrow-down'
                                    },
                                    style: {
                                        color: '#2D8CF0',
                                        'vertical-align': 'middle',
                                        'margin-left': '2px'
                                    },
                                    on: {
                                        click: () => {
                                        }
                                    }
                                }),
                                h('div', {
                                    slot: 'content',
                                    style: {
                                        width: '66px',
                                        height: (params.row.publishStatus === 0 || params.row.publishStatus === 5) ? '96px' : '122px'
                                    }
                                }, [
                                    h('Button', {
                                        props: {
                                            type: 'text',
                                            size: 'small'
                                        },
                                        style: {
                                            padding: '0px',
                                            color: '#2D8CF0',
                                            display: 'block',
                                            'margin-top': '4px'
                                        },
                                        on: {
                                            click: () => {
                                                this.viewActivity(params.row)
                                            }
                                        }
                                    }, '预览'),
                                    h('Button', {
                                        props: {
                                            type: 'text',
                                            size: 'small'
                                        },
                                        style: {
                                            padding: '0px',
                                            color: '#2D8CF0',
                                            display: 'block',
                                            'margin-top': '4px'
                                        },
                                        on: {
                                            click: () => {
                                                this.downloadQRCode(params.row)
                                            }
                                        }
                                    }, '下载签到码'),
                                    h('Button', {
                                        props: {
                                            type: 'text',
                                            size: 'small'
                                        },
                                        style: {
                                            display: (params.row.publishStatus === 2) ? 'block' : 'none',
                                            padding: '0px',
                                            color: '#2D8CF0',
                                            'margin-top': '4px'
                                        },
                                        on: {
                                            click: () => {
                                                // 暂停  1.先弹出弹出框 2.执行操作
                                                this.isConfirmPause = true
                                                this.pauseWid = params.row.wid
                                            }
                                        }
                                    }, '暂停报名'),
                                    h('Button', {
                                        props: {
                                            type: 'text',
                                            size: 'small'
                                        },
                                        style: {
                                            display: (params.row.publishStatus === 3) ? 'block' : 'none',
                                            padding: '0px',
                                            color: '#2D8CF0',
                                            'margin-top': '4px'
                                        },
                                        on: {
                                            click: () => {
                                                // 恢复
                                                this.recoverSign(params.row)
                                            }
                                        }
                                    }, '恢复报名'),
                                    h('Button', {
                                        props: {
                                            type: 'text',
                                            size: 'small'
                                        },
                                        style: {
                                            padding: '0px',
                                            color: '#2D8CF0',
                                            display: 'block',
                                            'margin-top': '4px'
                                        },
                                        on: {
                                            click: () => {
                                                if (params.row.publishStatus === 0) {
                                                    // delete
                                                    this.isShowDelete = true
                                                    this.deleteId = params.row.wid
                                                } else {
                                                    // cancel
                                                    this.isShowCancel = true
                                                    this.cancelId = params.row.wid
                                                }
                                            }
                                        }
                                    }, params.row.publishStatus === 0 ? '删除' : '取消发布'),
                                    h('Button', {
                                        props: {
                                            type: 'text',
                                            size: 'small'
                                        },
                                        style: {
                                            padding: '0px',
                                            color: '#2D8CF0',
                                            display: 'block',
                                            'margin-top': '4px'
                                        },
                                        on: {
                                            click: () => {
                                                this.exportPeople(params.row.wid)
                                            }
                                        }
                                    }, '导出名单')
                                ])
                            ])
                        ])
                    }
                }
            ],
            activityListData: []
        }
    },
    methods: {
        showPeoModal (row) {
            this.isJoinPeosShow = true
            this.currentJoinActivityId = row.wid
            this.joinPeosDatas = []
            this.getJoinPeoples()
        },
        modalOk () {
            this.modalQRCode = false
        },
        clickType (type) {
            this.publishStatus = type
            this.pageNumber = 1
            this.queryActivities()
        },
        queryActivities () {
            let data = {
                name: this.name,
                startTime: '',
                endTime: '',
                publishStatus: this.publishStatus,
                pageSize: this.pageSize,
                pageNumber: this.pageNumber
            }
            service.queryActivities(data)
                .then((datas) => {
                    for (let i = 0; i < datas.length; i++) {
                        datas[i].timeForShow = datas[i].time[0]
                        if (datas[i].publishStatus === 2) {
                            datas[i].publishStatusShow = '报名中'
                            datas[i].cellClassName = {
                                publishStatusShow: 'activity-status-green'
                            }
                        } else if (datas[i].publishStatus === 1) {
                            datas[i].publishStatusShow = '报名未开始'
                            datas[i].cellClassName = {
                                publishStatusShow: 'activity-status-blue'
                            }
                        } else if (datas[i].publishStatus === 3) {
                            datas[i].publishStatusShow = '暂停报名'
                        } else if (datas[i].publishStatus === 4) {
                            datas[i].publishStatusShow = '报名结束'
                        } else if (datas[i].publishStatus === 0) {
                            datas[i].publishStatusShow = '草稿'
                        }
                    }
                    this.activityListData = datas
                    console.log(this.activityListData)
                    return datas
                })
        },
        onJoinPeosPageSizeChange (size) {
            console.log(size)
            this.joinPeoPageSize = size
            this.joinPeoPageNum = 1
            this.getJoinPeoples()
        },
        onJoinPeosChange (size) {
            this.joinPeoPageNum = size
            this.getJoinPeoples()
        },
        onPageSizeChange (size) {
            this.pageSize = size
            this.pageNumber = 1
            this.queryActivities()
        },
        onChange (size) {
            this.pageNumber = size
            this.queryActivities()
        },
        addActivity () {
            sessionStorage.setItem('mode', '')
            this.$router.push('/home/addnewactivity')
        },
        cancelActivity () {
            util.httpPost(api.CANCEL_ACTIVITY + '?wid=' + this.cancelId, {}, util.handler.CODE).then(() => {
                this.isShowCancel = false
                this.$Message.success('取消成功')
                this.queryActivities()
            }).catch((res) => {
                this.$Message.error('异常：' + res.message)
            })
        },
        pauseActivity () {
            util.httpPost(api.PAUSE_ACTIVITY + '?wid=' + this.pauseWid, {}, util.handler.CODE).then(() => {
                this.isConfirmPause = false
                this.$Message.success('暂停成功')
                this.queryActivities()
            }).catch((res) => {
                this.isConfirmPause = false
                this.$Message.error('异常：' + res.message)
            })
        },
        recoverSign (row) {
            util.httpPost(api.RECOVER_SIGN + '?wid=' + row.wid, {}, util.handler.CODE).then(() => {
                this.$Message.success('恢复成功')
                this.queryActivities()
            }).catch((res) => {
                if (res.code === "-5" || res.code === -5) {
                    // 去编辑/取消
                    this.recoverId = row.wid
                    this.isRecoverSign = true
                } else {
                    this.$Message.error('异常：' + res.message)
                }
            })
        },
        delActivity () {
            util.httpPost(api.DEL_ACTIVITY + '?wid=' + this.deleteId, {}, util.handler.CODE).then((res) => {
                this.isShowDelete = false
                this.$Message.success('删除成功')
                this.queryActivities()
            }).catch((res) => {
                this.$Message.error('异常：' + res.message)
            })
        },
        recoverSignEdit () {
            if (this.recoverId) {
                this.editActivity(this.recoverId)
            }
        },
        editActivity (wid) {
            util.httpPost(api.validActivityDoing + '?wid=' + wid, {}, util.handler.CODE).then((res) => {
                if (res.datas) {
                    this.$Message.error('异常：活动正在进行中')
                } else {
                    sessionStorage.setItem('edit-activity-id', wid)
                    sessionStorage.setItem('mode', 'edit')
                    this.$router.push('/home/addnewactivity')
                }
            }).catch((res) => {
                this.$Message.error('异常：' + res.message)
            })
        },
        viewActivity (row) {
            this.downloadButtonShow = false
            this.modalQRCode = true
            this.$nextTick(() => {
                $('.qrcode-con').empty()
                $('.qrcode-con').qrcode({
                    render: "canvas",
                    text: row.qrCodeUrl ,
                    width: 300, //二维码的宽度
                    height: 300 //二维码的高度
                })
            })
        },
        downloadQRCode (row) {
            this.downloadButtonShow = true
            this.modalQRCode = true
            this.$nextTick(() => {
                $('.qrcode-con').empty()
                $('.qrcode-con').qrcode({
                    render: "canvas",
                    text: row.signInUrl ,
                    width: 300,
                    height: 300
                })
            })
        },
        downloadQRCodeImage () {
            domtoimage.toJpeg($('.qrcode-container').get(0), { quality: 1 })
            .then(function (dataUrl) {
                var link = document.createElement('a');
                link.download = '签到码.jpeg';
                link.href = dataUrl;
                link.click();
            });
        },
        exportPeople (wid) {
            window.open(api.exportExcel + '?wid=' + wid)
        },
        getJoinPeoples () {
            util.httpPost(api.GET_JOIN_PEOPLES, {activityId: this.currentJoinActivityId, pageSize: this.joinPeoPageSize, pageNumber: this.joinPeoPageNum}, util.handler.DATAS).then((data) => {
                this.joinTotalSize = data.totalSize
                let datas = data.rows
                for (let i = 0; i< datas.length ; i++) {
                    datas[i].signInStatusStr = datas[i].signInStatus ? '是' : '否'
                    if (!datas[i].signInTime) {
                        datas[i].signInTime = '/'
                    }
                    if (!datas[i].major) {
                        datas[i].major = '/'
                    }
                }
                this.joinPeosDatas = datas
        }).catch((res) => {
                this.$Message.error('异常：' + res.message)
        })
        }
    },
    mounted () {
        this.queryActivities()
    },
    components: {
        breadcrumb
    }
}
</script>

<style scoped lang="less" rel="stylesheet/less">
    .activity-list{
        .content{
            margin: 20px;
            min-height: e("calc(100vh - 144px)");
            background: #FFFFFF;
            padding: 26px 24px 26px 24px;
            box-shadow: 0 0 12px 0 rgba(132,134,219,0.16);
            border-radius: 2px;
            .title{
                font-size: 16px;
                color: #464C5B;
                letter-spacing: 0;
                line-height: 16px;
                font-weight: 500;
            }
            .search-input{
                position: relative;
                width: 480px;
                height: 32px;
                margin-top: 26px;
                box-sizing: border-box;
                font-size: 0px;
                background: #FFFFFF;
                .search-content{
                    width: 396px;
                    height: 100%;
                    font-size: 14px;
                    padding-left: 30px;
                    border: 1px solid #D7DDE4;
                    border-radius: 4px 0 0 4px;
                    &:focus{
                        border: 1px solid #2D8CF0;
                        outline: none;
                    }
                }
                .search-icon{
                    position: absolute;
                    top: 6px;
                    left: 8px;
                    color: #80848F;

                }
                .search-btn{
                    position: absolute;
                    width: 80px;
                }
            }
            .screen{
                margin-top: 20px;
                ul{
                    li{
                        display: inline-block;
                        margin-right: 8px;
                        font-size: 12px;
                        color: #747986;
                        letter-spacing: 0;
                        line-height: 12px;
                        padding: 6px;
                        cursor: pointer;
                    }
                    .screen-selected{
                        background: #2D8CF0;
                        border-radius: 4px;
                        color: #fff;
                    }
                }
            }
            .add-new-btn{
                width: 88px;
                height: 32px;
                margin-top: 22px;
            }
            .activity-data-list {
                position: relative;
                margin-top: 20px;

            }
            .bottom-page {
                position: relative;
                margin-top: 20px;
                text-align: left;
            }
        }
    }

</style>

<style lang="less" rel="stylesheet/less">
    .activity-list{
        .content{
            .activity-data-list {
                .ivu-table-cell{
                    padding-left: 11px;
                    padding-right: 11px;
                }
                .activity-status-green{
                    background-color:#EAF9F2;
                }
                .activity-status-blue{
                    background-color:#EDF5FE;
                }
            }
        }
    }
    .ivu-modal{
        .ivu-modal-header{
            border-bottom: none;
        }
        .ivu-modal-footer{
            border-top: none;
        }
        .people-bottom-page{
            margin-top: 20px;
        }
        .people-data-list{
            margin-top: 20px;
        }
    }
</style>
