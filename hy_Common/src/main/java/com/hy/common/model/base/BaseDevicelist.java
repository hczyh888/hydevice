package com.hy.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseDevicelist<M extends BaseDevicelist<M>> extends Model<M> implements IBean {

	public void setId(Integer id) {
		set("id", id);
	}
	
	public Integer getId() {
		return getInt("id");
	}

	public void setCnName(java.lang.String cnName) {
		set("cnName", cnName);
	}
	
	public java.lang.String getCnName() {
		return getStr("cnName");
	}

	public void setSimpleName(java.lang.String simpleName) {
		set("simpleName", simpleName);
	}
	
	public java.lang.String getSimpleName() {
		return getStr("simpleName");
	}

	public void setEnName(java.lang.String enName) {
		set("enName", enName);
	}
	
	public java.lang.String getEnName() {
		return getStr("enName");
	}

	public void setClassId(Integer classId) {
		set("classId", classId);
	}
	
	public Integer getClassId() {
		return getInt("classId");
	}

	public void setBrand(java.lang.String brand) {
		set("brand", brand);
	}
	
	public java.lang.String getBrand() {
		return getStr("brand");
	}

	public void setType(java.lang.String type) {
		set("type", type);
	}
	
	public java.lang.String getType() {
		return getStr("type");
	}

	public void setUnit(java.lang.String unit) {
		set("unit", unit);
	}
	
	public java.lang.String getUnit() {
		return getStr("unit");
	}

	public void setOtherCompanyID(java.lang.Integer otherCompanyID) {
		set("otherCompanyID", otherCompanyID);
	}
	
	public java.lang.Integer getOtherCompanyID() {
		return getInt("otherCompanyID");
	}

	public void setCreateTime(java.util.Date createTime) {
		set("createTime", createTime);
	}
	
	public java.util.Date getCreateTime() {
		return get("createTime");
	}

	public void setStatus(java.lang.String status) {
		set("status", status);
	}
	
	public java.lang.String getStatus() {
		return getStr("status");
	}

	public void setDeleted(java.lang.Integer deleted) {
		set("deleted", deleted);
	}

	public java.lang.Integer getDeleted() {
		return getInt("deleted");
	}

}
