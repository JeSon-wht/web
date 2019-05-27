(function (moduleName) {
    define(['grid', 'Base', Base.modulePath(moduleName)], function (Grid, Base, template) {
        return Grid.extend({
            install: function (me) {
                me.elem.html(template);
                me.render({
                    elem: $('#roleGrid'),
                    url: ctx + 'role/query',
                    caption: '角色列表',
                    colModel: [
                        {
                            name: 'name',
                            label: '角色名称',
                            align: 'center'
                        }, {
                            name: 'remark',
                            label: '备注信息',
                            align: 'center'
                        }
                    ],
                    btns: [
                        {
                            class: 'btn-info',
                            title: '修改',
                            func: 'goForm',
                            icon: 'glyphicon glyphicon-edit'
                        }, {
                            class: 'btn-danger',
                            title: '删除',
                            func: 'del',
                            icon: 'glyphicon glyphicon-remove-sign'
                        }, {
                            class: 'btn-warning',
                            title: '添加权限',
                            func: 'addRight',
                            icon: 'glyphicon glyphicon-plus'
                        }, {
                            class: 'btn-success',
                            title: '分配用户',
                            func: 'addUser',
                            icon: 'glyphicon glyphicon-user'
                        }
                    ]
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
                Base.confirm('确定要删除该角色吗？', function () {
                    Base.ajax('role/del/' + data.id, function (resp) {
                        if (resp.msg) {
                            Base.error('删除失败' + resp.msg);
                        } else {
                            Base.success('删除成功');
                            Base.showModule(moduleName, {
                                ctn: me.ctn
                            });
                        }
                    });
                });
            },

            addRight: function (e, elem, data) {
                var me = this;
                Base.showModule(moduleName + 'right', {
                    data: {
                        roleId: data.id
                    },
                    ctn: me.ctn
                });
            },

            addUser: function (e, elem, data) {
                var me = this;
                Base.showModule(moduleName + 'user', {
                    data: {
                        roleId: data.id
                    },
                    ctn: me.ctn
                });
            }
        });
    });
})(Base.moduleName());