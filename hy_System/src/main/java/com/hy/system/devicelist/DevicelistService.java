package com.hy.system.devicelist;


import com.hy.common.model.Devicelist;
import com.hy.common.model.Warehouse;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

public class DevicelistService {
    public static final DevicelistService me = new DevicelistService();
    private final Devicelist dao = new Devicelist().dao();
    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Devicelist> devicelistList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize,Db.getSql("devicelist.select"),Db.getSql("devicelist.fromWhere")+" "+sWhere+sOrderBy);
    }
    public Devicelist edit(int devicelistId){
        return dao.findById(devicelistId);
    }
    public boolean update(Devicelist devicelist){
        return devicelist.update();
    }
    public boolean save(){
        return dao.save();
    }
    public boolean deleteById(int devicelistId){
        return dao.deleteById(devicelistId);
    }
    public Devicelist getById(int devicelistId){
        return dao.findById(devicelistId);
    }

    /**
     * 获取库房下拉框数据
     */
    public List<Devicelist> getDeviceList(int pid) {
        return  dao.find("select id ,name text from sys_deviceclass where pid="+pid+" order by id");
    }
}
