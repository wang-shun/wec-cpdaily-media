<template>
    <div>
        <div class="title">
            图文实时预览
        </div>
        <div class="body">
            <div class="phone-con" :style="{'background-image': cBg}">
                <div class="msg-con" :style="{'background-image': cPic}">
                    <div class="cnt">
                        {{info.title}}
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    data () {
        return {
            // info: {
            //     pic: 'http://img.mp.sohu.com/upload/20170523/cd93638322414f9396f96f3b0b5cf652_th.png',
            //     content: '陆家嘴的城市天际线'
            // }
        }
    },
    components: {
        // fileUpload
    },
    computed: {
        cPic () {
            return `url(${this.info.pic})`
        },
        cBg () {
            return `url(static/phone.png)`
        }
    },
    props: {
        info: {
            type: Object,
            default: () => ({})
        }
    },
    methods: {
    },
    mounted () {
    }
}
</script>

<style scoped lang="less">
    .title {
        font-size: 14px;
        font-weight: bold;
    }
    .body {
        text-align: center;
        padding-top: 40px;
        .phone-con {
            width: 191px;
            height: 386px;
            display: inline-block;
            .msg-con {
                margin-top: 110px;
                margin-left: 15px;
                margin-right: 15px;
                height: 90px;
                background-size: cover;
                position: relative;
                .cnt {
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                    height: 2em;
                    line-height: 2em;
                    background-color: rgba(0, 0, 0, 0.5);
                    color: white;
                    position: absolute;
                    bottom: 0;
                    width: 100%;
                    text-align: center;
                    font-weight: bold;
                }
            }
        }
    }
</style>
