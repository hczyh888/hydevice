package com.hy.system.user;


import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.validate.Validator;

public class UserValidator extends Validator{
    UserService userService = UserService.me;
    protected void validate(Controller c) {
        validateAccount("user.account","errorAccount","该账号已经存在，请重新录入！");
    }

    protected void handleError(Controller c) {
        //c.keepPara();
        c.renderJson(Ret.fail().set("msg",c.getRequest().getAttribute("errorAccount")).set("code",1));
    }
    protected void validateAccount(String field,String errorKey, String errorMessage) {
        if(userService.isExitByAccount(controller.getPara(field))){
            addError(errorKey,errorMessage);
        }

    }
}
