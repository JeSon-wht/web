(function (moduleName) {
    define(['Base', Base.modulePath(moduleName)], function (Base, template) {
        return Base.extend({
            install: function (me) {
                if (me.id) {
                    Base.ajax('role/id/' + me.id, function (resp) {
                        resp.data.classify = resp.data.classify.substr(0,1);
                        me.render($.extend({}, me.data, resp.data));
                    });
                } else {
                    me.render(me.data);
                }
            },

            render: function (data) {
                var me = this;
                var html = Base.compile(template)(data);
                Base.validate(me.elem.html(html).find('form'), function (form) {
                    var formData = form.serializeObject();
                    var url = 'role/';
                    if (me.id) {
                        formData.id = me.id;
                        url += 'update';

                    } else {
                        url += 'save';
                    }
                    Base.ajax(url, formData, function (resp) {
                        if(resp.data.error){
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