<template>
    <div class="new-group-body">
        <div class="panel-group">
            <Tabs v-model="tab">
                <TabPane label="自定义分组" name="group"></TabPane>
                <TabPane v-if="viewOrgs" label="组织架构" name="org"></TabPane>
            </Tabs>
            <org-tree
                v-if="viewOrgs"
                class="user-body"
                ref="orgtree"
                v-show="tab === 'org'"
                :init-checked-groups="messageInfo.sendUserGroups"
                @checked-orgs-changed="checkedOrgsChanged"
                @org-selected="orgSelected"></org-tree>
            <custom-group
                class="user-body"
                ref="customgroup"
                v-show="tab === 'group'"
                :init-checked-groups="messageInfo.sendUserGroups"
                @checked-groups-changed="checkedGroupsChanged"
                @group-selected="groupSelected"></custom-group>
        </div>
        <div class="panel-users">
            <user-table ref="usertable" parent="message" @selected-users-changed="usersChanged" :init-checked-users="messageInfo.sendUsers"></user-table>
        </div>
        <div class="panel-selected">
            <selected-panel
                ref="selectedpanel"
                :selected-orgs="selectedOrgs"
                :selected-groups="selectedGroups"
                :selected-users="selectedUsers"
                @delete-user="deleteUser"
                @delete-org="deleteOrg"
                @delete-all-users="deleteAllCheckedUsers"></selected-panel>
            <div class="btn-add-con">
                <Button :loading="publishLoading" type="primary" @click="Publish">发　布</Button>
                <Button v-if="!publishLoading" @click="Save">保存</Button>
                <Button v-if="publishLoading" disabled>保存</Button>
                <div class="bh-mt-8">
                    <Button v-if="!publishLoading" @click="Prev">上一步</Button>
                    <Button v-if="!publishLoading" @click="Cancel">取消</Button>
                    <Button v-if="publishLoading" disabled>上一步</Button>
                    <Button v-if="publishLoading" disabled>取消</Button>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import breadcrumb from 'com/breadcrumb.vue'
// import * as service from 'services/openService.js'
// import api from 'conf/api'
import orgTree from '@/pages/group/new-group/org-users/orgTree'
import userTable from '@/pages/group/new-group/org-users/userTable'
import selectedPanel from '@/pages/group/new-group/selectedPanel'
import customGroup from './customGroup'
export default {
    data () {
        return {
            publishLoading: false,
            groupName: '',
            bcs: [{
                path: '/home/group',
                name: '用户组管理'
            }],
            tab: 'group',
            selectedOrgs: [],
            selectedGroups: [],
            selectedUsers: [],
            viewOrgs: false
        }
    },
    components: {
        breadcrumb,
        orgTree,
        userTable,
        customGroup,
        selectedPanel
    },
    computed: {
        ...Vuex.mapState({
            index: state => state.index
        })
    },
    props: {
        messageInfo: {
            type: Object,
            default: () => ({})
        }
    },
    methods: {
        checkedOrgsChanged (orgs) {
            this.selectedOrgs = orgs
            this.$refs.usertable.freshState()
        },
        orgSelected (org) {
            this.$refs.usertable.freshUserTable({type: 'org', org: org[0]})
        },
        checkedGroupsChanged (groups) {
            this.selectedGroups = groups
            this.$refs.usertable.freshState()
        },
        groupSelected (group) {
            this.$refs.usertable.freshUserTable({type: 'group', group: group[0]})
        },
        usersChanged (allCheckedUsers) {
            this.selectedUsers = allCheckedUsers
        },
        Publish () {
            var target = this.getTarget()
            if (target.groups.length < 1 && target.users.length < 1) {
                this.$Message.error('请选择发送对象')
                return
            }

            this.publishLoading = true
            // var pms = {
            //     users: this.$refs.selectedpanel.GetSelectedUsers().map((user) => { return { userId: user.userId } }),
            //     groups: this.$refs.selectedpanel.GetSelectedOrgs().map((org) => { return { groupId: org.id, groupName: org.title, groupType: org.path.indexOf('老师TEACHER') > -1 ? 2 : org.path.indexOf('学生STUDENT') > -1 ? 1 : 0 } })
            // }
            // sessionStorage.setItem('current-targets', JSON.stringify(pms))
            this.$emit('publish')
        },
        Save () {
            // var pms = {
            //     users: this.$refs.selectedpanel.GetSelectedUsers().map((user) => { return { userId: user.userId } }),
            //     groups: this.$refs.selectedpanel.GetSelectedOrgs().map((org) => { return { groupId: org.id, groupName: org.title, groupType: org.path.indexOf('老师') > -1 ? 2 : org.path.indexOf('学生') > -1 ? 1 : 0 } })
            // }
            // sessionStorage.setItem('current-targets', JSON.stringify(pms))
            this.$emit('save')
        },
        Prev () {
            this.$emit('prev')
        },
        Cancel () {
            sessionStorage.setItem('create-msg-type', '')
            this.$router.replace('/home/message')
        },
        deleteUser (user) {
            this.$refs.usertable.deleteCheckedUser(user)
        },
        deleteOrg (org) {
            if (this.$refs.orgtree) {
                this.$refs.orgtree.deleteCheckedOrg(org)
            }
            this.$refs.customgroup.deleteCheckedOrg(org)
            var index
            this.selectedGroups.forEach((group, i) => {
                if (org.id === group.id) {
                    index = i
                }
            })
            this.selectedGroups.splice(index, 1)
        },
        deleteAllCheckedUsers () {
            if (this.$refs.orgtree) {
                this.$refs.orgtree.deleteAllCheckedOrgs()
            }
            this.$refs.usertable.deleteAllCheckedUsers()
            this.$refs.customgroup.deleteAllCheckedOrgs()
        },
        getTarget () {
            let groupType = 0

            var pms = {
                users: this.$refs.selectedpanel.GetSelectedUsers().map((user) => { return { userId: user.userId } }),
                groups: this.$refs.selectedpanel.GetSelectedOrgs().map((org) => { return { groupId: org.id, groupName: org.title, groupType: org.path.indexOf('老师TEACHER') > -1 ? 2 : (org.path.indexOf('学生STUDENT') > -1 || org.path.indexOf('新生NEW') > -1) ? 1 : 0 } })
            }
            return pms
        },
        checkCanViewOrgs () {
            if (sessionStorage.getItem('canSeeOrgStructure') && sessionStorage.getItem('canSeeOrgStructure') === 'true') {
                this.viewOrgs = true
            }
        }
    },
    mounted () {
        this.checkCanViewOrgs()
        var type = this.$route.params.type
        if (type && (type === 'edit') ) {
            var sendUsers = this.messageInfo.sendUsers
            this.$refs.usertable.allCheckedUsers = sendUsers
        }
    }
}
</script>

<style scoped lang="less">
    .new-group-body {
        flex: 1;
        display: flex;
        .panel-group {
            width: 280px;
            min-width: 280px;
            border-right: 1px solid #E3E8EE;
            display: flex;
            flex-direction: column;
            .user-body {
                flex: 1;
            }
        }
        .panel-users {
            // width: 520px;
            // min-width: 500px;
            flex: 1;
            border-right: 1px solid #E3E8EE;
            display: flex;
            flex-direction: column;
        }
        .panel-selected {
            position: relative;
            // flex: 1;
            width: 250px;
            min-width: 250px;
            padding: 0 20px;
            .sl-header {
                color: #495060;
                font-weight: bold;
                font-size: 16px;
            }
            .orgs-con {
                .org-item {
                    display: inline-block;
                    border: 1px solid #2D8CF0;
                    padding: 0.1em 0.5em;
                    background-color: #2D8CF0;
                    color: white;
                    // border-radius: 1em;
                    span {
                        cursor: pointer;
                    }
                }
            }
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
                height: 60px;
                width: 100%;
                text-align: center;
            }
        }
    }
</style>
