package com.brenner.savingsgoals;

import com.brenner.savingsgoals.model.DepositModel;
import com.brenner.savingsgoals.model.SavingsGoalModel;
import com.brenner.savingsgoals.model.TransactionModel;
import com.brenner.savingsgoals.service.DepositsService;
import com.brenner.savingsgoals.service.SavingsGoalsService;
import com.brenner.savingsgoals.service.TransactionsService;
import com.brenner.savingsgoals.service.model.Deposit;
import com.brenner.savingsgoals.service.model.SavingsGoal;
import com.brenner.savingsgoals.service.model.SavingsGoalDepositAllocation;
import com.brenner.savingsgoals.service.model.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class manages the intersection between the view and API calls.
 */
public class SavingsGoalManager {
    
    private SavingsGoalsService savingsGoalsService = new SavingsGoalsService();
    private DepositsService depositsService = new DepositsService();
    private TransactionsService transactionsService = new TransactionsService();
    
    private Integer selectedSavingsGoalIndex = null;
    private DepositModel selectedDepositModel;
    
    private ObservableList<SavingsGoalModel> savingsGoalsList = FXCollections.observableArrayList();
    private ObservableList<DepositModel> depositsList = FXCollections.observableArrayList();
    private ObservableList<TransactionModel> transactionsList = FXCollections.observableArrayList();
    
    /**
     * Call to retrieve transactions and populate the transactions list. Creates a task and runs on a separate thread.
     * Converts the business objects to model objects and updated the observable list.
     */
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
                this.transactionsList.setAll(modelList);
            }
        });
        
        retrieveTransactions.setOnFailed(e -> {
            Alert alert = createErrorAlert("Failure retrieving transactions " + retrieveTransactions.getException().getMessage());
            alert.show();
        });
    }
    
    /**
     * Method to handle requests to save a new transaction. Runs on a separate thread.
     *
     * @param transaction The transaction to save.
     */
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
        
        newTransaction.setOnFailed(e -> {
            Alert alert = createErrorAlert("Save transaction failed: " + newTransaction.getException().getMessage());
            alert.show();
        });
        
        this.retrieveSavingsGoalsAsync();
    }
    
    /**
     * Method to call service to retrieve the list of deposits. Converts the business objects to model objects
     * and updates the observable list. Runs on a new thread.
     */
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
        
        retrieveDeposits.setOnFailed(e -> {
            Alert alert = createErrorAlert("Retrieve deposits failed: " + retrieveDeposits.getException().getMessage());
            alert.show();
        });
    }
    
    /**
     * Method to call delete deposit on API. After successful deletion the deposits list is refreshed.
     *
     * @param deposit The deposit to delete, must include depositId
     */
    public void deleteDeposit(Deposit deposit) {
        
        Task<Deposit> depositTask = new Task<Deposit>() {
            @Override
            protected Deposit call() throws Exception {
                depositsService.deleteDeposit(deposit.getDepositId());
                return null;
            }
        };
        depositTask.run();
        
        depositTask.setOnSucceeded(e -> {
            retrieveDeposits();
        });
        
        depositTask.setOnFailed(e -> {
            Alert alert = createErrorAlert("Delete deposit failed: " + depositTask.getException().getMessage());
            alert.show();
        });
    }
    
    /**
     * Entry point for saving or updating a deposit. A deposit with a depositId will be updated otherwise added.
     *
     * @param deposit Objext to persist
     */
    public void saveDeposit(Deposit deposit) {
        if (deposit.getDepositId() != null) {
            updateDeposit(deposit);
        }
        else {
            addDeposit(deposit);
        }
    }
    
    /**
     * Async method to update a deposit
     *
     * @param deposit The container for the deposit data to update
     */
    private void updateDeposit(Deposit deposit) {
        Task<Deposit> updateDepositTask = new Task<Deposit>() {
            @Override
            protected Deposit call() throws Exception {
                return depositsService.updateDeposit(deposit);
            }
        };
        updateDepositTask.run();
        
        updateDepositTask.setOnSucceeded(e -> {
            Deposit result = updateDepositTask.getValue();
            this.selectedDepositModel = null;
            retrieveDeposits();
        });
        
        updateDepositTask.setOnFailed(e -> {
            Alert alert = createErrorAlert("Update deposit failed: " + updateDepositTask.getException().getMessage());
            alert.show();
        });
    }
    
    public void updateTransactionAppliedValue(Transaction transaction) {
        Task<Transaction> task = new Task<Transaction>() {
            @Override
            protected Transaction call() throws Exception {
                transactionsService.updateTransaction(transaction);
                return null;
            }
        };
        task.run();
        
        task.setOnSucceeded(e -> {
            retrieveTransactions();
        });
        task.setOnFailed(e -> {
            Alert alert = createErrorAlert("Update transaction failed: " + task.getException().getMessage());
            alert.show();
        });
    }
    
    /**
     * Support for passing a new deposit object to the backend for saving. Runs on a new thread.
     * @param deposit
     */
    private void addDeposit(Deposit deposit) {
        
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
        
        addDeposit.setOnFailed(e -> {
            Alert alert = createErrorAlert("Add deposit failed: " + addDeposit.getException().getMessage());
            alert.show();
        });
    }
    
    /**
     * Uses a thread to call the service to retrieve the list of savings goals.
     */
    private void retrieveSavingsGoalsAsync() {
        
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
                savingsGoalsList.setAll(modelGoals);
            }
        });
        
        retrieveGoals.setOnFailed(e -> {
            Alert alert = createErrorAlert("Retrieve savings goals failed: " + retrieveGoals.getException().getMessage());
            alert.show();
        });
    }
    
    /**
     * Synchronous method for retrieve a list of savings goals.
     */
    private void retrieveSavingsGoalsBlocking() {
        List<SavingsGoal> savingsGoals = savingsGoalsService.retrieveSavingsGoals();
        if (savingsGoals != null) {
            List<SavingsGoalModel> modelGoals = new ArrayList<>(savingsGoals.size());
            for (SavingsGoal goal : savingsGoals) {
                modelGoals.add(new SavingsGoalModel(goal));
            }
            savingsGoalsList.addAll(modelGoals);
        }
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
        
        task.setOnFailed(e -> {
            Alert alert = createErrorAlert("Update savings goal failed: " + task.getException().getMessage());
            alert.show();
        });
    }
    
    /**
     * Async method to add a new savings goal
     *
     * @param savingsGoal The savings goal data to save
     */
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
            Alert alert = createErrorAlert("Add new savings goal failed: " + task.getException().getMessage());
            alert.show();
        });
    }
    
    /**
     * Helper method to add a business SavingsGoal object to the model backing the view.
     *
     * @param savingsGoal The object to convert and save
     */
    private void updateSavingsGoalModel(SavingsGoal savingsGoal) {
        SavingsGoalModel model = new SavingsGoalModel(savingsGoal);
        this.savingsGoalsList.add(model);
        Collections.sort(this.savingsGoalsList);
    }
    
    /**
     * Entry point for saving a SavingsGoal. This method will determine if the goal should be updated or saved as new.
     * @param savingsGoal
     */
    public void saveSavingsGoal(SavingsGoal savingsGoal) {
        if (savingsGoal.getSavingsGoalId() == null) {
            addNewSavingsGoal(savingsGoal);
        }
        else {
            updateSavingsGoal(savingsGoal);
        }
    }
    
    /**
     * Async method for deleting a savings goal.
     *
     * @param savingsGoal The goal with unique identifier to delete
     */
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
            Alert alert = createErrorAlert("Delete savings goal failed: " + task.getException().getMessage());
        });
    }
    
    public void deleteTransaction(Transaction transaction) {
        Task<Transaction> task = new Task<Transaction>() {
            @Override
            protected Transaction call() throws Exception {
                transactionsService.deleteTransaction(transaction.getTransactionId());
                return null;
            }
        };
        task.run();
        
        task.setOnSucceeded(e -> {
            retrieveTransactions();
        });
        
        task.setOnFailed(e -> {
            Alert alert = createErrorAlert("Delete transaction failed: " + task.getException().getMessage());
            alert.show();
        });
    }
    
    /**
     * Synchronous call to retrieve the designated default goal.
     *
     * @return The default goal model object
     */
    public SavingsGoalModel getDefaultGoal() {
        
        SavingsGoal goal = this.savingsGoalsService.getDefaultGoal();
        return new SavingsGoalModel(goal);
    }
    
    /**
     * Method converts the model objects in the list to allocation objects in a new list and then calls for them to be updated.
     *
     * After persistence the goals and deposit lists are refreshed.
     *
     * @param savingsGoalModels List of goals with their updated allocations amounts.
     */
    public void allocateDepositToGoals(List<SavingsGoalModel> savingsGoalModels) {
        
        List<SavingsGoalDepositAllocation> savingsGoalAllocations = new ArrayList<>(savingsGoalModels.size());
        
        for (SavingsGoalModel model : savingsGoalModels) {
            SavingsGoalDepositAllocation allocation = new SavingsGoalDepositAllocation(
                    model.getSavingsGoalId(),
                    this.selectedDepositModel.getDeposit().getDepositId(),
                    new BigDecimal(model.getAllocatedAmount()));
            savingsGoalAllocations.add(allocation);
        }
        
        this.savingsGoalsService.allocateDepositToGoals(savingsGoalAllocations);
        this.retrieveSavingsGoalsAsync();
        this.retrieveDeposits();
    }
    
    /**
     * Helper method to build an error  alert instance.
     *
     * @param message Message to display on the alert body
     * @return A new Alert instance
     */
    private Alert createErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        return alert;
    }
    
    public Integer getSelectedSavingsGoalIndex() {
        return selectedSavingsGoalIndex;
    }
    
    public void setSelectedSavingsGoalIndex(Integer selectedSavingsGoalIndex) {
        this.selectedSavingsGoalIndex = selectedSavingsGoalIndex;
    }
    
    public ObservableList<SavingsGoalModel> getSavingsGoalsList(boolean async) {
        
        if (this.savingsGoalsList == null || this.savingsGoalsList.size() == 0) {
            if (async) {
                retrieveSavingsGoalsAsync();
            }
            else {
                retrieveSavingsGoalsBlocking();
            }
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
    
    public DepositModel getSelectedDepositModel() {
        return selectedDepositModel;
    }
    
    public void setSelectedDepositModel(DepositModel selectedDepositModel) {
        this.selectedDepositModel = selectedDepositModel;
    }
}
