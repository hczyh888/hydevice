package com.hy.common.tools.grid;

import java.util.List;

/**
 * 拼接layui.table
 */
public class LayGrid<E> {
    private Integer code;
    /** 返回结果集  **/
    private List<E> data;

    /** 返回的总页数  **/
    private long total;

    /** 返回的当前页数  **/
    private long page;

    /** 一共有多少数据  **/
    private long count;

    public LayGrid() {

    }

    public LayGrid(List<E> data, long total, long page, long count) {
        super();
        this.data = data;
        this.total = total;
        this.page = page;
        this.count = count;
        this.code = 0;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
