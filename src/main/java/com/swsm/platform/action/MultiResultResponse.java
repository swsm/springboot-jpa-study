package com.swsm.platform.action;

import java.io.Serializable;
import java.util.List;

/**
 * @author tinel
 * @Title: MultiResultResponse
 * @ProjectName mes-wms
 * @Description: http数据封装，多个对象集合
 * @date 2018/11/715:35
 */
public class MultiResultResponse<T> implements Serializable{

    private static final long serialVersionUID = 2785555006271262296L;

    /**
     * 返回结果
     */
    private boolean result;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 总数
     */
    private long total;

    /**
     * 返回数据列表
     */
    private List<T> rows;


    public MultiResultResponse(){}
    public MultiResultResponse(List<T> rows){
        this(rows,rows.size(),"",true);
    }

    public MultiResultResponse(List<T> rows,long total){
        this(rows,total,"",true);
    }

    public MultiResultResponse(List<T> rows,long total,String message){
        this(rows,total,message,true);
    }

    public MultiResultResponse(List<T> rows,long total,String message,boolean result){
        this.total=total;
        this.rows=rows;
        this.message=message;
        this.result=result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }



}
