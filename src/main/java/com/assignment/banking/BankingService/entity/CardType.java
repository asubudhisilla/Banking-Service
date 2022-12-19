package com.assignment.banking.BankingService.entity;

public enum CardType {

    DEBIT("Debit"), CREDIT("Credit");

    private String type;

    CardType(String type) {
        this.type = type;
    }

    public String getCardType() {
        return type;
    }
}
