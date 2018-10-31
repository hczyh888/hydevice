package com.hy.hystock.archivesinfo;

import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.Stockpile;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArchivesInfoController extends BaseController {
    ArchivesInfoService archivesInfoService = ArchivesInfoService.me;
    public void index(){
        Page<Stockpile> archivesInfoTreeData =archivesInfoService.archivesinfoList(1, 1,"","id","desc");
        setAttr("archivesInfoTreeData",archivesInfoTreeData);
        render("archivesInfo.html");
    }

    /**
     *获取分页同时获取到数据pageGrid数据格式
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Stockpile(),paraMap);
        String sWhere=paraStr[0];
        Page<Stockpile> page= archivesInfoService.archivesinfoList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Stockpile> rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        PageUtils pageUtils=new PageUtils(rows,count,10,curPage);
        Ret ret = Ret.by("code",0).set("page",pageUtils);
        renderJson(ret);
    }

    /**
     *获取分页同时获取到数据,layui.table格式
     */
    public void layuigetList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Stockpile(),paraMap);
        String sWhere=paraStr[0];
        Page<Stockpile> page= archivesInfoService.archivesinfoList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Stockpile> rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();

        Ret ret = Ret.by("code",0).set("data",rows).set("count",total);
        renderJson(ret);
    }
    /**
     * 入库新增页面
     */
    public void add(){ render("add.html"); }
    /**
     * 保存
     */
    public void doadd(){
        Stockpile archivesinfo= getBean(Stockpile.class,"archivesinfo");
        Ret ret=null;
        if(archivesinfo.save()){
            ret = Ret.ok("msg","保存成功！").set("code",0);
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);
    }
    /*
    * 编辑
    * */
    public void edit(){
        Stockpile archivesinfo= archivesInfoService.edit(getParaToInt(0,0));
        setAttr("archivesinfo",archivesinfo);
        render("edit.html");
    }
    /*
    * 更新
    * */
    public void update(){
        Stockpile archivesinfo =getBean(Stockpile.class,"archivesinfo");
        Ret ret;
        if (archivesInfoService.update(archivesinfo)) {
            ret = Ret.ok("msg", "更新成功").set("code", 0);
        }else{
            ret = Ret.fail("msg", "更新失败！").set("code", -1);
        }
        renderJson(ret);
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(archivesInfoService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }
    /**
     * 查看
     */
    public void view(){
        Stockpile archivesinfo = archivesInfoService.getArchivesInfoAll (getParaToInt(0,0));
        setAttr("archivesinfo",archivesinfo);
        render("view.html");
    }

    public void exportLogToExcel(){
        List<Stockpile> archivesinfo = archivesInfoService.getExportDatas();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("archivesinfo",archivesinfo);
      /*  renderJson(map);*/
        this.exportToExcel("stocktemplate.xlsx","stock档案资料.xlsx",map);
        renderNull();
    }
    public void tabs() {
        int activeIndex = getParaToInt(0);
        switch (activeIndex) {
            case 0: {//设备
                Stockpile archivesinfo = archivesInfoService.getArchivesInfoAll (getParaToInt(1,0));
                setAttr("archivesinfo",archivesinfo);
                render("stockPile.html");
                break;
            }
            case 1:{//零部件
                render("subpart.html");
                break;
            }
            case 2:{//服务商
                render("subservice.html");
                break;
            }
            case 3:{
                render("serviceRecord.html");
                break;
            }
            case 4:{ //维修
                render("stockRepair.html");
                break;
            }
            case 5:{ //安装
                render("stockInstall.html");
                break;
            }
            case 6:{ //保养
                render("stockUpkeep.html");
                break;
            }
            case 7:{
                render("qualityControl.html");
                break;
            }
            case 8:{
                render("adverseEvent.html");
                break;
            }
            case 9:{
                render("transfer.html");
                break;
            }
            case 10:{  //出入库
                render("stockInoutrecord.html");
                break;
            }
            case 11:{
                render("depreciation.html");
                break;
            }
            default:
                renderText("waiting...");
                break;
        }
    }

}
