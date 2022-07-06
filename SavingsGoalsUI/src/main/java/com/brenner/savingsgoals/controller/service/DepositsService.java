package com.brenner.savingsgoals.controller.service;

import com.brenner.savingsgoals.controller.service.model.Deposit;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class DepositsService extends AbstractServiceManager{
    
    ObjectMapper mapper = new ObjectMapper();
    
    public List<Deposit> retrieveDeposits() {
        try {
            String response = super.doGet("/deposits");
            return mapper.readValue(response, new TypeReference<List<Deposit>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Deposit getDeposit(Long depositId) {
        try {
            String response = super.doGet("/deposits/" + depositId);
            return mapper.readValue(response, Deposit.class);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    public Deposit saveNewDeposit(Deposit deposit) {
        try {
            String response = super.doPost("/deposits", mapper.writeValueAsString(deposit));
            return mapper.readValue(response, Deposit.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Deposit updateDeposit(Deposit deposit) {
        try {
            String response = super.doPut("/deposits/" + deposit.getDepositId(), mapper.writeValueAsString(deposit));
            return mapper.readValue(response, Deposit.class);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    public int deleteDeposit(Long depositId) {
        try {
            int status = super.doDelete("/deposits/" + depositId, null);
            return status;
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
