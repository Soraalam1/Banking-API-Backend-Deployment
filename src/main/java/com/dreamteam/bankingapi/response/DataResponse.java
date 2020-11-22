package com.dreamteam.bankingapi.response;

public class DataResponse extends Response{
    private Object data;

    public DataResponse(Integer code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    public DataResponse(Integer code, String message, Object data) {
        this.setCode(code);
        this.setMessage(message);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "SuccessfulResponse{" +
                "data=" + data +
                '}';
    }
}
