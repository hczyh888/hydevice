/**
 * Created by zyh on 2018/6/21
 * email   :
 */
(function ($) {
    /* 入口函数 */
    $.fn.mychosen = function () {
        //当前表格对象
        var $grid = this;
        //获取表格参数
        myProps = $grid.attr("myProps");
        if (!myProps) {
            return
        }
        myProps = myProps ? myProps : "";
        //将表格参数转为json
        myProps = eval("({" + myProps + "})");
        //获取数据的地址，只能通过表码或url，如果两个都写，默认是url
        //从表码获取数据
        var codeName = myProps.codeName;
        //从后台获取数据
        var url = myProps.url;
        //从枚举获取数据
        var enumName = myProps.enumName;
        var R = "";
        //如果是通过表码取值
        if (codeName != undefined && codeName != "") {
            R = mychosen.getDataByCode(myProps.codeName);
        }
        //如果是从后台获取数据
        if (url != undefined && url != "") {
            R = mychosen.getDataByUrl(myProps.url);
        }
        //如果是从枚举获取数据
        if (enumName != undefined && url != "") {
            R = mychosen.getDataByEnum(myProps.enumName);
        }
        mychosen.renderData(R, $grid, myProps);
    };
    /*默认配置*/
    var myProps = {};
    /*方法对象*/
    var mychosen = {
        /**获取数据 by chenyi 2017/6/21*/
        getDataByUrl: function (url) {
            var data;
            $.ajax({
                url: url,
                async: false,
                type: 'post',
                dataType: "json",
                success: function (R) {
                    if (R.code == 0) {
                        data = R;
                    } else {
                        data = {};
                        alert(R.msg);
                    }
                }
            });
            return data;
        },
        /**获取数据 by chenyi 2017/7/5*/
        getDataByCode: function (codeName) {
            /**localStorage是否已存在该数据*/
            var data = $t.getStorageItem(codeName);
            if (!data) {
                $.ajax({
                    url: $s.getDataByCode,
                    async: false,
                    data: {codeName: codeName},
                    type: 'post',
                    dataType: "json",
                    success: function (R) {
                        if (R.code == 0) {
                            data = R;
                            /**设置localStorage缓存*/
                            $t.setStorageItem(codeName, data);
                        } else {
                            data = {};
                            alert(R.msg);
                        }
                    }
                });

            }

            return data;
        },
        /**获取数据 by chenyi 2017/7/19*/
        getDataByEnum: function (enumName) {
            /**localStorage是否已存在该数据*/
            var data = $t.getStorageItem(enumName);
            if (!data) {
                $.ajax({
                    url: $s.getDataByEnum,
                    async: false,
                    type: 'post',
                    data: {enumName: enumName},
                    dataType: "json",
                    success: function (R) {
                        if (R.code == 0) {
                            data = R;
                            /**设置localStorage缓存*/
                            $t.setStorageItem(enumName, data);
                        } else {
                            data = {};
                            alert(R.msg);
                        }
                    }
                });
            }
            return data;
        },
        /**渲染下拉框数据 by chenyi 2017/6/21*/
        renderData: function (R, $grid, myProps) {
            var _grid = $grid;
            //获取下拉控件的name
            var _name = $(_grid).attr("name");
            //获取下拉控件的默认值
            var _value = $(_grid).attr("value");



            //是否是下拉多选
            var _multiple=myProps.multiple||"false";
            //获取是否有提示
            var _selectTip = myProps.tips || "请选择";

            //搜索功能参数
            var _search = myProps.search || "true";
            //获取下拉框禁用的值
            var _disabled = myProps.disabled || "";
            var _disableds = _disabled.split(",");
            var data = R.data;

            if (data != undefined) {
                for (var i = 0; i < data.length; i++) {
                    var _option = '<option value="' + data[i].id + '" data-keys="'+data[i].keyValue+'">' + data[i].value + '</option>';
                    if(_multiple == "false"){
                        //设置默认值
                        if (_value == data[i].id) {
                            _option = _option.replace("<option", "<option selected ")
                        }
                    }
                    if(_multiple == "true"){
                        var _values=_value.split(",");
                        for(var z=0;z<_values.length;z++){
                            //设置默认值
                            if (_values[z] == data[i].id) {
                                _option = _option.replace("<option", "<option selected ")
                            }
                        }

                    }
                    //设置禁用
                    if (_disableds.indexOf(data[i].id) != -1) {
                        _option = _option.replace("<option", "<option disabled ")
                    }
                    $(_grid).append(_option);
                }
            }
        }
    }

})(jQuery);
$(document).ready(function () {
    //下拉树查询
    var selects = $("[myType='mychosen']");
    for (var i = 0; i < selects.length; i++) {
        $(selects[i]).mychosen();
    }
});