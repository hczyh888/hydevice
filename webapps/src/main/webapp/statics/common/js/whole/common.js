/**
 * Created by 陈熠 on 2017/6/21
 * email   :  228112142@qq.com
 * 公用模块
 */


//重写alert
window.alert = function(msg, callback){
    parent.layer.alert(msg, function(index){
        parent.layer.close(index);
        if(typeof(callback) === "function"){
            callback(index);
        }
    });
};

//重写confirm式样框
window.confirm = function(msg, callback){
    parent.layer.confirm(msg, {btn: ['确定','取消']},
        function(){//确定事件
            if(typeof(callback) === "function"){
                callback("ok");
            }
        });
};

//选择一条记录
function getSelectedRow(table,table_id) {
    //var checked=$("#"+table_id+" tbody .layui-form-checked");
      var checkStatus = table.checkStatus(table_id);
    if(checkStatus.data.length==0){
        parent.layer.msg("请选择一条记录", {icon: 5});
        //alert("请选择一条记录");
        return ;
    }

    if(checkStatus.data.length > 1){
        parent.layer.msg("只能选择一条记录", {icon: 5});
        //alert("只能选择一条记录");
        return ;
    }
    return checkStatus.data[0].id;
}

//选择多条记录
function getSelectedRows(table_id) {
    var checked=$("#"+table_id);
    if(checked.length==0){
        parent.layer.msg("至少选择一条记录", {icon: 5});
        //alert("至少选择一条记录");
        return ;
    }
    var selectedIDs = [];
    for(var i=0;i<checked.length;i++){
        var _this=$(checked[i]).prev();
        selectedIDs.push($(_this).attr("primary"));
    }
    return selectedIDs;
}

//清空按钮
$(function () {
    $("[type='reset']").click(function () {
        $(this).parents(".layui-form").find("input").val("");
        $(this).prev().click();
    });

});


/**跳转到添加页面
 * @param table_id 表格id
 * @param url      请求地址
 */
function addPage(title,url,id,area){
    var area = area.split('-');
    parent.layer.open({
        id:id,
        type: 2,
        title: title,
        shadeClose: false,
        shade: [0.3, '#000'],
        maxmin: true, //开启最大化最小化按钮
        area: area,
        content: url
    });
}
/**跳转到修改页面
 * @param table_id 表格id
 * @param url      请求地址
 */
function editPage(table,table_id,url,area){
    var area = area.split('-');
    var id=getSelectedRow(table,table_id);
    if(id!=null){
        parent.layer.open({
            type: 2,
            title: '修改',
            shadeClose: false,
            shade: [0.3, '#000'],
            maxmin: true, //开启最大化最小化按钮
            area: area,
            content: url+"/"+id
        });
    }
}
/**
 * 修改
 * @param url 请求地址
 * @param id  选中的id
 */
function editOne(url,id){
    parent.layer.open({
        type: 2,
        title: '修改',
        shadeClose: false,
        shade: [0.3, '#000'],
        maxmin: true, //开启最大化最小化按钮
        area: ['700px', '400px'],
        content: url+"/"+id
    });
}
/**
 * 授权
 * @param url 请求地址
 * @param id  选中的id
 */
function assign(url,id){
    parent.layer.open({
        type: 2,
        title: '授权',
        shadeClose: false,
        shade: [0.3, '#000'],
        maxmin: true, //开启最大化最小化按钮
        area: ['600px', '500px'],
        content: url+"/"+id
    });
}

/**
 * 批量删除
 * @param table_id 表格id
 * @param url      请求地址
 */
function deleteBatch(msg,table_id,url){
    //获取选中的id
    var ids= getSelectedRows(table_id);
    if(ids!=null){
        confirm("确认"+msg+"？",function(){
            $.ajax({
                type: "post",
                url: url,
                contentType: "application/json",
                data: JSON.stringify(ids),
                async: false,
                dataType:"json",
                success: function (R) {
                    if (R.code == 0) {
                        $(".search-btn").click();
                        if(R.msg!=""){
                            parent.layer.msg(R.msg, {icon: 1});
                        }else{
                            parent.layer.msg('删除成功 !', {icon: 1});
                        }

                    } else {
                        parent.layer.msg(R.msg, {icon: 5});
                    }
                },
                error: function () {
                    parent.layer.msg("系统繁忙", {icon: 5});
                }
            });
        });
    }

}
/**
 * 删除一条数据
 * @param url 请求地址
 * @param id  选中的id
 */
function deleteOnePage(table,table_id,url,title){
    var id=getSelectedRow(table,table_id);
    if(id!=null) {
        var ids = [];
        ids.push(id);
        var idss = {'ids': ids};
        //获取选中的id
        confirm("确认" + title + "？", function () {
            $.ajax({
                type: "post",
                url: url,
                contentType: "application/json",
                data: JSON.stringify(idss),
                async: false,
                dataType: "json",
                success: function (result) {

                    if (result.code == 0) {
                        $(".search-btn").click();
                        parent.layer.msg('删除成功 !', {icon: 1});
                    } else {

                        parent.layer.msg(result.msg, {icon: 5});
                    }
                },
                error: function () {
                    parent.layer.msg("系统繁忙", {icon: 5});
                }
            });
        });
    }

}

/**
 * 删除一条数据
 * @param url 请求地址
 * @param id  选中的id
 */
function deleteOne(id,url,title){
    var ids=[];
    ids.push(id);
    var idss={'ids':ids};
    //获取选中的id
    confirm("确认"+title+"？",function(){
        $.ajax({
            type: "post",
            url: url,
            contentType: "application/json",
            data: JSON.stringify(idss),
            async: false,
            dataType:"json",
            success: function (result) {

                if (result.code == 0) {
                    $(".search-btn").click();
                    parent.layer.msg('删除成功 !', {icon: 1});
                } else {

                    parent.layer.msg(result.msg, {icon: 5});
                }
            },
            error: function () {
                parent.layer.msg("系统繁忙", {icon: 5});
            }
        });
    });

}


/**
 * 批量启用或禁用
 * @param msg      提示信息(启用或禁用)
 * @param table_id 表格id
 * @param url      请求地址
 */
function updateState(msg,table_id,url){
    //获取选中的id
    var ids= getSelectedRows(table_id);
    if(ids!=null){
        confirm("确认"+msg+"？",function(){
            $.ajax({
                type: "post",
                url: url,
                contentType: "application/json",
                data: JSON.stringify(ids),
                async: false,
                dataType:"json",
                success: function (result) {
                    if (result.code == 0) {
                        $(".search-btn").click();
                        parent.layer.msg(msg+'删除成功!', {icon: 1});
                    } else {
                        parent.layer.msg(result.info, {icon: 5});
                    }
                },
                error: function () {
                    parent.layer.msg("系统繁忙", {icon: 5});
                }
            });
        });
    }else{
        parent.layer.msg("至少选择一条记录", {icon: 5});
        //alert("至少选择一条记录");
        return ;
    }

}

/**
 * 启用或禁用一条数据
 * @param msg 提示信息
 * @param url 请求地址
 * @param id  选中的id
 */
function updateStateOne(msg,url,id){
    var ids=[];
    ids.push(id);
    confirm("确认"+msg+"？",function(){
        $.ajax({
            type: "post",
            url: url,
            contentType: "application/json",
            data: JSON.stringify(ids),
            async: false,
            dataType:"json",
            success: function (result) {
                if (result.code == 0) {
                    $(".search-btn").click();
                    parent.layer.msg(msg+'成功 !', {icon: 1});
                } else {
                    parent.layer.msg(result.info, {icon: 5});
                }
            },
            error: function () {
                parent.layer.msg("系统繁忙", {icon: 5});
            }
        });
    });

}

/**
 * 启用或禁用一条数据
 * @param msg      提示信息
 * @param table_id 表格id
 * @param url      请求地址
 */
function updateStateOne(msg,url,id){
    var ids=[];
    ids.push(id);
    if(ids!=null){
        confirm("确认"+msg+"？",function(){
            $.ajax({
                type: "post",
                url: url,
                contentType: "application/json",
                data: JSON.stringify(ids),
                async: false,
                dataType:"json",
                success: function (result) {
                    if (result.code == 0) {
                        $(".search-btn").click();
                        parent.layer.msg(msg+'成功 !', {icon: 1});
                    } else {
                        parent.layer.msg(result.info, {icon: 5});
                    }
                },
                error: function () {
                    parent.layer.msg("系统繁忙", {icon: 5});
                }
            });
        });
    }

}

/**
 * 详情
 * @param url
 * @param id
 */
function detailOne(url,id){
    parent.layer.open({
        type: 2,
        title: '详情',
        shadeClose: false,
        shade: [0.3, '#000'],
        maxmin: true, //开启最大化最小化按钮
        area: ['1000px', '700px'],
        content: url+"/"+id
    });

}

function openIframe(id,url,title,area){
    area = area.split('-');
    parent.layer.open({
        type: 2,
        title: title,
        shadeClose: false,
        shade: [0.3, '#000'],
        maxmin: true, //开启最大化最小化按钮
        area: area,
        content: url+'/'+id
    });
}


//保存或修改,通过搜索按钮刷新grid
layui.use(['form'], function () {
    var form = layui.form;
    //监听提交
    form.on('submit(submit)', function (data) {
        var url=$(this).attr("data-url");
        $.ajax({
            url: url,
            type: "post",
            contentType: "application/x-www-form-urlencoded",
            data: $(data.form).serialize(),    //JSON.stringify(data.field),
            async: false,
            dataType: "json",
            success: function (R) {
                if (R.code == 0) {
                    $t.Refresh();
                    //刷新页面
                    parent.layer.msg('操作成功 !', {icon: 1});
                } else if(R.code==1){
                    parent.layer.alert(R.msg, {icon: 5});

                } else{
                    parent.layer.msg(R.msg, {icon: 5});
                }
            },
            error: function (err) {
                alert("系统繁忙");
            }
        });
        return false;
    });
});

//保存或修改,通过调用第二个frame方法刷新grid
layui.use(['form'], function () {
    var form = layui.form;
    //监听提交
    form.on('submit(submit2)', function (data) {
        var url=$(this).attr("data-url");
        $.ajax({
            url: url,
            type: "post",
            contentType: "application/x-www-form-urlencoded",
            data: $(data.form).serialize(),    //JSON.stringify(data.field),
            async: false,
            dataType: "json",
            success: function (R) {
                if (R.code == 0) {
                    $t.RefreshGrid();
                    //刷新页面
                    parent.layer.msg('操作成功 !', {icon: 1});
                } else if(R.code==1){
                    parent.layer.alert(R.msg, {icon: 5});

                } else{
                    parent.layer.msg(R.msg, {icon: 5});
                }
            },
            error: function (err) {
                alert("系统繁忙");
            }
        });
        return false;
    });
});

//planApply保存或修改
layui.use(['form'], function () {
    var form = layui.form;
    //监听提交
    form.on('submit(applysubmit)', function (data) {
        var url=$(this).attr("data-url");
        $.ajax({
            url: url,
            type: "post",
            contentType: "application/x-www-form-urlencoded",//application/json/text/json/text/plan
            data: $(data.form).serialize(),    //JSON.stringify(data.field),
            async: false,
            dataType: "json",
            success: function (R) {
                if (R.code == 0) {
                    $('#version').val(1);
                   // $t.Refresh();
                    //刷新页面
                    parent.layer.msg('操作成功 !', {icon: 1});
                } else if(R.code==1){
                    parent.layer.alert(R.msg, {icon: 5});

                } else{
                    parent.layer.msg(R.msg, {icon: 5});
                }
            },
            error: function (err) {
                alert("系统繁忙");
            }
        });
        return false;
    });
});

//更多查询条件监听事件
layui.use(['form'], function () {
    var form = layui.form;
    //监听提交
    form.on('submit(moreSearch)', function (data) {
        if($(this).children().hasClass("fa-chevron-down")){
            //显示更多条件
            $(this).parents(".layui-form").find(".more-search").show();
            //修改更多按钮图标
            $(this).html('<i class="fa fa-chevron-up">&nbsp;</i>收起');
        }else{
            //显示更多条件
            $(this).parents(".layui-form").find(".more-search").hide();
            //修改更多按钮图标
            $(this).html('<i class="fa fa-chevron-down">&nbsp;</i>更多');
        }

        return false;
    });
});
function addBreadcrumb(title) {
    li_item = "<li class='active'>"+title+"</li>";
    var ol = $(parent.document).contents().find("#nav_title");
    var lis = $(parent.document).contents().find("#nav_title li");
    var b=false;
    for(var i=0 ;i<lis.length;i++){
        s = $.trim(lis[i].innerText);
        if(s && s.search(title) !=-1){
            b=true;
            break;
        }
    }
    if(!b){
        ol.append(li_item);
    }
}

$(function () {
    //数字过多时tips显示表格中数据
    /*$(".nowrap").on("mouseover","td",(function(){
        if($(this).text().length>25){
            layer.tips($(this).text(), $(this));
        }

    }));*/
     //隐藏右侧更多li
     $("body").on("click",function () {
       $(parent.document).contents().find(".tabsMoreList").hide();
     })

    layui.config({
        skin:'layui-layer-lan'
    })

});