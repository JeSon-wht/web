(function (moduleName) {
    define(['Base', Base.modulePath(moduleName)], function (Base, template) {
        return Base.extend({
            install: function (me) {
                if (me.id) {
                    $.when(Base.getDrop('RIGHT_TYPE'),Base.ajax('right/id/' + me.id)).done(function (typeResp,resp) {
                        me.data = {
                            RIGHT_TYPE:typeResp[0].data
                        }
                        me.render($.extend({}, me.data, resp[0].data));
                    });
                } else {
                    Base.getDrop('RIGHT_TYPE',function (resp) {
                        me.render(resp);
                    })

                }
            },

            render: function (data) {
                var me = this;

                var html = Base.compile(template)(data);

                Base.validate(me.elem.html(html).find('form'), function (form) {
                    var data = form.serializeObject();
                    var url = 'right/';
                    if (me.id) {
                        data.id = me.id;
                        url += 'update';
                    } else {
                        url += 'save';
                    }
                    Base.ajax(url, data, function (resp) {
                        if (resp.msg) {
                            Base.error((me.id ? '更新' : '新增') + '失败', resp.msg);
                        } else {
                            Base.success((me.id ? '更新' : '新增') + '成功');
                            if (resp.data) {
                                me.id = resp.data;
                            }
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