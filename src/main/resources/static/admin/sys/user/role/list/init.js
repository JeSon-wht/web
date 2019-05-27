(function (moduleName) {
    define(['grid', 'Base', Base.modulePath(moduleName)], function (Grid, Base, template) {
        return Grid.extend({
            install: function (me) {
                me.elem.html(template);
                me.render({
                    elem: $("#userRoleNoGrid"),
                    postData: {userId: me.data.userId, own: '0'},
                    url: ctx + 'user/role/query',
                    caption: '角色列表',
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

            save: function (e, elem) {
                var me = this;
                var roleIds = $('#userRoleNoGrid').jqGrid('getGridParam', 'selarrrow');
                if (roleIds.length == 0) {
                    Base.error('您还没选择要添加的角色');
                    return false;
                }
                Base.ajax('user/role/grant', {
                    userId: me.data.userId,
                    roleIds: roleIds
                }, function () {
                    Base.showModule(moduleName + '..', {
                        data: {
                            userId: me.data.userId
                        },
                        ctn: me.ctn
                    });
                });
            },

            close: function (e, elem) {
                var me = this;
                Base.showModule(moduleName + '..', {
                    data: {
                        userId: me.data.userId
                    },
                    ctn: me.ctn
                });
            }

        });
    });
})(Base.moduleName());