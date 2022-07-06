package com.brenner.savingsgoals.service;

import com.brenner.savingsgoals.service.model.Transaction;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class TransactionsService extends AbstractServiceManager {
    
    ObjectMapper mapper = new ObjectMapper();
    
    private static final String TRANSACTIONS_BASE_URL = "/transactions";
    
    public List<Transaction> retrieveTransactions() {
        try {
            String response = super.doGet(TRANSACTIONS_BASE_URL);
            return mapper.readValue(response, new TypeReference<List<Transaction>>(){});
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    public Transaction getTransaction(Long transactionId) {
        try {
            String response = super.doGet(TRANSACTIONS_BASE_URL + "/" + transactionId);
            return mapper.readValue(response, Transaction.class);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    public Transaction saveNewTransaction(Transaction transaction) {
        try {
            String response = super.doPost(TRANSACTIONS_BASE_URL, mapper.writeValueAsString(transaction));
            return mapper.readValue(response, Transaction.class);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    public Transaction updateTransaction(Transaction transaction) {
        try {
            String response = super.doPut(TRANSACTIONS_BASE_URL + "/" + transaction.getTransactionId(), mapper.writeValueAsString(transaction));
            return mapper.readValue(response, Transaction.class);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    public int deleteTransaction(Long transactionId) {
        try {
            int response = super.doDelete(TRANSACTIONS_BASE_URL + "/" + transactionId, null);
            return response;
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
}
