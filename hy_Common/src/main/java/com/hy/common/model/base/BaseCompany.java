package com.hy.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseCompany<M extends BaseCompany<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setCnName(java.lang.String cnName) {
		set("cnName", cnName);
	}
	
	public java.lang.String getCnName() {
		return getStr("cnName");
	}

	public void setEnName(java.lang.String enName) {
		set("enName", enName);
	}
	
	public java.lang.String getEnName() {
		return getStr("enName");
	}

	public void setAddress(java.lang.String address) {
		set("address", address);
	}
	
	public java.lang.String getAddress() {
		return getStr("address");
	}

	public void setZip(java.lang.String zip) {
		set("zip", zip);
	}
	
	public java.lang.String getZip() {
		return getStr("zip");
	}

	public void setPicture(java.lang.String picture) {
		set("picture", picture);
	}
	
	public java.lang.String getPicture() {
		return getStr("picture");
	}

}
