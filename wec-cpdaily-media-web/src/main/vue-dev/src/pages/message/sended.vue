<template>
    <article>
        <div class="bh-mv-16">
            <UTable ref="table" :columns="columns" :options="options"></UTable>
        </div>
    </article>
</template>
<script>
import api from 'conf/api'
import UTable from '../components/UTable'
import util from 'conf/util.js'

export default {
    data () {
        return {
            data: [{
                title: '你们好',
                sendUserGroup: '拖拉机',
                cTime: '2017-10-10 10:10',
                state: '发送中 10/100'
            }],
            options: {
                url: api.msgList,
                queryParams: {
                    msgStatus: 'SEND_IN,SEND_ERROR,SEND_END'
                }
            },
            columns: [
                {
                    title: '消息标题',
                    key: 'title',
                    ellipsis: true,
                    render: (h, params) => {
                        return h('span', {
                            attrs: {
                                title: params.row.title
                            }
                        }, params.row.title)
                    }
                },
                {
                    title: '推送对象',
                    key: 'sendUserGroup',
                    ellipsis: true,
                    render: (h, params) => {
                        return h('span', {
                            attrs: {
                                title: params.row.sendUserGroup
                            }
                        }, params.row.sendUserGroup)
                    }
                },
                {
                    title: '创建时间',
                    key: 'cTime'
                },
                {
                    title: '已读 / 全部',
                    key: 'readCount',
                    render: (h, params) => {
                        return h('div', params.row.status == 'SEND_IN' ? '发送中' : params.row.readCount + ' / ' + params.row.receiveCount)
                    }
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
                                        this.copyMessage(params.row)
                                    }
                                }
                            }, '复制'),
                            h('a', {
                                style: {
                                    marginRight: '5px'
                                },
                                on: {
                                    click: () => {
                                        this.viewMessage(params.row)
                                    }
                                }
                            }, '查看')
                        ])
                    }
                }
            ],
            loading: true,
            forstagePackages: [],
            frontParams: {appletId: '', token: ''},
            frontLoading: true
        }
    },
    components: {
        UTable
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
        setDataStyle (rows) {
            rows.forEach((row) => {
                row.cellClassName = {
                    appletStatus: row.appletStatus ? 'cell-online' : 'cell-offline'
                }
            })
            return rows
        },
        copyMessage (row) {
            this.$router.push('/home/publishmessage/copy/' + row.msgId)
        },
        viewMessage (row) {
            this.$router.push('/home/publishmessage/view/' + row.msgId)
        },
        freshSendedMessage () {
            this.$refs.table.reload(this.options.queryParams)
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
