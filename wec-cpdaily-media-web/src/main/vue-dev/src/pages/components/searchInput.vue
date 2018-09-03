<template xmlns:v-bind="http://www.w3.org/1999/xhtml">
    <div class="search-input">
        <input type="text" ref="searchinput" class="search-content" :placeholder="placeholder" :value="value" @input="Input" @keyup.13="search">
        <Icon class="search-icon" type="ios-search-strong" size="22"></Icon>
        <Button class="search-btn" slot="append" type="primary" style="background: #2D8CF0;border-radius: 0 4px 4px 0;" @click="search">搜索</Button>
    </div>
</template>

<script>
export default {
    data () {
        return {
            keyword: ''
        }
    },
    methods: {
        search () {
            this.$emit('search', this.keyword)
        },
        Input (e) {
            var input = this.$refs.searchinput.value
            this.$emit('input', input)
        }
    },
    props: {
        placeholder: {
            type: String,
            default: ''
        },
        value: {
            type: String,
            default: ''
        }
    },
    mounted () {
        this.keyword = this.value
    },
    components: {
    }
}
</script>

<style scoped lang="less" rel="stylesheet/less">
    .search-input{
        position: relative;
        height: 32px;
        box-sizing: border-box;
        font-size: 0px;
        background: #FFFFFF;
        .search-content{
            width: e("calc(100% - 110px)");
            height: 100%;
            font-size: 14px;
            padding-left: 30px;
            border: 1px solid #D7DDE4;
            border-radius: 4px 0 0 4px;
            &:focus{
                border: 1px solid #2D8CF0;
                outline: none;
            }
        }
        .search-icon{
            position: absolute;
            top: 6px;
            left: 8px;
            color: #80848F;

        }
        .search-btn{
            position: absolute;
            width: 80px;
        }
    }
</style>
