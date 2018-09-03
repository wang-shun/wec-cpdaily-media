<template>
    <div class="fans-con">
<!--         <Input v-model="keyword" class="btn-search" placeholder="输入学生的姓名、学号">
            <Button slot="append" type="primary">搜索</Button>
        </Input> -->
        <search-input @search="search" style="width: 450px;margin-top: 26px;" placeholder="请输入姓名、学号/工号" v-model="options.queryParams.name"></search-input>
        <div class="bh-mv-16">
            <UTable
                ref="table"
                :columns="columns"
                :options="options"
                @on-selection-change="selectionChange">
<!--                 @on-select="selectRow"
                @on-select-cancel="unselectRow"> -->
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
            data: [{
                userCode: 'asd',
                userName: '学校',
                userGender: 'asd',
                userGrade: 'asd'
            }],
            options: {
                url: api.orgUsers,
                queryParams: {
                    groupId: 'fans',
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
                }
            ],
            allCheckedFanses: []
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
            this.$refs.table.reload(this.options.queryParams)
        },
        selectionChange (rows) {
            this.allCheckedFanses = rows
            this.$emit('selected-fanses-changed', this.allCheckedFanses)
        },
        deleteCheckedUser (dUser) {
            this.allCheckedFanses.forEach((user, index) => {
                if (user.userId === dUser.userId) {
                    this.allCheckedFanses.splice(index, 1)
                }
            })
            this.$refs.table.refreshTableChecked((row) => {
                return this.allCheckedFanses.some((fans) => { return fans.userId === row.userId })
            })
        },
        deleteAllCheckedUsers () {
            this.allCheckedFanses = []
            this.$refs.table.refreshTableChecked((row) => {
                return false
            })
            this.$emit('selected-fanses-changed', this.allCheckedFanses)
        },
        initCheckedUsers (users) {
            this.allCheckedFanses = users
            this.$emit('selected-fanses-changed', this.allCheckedFanses)
        }
    },
    mounted () {
    }
}
</script>

<style scoped lang="less">
    .fans-con {
        padding: 0 20px;
    }
</style>
