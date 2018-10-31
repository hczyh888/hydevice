package com.hy.hystock.deviceallot;

import com.hy.common.constant.ConstCache;
import com.hy.common.model.Allot;
import com.hy.common.model.Parameter;
import com.hy.common.model.Roles;
import com.hy.common.tools.Func;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;

import java.util.List;
import java.util.Map;


public class DeviceAllotService implements ConstCache{
    public static final DeviceAllotService me = new DeviceAllotService();
    private final Allot dao = new Allot().dao();
    private final Parameter paradao = new Parameter().dao();
    private final String allAllotCacheName = "allAllot";
    /**
     * 返回分页的List数据
     * @return
     */

    public Page<Allot> allotList(int curPage,int pageSize,String sWhere,String orderFiled,String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize,Db.getSql("allot.select"),Db.getSql("allot.fromWhere")+" "+sWhere+sOrderBy);
    }
    public Allot edit(int allotId) {

        return dao.findById(allotId);
    }
    public boolean update(Allot allotId){
        return allotId.update();
    }
    public boolean save(){
        return dao.save();
    }
    public boolean deleteById(int allotId){
        return dao.deleteById(allotId);
    }
    public Allot getById(int allotId){
        return dao.findById(allotId);
    }

    public Parameter value(String dhType){
        Parameter parameter = paradao.findFirst("select para from sys_parameter where num=3 and code='"+dhType+"'");
        return parameter ;
    }

}
