package com.dreamteam.bankingapi.enums;

public enum Status {
    PENDING("pending"),
    CANCELLED("cancelled"),
    COMPLETED("completed"),
    RECURRING("recurring");

    private final String status;

    Status(String value){
        this.status = value;
    }

    public String getValue(){
        return status;
    }
}
