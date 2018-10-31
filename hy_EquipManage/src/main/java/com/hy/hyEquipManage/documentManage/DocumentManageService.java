package com.hy.hyEquipManage.documentManage;


import com.hy.common.model.DocumentManage;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class DocumentManageService {

    public static final DocumentManageService me = new DocumentManageService();
    private final DocumentManage dao = new DocumentManage().dao();
    private final String allDocumentManageCacheName = "allDocumentManage";

    /**
     * 获取 documentManage 对象
     */
    public DocumentManage getById(int documentManageId) {
        return dao.findById(documentManageId);
    }
    /**
     * 修改 planTask 对象数据
     */
    public boolean update(DocumentManage documentManage) {
        return documentManage.update();
    }
    /**
     * 根据ID删除数据
     * @param documentManageId
     * @return
     */
    public boolean deleteById(int documentManageId){
        return dao.deleteById(documentManageId);
    }
    /**
     * 根据 UserId 值移除缓存
     */
    public void clearCache(int documentManageId) {
        CacheKit.remove(allDocumentManageCacheName, documentManageId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<DocumentManage> documentManageList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("planTask.select"),Db.getSql("planTask.fromWhere")+" "+sWhere+sOrderBy);
    }

    public boolean Save(){
        return  dao.save();
    }

    public DocumentManage edit(int planTaskId){
        return dao.findById(planTaskId);
    }

}



