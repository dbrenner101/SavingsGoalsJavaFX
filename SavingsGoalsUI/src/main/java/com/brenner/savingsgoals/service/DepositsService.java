package com.brenner.savingsgoals.service;

import com.brenner.savingsgoals.service.model.Deposit;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * REST client for the Deposits API
 */
public class DepositsService extends AbstractServiceManager{
    
    private ObjectMapper mapper = new ObjectMapper();
    
    private static final String DEPOSITS_BASE_URL = "/deposits";
    
    /**
     * API call to retrieve a list of deposits.
     *
     * @return The list of deposit objects
     */
    public List<Deposit> retrieveDeposits() {
        try {
            String response = super.doGet(DEPOSITS_BASE_URL);
            return mapper.readValue(response, new TypeReference<List<Deposit>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * API call to retrieve a specific deposit.
     *
     * @param depositId Deposit unique identifier
     * @return The Deposit
     */
    public Deposit getDeposit(Long depositId) {
        try {
            String response = super.doGet(DEPOSITS_BASE_URL + "/" + depositId);
            return mapper.readValue(response, Deposit.class);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    /**
     * API call to save a new deposit object
     *
     * @param deposit The deposit entity to save
     * @return The Deposit object back from the API
     */
    public Deposit saveNewDeposit(Deposit deposit) {
        try {
            String response = super.doPost(DEPOSITS_BASE_URL, mapper.writeValueAsString(deposit));
            return mapper.readValue(response, Deposit.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * API to update a Deposit object
     *
     * @param deposit Deposit to update including the unique identifier
     * @return The Deposit object back from the API
     */
    public Deposit updateDeposit(Deposit deposit) {
        try {
            String response = super.doPut(DEPOSITS_BASE_URL + "/" + deposit.getDepositId(), mapper.writeValueAsString(deposit));
            return mapper.readValue(response, Deposit.class);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    /**
     * API call to delete a deposit object
     *
     * @param depositId The deposit unique identifier
     * @return The HTTP status code. The API does not return a value
     */
    public int deleteDeposit(Long depositId) {
        try {
            int status = super.doDelete(DEPOSITS_BASE_URL + "/" + depositId, null);
            return status;
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
