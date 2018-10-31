package com.hy.system.partner;


import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.Partner;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

public class PartnerController extends BaseController {
    PartnerService partnerService= PartnerService.me;
    public void index(){
        setAttr("brandList",getDictListByCache(brandList).toJson()); //获取品牌列表传前端页面
        setAttr("partnerType",getDictListByCache(partnerType).toJson()); //获取合作伙伴分类列表传前端页面
        setAttr("countryList",getDictListByCache(countryList).toJson()); //获取品牌列表传前端页面
        render("partner.html");
    }

    /**
     *
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Partner() , paraMap);
        String sWhere=paraStr[0];
        Page<Partner> page= partnerService.partnerList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Partner>  rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        Ret ret = Ret.by("code",0).set("count",count).set("data",rows);
        renderJson(ret);
    }
    public  void add(){
        render("add.html");
    }
    public void edit(){
        Partner partner = partnerService.edit(getParaToInt(0, 0));
        setAttr("partner",partner);
        render("edit.html");
    }
    public void update(){
        Partner partner =getBean(Partner.class,"partner");
        if(partnerService.update(partner)){
            Ret ret =Ret.ok("msg","更新成功！").set("code",0);
            ret.set("datas",partner);
            renderJson(ret);
        }else{
            Ret ret=Ret.fail("msg","更新失败！").set("code",-1);
            renderJson(ret);
        }
    }
    public void doadd(){
        Partner partner= getBean(Partner.class,"partner");
        Ret ret=null;
        if(partner.save()){
            ret = Ret.ok("msg","保存成功！").set("code",0);
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);
    }
    public void view(){
        Partner partner = partnerService.getById(getParaToInt(0,0));
        setAttr("brandList",this.getDictListByCache("brandList"));
        setAttr("countryList",this.getDictListByCache("countryList"));
        setAttr("partnerType",this.getDictListByCache("partnerType"));
        setAttr("partner",partner);
        render("view.html");
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(partnerService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }
}
