const SHORT_PATH = process.env.NODE_ENV == 'development' ? '/wec-dev-back' : '/wec-cpdaily-media'
const ROOT_PATH = `${SHORT_PATH}/v3/campusmedia`

let dataServer = ''

let dataPath = (path) => {
    return dataServer + path
}

export default {
    convertBase64: dataPath(`${ROOT_PATH}/uploadFileBase64`),
    uploadFile: dataPath(`${ROOT_PATH}/uploadFile`),
    msgList: dataPath(`${SHORT_PATH}/msg/list`),
    msgView: dataPath(`${SHORT_PATH}/msg`),
    msgSend: dataPath(`${SHORT_PATH}/msg/send`),
    msgDel: dataPath(`${SHORT_PATH}/msg/del`),
    msgSaveAndSend: dataPath(`${SHORT_PATH}/msg/saveAndSend`),
    msgNotify: dataPath(`${SHORT_PATH}/msg/unread/sms/notify/`),
    msgNotifyWaitHour: dataPath(`${SHORT_PATH}//msg/sms/notify/wait`),
    orgTree: dataPath(`${ROOT_PATH}/user/tree/common`),
    customGroup: dataPath(`${ROOT_PATH}/user/tree/mine`),
    orgUsers: dataPath(`${ROOT_PATH}/user/tree/search`),
    groupList: dataPath(`${ROOT_PATH}/user/group/list`),
    canViewOrgs: dataPath(`${ROOT_PATH}/user/group/org/canview`),
    createGroup: dataPath(`${SHORT_PATH}/group/create/fromselected`),
    editGroup: dataPath(`${SHORT_PATH}/group/members/add`),
    editGroupName: dataPath(`${SHORT_PATH}/group/name/update`),
    delMembers: dataPath(`${SHORT_PATH}/group/members/delete`),
    addGroup: dataPath(`${ROOT_PATH}/user/group/add`),
    delGroup: dataPath(`${ROOT_PATH}/user/group/del`),
    groupAddUsers: dataPath(`${ROOT_PATH}/user/group/action`),
    replySettingSave: dataPath(`${ROOT_PATH}/auto/reply/msg/update`),
    getReplySetting: dataPath(`${ROOT_PATH}/auto/reply/msg/index`),
    getReadList: dataPath(`${SHORT_PATH}/msg/read/list`),
    getMsgView: dataPath(`${ROOT_PATH}/gateway/msg/view/`),
    logout: dataPath(`${SHORT_PATH}/logout`),
    login: dataPath(`${SHORT_PATH}/login`),


    // 发布活动
    ADD_ACTIVITY: dataPath(`${SHORT_PATH}/activity/activity-manage/addActivity`),
    // 查询活动
    QUERY_ACTIVITIES: dataPath(`${SHORT_PATH}/activity/activity-manage/activities`),
    CANCEL_ACTIVITY: dataPath(`${SHORT_PATH}/activity/activity-manage/cancelPublish`),
    PAUSE_ACTIVITY: dataPath(`${SHORT_PATH}/activity/activity-manage/pauseSign`),
    RECOVER_SIGN: dataPath(`${SHORT_PATH}/activity/activity-manage/recoverSign`),
    DEL_ACTIVITY: dataPath(`${SHORT_PATH}/activity/activity-manage/delActivity`),
    allUsers: dataPath(`${SHORT_PATH}/activity/common/all-users`),
    uploadFileActivity: dataPath(`${SHORT_PATH}/activity/common/uploadFile`),
    convertBase64Activity: dataPath(`${SHORT_PATH}/activity/common/uploadFileBase64`),
    viewActivity: dataPath(`${SHORT_PATH}/activity/activity-manage/activity`),
    allCircles: dataPath(`${SHORT_PATH}/activity/common/all-circles`),
    editActivity: dataPath(`${SHORT_PATH}/activity/activity-manage/editActivity`),
    validActivityDoing: dataPath(`${SHORT_PATH}/activity/activity-manage/validActivityDoing`),
    exportExcel: dataPath(`${SHORT_PATH}/activity/activity-manage/exportExcel`),

    accountInfo: dataPath(`${ROOT_PATH}/account/owner`),
    accountSchools: dataPath(`${SHORT_PATH}/tenant/list`),
    mediaApply: dataPath(`${ROOT_PATH}/account/apply/action`),
    updateMedia: dataPath(`${ROOT_PATH}/setting/updateMedia`),
    mediaList: dataPath(`${ROOT_PATH}/account/list`),
    mediaDel: dataPath(`${ROOT_PATH}/account/remove`),
    materialApprove: dataPath(`${ROOT_PATH}/account/apply/material/action`),
    getLoginInfo: dataPath(`${ROOT_PATH}/login/getLoginInfo`),
    checkLoginStatus: dataPath(`${ROOT_PATH}/login/checkLoginStatus`),
    loginOut: dataPath(`${ROOT_PATH}/login/logout`),
    mediaInfo: dataPath(`${ROOT_PATH}/setting/mediaInfo`),
    mediaSwitch: dataPath(`${ROOT_PATH}/account/switch`),
    mediaTenant: dataPath(`${ROOT_PATH}/setting/mediaTenant`),
    asdf: '',
    // 获取活动参与人分页列表
    GET_JOIN_PEOPLES: dataPath(`${SHORT_PATH}/activity/activity-manage/peoples`),

    // 获取一个校园号关注人员列表
    QUERY_ADMINS: dataPath(`${SHORT_PATH}/admins`),
    GET_FANS: dataPath(`${SHORT_PATH}/fans`),
    ADD_MANAGER: dataPath(`${SHORT_PATH}/admins/add`),
    DELETE_MANAGER: dataPath(`${SHORT_PATH}/admins/del`),
    GET_LOGS: dataPath(`${SHORT_PATH}/log/list`)
}
