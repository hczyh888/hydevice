package com.hy.common.plugin.shiro.tag;

import com.hy.common.tools.Func;
import com.jfinal.template.Env;
import com.jfinal.template.expr.ast.Expr;
import com.jfinal.template.expr.ast.ExprList;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;


/**
 * Created by jie on 2017/4/3.
 * 验证当前Subject是否有该权限
 * #hasPermission(permissionName)
 * body
 * #end
 */
public class HasPermissionTag extends SecureTag {
    private Expr[] exprs;

    public void setExprList(ExprList exprList) {
        exprs = exprList.getExprArray();
    }

    public void exec(Env env, Scope scope, Writer writer) {
        if (getSubject() != null && !Func.isEmpty(exprs))
            if (getSubject().isPermitted(exprs[0].toString()))
                stat.exec(env, scope, writer);
    }

    public boolean hasEnd() {
        return true;
    }
}
