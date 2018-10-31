package com.hy.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class FormTokenValidator implements Interceptor{
    @Override
    public void intercept(Invocation inv) {
        Controller controller= inv.getController();
        if(!controller.validateToken("hyToken")){
            controller.render("/common/error.html");
        }else{
            inv.invoke();
        }

    }
}
