define(['jquery', 'Base',
    pluginJS('jqgrid/src/i18n/grid.locale-cn'),
    pluginCSS('jqgrid/src/css/ui.jqgrid-bootstrap')], function ($, Base) {

    $(window).on('resize', function () {
        $('.ui-jqgrid-btable').each(function () {
            var grid = $(this);
            var gbox = grid.parents('.ui-jqgrid');
            grid.setGridWidth(gbox.parent().width());
            _resize(gbox);
        });
    });

    var _resize = function (ctnElem) {
        var td = $('#tdCompute')//获取计算实际列长度的容器
            , arr = [];//用于保存最大的列宽
        if (!td.length) {
            td = $('<div id="tdCompute" class="ui-jqgrid ui-widget ui-widget-content ui-corner-all" style="position:absolute;top:-9999px"></div>');
            $(document.body).append(td);
        }
        //遍历每行获得每行中的最大列宽
        ctnElem.find('.ui-jqgrid-htable tr,.ui-jqgrid-btable tr:gt(0)').each(function () {
            $(this).find('td,th').each(function (idx) {
                arr[idx] = Math.max(arr[idx] ? arr[idx] : 0, td.html($(this).html())[0].offsetWidth);
            });
        });
        ctnElem.find('.ui-jqgrid-labels th').each(function (idx) {
            this.style.width = arr[idx] + 'px'
        });//设置页头单元格宽度
        ctnElem.find('.ui-jqgrid-btable tr:eq(0) td').each(function (idx) {
            this.style.width = arr[idx] + 'px'
        });//设置内容表格中控制单元格宽度的单元格，在第一行
    };
    return Base.extend({
        render: function (opts) {
            var me = this;
            $.each(opts.colModel, function (idx, col) {
                col.sortable = col.sortable || false;
                col.resizable = col.resizable || false;
            });

            if (opts.btns) {
                opts.colModel.push({
                    label: '操作',
                    sortable: false,
                    formatter: function (value, options, row) {
                        var btnHtml = '';
                        $.each(opts.btns, function (idx, btn) {
                            btnHtml += '<button type="button" class="btn btn-xs '
                                + btn.class + '" title="' + btn.title + '" data-event="click '
                                + btn.func + '" data-id="' + options.rowId + '"';
                            if (btn.data) {
                                if ($.isArray(btn.data)) {
                                    $.each(btn.data, function (i, attr) {
                                        var val;
                                        if (typeof attr === 'function') {
                                            val = attr(row);
                                            attr = val.name;
                                            val = val.value;
                                        } else {
                                            val = row[attr];
                                        }
                                        btnHtml += ' data-' + attr + '="' + val + '"'
                                    })
                                } else {
                                    $.each(btn.data, function (attr, val) {
                                        btnHtml += ' data-' + attr + '="' + val + '"'
                                    })
                                }
                            }
                            btnHtml += '>' + (btn.label ? btn.label : '<span class="' + btn.icon + '" aria-hidden="true"></span>') + '</button>';
                        });
                        return btnHtml;
                    }
                });
            }

            me.opts = $.extend(true, {
                mtype: 'POST',
                styleUI: 'Bootstrap',
                datatype: "json",
                gridview: true,
                treeGrid: true,
                loadonce: false,
                ExpandColClick: true,
                treeGridModel: 'adjacency',
                tree_root_level: 0,
                height: 'auto',
                autowidth: true,
                treeIcons: {
                    plus: 'glyphicon glyphicon-triangle-right',
                    minus: 'glyphicon glyphicon-triangle-bottom',
                    leaf: 'glyphicon glyphicon-record'
                },
                treeReader: {
                    level_field: "level",
                    parent_id_field: 'pId',
                    leaf_field: "leaf",
                    expanded_field: "expanded",
                    loaded: "loaded",
                    icon_field: "icon"
                },
                beforeRequest: function () {
                    var postData = this.p.postData;
                    if (postData.nodeid != null && typeof postData.n_level === 'number') {
                        me.currentId = postData.nodeid;
                        me.currentLevel = postData.n_level + 1;
                    } else {
                        me.currentLevel = 0;
                    }
                    this.p.postData = {
                        pId: postData['nodeid']
                    }
                },
                jsonReader: {
                    root: 'data',
                    page: 'pageNo',
                    total: 'pages',
                    repeatitems: false,
                    records: 'total'
                },
                beforeProcessing: function (resp) {
                    if (resp.data.length === 0) {
                        var id = me.currentId;
                        var rowData = me.grid.jqGrid('getRowData', id);
                        rowData.leaf = true;
                        me.grid.jqGrid('setRowData', me.currentId, rowData);
                        $(document.getElementById(id)).find('.treeclick').attr('class', 'treeclick ' + me.opts.treeIcons.leaf + ' tree-minus');
                    }
                    $.each(resp.data, function (idx, row) {
                        row.level = me.currentLevel;
                        row.pId = me.currentId;
                    });
                },
                gridComplete: function () {
                    _resize($('#gbox_' + this.id));
                }
            }, opts);
            me.grid = me.opts.elem.jqGrid(me.opts);
        },
        refresh: function (e, elem, data, params) {
            var postData = params || {};
            postData.n_level = 0;
            this.grid.jqGrid("setGridParam", {
                postData: postData
            }).trigger("reloadGrid");
        }
    });
});