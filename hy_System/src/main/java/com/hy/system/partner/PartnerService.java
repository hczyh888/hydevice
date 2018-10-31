package com.hy.system.partner;


import com.hy.common.model.Partner;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

public class PartnerService {
    public static final PartnerService me = new PartnerService();
    private final Partner dao = new Partner().dao();
    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Partner> partnerList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize,Db.getSql("partner.select"),Db.getSql("partner.fromWhere")+" "+sWhere+sOrderBy);
    }
    public Partner edit(int partnerId){
        return dao.findById(partnerId);
    }
    public boolean update(Partner parter){
        return parter.update();
    }
    public boolean save(){
        return dao.save();
    }
    public boolean deleteById(int partnerId){
        return dao.deleteById(partnerId);
    }
    public Partner getById(int partnerId){
        return dao.findById(partnerId);
    }
}
