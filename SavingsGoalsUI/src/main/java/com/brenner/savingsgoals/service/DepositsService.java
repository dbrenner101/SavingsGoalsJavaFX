package com.brenner.savingsgoals.service;

import com.brenner.savingsgoals.service.model.Deposit;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class DepositsService extends AbstractServiceManager{
    
    ObjectMapper mapper = new ObjectMapper();
    
    private static final String DEPOSITS_BASE_URL = "/deposits";
    
    public List<Deposit> retrieveDeposits() {
        try {
            String response = super.doGet(DEPOSITS_BASE_URL);
            return mapper.readValue(response, new TypeReference<List<Deposit>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Deposit getDeposit(Long depositId) {
        try {
            String response = super.doGet(DEPOSITS_BASE_URL + "/" + depositId);
            return mapper.readValue(response, Deposit.class);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    public Deposit saveNewDeposit(Deposit deposit) {
        try {
            String response = super.doPost(DEPOSITS_BASE_URL, mapper.writeValueAsString(deposit));
            return mapper.readValue(response, Deposit.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Deposit updateDeposit(Deposit deposit) {
        try {
            String response = super.doPut(DEPOSITS_BASE_URL + "/" + deposit.getDepositId(), mapper.writeValueAsString(deposit));
            return mapper.readValue(response, Deposit.class);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    public int deleteDeposit(Long depositId) {
        try {
            int status = super.doDelete(DEPOSITS_BASE_URL + "/" + depositId, null);
            return status;
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
