/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.hy.common.plugin.shiro.tag;

import com.hy.common.tools.Func;
import com.jfinal.template.Env;
import com.jfinal.template.expr.ast.Expr;
import com.jfinal.template.expr.ast.ExprList;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;


/**
 * Created by jie on 2017/4/3.
 * 验证当前Subject是否没有角色
 * #lacksRole(roleName)
 * body
 * #end
 */
public class LacksRoleTag extends SecureTag {
    private Expr[] exprs;


    public void setExprList(ExprList exprList) {
        exprs = exprList.getExprArray();
    }


    public void exec(Env env, Scope scope, Writer writer) {
        boolean hasAnyRole = false;
        if (getSubject() != null && !Func.isEmpty(exprs)) {
            for (Expr expr : exprs) {
                if (getSubject().hasRole(expr.toString())) {
                    hasAnyRole = true;
                    break;
                }
            }
        }
        if (!hasAnyRole)
            stat.exec(env, scope, writer);
    }

    public boolean hasEnd() {
        return true;
    }


}