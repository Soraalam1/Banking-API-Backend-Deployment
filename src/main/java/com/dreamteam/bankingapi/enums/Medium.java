package com.dreamteam.bankingapi.enums;

public enum Medium {
    BALANCE("Balance"),
    REWARDS("Rewards");

    private final String medium;

    Medium(String medium){
        this.medium = medium;
    }

    public String getValue(){
        return medium;
    }
}
