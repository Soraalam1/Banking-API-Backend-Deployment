package com.dreamteam.bankingapi.models;

import javax.persistence.*;

@Entity
public class Transaction {

    @Id
    //Todo: generatedtype to identity
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private String type;
    private String transactionDate;
    private String status;
    private Long payeeAccountId;
    private Long payerAccountId;
    private String medium;
    private Double amount;
    private String description;

    public Transaction() {
    }

    public Transaction(String type,Long payerAccountId, String medium, Double amount, String description) {
        this.type = type;
        this.payerAccountId = payerAccountId;
        this.medium = medium;
        this.amount = amount;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPayeeAccountId() {
        return payeeAccountId;
    }

    public void setPayeeAccountId(Long payee_id) {
        this.payeeAccountId = payee_id;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPayerAccountId() {
        return payerAccountId;
    }

    public void setPayerAccountId(Long payer_id) {
        this.payerAccountId = payer_id;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                ", status='" + status + '\'' +
                ", payee_id=" + payeeAccountId + '\'' +
                ",payer_id=" + payerAccountId + '\'' +
                ", medium='" + medium + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
