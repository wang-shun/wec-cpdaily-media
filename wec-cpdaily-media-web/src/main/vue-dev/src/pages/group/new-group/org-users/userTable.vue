<template>
    <div class="tab-con">
        <div class="list-header bh-mv-16">
            {{groupName}}
        </div>
        <search-input @search="search" style="width: 450px;margin-top: 26px;" placeholder="请输入姓名、学号/工号" v-model="options.queryParams.name"></search-input>
        <div class="bh-mv-16">
            <UTable
                ref="table"
                :columns="columns"
                :options="options"
                :data-optimize="dataOptimize"
                :lazyLoad="true"
                @on-select-cancel="selectCancel"
                @on-selection-change="selectionChange">
            </UTable>
        </div>
    </div>
</template>
<script>
import api from 'conf/api'
import UTable from 'com/UTable'
import searchInput from 'com/searchInput'
export default {
    data () {
        return {
            keyword: '',
            groupName: '',
            options: {
                url: api.orgUsers,
                queryParams: {
                    groupId: '',
                    optType: 3,
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
                    key: 'openId',
                    ellipsis: true,
                    render: (h, params) => {
                        return h('span', {
                            attrs: {
                                title: params.row.openId
                            }
                        }, params.row.openId)
                    }
                },
                {
                    title: '姓名',
                    key: 'userName'
                },
                {
                    title: '性别',
                    key: 'gender',
                    width: 65,
                    render: (h, params) => {
                        return h('div', params.row.gender === 'MALE' ? '男' : '女')
                    }
                },
                {
                    title: '类别',
                    width: 65,
                    key: 'userType',
                    render: (h, params) => {
                        return h('div', params.row.userType === 'TEACHER' ? '教师' : '学生')
                    }
                },
                {
                    title: '班级',
                    key: 'className',
                    ellipsis: true,
                    render: (h, params) => {
                        return h('span', {
                            attrs: {
                                title: params.row.className
                            }
                        }, params.row.className)
                    }
                },
                {
                    title: '部门',
                    key: 'departName',
                    ellipsis: true,
                    render: (h, params) => {
                        return h('span', {
                            attrs: {
                                title: params.row.departName
                            }
                        }, params.row.departName)
                    }
                }
            ],
            obj: {},
            allCheckedUsers: []
        }
    },
    props: {
        parent: {
            type: String,
            default: ''
        },
        initCheckedUsers: {
            type: Array,
            default: () => ([])
        }
    },
    components: {
        UTable,
        searchInput
    },
    computed: {
        ...Vuex.mapState({
            index: state => state.index
        }),
        calcUpload () {
            return api.forestageUpload
        }
    },
    methods: {
        search () {
            var obj = this.obj
            if (obj && obj.type === 'org') {
                this.options.queryParams.groupId = obj.org.id
                this.options.queryParams.optType = obj.org.path.indexOf('老师') > -1 ? 2 : 1
            } else if (obj && obj.type === 'group') {
                this.options.queryParams.groupId = obj.group.id
                this.options.queryParams.optType = 0
            } else {
                // 未选择节点，当做粉丝查询
                this.options.queryParams.groupId = 'fans'
                this.options.queryParams.optType = 0
            }
            var queryParams = $.extend(true, {} , this.options.queryParams)
            queryParams.name = encodeURIComponent(queryParams.name)
            this.$refs.table.reload(queryParams)
        },
        selectionChange (rows) {
            rows.forEach((row) => {
                if (!this.allCheckedUsers.some((ck_user) => { return ck_user.userId === row.userId })) {
                    this.allCheckedUsers.push(row)
                }
            })
            this.$emit('selected-users-changed', this.allCheckedUsers)
        },
        selectCancel (selection, row) {
            var dIndex
            this.allCheckedUsers.forEach((user, index) => {
                if (user.userId === row.userId) {
                    dIndex = index
                }
            })
            if (dIndex !== undefined) {
                this.allCheckedUsers.splice(dIndex, 1)
                this.$emit('selected-users-changed', this.allCheckedUsers)
            }
        },
        freshUserTable (obj) {
            this.obj = obj
            if (obj.type === 'org') {
                this.options.queryParams.groupId = obj.org.id
                this.options.queryParams.optType = obj.org.path.indexOf('老师') > -1 ? 2 : 1
                this.groupName = obj.org.title
                this.checked = obj.org.checked
            } else {
                this.options.queryParams.groupId = obj.group.id
                this.options.queryParams.optType = 0
                this.groupName = obj.group.title
                this.checked = obj.group.checked
            }
            var queryParams = $.extend(true, {} , this.options.queryParams)
            queryParams.name = encodeURIComponent(queryParams.name)
            this.$refs.table.reload(queryParams)
        },
        freshState () {
            if ((this.obj.org && this.obj.org.checked) || (this.obj.group && this.obj.group.checked)) {
                this.$refs.table.refreshTableChecked((row) => { return true })
                this.$refs.table.refreshTableCheckDisabled((row) => { return true })
            } else if ((this.obj.org && this.obj.org.checked === false) || (this.obj.group && this.obj.group.checked === false)) {
                this.$refs.table.refreshTableChecked((row) => { return false })
                this.$refs.table.refreshTableCheckDisabled((row) => { return false })
            }
            //  else {
            //     this.$refs.table.refreshTableChecked((row) => { return false })
            //     this.$refs.table.refreshTableCheckDisabled((row) => { return false })
            // }
        },
        dataOptimize (rows) {
            if (this.checked === true) {
                rows.forEach((row) => {
                    row._checked = true
                    row._disabled = true
                })
            } else {
                rows.forEach((row) => {
                    if (this.allCheckedUsers.some((user) => { return user.userId === row.userId })) {
                        row._checked = true
                    }
                })
            }
            return rows
        },
        deleteCheckedUser (dUser) {
            var dUserId = dUser.userId
            this.allCheckedUsers.forEach((user, index) => {
                if (user.userId === dUserId) {
                    this.allCheckedUsers.splice(index, 1)
                }
            })
            this.$refs.table.refreshTableChecked((row) => {
                return this.allCheckedUsers.some((user) => { return user.userId === row.userId })
            })
        },
        deleteAllCheckedUsers () {
            this.allCheckedUsers = []
            this.$refs.table.refreshTableChecked((row) => {
                return false
            })
            this.$emit('selected-users-changed', this.allCheckedUsers)
        }
    },
    mounted () {
        if (this.parent === 'message') {
            this.groupName = '关注我的用户'
        }
        if (this.initCheckedUsers.length > 0) {
            this.allCheckedUsers = this.initCheckedUsers
            this.$emit('selected-users-changed', this.allCheckedUsers)
        }
    }
}
</script>

<style scoped lang="less">
    .tab-con {
        padding: 0 10px;
    }
    .list-header {
        color: #495060;
        font-weight: bold;
        font-size: 16px;
        height: 24px;
    }
</style>
