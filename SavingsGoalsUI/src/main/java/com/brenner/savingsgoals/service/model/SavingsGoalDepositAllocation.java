package com.brenner.savingsgoals.service.model;

import java.math.BigDecimal;

/**
 * Service object for a SavingsGoalDepositAllocation that acts in a DTO capacity.
 */
public class SavingsGoalDepositAllocation {
    Integer savingsGoalId;
    Long depositId;
    BigDecimal allocationAmount;
    
    public SavingsGoalDepositAllocation(Integer savingsGoalId, Long depositId, BigDecimal allocationAmount) {
        this.savingsGoalId = savingsGoalId;
        this.depositId = depositId;
        this.allocationAmount = allocationAmount;
    }
    
    public Integer getSavingsGoalId() {
        return savingsGoalId;
    }
    
    public void setSavingsGoalId(Integer savingsGoalId) {
        this.savingsGoalId = savingsGoalId;
    }
    
    public Long getDepositId() {
        return depositId;
    }
    
    public void setDepositId(Long depositId) {
        this.depositId = depositId;
    }
    
    public BigDecimal getAllocationAmount() {
        return allocationAmount;
    }
    
    public void setAllocationAmount(BigDecimal allocationAmount) {
        this.allocationAmount = allocationAmount;
    }
}
