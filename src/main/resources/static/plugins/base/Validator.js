require({
    shim: {
        'validator/validator': {
            deps: ['Bootstrap']
        }
    }
});
define(['jquery', 'validator/IDValidator', 'validator/validator'], function ($, IDValidator) {
        var Validator = $.fn.validator.Constructor;
        // 扩展校验支持，true表示不通过
        $.extend(Validator.DEFAULTS.custom, {
            gmsfhm: function (elem) {
                return !IDValidator.isValid(elem.val());
            },
            pattern: function (elem) {
                return !new RegExp('^' + elem.data('pattern') + '$').test(elem.val());
            }
        });

        return {
            validate: function (form, success) {
                form.validator().on('submit', (function (validator) {
                    return function (e) {
                        if (validator.hasErrors()) {
                            return false;
                        } else {
                            return success && success(form);
                        }
                    }
                })(form.data('bs.validator')));
            }
        }
    }
);