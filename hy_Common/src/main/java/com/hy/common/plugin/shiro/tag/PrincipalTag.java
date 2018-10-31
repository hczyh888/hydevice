package com.hy.common.plugin.shiro.tag;

import com.jfinal.kit.LogKit;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

import java.io.IOException;

public class PrincipalTag extends SecureTag{
    public void exec(Env env, Scope scope, Writer writer) {
        if (getSubject() != null && getSubject().getPrincipal() != null) {
            Object principal = getSubject().getPrincipal();
            try {
                writer.write(principal.toString());
            } catch (IOException e) {
                LogKit.error("PrincipalTag IOException");
                e.printStackTrace();
            }
        }
    }
}
