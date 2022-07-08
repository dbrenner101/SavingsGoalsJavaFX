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

/**
 * Class to handle initializing controllers for individual views and then making them visible. Also handles swapping
 * from one view to another.
 */
public class ViewFactory {
    private Parent displayedView;
    private AnchorPane targetPane;
    
    SavingsGoalManager savingsGoalManager;
    Stage stage;
    
    public ViewFactory(SavingsGoalManager savingsGoalManager) {
        this.savingsGoalManager = savingsGoalManager;
    }
    
    /**
     * Method to call when application first starts. The main window contains the menu and anchor pane to display the
     * active scene.
     */
    public void showMainWindow() {
        MainWindowController mainWindowController = new MainWindowController(this.savingsGoalManager, this, "mainWindow.fxml");
        initializeStage(mainWindowController);
        this.targetPane = mainWindowController.getRootPane();
        showSavingsGoalList();
    }
    
    /**
     * Shows the scene to enter allocation values for goals
     */
    public void showDepositAllocation() {
        BaseController baseController = new DepositAllocationController(this.savingsGoalManager, this, "allocateDepositView.fxml");
        initView(baseController);
    }
    
    /**
     * Displays the scene with the list of transactions
     */
    public void showTransactionList() {
        BaseController baseController = new TransactionsListController(this.savingsGoalManager, this, "transactionsList.fxml");
        initView(baseController);
    }
    
    /**
     * Displays the scene for adding or editing a transaction (only adding is currently supported)
     */
    public void showAddEditTransaction() {
        BaseController baseController = new AddEditTransactionController(this.savingsGoalManager, this, "addEditTransaction.fxml");
        initView(baseController);
    }
    
    /**
     * Displays the scene with the list of savings goals and their information
     */
    public void showSavingsGoalList() {
        BaseController baseController = new SavingsGoalTableController(this.savingsGoalManager, this, "savingsGoalsTableView.fxml");
        initView(baseController);
    }
    
    /**
     * Displays the scene for adding or updating a savings goal
     */
    public void showAddUpdateSavingsGoals() {
        BaseController baseController = new AddUpdateSavingsGoalController(this.savingsGoalManager, this, "addSavingsGoal.fxml");
        initView(baseController);
    }
    
    /**
     * Displays a scene with the list of deposits waiting to be allocated.
     */
    public void showDepositsList() {
        BaseController baseController = new DepositsListController(this.savingsGoalManager, this, "depositsList.fxml");
        initView(baseController);
    }
    
    /**
     * Displays the add/update deposit scene.
     */
    public void showAddUpdateDeposits() {
        BaseController baseController = new AddUpdateDepositController(this.savingsGoalManager, this, "addDeposit.fxml");
        initView(baseController);
    }
    
    /** method called to close a stage.
     *
     *  @param stageToClose The stage to close
     */
    public  void closeStage(Stage stageToClose){
        
        stageToClose.close();
    }
    
    /**
     * Initializes the controller and loads the FXML to build the scene.
     *
     * @param baseController The controller instance with the named FXML file
     */
    private void initView(BaseController baseController) {
        Parent viewPane = loadParentFromFxml(baseController);
    
        if (this.displayedView != null) {
            this.targetPane.getChildren().remove(this.displayedView);
        }
        
        this.targetPane.getChildren().add(viewPane);
        this.displayedView = viewPane;
    }
    
    /**
     * USed to initialize the main window and set the stage.
     * @param baseController
     */
    private void initializeStage(BaseController baseController){
        Parent parent = loadParentFromFxml(baseController);
        
        Scene scene = new Scene(parent);
        
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
        baseController.postInitialization();
    }
    
    private Parent loadParentFromFxml(BaseController baseController) {
        URL file = getClass().getResource(baseController.getFxmlName());
        FXMLLoader fxmlLoader = new FXMLLoader(file);
        fxmlLoader.setController(baseController);
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return parent;
    }
}
