define([pluginJS('handlebars/handlebars')], function (Handlebars) {
    Handlebars.registerHelper('addOne', function (num) {
        return num + 1;
    });

    Handlebars.registerHelper('equals', function (v1, v2, options) {
        if (v1 === v2) {
            return options.fn(this);
        }
        return options.inverse(this);
    });

    Handlebars.registerHelper('getValue', function (obj, attrName) {
        return obj[attrName];
    });

    var getLabel = function (dic, code) {
        for (var i = 0; i < dic.length; i++) {
            var item = dic[i];
            if (item.code === code) {
                return item.label;
            }
        }

        return '';
    };

    Handlebars.registerHelper('getLabel', getLabel);
    return {
        compile: function (template) {
            return Handlebars.compile(template);
        },
        getLabel: getLabel
    }
});