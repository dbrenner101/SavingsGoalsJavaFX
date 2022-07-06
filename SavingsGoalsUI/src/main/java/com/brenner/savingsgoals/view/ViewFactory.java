package com.brenner.savingsgoals.view;

import com.brenner.savingsgoals.SavingsGoalManager;
import com.brenner.savingsgoals.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ViewFactory {
    private Parent displayedView;
    private AnchorPane targetPane;
    
    SavingsGoalManager savingsGoalManager;
    Stage stage;
    
    public ViewFactory(SavingsGoalManager savingsGoalManager) {
        this.savingsGoalManager = savingsGoalManager;
    }
    
    public void showMainWindow() {
        MainWindowController mainWindowController = new MainWindowController(this.savingsGoalManager, this, "mainWindow.fxml");
        //BaseController baseController = new MainWindowControllerOld(this.savingsGoalManager, this, "mainWindowOld.fxml");
        initializeStage(mainWindowController);
        this.targetPane = mainWindowController.getRootPane();
    }
    
    public void showDepositAllocation() {
        BaseController baseController = new DepositAllocationController(this.savingsGoalManager, this, "allocateDepositView.fxml");
        initView(baseController);
    }
    
    public void showTransactionList() {
        BaseController baseController = new TransactionsListController(this.savingsGoalManager, this, "transactionsList.fxml");
        initView(baseController);
    }
    
    public void showAddEditTransaction() {
        BaseController baseController = new AddEditTransactionController(this.savingsGoalManager, this, "addEditTransaction.fxml");
        initView(baseController);
    }
    
    public void showSavingsGoalList() {
        BaseController baseController = new SavingsGoalTableController(this.savingsGoalManager, this, "savingsGoalsTableView.fxml");
        initView(baseController);
    }
    
    public void showAddUpdateSavingsGoals() {
        BaseController baseController = new AddUpdateSavingsGoalController(this.savingsGoalManager, this, "addSavingsGoal.fxml");
        initView(baseController);
        //initializeStage(baseController);
    }
    
    public void showDepositsList() {
        BaseController baseController = new DepositsListController(this.savingsGoalManager, this, "depositsList.fxml");
        initView(baseController);
    }
    
    public void showAddUpdateDeposits() {
        BaseController baseController = new AddUpdateDepositController(this.savingsGoalManager, this, "addDeposit.fxml");
        initView(baseController);
        //this.screenController.addScreen("addUpdateDeposits", scene);
        //initializeStage(baseController);
    }
    
    public  void closeStage(Stage stageToClose){
        
        stageToClose.close();
        //activeStages.remove(stageToClose);
    }
    
    private void initView(BaseController baseController) {
        URL file = getClass().getResource(baseController.getFxmlName());
        FXMLLoader fxmlLoader = new FXMLLoader(file);
        fxmlLoader.setController(baseController);
        Parent viewPane = null;
        try {
            viewPane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        if (this.displayedView != null) {
            this.targetPane.getChildren().remove(this.displayedView);
        }
        /*this.stage.getScene().getWindow().setHeight(viewPane.getLayoutX());
        this.stage.getScene().getWindow().setWidth(viewPane.getLayoutY());*/
        this.targetPane.getChildren().add(viewPane);
        this.displayedView = viewPane;
    }
    
    private void initializeStage(BaseController baseController){
        URL file = getClass().getResource(baseController.getFxmlName());
        FXMLLoader fxmlLoader = new FXMLLoader(file);
        fxmlLoader.setController(baseController);
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Scene scene = new Scene(parent);
        
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
        baseController.postInitialization();
    }
}
