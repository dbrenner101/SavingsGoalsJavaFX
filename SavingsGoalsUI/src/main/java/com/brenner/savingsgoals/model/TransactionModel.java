package com.brenner.savingsgoals.model;

import com.brenner.savingsgoals.controller.service.model.Transaction;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class TransactionModel {
    
    Transaction transaction;
    
    private final SimpleObjectProperty<Date> transactionDateProp;
    
    private final SimpleStringProperty fromGoalProp;
    
    private final SimpleStringProperty toGoalProp;
    
    private final SimpleStringProperty amountProp;
    
    private final SimpleStringProperty notesProp;
    
    public TransactionModel(Transaction transaction) {
        this.transaction = transaction;
        this.transactionDateProp = new SimpleObjectProperty<>(transaction.getDate());
        this.fromGoalProp = new SimpleStringProperty(transaction.getFromGoal().getGoalName());
        this.toGoalProp = new SimpleStringProperty(transaction.getToGoal().getGoalName());
        this.amountProp = new SimpleStringProperty(transaction.getAmount().toString());
        this.notesProp = new SimpleStringProperty(transaction.getNotes());
    }
    
    public Transaction getTransaction() {
        return transaction;
    }
    
    public Date getTransactionDateProp() {
        return transactionDateProp.get();
    }
    
    public SimpleObjectProperty<Date> transactionDatePropProperty() {
        return transactionDateProp;
    }
    
    public String getFromGoalProp() {
        return fromGoalProp.get();
    }
    
    public SimpleStringProperty fromGoalPropProperty() {
        return fromGoalProp;
    }
    
    public String getToGoalProp() {
        return toGoalProp.get();
    }
    
    public SimpleStringProperty toGoalPropProperty() {
        return toGoalProp;
    }
    
    public String getAmountProp() {
        return amountProp.get();
    }
    
    public SimpleStringProperty amountPropProperty() {
        return amountProp;
    }
    
    public String getNotesProp() {
        return notesProp.get();
    }
    
    public SimpleStringProperty notesPropProperty() {
        return notesProp;
    }
}
