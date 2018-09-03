<template>
    <div>
        <div class="sl-header bh-mv-16">
            已选择：
            <div class="bh-pull-right bh-mt-4">
                <Icon type="android-delete"></Icon>
                <a @click.prevent="clear">清空已选</a>
            </div>
        </div>
        <div class="orgs-con bh-mv-16">
            <div class="org-item" v-for="(org, index) in cOrgs" :key="index">{{org.title}}
                <span @click="delOrg(index)">X</span>
            </div>
        </div>
        <div class="users-con bh-mv-16">
            <div class="user-item" v-for="(user, index) in cUsers" :key="user.userCode">{{user.userName + ' '}}
                <span @click="delUser(index)">X</span>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    data () {
        return {
        }
    },
    components: {
    },
    computed: {
        ...Vuex.mapState({
            index: state => state.index
        }),
        cUsers () {
            var users = []
            this.selectedUsers.forEach((user) => {
                users.push(user)
            })
            this.selectedFanses.forEach((fans) => {
                if (!users.some((u) => { return u.userId === fans.userId })) {
                    users.push(fans)
                }
            })
            return users
        },
        cOrgs () {
            var orgs = []
            this.selectedOrgs.forEach((org) => {
                orgs.push(org)
            })
            this.selectedGroups.forEach((group) => {
                if (!orgs.some((o) => { return o.id === group.id })) {
                    orgs.push(group)
                }
            })
            return orgs
        }
    },
    props: {
        selectedOrgs: {
            type: Array,
            default: () => { return [] }
        },
        selectedGroups: {
            type: Array,
            default: () => { return [] }
        },
        selectedUsers: {
            type: Array,
            default: () => { return [] }
        },
        selectedFanses: {
            type: Array,
            default: () => { return [] }
        }
    },
    methods: {
        delOrg (index) {
            this.$emit('delete-org', this.cOrgs[index])
        },
        delUser (index) {
            this.$emit('delete-user', this.cUsers[index])
        },
        cancelSave () {
            this.$router.push('/home/group')
        },
        Save () {
            console.log()
        },
        GetSelectedUsers () {
            return this.cUsers
        },
        GetSelectedOrgs () {
            return this.cOrgs
        },
        clear () {
            this.$emit('delete-all-users')
        }
    },
    mounted () {
    }
}
</script>

<style scoped lang="less">
    .sl-header {
        color: #495060;
        font-weight: bold;
        font-size: 16px;
        div {
            display: flex;
            align-items: center;
            color: #2D8CF0;
            font-size: 0.5rem;
            i {
                margin-right: 0.2em;
                font-size: 1rem;
            }
        }
    }
    .orgs-con {
        .org-item {
            display: inline-block;
            border: 1px solid #2D8CF0;
            padding: 0.1em 0.5em;
            background-color: #2D8CF0;
            color: white;
            margin-right: 5px;
            margin-top: 5px;
            // border-radius: 1em;
            span {
                cursor: pointer;
            }
        }
    }
    .users-con {
        max-height: 500px;
        overflow: auto;
        .user-item {
            display: inline-block;
            border: 1px solid #DDDEE1;
            padding: 0.1em 0.5em;
            color: #495060;
            margin-right: 5px;
            margin-top: 5px;
            span {
                cursor: pointer;
            }
        }
    }
</style>
