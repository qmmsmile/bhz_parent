package com.qmm.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 响应对象
 */
public class ResponseMessage {

    private Meta meta;
    private Object data;

    public ResponseMessage success() {
        this.meta = new Meta(ErrorMsgEnum.SUCCESS.getErrorCode(), ErrorMsgEnum.SUCCESS.getErrorMsg());
        this.meta.setErrorMsg();
        return this;
    }

    public ResponseMessage success(Object data) {
        success();
        this.data = data;
        return this;
    }

    /***
     * 默认错误
     * @return
     */
    public ResponseMessage failure() {
        this.meta = new Meta(ErrorMsgEnum.INTERNAL_ERROR.getErrorCode(), ErrorMsgEnum.INTERNAL_ERROR.getErrorMsg());
        return this;
    }

    /**
     * 指定前端显示错误信息
     *
     * @param errorMessage 显示的错误信息
     * @return
     */
    public ResponseMessage failure(String errorMessage) {
        this.meta = new Meta(ErrorMsgEnum.INTERNAL_ERROR.getErrorCode());
        meta.setErrorMsg(errorMessage);
        return this;
    }

    /**
     * 指定错误代码及错误追踪信息
     *
     * @param trackMessage 追踪信息
     * @param errorCode    错误代码
     * @return
     */
    public ResponseMessage failure(String trackMessage, String errorCode) {
        this.meta = new Meta(errorCode, trackMessage);
        this.meta.setErrorMsg();
        return this;
    }

    /**
     * 根据错误枚举类来生成错误信息
     *
     * @param errorMsgEnum 错误枚举类
     * @return
     */
    public ResponseMessage failure(ErrorMsgEnum errorMsgEnum) {
        this.meta = new Meta(errorMsgEnum.getErrorCode(), errorMsgEnum.getErrorMsg());
        this.meta.setErrorMsg();
        return this;
    }

    /**
     * 根据错误枚举类来生成错误信息
     * @param errorMsgEnum 错误枚举类
     * @param displayMsg   显示的错误信息
     * @return
     */
    public ResponseMessage failure(ErrorMsgEnum errorMsgEnum, String displayMsg) {
        this.meta = new Meta(errorMsgEnum.getErrorCode(), errorMsgEnum.getErrorMsg());
        this.meta.setErrorMsg(displayMsg);
        return this;
    }

    public Meta getMeta() {
        return meta;
    }

    public Object getData() {
        return data;
    }

    /**
     * 包装data数据
     * @param name
     * @param object
     * @return
     */
    public ResponseMessage packageData(String name, Object object) {
        HashMap mapData;
        if (data != null && data instanceof HashMap) {
            mapData = (HashMap) getData();
            mapData.put(name, object);
        } else {
            mapData = new HashMap<>();
            mapData.put(name, object);
        }
        return success(mapData);
    }

    private class Meta {

        private int errorCode;
        private String errorMsg;
        private String trackMsg;

        Meta(String errorCodeStr) {
            this.errorCode = Integer.parseInt(errorCodeStr);
        }

        Meta(String errorCode, String trackMsg) {
            int code = Integer.parseInt(errorCode);
            setErrorCode(code);
            setTrackMsg(trackMsg);
        }

        public int getErrorCode() {
            return errorCode;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        void setErrorMsg() {
            if (errorCode != 1000)
                errorMsg = "系统异常,请稍后重试!";
            else
                errorMsg = "正常";
        }

        public String getTrackMsg() {
            return trackMsg;
        }

        void setTrackMsg(String trackMsg) {
            this.trackMsg = trackMsg;
        }
    }

    public static void main(String[] args) {
        List<String> strings = Arrays.asList("1h", "23");
        ResponseMessage response = new ResponseMessage().packageData("1", strings);
        response.packageData("2", strings);

    }

}
