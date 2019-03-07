package com.swsm.platform.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Filter implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3867319088299904979L;

    /**
     * 分页当前页码
     */
    private int start = 0;

    /**
     * 分页每页大小
     */
    private int limit = 10;

    /**
     * 排序字段 多个使用","分开
     */
    private String order;

    /**
     * 排序方向
     */
    private String sort;

    /**
     * 记录总页数 （分页方法中赋值，最终返回结果中赋值到pageInfo）
     */
    private long total;

    /**
     * 查询条件<字段名, 字段值>
     */
    private Map<String, Query> queryMap = new HashMap<String, Query>();

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

    public Map<String, Query> getQueryMap() {
        return queryMap;
    }

    public void setQueryMap(Map<String, Query> queryMap) {
        this.queryMap = queryMap;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }


}
