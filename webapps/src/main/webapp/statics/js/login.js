layui.config({
    base: "js/"
}).use(['form', 'layer','element'], function () {
    var form = layui.form,
        element = layui.element,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery;
    //登录按钮事件
    form.on("submit(login)", function (data) {
        var datas = "account=" + data.field.account + "&password=" + data.field.password + "&imgCode=" + data.field.imgCode;
        $.ajax({
            type: "POST",
            url: "/doLogin",
            data: datas,
            dataType: "json",
            success: function (result) {
                if (result.state == 'ok') {//登录成功
                    location.href = '/';
                } else {
                    layer.msg(result.msg, {icon: 5});
                    refreshCode();
                }
            }
        });
        return false;
    })
});
function refreshCode(){
    var captcha = document.getElementById("captcha");
    captcha.src = "/captcha?t=" + new Date().getTime();
}
