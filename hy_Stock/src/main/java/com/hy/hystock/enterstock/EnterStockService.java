package com.hy.hystock.enterstock;


import com.hy.common.model.Enterstock;
import com.hy.common.model.LeaveStock;
import com.hy.common.model.Parameter;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;

public class EnterStockService {

    public static final EnterStockService me = new EnterStockService();
    private final Enterstock dao = new Enterstock().dao();
    private final Parameter paradao = new Parameter().dao();
    private final String allEnterStockCacheName = "allEnterStock";

    /**
     * 获取 apply 对象
     */
    public Enterstock getById(int enterStockId) {
        return dao.findById(enterStockId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(Enterstock enterstock) {
        return enterstock.update();
    }
    /**
     * 根据ID删除数据
     * @param enterStockId
     * @return
     */
    public boolean deleteById(int enterStockId){
        return dao.deleteById(enterStockId);
    }
    /**
     * 根据 EnterstockId 值移除缓存
     */
    public void clearCache(int enterStockId) {
        CacheKit.remove(allEnterStockCacheName, enterStockId);
    }

    public Enterstock edit(int enterStockId){
        Enterstock enterstock =  dao.findById(enterStockId);
        /*//拿对应的明细
        List<Record> enter = Db.find(Db.getSqlPara("enterstockdetail.selectByEnterId", Kv.by("enterId",enterStockId)));
        enterstock.setEnterdetailList(enter);*/
        return enterstock;
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Enterstock> enterstockList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("enterstock.select"),Db.getSql("enterstock.fromWhere")+" "+sWhere+sOrderBy);
    }


    public Parameter value(String dhType){
        Parameter parameter = paradao.findFirst("select para from sys_parameter where num=1 and code='"+dhType+"'");
      /*  Parameter parameter6 = paradao.findFirst("select para from sys_parameter where num=4");
        Parameter parameter7 = paradao.findFirst("select para from sys_parameter where num=7");
        Parameter parameter8 = paradao.findFirst("select para from sys_parameter where num=8");
        Parameter[] val={parameter5,parameter6,parameter7,parameter8};*/
        return parameter ;
    }

}



