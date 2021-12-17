package org.vae.base;

import org.vae.model.UserVO;

public class ResponseVO<F> {
    private int code;
    private String message;
    private F response;


    public ResponseVO(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseVO(int code, String message, F response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    public static ResponseVO fail(Integer code, String msg) {
        return new ResponseVO<>(code, msg);
    }

    public static ResponseVO ok() {
        SystemCode systemCode = SystemCode.OK;
        return new ResponseVO<>(systemCode.getCode(), systemCode.getMessage());
    }

    /**
     * Ok rest response.
     *
     * @param <F>      the type parameter
     * @param response the response
     * @return the rest response
     */
    public static <F> ResponseVO<F> ok(F response) {
        SystemCode systemCode = SystemCode.OK;
        return new ResponseVO<>(systemCode.getCode(), systemCode.getMessage(), response);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public F getResponse() {
        return response;
    }

    public void setResponse(F response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ResponseVO{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", response=" + response +
                '}';
    }


}
