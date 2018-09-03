<template>
    <div class="org-con">
        <div class="org-tree">
            <org-tree ref="orgtree" @checked-orgs-changed="checkedOrgsChanged" @org-selected="orgSelected"></org-tree>
        </div>
        <div class="user-list">
            <user-table ref="usertable" @selected-users-changed="usersChanged"></user-table>
        </div>
    </div>
</template>
<script>
import orgTree from './orgTree'
import userTable from './userTable'
export default {
    data () {
        return {
        }
    },
    components: {
        orgTree,
        userTable
    },
    computed: {
        ...Vuex.mapState({
            index: state => state.index
        })
    },
    methods: {
        checkedOrgsChanged (orgs) {
            this.$emit('checked-orgs-changed', orgs)
            this.$refs.usertable.freshState()
        },
        orgSelected (orgs) {
            this.$refs.usertable.freshUserTable({type: 'org', org: orgs[0]})
        },
        usersChanged (allCheckedUsers) {
            this.$emit('selected-users-changed', allCheckedUsers)
        }
    },
    mounted () {
    }
}
</script>

<style scoped lang="less">
    .org-con {
        display: flex;
        .org-tree {
            width: 200px;
            min-width: 200px;
            border-right: 1px solid #E3E8EE;
        }
        .user-list {
            flex: 1;
        }
    }
</style>
