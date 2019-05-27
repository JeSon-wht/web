(function (moduleName) {
    define(['grid', 'Base', Base.modulePath(moduleName)], function (Grid, Base, template) {
        return Grid.extend({
            install: function (me) {
                Base.getDrop('RIGHT_TYPE', function (resp) {
                    me.elem.html(Base.compile(template)(resp));
                    me.render({
                        elem: $('#rightGrid'),
                        url: ctx + 'right/query',
                        caption: '权限列表',
                        colModel: [
                            {
                                name: 'name',
                                label: '权限名称',
                                align: 'center'
                            },
                            {
                                name: 'remark',
                                label: '备注信息',
                                align: 'center'
                            }
                        ],
                        btns: [
                            {
                                class: 'btn-info',
                                title: '编辑',
                                func: 'goForm',
                                icon: 'glyphicon glyphicon-edit'
                            },
                            {
                                class: 'btn-danger',
                                title: '删除',
                                func: 'del',
                                icon: 'glyphicon glyphicon-remove-sign'
                            }
                        ]
                    });
                });
            },

            goForm: function (e, elem, data) {
                var me = this;
                var opts = {
                    ctn: me.ctn
                };
                if (data.id) {
                    opts.id = data.id;
                }
                Base.showModule(moduleName + 'form', opts);
            },

            del: function (e, elem, data) {
                var me = this;
                Base.confirm('确定要删除该权限吗？', function () {
                    Base.ajax('right/del/' + data.id, function (resp) {
                        if (resp.msg) {
                            Base.error('删除失败' + resp.msg);
                        } else {
                            Base.showModule(moduleName, {
                                ctn: me.ctn
                            });
                        }
                    });
                });
            },

        });
    });
})(Base.moduleName());