<template>
    <article>
        <div class="btn-area">
            <div class="input-col">
                <search-input style="width: 450px;margin-top: 26px;" @search="search" placeholder="请输入用户组名称" v-model="options.queryParams.groupName"></search-input>
            </div>
            <div class="button-col">
                <Button type="primary" @click="addGroup">新增分组</Button>
            </div>
        </div>
        <div class="bh-mv-16">
            <UTable ref="table" :columns="columns" :options="options"></UTable>
        </div>
        <Modal
            v-model="modalDel"
            title="警告"
            @on-ok="delGroupOk"
            @on-cancel="cancelDelGroup">
            <p>确认删除此用户组？</p>
        </Modal>
    </article>
</template>
<script>
import api from 'conf/api'
import util from 'conf/util'
import UTable from '../components/UTable'
import searchInput from 'com/searchInput'
export default {
    data () {
        return {
            data: [{
                title: '拖拉机',
                count: 20
            }],
            options: {
                url: api.groupList,
                queryParams: {
                    groupName: ''
                }
            },
            columns: [
                {
                    title: '用户组名称',
                    key: 'groupName',
                    ellipsis: true,
                    render: (h, params) => {
                        return h('span', {
                            attrs: {
                                title: params.row.groupName
                            }
                        }, params.row.groupName)
                    }
                },
                {
                    title: '人数',
                    key: 'userCount'
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
                                        this.addUser(params.row)
                                    }
                                }
                            }, '添加人员'),
                            h('a', {
                                style: {
                                    marginRight: '5px'
                                },
                                on: {
                                    click: () => {
                                        this.viewGroup(params.row)
                                    }
                                }
                            }, '查看'),
                            h('a', {
                                style: {
                                    marginRight: '5px'
                                },
                                on: {
                                    click: () => {
                                        this.delGroup(params.row)
                                    }
                                }
                            }, '删除')
                        ])
                    }
                }
            ],
            loading: true,
            forstagePackages: [],
            frontParams: {appletId: '', token: ''},
            frontLoading: true,
            modalDel: false
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
        setDataStyle (rows) {
            rows.forEach((row) => {
                row.cellClassName = {
                    appletStatus: row.appletStatus ? 'cell-online' : 'cell-offline'
                }
            })
            return rows
        },
        resetLoading () {
            this.loading = false
            this.$nextTick(() => {
                this.loading = true
            })
        },
        addGroup () {
            sessionStorage.setItem('eidtGroup', '')
            this.$router.push('/home/newgroup')
        },
        addUser (row) {
            sessionStorage.setItem('eidtGroup', JSON.stringify({groupId: row.groupId, groupName: row.groupName}))
            this.$router.push('/home/newgroup')
        },
        viewGroup (row) {
            sessionStorage.setItem('currentGroup', JSON.stringify({groupId: row.groupId, groupName: row.groupName}))
            this.$router.push('/home/viewgroup')
        },
        delGroup (row) {
            this.currentGroup = row
            this.modalDel = true
        },
        delGroupOk () {
            // let params = {}
            util.httpPost(api.delGroup + '/' + this.currentGroup.groupId, {}, util.handler.CODE).then((res) => {
                this.$Message.success('删除成功')
                this.$refs['table'].reload()
            }).catch((res) => {
                this.$Message.error('异常：' + res.errMsg)
            })
        },
        cancelDelGroup () {
            this.modalDel = false
        }
    },
    mounted () {
    }
}
</script>

<style scoped lang="less">
    .btn-area {
        display: flex;
        .input-col {
            flex: 1;
            .btn-search {
                width: 480px;
            }
        }
        .button-col {
            width: 82px;
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
