require({
    paths: {
        Bootbox: 'bootbox/bootbox'
    },
    shim: {
        Bootbox: {
            deps: ['Bootstrap']
        }
    }
});
define(['Bootbox'], function (Box) {
    return {
        alert: Box.alert,
        dialog: Box.dialog
    };
});