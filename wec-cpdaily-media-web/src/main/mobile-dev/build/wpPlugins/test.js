// htmlPluginData的内容
{
    html: '<!DOCTYPE html>\n<html>\n\n<head>\n    <meta charset="utf-8">\n    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">\n    <meta http-equiv="Pragma" content="no-cache">\n    <meta http-equiv="Cache-Control" content="no-cache">\n    <meta http-equiv="Expires" content="0">\n    <link rel="stylesheet" href="https://feres.cpdaily.com/fe_components/iconfont/iconfont.css">\n    <link rel="stylesheet" href="https://feres.cpdaily.com/fe_components/iconfont_2.0/iconfont.css">\n    <link rel="stylesheet" href="./statics/css/main.css">\n    <style type="text/css">.img-none-for-spider {display: none;}</style>\n    <title>MBTI职业性格测试</title>\n</head>\n\n<body>\n    <div id="app"></div>\n    <img class="img-none-for-spider" src="https://microapp.cpdaily.com/commonservices/mbti/statics/imgs/bac_begin.png"></img>\n    <!-- <script src="./statics/js/flexible.js"></script> -->\n    <script src="https://feres.cpdaily.com/bower_components/jquery/dist/jquery.min.js"></script>\n    <script src="https://feres.cpdaily.com/bower_components/axios/axios.min.js"></script>\n    <script src="https://feres.cpdaily.com/fe_components/micro/js/fcUtil.js"></script>\n    <script src="https://feres.cpdaily.com/fe_components/mobile/BH_MOBILE_SDK.js"></script>\n    <!-- built files will be auto injected -->\n    <!-- zsl-mark -->\n</body>\n\n</html>\n',
    assets: {
        publicPath: './',
        chunks: { manifest: [Object], vendor: [Object], app: [Object] },
        js: ['./statics/js/manifest.c9410bf6de65c7667da0.js',
            './statics/js/vendor.a6672d41b40f229628a7.js',
            './statics/js/app.7def121e51b9e9f6bac7.js'
        ],
        css: ['./statics/css/app.a1a0d3b45018b97796ec6136365e403c.css'],
        manifest: undefined
    },
    plugin: HtmlWebpackPlugin {
        options: {
            template: 'E:\\项目\\小程序\\mbti\\app\\node_modules\\html-webpack-plugin\\lib\\loader.js!E:\\项目\\小程序\\mbti\\app\\index.html',
            filename: 'index.html',
            hash: false,
            inject: true,
            compile: true,
            favicon: false,
            minify: [Object],
            cache: true,
            showErrors: true,
            chunks: 'all',
            excludeChunks: [],
            title: 'Webpack App',
            xhtml: false,
            chunksSortMode: 'dependency'
        },
        childCompilerHash: '1fec625a69feb9a8badae93f524d44ed',
        childCompilationOutputName: 'index.html',
        assetJson: '["./","./statics/css/app.a1a0d3b45018b97796ec6136365e403c.css","./statics/js/app.7def121e51b9e9f6bac7.js","./statics/js/manifest.c9410bf6de65c7667da0.js","./statics/js/vendor.a6672d41b40f229628a7.js"]'
    },
    outputName: 'index.html'
}
