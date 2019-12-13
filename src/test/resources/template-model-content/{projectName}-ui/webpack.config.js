const path = require('path');
const {CleanWebpackPlugin} = require('clean-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const {args2options} = require('peacetrue-js/src/node');
let options = args2options(process.argv, '-pt:');
//生成一个源文件、一个压缩文件和一个source map 文件

let config = {
    mode: 'production',
    entry: {
        '${name}List': './src/demo_list.js',
    },
    devtool: 'source-map',
    plugins: [
        new CleanWebpackPlugin(),
    ],
    devServer: {
        contentBase: './'
    },
    module: {
        rules: [{test: /\.css$/, use: ['style-loader', 'css-loader']}]
    },
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename(chunkData) {
            let name = chunkData.chunk.name.replace(/([A-Z])/g, (value) => '_' + value.toLowerCase()).substr(1);
            return `${name}.${options.mode === 'production' ? 'min.' : ''}js`;
        },
        library: '[name]',
        libraryExport: '',
        libraryTarget: 'umd',
        globalObject: 'this',
    },
    externals: {
        'vue': 'vue',
        'iview/dist/iview': 'iview',
        'axios': 'axios',
        'lodash': {
            root: '_',
            commonjs: 'lodash',
            commonjs2: 'lodash',
            amd: 'lodash',
        },
        'peacetrue-iview/dist/components/page-table': {
            root: ['PeaceIview', 'PageTable'],
            commonjs: 'peacetrue-iview/src/components/page-table',
            commonjs2: 'peacetrue-iview/src/components/page-table',
            amd: 'peacetrue-iview/src/components/page-table'
        },
        'peacetrue-async-validator/dist/peace.async-validator': {
            root: ['Peace', 'AsyncValidator'],
            commonjs: 'peacetrue-async-validator/dist/peace.async-validator',
            commonjs2: 'peacetrue-async-validator/dist/peace.async-validator',
            amd: 'peacetrue-async-validator/dist/peace.async-validator'
        },
    }
};

/* prefix:--
plugins|p=html,clean  //指定使用的插件
*/
function formatAlias(options, alias) {
    Object.keys(alias).forEach(key => {
        if (key in options) {
            options[alias[key]] = options[key];
        }
    });
}

formatAlias(options, {p: 'plugins'});
if (options.plugins && options.plugins.indexOf('html') > -1) {
    config.plugins.push(new HtmlWebpackPlugin({
            title: 'Test',
            inject: 'head',
            template: 'test/demo-list.ejs'
        })
    );
}
module.exports = config;