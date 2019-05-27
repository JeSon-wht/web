(function (moduleName) {
    define(['Base', Base.modulePath(moduleName)], function (Base, template) {
        return Base.extend({
            install: function (me) {
                var html = Base.compile(template);
                me.elem.html(html);
            },

            change: function (e, elem, data) {
                var me = this;
                var val = $("#oldPassword").val();
                var val1 = $("#newPassword").val();
                var val2 = $("#confirmPassword").val();
                $("#check").text("");
                if (val1 != val2) {
                    $("#check").text('与新密码不一致').css("color","red");
                    return false;
                }
                Base.ajax('user/pwd/alter', {id: me.data.id, oldPassword: val, newPassword: val1}, function (resp) {
                    if (resp.data == 1) {
                        Base.success("修改成功");
                        Base.showModule(moduleName + '..', {
                            ctn: me.ctn
                        });
                    } else {
                        Base.error(resp.data);
                    }
                });
            },

            goBack: function (e, elem) {
                var me = this;
                Base.showModule(moduleName + '..', {
                    ctn: me.ctn
                });
            }
        });
    });
})(Base.moduleName());