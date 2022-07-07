package com.brenner.savingsgoals.service;

import com.brenner.savingsgoals.service.model.SavingsGoal;
import com.brenner.savingsgoals.service.model.SavingsGoalDepositAllocation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class SavingsGoalsService extends AbstractServiceManager {
    ObjectMapper mapper = new ObjectMapper();
    
    public void allocateDepositToGoals(List<SavingsGoalDepositAllocation> savingsGoalAllocations) {
        try {
            super.doPut("/savingsgoals/allocateDeposit", mapper.writeValueAsString(savingsGoalAllocations));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    public SavingsGoal getDefaultGoal() {
        try {
            String response = super.doGet("/savingsgoals/defaultgoal");
            return mapper.readValue(response, SavingsGoal.class);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
    
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
    
    public SavingsGoal updateSavingsGoal(SavingsGoal savingsGoal) {
        try {
            String response = super.doPut("/savingsgoals/" + savingsGoal.getSavingsGoalId(), mapper.writeValueAsString(savingsGoal));
            return mapper.readValue(response, SavingsGoal.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
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
    
    public void deleteSavingsGoal(Integer savingsGoalId) {
        try {
            int status = super.doDelete("/savingsgoals/" + savingsGoalId, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
