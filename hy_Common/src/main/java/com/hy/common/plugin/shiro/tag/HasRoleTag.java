package com.hy.common.plugin.shiro.tag;

import com.hy.common.tools.Func;
import com.jfinal.template.Env;
import com.jfinal.template.expr.ast.Expr;
import com.jfinal.template.expr.ast.ExprList;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;


/**
 * Created by jie on 2017/4/3.
 * 验证当前用户是否属于以下角色
 * #hasRole(roleName)
 * body
 * #end
 */
public class HasRoleTag extends SecureTag {
    private Expr[] exprs;

    public void setExprList(ExprList exprList) {
        exprs = exprList.getExprArray();
    }

    public void exec(Env env, Scope scope, Writer writer) {
        boolean hasRole = false;
        if (getSubject() != null && !Func.isEmpty(exprs))
            if (getSubject().hasRole(exprs[0].toString()))
                hasRole = true;

        if (hasRole)
            stat.exec(env, scope, writer);
    }

    public boolean hasEnd() {
        return true;
    }

}
