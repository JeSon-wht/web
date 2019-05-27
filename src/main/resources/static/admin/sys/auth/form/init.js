(function (moduleName) {
    define(['Base', 'Tree', Base.modulePath(moduleName)], function (Base, Tree, template) {
        return Base.extend({
            install: function (me) {
                Base.ajax('user/auth', function (resp) {
                    me.render($.extend({}, me.data, resp.data));
                });
            },

            render: function (data) {
                var me = this;
                Base.getDrop('SEX', function (resp, type) {
                    var SEX = resp[type];
                    data.SEX = SEX;
                    var html = Base.compile(template)(data);
                    Base.validate(me.elem.html(html).find('form'), function (form) {
                        var data = form.serializeObject();
                        Base.ajax('user/publicupdate', data, function (resp) {
                            if (resp.msg) {
                                Base.error('更新失败', resp.msg);
                            } else {
                                Base.success('更新成功');
                                if (resp.data) {
                                    me.id = resp.data;
                                }
                            }
                        });
                        return false;
                    });
                    Tree.render({
                        elem: $("#deptTree"),
                        url: function (treeId, node) {
                            return 'tree/dept/children/' + (node && node.id || '-');
                        },
                        click: function (e, node) {
                            $("#deptId").val(node.id);
                            $("#deptName").text(node.label).click();
                        }
                    });
                });

            },

            goBack: function (e, elem) {
                var me = this;
                Base.showModule(moduleName + '../../..', {
                    ctn: me.ctn
                });
            }
        });
    });
})(Base.moduleName());