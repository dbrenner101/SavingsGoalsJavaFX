package com.brenner.savingsgoals.model;

import com.brenner.savingsgoals.controller.service.model.Deposit;
import com.brenner.savingsgoals.util.CommonUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class DepositModel {
    
    Deposit deposit;
    
    private final SimpleObjectProperty<Date> depositDateProp;
    
    private final SimpleStringProperty depositAmountProp;
    
    public DepositModel(Deposit deposit) {
        this.deposit = deposit;
        this.depositDateProp = new SimpleObjectProperty<>(deposit.getDate());
        this.depositAmountProp = new SimpleStringProperty(CommonUtils.formatAsCurrency(deposit.getAmount()));
    }
    
    public Deposit getDeposit() {
        return deposit;
    }
    
    public void setDeposit(Deposit deposit) {
        this.deposit = deposit;
    }
    
    public Date getDepositDateProp() {
        return depositDateProp.get();
    }
    
    public SimpleObjectProperty<Date> depositDatePropProperty() {
        return depositDateProp;
    }
    
    public void setDepositDateProp(Date depositDateProp) {
        this.depositDateProp.set(depositDateProp);
    }
    
    public String getDepositAmountProp() {
        return depositAmountProp.get();
    }
    
    public SimpleStringProperty depositAmountPropProperty() {
        return depositAmountProp;
    }
    
    public void setDepositAmountProp(String depositAmountProp) {
        this.depositAmountProp.set(depositAmountProp);
    }
}
