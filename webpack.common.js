const path = require('path');

module.exports = {
    entry: './src/main/js/app.js',
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                loader: 'babel-loader',
                query: {
                    cacheDirectory: true,
                    presets: [
                        'babel-preset-env',
                        'react',
                        'stage-2'
                    ]
                }
            }
        ]
    },
};