package com.brenner.savingsgoals.service.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Service object for a Transaction that acts in a DTO capacity.
 */
public class Transaction {
    private Long transactionId;
    
    private Date date;
    
    private SavingsGoal fromGoal;
    
    private SavingsGoal toGoal;
    
    private BigDecimal amount;
    
    private String notes;
    
    private boolean applied;
    
    public Transaction() {}
    
    public Transaction(Long transactionId, Date date, SavingsGoal fromGoal, SavingsGoal toGoal, BigDecimal amount, String notes) {
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
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public boolean isApplied() {
        return applied;
    }
    
    public void setApplied(boolean applied) {
        this.applied = applied;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("transactionId=").append(transactionId);
        sb.append(", date=").append(date);
        sb.append(", fromGoal=").append(fromGoal);
        sb.append(", toGoal=").append(toGoal);
        sb.append(", amount=").append(amount);
        sb.append(", notes='").append(notes).append('\'');
        sb.append(", applied=").append(applied);
        sb.append('}');
        return sb.toString();
    }
}
