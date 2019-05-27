(function (moduleName) {
    define(['Base', Base.modulePath(moduleName)], function (Base, template) {
        return Base.extend({
            install: function (me) {
                if(me.id){
                        Base.ajax('menu/id/' + me.id, function (resp) {
                            me.render($.extend({}, me.data, resp.data));
                        });
                }else if(me.pId){
                    me.data={
                        pId:me.pId,
                        pName:me.pName
                    }
                    me.render(me.data);
                }else {
                    me.render(me.data);
                }
            },
            render:function(data){
                var me = this;
                var html = Base.compile(template)(data);
                Base.validate(me.elem.html(html).find('form'),function (form) {
                    var data = form.serializeObject();
                    var url = 'menu/';
                    if(me.id){
                            data.id = me.id;
                            url += 'update';
                    }else if(me.pId){
                        data.pId = me.pId;
                        url +='save';
                    }else{
                        url += 'save';
                    }
                    Base.ajax(url,data,function(resp){
                        if(resp.msg){
                            Base.error((me.id?'更新':'新增')+'失败',resp.msg);
                        }else{
                            Base.success((me.id?'更新':'新增')+'成功');
                            if(resp.data){
                                me.id = resp.data;
                            }
                        }
                    });
                    return false;
                });
            },

            goBack: function (me) {
                var me = this;
                Base.showModule(moduleName+'..',{
                    ctn: me.ctn
                });
            }
        });
    });
})(Base.moduleName());