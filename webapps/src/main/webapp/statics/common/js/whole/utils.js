/**
 * Created by Administrator on 2017/11/9.
 */
/**
 * 前台js常用函数工具类
 * @author:chenyi
 * @version 1.0
 * @Date: 2017/12/8
 */
(function ($, window) {
    //前台工具类对象
    window.$t = window.$t || {};

    /**
     * 渲染组件
     * @Date: 2018/01/27
     * @param type radio|checkbox|select
     */
    window.$t.render=function (type) {
        type=type?type:"";
        layui.use('form', function () {
            var form = layui.form;
            form.render(type);
        });
    };

    /**
     * 关闭窗口
     * @Date: 2017/12/8
     */
    window.$t.closeWindow=function () {
        var  frameindex= parent.layer.getFrameIndex(window.name);
        parent.layer.close(frameindex);
    };
    /**
     * 关闭窗口并刷新
     * @Date: 2017/12/8
     */
    window.$t.Refresh=function () {
        var  frameindex= parent.layer.getFrameIndex(window.name);
        parent.layer.close(frameindex);
        var parent_iframe=$(parent.document).find("iframe.cy-show ")[0]||$(parent.document).find("iframe")[0];
        $(parent_iframe).contents().find(".search-btn").click();
    };
    /**
     * 关闭窗口并通过reload刷新
     * @Date: 2017/12/8
     */
    window.$t.RefreshGrid=function () {
        var  frameindex= parent.layer.getFrameIndex(window.name);
        parent.layer.close(frameindex);
        var parent_iframe=$(parent.document).find("iframe.cy-show ")[2]||$(parent.document).find("iframe")[2];
        $(parent_iframe).contents().find(".search-btn").click();
    };
    /**
     * 获取前端缓存
     * @param key   字典或枚举  code|enum
     * @Date: 2017/12/8
     */
    window.$t.getStorageItem = function (key) {
        return JSON.parse(localStorage.getItem(key));
    };

    /**
     * 设置前端缓存
     * @param key   字典或枚举  code|enum
     * @param data   存储的值（数组）
     * @Date: 2017/12/8
     */
    window.$t.setStorageItem = function (key, data) {
        localStorage.setItem(key, JSON.stringify(data));
    };


    /**
     * Ajax请求数据
     * @param url 请求地址
     * @param params 请求参数
     * @param success 成功回调函数
     * @param async 是否异步请求
     * @param type 请求类型("post"或"get")
     * @param dataType 数据类型，默认"json"
     */
    window.$t.doAjax = function (url, params, success, async, type, dataType) {
        jQuery.support.cors = true;
        $.ajax({
            url: url,
            cache: false,
            dataType: dataType ? dataType : "json",
            type: type && type === 'get' ? 'get' : "post",
            data: params,
            async: async != undefined && async != null && async === false ? async : true,
            success: success,
            timeout: 10000,    //超时时间设置
            error: function (jqXHR, textStatus, errorThrown) {
                $.error("Ajax请求错误\n" + "     textStatus=" + textStatus + "\n" + "     errorThrown=" + errorThrown);
                $.error("\n     url=" + url + "\n    data=" + window.$t.jsonToStr(params));
            },
            beforeSend: function (XMLHttpRequest) {
            }
        });
    };
    /**
     ** 加法函数，用来得到精确的加法结果
     ** 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
     ** 调用：accAdd(arg1,arg2)
     ** 返回值：arg1加上arg2的精确结果
     **/
    window.$t.accAdd = function (arg1, arg2) {
        var r1, r2, m, c;
        try {
            r1 = arg1.toString().split(".")[1].length;
        }
        catch (e) {
            r1 = 0;
        }
        try {
            r2 = arg2.toString().split(".")[1].length;
        }
        catch (e) {
            r2 = 0;
        }
        c = Math.abs(r1 - r2);
        m = Math.pow(10, Math.max(r1, r2));
        if (c > 0) {
            var cm = Math.pow(10, c);
            if (r1 > r2) {
                arg1 = Number(arg1.toString().replace(".", ""));
                arg2 = Number(arg2.toString().replace(".", "")) * cm;
            } else {
                arg1 = Number(arg1.toString().replace(".", "")) * cm;
                arg2 = Number(arg2.toString().replace(".", ""));
            }
        } else {
            arg1 = Number(arg1.toString().replace(".", ""));
            arg2 = Number(arg2.toString().replace(".", ""));
        }
        return (arg1 + arg2) / m;
    }

    /**
     ** 减法函数，用来得到精确的减法结果
     ** 说明：javascript的减法结果会有误差，在两个浮点数相减的时候会比较明显。这个函数返回较为精确的减法结果。
     ** 调用：accSub(arg1,arg2)
     ** 返回值：arg1减去arg2的精确结果
     **/
    window.$t.accSub = function (arg1, arg2) {
        var r1, r2, m, n;
        try {
            r1 = arg1.toString().split(".")[1].length;
        }
        catch (e) {
            r1 = 0;
        }
        try {
            r2 = arg2.toString().split(".")[1].length;
        }
        catch (e) {
            r2 = 0;
        }
        m = Math.pow(10, Math.max(r1, r2)); //last modify by deeka //动态控制精度长度
        n = (r1 >= r2) ? r1 : r2;
        return ((arg1 * m - arg2 * m) / m).toFixed(n);
    },

        /**
         ** 乘法函数，用来得到精确的乘法结果
         ** 说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
         ** 调用：accMul(arg1,arg2)
         ** 返回值：arg1乘以 arg2的精确结果
         */
        window.$t.accMul = function (arg1, arg2) {
            var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
            try {
                m += s1.split(".")[1].length;
            }
            catch (e) {
            }
            try {
                m += s2.split(".")[1].length;
            }
            catch (e) {
            }
            return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
        },

        /**
         ** 除法函数，用来得到精确的除法结果
         ** 说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
         ** 调用：accDiv(arg1,arg2)
         ** 返回值：arg1除以arg2的精确结果
         **/
        window.$t.accDiv = function (arg1, arg2) {
            var t1 = 0, t2 = 0, r1, r2;
            try {
                t1 = arg1.toString().split(".")[1].length;
            }
            catch (e) {
            }
            try {
                t2 = arg2.toString().split(".")[1].length;
            }
            catch (e) {
            }
            with (Math) {
                r1 = Number(arg1.toString().replace(".", ""));
                r2 = Number(arg2.toString().replace(".", ""));
                return (r1 / r2) * pow(10, t2 - t1);
            }
        },

        /**
         * 获取项目根目录
         */
        window.$t.getContextPath = function () {
            // 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
            var curWwwPath = window.document.location.href;
            // 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
            var pathName = window.document.location.pathname;
            var pos = curWwwPath.indexOf(pathName);
            // 获取主机地址，如： http://localhost:8083
            var localhostPaht = curWwwPath.substring(0, pos);
            // 获取带"/"的项目名，如：/uimcardprj
            var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
            return (localhostPaht + projectName) + "/";
        };


    /**
     * 产生一个唯一的uuid
     * @param len 产生的字符串长度
     * @param radix 进制数
     */
    window.$t.getUUID = function (len, radix) {
        var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
        var uuid = [], i;
        radix = radix || chars.length;
        if (len) {
            for (i = 0; i < len; i++) {
                uuid[i] = chars[0 | Math.random() * radix];
            }
        } else {
            var r;
            uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
            uuid[14] = '4';
            for (i = 0; i < 36; i++) {
                if (!uuid[i]) {
                    r = 0 | Math.random() * 16;
                    uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                }
            }
        }
        return uuid.join('');
    };

    window.$t.removeImg=function(){

    };

    //实现工具类
    $.extend(window.$t.utils, {
        /**
         * 验证对象的数据类型
         * @param obj 待验证的对象
         * @returns 验证结果，参考dataType属性
         */
        'type': function (obj) {
            var type = $.type(obj);
            return type.toLowerCase();
        },
        /**
         * 产生一个唯一的uuid
         * @param len 产生的字符串长度
         * @param radix 进制数
         */
        'uuid': function (len, radix) {
            var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
            var uuid = [], i;
            radix = radix || chars.length;
            if (len) {
                for (i = 0; i < len; i++) {
                    uuid[i] = chars[0 | Math.random() * radix];
                }
            } else {
                var r;
                uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
                uuid[14] = '4';
                for (i = 0; i < 36; i++) {
                    if (!uuid[i]) {
                        r = 0 | Math.random() * 16;
                        uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                    }
                }
            }
            return uuid.join('');
        },
        /**
         * 产生一个32位16进制的UUID
         */
        'uuidHex': function () {
            return window.$t.utils.uuid(32, 16);
        },
        /**
         * 产生一个整型的随机数
         * @param maxNum  产生的最大整数
         * @returns 产生的随机数
         */
        'randomInt': function (maxNum) {
            var num = maxNum && maxNum ? maxNum : 1000000;
            return Math.ceil(Math.random() * num);
        },
        /**
         * 产生一个小数的随机数(0~1)
         * @returns 产生的随机数
         */
        'randomFloat': function () {
            return Math.random();
        },
        /**
         * 获取系统项目根路径
         */
        'getContextPath': function () {
            if ($.fn.contextPath) {
                return $.fn.contextPath;
            }
            // 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
            var curWwwPath = window.document.location.href;
            // 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
            var pathName = window.document.location.pathname;
            var pos = curWwwPath.indexOf(pathName);
            // 获取主机地址，如： http://localhost:8083
            var localhostPaht = curWwwPath.substring(0, pos);
            // 获取带"/"的项目名，如：/uimcardprj
            var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
            return (localhostPaht + projectName) + "/";
        },
        /**
         * 打印页面
         * @param $target 要打印的区域(jquery对象)
         */
        'print': function ($target) {
        },
        /**
         * 对url地址编码
         * @param url 需要编码的url地址
         * @returns 编码后的url
         */
        'encodeURI': function (url) {
            return encodeURI(url);
        },
        /**
         * 对url地址解码
         * @param url 需要解码的url地址
         * @returns 解码后的url
         */
        'decodeURI': function (url) {
            return decodeURI(url);
        },
        /**
         * 动态执行js
         * @param str js代码片段
         */
        'execScript': function (str) {
            return eval("(" + str + ")");
        }
    });
    /*
    * 自动保留两位小数
    * 
    * */
    window.$t.checkNumber=function() {
        var f = parseFloat(x);
        if (isNaN(f)) {
            return false;
        }
        var f = Math.round(x*100)/100;
        var s = f.toString();
        var rs = s.indexOf('.');
        if (rs < 0) {
            rs = s.length;
            s += '.';
        }
        while (s.length <= rs + 2) {
            s += '0';
        }
        return s;
    };
    /*
    *
    * 获取当前系统时间工具类（勿删！！！！）
    * */
    /**
     * 将日期格式化成指定格式的字符串
     * @param date 要格式化的日期，不传时默认当前时间，也可以是一个时间戳
     * @param fmt 目标字符串格式，支持的字符有：y,M,d,q,w,H,h,m,S，默认：yyyy-MM-dd HH:mm:ss
     * @returns 返回格式化后的日期字符串
     */
window.$t.formatDate= function (date, fmt)
    {
        date = date == undefined ? new Date() : date;
        date = typeof date == 'number' ? new Date(date) : date;
        fmt = fmt || 'yyyy-MM-dd HH:mm:ss';
        var obj =
            {
                'y': date.getFullYear(), // 年份，注意必须用getFullYear
                'M': date.getMonth() + 1, // 月份，注意是从0-11
                'd': date.getDate(), // 日期
                'q': Math.floor((date.getMonth() + 3) / 3), // 季度
                'w': date.getDay(), // 星期，注意是0-6
                'H': date.getHours(), // 24小时制
                'h': date.getHours() % 12 == 0 ? 12 : date.getHours() % 12, // 12小时制
                'm': date.getMinutes(), // 分钟
                's': date.getSeconds(), // 秒
                'S': date.getMilliseconds() // 毫秒
            };
        var week = ['天', '一', '二', '三', '四', '五', '六'];
        for(var i in obj)
        {
            fmt = fmt.replace(new RegExp(i+'+', 'g'), function(m)
            {
                var val = obj[i] + '';
                if(i == 'w') return (m.length > 2 ? '星期' : '周') + week[val];
                for(var j = 0, len = val.length; j < m.length - len; j++) val = '0' + val;
                return m.length == 1 ? val : val.substring(val.length - m.length);
            });
        }
        return fmt;
    }
})(jQuery, window);