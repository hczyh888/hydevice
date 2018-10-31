package com.hy.system.deviceclass;


import com.hy.common.model.Deviceclass;
import com.hy.common.model.Devicelist;
import com.hy.common.model.Warehouse;
import com.jfinal.json.FastJson;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;

import java.util.List;

public class DeviceclassService {
    public static final DeviceclassService me = new DeviceclassService();
    private final Deviceclass dao = new Deviceclass().dao();
    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Deviceclass> deviceclassList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize,Db.getSql("deviceclass.select"),Db.getSql("deviceclass.fromWhere")+" "+sWhere+sOrderBy);
    }
    public Deviceclass edit(int deviceclassId){
        return dao.findById(deviceclassId);
    }
    public boolean update(Deviceclass deviceclass){
        return deviceclass.update();
    }
    public boolean save(){
        return dao.save();
    }
    public boolean deleteById(int deviceclassId){
        return dao.deleteById(deviceclassId);
    }
    public Deviceclass getById(int deviceclassId){
        return dao.findById(deviceclassId);
    }

    /**
     * 获取库房下拉框数据
     */
    public List<Deviceclass> getDeviceClass(int pid) {
        return  dao.find("select id ,name text from sys_deviceclass where pid="+pid+" order by id");
    }

}
