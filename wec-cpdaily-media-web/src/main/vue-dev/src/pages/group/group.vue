<template>
    <div class="main-body">
        <breadcrumb :items="bcs"></breadcrumb>
        <div class="body-con">
            <div class="group-header bh-mb-8 bh-mt-4">
                用户组管理
            </div>
            <div class="group-body">
                <Tabs v-model="tab">
                    <TabPane label="自定义分组" name="custom"></TabPane>
                    <TabPane label="我的粉丝" name="fans"></TabPane>
                </Tabs>
                <custom v-show="tab === 'custom'" ref="custom"></custom>
                <fans v-show="tab === 'fans'" @fans-add-group="fansAddGroup"></fans>
            </div>
        </div>
    </div>
</template>

<script>
import breadcrumb from 'com/breadcrumb.vue'
import custom from './custom.vue'
import fans from './fans.vue'

export default {
    data () {
        return {
            tab: 'custom',
            bcs: [{
                // path: '/home/group',
                name: '用户组管理'
            }]
        }
    },
    methods: {
        fansAddGroup () {
            this.$refs.custom.search()
        }
    },
    mounted () {
    },
    components: {
        breadcrumb,
        custom,
        fans
    }
}
</script>

<style scoped lang="less" rel="stylesheet/less">
    .main-body{
        .body-con{
            margin: 20px;
            padding: 20px;
            min-height: e("calc(100vh - 144px)");
            background: #FFFFFF;
            box-shadow: 0 0 12px 0 rgba(132,134,219,0.16);
            border-radius: 2px;
            .group-header {
                font-size: 16px;
                font-weight: bold;
            }
        }
    }
</style>
