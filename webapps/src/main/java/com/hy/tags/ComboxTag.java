package com.hy.tags;

import com.hy.common.constant.ConstCache;
import com.hy.common.plugin.shiro.ShiroKit;
import com.hy.common.tools.Func;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.expr.ast.Expr;
import com.jfinal.template.expr.ast.ExprList;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

import java.util.List;

/**
 * 自定义下拉框标签
 * 页面调用如：#combox(type='dict',name='partner.nationality',value=partner.nationality,requried='',pcode='countryList')
 * @param type=dict 指查询哪张表
 * @param name=partner.field 对应的字段
 * @param value=对应的字段值，可以是request的内存变量
 * @param requried 是否必须
 * @param pcode 通过表中的这个字段值过滤相应的下拉数据
 */
public class ComboxTag extends Directive {
    private Expr valueExpr;
    @Override
    public void setExprList(ExprList exprList) {
        super.setExprList(exprList);
        //该方法非必须
    }
    @Override
    public void exec(Env env, Scope scope, Writer writer) {
        this.valueExpr = exprList.getExpr(2);
        Object[] ob=exprList.evalExprList(scope);
        //ob的格式，也就是前端页面传递过来的参数
        String type = ob[0].toString();
        String name = ob[1].toString();
        int value = Func.toInt(valueExpr.eval(scope));
        String token = (value > 0) ? "" : "token_";
        String required = ob[3].toString();
        String sql = "";
        String pcode= ob[4].toString();
        String CACHE_NAME = ConstCache.DICT_CACHE;
        if (type.equals("dict")) {
            sql = "select ID,pId as PID,name as TEXT from  sys_dict where pcode='" + pcode + "' and code>0";
        } else if (type.equals("user")) {
            CACHE_NAME = ConstCache.USER_CACHE;
            sql = "select ID,CONCAT_WS('|',name,account) as TEXT from  sys_user where status=1";
        } else if (type.equals("dept")) {
            CACHE_NAME = ConstCache.DEPT_CACHE;
            sql = "select ID,pid as PID,simpleName as TEXT from  sys_dept";
        } else if (type.equals("role")) {
            CACHE_NAME = ConstCache.ROLES_CACHE;
            sql = "select ID,name as TEXT from  sys_role";
        }else if (type.equals("leaveStock")) {
            CACHE_NAME = ConstCache.LEAVESTOCK_CACHE;
            sql = "select ID,leaveType as TEXT from  stock_leaveStock";
        }else if (type.equals("wareHouse")) {
            CACHE_NAME = ConstCache.WAREHOUSE_CACHE;
            sql = "select ID,type as TEXT from  stock_warehouse";
        }else if (type.equals("company")) {
            CACHE_NAME = ConstCache.WAREHOUSE_CACHE;
            sql = "select ID,cnName as TEXT from  sys_company";
        }else if (type.equals("deviceclass")) {
            CACHE_NAME = ConstCache.DEVICECLASS_CACHE;
            sql = "select ID,pid as PID,sn as TEXT from  sys_deviceclass";
        }else if (type.equals("devicelist")) {
            CACHE_NAME = ConstCache.DEVICECLASS_CACHE;
            sql = "select ID,SN,PID, CONCAT_WS('|',NAME,sn) as TEXT from  sys_deviceclass";
        }
        final String _sql = sql;

        List<Record> dict = CacheKit.get(CACHE_NAME, "dict_" + type + "_" + pcode + "_"+ ShiroKit.getUser().getId(), new IDataLoader() {
            @Override
            public Object load() {
                return Db.find(_sql);
            }
        });
        StringBuilder sb = new StringBuilder();
        String sid = "_" + name.split("\\.")[1];
        sb.append("<select class=\"form-control\" onchange=\"" +sid + "_selectChanged('" + sid + "')\" "  + " lay-verify='"+ required+"' lay-search id=\"" + sid + "\"  name=\"" + name + "\">");
        sb.append("<option value></option>");

        for (Record dic : dict) {
            int id =  Func.toInt(dic.get("ID"));
            String selected = "";
            if (id == value) {
                selected = "selected";
            }
            sb.append("<option " + selected + " value=\"" + id + "\">" + dic.get("TEXT") + "</option>");
        }
        sb.append("</select>");
        write(writer,sb.toString());
        //stat.exec(env, scope, writer);
    }

    @Override
    public boolean hasEnd() {
        return false;
    }
}
