(function (moduleName) {
    define(['grid', 'Base', Base.modulePath(moduleName)], function (Grid, Base, template) {
        return Grid.extend({
            install: function (me) {
                me.elem.html(template);
                me.render({
                    elem: $("#userRoleGrid"),
                    postData: {userId: me.data.userId},
                    url: ctx + 'user/role/query',
                    caption: '用户已有角色列表',
                    multiselect: true,
                    colModel: [{
                        name: 'name',
                        label: '角色名称',
                        align: 'center'
                    }, {
                        name: 'remark',
                        label: '备注信息',
                        align: 'center'
                    }]
                });
            },

            add: function (e, elem) {
                var me = this;
                Base.showModule(moduleName + 'list', {
                    data: {
                        userId: me.data.userId
                    },
                    ctn: me.ctn
                });
            },

            close: function (e, elem) {
                var me = this;
                Base.showModule(moduleName + '..', {
                    ctn: me.ctn
                });
            },

            cancel: function (e, elem) {
                var me = this;
                var roleIds = $('#userRoleGrid').jqGrid('getGridParam', 'selarrrow');
                if (roleIds.length == 0) {
                    Base.error('您还没选择要取消的角色');
                    return false;
                }

                Base.confirm('确定要取消这些角色吗？', function () {
                    Base.ajax('user/role/cancel', {
                        userId: me.data.userId,
                        roleIds: roleIds
                    }, function () {
                        Base.showModule(moduleName, {
                            data: {
                                userId: me.data.userId
                            },
                            ctn: me.ctn
                        });
                    });
                });
            }

        });
    });
})(Base.moduleName());