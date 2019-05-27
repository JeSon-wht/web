define(function () {
    /**
     * 注意：只能在初始化脚本的时候调用
     * @returns {string} 当前初始化脚本路径
     */
    var _currentScript = function () {
        try {
            throw Error();
        } catch (e) {
            if (e.stack) {
                return (e.stack.substring(e.stack.lastIndexOf('\n', e.stack.length - 2)).match(/((http(s?)|file):\/{2,3}\S+\/\S+\.[a-z0-9]+)/i) || ['', ''])[1];
            } else if (e['stacktrace']) {
                return (e['stacktrace'].match(/\(\) in\s+(.*?:\/\/\S+)/m) || ["", ""])[2];
            } else { // ie9 及以下
                var scripts = document.scripts;
                for (var i = scripts.length - 1; i >= 0; i--) {
                    if (scripts[i].readyState === 'interactive') {
                        return scripts[i].src;
                    }
                }
            }
        }
    };
    return {
        requireType: {
            TEXT: 'text!',
            CSS: 'css!'
        },
        moduleName: function () {
            var src = _currentScript();
            var idx = 0 === src.indexOf('/') ? 0 : src.indexOf(location.host) + location.host.length;
            return src.substring(idx, src.lastIndexOf('/') + 1);
        },
        showModule: function (path, opts) {
            // console.log('showModule: path=' + path + ', module=' + module + ', opts=' + opts);
            if (typeof path === 'object') {
                opts = path;
                path = ctx;
            } else if (!path) {
                path = ctx;
            } else if (path.lastIndexOf('/') + 1 !== path.length) {
                path += '/';
            }
            require([path + 'init.js'], function (Module) {
                var module = new Module(opts);
                module.install(module, module.elem);
            });
        },
        modulePath: function (type, file, module) {
            var rt = this.requireType;
            if (type === '') {
                var idx = file.lastIndexOf('.');
                if (idx < 0 || file.length > idx + 2) {
                    file += '.js';
                }
            } else if (type.lastIndexOf('!') < 0 || type !== rt.TEXT && type !== rt.CSS) {
                module = file;
                file = type;
                type = rt.TEXT;
            }

            if (!file || file.lastIndexOf('.') < 0) {
                module = file;
                if (type === rt.TEXT) {
                    file = 'index.html';
                } else if (type === rt.CSS) {
                    file = 'style.css';
                }
            }

            return type + module + file;
        }
    }
});