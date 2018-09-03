/* eslint no-undef: 0 */
// 数据处理方法
let _code = (data) => {
    if (data && (data.code === '0' || data.code === 0)) {
        return data
    }  else {
        throw data
    }
}
// 一般是返回数据列表
let _rows = (data) => {
    data = _code(data)
    if (data && data.datas && data.datas.rows) {
        return data.datas.rows
    } else {
        return []
    }
}
// 自由数据
let _datas = (data) => {
    data = _code(data)
    if (data && data.datas !== undefined) {
        return data.datas
    } else {
        return []
    }
}
// 自由数据2
let _data = (data) => {
    data = _code(data)
    if (data && data.data !== undefined) {
        return data.data
    } else {
        return []
    }
}
// 一般用来获取单行数据
let _firstRow = (data) => {
    data = _code(data)
    if (data && data.datas && data.datas.rows) {
        return data.datas.rows[0]
    } else {
        return null
    }
}
function errorHandle (error) {
    var errObj = $.extend(true, {}, error)
    errObj.message = error.message
    sessionStorage.setItem('error', JSON.stringify(errObj))
    location.href = location.href.replace(/\/\w*$/, '/error')
    throw error
}
/*
    axios 配置
*/
axios.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
function httpGet (url, config, cb) {
    var cfg = {}
    if (config && config.noCredentials === true) {
        cfg.noCredentials = true
    }
    return axios.get(url, cfg).then((res) => {
        if (cb && typeof(cb) === 'function') {
            return cb(res.data)
        } else {
            return res.data
        }
    })
}
function httpGetQuery (url, qParams, config, cb) {
    var cfg = {}
    if (config && config.noCredentials === true) {
        cfg.noCredentials = true
    }
    let aQPms = obj2query(qParams)
    if (aQPms.length > 0) {
        url += ('?' + aQPms.join('&'))
    }
    return axios.get(url, cfg).then((res) => {
        if (cb && typeof(cb) === 'function') {
            return cb(res.data)
        } else {
            return res.data
        }
    })
}
function fetchPost (url, params) {
    console.log(params)
    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-Requested-With': 'XMLHttpRequest'
        },
        credentials: 'include',
        body: JSON.stringify(params)
    })
}
function httpPost (url, data, cb) {
    // console.log('send post')
    return axios.post(url, data, {
            withCredentials: true
        })
        .then((res) => {
            // console.log('axios response:')
            // console.log(res)
            if (cb && typeof(cb) === 'function') {
                return cb(res.data)
            } else {
                return res.data
            }
        })
        .catch(function (error) {
            throw (error)
        })
}
function obj2query (params) {
    let rtn = []
    for (var prop in params) {
        if (params.hasOwnProperty(prop)) {
            rtn.push(`${prop}=${params[prop]}`)
        }
    }
    return rtn
}
function httpPostQuery (url, qParams, pParams, cb) {
    let aQPms = obj2query(qParams)
    if (aQPms.length > 0) {
        url += ('?' + aQPms.join('&'))
    }
    return axios.post(url, pParams, {
            // withCredentials: true
        })
        .then((res) => {
            // console.log('axios response:')
            // console.log(res)
            if (cb && typeof(cb) === 'function') {
                return cb(res.data)
            } else {
                return res.data
            }
        })
        .catch(function(error) {
            throw (error)
        })
}
function getQueryParam (name) {
    var value
    var params = location.search.replace(/\?/g, '').split('&')
    params.forEach(function(param) {
        if (param && typeof param === 'string') {
            var arr = param.split('=')
            if (arr.length > 1 && arr[0] === name) {
                value = arr[1]
            }
        }
    })
    return value
}
function getAllQueryParams () {
    var data = {}
    var params = location.search.replace(/\?/g, '').split('&')
    params.forEach(function(param) {
        if (param && typeof param === 'string') {
            var arr = param.split('=')
            if (arr.length > 0) {
                data[arr[0]] = arr[1]
            }
        }
    })
    return data
}
function log(msg) {
    console.log(msg)
        // if (typeof msg === 'object') {
        //     msg = JSON.stringify(msg)
        // }
        // alert(msg)
}
// // 系统出错截取
// window.onerror = function (errorMessage, scriptURI, lineNumber, columnNumber, errorObj) {
//     alert('错误信息：' + JSON.stringify(errorMessage))
//     alert('出错文件：' + JSON.stringify(scriptURI))
//     alert('出错行号：' + JSON.stringify(lineNumber))
//     alert('出错列号：' + JSON.stringify(columnNumber))
//     alert('错误详情：' + JSON.stringify(errorObj))
// }
function setCookie (c_name, value, expiredays) {
    var exdate = new Date()
    exdate.setDate(exdate.getDate() + expiredays)
    document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
}
// setCookie('mediaSession', '1f1815e0-1505-4e61-a72c-6e04ad548efe', 1)
// setCookie('mediaId', 'pl123_app', 1)
axios.interceptors.response.use(function(response) {
    var LOGIN_FLAG = 'WEC-HASLOGIN';
    var REDIRECT_KEY = 'WEC-REDIRECTURL';
    // 检查是否登陆，若未登录则跳转到登陆页
    function _checkToLogin(response) {
        if (response && 
            response.data && 
            response.data.datas && 
            (response.data.datas[LOGIN_FLAG] === false) && 
            response.data.datas[REDIRECT_KEY]) {
            response.data['code'] = '0'
            var href = window.location.href
            window.location.href = href.substring(0, href.lastIndexOf('#')) + '#/login'
        }
        if (response && 
            response.data && 
            (response.data['code'] === '403') ) {
            response.data['code'] = '0'
            var href = window.location.href
            window.location.href = href.substring(0, href.lastIndexOf('#')) + '#/login'
        }
        
    }
    _checkToLogin(response)
    return response
})
export default {
    httpGet: httpGet,
    httpGetQuery: httpGetQuery,
    httpPost: httpPost,
    httpPostQuery: httpPostQuery,
    errorHandle: errorHandle,
    getQueryParam: getQueryParam,
    getAllQueryParams: getAllQueryParams,
    handler: {
        CODE: _code,
        ROWS: _rows,
        DATAS: _datas,
        FIRST_ROW: _firstRow
    },
    log: log
}