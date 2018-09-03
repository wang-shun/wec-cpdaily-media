<template>
    <div class="main-body">
        <breadcrumb :items="bcs"></breadcrumb>
        <div class="body-con">
            <div class="group-header bh-pv-16 bh-ph-16">
                添加人员
            </div>
            <div class="group-body">
                <div class="user-list">
                    <div class="list-header bh-mv-16">
                        {{groupName}}
                    </div>
                    <Input v-model="keyword" class="btn-search" placeholder="输入学生的姓名、学号">
                        <Button slot="append" type="primary">搜索</Button>
                    </Input>
                    <div class="bh-mv-16">
                        <UTable ref="table" :columns="columns" :options="options" :data="data"></UTable>
                    </div>
                </div>
                <div class="panel-selected">
                    <div class="sl-header bh-mv-16">
                        已选择：
                    </div>
                    <div class="users-con bh-mv-16">
                        <div class="user-item" v-for="(user, index) in selectedUsers" :key="user.userCode">{{user.userName + ' ' + user.userCode}}
                            <span @click="delUser(index)">X</span>
                        </div>
                    </div>
                    <div class="btn-add-con">
                        <Button type="primary" @click="Save">保存</Button>
                        <Button @click="cancelSave">取消</Button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import breadcrumb from '../../components/breadcrumb.vue'
import UTable from '../../components/UTable'
import api from 'conf/api'

export default {
    data () {
        return {
            data: [{
                userCode: 'asd',
                userName: '学校',
                userGender: 'asd',
                userGrade: 'asd'
            }],
            groupName: '水电费公司的风格',
            keyword: '',
            selectedUsers: [{
                userCode: '001111',
                userName: '晨晨'
            }],
            tab: 'custom',
            bcs: [{
                path: '/home/group',
                name: '用户组管理'
            }, {
                // path: '/home/adduser',
                name: '添加人员'
            }],
            options: {
                url: api.CUSTOM_GROUP,
                queryParams: {
                    keyword: ''
                }
            },
            columns: [
                {
                    type: 'selection',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '学号',
                    key: 'userCode'
                },
                {
                    title: '姓名',
                    key: 'userName'
                },
                {
                    title: '性别',
                    key: 'userGender'
                },
                {
                    title: '班级',
                    key: 'userGrade'
                }
            ]
        }
    },
    methods: {
        cancelSave () {
        },
        Save () {
        }

    },
    mounted () {
    },
    components: {
        breadcrumb,
        UTable
    }
}
</script>

<style scoped lang="less" rel="stylesheet/less">
    .main-body{
        .body-con{
            margin: 20px;
            min-height: e("calc(100vh - 144px)");
            background: #FFFFFF;
            box-shadow: 0 0 12px 0 rgba(132,134,219,0.16);
            border-radius: 2px;
            display: flex;
            flex-direction: column;
            .group-header {
                font-size: 16px;
                font-weight: bold;
                border-bottom: 1px solid #DDDEE1;
            }
            .group-body {
                flex: 1;
                display: flex;
                .user-list {
                    border-right: 1px solid #DDDEE1;
                    width: 600px;
                    min-width: 600px;
                    padding: 20px;
                    flex: 1;
                    .list-header {
                        color: #495060;
                        font-weight: bold;
                        font-size: 16px;
                    }
                }
                .panel-selected {
                    position: relative;
                    flex: 1;
                    padding: 0 20px;
                    .sl-header {
                        color: #495060;
                        font-weight: bold;
                        font-size: 16px;
                    }
                    .orgs-con {
                        .org-item {
                            display: inline-block;
                            border: 1px solid #2D8CF0;
                            padding: 0.1em 0.5em;
                            background-color: #2D8CF0;
                            color: white;
                            // border-radius: 1em;
                            span {
                                cursor: pointer;
                            }
                        }
                    }
                    .users-con {
                        .user-item {
                            display: inline-block;
                            border: 1px solid #DDDEE1;
                            padding: 0.1em 0.5em;
                            color: #495060;
                            // border-radius: 1em;
                            span {
                                cursor: pointer;
                            }
                        }
                    }
                    .btn-add-con {
                        position: absolute;
                        bottom: 20px;
                        height: 30px;
                        width: 100%;
                        text-align: center;
                    }
                }
            }
        }
    }
</style>
