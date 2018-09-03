<template>
    <div class="main-body">
        <breadcrumb :items="bcs"></breadcrumb>
        <div class="body-con">
            <div class="title-area bh-pv-32 bh-ph-16">
                <Input v-model="groupName" class="btn-search" v-if="!editMode" placeholder="请输入用户组名称" style="width: 400px;"></Input>
                <div v-if="editMode" class="bh-pl-8" style="font-size: 16px;font-weight: bold;">{{groupName}}</div>
            </div>
            <div class="new-group-body">
                <div class="panel-users bh-pv-16">
                    <Tabs v-model="tab">
                        <TabPane label="我的粉丝" name="fans"></TabPane>
                        <TabPane v-if="viewOrgs" label="组织架构" name="org"></TabPane>
                    </Tabs>
                    <org-users
                        v-if="viewOrgs"
                        ref="orgusers"
                        class="user-body"
                        v-show="tab === 'org'"
                        @checked-orgs-changed="checkedOrgsChanged"
                        @selected-users-changed="selectedUsersChange"></org-users>
                    <user-fans
                        ref="userfans"
                        class="user-body"
                        v-show="tab === 'fans'"
                        @selected-fanses-changed="selectedFansesChange"></user-fans>
                </div>
                <div class="panel-selected">
                    <selected-panel
                        ref="selectedpanel"
                        :selected-orgs="selectedOrgs"
                        :selected-users="selectedUsers"
                        :selected-fanses="selectedfanses"
                        @delete-user="deleteUser"
                        @delete-org="deleteOrg"
                        @delete-all-users="deleteAllCheckedUsers"></selected-panel>
                    <div class="btn-add-con">
                        <Button type="primary" v-if="!editMode" @click="Save" :loading="loading">新增</Button>
                        <Button type="primary" v-if="editMode" @click="Save" :loading="loading">保存</Button>
                        <Button @click="cancelSave">取消</Button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import breadcrumb from 'com/breadcrumb.vue'
import util from 'conf/util.js'
import api from 'conf/api'
import orgUsers from './org-users/orgUsers'
import userFans from './userFans'
import selectedPanel from './selectedPanel'
export default {
    data () {
        return {
            groupName: '',
            bcs: [{
                path: '/home/group',
                name: '用户组管理'
            }, {
                // path: '/home/newgroup',
                name: '新建用户组'
            }],
            tab: 'fans',
            selectedOrgs: [],
            selectedUsers: [],
            selectedfanses: [],
            editMode: false,
            groupId: '',
            viewOrgs: false,
            loading: false
        }
    },
    components: {
        breadcrumb,
        orgUsers,
        userFans,
        selectedPanel
    },
    computed: {
        ...Vuex.mapState({
            index: state => state.index
        })
    },
    methods: {
        deleteOrg (org) {
            if (this.$refs.orgusers) {
                this.$refs.orgusers.$refs.orgtree.deleteCheckedOrg(org)
            }
        },
        deleteAllCheckedUsers () {
            if (this.$refs.orgusers) {
                this.$refs.orgusers.$refs.usertable.deleteAllCheckedUsers()
                this.$refs.orgusers.$refs.orgtree.deleteAllCheckedOrgs()
            }
            this.$refs.userfans.deleteAllCheckedUsers()
        },
        deleteUser (user) {
            if (this.$refs.orgusers) {
                this.$refs.orgusers.$refs.usertable.deleteCheckedUser(user)
                this.$refs.orgusers.$refs.usertable.deleteCheckedUser(user)
            }
            this.$refs.userfans.deleteCheckedUser(user)
        },
        selectedUsersChange (users) {
            this.selectedUsers = users
        },
        selectedFansesChange (fanses) {
            this.selectedfanses = fanses
        },
        checkedOrgsChanged (orgs) {
            this.selectedOrgs = orgs
        },
        delOrg (index) {
            this.selectedOrgs.splice(index, 1)
            // 发送事件
        },
        delUser (index) {
            this.selectedUsers.splice(index, 1)
            // 发送事件
        },
        cancelSave () {
            sessionStorage.setItem('eidtGroup', '')
            this.$router.push('/home/group')
        },
        Save () {
            if (!this.groupName) {
                this.$Message.error('请输入用户组名称')
                return
            }
            if (this.groupName.length > 50) {
                this.$Message.error('用户组名称不能超过50个字')
                return
            }
            if (/\s/.test(this.groupName)) {
                this.$Message.error('用户组名称不能包含空格')
                return
            }
            var users = this.$refs.selectedpanel.GetSelectedUsers()
            var orgs = this.$refs.selectedpanel.GetSelectedOrgs()
            var pms = {
                groupId: this.groupId,
                groupName: this.groupName,
                userIds: users.map((user) => { return user.userId }),
                groups: orgs.map((org) => { return { groupId: org.id, groupName: org.title, groupType: org.path.indexOf('老师') > -1 ? 2 : 1 } })
            }
            this.loading = true
            if (this.editMode) {
                util.httpPost(api.editGroup, pms).then((res) => {
                    if (res.code !== '0') {
                        throw res
                    }
                    this.$Message.success('添加成功')
                    this.loading = false
                }).catch((err) => {
                    console.log(err)
                    this.$Message.error('添加失败：' + err.errMsg)
                    this.loading = false
                })
            } else {
                util.httpPost(api.createGroup, pms).then((res) => {
                    if (res.code !== '0') {
                        throw res
                    }
                    this.$Message.success('用户组创建成功')
                    this.loading = false
                    this.$router.push('/home/group')
                }).catch((err) => {
                    console.log(err)
                    this.$Message.error('用户组创建失败：' + err.errMsg)
                    this.loading = false
                })
            }
        },
        checkCanViewOrgs () {
            if (sessionStorage.getItem('canSeeOrgStructure') && sessionStorage.getItem('canSeeOrgStructure') === 'true') {
                this.viewOrgs = true
            }
            // util.httpGet(api.canViewOrgs).then((res) => {
            //     if (res.code === '0' && res.data === true) {
            //         this.viewOrgs = true
            //     } else {
            //         this.$Message.error('判断组织架构可见异常：' + err.errMsg)
            //     }
            // }).catch((err) => {
            //     this.$Message.error('判断组织架构可见异常：' + err.errMsg)
            // })
        }
    },
    mounted () {
        this.checkCanViewOrgs()
        var editGroupStr = sessionStorage.getItem('eidtGroup')
        // 状态 --- 添加组员
        if (editGroupStr) { 
            var editGroup = JSON.parse(editGroupStr)
            this.editMode = true
            this.groupId = editGroup.groupId
            this.groupName = editGroup.groupName
            this.bcs[1].name = '添加组员'

            // let pms = {
            //     groupId: this.groupId,
            //     name: '',
            //     optType: '0',
            //     pageNumber: '1',
            //     pageSize: '100000000',
            // }
            // util.httpGetQuery(api.orgUsers, pms).then((res) => {
            //     res = res.data
            //     if (res.errCode === '0') {
            //         this.$refs.userfans.initCheckedUsers(res.data.rows)
            //     } else {
            //         this.$Message.error('获取用户异常：' + res.errMsg)
            //     }
            // }).catch((err) => {
            //     this.$Message.error('获取用户异常：' + err.errMsg)
            // })
        }
    }
}
</script>

<style scoped lang="less">
    .main-body{
        .body-con{
            margin: 20px;
            min-height: e("calc(100vh - 144px)");
            background: #FFFFFF;
            box-shadow: 0 0 12px 0 rgba(132,134,219,0.16);
            border-radius: 2px;
            display: flex;
            flex-direction: column;
        }
    }
    .title-area {
        border-bottom: 1px solid #E3E8EE;
    }
    .new-group-body {
        flex: 1;
        display: flex;
        .panel-users {
            padding-left: 20px;
            width: 900px;
            min-width: 900px;
            border-right: 1px solid #E3E8EE;
            display: flex;
            flex-direction: column;
            .user-body {
                flex: 1;
            }
        }
        .panel-selected {
            position: relative;
            flex: 1;
            padding: 0 20px;
            .users-con {
                .user-item {
                    display: inline-block;
                    border: 1px solid #DDDEE1;
                    padding: 0.1em 0.5em;
                    color: #495060;
                    // border-radius: 1em;
                    span {
                        cursor: pointer;
                    }
                }
            }
            .btn-add-con {
                position: absolute;
                bottom: 20px;
                height: 30px;
                width: 100%;
                text-align: center;
            }
        }
    }
</style>
<style type="text/css">
  .cell-online {
    background-color: #DFF2D4 !important;
  }
  .cell-offline {
    background-color: #FFEACC !important;
  }
</style>
