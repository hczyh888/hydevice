package com.hy.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class TokenInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        inv.getController().createToken("hyToken");
        inv.invoke();
    }
}
