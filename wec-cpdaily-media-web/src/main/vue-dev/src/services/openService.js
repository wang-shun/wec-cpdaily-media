import util from 'conf/util.js'
import api from 'conf/api'

export const addActivity = (param) => {
    return util.httpPost(api.ADD_ACTIVITY, param)
}

export const editActivity = (param) => {
    return util.httpPost(api.editActivity, param, util.handler.CODE)
}

export const queryActivities = (param) => {
    return util.httpPost(api.QUERY_ACTIVITIES, param, util.handler.ROWS)
}

export const getLogs = (param) => {
    return util.httpPost(api.GET_LOGS, param)
}
