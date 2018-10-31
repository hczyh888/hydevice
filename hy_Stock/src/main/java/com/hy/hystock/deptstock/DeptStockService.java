package com.hy.hystock.deptstock;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hy.common.model.Deptstock;
import com.hy.common.tools.RequestJson;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;

import java.lang.reflect.Array;

public class DeptStockService {

    public static final DeptStockService me = new DeptStockService();
    private final Deptstock dao = new Deptstock().dao();
    private final String allDeptStockCacheName = "allDeptStock";

    /**
     * 获取 deptstock 对象
     * @param deptStockId
     * @return
     */
    public Deptstock getById(int deptStockId) {
        return dao.findById(deptStockId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(Deptstock deptstock) {
        return deptstock.update();
    }
    /**
     * 根据ID删除数据
     * @param deptStockId
     * @return
     */
    public boolean deleteById(int deptStockId){
        return dao.deleteById(deptStockId);
    }
    /**
     * 根据 deptStockId 值移除缓存
     */
    public void clearCache(int deptStockId) {
        CacheKit.remove(allDeptStockCacheName, deptStockId);
    }

    public Deptstock edit(int deptStockId){
        return dao.findById(deptStockId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Deptstock> deptstockList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("deptstock.select"),Db.getSql("deptstock.fromWhere")+" "+sWhere+sOrderBy);
    }
    @Before(Tx.class)
    public Boolean BatchSave(RequestJson requestJson){
        Boolean b = false;
        if(requestJson!=null&&requestJson.getDataArray().length>0){
            String[] datas=requestJson.getDataArray(); //字符串数组
            for(int i=0;i<datas.length;i++){
                JSONObject obj = JSON.parseObject(datas[i]);
                Deptstock deptstock = new Deptstock();
                deptstock.setDeptId(obj.getInteger("devicename"));
                b =deptstock.save();
            }

        }
        return b;
    }


}



