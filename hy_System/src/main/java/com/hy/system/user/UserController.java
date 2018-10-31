package com.hy.system.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastjsonSockJsMessageCodec;
import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.FormTokenValidator;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.interceptor.TokenInterceptor;
import com.hy.common.kit.DateTimeKit;
import com.hy.common.model.User;
import com.hy.common.plugin.shiro.ShiroKit;
import com.hy.common.tools.Func;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class UserController extends BaseController{
    UserService userService = UserService.me;
    public void index(){
       // Page <User> userTreeData = userService.userList(1, 1,"","createtime"," desc ");
       // setAttr("userTreeData",userTreeData);
        setAttr("stateEnum",getEnumRet("StateEnum").toJson());
        render("user.html");
    }

    /**
     *
     */
    public void getList(){

        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new User() , paraMap);
        String sWhere=paraStr[0];
        Page<User> page= userService.userList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<User>  rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        Ret ret = Ret.by("code",0).set("count",count).set("data",rows);
        renderJson(ret);
    }

   @Before(TokenInterceptor.class)
    public void add(){
        render("add.html");
    }
    public void edit(){
        User user= userService.edit(getParaToInt(0,0));
        setAttr("user",user);
        render("edit.html");
    }
    public void update(){
        User user =getBean(User.class,"user");
        Ret ret;
        if(userService.update(user)){
             ret =Ret.ok("msg","更新成功").set("code",0);
        }else{
            ret=Ret.fail("msg","更新失败！").set("code",-1);
        }
        renderJson(ret);
    }

    /**
     * 保存
     */
    @Before({UserValidator.class})
    public void doadd(){
        User user= getBean(User.class,"user");
        String pwd = user.getPassword();
        String salt = ShiroKit.getRandomSalt(5);
        String pwdMD5 = ShiroKit.md5(pwd,salt);
        user.setSalt(salt);
        user.setPassword(pwdMD5);
        user.setStatus(1);
        user.setCreatetime(DateTimeKit.date());
        Ret ret=null;
        if(user.save()){
            CacheKit.removeAll(DEPT_CACHE);
            CacheKit.removeAll(DICT_CACHE);
            CacheKit.removeAll(USER_CACHE);
            ret = Ret.ok("msg","保存成功！").set("code",0);
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);
    }
    public void view(){
        User user = userService.getById(getParaToInt(0,0));
        setAttr("user",user);
        render("view.html");
    }

    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(userService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }
    /**
     * 判断account是否已存在
     * post方式的提交
     */
    public void accountExit(){
        String account = getPara("account");
        if(userService.isExitByAccount(account)){
            renderJson(Ret.ok("msg","该账号已存在").set("code",0));
        }else{
            renderJson(Ret.fail("msg","该账号不存在").set("code",-1));
        }
    }

    /**
     * 得到当前用户信息
     */
    public void info(){
        renderJson(Ret.by("code",0).set("user",ShiroKit.getUser()));
    }


}
