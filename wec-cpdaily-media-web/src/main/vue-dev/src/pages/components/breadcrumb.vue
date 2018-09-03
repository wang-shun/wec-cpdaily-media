<template>
    <div class="bc-con">
        <Breadcrumb>
            <BreadcrumbItem to="/">校园号平台</BreadcrumbItem>
            <BreadcrumbItem :to="item.path" v-for="item in items" :key="item.name">{{item.name}}</BreadcrumbItem>
        </Breadcrumb>
    </div>
</template>

<script>
export default {
    data () {
        return {}
    },
    methods: {},
    props: {
        items: {
            default: () => { return [] },
            type: Array
        }
    },
    mounted () {
    }
}
</script>

<style scoped lang="less" rel="stylesheet/less">
    .bc-con{
        font-size: 20px;
        height: 40px;
        vertical-align: middle;
        padding-left: 20px;
        padding-top: 20px;
    }
</style>
<style type="text/css">
    .bc-con .ivu-breadcrumb {
        font-size: 16px;
    }

    .bc-con .ivu-breadcrumb .ivu-breadcrumb-item-link {
        color: #9EA7B4;
    }

    .bc-con .ivu-breadcrumb span:last-child .ivu-breadcrumb-item-link {
        color: #555;
    }
</style>
