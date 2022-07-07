package com.brenner.savingsgoals.service.model;

public class SavingsGoalDepositAllocation {
    Integer savingsGoalId;
    Long depositId;
    Float allocationAmount;
    
    public SavingsGoalDepositAllocation(Integer savingsGoalId, Long depositId, Float allocationAmount) {
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
    
    public Float getAllocationAmount() {
        return allocationAmount;
    }
    
    public void setAllocationAmount(Float allocationAmount) {
        this.allocationAmount = allocationAmount;
    }
}
