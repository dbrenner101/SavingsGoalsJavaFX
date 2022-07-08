package com.brenner.savingsgoals.service.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Service object for a Deposit that acts in a DTO capacity.
 */
public class Deposit {
    
    private Long depositId;
    
    private BigDecimal amount;
    
    private Date date;
    
    private boolean allocated;
    
    public Deposit() {}
    
    public Deposit(Long depositId, BigDecimal amount, Date date) {
        this.depositId = depositId;
        this.amount = amount;
        this.date = date;
    }
    
    public Deposit(BigDecimal amount, Date date) {
        this.amount = amount;
        this.date = date;
    }
    
    public Long getDepositId() {
        return depositId;
    }
    
    public void setDepositId(Long depositId) {
        this.depositId = depositId;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
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
