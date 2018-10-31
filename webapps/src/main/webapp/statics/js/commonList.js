/**
 * Created by chenyi on 2017-10-20 13:54:57
 *  email   :  qq228112142@qq.com//
 */
/**数据渲染对象*/
var Render = {
    /**
     * 渲染状态列
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @param index
     * @param value      当前对象值
     */
    customState: function (rowdata,renderData,index, value) {

        if(value == "启用"){
            return '<span style="color:green">'+value+'</span>';
        }
        if(value == "禁用"){
            return '<span style="color:red">'+value+'</span>';
        }
        return value;
    },
    link:function (rowdata,renderData,index, value)  {
        return "<a >"+value+"</a>";
    },

    /**
     * 渲染状态列
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @param index
     * @param value      当前对象值
     */
    enterStatus: function (rowdata,renderData,index, value) {

        if(value == "未审核"){
            return '<span style="color:green">'+value+'</span>';
        }
        if(value == "已审核"){
            return '<span style="color:red">'+value+'</span>';
        }
        return value;
    },
    link:function (rowdata,renderData,index, value)  {
        return "<a >"+value+"</a>";
    },



    /**
     * 渲染状态列
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @param index
     * @param value      当前对象值
     */
    planApplyState: function (rowdata,renderData,index, value) {


        if(value == "审核未通过"){
            return '<span style="color:red">'+value+'</span>';
        }
        if (value =="待审核"){
            return '<span style="color:orange">'+value+'</span>';
        }
        if (value =="已下达"||"待下达"){
            return '<span style="color:green">'+value+'</span>';
        }
        return value;
    },
    /**
     * 渲染状态列
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @param index
     * @param value      当前对象值
     */
    leaveStockState: function (rowdata,renderData,index, value) {
        if(value == "审核通过"){
            return '<span style="color:red">'+value+'</span>';
        }
        if (value =="审核未通过"){
            return '<span style="color:orange">'+value+'</span>';
        }
        return value;
    },
    /**
     * 渲染状态列
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @param index
     * @param value      当前对象值
     */
    planState: function (rowdata,renderData,index, value) {

        if(value == "已生成"){

            return '<span style="color:green">'+value+'</span>';
        }
        if(value == "未生成"){
            return '<span style="color:red">'+value+'</span>';
        }
        return value;
    },
    /**
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @description      详情按钮渲染
     */
    info:function(rowdata,renderData){
        var btn=' <button  onclick="openIframe(\''+rowdata.id+'\',\''+renderData.url+'\',\''+renderData.title+'\',\''+renderData.area+'\')" class="layui-btn layui-btn-normal layui-btn-xs">'+renderData.title+'</button>';
        return btn;
    },
    rowclick:function(rowdata,renderData){
        var tr=' <tr style=\'cursor:pointer\' onclick="openIframe(\''+rowdata.id+'\',\''+renderData.url+'\',\''+renderData.title+'\',\''+renderData.area+'\')"> </tr>';
        return tr;
    },
    /**
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @description      审核按钮渲染
     */
    check:function(rowdata,renderData){
        var btn=' <button  onclick="openIframe(\''+rowdata.id+'\',\''+renderData.url+'\',\''+renderData.title+'\',\''+renderData.area+'\')" class="layui-btn layui-btn-sm">'+renderData.title+'</button>';
        return btn;
    },
    /**
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @description      生成任务按钮渲染
     */
    task:function(rowdata,renderData){
        var btn='';
        if(rowdata.status==1){

            btn=' <span class="layui-bg-gray">任务已生成</span>';

        }else{
            btn=' <button  onclick="openIframe(\''+rowdata.id+'\',\''+renderData.url+'\',\''+renderData.title+'\',\''+renderData.area+'\')"  class="layui-btn layui-btn-sm">'+renderData.title+'</button>';
        }
        return btn;
    },
    /**
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @description      审核按钮渲染
     */
    issued:function(rowdata,renderData){
        var btn='';
        if(rowdata.status==1){
            btn='<span class="layui-bg-gray">未下达</span>'
        }else{
            btn=' <button  onclick="openIframe(\''+rowdata.id+'\',\''+renderData.url+'\',\''+renderData.title+'\',\''+renderData.area+'\')" class="layui-btn layui-btn-sm">'+renderData.title+'</button>';
        }

        return btn;
    },
    /**
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @description      审核按钮渲染
     */
    audited:function(rowdata,renderData){
        var btn='';
        if(rowdata.status==1){
            btn='<span class="layui-bg-gray">未审核</span>'
        }else{
            btn=' <button  onclick="openIframe(\''+rowdata.id+'\',\''+renderData.url+'\',\''+renderData.title+'\',\''+renderData.area+'\')" class="layui-btn layui-btn-sm">'+renderData.title+'</button>';
        }

        return btn;
    },
    /**
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @description      完善计划审核按钮渲染
     */
    prePlan:function(rowdata,renderData){
        var btn=' <button  onclick="openIframe(\''+rowdata.id+'\',\''+renderData.url+'\',\''+renderData.title+'\',\''+renderData.area+'\')" class="layui-btn layui-btn-sm">'+renderData.title+'</button>';
        return btn;
    },
    /**
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @description      工作进度按钮渲染
     */
    progress:function(rowdata,renderData){
        var btn=' <button  onclick="openIframe(\''+rowdata.id+'\',\''+renderData.url+'\',\''+renderData.title+'\',\''+renderData.area+'\')" class="layui-btn layui-btn-sm">'+renderData.title+'</button>';
        return btn;
    },
    /**
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @description      编辑按钮渲染
     */
    edit:function(rowdata,renderData){
        var btn=' <button  onclick="openIframe(\''+rowdata.id+'\',\''+renderData.url+'\',\''+renderData.title+'\',\''+renderData.area+'\')" class="layui-btn layui-btn-xs layui-btn-warm">'+renderData.title+'</button>';
        return btn;
    },
    /**
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @description      修改按钮渲染
     */
    audit:function(rowdata,renderData){
        var btn=' <button  onclick="openIframe(\''+rowdata.id+'\',\''+renderData.url+'\',\''+renderData.title+'\',\''+renderData.area+'\')" class="layui-btn layui-btn-xs">'+renderData.title+'</button>';
        return btn;
    },
    /**
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @description      修改按钮渲染
     */
    export:function(rowdata,renderData){
        var btn=' <button  onclick="openIframe(\''+rowdata.id+'\',\''+renderData.url+'\',\''+renderData.title+'\',\''+renderData.area+'\')" class="layui-btn layui-btn-xs">'+renderData.title+'</button>';
        return btn;
    },
    /**
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @description     删除按钮渲染
     */
    delete:function(rowdata,renderData){
        var btn=' <button  onclick="deleteOne(\''+rowdata.id+'\',\''+renderData.url+'\',\''+renderData.title+'\')" class="layui-btn layui-btn-xs layui-btn-danger">'+renderData.title+'</button>';
        return btn;
    },
    assign:function(rowdata,renderData){
        var btn=' <button  onclick="openIframe(\''+rowdata.id+'\',\''+renderData.url+'\',\''+renderData.title+'\',\''+renderData.area+'\')" class="layui-btn layui-btn-sm ">分配角色权限</button>';
        return btn;
    },
    /**
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @description     启用禁用按钮渲染
     */
    state:function(rowdata,renderData){

        if(rowdata.state=='0'){
            return' <button onclick="updateStateOne(\''+"启用"+'\',\''+"/sysoss/enable"+'\',\''+rowdata.bucket+'\')"' +
                '  class="layui-btn layui-btn-mini layui-btn-green">启用</button>';
        }
        if(rowdata.state=='1'){
            return' <button  onclick="updateStateOne(\''+"禁用"+'\',\''+"/sysoss/limit"+'\',\''+rowdata.bucket+'\')" ' +
                'class="layui-btn layui-btn-mini layui-btn-danger">禁用</button>';
        }
        return "";
    },
    getDataByCode:function(codeName) {
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
    renderValue:function (codeName,key) {
        var codeValues = this.getDataByCode(codeName).data || "";
        var value="";
        for (var _code in codeValues) {
            if (codeValues[_code].code == key) {
                value = codeValues[_code].value;
                break;
            }
        }
        return value;
    }
};
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
