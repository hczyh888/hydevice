package com.hy.common.model;

import com.hy.common.model.base.BaseStockpile;
import com.jfinal.plugin.activerecord.Record;

import java.util.Date;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Stockpile extends BaseStockpile<Stockpile> {
	
	    private Integer id ; //
	    private Integer storehouseId ; //
	    private Integer deviceId ; //
	    private Date firstEnterDate ; //
	    private Date lastLeaveDate ; //
	    private Integer number ; //
	    private Double price ; //
	    private String code ; //
	    private String fileNumber ; //
	    private Integer classId ; //
		private String brand ; //
		private Integer enterId ; //
		private Integer warehouseId ; //
		private Integer status ; //
		private Integer scrap ; //
		private Integer salvage ; //
		private Integer deptId ; //
		private Integer userId ; //

	private Record stockpileSubinfor;
	//申请表的子表 stockpileSubinfor
	private Record deviceclass;
	//申请表的子表 deviceclass
	private Record devicelist;
	//申请表的子表 devicelist
	private Record warehouse;
	//申请表的子表 warehouse
	private Record enterstock;
	//申请表的子表 enterstock

	public Record getStockpileSubinfor() {
		return stockpileSubinfor;
	}

	public void setStockpileSubinfor(Record stockpileSubinfor) {
		this.stockpileSubinfor = stockpileSubinfor;
	}


	public Record getDeviceclass() {
		return deviceclass;
	}

	public void setDeviceclass(Record deviceclass) {
		this.deviceclass = deviceclass;
	}

	public Record getDevicelist() {
		return devicelist;
	}

	public void setDevicelist(Record devicelist) {
		this.devicelist = devicelist;
	}

	public Record getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Record warehouse) {
		this.warehouse = warehouse;
	}

	public Record getEnterstock() {
		return enterstock;
	}

	public void setEnterstock(Record enterstock) {
		this.enterstock = enterstock;
	}
}
