(function (moduleName) {
    define(['Base', Base.modulePath(moduleName), 'jquery'], function (Base, tempalte, $) {
        var url = 'http://127.0.0.1:8089/PPSignSDK/';

        function init() {
            $.ajax({
                url: url + 'InitialDevice?id=7&width=740&height=480',
                type: 'GET'
            }).fail(function () {
                alert('请检查签名设备是否正常连接!');
            });
        }

        init();
        return Base.extend({
            install: function (me, elem) {
                elem.html(tempalte);
                me.canvas = document.getElementById('signCanvas');

                if (me.canvas.getContext) {
                    me.context = me.canvas.getContext('2d');
                }
                me.getStatus(this);
            },
            getStatus: function (me) {
                (function poll() {
                    var timeId = setTimeout(function () {
                        clearTimeout(timeId);
                        $.ajax({
                            url: url + 'GetDeviceConfirmOrCancelKeyStatus',
                            type: 'GET'
                        }).done(function (result) {
                            if (result === '1') {
                                me.getInks();
                            } else if (result === '0') {
                                me.clear(function () {
                                    me.context.clearRect(0, 0, me.canvas.width, me.canvas.height);
                                });
                            }
                        }).fail(function () {
                            console.log('Fail to get confirmed status!', arguments);
                            init();
                        }).always(function () {
                            poll();
                        });
                    }, 500);
                }());
            },
            getInks: function () {
                var me = this;
                $.ajax({
                    url: url + 'GetInks',
                    type: 'get'
                }).done(function (data) {
                    var dataInfos = JSON.parse(data);
                    if (dataInfos.length < 1)
                        return;
                    me.clear(function () {
                        me.context.clearRect(0, 0, me.canvas.width, me.canvas.height);
                        dataInfos.forEach(function (value) {
                            if (value.EventType === 0) {
                                me.drawImage(value.Image)
                            }
                        });
                    });
                });
            },
            drawImage: function (base64) {
                var me = this;
                me.img = base64;
                var img = new Image();
                img.addEventListener('load', function () {
                    me.context.drawImage(this, 0, 0, me.canvas.width, me.canvas.height);
                }, false);
                img.src = 'data:image/png;base64,' + base64;
            },
            clear: function (cb) {
                var me = this;
                me.img = undefined;
                $.ajax({
                    url: url + 'Clear',
                    type: 'get',
                    success: function () {
                        cb && cb();
                    }
                });
            },
            signDone: function () {
                var me = this;
                if (me.callback) {
                    var data = me.canvas.toDataURL();
                    me.callback(data.substring(data.indexOf(',') + 1));
                }
            }
        });
    });
}(Base.moduleName()));