package com.hy.common.model;

import com.hy.common.model.base.BaseMenu;

import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Menu extends BaseMenu<Menu> {
	
	    private Integer id ; //
	    private String code ; //
	    private String pcode ; //
	    private String alias ; //
	    private String name ; //
	    private String icon ; //
	    private String url ; //
	    private Integer num ; //
	    private Integer levels ; //
	    private Integer menuType ; //
	    private String path ; //
	    private String tips ; //
	    private Integer status ; //
	    private String isopen ; //
	    private String istemplate ; //
	    private Integer version ; //
		private List<?> sublist;

	public List<?> getSublist() {
		return sublist;
	}

	public void setSublist(List<?> sublist) {
		this.sublist = sublist;
	}
}