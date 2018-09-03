import util from 'conf/util.js'
import api from 'conf/api'

export const addActivity = (param) => {
    return util.httpGet(api.ADD_ACTIVITY, param)
}

