<template>
    <div class="group-tree">
        <Tree ref="tree" :if="showTree" :data="groups" show-checkbox @on-check-change="checkChange" @on-select-change="selectNode"></Tree>
    </div>
</template>
<script>
import api from 'conf/api'
import util from 'conf/util'

const cancelCheckedItems = (nodes) => {
    if (nodes && nodes.length > 0) {
        nodes.forEach((node) => {
            node.checked = false
            cancelCheckedItems(node.children)
        })
    }
}
const getTreeItem = (nodes, id) => {
    var tNode
    if (nodes && nodes.length > 0) {
        nodes.forEach((node) => {
            if (node.id === id) {
                tNode = node
            } else {
                if (getTreeItem(node.children, id)) {
                    tNode = getTreeItem(node.children, id)
                }
            }
        })
    }
    return tNode
}

export default {
    data () {
        return {
            showTree: true,
            groups: []
        }
    },
    components: {
    },
    computed: {
        ...Vuex.mapState({
            index: state => state.index
        }),
        cGroups () {
            var wrapper = [{
                title: '我的分组',
                expand: true
            }]
            wrapper.children = this.groups
            return wrapper
        }
    },
    props: {
        initCheckedGroups: {
            type: Array,
            default: () => ([])
        }
    },
    methods: {
        checkChange (groups) {
            if (groups.length > 0 && groups[0].id === 'root') {
                groups.splice(0, 1)
            }
            this.$emit('checked-groups-changed', groups)
        },
        selectNode (group) {
            if (group.length < 1) return
            this.$emit('group-selected', group)
        },
        getGroups () {
            util.httpGet(api.customGroup, {}, util.handler.DATAS).then((data) => {
                data.title = '我的分组'
                data.expand = true
                if (this.initCheckedGroups.length > 0) {
                    var groups = []
                    data.children.forEach((org) => {
                        if (this.initCheckedGroups.some((group) => { return group.groupId === org.id })) {
                            org.checked = true
                            groups.push(org)
                        }
                    })
                    this.$emit('checked-groups-changed', groups)
                }
                this.groups = [data]
            })
        },
        deleteCheckedOrg (org) {
            var node = getTreeItem(this.groups, org.id)
            if (!node) return
            cancelCheckedItems([node])
        },
        deleteAllCheckedOrgs () {
            cancelCheckedItems(this.groups)
            this.$emit('checked-groups-changed', [])
            this.showTree = false
            this.$nextTick(() => {
                this.showTree = true
            })
        }
    },
    mounted () {
        this.getGroups()
    }
}
</script>

<style scoped lang="less">
    .group-tree {
        padding-left: 20px;
        .my-fans {
            margin-left: 15px;
            .label {
                cursor: pointer;
                padding: 0.2em 0.4em;
            }
            .fans-selected {
                background-color: #D4E5FC;
            }
        }
    }
</style>
