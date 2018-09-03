<template>
    <div class="mobile-select-identity">
        <div class="container">
            <div class="card-container" @click="goToApply('OFFICIAL')">
                <div class="head-img">
                    <img src="static/mobile_school_function_icon.png">
                </div>
                <p class="title">学校职能部门</p>
                <p class="desc">适合由学校专职人员组成，旨在提供校内师生管理服务的职能部门申请</p>
            </div>
            <div class="card-container" @click="goToApply('CLUB')">
                <div class="head-img student-club">
                    <img src="static/student_club_icon.png">
                </div>
                <p class="title">学生组织</p>
                <p class="desc">适合由学校学生组织的，受学校党委领导、团委指导，自我服务、自我提高、自我管理、辅助教学的学生组织、社团组织等团队申请</p>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data () {
        return {}
    },
    methods: {
        goToApply (mediaType) {
            this.$router.push({name:'mobileapply',query:{mediaType: mediaType}})
        }
    },
    mounted () {
        let vm = this
        // 拦截返回按钮
        if (window.mamp) {
            var config = {
                left: {
                    left1: {
                        callFunction: function(){
                            vm.$router.go(-1)
                        }
                    }
                },
                title: '身份类型选择'
            };
            mamp.UI.setNavHeader(config);
        }
    }
}
</script>

<style scoped lang="less" rel="stylesheet/less">
    .mobile-select-identity{
        position: absolute;
        background: #ffffff;
        width: 100%;
        height: 100%;
        .container{
            background: #ffffff;
            .card-container{
                height: 217px;
                padding: 37px 30px 0 30px;
                margin: 38px 20px 38px 20px;
                box-shadow: 0 5px 20px 5px rgba(0,0,0,0.08);
                border-radius: 4px;
                text-align: center;
                .head-img{
                    width: 60px;
                    height: 60px;
                    margin: 0 auto;
                    background: rgba(82,199,202,0.20);
                    box-shadow: 0 4px 8px 0 rgba(82,199,202,0.16);
                    border-radius: 60px;
                    line-height: 56px;
                    text-align: center;
                    img{
                        width: 38px;
                        height: 38px;
                        vertical-align: middle;
                    }
                }
                .student-club{
                    background: rgba(255,172,54,0.20);
                    box-shadow: 0 4px 8px 0 rgba(255,172,54,0.16);
                }
                .title{
                    font-size: 16px;
                    color: #333333;
                    letter-spacing: 1px;
                    margin-top: 15px;
                    font-weight: 500;
                }
                .desc{
                    font-size: 13px;
                    color: #CCCCCC;
                    letter-spacing: 0;
                    text-align: center;
                    margin-top: 10px;
                }
            }
        }
    }
</style>