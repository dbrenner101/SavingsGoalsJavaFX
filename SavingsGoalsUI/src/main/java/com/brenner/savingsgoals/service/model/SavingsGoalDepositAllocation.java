package com.brenner.savingsgoals.service.model;

import java.util.List;

/**
 * Service object for a SavingsGoalDepositAllocation that acts in a DTO capacity.
 */
public class SavingsGoalDepositAllocation {
    
    List<Deposit> deposits;
    List<SavingsGoalAllocation> savingsGoalAllocations;
    
    public SavingsGoalDepositAllocation() {}
    
    public SavingsGoalDepositAllocation(List<Deposit> deposits, List<SavingsGoalAllocation> savingsGoalAllocations) {
        this.deposits = deposits;
        this.savingsGoalAllocations = savingsGoalAllocations;
    }
    
    public List<Deposit> getDeposits() {
        return deposits;
    }
    
    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }
    
    public List<SavingsGoalAllocation> getSavingsGoalAllocations() {
        return savingsGoalAllocations;
    }
    
    public void setSavingsGoalAllocations(List<SavingsGoalAllocation> savingsGoalAllocations) {
        this.savingsGoalAllocations = savingsGoalAllocations;
    }
}
