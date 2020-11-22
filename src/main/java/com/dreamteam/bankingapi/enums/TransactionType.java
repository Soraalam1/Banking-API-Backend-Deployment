package com.dreamteam.bankingapi.enums;

public enum TransactionType {
    P2P("P2P"),
    DEPOSIT("Deposit"),
    WITHDRAWAL("Withdrawal");

    private final String type;

    TransactionType(String type){
        this.type = type;
    }

    public String getValue(){
        return type;
    }

}
