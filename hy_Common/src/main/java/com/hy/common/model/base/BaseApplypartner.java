package com.hy.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;



import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseApplypartner<M extends BaseApplypartner<M>> extends Model<M> implements IBean {
	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setApplyId(java.lang.Integer applyId) {
		set("applyId", applyId);
	}

	public java.lang.Integer getApplyId() {
		return getInt("applyId");
	}

	public void setBrand(java.lang.String brand) {
		set("brand", brand);
	}

	public java.lang.String getBrand() {
		return getStr("brand");
	}

	public void setModels(java.lang.String models) {
		set("models", models);
	}

	public java.lang.String getModels() {
		return getStr("models");
	}

	public void setImportOut(java.lang.String importOut) {
		set("importOut", importOut);
	}

	public java.lang.String getImportOut() {
		return getStr("importOut");
	}

	public void setWay(java.lang.String way) {
		set("way", way);
	}

	public java.lang.String getWay() {
		return getStr("way");
	}

}
