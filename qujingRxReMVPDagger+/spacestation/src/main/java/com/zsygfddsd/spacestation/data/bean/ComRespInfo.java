package com.zsygfddsd.spacestation.data.bean;

/**
 * Created by mac on 16/3/3.
 */
public class ComRespInfo<T> {


    /**
     * result : 0
     * resultcode : 2
     * msg : 参数错误
     * data : null
     */

    private Boolean result;
    private int resultcode;
    private String message;
    private T data;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResultcode() {
        return resultcode;
    }

    public void setResultcode(int resultcode) {
        this.resultcode = resultcode;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
