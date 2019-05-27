(function (moduleName) {
    define(['jquery', 'Base', Base.modulePath(moduleName),
        pluginJS('jsencrypt/jsencrypt.min')], function ($, Base, template) {
        return Base.extend({
            install: function () {
                $(document.body).attr('class', 'hold-transition login-page');
                this.elem.html(template);
                var msgElem = $('.login-box-msg');

                $('#login').on('click', function () {
                    var account = $('#account').val();
                    if (!/^[0-9a-zA-Z]{5,16}$/.test(account)) {
                        msgElem.text('账号必须为6-16个英文字母或数字');
                        return false;
                    }

                    var password = $('#password').val();
                    if (password.length < 1) {
                        msgElem.text('密码不能为空');
                        return false;
                    }

                    Base.ajax('key', function (resp) {
                        var publicKey = resp.data;
                        var server = new window.JSEncryptExports.JSEncrypt();
                        server.setPublicKey(publicKey);
                        Base.ajax('login', {
                            key: publicKey,
                            auth: server.encrypt(account + '&' + password)
                        }, function (resp) {
                            window.token = resp.data;
                            Base.showModule('admin');
                        }, function (jqXHR, statusText, error) {
                            if (error.data) {
                                msgElem.text(error.data);
                            }
                        });
                    });
                    return false;
                });
            }
        })
    });
}(Base.moduleName()));