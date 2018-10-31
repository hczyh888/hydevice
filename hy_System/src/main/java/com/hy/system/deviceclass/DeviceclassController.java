package com.hy.system.deviceclass;

import com.hy.common.constant.ConstCache;
import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.Deviceclass;
import com.hy.common.model.Partner;
import com.hy.common.plugin.shiro.ShiroKit;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;

import java.util.List;
import java.util.Map;

public class DeviceclassController extends BaseController {
    DeviceclassService deviceclassService= DeviceclassService.me;
    public void index(){
        setAttr("stateEnum",getEnumRet("StateEnum").toJson());
        render("deviceclass.html");
    }

    /**
     *
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Deviceclass() , paraMap);
        String sWhere=paraStr[0];
        String deviceClassId = getPara("deviceClassId");
        if(StrKit.notBlank(deviceClassId)){
            sWhere = "and ( id='"+deviceClassId+"' or pid='"+deviceClassId+"')";
        }
        Page<Deviceclass> page= deviceclassService.deviceclassList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Deviceclass>  rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        Ret ret = Ret.by("code",0).set("data",rows).set("count",count);
        renderJson(ret);
    }
    public  void add(){
        render("add.html");
    }


    public void getDeviceClass(){
        int pid = getParaToInt(0,0);
        renderJson(deviceclassService.getDeviceClass(pid));
    }

    public void edit(){
        Deviceclass deviceclass = deviceclassService.edit(getParaToInt(0, 0));
        setAttr("deviceclass",deviceclass);
        render("edit.html");
    }
    public void update(){
        Deviceclass deviceclass =getBean(Deviceclass.class,"deviceclass");
        if(deviceclassService.update(deviceclass)){
            Ret ret =Ret.ok("msg","更新成功！").set("code",0);
            ret.set("datas",deviceclass);
            renderJson(ret);
        }else{
            Ret ret=Ret.fail("msg","更新失败！").set("code",-1);
            renderJson(ret);
        }
    }
    public void doadd(){
        Deviceclass deviceclass= getBean(Deviceclass.class,"deviceclass");
        Ret ret=null;
        if(deviceclass.save()){
            ret = Ret.ok("msg","保存成功！").set("code",0);
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);
    }
    public void view(){
        Deviceclass deviceclass = deviceclassService.getById(getParaToInt(0,0));
        setAttr("deviceclass",deviceclass);
        render("view.html");
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(deviceclassService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }
}
