define(['validator/IDValidator'], function (IDValidator) {
    return {
        generateGmsfhm: function () {
            return IDValidator.makeID();
        }
    }
});