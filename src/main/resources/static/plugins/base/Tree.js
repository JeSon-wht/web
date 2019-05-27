require({
    paths: {
        zTree: 'ztree/js/jquery.ztree.all'
    },
    shim: {
        zTree: {
            deps: ['jquery', pluginCSS('ztree/css/metroStyle/metroStyle')]
        }
    }
});
define(['jquery', 'zTree'], function ($) {
    return {
        render: function (opts) {
            var settings = {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: 'id',
                        pIdKey: 'pId',
                        rootPId: 0
                    },
                    key: {
                        name: 'label',
                        isParent: 'parent'
                    }
                },
                view: {
                    selectedMulti: false
                },
                callback: {}
            };

            var fields = settings.data.simpleData;

            if (opts.fields) {
                fields.idKey = opts.fields.idKey || fields.idKey;
                fields.pIdKey = opts.fields.pIdKey || fields.pIdKey;
                settings.data.key.name = opts.fields.name || settings.data.key.name;
            }

            if (!opts.data) {
                settings.async = {
                    enable: true,
                    url: opts.url,
                    autoParam: [fields.idKey + '=' + fields.pIdKey],
                    dataFilter: function (treeId, parentNode, responseData) {
                        return responseData.data;
                    }
                }
            }

            if (opts.multi) {
                settings.view.selectedMulti = true;
            }

            if (opts.click) {
                settings.callback.onClick = function (event, treeId, node) {
                    opts.click(event, node, treeId);
                }
            }

            return $.fn.zTree.init(opts.elem, settings, opts.data);
        }
    };
});