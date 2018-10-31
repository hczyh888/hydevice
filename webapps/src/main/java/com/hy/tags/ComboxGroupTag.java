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
import com.jfinal.template.expr.ast.ExprList;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

import java.util.List;

public class ComboxGroupTag extends Directive {
    @Override
    public void setExprList(ExprList exprList) {
        super.setExprList(exprList);
        //该方法非必须
    }
    @Override
    public void exec(Env env, Scope scope, Writer writer) {
        Object[] ob=exprList.evalExprList(scope);
        String type = ob[0].toString();
        String name = ob[1].toString();
        int value = Func.toInt(ob[2]);
        String token = (value > 0) ? "" : "token_";
        String required = ob[3].toString();
        String sql = "";
        String code="";
        String CACHE_NAME = ConstCache.DICT_CACHE;
        if (type.equals("dict")) {
            sql = "select num as ID,pId as PID,name as TEXT from  sys_dict where code=" + code + " and num>0";
        } else if (type.equals("user")) {
            CACHE_NAME = ConstCache.USER_CACHE;
            sql = "select ID,CONCAT_WS('|',name,account) as TEXT from  sys_user where status=1";
        } else if (type.equals("dept")) {
            CACHE_NAME = ConstCache.DEPT_CACHE;
            sql = "select ID,PID,SIMPLENAME as TEXT from  sys_dept";
        } else if (type.equals("role")) {
            CACHE_NAME = ConstCache.ROLES_CACHE;
            sql = "select ID,name as TEXT from  sys_role";
        }else if (type.equals("devicelist")) {
            CACHE_NAME = ConstCache.DEVICECLASS_CACHE;
            sql = "select ID,SN,PID, CONCAT_WS('|',NAME,sn) as TEXT from  sys_deviceclass";
        }
        final String _sql = sql;

        List<Record> dict = CacheKit.get(CACHE_NAME, "dictg_" + type + "_"+code + "_" + ShiroKit.getUser().getId(), new IDataLoader() {
            @Override
            public Object load() {
                return Db.find(_sql);
            }
        });
        StringBuilder sb = new StringBuilder();
        String sid = "_" + name.split("\\.")[1];
        sb.append("<select onchange=\"" +sid + "_selectChanged('" + sid + "')\" " + required + " lay-verify=''class=\"form-control\" lay-search id=\"" + sid + "\"  name=\"" + token + name + "\">");
        sb.append("<option value></option>");

        for (Record dic : dict) {
            int id =  Func.toInt(dic.get("ID"));
            int sn =  Func.toInt(dic.get("SN"));
            int pid = Func.toInt(dic.get("PID"));
            String selected = "";
            if (id == value) {
                selected = "selected";
            }
            if(id==pid) { //pItem begin
                sb.append("<optgroup label='"  + dic.get("TEXT") + "'style='color: #f0ab49'>");

            }else if(1==2){ //pItem end
                sb.append("</optgroup"); //先关闭老的，在开始新的分组
                sb.append("<optgroup label='" + dic.get("TEXT") + "'>");
            }
            for (Record dic1:dict){
                int pid1 =  Func.toInt(dic1.get("PID"));

                if (id==pid1){
                    sb.append("<option " + selected + " value=\"" + dic1.get("ID")+","+dic1.get("PID")+","+dic1.get("SN") + "\">" + dic1.get("TEXT") + "</option>");
                }
            }
        }
        sb.append("</optgroup>");
        sb.append("</select>");
        write(writer,sb.toString());
        stat.exec(env, scope, writer);

    }
    @Override
    public boolean hasEnd() {
        //告诉jfinal，这个指令是包含#end标识结束的
        return true;
    }
}
