package com.hy.common.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseProject<M extends BaseProject<M>> extends Model<M> implements IBean {

	public void setId(Integer id) {
		set("id", id);
	}

	public Integer getId() {
		return getInt("id");
	}

	public void setAccountId(Integer accountId) {
		set("accountId", accountId);
	}

	public Integer getAccountId() {
		return getInt("accountId");
	}

	public void setName(String name) {
		set("name", name);
	}

	public String getName() {
		return getStr("name");
	}

	public void setTitle(String title) {
		set("title", title);
	}

	public String getTitle() {
		return getStr("title");
	}

	public void setContent(String content) {
		set("content", content);
	}

	public String getContent() {
		return getStr("content");
	}

	public void setCreateAt(java.util.Date createAt) {
		set("createAt", createAt);
	}

	public java.util.Date getCreateAt() {
		return get("createAt");
	}

	public void setClickCount(Integer clickCount) {
		set("clickCount", clickCount);
	}

	public Integer getClickCount() {
		return getInt("clickCount");
	}

	public void setReport(Integer report) {
		set("report", report);
	}

	public Integer getReport() {
		return getInt("report");
	}

	public void setLikeCount(Integer likeCount) {
		set("likeCount", likeCount);
	}

	public Integer getLikeCount() {
		return getInt("likeCount");
	}

	public void setFavoriteCount(Integer favoriteCount) {
		set("favoriteCount", favoriteCount);
	}

	public Integer getFavoriteCount() {
		return getInt("favoriteCount");
	}

}
