package com.brenner.savingsgoals.controller.service.model;

import java.util.Date;

public class Transaction {
    private Long transactionId;
    
    private Date date;
    
    private SavingsGoal fromGoal;
    
    private SavingsGoal toGoal;
    
    private Float amount;
    
    private String notes;
    
    public Transaction() {}
    
    public Transaction(Long transactionId, Date date, SavingsGoal fromGoal, SavingsGoal toGoal, Float amount, String notes) {
        this.transactionId = transactionId;
        this.date = date;
        this.fromGoal = fromGoal;
        this.toGoal = toGoal;
        this.amount = amount;
        this.notes = notes;
    }
    
    public Long getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public SavingsGoal getFromGoal() {
        return fromGoal;
    }
    
    public void setFromGoal(SavingsGoal fromGoal) {
        this.fromGoal = fromGoal;
    }
    
    public SavingsGoal getToGoal() {
        return toGoal;
    }
    
    public void setToGoal(SavingsGoal toGoal) {
        this.toGoal = toGoal;
    }
    
    public Float getAmount() {
        return amount;
    }
    
    public void setAmount(Float amount) {
        this.amount = amount;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
