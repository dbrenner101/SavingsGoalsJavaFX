package com.brenner.savingsgoals.service;

import com.brenner.savingsgoals.service.model.SavingsGoal;
import com.brenner.savingsgoals.service.model.SavingsGoalDepositAllocation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * REST client for the Savings Goals API
 */
public class SavingsGoalsService extends AbstractServiceManager {
    
    private ObjectMapper mapper = new ObjectMapper();
    
    /**
     * API call to allocate pieces of a deposit to individual goals.
     *
     * @param savingsGoalAllocations A list of the objects that encapsulate the allocations
     */
    public void allocateDepositToGoals(SavingsGoalDepositAllocation savingsGoalAllocations) {
        try {
            super.doPut("/savingsgoals/allocateDeposit", mapper.writeValueAsString(savingsGoalAllocations));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    /**
     * API call to retrieve the default goal.
     *
     * @return The Savings Goal designated as default
     */
    public SavingsGoal getDefaultGoal() {
        try {
            String response = super.doGet("/savingsgoals/defaultgoal");
            return mapper.readValue(response, SavingsGoal.class);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
    
    /**
     * API call to retrieve the list of savings goals.
     *
     * @return The list of savings goals
     */
    public List<SavingsGoal> retrieveSavingsGoals() {
        
        List<SavingsGoal> allSavingsGoals;
        
        try {
            String response = super.doGet("/savingsgoals");
            allSavingsGoals = new ObjectMapper().readValue(response, new TypeReference<List<SavingsGoal>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        return allSavingsGoals;
    }
    
    /**
     * API call to update a savings goal
     *
     * @param savingsGoal Goal with unique identifier to update
     * @return The SavingsGoal back from the API
     */
    public SavingsGoal updateSavingsGoal(SavingsGoal savingsGoal) {
        try {
            String response = super.doPut("/savingsgoals/" + savingsGoal.getSavingsGoalId(), mapper.writeValueAsString(savingsGoal));
            return mapper.readValue(response, SavingsGoal.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * API call to add a new savings goal
     *
     * @param newSavingsGoal Goal to create/save
     * @return The SavingsGoal back from the API
     */
    public SavingsGoal addNewSavingsGoal(SavingsGoal newSavingsGoal) {
        
        SavingsGoal newGoal;
        
        try {
            String response = super.doPost("/savingsgoals", mapper.writeValueAsString(newSavingsGoal));
            newGoal = mapper.readValue(response, SavingsGoal.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        return newGoal;
    }
    
    /**
     * API call to delete a savings goal.
     *
     * @param savingsGoalId The HTTP status call (API returns no value)
     */
    public void deleteSavingsGoal(Integer savingsGoalId) {
        try {
            int status = super.doDelete("/savingsgoals/" + savingsGoalId, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
