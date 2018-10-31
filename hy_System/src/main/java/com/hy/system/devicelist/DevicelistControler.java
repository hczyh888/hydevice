package com.hy.system.devicelist;

import com.hy.common.controller.BaseController;
import com.hy.common.enumresource.StateEnum;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.kit.DateTimeKit;
import com.hy.common.model.Deviceclass;
import com.hy.common.model.Devicelist;
import com.hy.common.model.Partner;
import com.hy.common.tools.PinyingUtil;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.system.deviceclass.DeviceclassService;
import com.hy.system.partner.PartnerService;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;
import java.util.Map;

public class DevicelistControler extends BaseController {
    DevicelistService devicelistService= DevicelistService.me;
    DeviceclassService deviceclassService= DeviceclassService.me;
    PartnerService partnerService = PartnerService.me;

    public void index(){

        setAttr("stateEnum",getEnumRet("StateEnum").toJson());
        setAttr("unitList",getDictListByCache(unitList).toJson());//获取单位列表传前端页面
        setAttr("brandList",getDictListByCache(brandList).toJson()); //获取品牌列表传前端页面
        render("devicelist.html");
    }

    /**
     *
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Devicelist() , paraMap);
        String sWhere=paraStr[0];
        Page<Devicelist> page=devicelistService.devicelistList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Devicelist>  rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        //PageUtils pageUtils=new PageUtils(rows,count,10,curPage);
        Ret ret = Ret.by("code",0).set("count",count).set("data",rows);
        renderJson(ret);
    }
    public  void add(){
        render("add.html");
    }
    public void edit(){
        Devicelist devicelist = devicelistService.edit(getParaToInt(0, 0));
        setAttr("devicelist",devicelist);
        render("edit.html");
    }
    public void update(){
        Devicelist devicelist =getBean(Devicelist.class,"devicelist");
        if(devicelistService.update(devicelist)){
            Ret ret =Ret.ok("msg","更新成功！").set("code",0);
            ret.set("datas",devicelist);
            renderJson(ret);
        }else{
            Ret ret=Ret.fail("msg","更新失败！").set("code",-1);
            renderJson(ret);
        }
    }
    public void doadd(){
        Devicelist devicelist= getBean(Devicelist.class,"devicelist");
        devicelist.setCreateTime(DateTimeKit.date());
        String cnName=devicelist.getCnName();
        devicelist.setSimpleName(PinyingUtil.getGBKpy(cnName));
        Ret ret=null;
        if(devicelist.save()){
            ret = Ret.ok("msg","保存成功！").set("code",0);
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);
    }
    public void view(){
        Devicelist devicelist = devicelistService.getById(getParaToInt(0,0));
        Deviceclass deviceclass = deviceclassService.getById(devicelist.getClassId());
        Partner partner = partnerService.getById(devicelist.getOtherCompanyID());
        devicelist.setDeviceClass(deviceclass);
        devicelist.setPartner(partner);
        setAttr("statusList",this.getEnumRet("StateEnum"));
        setAttr("unitList",this.getDictListByCache("unitList"));
        setAttr("brandList",this.getDictListByCache("brandList"));
        setAttr("devicelist",devicelist);

        render("view.html");
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(devicelistService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }
}
