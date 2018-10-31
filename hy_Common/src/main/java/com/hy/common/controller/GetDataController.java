package com.hy.common.controller;

import com.hy.common.constant.Const;
import com.hy.common.enumresource.EnumBean;
import com.hy.common.enumresource.EnumUtils;
import com.hy.common.model.Dict;
import com.hy.common.utils.EnumMessage;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
public class GetDataController extends Controller implements Const {
    /**
     * @param
     * @author chenyi
     * @Description 通过枚举获取数据列表
     * @date date 2017-7-20
     */
    public void getEnum(){
        String enumName = getPara ("enumName","");
        List<EnumBean> values = EnumUtils.EnumToList(enumName);
        if(values != null){
            renderJson (Ret.create("code",0).set("data",values));
        }else{
            renderJson(Ret.create("code",-1).set("msg","error!"));
        }
    }
    /**
     * @param
     * @author chenyi
     * @Description 通过表码获取数据列表
     * @date date 2017-7-20
     */
    public void getCodeValues() {
        List<Dict> sysCodeList = null;
        String codeName = getPara("codeName");
        if (codeName != null && !"".equals(codeName)) {
            //DictService dictService = DictService.me;
            Dict dict = new Dict();
            sysCodeList= dict.find("select id,name value from sys_dict where code>0 and pcode=? order by code",codeName);
        }
        renderJson(Ret.create("code",0).set("data", sysCodeList));
    }

}
