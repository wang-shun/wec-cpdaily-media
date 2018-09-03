<template>
    <div class="main-body">
        <breadcrumb :items="bcs"></breadcrumb>
        <div class="body-con">
            <div class="message-header bh-mb-8 bh-mt-4">
                消息送达
            </div>
            <div class="message-body">
                <div class="message-header bh-pv-16">
                    <Button type="primary" @click="publishMessage">发布消息</Button>
                </div>
                <div>
                    <Tabs v-model="tab" @on-click="changTab">
                        <TabPane label="已发布" name="sended"></TabPane>
                        <TabPane label="草稿箱" name="draft"></TabPane>
                    </Tabs>
                    <sended v-show="tab === 'sended'" ref="sended"></sended>
                    <draft v-show="tab === 'draft'" @draft-message-send="freshSendedMessage"></draft>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import breadcrumb from '../components/breadcrumb.vue'
import sended from './sended.vue'
import draft from './draft.vue'

export default {
    data () {
        return {
            tab: 'sended',
            bcs: [{
                // path: '/home/message',
                name: '消息送达'
            }],
            fromDetail: false
        }
    },
    methods: {
        publishMessage () {
            this.$router.push('/home/publishmessage/new/n')
        },
        freshSendedMessage () {
            this.$refs.sended.freshSendedMessage()
        },
        changTab (name) {
            sessionStorage.setItem('msg-tab', name)
        }
    },
    beforeRouteEnter (to, from, next) {
        next(vm => {
            // debugger
            if ((from.path.indexOf('/home/publishmessage/edit/') > -1 || from.path.indexOf('/home/publishmessage/copy/') > -1) && sessionStorage.getItem('msg-tab') === 'draft') {
                vm.tab = 'draft'
            }
        })
    },
    mounted () {
    },
    components: {
        breadcrumb,
        sended,
        draft
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
            .message-header {
                font-size: 16px;
                font-weight: bold;
            }
        }
    }
</style>
