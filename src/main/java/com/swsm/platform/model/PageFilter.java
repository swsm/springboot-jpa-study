package com.swsm.platform.model;

import java.io.Serializable;

/**
 * Wisdytech.cn
 * com.swsm.linkmes.common.orm.jpa.bean.PageFilter
 *
 * @author hailin.zhl
 * @date 2018/11/07
 */
public class PageFilter implements Serializable {


    public static final String ASC = "asc";

    public static final String DESC = "desc";

    /**分页当前页码*/
    private int start=0;

    /**分页每页大小*/
    private int limit=10;

    /**排序字段 多个使用","分开*/
    private String order;

    /**排序方向*/
    private String sort = ASC;

    /**记录总页数 （分页方法中赋值，最终返回结果中赋值到pageInfo）*/
    private long total;

    public PageFilter() {

    }

    public PageFilter(int start,int limit) {
        this.limit = limit;
        this.start = start;
    }

    public static PageFilter of(int start, int limit) {
        return new PageFilter(start,limit);
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
