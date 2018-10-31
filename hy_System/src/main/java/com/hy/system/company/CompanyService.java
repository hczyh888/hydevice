package com.hy.system.company;

import com.hy.common.model.Company;
import com.hy.common.model.Deviceclass;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

public class CompanyService {
    public static final CompanyService me = new CompanyService();
    private final Company dao = new Company().dao();


    public boolean update(Company company){
        return company.update();
    }
    public Company getFirst(){
      return  dao.findFirst("select * from sys_company");
    }

}
