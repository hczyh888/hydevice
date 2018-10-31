package com.hy.common.model;

import com.hy.common.model.base.BaseEnterstockDetail;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class EnterstockDetail extends BaseEnterstockDetail<EnterstockDetail> {
	private java.lang.Integer id; //
	private java.lang.Integer classId ; //
	private java.lang.Integer deviceId ; //

	public Integer getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(Integer deviceSn) {
		this.deviceSn = deviceSn;
	}

	private java.lang.Integer deviceSn; //
	private java.lang.Integer enternum ; //
	private java.lang.Double price ; //
	private java.util.Date productdate ; //
	private java.util.Date beginTime ; //
	private java.lang.Integer warrantyPeriod ; //
	private java.lang.Integer enterId ; //
	private java.lang.Integer wareHouseId ; //
	private java.lang.String enterType ; //
	private java.lang.Integer companyId ; //

}