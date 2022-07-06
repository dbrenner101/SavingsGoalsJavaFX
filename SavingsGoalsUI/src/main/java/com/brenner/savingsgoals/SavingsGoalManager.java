package com.brenner.savingsgoals;

import com.brenner.savingsgoals.controller.service.DepositsService;
import com.brenner.savingsgoals.controller.service.SavingsGoalsService;
import com.brenner.savingsgoals.controller.service.model.Deposit;
import com.brenner.savingsgoals.controller.service.model.SavingsGoal;
import com.brenner.savingsgoals.model.DepositModel;
import com.brenner.savingsgoals.model.SavingsGoalModel;
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
    
    
    private ObservableList<SavingsGoalModel> savingsGoalsList = FXCollections.observableArrayList();
    
    private ObservableList<DepositModel> depositsList = FXCollections.observableArrayList();
    
    private Integer selectedSavingsGoalIndex = null;
    
    public void retrieveDeposits() {
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
    public void retrieveSavingsGoals() {
        
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
        return savingsGoalsList;
    }
    
    public ObservableList<DepositModel> getDepositsList() {
        return depositsList;
    }
}
