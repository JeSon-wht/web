window.title = '后台管理';
window.version = '1.0.0';
require.config({
    urlArgs: 'v=' + version
});
(function (moduleName) {
    define([
        'Base', Base.modulePath(moduleName),
        pluginCSS('bootstrap/css/bootstrap'),
        pluginCSS('font-awesome/css/font-awesome'),
        pluginCSS('Ionicons/css/ionicons'),
        pluginCSS('adminlte/css/AdminLTE'),
        pluginCSS('adminlte/css/skins/_all-skins')
    ], function (Base, template) {
        $('title').text(title);
        return Base.extend({
            menu: null,
            install: function (me) {
                if (!window.token) {
                    Base.showModule(ctx + 'login');
                    return;
                }
                $.when(Base.ajax(ctx + 'tree/menu/-'),Base.ajax('auth/info')).done(function (resp, infoResp) {
                    var menu = me.toTree(resp[0].data);
                    me.data = infoResp[0].data;
                    var time = new Date();
                    var month = time.getMonth()+1;
                    var html = Base.compile(template)({
                        plugins: plugins,
                        version: version,
                        title: title,
                        menu: menu,
                        name: me.data,
                        nowTime: time.getFullYear()+'年'+month+'月'+time.getDate()+'日'
                    });
                    me.ctnElem = me.elem.addClass('hold-transition skin-blue-light sidebar-mini fixed').html(html).find('.content-wrapper');
                    require([
                        'Adminlte'
                    ], function () {
                        me.menu = $('.sidebar-menu').data('animationSpeed', '50'); // 配置菜单伸缩时间
                        $(window).load();
                        require([
                            'Adminlte'
                        ], function () {
                            me.menu = $('.sidebar-menu').data('animationSpeed', '50'); // 配置菜单伸缩时间
                            $(window).load();
                            require([
                                Base.modulePath('', 'config', moduleName)
                            ]);
                            me.menu.find('[data-event="click openMenu"]').first().click();
                        });
                    });

                });
            },
            openMenu: function (event, elem, data) {
                var me = this;
                var value = data.value;
                switch (data.type) {
                    case 1: {
                        Base.showModule(ctx + value, {
                            ctn: '.content-wrapper',
                            elem: me.ctnElem
                        });
                        break;
                    }
                    case 2: {
                        var src = value;
                        if (src.lastIndexOf('#') > 0) {
                            src = src.replace('#', '?r=' + Math.random() + '#')
                        }
                        // window.open(data.value);
                        me.ctnElem.html('<iframe src="' + src + '" frameborder="0" style="width: 100%" onload="this.height=($(this).parent().height() - 5) + \'px\';"></iframe>');

                        $(window).on('resize', function (e) {
                            var height = me.ctnElem.css('min-height');
                            height = height.substring(0, height.length - 2);
                            me.ctnElem.children().height(height - 55);
                        });
                        break;
                    }
                }

                me.menu.find('li.active').not(elem.parents('li')).removeClass('active');
                elem.parent().addClass('active');

                return true;
            },
            toTree: function (data, options) {
                if ($.isArray(data)) {
                    var opts = $.extend(true, {
                        fields: {
                            id: 'id',
                            pId: 'pId',
                            children: 'children'
                        }
                    }, options || {});
                    var fields = opts.fields;
                    var filter = opts.filter;
                    var r = [];
                    var tmpMap = {};
                    for (var i = 0, l = data.length; i < l; i++) {
                        if (!filter || filter(data[i]))
                            tmpMap[data[i][fields.id]] = data[i];
                    }

                    for (i = 0, l = data.length; i < l; i++) {
                        if (filter && !filter(data[i]))
                            continue;
                        var p = tmpMap[data[i][fields.pId]];
                        if (p && data[i][fields.id] !== data[i][fields.pId]) {
                            var children = p[fields.children];
                            if (!children) {
                                children = p[fields.children] = [];
                            }
                            children.push(data[i]);
                        } else {
                            r.push(data[i]);
                        }
                    }
                    return r;
                } else {
                    return [data];
                }
            },
            info: function () {
                var me = this;
                Base.showModule(moduleName + 'sys/auth/form',{
                    ctn: '.content-wrapper',
                    elem: me.ctnElem
                })
            },
            change: function () {
                var me = this;
                Base.showModule(moduleName + 'sys/auth/change', {
                    ctn: '.content-wrapper',
                    elem: me.ctnElem
                });
            },
            out: function () {
                Base.confirm('确定退出吗?',function () {
                    location.reload();
                });
            }
        });
    });
})(Base.moduleName());