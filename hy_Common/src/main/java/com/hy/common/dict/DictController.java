package com.hy.common.dict;


import com.hy.common.constant.ConstCache;
import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.Dept;
import com.hy.common.model.Dict;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;
import java.util.Map;

public class DictController extends BaseController {
    DictService dictService = DictService.me;
    public void index(){
        render("dict.html");
    }

    /**
     *
     */
    public void getList(){

        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Dict() , paraMap);
        String sWhere=paraStr[0];
        String dictId = getPara("dictId");
        if(StrKit.notBlank(dictId)){
            sWhere = "and ( id="+dictId+" or pid="+dictId+")";
        }
        Page<Dict> page= dictService.dictList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Dict>  rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        Ret ret = Ret.by("code",0).set("count",count).set("data",rows);
        renderJson(ret);
    }
    public void add(){
        render("add.html");
    }
    public void edit(){
        Dict dict= dictService.edit(getParaToInt(0,0));
        setAttr("dict",dict);
        render("edit.html");
    }
    public void update(){
        Dict dict =getBean(Dict.class,"dict");
        if(dictService.update(dict)){
            CacheKit.removeAll(DICT_CACHE);
            Ret ret =Ret.ok("msg","更新成功").set("code",0).set("datas",dict);
            renderJson(ret);
        }else{
            Ret ret=Ret.fail("msg","更新失败！").set("code",0);
            renderJson(ret);
        }
    }
    public void doadd(){
        Dict dict= getBean(Dict.class,"dict");
        Ret ret=null;
        if(dict.save()){
            CacheKit.remove(DICT_CACHE,"dict_tree_all");
            ret = Ret.ok("msg","保存成功！").set("code",0);
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);
    }
    public void view(){
        Dict dict = dictService.getById(getParaToInt(0,0));
        setAttr("dict",dict);
        render("view.html");
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(dictService.deleteById(id)){
            CacheKit.removeAll(ConstCache.DICT_CACHE);
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }

}
