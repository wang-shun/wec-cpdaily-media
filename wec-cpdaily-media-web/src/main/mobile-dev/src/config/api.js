const ROOT_PATH = '/wec-cpdaily-media'

let dataServer = ''

let dataPath = (path) => {
    return dataServer + path
}

export default {
    MEDIA_LIST_UNAUTHED: dataPath(`${ROOT_PATH}/v3/campusmedia/account/list/unauthed`),
    APPLY_CHECK: dataPath(`${ROOT_PATH}/v3/campusmedia/account/apply/check`),
    MEDIA_INFO: dataPath(`${ROOT_PATH}/v3/campusmedia/setting/mediaInfo`),
    UPDATE_MEDIA: dataPath(`${ROOT_PATH}/v3/campusmedia/setting/updateMedia`),
    CONVERT_BASE64: dataPath(`${ROOT_PATH}/v3/campusmedia/uploadFileBase64`),
    MEDIA_APPLY: dataPath(`${ROOT_PATH}/v3/campusmedia/account/apply/action`),
    RANKING_LIST: dataPath(`${ROOT_PATH}/rankings`)
}
