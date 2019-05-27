define(['jquery', pluginCSS('base/style')], function ($) {
    $.fn.serializeObject = function () {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function (idx, me) {
            if (o[me.name] !== undefined) {
                if (!o[me.name].push) {
                    o[me.name] = [o[me.name]];
                }
                o[me.name].push(me.value || '');
            } else {
                o[me.name] = me.value || '';
            }
        });
        return o;
    };


    var r20 = /%20/g,
        rbracket = /\[\]$/;

    function buildParams(prefix, obj, traditional, add) {
        var name;

        if (jQuery.isArray(obj)) {

            // Serialize array item.
            jQuery.each(obj, function (i, v) {
                if (traditional || rbracket.test(prefix)) {

                    // Treat each array item as a scalar.
                    add(prefix, v);

                } else {

                    // Item is non-scalar (array or object), encode its numeric index.
                    buildParams(
                        prefix + (typeof v === "object" && v != null ? "[" + i + "]" : ""),
                        v,
                        traditional,
                        add
                    );
                }
            });

        } else if (!traditional && jQuery.type(obj) === "object") {

            // Serialize object item.
            for (name in obj) {
                buildParams(prefix + "." + name, obj[name], traditional, add);
            }

        } else {

            // Serialize scalar item.
            add(prefix, obj);
        }
    }

    $.param = function (a, traditional, callback) {
        var prefix,
            s = [],
            add = function (key, value) {

                // If value is a function, invoke it and return its value
                value = $.isFunction(value) ? value() : (value == null ? "" : value);
                callback && callback(key, value);
                s[s.length] = encodeURIComponent(key) + "=" + encodeURIComponent(value);
            };

        // Set traditional to true for jQuery <= 1.3.2 behavior.
        if (traditional === undefined) {
            traditional = $.ajaxSettings && $.ajaxSettings.traditional;
        }

        // If an array was passed in, assume that it is an array of form elements.
        if ($.isArray(a) || (a.jquery && !$.isPlainObject(a))) {

            // Serialize the form elements
            $.each(a, function () {
                add(this.name, this.value);
            });

        } else {

            // If traditional, encode the "old" way (the way 1.3.2 or older
            // did it), otherwise encode params recursively.
            for (prefix in a) {
                buildParams(prefix, a[prefix], traditional, add);
            }
        }

        // Return the resulting serialization
        return s.join("&").replace(r20, "+");
    };

    var ajax = $.ajax;

    $.ajax = function (url, options) {
        if ( typeof url === "object" ) {
            options = url;
            url = options.url;
        }

        options = options || {};

        if (window.token) {
            if (options.headers) {
                options.headers.Authorization = window.token;
            } else {
                options.headers = {
                    Authorization: window.token
                }
            }
        }

        if($.isFunction(window.ajaxURLHandle)){
            url = ajaxURLHandle(url)
        }

        return ajax(url, options);
    }
});