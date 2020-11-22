package com.dreamteam.bankingapi.response;

public class PlainResponse extends Response {
    public PlainResponse(Integer code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }
}
