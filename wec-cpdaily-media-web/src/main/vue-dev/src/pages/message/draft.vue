<template>
    <article>
        <div class="bh-mv-16">
            <UTable ref="table" :columns="columns" :options="options"></UTable>
        </div>
        <Modal
            v-model="modalDel"
            title="警告"
            @on-ok="delMessageOk"
            @on-cancel="cancelDelMessage">
            <p>确认删除此消息？</p>
        </Modal>
    </article>
</template>
<script>
import api from 'conf/api'
import UTable from 'com/UTable'
import util from 'conf/util.js'

export default {
    data () {
        return {
            options: {
                url: api.msgList,
                queryParams: {
                    msgStatus: 'SEND'
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
                                        this.sendMessage(params.row)
                                    }
                                }
                            }, '发送'),
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
                                        this.editMessage(params.row)
                                    }
                                }
                            }, '编辑'),
                            h('a', {
                                style: {
                                    marginRight: '5px'
                                },
                                on: {
                                    click: () => {
                                        this.delMessage(params.row)
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
            currentMsg: null,
            modalDel: false
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
        editMessage (row) {
            this.$router.push('/home/publishmessage/edit/' + row.msgId)
        },
        sendMessage (row) {
            util.httpPost(api.msgSend + '/' + row.msgId, {}, util.handler.CODE).then((res) => {
                this.$Message.success('发送成功')
                this.$refs.table.reload(this.options.queryParams)
                this.$emit('draft-message-send')
            }).catch((err) => {
                this.$Message.error('异常：' + err.message)
            })
        },
        delMessage (row) {
            this.currentMsg = row
            this.modalDel = true
        },
        cancelDelMessage (row) {
            this.modalDel = false
        },
        delMessageOk () {
            util.httpPost(api.msgDel + '/' + this.currentMsg.msgId, {}, util.handler.CODE).then((res) => {
                this.$Message.success('删除成功')
                this.$refs.table.reload(this.options.queryParams)
            }).catch((err) => {
                this.$Message.error('删除异常：' + err.message)
            })
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
