package com.hy.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.HttpKit;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;

/**
 * 拦截ajax的提交
 * contentType=application/json时，getPara无法获取数据的情况
 */
public class JSONInterceptor implements Interceptor {
    public void intercept(Invocation inv){
        HttpServletRequest request =inv.getController().getRequest();
        if("application/json".equals(request.getContentType())){
            String json= HttpKit.readData(request);
            request.setAttribute("json",json);
        }
        inv.invoke();
    }

}
