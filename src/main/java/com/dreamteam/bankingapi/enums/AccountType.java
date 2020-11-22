package com.dreamteam.bankingapi.enums;

public enum AccountType {
    SAVINGS("savings"),
    CHECKING("checking"),
    CREDIT("credit");

    private final String type;

    AccountType(String value){
        this.type = value;
    }

    public String getValue(){
        return type;
    }

}
