(function (moduleName) {
    define(['grid', 'Base', Base.modulePath(moduleName)], function (Grid, Base, template) {
        return Grid.extend({
            install: function (me) {
                Base.getDrop('RIGHT_TYPE', function (resp) {
                    me.elem.html(Base.compile(template)(resp));
                    me.render({
                        elem: $('#roleRightGrid'),
                        url: ctx + 'role/right/query',
                        postData: {
                            roleId: me.data.roleId,
                            own: '1'
                        },
                        caption: '权限列表',
                        colModel: [
                            {
                                name: 'name',
                                label: '权限',
                                align: 'center'
                            }, {
                                name: 'type',
                                label: '权限类型',
                                align: 'center'
                            }, {
                                name: 'remark',
                                label: '备注信息',
                                align: 'center'
                            }
                        ],
                        multiselect: true
                    });
                });
            },

            close: function (e, elem) {
                var me = this;
                Base.showModule(moduleName + '..', {
                    ctn: me.ctn
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
                var rightIds = $('#roleRightGrid').jqGrid('getGridParam', 'selarrrow');
                if (rightIds.length == 0) {
                    Base.error("抱歉,您没有选择要删除的权限!");
                    return false;
                }
                Base.ajax('role/right/cancel', {
                    roleId: me.data.roleId,
                    rightIds: rightIds
                }, function () {
                    Base.success("删除成功!");
                    me.search(e, elem, null, null);
                })
            }

        });
    });
})(Base.moduleName());