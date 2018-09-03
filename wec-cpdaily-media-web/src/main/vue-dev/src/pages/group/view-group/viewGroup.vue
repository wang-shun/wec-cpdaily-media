<template>
    <div class="main-body">
        <breadcrumb :items="bcs"></breadcrumb>
        <div class="body-con">
            <div v-if="!nameEditing" class="group-header bh-pv-16 bh-ph-16">
                {{groupName}}
                <span @click="editGroupName"><Icon type="edit"></Icon></span>
            </div>
            <div v-if="nameEditing" class="group-header bh-pv-16 bh-ph-16">
                <Input v-model="newGroupName" placeholder="请输入用户组名称" style="width: 400px;"></Input>
                <Button type="primary" @click="saveGroupName">保存</Button>
            </div>
            <div class="group-body">
                <div class="user-list">
                    <div class="search-area">
                        <search-input style="width: 450px;margin-top: 26px;" @search="search" placeholder="请输入姓名、学号/工号" v-model="options.queryParams.name"></search-input>
                        <div class="btn-area">
                            <Button type="primary" @click="addUser">添加人员</Button>
                            <Button @click="delUsers">批量移除分组</Button>
                        </div>
                    </div>
                    <div class="bh-mv-16">
                        <UTable ref="table" :columns="columns" :options="options" :lazyLoad="true"></UTable>
                    </div>
                </div>
            </div>
        </div>
        <Modal
            v-model="modalDel"
            title="警告"
            @on-ok="delOk"
            @on-cancel="cancelDel">
            <p>{{delTip}}</p>
        </Modal>
    </div>
</template>

<script>
import breadcrumb from 'com/breadcrumb.vue'
import UTable from 'com/UTable'
import searchInput from 'com/searchInput'
import api from 'conf/api'
import util from 'conf/util'

export default {
    data () {
        return {
            bcs: [{
                path: '/home/group',
                name: '用户组管理'
            }, {
                // path: '/home/viewgroup',
                name: '查看用户组'
            }],
            data: [{
                userCode: 'asd',
                userName: '学校',
                userGender: 'asd',
                userGrade: 'asd'
            }],
            groupName: '水电费公司的风格',
            groupId: '',
            keyword: '',
            selectedUsers: [{
                userCode: '001111',
                userName: '晨晨'
            }],
            options: {
                url: api.orgUsers,
                queryParams: {
                    groupId: '',
                    optType: 0,
                    name: ''
                }
            },
            columns: [
                {
                    type: 'selection',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '学号',
                    key: 'openId'
                },
                {
                    title: '姓名',
                    key: 'userName'
                },
                {
                    title: '性别',
                    key: 'gender',
                    render: (h, params) => {
                        return h('div', params.row.gender === 'MALE' ? '男' : '女')
                    }
                },
                {
                    title: '类别',
                    key: 'userType',
                    render: (h, params) => {
                        return h('div', params.row.userType === 'TEACHER' ? '教师' : '学生')
                    }
                },
                {
                    title: '班级',
                    key: 'className'
                },
                {
                    title: '部门',
                    key: 'departName'
                },
                {
                    title: '操作',
                    key: 'action',
                    width: 330,
                    align: 'center',
                    render: (h, params) => {
                        return h('div', [
                            h('a', {
                                style: {
                                    marginRight: '5px'
                                },
                                on: {
                                    click: () => {
                                        this.delUser(params.row)
                                    }
                                }
                            }, '移出分组')
                        ])
                    }
                }
            ],
            currentUser: null,
            modalDel: false,
            delTip: '确认移除此用户吗？',
            userIds: [],
            nameEditing: false,
            newGroupName: ''
        }
    },
    methods: {
        search () {
            this.$refs.table.reload(this.options.queryParams)
        },
        addUser () {
            sessionStorage.setItem('eidtGroup', JSON.stringify({groupId: this.groupId, groupName: this.groupName}))
            this.$router.push('/home/newgroup')
        },
        delUsers () {
            var rows = this.$refs.table.getSelectedRows()
            if (!rows || rows.length < 1) {
                this.$Message.error('请选择要删除的用户')
                return
            }
            this.delTip = '确认移除已选用户吗？'
            this.userIds = rows.map((row) => {
                return row.userId
            })
            this.modalDel = true
        },
        delUser (row) {
            this.delTip = '确认移除此用户吗？'
            this.userIds = [row.userId]
            this.modalDel = true
        },
        delOk () {
            var pms = {
                groupId: this.groupId,
                userIds: this.userIds
            }
            util.httpPost(api.delMembers, pms, util.handler.CODE).then((res) => {
                this.$Message.success('移除成功')
                this.$refs['table'].reload()
            }).catch((res) => {
                this.$Message.error('异常：' + res.message)
            })
        },
        editGroupName () {
            this.newGroupName = this.groupName
            this.nameEditing = true
        },
        saveGroupName () {
            if (!this.newGroupName) {
                this.$Message.error('请输入用户组名称')
                return
            }
            if (this.newGroupName.length > 50) {
                this.$Message.error('用户组名称不能超过50个字')
                return
            }
            if (/\s/.test(this.newGroupName)) {
                this.$Message.error('用户组名称不能包含空格')
                return
            }
            var pms = {
                groupId: this.groupId,
                groupName: this.newGroupName
            }
            util.httpPost(api.editGroupName, pms, util.handler.CODE).then(() => {
                this.$Message.success('修改成功')
                this.groupName = this.newGroupName
                this.nameEditing = false
            }).catch((res) => {
                this.$Message.error('修改成功：' + res.message)
            })
        },
        // deleteUsers () {
        //     var pms = {
        //         groupId: this.groupId,
        //         userIds: this.userIds
        //     }
        //     util.httpPost(api.delMembers, pms).then((res) => {
        //         if (res.code === '0') {
        //             this.$Message.success('移除成功')
        //             this.$refs['table'].reload()
        //         } else {
        //             this.$Message.error('异常：' + res.message)
        //         }
        //     }).catch((res) => {
        //         this.$Message.error('异常：' + res.message)
        //     })
        // },
        cancelDel () {
            this.modalDel = false
        }
    },
    mounted () {
        var groupStr = sessionStorage.getItem('currentGroup')
        if (groupStr) {
            var group = JSON.parse(groupStr)
            this.groupName = group.groupName
            this.groupId = group.groupId
            this.options.queryParams.groupId = this.groupId
            this.$refs.table.reload(this.options.queryParams)
        }
    },
    components: {
        breadcrumb,
        UTable,
        searchInput
    }
}
</script>

<style scoped lang="less" rel="stylesheet/less">
    .main-body{
        .body-con{
            margin: 20px;
            min-height: e("calc(100vh - 144px)");
            background: #FFFFFF;
            box-shadow: 0 0 12px 0 rgba(132,134,219,0.16);
            border-radius: 2px;
            display: flex;
            flex-direction: column;
            .group-header {
                font-size: 16px;
                font-weight: bold;
                border-bottom: 1px solid #DDDEE1;
                i {
                    margin-left: 0.5em;
                    color: #2D8CF0;
                    cursor: pointer;
                }
            }
            .group-body {
                flex: 1;
                .user-list {
                    border-right: 1px solid #DDDEE1;
                    padding: 20px;
                    flex: 1;
                    .search-area {
                        display: flex;
                        .btn-search {
                            width: 400px;
                        }
                        .btn-area {
                            flex: 1;
                            text-align: right;
                        }
                    }
                }
            }
        }
    }
</style>
