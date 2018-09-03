<template xmlns:v-bind="http://www.w3.org/1999/xhtml">
    <div class="view-admins">
        <div class="content">
            <div class="bcs">
                <span class="home-title" @click="goBack">校园号管理</span>
                <span class="split-line">/</span>
                <span class="current-title">查看管理员</span>
            </div>
            <div class="btn-container" v-if="isOwner">
                <Button class="add-btn" type="primary" @click="showAdd">添加管理</Button>
                <Button class="delete-btn" type="primary" @click="deleteManagers">移出管理</Button>
            </div>
            <div class="admins-data-list">
                <Table border :columns="mediaListColumns" :data="mediaListData" @on-selection-change="onAdminSelectionChange"></Table>
            </div>
        </div>

        <!-- 添加管理员model-->
        <Modal v-model="addAdminShow" width="950" :closable="false" :styles="{top: '70px'}">
            <p slot="header" style="text-align:left">
                <span>添加管理员 <span style="color: #ccc;font-size: 13px;">只能从粉丝中添加哦～</span> </span>
            </p>
            <div class="add-admins-content">
                <div class="left">
                    <div class="search-input">
                        <input type="text" class="search-content" placeholder="请输入姓名或者学工号"  v-model.trim="KeyWord" @keyup.13="queryPerson">
                        <Icon class="search-icon" type="ios-search-strong" size="22"></Icon>
                        <Button class="search-btn" slot="append" type="primary" style="background: #2D8CF0;border-radius: 0 4px 4px 0;" @click="queryPerson">搜索</Button>
                    </div>
                    <div class="search-admins-data-list">
                        <Table v-if="isTableShow" height="400" border :columns="searchAdminsListColumns" :data="searchAdminsListData" @on-selection-change="onSelectionChange"
                        :no-data-text="emptyShowTxt"></Table>
                    </div>
                    <div class="bottom-page">
                        <Page :total="totalSize" :current="pageNum" show-elevator show-sizer placement="top" :page-size="pageSize"
                              @on-page-size-change="onPageSizeChange" @on-change="onChange"></Page>
                    </div>
                </div>
                <div class="middle">
                </div>
                <div class="right">
                    <div class="top">
                        <span class="title">已选择:</span>
                        <div class="clear-btn" @click="clearSelected">
                            <Icon type="ios-trash"></Icon>
                            <span>清空已选</span>
                        </div>
                    </div>
                    <div class="right-content">
                        <ul>
                            <li v-for="(item,index) in copyMediaListData" v-bind:key="index">
                                <Icon type="person"></Icon>
                                <span>{{item.userName}}</span>
                                <div v-if="item.isNewAdd" class="delete-btn" @click="deleteManager(item)">
                                    <Icon class="delete-icon" type="close-round"></Icon>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="bottom">
                        <ButtonGroup>
                            <Button type="primary" @click="addManager">添加</Button>
                            <Button type="ghost" @click="closeAdd">取消</Button>
                        </ButtonGroup>
                    </div>
                </div>
            </div>
            <div slot="footer"></div>
        </Modal>
    </div>
</template>

<script>
import util from 'conf/util'
import api from 'conf/api'
export default {
    data () {
        return {
            isTableShow: true,
            mediaId: '',
            isOwner: false,
            totalSize: 0,
            pageNum: 1,
            pageSize: 10,
            owner: {},
            KeyWord: '',
            addAdminShow: false,
            emptyShowTxt: '请输入姓名或者学工号搜索你要添加的管理员',
            mediaListColumns: [
                {
                    type: 'selection',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '姓名',
                    key: 'name',
                    render: (h, params) => {
                        return h('div', {
                            style: {
                                'line-height': '20px'
                            }
                        }, [
                            h('span', {
                            }, params.row.name),
                            h('span', {
                                style: {
                                    padding: '3px',
                                    color: '#ffffff',
                                    background: '#2D8CF0',
                                    'border-radius': '4px',
                                    'margin-left': '4px',
                                    display: params.row.manageType === 'OWNER' ? 'inline-block' : 'none'
                                }
                            }, '拥有者')
                        ])
                    }
                },
                {
                    title: '性别',
                    key: 'sex'
                },
                {
                    title: '学工号',
                    key: 'openId'
                }
            ],
            mediaListData: [],
            selectedMediaListData: [],
            copyMediaListData: [],
            searchAdminsListColumns: [
                {
                    type: 'selection',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '学工号',
                    key: 'openId',
                    width: 140,
                    align: 'center',
                    ellipsis: true,
                    render: (h, params) => {
                        return h('span',{
                            attrs:{
                                title: params.row.openId
                            }
                        }, params.row.openId);
                    }
                },
                {
                    title: '姓名',
                    key: 'userName',
                    width: 130,
                    align: 'center'
                },
                {
                    title: '性别',
                    key: 'sex',
                    width: 80,
                    align: 'center'
                },
                {
                    title: '学院',
                    key: 'departName',
                    width: 288,
                    align: 'center'
                }
            ],
            searchAdminsListData: []
        }
    },
    methods: {
        onPageSizeChange (size) {
            this.pageSize = size
            this.pageNum = 1
            this.searchAdminsListData = []
            this.queryPerson()
        },
        onChange (size) {
            this.pageNum = size
            this.searchAdminsListData = []
            this.queryPerson()
        },
        showAdd () {
            this.addAdminShow = true
            this.searchAdminsListData = []
            this.KeyWord = ''
            this.queryPerson()
            this.getAdmins()
        },
        closeAdd () {
            this.addAdminShow = false
        },
        getAdmins () {
            this.copyMediaListData = []
            util.httpGetQuery(api.QUERY_ADMINS, {mediaId: this.mediaId}, util.handler.CODE).then((res) => {
                if (res && res.code == '0') {
                    let datas = res.datas
                    for (let i = 0; i < datas.length; i++) {
                        datas[i].sex = this.getShowSex(datas[i].gender)
                        datas[i].userName = datas[i].name
                        if (datas[i].manageType === 'MANAGE') {
                            // 非owner的成员才能增删
                            this.copyMediaListData.push(datas[i])
                        } else if (datas[i].manageType === 'OWNER') {
                            this.owner = datas[i]
                            datas[i]._disabled = true
                        }
                    }
                    this.mediaListData = datas
                }
            }).catch((err) => {
                
            })
        },
        getShowSex (gender) {
            if (!gender || gender === 'UNKNOWN') {
                return '未知'
            }
            if (gender === 'MALE') {
                return '男'
            }
            if (gender === 'FEMALE') {
                return '女'
            }
        },
        queryPerson () {
            let param = {
                mediaId: this.mediaId,
                name: this.KeyWord,
                pageNum: this.pageNum,
                pageSize: this.pageSize
            }
            util.httpGetQuery(api.GET_FANS, param, util.handler.CODE).then((res) => {
                if (res && res.code == '0') {
                    let datas = res.datas.rows
                    this.totalSize = res.datas.totalSize
                    this.searchAdminsListData = []
                    if (!datas) {
                        return
                    }
                    if (datas.length === 0) {
                        if (this.KeyWord === '') {
                            this.emptyShowTxt = '请输入姓名或者学工号搜索你要添加的管理员'
                        } else {
                            this.emptyShowTxt = '没有查询到数据'
                        }
                    }
                    for (let i = 0; i < datas.length; i++) {
                        datas[i].sex = this.getShowSex(datas[i].gender)
                        if (this.owner && this.owner.personId === datas[i].personId) {
                            datas[i]._disabled = true
                        } else {
                            for (let j = 0; j < this.copyMediaListData.length; j++) {
                                if (datas[i].personId === this.copyMediaListData[j].personId) {
                                    if (!this.copyMediaListData[j].isNewAdd) {
                                        datas[i]._disabled = true
                                    } else {
                                        datas[i]._checked = true
                                    }
                                }
                            }
                        }
                    }
                    this.searchAdminsListData = datas
                }
            }).catch((err) => {
                
            })
        },
        onSelectionChange (selection) {
            let isDel = false
            var delIndexs = []
            this.searchAdminsListData.forEach((item) => {
                this.copyMediaListData.forEach((mng, index) => {
                    if (item.personId === mng.personId && !item._disabled) {
                        delIndexs.push(index)
                    }
                })
            })
            if (delIndexs.length > 0) {
                isDel = true
                delIndexs = delIndexs.reverse()
                delIndexs.forEach((i) => {
                    this.copyMediaListData.splice(i, 1)
                })
            }
            // 这个用户集会变，所以不能直接赋值，而是应该做比较增量
            for (let i = 0; i < selection.length; i++) {
                if (!this.isInCopyList(selection[i].personId)) {
                    selection[i].isNewAdd = true
                    this.copyMediaListData.push(selection[i])
                }
            }

            if (!isDel) {
                this.searchAdminsListData.forEach((item) => {
                    if (this.isInCopyList(item.personId)) {
                        console.log('#####')
                        item._checked = true
                    } else {
                        item._checked = false
                    }
                })
                this.isTableShow = false
                this.$nextTick(() => {
                   this.isTableShow = true
                })
            }
            
        },
        isInCopyList (personId) {
            for (let i = 0; i < this.copyMediaListData.length; i++) {
                if (this.copyMediaListData[i].personId === personId) {
                    return true
                }
            }
            return false
        },
        onAdminSelectionChange (selection) {
            this.selectedMediaListData = selection
        },
        clearSelected () {
            for (let i = 0; i < this.searchAdminsListData.length; i++) {
                this.searchAdminsListData[i]._checked = false
            }
            this.copyMediaListData = this.copyMediaListData.filter(function (item) {
                return !item.isNewAdd
            })

        },
        addManager () {
            if (this.copyMediaListData.length === 0) {
                this.$Message.error('未选择任何人员！')
            } else {
                let personIds = []
                for (let i = 0; i < this.copyMediaListData.length; i++) {
                    if (this.copyMediaListData[i].userId) {
                        personIds.push(this.copyMediaListData[i].userId)
                    } 
                }
                // 发起添加请求
                let pms = {
                    mediaId: this.mediaId,
                    admins: personIds
                }
                util.httpPost(api.ADD_MANAGER, pms, util.handler.CODE).then((res) => {
                    if (res && res.code == '0') {
                        this.addAdminShow = false
                        this.getAdmins()
                    }
                }).catch((res) => {
                    this.$Message.error('异常：' + res.message)
                })
            }
        },
        deleteManager (item) {
            let personIds = []
            personIds.push(item.personId)
            for (let j = 0; j < personIds.length; j++) {
                let index = this.indexOf(personIds[j])
                if (index > -1) {
                    this.copyMediaListData.splice(index, 1)
                }
            }
            if (this.addAdminShow) {
                this.queryPerson()
            }
        },
        deleteManagers () {
            let personIds = []
            for (let i = 0; i < this.selectedMediaListData.length; i++) {
                personIds.push(this.selectedMediaListData[i].pwid)
            }
            this.deleteManagerRequest(personIds)
        },
        deleteManagerRequest (personIds) {
            if (personIds.length === 0) {
                this.$Message.error('未选择任何管理员！')
            } else {
                util.httpPost(api.DELETE_MANAGER, {mediaId: this.mediaId, admins: personIds}, util.handler.CODE).then((res) => {
                       if (res && res.code == '0') {
                            this.selectedMediaListData = []
                            this.$Message.success('移出成功！')
                            this.getAdmins()
                            if (this.addAdminShow) {
                                this.queryPerson()
                            }
                            for (let j = 0; j < personIds.length; j++) {
                                let index = this.indexOf(personIds[j])
                                if (index > -1) {
                                    this.copyMediaListData.splice(index, 1)
                                }
                            }
                        }
                    }).catch((err) => {
                        
                    })
            }
        },
        indexOf (personId) {
            for (let i = 0; i < this.copyMediaListData.length; i++) {
                if (this.copyMediaListData[i].personId === personId) return i
            }
            return -1
        },
        goBack () {
            this.$router.go(-1)
        }
    },
    mounted () {
        this.getAdmins()
        this.queryPerson()
    },
    created () {
        this.mediaId = this.$route.query.mediaId
        this.isOwner = this.$route.query.owner
        if(!this.isOwner){
            this.mediaListColumns.splice(0,1)
        }
    }
}
</script>

<style scoped lang="less" rel="stylesheet/less">
    .view-admins{
        .content{
            margin: 20px;
            width: 1232px;
            padding: 26px 24px 26px 24px;
            box-sizing: border-box;
            min-height: e("calc(100vh - 134px)");
            background: #FFFFFF;
            box-shadow: 0 0 12px 0 rgba(132,134,219,0.16);
            border-radius: 2px;
            .bcs{
                .home-title{
                    color: #495060;
                    transition: color .2s ease-in-out;
                    font-size: 14px;
                    cursor: pointer;
                    &:hover{
                        color: #57a3f3;
                    }
                }
                .split-line{
                    margin: 0 8px;
                    color: #dddee1;
                }
                .current-title{
                    font-weight: 700;
                    color: #495060;
                    font-size: 14px;
                }
            }
            .btn-container{
                margin-top: 26px;
                width: 100%;
                .add-btn{
                    width: 100px;
                }
                .delete-btn{
                    float: right;
                    width: 100px;
                }
            }
            .admins-data-list{
                margin-top: 20px;
            }
        }
    }

    @media screen {
        /* 大于1300px的屏幕 */
        @media (min-width: 1280px){
            .view-admins{
                .content{
                    margin: 20px auto;
                }
            }
        }
    }
</style>

<style lang="less" rel="stylesheet/less">
    .ivu-modal{
        .ivu-modal-header{
            border-bottom: none;
        }
        .ivu-modal-footer{
            border-top: none;
        }
        .ivu-table-tip{
            font-size: 20px;
            color: #C5C5C5;
        }
        .add-admins-content{
            display: flex;
            min-height: 400px;
            overflow: auto;
            width: 100%;
            .left{
                width: 700px;
                .search-input{
                    position: relative;
                    width: 500px;
                    height: 32px;
                    box-sizing: border-box;
                    font-size: 0px;
                    background: #FFFFFF;
                    .search-content{
                        width: 420px;
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
                .search-admins-data-list{
                    margin-top: 20px;
                }
                .bottom-page{
                    margin-top: 8px;
                }
            }
            .middle{
                width: 20px;
                position: relative;
                &:after{
                    position: absolute;
                    content: '';
                    display: inline-block;
                    width: 1px;
                    height: 100%;
                    left: 10px;
                    background: #e9eaec;
                }
            }
            .right{
                position: relative;
                flex:1;
                .top{
                    .title{
                        font-size: 14px;
                        color: #464C5B;
                        letter-spacing: 0;
                        line-height: 16px;
                        font-weight: 500;
                    }
                    .clear-btn{
                        float: right;
                        display: inline-block;
                        color: #2D8CF0;
                        cursor: pointer;
                    }
                }
                .right-content{
                    margin-top: 34px;
                    ul{
                        display: flex;
                        flex-wrap: wrap;
                        margin-bottom: 50px;
                        li{
                            margin-bottom: 8px;
                            flex: none;
                            display: inline-block;
                            padding: 2px 8px 2px 8px;
                            border: 1px solid #D7DDE4;
                            border-radius: 4px;
                            margin-right: 8px;
                            .delete-btn{
                                display: inline-block;
                                cursor: pointer;
                            }
                        }
                    }
                }
                .bottom{
                    position: absolute;
                    width: 100%;
                    bottom: 0;
                    text-align: center;
                }
            }
        }
    }
</style>
