/**
 * 编译、打包参数配置
 */

const CSS_LIBS = [
    '//cdn.bootcss.com/normalize/7.0.0/normalize',
    '//cdn.bootcss.com/iview/2.8.0/styles/iview.css',
    '//feres.cpdaily.com/custom/forNOBH/position_assistance.css'
];

const JS_LIBS = [
    '//cdn.bootcss.com/vue/2.2.4/vue',
    '//cdn.bootcss.com/vue-router/2.2.1/vue-router',
    '//cdn.bootcss.com/vue-i18n/5.0.3/vue-i18n',
    '//cdn.bootcss.com/vuex/2.2.1/vuex',
    '//cdn.bootcss.com/axios/0.15.3/axios',
    '//cdn.bootcss.com/iview/2.8.0/iview',
    '//cdn.bootcss.com/jquery/3.2.1/jquery',
];

module.exports = {
    proxy: {
        // '/wec-cpdaily-media': 'http://172.16.6.150:31247' // 测试 -- 活动
        // '/wec-cpdaily-media': 'http://iwecloud0:31607' // 测试
        '/wec-cpdaily-media': 'http://172.20.6.75:8081' // 某东
    },
    alias: {
        '@': 'src',
        'com': 'src/pages/components',
        'services': 'src/services',
        'conf': 'src/config',
        'api': 'src/config/api.js',
        'util': 'src/config/util.js'
    },
    port: 3000, // can be overwritten by process.env.PORT, if port is in use, a free one will be determined
    // babelDir: [/bh-vue/, /wec-vue/], // 指定允许使用babel-loader编译的文件目录或路径匹配，默认已支持src
    // loaders: [], // 增加其他文件类型的loader，默认已支持 vue
    csslibs: CSS_LIBS,
    jslibs: JS_LIBS,
    distDir: '../webapp/mobile',
    title: '校园号平台', // index文件的title
}
