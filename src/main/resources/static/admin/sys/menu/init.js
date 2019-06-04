(function (moduleName) {
    define(['treegrid','Base', Base.modulePath(moduleName)], function (TreeGrid, Base, template) {
        return TreeGrid.extend({
            install: function (me) {
                me.elem.html(template);
                me.render({
                    elem:$("#menuTreeGrid"),
                    url: ctx + 'menu/children',
                    caption:'菜单列表',
                    colModel:[{
                        name:'name',
                        label:'名称'
                    },{
                        name:'href',
                        label:'链接'
                    },{
                        name:'sortCode',
                        label:'排序码'
                    }],
                    btns:[{
                        class: 'btn-info',
                        title: '编辑',
                        func: 'goForm',
                        icon: 'glyphicon glyphicon-edit'
                    },{
                        class: 'btn-danger',
                        title: '删除',
                        func: 'del',
                        icon: 'glyphicon glyphicon-remove-sign'
                    },{
                        class: 'btn-primary',
                        title: '添加子菜单',
                        func: 'goForm',
                        icon: 'glyphicon glyphicon-plus',
                        data: [function (row) {
                            var colName = 'name';
                            return {
                                name: colName,
                                value: $('<div>' + row[colName] + '</div>').text()
                            }
                        }]
                    }]
                });
            },
            goForm: function (e, elem, data) {
                var me = this;
                var opts = {
                    ctn: me.ctn
                };
                if(data.id && data.name){
                    opts.pId = data.id;
                    opts.pName = data.name;
                }else if(data.id){
                    opts.id = data.id;
                }
                Base.showModule(moduleName + 'form', opts);
            },
            del: function (e, elem, data) {
                var me = this;
                Base.confirm('确定删除该菜单吗？', function () {
                    Base.ajax('menu/del/' + data.id, function (resp) {
                        if (resp.msg) {
                            Base.error('删除失败', resp.msg);
                        } else {
                            Base.showModule(moduleName, {
                                ctn: me.ctn
                            });
                        }
                    });
                })
            }
        });
    });
})(Base.moduleName());
