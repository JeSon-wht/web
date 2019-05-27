(function (moduleName) {
    define(['grid', 'Base', Base.modulePath(moduleName)], function (Grid, Base, template) {
        return Grid.extend({
            install: function (me) {
                Base.getDrop('RIGHT_TYPE', function (resp) {
                    me.elem.html(Base.compile(template)(resp));
                    me.render({
                        elem: $('#userRightGrid'),
                        postData: {userId: me.data.userId},
                        url: ctx + 'user/right/query/',
                        caption: '用户已有权限列表',
                        multiselect: true,
                        colModel: [{
                            name: 'name',
                            label: '权限名称',
                            align: 'center'
                        }, {
                            name: 'type',
                            label: '权限类型',
                            align: 'center'
                        }, {
                            name: 'remark',
                            label: '备注信息',
                            align: 'center'
                        }]
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
                        userId: me.data.userId
                    },
                    ctn: me.ctn
                });
            },

            cancel: function (e, elem, data) {
                var me = this;
                var rightIds = $('#userRightGrid').jqGrid('getGridParam', 'selarrrow');
                if (rightIds.length == 0) {
                    Base.error('您还没选择要取消的权限');
                    return false;
                }

                Base.confirm('确定要取消这些权限吗？', function () {
                    Base.ajax('user/right/cancel', {
                        userId: me.data.userId,
                        rightIds: rightIds
                    }, function () {
                        Base.showModule(moduleName, {
                            data: {
                                userId: me.data.userId
                            },
                            ctn: me.ctn
                        });
                    });
                });
            },

        });
    });
})(Base.moduleName());