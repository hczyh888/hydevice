package com.hy.system.company;


import com.hy.common.kit.DateTimeKit;
import com.hy.common.model.Company;
import com.hy.common.tools.Func;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.render.Render;
import com.jfinal.upload.UploadFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompanyController extends Controller {
    CompanyService companyService = CompanyService.me;

    public void index() {
        setAttr("company",companyService.getFirst());
        render("company.html");
    }

    public void upload() {
        UploadFile file = getFile();
        if (!Func.isEmpty(file)){
            Ret ret = Ret.ok("msg","上传成功");
            ret.set("filePath","/upload/"+file.getFileName());
            renderJson(ret);
        }else{
            renderJson(Ret.fail ("msg","上传失败"));

        }


    }
    public void doadd(){
        Company company=getBean(Company.class,"company");
        Ret ret=null;
        if(StrKit.notNull(company.getId())) {
            if(company.update()){
                ret = Ret.ok("msg", "保存成功！").set("code", 0);
            }else{
                ret = Ret.fail("msg", "保存失败！").set("code", -1);
            }
        }else {
            if (company.save()) {
                ret = Ret.ok("msg", "保存成功！").set("code", 0);
            } else {
                ret = Ret.fail("msg", "保存失败！").set("code", -1);
            }
        }
        renderJson(ret);
    }
}
