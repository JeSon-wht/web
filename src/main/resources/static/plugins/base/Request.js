define({
    ajax: function (url, data, successFunc, options) {
        var opts = {
            type: 'post',
            dataType: 'json'
        };

        if (data) {
            if (typeof data === 'function') {
                options = successFunc;
                successFunc = data;
            } else {
                opts.data = data;
            }
        }

        if (!opts.data && url.substring(url.lastIndexOf('/') + 1).indexOf('.') > 0) {
            opts.type = 'get';
        }

        if (successFunc) {
            opts.success = function (resp, statusText, jqXHR) {
                if (resp.code === 0)
                    successFunc(resp, statusText, jqXHR);
                else
                    opts.error(jqXHR, statusText, resp);

            }
        }

        var errorFunc;
        if (typeof options === 'function') {
            errorFunc = options;
        } else if (options) {
            if (options.error) {
                errorFunc = options.error;
                delete options.error;
            }
            $.extend(opts, options);
        }

        opts.error = function (jqXHR, statusText, error) {
            if (errorFunc) {
                errorFunc(jqXHR, statusText, error);
            } else if (typeof error.code === 'number') {
                var msg = '';
                switch (error.code) {
                    case 1:
                        msg = '参数错误';
                        break;
                    case 2:
                        msg = '处理超时';
                        break;
                    case 3: {
                        Base.confirm('身份认证失败', '确定将跳转登录页面，本页信息会丢失，请注意备份', function () {
                            Base.showModule('login');
                        });
                        return;
                    }
                    case 4:
                        msg = '无权限';
                        break;
                    case 5:
                        msg = '服务器内部错误';
                        break;
                    case -1:
                        msg = error.msg ? error.msg : '未知错误';
                        break;
                }
                Base.error(msg);
            } else {
                console.error(jqXHR, statusText, error);
            }
        };

        return $.ajax(url, opts);
    },
    getDrop: function (type, filter, callback) {
        var url = ctx + 'dic/' + type + '/';

        if (filter) {
            if (typeof filter === 'function') {
                callback = filter;
                filter = 'N';
            }
        } else {
            filter = 'N';
        }
        url += filter;

        return this.ajax(url, callback && function (resp) {
            var data = {};
            data[type] = resp.data;
            callback(data, type, filter);
        });
    }
});