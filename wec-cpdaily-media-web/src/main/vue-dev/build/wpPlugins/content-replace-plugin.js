var opts = [];

function ContentReplacePlugin(options) {
	opts = options;
}

ContentReplacePlugin.prototype.apply = function(compiler) {
    compiler.plugin('compilation', function(compilation) {
        compilation.plugin('html-webpack-plugin-before-html-processing', function(htmlPluginData, callback) {
        	opts.forEach(function (option) {
        		htmlPluginData.html = htmlPluginData.html.replace(option.templateString, option.newString);
        	});
            callback(null, htmlPluginData);
        });
    });

};

module.exports = ContentReplacePlugin;
