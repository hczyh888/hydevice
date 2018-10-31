package com.hy.hystock.leavestock;


import com.hy.common.model.Enterstock;
import com.hy.common.model.LeaveStock;
import com.hy.common.model.LeaveStockDetail;
import com.hy.common.model.Parameter;
import com.hy.hystock.leavestockdetail.LeaveStockDetailService;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;

public class LeaveStockService {

    public static final LeaveStockService me = new LeaveStockService();
    private final LeaveStock dao = new LeaveStock().dao();
    private final Parameter paradao = new Parameter().dao();
    private final String allLeaveStockCacheName = "allLeaveStock";

    /**
     * 获取 apply 对象
     */
    public LeaveStock getById(int leavestockId) {
        return dao.findById(leavestockId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(LeaveStock leavestock) {
        return leavestock.update();
    }
    /**
     * 根据ID删除数据
     * @param leavestockId
     * @return
     */
    public boolean deleteById(int leavestockId){
        return dao.deleteById(leavestockId);
    }
    /**
     * 根据 LeavestockId 值移除缓存
     */
    public void clearCache(int leavestockId) {
        CacheKit.remove(allLeaveStockCacheName, leavestockId);
    }


    public LeaveStock edit(int leavestockId){
        //拿头
        LeaveStock leavestock =  dao.findById(leavestockId);

        return leavestock;
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<LeaveStock> leavestockList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("leavestock.select"),Db.getSql("leavestock.fromWhere")+" "+sWhere+sOrderBy);
    }
    //在参数表查询数据
    public Parameter value( String dhTypes){
        Parameter parameter = paradao.findFirst("select para from sys_parameter where num=2 and code='"+dhTypes+"'");
        return parameter ;
    }

}



