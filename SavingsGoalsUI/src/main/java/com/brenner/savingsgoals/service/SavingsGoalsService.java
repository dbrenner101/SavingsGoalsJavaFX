package com.brenner.savingsgoals.service;

import com.brenner.savingsgoals.service.model.SavingsGoal;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class SavingsGoalsService extends AbstractServiceManager {
    
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
            ObjectMapper mapper = new ObjectMapper();
            String response = super.doPut("/savingsgoals/" + savingsGoal.getSavingsGoalId(), mapper.writeValueAsString(savingsGoal));
            return mapper.readValue(response, SavingsGoal.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public SavingsGoal addNewSavingsGoal(SavingsGoal newSavingsGoal) {
        
        SavingsGoal newGoal;
        
        try {
            ObjectMapper mapper = new ObjectMapper();
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
