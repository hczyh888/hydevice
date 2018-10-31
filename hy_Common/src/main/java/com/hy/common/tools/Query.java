package com.hy.common.tools;



import com.hy.common.xss.SQLFilter;
import com.jfinal.kit.StrKit;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 查询参数
 *
 * @author
 * @email
 * @date
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	//当前页码
    private int page;
    //每页条数
    private int limit;
    //排序字段
    private String sidx;
    //排序
    private String order;
    public Query(Map<String, String[]> params){

        //this.putAll(params);

        //分页参数
        this.page = Integer.parseInt(params.get("page")[0].toString());
        this.limit = Integer.parseInt(params.get("limit")[0].toString());
        this.put("offset", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
       /* this.sidx = StrKit.notNull(params.get("sidx"))?params.get("sidx")[0].toString():"enterStockId";
        this.order = StrKit.notNull(params.get("order"))?params.get("order")[0].toString():"desc";*/

        this.sidx = StrKit.notNull(params.get("sidx"))?params.get("sidx")[0].toString():"";
        this.order = StrKit.notNull(params.get("order"))?params.get("order")[0].toString():"";

        this.put("sidx", SQLFilter.sqlInject(this.sidx,true));
        this.put("order", SQLFilter.sqlInject(this.order,false));

    }
    public static String[] makePara(Object obj, Map<String,String[]> paraMap) {
        String[] returnStr = new String[2];
        StringBuffer sqlStr = new StringBuffer(128);
        StringBuffer paraStr = new StringBuffer(128);
        /**
         * 循环遍历所有的元素，检测有没有这个名字
         */
        Field[] fields=obj.getClass().getDeclaredFields();
        Set<String> nameSet = paraMap.keySet();
        try {
            for(String name:nameSet){
                String[] props = name.split("\\.");
                boolean b=false;
                for (int i = 0; i < fields.length; i++) {
                    if(fields[i].getName().equals(props[0]))
                    {
                        b=true;
                        break;
                    }
                }
                if(b){
                    Class<?> type = obj.getClass().getDeclaredField(props[0]).getType();
                    if (paraMap.get(name) != null && !paraMap.get(name)[0].equals("")) {
                        if(type == String.class){
                            sqlStr.append(" and ").append(props[0]).append(" like '%").append(paraMap.get(name)[0]).append("%'");
                        }else if(type == Integer.class){
                            sqlStr.append(" and ").append(props[0]).append("=").append(paraMap.get(name)[0]);
                        }else if(type == Double.class){
                            sqlStr.append(" and ").append(props[0]).append("=").append(paraMap.get(name)[0]);
                        }else if(type == Boolean.class){
                            sqlStr.append(" and ").append(props[0]).append("=").append(paraMap.get(name)[0]);
                        }else if(type == Date.class){
                            sqlStr.append(" and ").append(props[0]).append(" ='").append(paraMap.get(name)[0]).append("'");
                        }
                        paraStr.append("&").append(name).append("=").append(paraMap.get(name)[0]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        returnStr[0] = sqlStr.toString();
        returnStr[1] = paraStr.toString();
        return returnStr;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }


}
