<template>
    <div class="org-tree-node">
        <Tree v-if="showTree" :data="orgs" show-checkbox @on-check-change="checkChange" @on-select-change="selectNode"></Tree>
    </div>
</template>
<script>
import api from 'conf/api'
import util from 'conf/util'
const extract = (arr, children) => {
    let len = arr.length
    for (var j = 0; j < len; j++) {
        var matchs = children.filter((child) => {
            return child.id === arr[j].id
        })
        if (matchs.length > 0) {
            arr.splice(j--, 1)
            len--
        }
    }
    children.forEach((child) => {
        if (child.children && child.children.length > 0) {
            extract(arr, child.children)
        }
    })
}
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
            keyword: '',
            grade: '',
            orgs: [],
            showTree: true,
            checkedOrgs: []
        }
    },
    components: {
    },
    computed: {
        ...Vuex.mapState({
            index: state => state.index
        })
    },
    props: {
        initCheckedGroups: {
            type: Array,
            default: () => ([])
        }
    },
    methods: {
        getOrgTree () {
            return util.httpGet(api.orgTree, {}, util.handler.DATAS).then((data) => {
                if (data.id) {
                    var orgs = data.children
                    if (this.initCheckedGroups.length > 0) {
                        mergeData(this, orgs)
                        this.$emit('checked-orgs-changed', this.checkedOrgs)
                    }
                    function mergeData (vm, ogs) {
                        ogs.forEach((og) => {
                            if (vm.initCheckedGroups.some((group) => { return group.groupId === og.id && group.groupName === og.title })) {
                                og.checked = true
                                vm.checkedOrgs.push(og)
                            }
                            if (og.children && og.children.length > 0) {
                                mergeData(vm, og.children)
                            }
                        })
                    }
                    this.orgs = orgs
                }
            })
        },
        checkChange (orgs) {
            // 对返回的orgs进行精简
            var arrLen = orgs.length
            for (var i = 0; i < arrLen; i++) {
                extract(orgs, orgs[i].children)
                arrLen = orgs.length
            }
            this.checkedOrgs = orgs
            this.$emit('checked-orgs-changed', this.checkedOrgs)
        },
        selectNode (org) {
            if (org.length < 1) return
            this.$emit('org-selected', org)
        },
        deleteAllCheckedOrgs () {
            cancelCheckedItems(this.orgs)
            this.checkedOrgs = []
            this.$emit('checked-orgs-changed', this.checkedOrgs)
            this.showTree = false
            this.$nextTick(() => {
                this.showTree = true
            })
        },
        deleteCheckedOrg (org) {
            var node = getTreeItem(this.orgs, org.id)
            if (!node) return
            cancelCheckedItems([node])
            var cIndex
            this.checkedOrgs.forEach((o, index) => {
                if (o.id === org.id) {
                    cIndex = index
                }
            })
            this.checkedOrgs.splice(cIndex, 1)
        }
    },
    mounted () {
        this.getOrgTree().then(() => {
            // debugger
            // $('.org-tree').css('height', $('.org-tree').parent().height())
            // $('.org-tree-node').css('height', 600)
            // $('.org-tree-node').niceScroll({cursorcolor : "#E2E2E2"})
        })
    }
}
</script>

<style scoped lang="less">
    .org-tree-node {
        padding-left: 20px;
        height: 600px;
        overflow: auto;
    }
</style>
