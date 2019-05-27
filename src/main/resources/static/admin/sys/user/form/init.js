(function (moduleName) {
    define(['Base', 'Tree', Base.modulePath(moduleName)], function (Base, Tree, template) {
        return Base.extend({
            install: function (me) {
                if (me.id) {
                    Base.ajax('user/id/' + me.id, function (resp) {
                        me.render($.extend({}, me.data, resp.data));
                    });
                } else {
                    me.render(me.data);
                }
            },

            render: function (data) {
                var me = this;
                if (!data.SEX) {
                    data.SEX = '1'
                }
                var html = Base.compile(template)(data);
                Base.validate(me.elem.html(html).find('form'), function (form) {
                    var data = form.serializeObject();
                    var url = 'user/';
                    if (me.id) {
                        data.id = me.id;
                        url += 'update';
                    } else {
                        url += 'save';
                    }
                    Base.ajax(url, data, function (resp) {
                        if(url === 'user/save' && resp.data.error){
                            Base.error(resp.data.error);
                        } else if (resp.msg) {
                            Base.error((me.id ? '更新' : '新增') + '失败', resp.msg);
                        } else {
                            Base.success((me.id ? '更新' : '新增') + '成功');
                            if (resp.data) {
                                me.id = resp.data;
                            }
                            Base.showModule(moduleName + '..', {
                                ctn: me.ctn
                            });
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