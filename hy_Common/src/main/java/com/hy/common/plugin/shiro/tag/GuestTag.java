package com.hy.common.plugin.shiro.tag;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;


/**
 * Created by jie on 2017/4/3.
 * <p>
 * 用户没有身份验证时显示相应信息，即游客访问信息。
 * #guest()
 * body
 * #end
 */
public class GuestTag extends SecureTag {
    public void exec(Env env, Scope scope, Writer writer) {
        if (getSubject() == null || getSubject().getPrincipal() == null)
            stat.exec(env, scope, writer);
    }

    public boolean hasEnd() {
        return true;
    }
}
