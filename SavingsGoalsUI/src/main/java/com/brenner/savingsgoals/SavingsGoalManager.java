package com.brenner.savingsgoals;

import com.brenner.savingsgoals.model.DepositModel;
import com.brenner.savingsgoals.model.SavingsGoalModel;
import com.brenner.savingsgoals.model.TransactionModel;
import com.brenner.savingsgoals.service.DepositsService;
import com.brenner.savingsgoals.service.SavingsGoalsService;
import com.brenner.savingsgoals.service.TransactionsService;
import com.brenner.savingsgoals.service.model.Deposit;
import com.brenner.savingsgoals.service.model.SavingsGoal;
import com.brenner.savingsgoals.service.model.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class manages the intersection between the view and API calls.
 */
public class SavingsGoalManager {
    
    SavingsGoalsService savingsGoalsService = new SavingsGoalsService();
    DepositsService depositsService = new DepositsService();
    TransactionsService transactionsService = new TransactionsService();
    
    
    private ObservableList<SavingsGoalModel> savingsGoalsList = FXCollections.observableArrayList();
    
    private ObservableList<DepositModel> depositsList = FXCollections.observableArrayList();
    
    private ObservableList<TransactionModel> transactionsList = FXCollections.observableArrayList();
    
    private Integer selectedSavingsGoalIndex = null;
    
    private void retrieveTransactions() {
        Task<List<Transaction>> retrieveTransactions = new Task<List<Transaction>>() {
            @Override
            protected List<Transaction> call() throws Exception {
                return transactionsService.retrieveTransactions();
            }
        };
        retrieveTransactions.run();
        
        retrieveTransactions.setOnSucceeded(e -> {
            List<Transaction> transactions = retrieveTransactions.getValue();
            if (transactions != null) {
                List<TransactionModel> modelList = new ArrayList<>(transactions.size());
                for (Transaction transaction : transactions) {
                    TransactionModel model = new TransactionModel(transaction);
                    modelList.add(model);
                }
                this.transactionsList.addAll(modelList);
            }
        });
    }
    
    public void addTransaction(Transaction transaction) {
        Task<Transaction> newTransaction = new Task<Transaction>() {
            @Override
            protected Transaction call() throws Exception {
                return transactionsService.saveNewTransaction(transaction);
            }
        };
        newTransaction.run();
        
        newTransaction.setOnSucceeded(e -> {
            TransactionModel model = new TransactionModel(newTransaction.getValue());
            this.transactionsList.add(model);
        });
    }
    
    private void retrieveDeposits() {
        Task<List<Deposit>> retrieveDeposits = new Task<List<Deposit>>() {
            @Override
            protected List<Deposit> call() throws Exception {
                return depositsService.retrieveDeposits();
            }
        };
        retrieveDeposits.run();
    
        retrieveDeposits.setOnSucceeded(e -> {
            List<Deposit> deposits = retrieveDeposits.getValue();
            if (deposits != null) {
                List<DepositModel> modelData = new ArrayList<>(deposits.size());
                for (Deposit deposit : deposits) {
                    DepositModel model = new DepositModel(deposit);
                    modelData.add(model);
                }
                this.depositsList.setAll(modelData);
            }
        });
    }
    
    public void addDeposit(Deposit deposit) {
        
        Task<Deposit> addDeposit = new Task<Deposit>() {
            @Override
            protected Deposit call() throws Exception {
                return depositsService.saveNewDeposit(deposit);
            }
        };
        addDeposit.run();
        
        addDeposit.setOnSucceeded(e -> {
            Deposit savedDeposit = addDeposit.getValue();
            this.depositsList.add(new DepositModel(savedDeposit));
        });
    }
    
    /**
     * Uses a thread to call the service to retrieve the list of savings goals.
     *
     * @TODO implement failure handling
     */
    private void retrieveSavingsGoals() {
        
        Task<List<SavingsGoal>> retrieveGoals = new Task<>() {
            @Override
            protected List<SavingsGoal> call() {
                return savingsGoalsService.retrieveSavingsGoals();
            }
        };
        retrieveGoals.run();
        
        retrieveGoals.setOnSucceeded(e -> {
            List<SavingsGoal> savingsGoals = retrieveGoals.getValue();
            if (savingsGoals != null) {
                List<SavingsGoalModel> modelGoals = new ArrayList<>(savingsGoals.size());
                for (SavingsGoal goal : savingsGoals) {
                    modelGoals.add(new SavingsGoalModel(goal));
                }
                savingsGoalsList.addAll(modelGoals);
            }
        });
    }
    
    /**
     * Delegates to the API service to update the savings goal. Runs on a separate thread.
     *
     * @param savingsGoal Contains the updated values to persist. Must include a savingsGoalId.
     */
    private void updateSavingsGoal(SavingsGoal savingsGoal) {
        Task<SavingsGoal> task = new Task<>() {
            @Override
            protected SavingsGoal call() {
                return savingsGoalsService.updateSavingsGoal(savingsGoal);
            }
        };
        task.run();
    
        task.setOnSucceeded(e -> {
            SavingsGoal savedGoal = task.getValue();
            SavingsGoalModel oldModel = this.savingsGoalsList.get(this.selectedSavingsGoalIndex);
            this.savingsGoalsList.remove(oldModel);
            this.selectedSavingsGoalIndex = null;
            updateSavingsGoalModel(savedGoal);
        });
    }
    
    private void addNewSavingsGoal(SavingsGoal savingsGoal) {
        Task<SavingsGoal> task = new Task<>() {
            @Override
            protected SavingsGoal call() {
                return savingsGoalsService.addNewSavingsGoal(savingsGoal);
            }
        };
        task.run();
    
        task.setOnSucceeded(e -> {
            SavingsGoal savedGoal = task.getValue();
            updateSavingsGoalModel(savedGoal);
        });
        task.setOnFailed(e -> {
            System.out.println("Failed");
        });
    }
    
    private void updateSavingsGoalModel(SavingsGoal savingsGoal) {
        SavingsGoalModel model = new SavingsGoalModel(savingsGoal);
        this.savingsGoalsList.add(model);
        Collections.sort(this.savingsGoalsList);
    }
    
    public void saveSavingsGoal(SavingsGoal savingsGoal) {
        if (savingsGoal.getSavingsGoalId() == null) {
            addNewSavingsGoal(savingsGoal);
        }
        else {
            updateSavingsGoal(savingsGoal);
        }
    }
    
    public void deleteSavingsGoal(SavingsGoal savingsGoal) {
        Task task = new Task() {
            @Override
            protected Object call() {
                savingsGoalsService.deleteSavingsGoal(savingsGoal.getSavingsGoalId());
                return null;
            }
        };
        task.run();
        
        task.setOnSucceeded(e -> {
            SavingsGoalModel selectedItem = this.savingsGoalsList.get(this.selectedSavingsGoalIndex);
            this.savingsGoalsList.remove(selectedItem);
            this.selectedSavingsGoalIndex = null;
        });
        
        task.setOnFailed(e -> {
            System.out.println(e);
        });
    }
    
    public Integer getSelectedSavingsGoalIndex() {
        return selectedSavingsGoalIndex;
    }
    
    public void setSelectedSavingsGoalIndex(Integer selectedSavingsGoalIndex) {
        this.selectedSavingsGoalIndex = selectedSavingsGoalIndex;
    }
    
    public ObservableList<SavingsGoalModel> getSavingsGoalsList() {
        
        if (this.savingsGoalsList == null || this.savingsGoalsList.size() == 0) {
            retrieveSavingsGoals();
        }
        return savingsGoalsList;
    }
    
    public ObservableList<DepositModel> getDepositsList() {
        if (this.depositsList == null || this.depositsList.size() == 0) {
            retrieveDeposits();
        }
        return depositsList;
    }
    
    public ObservableList<TransactionModel> getTransactionsList() {
        if (this.transactionsList == null || this.transactionsList.size() == 0) {
            retrieveTransactions();
        }
        return transactionsList;
    }
}
