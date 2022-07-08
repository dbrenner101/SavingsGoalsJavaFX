package com.brenner.savingsgoals.service.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Service object for a SavingsGoal that acts in a DTO capacity.
 */
public class SavingsGoal {
    
    private Integer savingsGoalId;
    
    private String goalName;
    
    private Date savingsStartDate;
    
    private Date savingsEndDate;
    
    private BigDecimal targetAmount;
    
    private Integer monthsTillPayment;
    
    private Integer weeksTillPayment;
    
    private Integer daysTillPayment;
    
    private BigDecimal savingsPerMonth;
    
    private BigDecimal savingsPerWeek;
    
    private BigDecimal savingsPerDay;
    
    private BigDecimal initialBalance;
    
    private BigDecimal currentBalance;
    
    private boolean isDefault;
    
    private String notes;
    
    public SavingsGoal() {}
    
    public SavingsGoal(Integer savingsGoalId, String goalName, Date savingsStartDate, Date savingsEndDate,
                       BigDecimal targetAmount, BigDecimal initialBalance, BigDecimal currentBalance, boolean isDefault) {
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
    
    public BigDecimal getTargetAmount() {
        return targetAmount;
    }
    
    public void setTargetAmount(BigDecimal targetAmount) {
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
    
    public BigDecimal getSavingsPerMonth() {
        return savingsPerMonth;
    }
    
    public void setSavingsPerMonth(BigDecimal savingsPerMonth) {
        this.savingsPerMonth = savingsPerMonth;
    }
    
    public BigDecimal getSavingsPerWeek() {
        return savingsPerWeek;
    }
    
    public void setSavingsPerWeek(BigDecimal savingsPerWeek) {
        this.savingsPerWeek = savingsPerWeek;
    }
    
    public BigDecimal getSavingsPerDay() {
        return savingsPerDay;
    }
    
    public void setSavingsPerDay(BigDecimal savingsPerDay) {
        this.savingsPerDay = savingsPerDay;
    }
    
    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }
    
    public void setCurrentBalance(BigDecimal currentBalance) {
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
    
    public BigDecimal getInitialBalance() {
        return initialBalance;
    }
    
    public void setInitialBalance(BigDecimal initialBalance) {
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
