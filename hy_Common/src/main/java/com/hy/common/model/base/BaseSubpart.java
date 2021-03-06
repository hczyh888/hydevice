package com.hy.common.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

import java.util.Date;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSubpart<M extends BaseSubpart<M>> extends Model<M> implements IBean {
	public void setId(Integer id) {
		set("id", id);
	}

	public Integer getId() {
		return getInt("id");
	}

	public void setPartName(String partName) {
		set("partName", partName);
	}

	public String getPartName() {
		return getStr("partName");
	}

	public void setNumUntil(String numUntil) {
		set("numUntil", numUntil);
	}

	public String getNumUntil() {
		return getStr("numUntil");
	}

	public void setPrice(Double price) {
		set("price", price);
	}

	public Double getPrice() {
		return getDouble("price");
	}

	public void setStandard(String standard) {
		set("standard", standard);
	}

	public String getStandard() {
		return getStr("standard");
	}

	public void setBrand(String brand) {
		set("brand", brand);
	}

	public String getBrand() {
		return getStr("brand");
	}

	public void setDealer(String dealer) {
		set("dealer", dealer);
	}

	public String getDealer() {
		return getStr("dealer");
	}

	public void setSource(String source) {
		set("source", source);
	}

	public String getSource() {
		return getStr("source");
	}

	public void setAddTime(Date addTime) {
		set("addTime", addTime);
	}

	public Date getAddTime() {
		return getDate("addTime");
	}

}
