package com.dreamteam.bankingapi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bill {

    //Todo: Id's are not being generated automatically
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //Todo: Status needs to be of type string, refractor the underscore and make it just status
    private String status;
    private String payee;
    private String nickname;
    private String creationDate;
    private String paymentDate;
    private Integer recurringDate;
    private String upcomingPaymentDate;
    private Double paymentAmount;
    private Long accountId;

    //Todo: since account_id was changed to Long, its getters and setters need to be redone

    public Bill() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getRecurringDate() {
        return recurringDate;
    }

    public void setRecurringDate(Integer recurringDate) {
        this.recurringDate = recurringDate;
    }

    public String getUpcomingPaymentDate() {
        return upcomingPaymentDate;
    }

    public void setUpcomingPaymentDate(String upcomingPaymentDate) {
        this.upcomingPaymentDate = upcomingPaymentDate;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }


    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", status_bill=" + status +
                ", payee='" + payee + '\'' +
                ", nickname='" + nickname + '\'' +
                ", creation_date='" + creationDate + '\'' +
                ", payment_date='" + paymentDate + '\'' +
                ", recurring_date=" + recurringDate +
                ", upcoming_payment_date='" + upcomingPaymentDate + '\'' +
                ", payment_amount=" + paymentAmount +
                ", account_id='" + accountId + '\'' +
                '}';
    }
}
