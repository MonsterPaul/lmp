var Verify = {
    required: function(val){
        return /[\S]+/.test(val);
    }
    ,isPhone: function(val){
        return /^1\d{10}$/.test(val);
    }
    ,isEmail: function(val){
        return /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(val);
    }
    ,isUrl: function(val){
        return /(^#)|(^http(s*):\/\/[^\s]+\.[^\s]+)/.test(val);
    }
    ,isNumber: function(val){
        return /^\d+$/.test(val);
    }
    ,isDate: function(val){
        return /^(\d{4})[-\/](\d{1}|0\d{1}|1[0-2])([-\/](\d{1}|0\d{1}|[1-2][0-9]|3[0-1]))*$/.test(val);
    }
    ,isIdentity: function(val){
        return /(^\d{15}$)|(^\d{17}(x|X|\d)$)/.test(val);
    }
};
