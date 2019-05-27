(function (moduleName) {
    define(['grid', 'Base', Base.modulePath(moduleName)], function (Grid, Base, template) {
        return Grid.extend({
            install: function (me) {
                me.elem.html(template);
                me.render({
                    elem: $("#roleUserGrid"),
                    url: ctx + 'role/user/query',
                    postData: {
                        roleId: me.data.roleId,
                        own: '1'
                    },
                    caption: '角色已授权用户列表',
                    colModel: [
                        {
                            name: 'name',
                            label: '用户名称',
                            align: 'center'
                        }, {
                            name: 'remark',
                            label: '备注信息',
                            align: 'center'
                        }
                    ],
                    multiselect: true
                });
            },

            add: function (e, elem) {
                var me = this;
                Base.showModule(moduleName + 'list', {
                    data: {
                        roleId: me.data.roleId
                    },
                    ctn: me.ctn
                });
            },

            cancel: function (e, elem) {
                var me = this;
                var userIds = $('#roleUserGrid').jqGrid('getGridParam', 'selarrrow');
                if (userIds.length == 0) {
                    Base.error("抱歉,您没有选择要删除的用户!");
                    return false;
                }
                Base.ajax('role/user/cancel', {
                    roleId: me.data.roleId,
                    userIds: userIds
                }, function () {
                    Base.success("删除成功!");
                    me.search(e, elem, null, null);
                })
            },

            close: function (e, elem) {
                var me = this;
                Base.showModule(moduleName + '..', {
                    ctn: me.ctn
                });
            }

        });
    });
})(Base.moduleName());