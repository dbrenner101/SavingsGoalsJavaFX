package com.brenner.savingsgoals.model;

import com.brenner.savingsgoals.service.model.SavingsGoal;
import com.brenner.savingsgoals.util.CommonUtils;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

/**
 * UI object for a SavingsGoal. Includes a corresponding reference to the SavingsGoal dto.
 */
public class SavingsGoalModel implements Comparable<SavingsGoalModel> {
    
    private final SimpleStringProperty goalNameProp;
    
    private final SimpleObjectProperty<Date> savingsStartDateProp;
    
    private final SimpleObjectProperty<Date> savingsEndDateProp;
    
    private final SimpleStringProperty targetAmountProp;
    
    private final SimpleIntegerProperty monthsTillPaymentProp;
    
    private final SimpleIntegerProperty weeksTillPaymentProp;
    
    private final SimpleIntegerProperty daysTillPaymentProp;
    
    private final SimpleStringProperty savingsPerWeekProp;
    
    private final SimpleStringProperty initialBalanceProp;
    
    private final SimpleStringProperty currentBalanceProp;
    
    private String allocatedAmount;
    
    private final SavingsGoal savingsGoal;
    
    public SavingsGoalModel(SavingsGoal savingsGoal) {
        this.savingsGoal = savingsGoal;
        this.goalNameProp = new SimpleStringProperty(savingsGoal.getGoalName());
        this.savingsStartDateProp = new SimpleObjectProperty<>(savingsGoal.getSavingsStartDate());
        this.savingsEndDateProp = new SimpleObjectProperty<>(savingsGoal.getSavingsEndDate());
        this.targetAmountProp = new SimpleStringProperty(CommonUtils.formatAsCurrency(savingsGoal.getTargetAmount()));
        this.monthsTillPaymentProp = (savingsGoal.getMonthsTillPayment() != null) ? new SimpleIntegerProperty(savingsGoal.getMonthsTillPayment()) : null;
        this.weeksTillPaymentProp = (savingsGoal.getWeeksTillPayment() != null) ? new SimpleIntegerProperty(savingsGoal.getWeeksTillPayment()) : null;
        this.daysTillPaymentProp = (savingsGoal.getDaysTillPayment() != null) ? new SimpleIntegerProperty(savingsGoal.getDaysTillPayment()) : null;
        this.savingsPerWeekProp = (savingsGoal.getSavingsPerWeek() != null) ? new SimpleStringProperty(CommonUtils.formatAsCurrency(savingsGoal.getSavingsPerWeek())) : null;
        this.initialBalanceProp = (savingsGoal.getInitialBalance() != null) ? new SimpleStringProperty(CommonUtils.formatAsCurrency(savingsGoal.getInitialBalance())) : null;
        this.currentBalanceProp = (savingsGoal.getCurrentBalance() != null) ? new SimpleStringProperty(CommonUtils.formatAsCurrency(savingsGoal.getCurrentBalance())) : null;
    }
    
    public Integer getSavingsGoalId() {
        return this.savingsGoal.getSavingsGoalId();
    }
    
    public String getGoalNameProp() {
        return goalNameProp.get();
    }
    
    public SimpleStringProperty goalNamePropProperty() {
        return goalNameProp;
    }
    
    public Object getSavingsStartDateProp() {
        return savingsStartDateProp.get();
    }
    
    public SimpleObjectProperty savingsStartDatePropProperty() {
        return savingsStartDateProp;
    }
    
    public Object getSavingsEndDateProp() {
        return savingsEndDateProp.get();
    }
    
    public SimpleObjectProperty savingsEndDatePropProperty() {
        return savingsEndDateProp;
    }
    
    public String getTargetAmountProp() {
        return targetAmountProp.get();
    }
    
    public SimpleStringProperty targetAmountPropProperty() {
        return targetAmountProp;
    }
    
    public int getMonthsTillPaymentProp() {
        return monthsTillPaymentProp.get();
    }
    
    public SimpleIntegerProperty monthsTillPaymentPropProperty() {
        return monthsTillPaymentProp;
    }
    
    public int getWeeksTillPaymentProp() {
        return weeksTillPaymentProp.get();
    }
    
    public SimpleIntegerProperty weeksTillPaymentPropProperty() {
        return weeksTillPaymentProp;
    }
    
    public int getDaysTillPaymentProp() {
        return daysTillPaymentProp.get();
    }
    
    public SimpleIntegerProperty daysTillPaymentPropProperty() {
        return daysTillPaymentProp;
    }
    
    public String getSavingsPerWeekProp() {
        return savingsPerWeekProp.get();
    }
    
    public SimpleStringProperty savingsPerWeekPropProperty() {
        return savingsPerWeekProp;
    }
    
    public String getInitialBalanceProp() {
        return initialBalanceProp.get();
    }
    
    public SimpleStringProperty initialBalancePropProperty() {
        return initialBalanceProp;
    }
    
    public String getCurrentBalanceProp() {
        return currentBalanceProp.get();
    }
    
    public SimpleStringProperty currentBalancePropProperty() {
        return currentBalanceProp;
    }
    
    public boolean isDefault() {
        return this.savingsGoal.isDefault();
    }
    
    public String getNotes() {
        return this.savingsGoal.getNotes();
    }
    
    public SavingsGoal getSavingsGoal() {
        return savingsGoal;
    }
    
    public String getAllocatedAmount() {
        return allocatedAmount;
    }
    
    public void setAllocatedAmount(String allocatedAmount) {
        this.allocatedAmount = allocatedAmount;
    }
    
    @Override
    public int compareTo(SavingsGoalModel o) {
        return this.savingsGoal.getGoalName().compareTo(o.getSavingsGoal().getGoalName());
    }
}
