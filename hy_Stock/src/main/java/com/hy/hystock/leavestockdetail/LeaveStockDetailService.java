package com.hy.hystock.leavestockdetail;

import com.hy.common.model.LeaveStockDetail;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;

public class LeaveStockDetailService {

    public static final LeaveStockDetailService me = new LeaveStockDetailService();
    private final LeaveStockDetail dao = new LeaveStockDetail().dao();
    private final String allLeaveStockDetailCacheName = "allLeaveStockDetail";

    /**
     * 获取 apply 对象
     */
    public LeaveStockDetail getById(int leaveStockDetailId) {
        return dao.findById(leaveStockDetailId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(LeaveStockDetail leaveStockDetail) {
        return leaveStockDetail.update();
    }
    /**
     * 根据ID删除数据
     * @param leaveStockDetailId
     * @return
     */
    public boolean deleteById(int leaveStockDetailId){
        return dao.deleteById(leaveStockDetailId);
    }
    /**
     * 根据 leaveStockId 值移除缓存
     */
    public void clearCache(int leaveStockDetailId) {
        CacheKit.remove(allLeaveStockDetailCacheName, leaveStockDetailId);
    }
    public List<Record> getByLeaveId(int leaveId){
        //拿对应的明细
        return  Db.find(Db.getSqlPara("leavestockdetail.selectByLeaveId", Kv.by("leaveId",leaveId)));
    }

    /**
     * 通过主表ID删除对应的明细记录
     * @param leaveId
     * @return
     */
    public int deleteByLeaveId(int leaveId){
        return Db.delete("delete from stock_leavestock_detail where leaveId ="+leaveId);
    }

    public LeaveStockDetail edit(int leaveStockDetailId){
        return dao.findById(leaveStockDetailId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<LeaveStockDetail> leavestockdetailList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("leavestockdetail.select"),Db.getSql("leavestockdetail.fromWhere")+" "+sWhere+sOrderBy);
    }


}