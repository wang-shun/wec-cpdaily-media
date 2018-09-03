<template>
    <div id="main">
        <router-view/>
    </div>
</template>

<script>
export default {
    mounted () {
        if (window.mamp) {
            mamp.registerNetworkChangeCallback(function (type){
                if (type === 'none') {
                    mamp.UI.showToast('网络错误');
                }
            });
        }
    }
}
</script>

<style lang="less" rel="stylesheet/less">

</style>
