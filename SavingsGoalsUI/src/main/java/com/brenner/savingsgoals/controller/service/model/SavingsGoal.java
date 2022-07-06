package com.brenner.savingsgoals.controller.service.model;

import java.util.Date;

public class SavingsGoal {
    
    private Integer savingsGoalId;
    
    private String goalName;
    
    private Date savingsStartDate;
    
    private Date savingsEndDate;
    
    private Float targetAmount;
    
    private Integer monthsTillPayment;
    
    private Integer weeksTillPayment;
    
    private Integer daysTillPayment;
    
    private Float savingsPerMonth;
    
    private Float savingsPerWeek;
    
    private Float savingsPerDay;
    
    private Float initialBalance;
    
    private Float currentBalance;
    
    private boolean isDefault;
    
    private String notes;
    
    public SavingsGoal() {}
    
    public SavingsGoal(Integer savingsGoalId, String goalName, Date savingsStartDate, Date savingsEndDate,
                       Float targetAmount, Float initialBalance, Float currentBalance, boolean isDefault) {
        this.savingsGoalId = savingsGoalId;
        this.goalName = goalName;
        this.savingsStartDate = savingsStartDate;
        this.savingsEndDate = savingsEndDate;
        this.targetAmount = targetAmount;
        this.initialBalance = initialBalance;
        this.currentBalance = currentBalance;
        this.isDefault = isDefault;
    }
    
    public Integer getSavingsGoalId() {
        return savingsGoalId;
    }
    
    public void setSavingsGoalId(Integer savingsGoalId) {
        this.savingsGoalId = savingsGoalId;
    }
    
    public String getGoalName() {
        return goalName;
    }
    
    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }
    
    public Date getSavingsStartDate() {
        return savingsStartDate;
    }
    
    public void setSavingsStartDate(Date savingsStartDate) {
        this.savingsStartDate = savingsStartDate;
    }
    
    public Date getSavingsEndDate() {
        return savingsEndDate;
    }
    
    public void setSavingsEndDate(Date savingsEndDate) {
        this.savingsEndDate = savingsEndDate;
    }
    
    public Float getTargetAmount() {
        return targetAmount;
    }
    
    public void setTargetAmount(Float targetAmount) {
        this.targetAmount = targetAmount;
    }
    
    public Integer getMonthsTillPayment() {
        return monthsTillPayment;
    }
    
    public void setMonthsTillPayment(Integer monthsTillPayment) {
        this.monthsTillPayment = monthsTillPayment;
    }
    
    public Integer getWeeksTillPayment() {
        return weeksTillPayment;
    }
    
    public void setWeeksTillPayment(Integer weeksTillPayment) {
        this.weeksTillPayment = weeksTillPayment;
    }
    
    public Integer getDaysTillPayment() {
        return daysTillPayment;
    }
    
    public void setDaysTillPayment(Integer daysTillPayment) {
        this.daysTillPayment = daysTillPayment;
    }
    
    public Float getSavingsPerMonth() {
        return savingsPerMonth;
    }
    
    public void setSavingsPerMonth(Float savingsPerMonth) {
        this.savingsPerMonth = savingsPerMonth;
    }
    
    public Float getSavingsPerWeek() {
        return savingsPerWeek;
    }
    
    public void setSavingsPerWeek(Float savingsPerWeek) {
        this.savingsPerWeek = savingsPerWeek;
    }
    
    public Float getSavingsPerDay() {
        return savingsPerDay;
    }
    
    public void setSavingsPerDay(Float savingsPerDay) {
        this.savingsPerDay = savingsPerDay;
    }
    
    public Float getCurrentBalance() {
        return currentBalance;
    }
    
    public void setCurrentBalance(Float currentBalance) {
        this.currentBalance = currentBalance;
    }
    
    public boolean isDefault() {
        return isDefault;
    }
    
    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public Float getInitialBalance() {
        return initialBalance;
    }
    
    public void setInitialBalance(Float initialBalance) {
        this.initialBalance = initialBalance;
    }
    
    @Override
    public String toString() {
        return "SavingsGoal [savingsGoalId=" + savingsGoalId + ", goalName=" + goalName + ", savingsStartDate="
                       + savingsStartDate + ", savingsEndDate=" + savingsEndDate + ", targetAmount=" + targetAmount
                       + ", monthsTillPayment=" + monthsTillPayment + ", weeksTillPayment=" + weeksTillPayment
                       + ", daysTillPayment=" + daysTillPayment + ", savingsPerMonth=" + savingsPerMonth + ", savingsPerWeek="
                       + savingsPerWeek + ", savingsPerDay=" + savingsPerDay + ", initialBalance=" + initialBalance
                       + ", currentBalance=" + currentBalance + ", isDefault=" + isDefault + ", notes=" + notes + "]";
    }
}
