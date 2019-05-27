(function (moduleName) {
    define(['treegrid', 'Base', Base.modulePath(moduleName)], function (TreeGrid, Base, template) {
        return TreeGrid.extend({
            install: function (me) {
                me.elem.html(template);
                me.render({
                    elem: $('#deptTreeGrid'),
                    url: ctx + 'dept/children',
                    caption: '部门列表',
                    colModel: [{
                        name: 'name',
                        label: '名称',
                        align: 'left'
                    }, {
                        name: 'code',
                        label: '代码'
                    }, {
                        name: 'ext',
                        label: '扩展'
                    }, {
                        name: 'remark',
                        label: '备注信息'
                    }, {
                        name: 'sortCode',
                        label: '排序'
                    }],
                    btns: [
                        {
                            class: 'btn-info',
                            title: '修改',
                            func: 'goForm',
                            icon: 'glyphicon glyphicon-edit'
                        },
                        {
                            class: 'btn-warning',
                            title: '新增下级部门',
                            func: 'goForm',
                            icon: 'glyphicon glyphicon-plus',
                            data: [function (row) {
                                var colName = 'name';
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
                if (data.id && data.name) {
                    opts.pId = data.id;
                    opts.pName = data.name;
                } else if (data.id) {
                    opts.id = data.id;
                }
                Base.showModule(moduleName + 'form', opts);
            },

            del: function (e, elem, data) {
                var me = this;
                Base.confirm('确定要删除该部门及所有子部门吗？', function () {
                    Base.ajax('dept/del/' + data.id, function (resp) {
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