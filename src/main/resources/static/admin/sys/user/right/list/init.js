(function (moduleName) {
    define(['grid', 'Base', Base.modulePath(moduleName)], function (Grid, Base, template) {
        return Grid.extend({
            install: function (me) {
                Base.getDrop('RIGHT_TYPE', function (resp) {
                    me.elem.html(Base.compile(template)(resp));
                    me.render({
                        elem: $('#userRightNoGrid'),
                        postData: {userId: me.data.userId, own: '0'},
                        url: ctx + 'user/right/query/',
                        caption: '权限列表',
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

            save: function (e, elem) {
                var me = this;
                var rightIds = $('#userRightNoGrid').jqGrid('getGridParam', 'selarrrow');
                if (rightIds.length == 0) {
                    Base.error('抱歉，您还没有选择你要添加的权限');
                    return false;
                }
                Base.ajax('user/right/grant', {
                    userId: me.data.userId,
                    rightIds: rightIds
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
            },

        });
    });
})(Base.moduleName());