package com.story.change.android.mvp.bean.base;

/**
 * Created by story on 2017/4/11 0011 下午 8:14.
 * 所有返回数据基类
 */
public class ResponseBase<T> {
    private String rspType;
    private String rspCode;
    private String rspMsg;
    private T rspData;

    public String getRspType() {
        return rspType;
    }

    public void setRspType(String rspType) {
        this.rspType = rspType;
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }

    public T getRspData() {
        return rspData;
    }

    public void setRspData(T rspData) {
        this.rspData = rspData;
    }

}
