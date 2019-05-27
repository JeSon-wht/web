require({
    paths: {
        Adminlte: 'adminlte/js/adminlte',
        Base: 'base/Base',
        Bootstrap: 'bootstrap/js/bootstrap',
        grid: 'base/Grid',
        treegrid: 'base/TreeGrid',
        'grid.base': 'jqgrid/src/jquery.jqGrid',
        jquery: 'jquery-1.12.4',
        Step: 'base/Step',
        Tree: 'base/Tree'
    },
    shim: {
        Adminlte: {
            deps: ['Bootstrap', pluginCSS('adminlte/css/AdminLTE'), pluginCSS('adminlte/css/skins/_all-skins')]
        },
        Bootstrap: {
            deps: ['jquery', pluginCSS('bootstrap/css/bootstrap')]
        }
    }
});

require([
    'jquery',
    'Base',
    pluginJS('base/Alert'),
    pluginJS('base/Dialog'),
    pluginJS('base/Generator'),
    pluginJS('base/Module'),
    pluginJS('base/Template'),
    pluginJS('base/Request'),
    pluginJS('base/Validator')
], function ($, Base, Alert, Dialog, Generator, Module, Request, Template, Validator) {
    window.Base = $.extend(Base, Alert, Dialog, Generator, Module, Request, Template, Validator);
    require([pluginJS('base/Fix')]);

    var enterPath = location.hash;
    if (enterPath) {
        enterPath = enterPath.substring(enterPath.indexOf('/') === 1 ? 2 : 1);
    }

    enterPath = ctx + enterPath;

    Base.showModule(enterPath);
});
