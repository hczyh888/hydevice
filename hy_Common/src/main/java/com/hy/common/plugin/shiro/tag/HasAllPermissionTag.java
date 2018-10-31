package com.hy.common.plugin.shiro.tag;

import com.hy.common.tools.Func;
import com.jfinal.template.Env;
import com.jfinal.template.expr.ast.Expr;
import com.jfinal.template.expr.ast.ExprList;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;


/**
 * Created by jie on 2017/4/3.
 * 验证当前Subject是否有下列全部权限
 * #hasAllPermission(permissionName1,permissionName2)
 * body
 * #end
 */
public class HasAllPermissionTag extends SecureTag {
    private Expr[] exprs;

    public void setExprList(ExprList exprList) {
        exprs = exprList.getExprArray();
    }

    public void exec(Env env, Scope scope, Writer writer) {
        if (getSubject() != null && !Func.isEmpty(exprs)) {
            boolean hasAllPermission = true;
            for (Expr expr : exprs)
                if (!getSubject().isPermitted(expr.toString())) {
                    hasAllPermission = false;
                    break;
                }

            if (hasAllPermission)
                stat.exec(env, scope, writer);
        }
    }

    public boolean hasEnd() {
        return true;
    }
}
