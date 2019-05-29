(function (moduleName) {
    define(['grid', 'Base', Base.modulePath(moduleName)], function (Grid, Base, template) {
        return Grid.extend({
            install: function (me) {
                Base.getDrop('SEX', function (resp, type) {
                    me.elem.html(Base.compile(template)(resp));
                    var SEX = resp[type];
                    me.render({
                        elem: $('#userGrid'),
                        url: ctx + 'user/query',
                        caption: '用户列表',
                        colModel: [{
                            name: 'account',
                            label: '账号',
                            align: 'center'
                        }, {
                            name: 'name',
                            label: '姓名',
                            align: 'center'
                        }, {
                            name: 'email',
                            label: '邮箱',
                            align: 'center'
                        }, {
                            name: 'phone',
                            label: '电话',
                            align: 'center'
                        }, {
                            name: 'sex',
                            label: '性别',
                            align: 'center',
                            formatter: function (val) {
                                return Base.getLabel(SEX, val);
                            }
                        }],
                        btns: [
                            {
                                class: 'btn-info',
                                title: '编辑',
                                func: 'goForm',
                                icon: 'glyphicon glyphicon-edit',
                                SEX: SEX
                            },
                            {
                                class: 'btn-warning',
                                title: '重置密码',
                                func: 'resetPassword',
                                icon: 'glyphicon glyphicon-repeat'
                            },
                            {
                                class: 'btn-danger',
                                title: '删除',
                                func: 'del',
                                icon: 'glyphicon glyphicon-remove-sign'
                            },
                            {
                                class: 'btn-success',
                                title: '分配角色',
                                func: 'addRole',
                                icon: 'glyphicon glyphicon-user'
                            },
                            {
                                class: 'btn-danger',
                                title: '修改密码',
                                func: 'changePassword',
                                icon: 'glyphicon glyphicon-pencil'
                            }
                        ]
                    });
                });
            },

            goForm: function (e, elem, data) {
                var me = this;
                var opts = {
                    data: {
                        SEX: me.opts.btns[0].SEX
                    },
                    ctn: me.ctn
                };
                if (data.id) {
                    opts.id = data.id;
                }
                Base.showModule(moduleName + 'form', opts);
            },

            resetPassword: function (e, elem, data) {
                var me = this;
                var url = "user/pwd/reset?id="+data.id;
                var xhr = new XMLHttpRequest();
                xhr.open('GET', url, true);
                xhr.responseType = "blob";   //存储二进制文件的容器
                xhr.onload = function () {
                    if (this.status === 200&& this.readyState ===4) {
                        var blob = this.response;
                        var reader = new FileReader();
                        reader.readAsDataURL(blob);
                        reader.onload = function () {
                            var a = document.createElement('a');
                            a.href = window.URL.createObjectURL(blob);
                            a.download = 'password.txt';
                            $("body").append(a);    // 修复firefox中无法触发click
                            a.click();
                            $(a).remove();
                        }
                    }else{
                        Base.error("下载失败");
                    }
                }
                xhr.send();
            },

            del: function (e, elem, data) {
                var me = this;
                Base.confirm('确定删除该用户吗？', function () {
                    Base.ajax('user/del/' + data.id, function (resp) {
                        if (resp.msg) {
                            Base.error('删除失败', resp.msg);
                        } else {
                            me.search(e, elem, data, null);
                        }
                    });
                })
            },

            addRole: function (e, elem, data) {
                var me = this;
                Base.showModule(moduleName + 'role', {
                    data: {
                        userId: data.id
                    },
                    ctn: me.ctn
                });
            },
            changePassword: function (e, elem, data) {
                var me = this;
                Base.showModule(moduleName + 'change', {
                    data: {
                        id: data.id
                    },
                    ctn: me.ctn
                });
            }
        });
    });
})(Base.moduleName());