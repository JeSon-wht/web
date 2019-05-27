define(['jquery', pluginJS('klass/klass')], function ($, klass) {
    return klass({
        install: function (me, elem) {
        },
        initialize: function (ext) {
            var me = this;
            $.extend(true, me, ext);
            me.elem = $(me.ctn || 'body');
            me._bindEvent();
        },
        _bindEvent: function () {
            var me = this;
            var _events = function (e, eventType) {
                var elem = $(this);
                var data = elem.data();
                var eventValue = data['event'];
                if (!eventValue)
                    return;
                var eventArray = eventValue.split(',');
                for (var i = 0; i < eventArray.length; i++) {
                    var temp = eventArray[i];
                    var tempArray = temp.split(/\s+/g);
                    if (tempArray.length === 2 && me[tempArray[1]]) {
                        var eventTypeValue = tempArray[0];
                        if (eventType === eventTypeValue) {
                            e.stopPropagation();
                            me[tempArray[1]](e, elem, data) || e.preventDefault();
                        }
                    }
                }
            };

            var eventTypes = {
                'click': '[data-event]',
                'dblclick': '[data-event]',
                'focusout': '[data-event]',
                'change': '[data-event]'
            };

            for (var eventType in eventTypes) {
                (function (eventType) {
                    me.elem.off(eventType).on(eventType, eventTypes[eventType], function (e) {
                        _events.call(this, e, eventType);
                    });
                })(eventType);
            }
        }
    }).statics({
        ctx: ctx,
        plugins: plugins
    });
});