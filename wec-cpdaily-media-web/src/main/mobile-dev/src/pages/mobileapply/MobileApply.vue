<template>
    <div class="mobile-apply">
        <div class="container">
            <div class="type-container" v-if="isEdit">
                <div class="top">
                    <span class="title">学生组织</span>
                    <span class="status" :class="getStatusClass()">{{status}}</span>
                </div>
                <span class="reject-reason" v-if="status === '已拒绝'">审批意见：{{reviewOpinion}}</span>
            </div>
            <div class="name-container common-container">
                <span class="title">名称</span>
                <input style="color: #666666;line-height: 13px;" v-if="isInEdit" v-model.trim="name" type="text" placeholder="2-10个字，请勿使用含有推广意图的媒体名" :maxlength="10">
                <p v-if="!isInEdit" style="font-size: 13px;color: #666666;letter-spacing: 0;line-height: 60px;padding-left: 30px;">{{name}}</p>
            </div>
            <div class="avatar-container common-container">
                <span class="title">头像</span>
                <div v-if="isInEdit" class="avatar" @click="chooseAvatar">
                    <Avatar style="width: 42px;height: 42px;border-radius:42px;margin-right:20px;background-color:#F8F8F8;color:#BDC0C5" icon="ios-person-outline" size="large" :src="icon"/>
                    <Icon style="color: #CCCCCC;position: absolute;right: 0px;margin-top: 20px;" type="ios-arrow-right" size="23"></Icon>
                </div>
                <div v-if="!isInEdit" class="avatar" @click="previewImage">
                    <Avatar style="width: 42px;height: 42px;border-radius:42px;background-color:#F8F8F8;color:#BDC0C5" icon="ios-person-outline" size="large" :src="icon"/>
                </div>
            </div>
            <div v-if="isInEdit" class="desc-container common-container">
                <span class="title">介绍</span>
                <textarea style="font-size: 14px;color: #666666;letter-spacing: 0;line-height: 18px;" v-model.trim="description" :maxlength="100" placeholder="0-100个字，要求内容完整通顺，请勿添加任何联系方式，如QQ、微博、手机号等"></textarea>
                <span class="input-notice">{{description.length}}/100</span>
            </div>
            <div v-if="!isInEdit" class="desc-container common-container">
                <span class="title">介绍</span>
                <textarea style="font-size: 14px;color: #666666;letter-spacing: 0;line-height: 18px;" :maxlength="100" v-model.trim="description" readonly placeholder="0-100个字，要求内容完整通顺，请勿添加任何联系方式，如QQ、微博、手机号等"></textarea>
            </div>
            <div v-if="isInEdit && !isWaitCheck" class="cer-info-container common-container">
                <span class="title">认证资料</span>
                <div class="pics">
                    <div class="pics-container">
                        <ul>
                            <li v-for="(item,index) in authMaterialsForLoading" class="pic-item">
                                <div style="overflow: hidden;width: 42px;height: 42px;" class="img-container">
                                    <img v-if="item.isOk" :src="item.url" width="42" height="42">
                                    <div v-if="!item.isOk" class="empty-url">
                                        <Spin fix>
                                            <Icon type="load-c" size=18 class="spin-icon-load"></Icon>
                                        </Spin>
                                    </div>
                                    <div class="delete-pic" v-if="item.isOk">
                                        <Icon style="font-size: 10px;color:#ffffff;position: absolute;bottom: 3px;left: 6px;font-weight: 500;" type="android-close"></Icon>
                                    </div>
                                    <div class="delete-pic-click" v-if="item.isOk" @click="deleteImg(index)">

                                    </div>
                                </div>
                            </li>
                            <li class="add-pic" v-if="authMaterialsForLoading.length < 3" @click="chooseAuthImg">
                                <Icon style="display: block;width: 42px;height: 25px;text-align: center;color: #BDC0C5;" type="ios-camera-outline" size="25"></Icon>
                                <span style="font-size: 11px;color: #BDC0C5;display: block;width: 42px;height: 20px;line-height: 12px;text-align: center;">{{authMaterialsForLoading.length}}/3</span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div v-if="isInEdit && isWaitCheck" class="cer-info-container common-container">
                <span class="title">认证资料</span>
                <div class="pics">
                    <div class="pics-container">
                        <ul>
                            <li v-for="(item,index) in authMaterialsForLoading" class="pic-item">
                                <div style="overflow: hidden;width: 42px;height: 42px;" class="img-container">
                                    <img :src="item.url" width="42" height="42">
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div v-if="!isInEdit" class="cer-info-container common-container">
                <span class="title">认证资料</span>
                <div class="pics">
                    <div class="pics-container">
                        <ul>
                            <li v-for="(item,index) in authMaterialsForLoading" class="pic-item">
                                <div style="overflow: hidden;width: 42px;height: 42px;" class="img-container">
                                    <img :src="item.url" width="42" height="42" @click="previewImages(index)">
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="agreement-container" v-if="!isEdit && !isRejected">
                <Checkbox style="margin-right: 0px;" v-model="isAgree">我已阅读并同意</Checkbox><span style="color:#66AEFF;cursor: pointer;font-size: 12px;" @click.top="goToProtocol">《校园号用户注册协议》</span>
            </div>
            <div v-if="!isEdit && !isRejected" class="commit-btn add-media-btn" :class="{'add-media-btn-ok':isAddMediaOk}" @click="addMedia">
                <span>提交</span>
            </div>
            <div v-if="isEdit && !isInEdit && !isRejected" class="commit-btn" @click="isInEdit = true">
                <span>编辑</span>
            </div>
            <div v-if="isEdit && isInEdit && !isRejected" class="commit-btn" @click="saveEdit">
                <span>完成</span>
            </div>
        </div>
    </div>
</template>

<script type="text/ecmascript-6">
    import util from 'conf/util'
    import api from 'conf/api'

    export default {
        data () {
            return {
                isWaitCheck: false,
                tenantId: '',
                mediaType: '',
                isRejected: false,
                wid: '',
                icon: '',
                isEdit: false,
                description: '',
                isInEdit: true,
                status: '',
                isAgree: false,
                name: '',
                reviewOpinion: '',
                authMaterials: [],
                authMaterialsForLoading: [],
                copyMediaForCheck: {
                    wid: '',
                    icon: '',
                    description: '',
                    name: '',
                    authMaterialsForLoading: []
                }
            }
        },
        methods: {
            hideKeyboard () {
                $("input").blur();
                $("textarea").blur();
                document.activeElement.blur();
            },
            previewImage () {
                if (window.mamp) {
                    let imgs = []
                    imgs.push({url:this.icon, desc:''})
                    mamp.UI.preViewImages(imgs,0);
                }
            },
            previewImages (index) {
                if (window.mamp) {
                    let imgs = []
                    for (let i=0; i<this.authMaterialsForLoading.length; i++) {
                        if(this.authMaterialsForLoading[i].url){
                            imgs.push({url:this.authMaterialsForLoading[i].url, desc:''})
                        } else {
                            imgs.push({url:this.authMaterialsForLoading[i].key, desc:''})
                        }
                    }
                    mamp.UI.preViewImages(imgs,index);
                }
            },
            goToProtocol () {
                if (window.mamp) {
                    mamp.UI.openWebView(window.location.href.replace('mobileapply','protocol'));
                }
            },
            addMedia () {
                if (!this.isAddMediaOk) {
                    return
                }
                if (this.saveCheck()) {
                    if (!this.isAgree) {
                        if (window.mamp) {
                            mamp.UI.showToast('请勾选校园号注册协议');
                        }
                        return
                    }

                    // make authMaterials  data
                    this.authMaterials = []
                    this.authMaterialsForLoading.forEach((item) => {
                        if (item.isOk && item.url) {
                            this.authMaterials.push(item.url)
                        }
                    })
                    let params = {
                        name: this.name,
                        tenantId: this.tenantId,
                        description: this.description,
                        icon: this.icon,
                        authMaterials: this.authMaterials.join(','),
                        mediaType: this.mediaType
                    }
                    util.httpPost(api.MEDIA_APPLY, params, util.handler.CODE).then((res) => {
                        if (window.mamp) {
                            mamp.UI.showToast('校园号申请成功');
                        }
                        this.$router.go(-2)
                    }).catch((err) => {
                        if (window.mamp) {
                            mamp.UI.showToast(err.message);
                        }
                    })
                }
            },
            deleteImg (index) {
                this.authMaterialsForLoading.splice(index, 1)
            },
            chooseAuthImg () {
                let vm = this
                if (window.mamp) {
                    let menus = ['拍照','从相册选择']
                    let title = '上传图片'
                    mamp.UI.actionSheet(title,menus,function(index){
                        if(index === 0){
                            mamp.systemAbility.takeCamera(function (ret){
                                if (ret && ret.length > 0 ) {
                                    vm.authMaterialsForLoading.push({
                                        key: ret[0].url,
                                        url: '',
                                        isOk: false
                                    })
                                    //上传认证图片
                                    vm.uploadImage(ret[0].base64, false, ret[0].url)
                                }
                            });
                        } else if (index === 1) {
                            mamp.systemAbility.takePhoto(function (ret){
                                if (ret && ret.length > 0 ) {
                                    //上传认证图片
                                    for (let i=0; i<ret.length ; i++) {
                                        vm.authMaterialsForLoading.push({
                                            key: ret[i].url,
                                            url: '',
                                            isOk: false
                                        })
                                        //上传头像图片
                                        vm.uploadImage(ret[i].base64, false, ret[i].url)
                                    }
                                }
                            },3-vm.authMaterialsForLoading.length);
                        }
                    });
                }
            },
            getStatusClass () {
                if (this.status === '待审核') {
                    return 'status-wait-audit'
                }
                if (this.status === '待认证') {
                    return 'status-wait-auth'
                }
                if (this.status === '已拒绝') {
                    return 'status-reject'
                }
            },
            chooseAvatar () {
                let vm = this
                if (window.mamp) {
                    let menus = []
                    let title = ''
                    if (this.icon) {
                        title = '替换图片或删除'
                        menus = ['拍照','从相册选择','删除']
                    } else {
                        title = '上传图片'
                        menus = ['拍照','从相册选择']
                    }
                    mamp.UI.actionSheet(title,menus,function(index){
                        if(index === 0){
                            mamp.systemAbility.takeCamera(function (ret){
                                if (ret && ret.length > 0 ) {
                                    var config = {
                                        url:ret[0].url,
                                        width:200,
                                        height:200
                                    };
                                    mamp.UI.cropImage(config, function(msg){
                                        if (msg instanceof Array) {
                                            if (msg.length > 0 ) {
                                                if (msg[0].base64.indexOf('data:image/') != 0) {
                                                    msg[0].base64 = 'data:image/'+msg[0].type+';base64,' + msg[0].base64
                                                }
                                                vm.icon = msg[0].base64
                                                //上传头像图片
                                                vm.uploadImage(msg[0].base64, true, '')
                                            }
                                        } else {
                                            if (msg.base64.indexOf('data:image/') != 0) {
                                                msg.base64 = 'data:image/'+msg.type+';base64,' + msg.base64
                                            }
                                            vm.icon = msg.base64
                                            //上传头像图片
                                            vm.uploadImage(msg.base64, true, '')
                                        }
                                    });
                                }
                            });
                        } else if (index === 1) {
                            mamp.systemAbility.takePhoto(function (ret){
                                if (ret && ret.length > 0 ) {
                                    var config = {
                                        url:ret[0].url,
                                        width:200,
                                        height:200
                                    };
                                    mamp.UI.cropImage(config, function(msg){
                                        if (msg instanceof Array) {
                                            if (msg.length > 0 ) {
                                                if (msg[0].base64.indexOf('data:image/') != 0) {
                                                    msg[0].base64 = 'data:image/'+msg[0].type+';base64,' + msg[0].base64
                                                }
                                                vm.icon = msg[0].base64
                                                //上传头像图片
                                                vm.uploadImage(msg[0].base64, true, '')
                                            }
                                        } else {
                                            if (msg.base64.indexOf('data:image/') != 0) {
                                                msg.base64 = 'data:image/'+msg.type+';base64,' + msg.base64
                                            }
                                            vm.icon = msg.base64
                                            //上传头像图片
                                            vm.uploadImage(msg.base64, true, '')
                                        }
                                    });
                                }
                            },1);
                        } else if (index === 2) {
                            vm.icon = ''
                        }
                    },2);
                }
            },
            getMediaInfo (wid) {
                util.httpGet(api.MEDIA_INFO + '/' + wid, {}, util.handler.DATAS).then((data) => {
                    console.log(data)
                    this.reviewOpinion = data.reviewOpinion
                    this.name = data.name
                    this.copyMediaForCheck.name = data.name
                    this.icon = data.icon
                    this.copyMediaForCheck.icon = data.icon
                    this.description = data.description
                    this.copyMediaForCheck.description = data.description
                    this.authMaterialsForLoading = []
                    this.copyMediaForCheck.authMaterialsForLoading = []
                    if (data.authMaterials) {
                        let tempMaterials = data.authMaterials.split(',')
                        if (tempMaterials.length > 0) {
                            for (let i = 0; i < tempMaterials.length ; i ++) {
                                if (tempMaterials[i]) {
                                    this.authMaterialsForLoading.push({key:tempMaterials[i], url: tempMaterials[i], isOk: true})
                                    this.copyMediaForCheck.authMaterialsForLoading.push({key:tempMaterials[i], url: tempMaterials[i], isOk: true})
                                }
                            }
                        }
                    }
                    this.mediaType = data.mediaType
                    this.tenantId = data.tenantId
                }).catch((err) => {
                })
            },
            getStatusName (status) {
                if (status === 'AUTHED') {
                    return '已审核'
                }
                if (status === 'AUTH_FAIL') {
                    return '已拒绝'
                }
                if (status === 'UN_AUTH') {
                    return '待认证'
                }
                if (status === 'AUTHING') {
                    return '待审核'
                }
                return '数据异常'
            },
            saveEdit () {
                if (this.saveCheck()) {

                    // make authMaterials  data
                    this.authMaterials = []
                    this.authMaterialsForLoading.forEach((item) => {
                        if (item.isOk && item.url) {
                            this.authMaterials.push(item.url)
                        }
                    })

                    let params = {
                        name: this.name,
                        wid: this.wid,
                        tenantId: this.tenantId,
                        description: this.description,
                        icon: this.icon,
                        authMaterials: this.authMaterials.join(','),
                        mediaType: this.mediaType
                    }
                    console.log(params)
                    util.httpPost(api.UPDATE_MEDIA, params, util.handler.DATAS).then(() => {
                        if (window.mamp) {
                            mamp.UI.showToast('保存成功');
                            this.$router.go(-1)
                        }
                        this.isInEdit = false
                    }).catch((err) => {
                        if (window.mamp) {
                            mamp.UI.showToast(err.message);
                        }
                    })
                }
            },
            saveCheck () {
                if (!this.name) {
                    if (window.mamp) {
                        mamp.UI.showToast('名称不能为空');
                    }
                    return false
                } else if (this.name.length < 2 || this.name.length > 10) {
                    if (window.mamp) {
                        mamp.UI.showToast('名称为2-10个字符');
                    }
                    return false
                }
                if (!this.description) {
                    if (window.mamp) {
                        mamp.UI.showToast('介绍不能为空');
                    }
                    return false
                } else if (this.description.length > 100) {
                    if (window.mamp) {
                        mamp.UI.showToast('介绍为0-100个字符');
                    }
                    return false
                }
                if (!this.icon) {
                    if (window.mamp) {
                        mamp.UI.showToast('头像不能为空');
                    }
                    return false
                }
                if (!this.tenantId) {
                    if (window.mamp) {
                        mamp.UI.showToast('当前租户获取失败');
                    }
                    return false
                }
                return true
            },
            uploadImage (data, isIcon, key) {
                util.httpPost(api.CONVERT_BASE64, {data}, util.handler.DATAS).then(data => {
                    if (isIcon) {
                        this.icon = data.fileUrl
                    } else {
                        if (this.authMaterialsForLoading.length < 4) {
                            for (let i=0; i<this.authMaterialsForLoading.length; i++) {
                                if (this.authMaterialsForLoading[i].key === key) {
                                    this.authMaterialsForLoading[i].isOk = true
                                    this.authMaterialsForLoading[i].url = data.fileUrl
                                }
                            }
                        }
                    }
                })
            },
            checkCanBackOrClose () {
                // 编辑模式对比
                if (this.name != this.copyMediaForCheck.name) {
                    return false
                }
                if (this.icon != this.copyMediaForCheck.icon) {
                    return false
                }
                if (this.description != this.copyMediaForCheck.description) {
                    return false
                }
                if (JSON.stringify(this.authMaterialsForLoading) != JSON.stringify(this.copyMediaForCheck.authMaterialsForLoading)) {
                    return false
                }
                return true
            }
        },
        computed: {
            isAddMediaOk(){
                if (this.name && this.description && this.icon && this.isAgree) {
                    return true
                } else {
                    return false
                }
            }
        },
        mounted () {
            let vm = this
            let status = this.$route.query.status;
            let wid = this.$route.query.wid;
            if (status && wid) {
                this.wid = wid
                this.isEdit = true
                this.isInEdit = false
                this.status = this.getStatusName(status)
                if (this.status === '已拒绝') {
                    this.isRejected = true
                }
                if (this.status === '待审核') {
                    this.isWaitCheck = true
                }
                this.getMediaInfo(wid)
            } else {
                this.mediaType = this.$route.query.mediaType
                console.log(this.mediaType)
                document.addEventListener("deviceready", function(){
                        // 不是编辑，就从今日校园里面获取tenantId
                        if (window.mamp) {
                            mamp.cpdaily.getTenantInfo(function(tenantObject){
                                if (tenantObject) {
                                    vm.tenantId = tenantObject.id
                                }
                            });
                        }
                    }, false);
            }

            let title = '您当前还未提交，是否放弃' + (vm.isEdit ? '修改' : '申请')
            document.addEventListener("deviceready", function(){
                    // 拦截返回按钮
                    if (window.mamp) {
                        var config = {
                            left: {
                                left1: {
                                    callFunction: function(){
                                        vm.hideKeyboard()
                                        // 检测是否可以返回
                                        if (vm.checkCanBackOrClose()) {
                                            vm.$router.go(-1)
                                        } else {
                                            mamp.UI.alertView('提示', title, ['取消','确定'],function(index){
                                                if (index === 1) {
                                                    vm.$router.go(-1)
                                                }
                                            });
                                        }
                                    }
                                }
                            },
                            title: '申请校园号'
                        };
                        mamp.UI.setNavHeader(config);
                    }
                }, false);
        }
    }
</script>

<style lang="less" rel="stylesheet/less">
    .mobile-apply{
        .ivu-avatar{
            img{
                object-fit: cover;
            }
        }
        .ivu-spin{
            margin-top: 6px;
        }
    }
</style>

<style scoped lang="less" rel="stylesheet/less">
    .mobile-apply{
        position: absolute;
        width: 100%;
        height: 100%;
        background: #ffffff;
        .container{
            padding: 0px 20px 0px 20px;
            background: #ffffff;
            position: relative;
            .type-container{
                padding: 20px 0 20px 0;
                position: relative;
                .top{
                    height: 25px;
                    font-size: 0px;
                    .title{
                        font-size: 18px;
                        color: #333333;
                        font-weight: blod;
                    }
                    .status{
                        font-size: 12px;
                        line-height: 29px;
                        vertical-align: top;
                        margin-left: 10px;
                        border-radius: 4px;
                        padding: 2px;
                    }
                    .status-wait-audit{
                        background: rgba(255,172,54,0.16);
                        color: #FFAC36;
                    }
                    .status-wait-auth{
                        background: rgba(82,199,202,0.16);
                        color: rgb(82,199,202);
                    }
                    .status-reject{
                        background: rgba(255,77,77,0.16);
                        color: rgb(255,77,77);
                    }
                }
                .reject-reason{
                    margin-top: 8px;
                    display: block;
                    font-size: 14px;
                    color: #FF4D4D;
                    word-break: break-all;
                }
                &:after{
                    content: '';
                    position: absolute;
                    height: 1px;
                    background: #EEEEEE;
                    bottom: 0;
                    left: 0px;
                    right: 0px;
                    z-index: 20;
                }
            }
            .common-container{
                display: flex;
                position: relative;
                height: 60px;
                line-height: 60px;
                .title{
                    font-size: 16px;
                    color: #333333;
                    letter-spacing: 1px;
                }
                &:after{
                    content: '';
                    position: absolute;
                    height: 1px;
                    background: #EEEEEE;
                    top: 60px;
                    left: 0px;
                    right: 0px;
                    z-index: 20;
                }
            }
            .name-container{
                input{
                    width: 100%;
                    margin-left: 30px;
                    flex: 1;
                    font-size: 13px;
                    border: none;
                    &:focus{
                        border:none;
                        outline: none;
                    }
                }
            }
            .avatar-container{
                .avatar{
                    flex: 1;
                    text-align: right;
                    line-height: 60px;
                    cursor: pointer;
                    position: relative;
                }
            }
            .desc-container{
                height: 118px;
                textarea{
                    flex: 1;
                    font-size: 13px;
                    margin-left: 30px;
                    margin-top: 20px;
                    line-height: 24px;
                    border: none;
                    resize: none;
                    margin-bottom: 30px;
                    &:focus{
                        outline: none;
                    }
                }
                .input-notice{
                    position: absolute;
                    bottom: 10px;
                    right: 0px;
                    font-size: 12px;
                    color: #444444;
                    letter-spacing: 1px;
                    text-align: right;
                    line-height: 14px;
                }
                &:after{
                    top: 108px;
                }
            }
            .cer-info-container{
                .pics{
                    flex: 1;
                    text-align: right;
                    line-height: 60px;
                    position: relative;
                    .pics-container{
                        height: 60px;
                        line-height: 60px;
                        ul{
                            font-size: 0px;
                            height: 60px;
                            li{
                                height: 60px;
                                vertical-align: middle;
                                display: inline-block;
                                margin-left: 8px;
                            }
                            .pic-item{
                                position: relative;
                                .img-container{
                                    position: relative;
                                    margin-top: 9px;
                                    border-radius: 4px;
                                    .empty-url{
                                        position: absolute;
                                        top: 0;
                                        left: 0;
                                        background: #ffffff;
                                        z-index: 2;
                                        height: 42px;
                                        width: 42px;
                                        .spin-icon-load{
                                            animation: ani-demo-spin 1s linear infinite;
                                        }
                                    }
                                    .delete-pic{
                                        position: absolute;
                                        background: #444444;
                                        border-radius: 15px;
                                        height: 30px;
                                        width: 30px;
                                        top: -15px;
                                        right: -15px;
                                        cursor: pointer;
                                    }
                                    .delete-pic-click{
                                        height: 21px;
                                        width: 21px;
                                        position: absolute;
                                        top: 0px;
                                        right: 0px;
                                    }
                                }
                                img{
                                    object-fit: cover;
                                }
                            }
                            .add-pic{
                                width: 42px;
                                height: 42px;
                                background: #F8F8F8;
                                border-radius: 4px;
                                cursor: pointer;
                            }
                        }
                    }
                }
            }
            .agreement-container{
                height: 50px;
                line-height: 50px;
                text-align: left;
                font-size: 14px;
                color: #666666;
            }
            .commit-btn{
                height: 40px;
                background: #52C7CA;
                border-radius: 4px;
                text-align: center;
                margin-top: 10px;
                span{
                    line-height: 40px;
                    vertical-align: middle;
                    font-size: 16px;
                    color: #FFFFFF;
                    letter-spacing: 1px;
                }
            }
            .add-media-btn{
                background: rgba(82,199,202,0.16);
            }
            .add-media-btn-ok{
                background: #52C7CA;
            }
        }
    }
    @keyframes ani-demo-spin {
        from { transform: rotate(0deg);}
        50%  { transform: rotate(180deg);}
        to   { transform: rotate(360deg);}
    }
</style>