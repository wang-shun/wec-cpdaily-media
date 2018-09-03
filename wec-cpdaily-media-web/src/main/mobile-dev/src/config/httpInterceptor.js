/**
 * 一些通用请求拦截处理 (比如 session 失效)
 * 
 * 用于公有云内控业务
 */

(function(global, undefined) {

    var LOGIN_FLAG = 'WEC-HASLOGIN';
    var REDIRECT_KEY = 'WEC-REDIRECTURL';

    // 检查是否登陆，若未登录则跳转到登陆页
    function _checkToLogin(response) {
        
        if (response && 
            response.datas && 
            (response.datas[LOGIN_FLAG] === false) && 
            response.datas[REDIRECT_KEY]) {
            top.location.href = response.datas[REDIRECT_KEY];
        }
    }

    /**
     * 拦截基于 vue-resource 发送的请求
     */
    function _vueIntercept() {
        if ((typeof Vue === 'undefined') || !Vue.http) { // no vue or vue-resource in use
            return;
        }

        Vue.http.interceptors.push(function(request, next) {
            next(function(response) {
                _checkToLogin(response && response.body);
            });
        });
    }

    /**
     * 拦截基于 axios 发送的请求
     */
    function _axiosIntercept() {
        if (typeof axios === 'undefined') {
            return;
        }

        axios.interceptors.response.use(function(response) {
            _checkToLogin(response && response.data);
        });
    }

    /**
     * 拦截基于 jquery ajax 发送的请求
     */
    function _jqIntercept() {
        if (typeof $ === 'undefined') { // no jquery in use
            return;
        }

        $.ajaxSetup({
            complete: function(xhr, textStatus) {
                var result = xhr.responseText;
                if (!result) {
                    return;
                }

                try { // 防止json对象转换失败
                    var response = JSON.parse(result);
                    _checkToLogin(response);
                } catch (e) {
                    return;
                }
            }
        });
    }

    _vueIntercept();
    _axiosIntercept();
    _jqIntercept();

})(window);
