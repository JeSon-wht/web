(function (moduleName) {
    define(['grid', 'Base', Base.modulePath(moduleName)], function (Grid, Base, template) {
        return Grid.extend({
            install: function (me) {
                me.elem.html(template);
                me.render({
                    elem: $("#roleUserListGrid"),
                    url: ctx + 'role/user/query',
                    postData: {
                        roleId: me.data.roleId,
                        own: '0'
                    },
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

            save: function (e, elem) {
                var me = this;
                var userIds = $('#roleUserListGrid').jqGrid('getGridParam', 'selarrrow');
                if (userIds.length == 0) {
                    Base.error("抱歉,您没有选择要添加的用户!");
                    return false;
                }
                Base.ajax('role/user/grant', {
                    roleId: me.data.roleId,
                    userIds: userIds
                }, function () {
                    Base.success("添加成功!");
                    me.search(e, elem, null, null);
                });
            },

            close: function (e, elem) {
                var me = this;
                Base.showModule(moduleName + '..', {
                    data: {
                        roleId: me.data.roleId
                    },
                    ctn: me.ctn
                });
            }

        });
    });
})(Base.moduleName());