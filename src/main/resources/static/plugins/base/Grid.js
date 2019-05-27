define(['jquery', 'Base',
    pluginJS('jqgrid/src/i18n/grid.locale-cn'),
    pluginCSS('jqgrid/src/css/ui.jqgrid-bootstrap')], function ($, Base) {
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
                // col.align = col.align || 'center';
                col.sortable = col.sortable || false;
            });

            if (opts.btns) {
                opts.colModel.push({
                    label: '操作',
                    sortable: false,
                    formatter: function (value, options, row) {
                        var btnHtml = '';
                        $.each(opts.btns, function (idx, btn) {
                            if(btn.before && btn.before(value, options, row)){
                                return;
                            }
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

            if (typeof opts.pager === 'undefined') {
                opts.pager = '#' + opts.elem.attr('id') + 'Pager';
            }
            me.opts = opts;
            me.grid = me.opts.elem.jqGrid($.extend(true, {
                mtype: 'POST',
                styleUI: 'Bootstrap',
                datatype: "json",
                height: 'auto',
                autowidth: true,
                viewrecords: true,
                rowNum: 15,
                rowList: [15, 30, 50, 100],
                jsonReader: {
                    root: 'data',
                    page: 'pageNo',
                    total: 'pages',
                    records: 'total'
                },
                prmNames: {
                    page: 'pageNo',
                    rows: 'limit'
                },
                gridComplete: function () {
                    _resize($('#gbox_' + this.id));
                }
            }, me.opts));
        },
        refresh: function (e, elem, data, params) {
            this.grid.jqGrid("setGridParam", {
                postData: params || {}
            }).trigger("reloadGrid");
        },
        search: function (e, elem, data, params) {
            var me = this;
            var formData = $('#' + me.grid.attr('rel')).serializeObject();
            if (params) {
                $.extend(true, formData, params);
            }
            me.grid.jqGrid('setGridParam', {
                postData: formData,
                page: 1
            }).trigger("reloadGrid");
        }
    });
});