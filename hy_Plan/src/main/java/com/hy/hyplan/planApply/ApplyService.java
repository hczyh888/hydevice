package com.hy.hyplan.planApply;


import com.hy.common.model.Apply;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;

public class ApplyService {

    public static final ApplyService me = new ApplyService();
    private final Apply dao = new Apply().dao();
    private final String allApplyCacheName = "allApply";

    /**
     * 获取 Apply 对象
     */
    public Apply getById(int applyId) {
        return dao.findById(applyId);
    }
    /**
     * 修改 Apply 对象数据
     */
    public boolean update(Apply apply) {
        return apply.update();
    }
    /**
     * 根据ID删除数据
     * @param applyId
     * @return
     */
    public boolean deleteById(int applyId){
        return dao.deleteById(applyId);
    }
    /**
     * 根据 ApplyId 值移除缓存
     */
    public void clearCache(int applyId) {
        CacheKit.remove(allApplyCacheName, applyId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Apply> applyList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        Page<Apply> paginate = dao.paginate(curPage, pageSize, Db.getSql("apply.select"), Db.getSql("apply.fromWhere") + " " + sWhere + sOrderBy);
        return paginate;
    }

    public boolean Save(){
        return  dao.save();
    }

    public Apply edit(int applyId){
        return dao.findById(applyId);
    }
    public Apply getApplyAll(int id){
        Apply apply = dao.findById(id);
        List<Record> planpartnerLst = Db.find("select * from plan_applypartner where applyId=?",id);
        Record applybenefit =  Db.findFirst("select * from plan_applybenefit where applyId=?",id);
        Record applyinfor =  Db.findFirst("select * from plan_applyinfor where applyId=?",id);
        /*List<Record> deviceId = Db.find("select * from sys_devicelist where deviceId=?", apply.getDeviceId());*/

        apply.setApplyPartnerLst(planpartnerLst);
        apply.setApplybenefit(applybenefit);
        apply.setApplyinfor(applyinfor);
        /*apply.setDevice(deviceId);*/
        return apply;
    }
}



