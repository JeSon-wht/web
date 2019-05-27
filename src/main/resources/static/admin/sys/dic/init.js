(function (moduleName) {
    define(['treegrid', 'Base', Base.modulePath(moduleName)], function (TreeGrid, Base, template) {
        return TreeGrid.extend({
            install: function (me) {
                me.elem.html(template);
                me.render({
                    elem: $('#dicTreeGrid'),
                    url: ctx + 'dic/children',
                    caption: '字典列表',
                    colModel: [{
                        name: 'label',
                        label: '字典名称'
                    }, {
                        name: 'code',
                        label: '字典代码'
                    }, {
                        name: 'type',
                        label: '字典类型'
                    }, {
                        name: 'filter',
                        label: '过滤器'
                    }, {
                        name: 'sortCode',
                        label: '排序'
                    }],
                    btns: [
                        {
                            class: 'btn-info',
                            title: '编辑',
                            func: 'goForm',
                            icon: 'glyphicon glyphicon-edit'
                        },
                        {
                            class: 'btn-warning',
                            title: '增加下级字典',
                            func: 'goForm',
                            icon: 'glyphicon glyphicon-plus',
                            data: [function (row) {
                                var colName = 'label';
                                return {
                                    name: colName,
                                    value: $('<div>' + row[colName] + '</div>').text()
                                }
                            }]
                        },
                        {
                            class: 'btn-danger',
                            title: '删除',
                            func: 'del',
                            icon: 'glyphicon glyphicon-remove-sign'
                        }
                    ]
                });
            },

            goForm: function (e, elem, data) {
                var me = this;
                var opts = {
                    ctn: me.ctn
                };
                if (data.id && data.label) {
                    opts.pId = data.id;
                    opts.pLabel = data.label;
                } else if (data.id) {
                    opts.id = data.id;
                }
                Base.showModule(moduleName + 'form', opts);
            },

            del: function (e, elem, data) {
                var me = this;
                Base.confirm('确定要删除该字典数据吗？', function () {
                    Base.ajax('dic/del/' + data.id, function (resp) {
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