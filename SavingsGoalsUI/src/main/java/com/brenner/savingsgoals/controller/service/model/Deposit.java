package com.brenner.savingsgoals.controller.service.model;

import java.util.Date;

public class Deposit {
    
    private Long depositId;
    
    private Float amount;
    
    private Date date;
    
    private boolean allocated;
    
    public Deposit() {}
    
    public Deposit(Long depositId, Float amount, Date date) {
        this.depositId = depositId;
        this.amount = amount;
        this.date = date;
    }
    
    public Deposit(Float amount, Date date) {
        this.amount = amount;
        this.date = date;
    }
    
    public Long getDepositId() {
        return depositId;
    }
    
    public void setDepositId(Long depositId) {
        this.depositId = depositId;
    }
    
    public Float getAmount() {
        return amount;
    }
    
    public void setAmount(Float amount) {
        this.amount = amount;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public boolean isAllocated() {
        return allocated;
    }
    
    public void setAllocated(boolean allocated) {
        this.allocated = allocated;
    }
}
