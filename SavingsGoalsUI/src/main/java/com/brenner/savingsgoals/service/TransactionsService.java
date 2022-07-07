package com.brenner.savingsgoals.service;

import com.brenner.savingsgoals.service.model.Transaction;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * RESTful client for the Transactions API
 */
public class TransactionsService extends AbstractServiceManager {
    
    private ObjectMapper mapper = new ObjectMapper();
    
    private static final String TRANSACTIONS_BASE_URL = "/transactions";
    
    /**
     * Calls the REST endpoint supporting retrival of the transactions list.
     *
     * @return The List of transaction objects
     */
    public List<Transaction> retrieveTransactions() {
        try {
            String response = super.doGet(TRANSACTIONS_BASE_URL);
            return mapper.readValue(response, new TypeReference<List<Transaction>>(){});
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    /**
     * Calls the REST endpoint for retrieving a single transaction.
     *
     * @param transactionId The transaction unique identifier
     * @return The Transaction object
     */
    public Transaction getTransaction(Long transactionId) {
        try {
            String response = super.doGet(TRANSACTIONS_BASE_URL + "/" + transactionId);
            return mapper.readValue(response, Transaction.class);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    /**
     * Calls the REST endpoint to save a new transaction.
     *
     * @param transaction The transaction to save
     * @return The transaction returned back from the API
     */
    public Transaction saveNewTransaction(Transaction transaction) {
        try {
            String response = super.doPost(TRANSACTIONS_BASE_URL, mapper.writeValueAsString(transaction));
            return mapper.readValue(response, Transaction.class);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    /**
     * Call to the REST endpoint to update a transaction
     *
     * @param transaction The transaction to update including the unique identifier
     * @return The Transaction returned from the API
     */
    public Transaction updateTransaction(Transaction transaction) {
        try {
            String response = super.doPut(TRANSACTIONS_BASE_URL + "/" + transaction.getTransactionId(), mapper.writeValueAsString(transaction));
            return mapper.readValue(response, Transaction.class);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    /**
     * API call to delete a transaction.
     *
     * @param transactionId The transaction unique identifer.
     * @return The HTTP status. The API does not return a value
     */
    public int deleteTransaction(Long transactionId) {
        try {
            int response = super.doDelete(TRANSACTIONS_BASE_URL + "/" + transactionId, null);
            return response;
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
}
