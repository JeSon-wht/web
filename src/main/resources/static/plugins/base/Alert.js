require({
    paths: {
        Sweetalert2: 'sweetalert2/sweetalert2'
    },
    shim: {
        Sweetalert2: {
            deps: ['promise/promise', 'promise/polyfill', pluginCSS('sweetalert2/sweetalert2')]
        }
    }
});
define(function (require) {
    var Swal = require('Sweetalert2');
    var Alert = {
        load: function (tips, timmer, callback) {
            if (typeof timmer === 'function') {
                callback = timmer;
                timmer = 3000;
            }

            var timerInterval;
            Swal.fire({
                title: tips,
                html: '将在 <strong></strong> 毫秒后关闭.',
                timer: timmer,
                allowOutsideClick: false,
                onBeforeOpen: function () {
                    Swal.showLoading();
                    timerInterval = setInterval(function () {
                        Swal.getContent().querySelector('strong')
                            .textContent = Swal.getTimerLeft()
                    }, 100);
                },
                onClose: function () {
                    clearInterval(timerInterval);
                }
            }).then(function (result) {
                if (result.dismiss === Swal.DismissReason.timer) {
                    callback();
                }
            })
        },
        confirm: function (title, text, yesFunc, noFunc) {
            var opts = {
                title: title,
                type: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#3085d6',
                confirmButtonText: '确定',
                cancelButtonText: '取消'
            };

            if (typeof text === 'function') {
                noFunc = yesFunc;
                yesFunc = text;
            } else {
                opts.text = text;
            }
            Swal.fire(opts).then(function (result) {
                if (result.value) {
                    yesFunc && yesFunc(result);
                } else {
                    noFunc && noFunc(result);
                }
            });
        }
    };

    var types = ['warning', 'error', 'success', 'info', 'question'];
    for (var i = 0; i < types.length; i++) {
        var type = types[i];
        Alert[type] = (function (type) {
            return function (title, text) {
                var opts = {
                    title: title,
                    type: type,
                    showConfirmButton: false,
                    timer: 1500
                };

                if (text) {
                    opts.text = text;
                }

                Swal.fire(opts);
            }
        }(type));
    }

    return Alert;
});