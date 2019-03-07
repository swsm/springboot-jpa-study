package com.swsm.platform.action;

import java.io.Serializable;

/**
 * @author tinel
 * @Title: UniqueResultResponse
 * @ProjectName mes-wms
 * @Description: http返回数据封装，单个数据
 * @date 2018/11/715:34
 */
public class  UniqueResultResponse<T> implements Serializable {

    private static final long serialVersionUID = -4911822707263668901L;


    /**
     * 结果
     */
    private boolean result;

    /**
     * 消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    public UniqueResultResponse(){}

    public UniqueResultResponse(T data){
            this(data,"");
    }

    public UniqueResultResponse(T data,String message){
        this(data,message,true);
    }

    public UniqueResultResponse(T data,String message,boolean result){
        this.data=data;
        this.message=message;
        this.result=result;
    }


    public boolean getResult() {
        return this.result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T t) {
        this.data = data;
    }
}
