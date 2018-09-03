<template>
    <div class="fans-con">
        <search-input @search="search" style="width: 450px;margin-top: 26px;" placeholder="请输入姓名、学号/工号" v-model="options.queryParams.name"></search-input>
        <div class="bh-mv-16">
            <UTable ref="table" :columns="columns" :options="options"></UTable>
        </div>
        <Modal
            v-model="modalAdd"
            title="信息"
            @on-ok="addGroup"
            @on-cancel="cancelAddGroup">
            <Select v-model="groupId" style="width:200px">
                <Option v-for="item in groupList" :value="item.id" :key="item.id">{{ item.title }}</Option>
            </Select>
        </Modal>
    </div>
</template>
<script>
import api from 'conf/api'
import util from 'conf/util'
import UTable from '../components/UTable'
import searchInput from 'com/searchInput'
export default {
    data () {
        return {
            groupId: '',
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
                },
                {
                    title: '操作',
                    key: 'action',
                    align: 'center',
                    render: (h, params) => {
                        return h('div', [
                            h('a', {
                                style: {
                                    marginRight: '5px'
                                },
                                on: {
                                    click: () => {
                                        this.add2Group(params.row)
                                    }
                                }
                            }, '添加至分组')
                        ])
                    }
                }
            ],
            groupList: [],
            modalAdd: false,
            currentUser: null
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
        add2Group (row) {
            this.currentUser = row
            this.modalAdd = true
        },
        getGroups () {
            util.httpGet(api.customGroup, {}, util.handler.DATAS).then((data) => {
                data.children.splice(0, 1)
                this.groupList = data.children
            })
        },
        addGroup () {
            let pms = {
                groupIds: [this.groupId],
                userIds: [this.currentUser.userId]
            }
            util.httpPost(api.groupAddUsers, pms, util.handler.CODE).then((res) => {
                this.$Message.success('添加成功')
                this.$refs['table'].reload()
                this.$emit('fans-add-group')
            }).catch((res) => {
                this.$Message.error('异常：' + res.message)
            })
        },
        cancelAddGroup () {
            this.modalAdd = false
        }
    },
    mounted () {
        this.getGroups()
    }
}
</script>

<style scoped lang="less">
    .fans-con {
        padding: 0 20px;
    }
</style>
