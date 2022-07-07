package com.brenner.savingsgoals.controller;

import com.brenner.savingsgoals.SavingsGoalManager;
import com.brenner.savingsgoals.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller than handles interactions with the parent window/stage. This includes the menu options which are
 * the primary navigation tools in the UI. Delegation of view initialization and display are left to the ViewFactory
 *
 * Relies on teh mainWindow.fxml
 */
public class MainWindowController extends BaseController implements Initializable {
    
    @FXML
    private MenuItem addNewDepositMenuItem;
    
    @FXML
    private MenuItem addNewSavingsGoalMenuItem;
    
    @FXML
    private MenuItem addNewTransactionMenuItem;
    
    @FXML
    private MenuItem displayDepositMenuItem;
    
    @FXML
    private MenuItem displaySavingsGoalsMnuItm;
    
    @FXML
    private MenuItem displayTransactionMenuItem;
    
    @FXML
    private AnchorPane rootPane;
    
    
    public MainWindowController(SavingsGoalManager savingsGoalManager, ViewFactory viewFactory, String fxmlName) {
        super(savingsGoalManager, viewFactory, fxmlName);
    }
    
    @FXML
    void addNewTransactionAction(ActionEvent event) {
        super.viewFactory.showAddEditTransaction();
    }
    
    @FXML
    void displayTransactionsAction(ActionEvent event) {
        super.viewFactory.showTransactionList();
    }
    
    @FXML
    void displaySavingsGoalsAction(ActionEvent event) {
        super.viewFactory.showSavingsGoalList();
    }
    
    @FXML
    void addNewSavingsGoalMenuItemAction(ActionEvent event) {
        super.viewFactory.showAddUpdateSavingsGoals();
    }
    
    @FXML
    void displayDepositsMenuItemAction(ActionEvent event) {
        super.viewFactory.showDepositsList();
    }
    
    @FXML
    void addNewDepositsMenuItemAction(ActionEvent event) {
        this.savingsGoalManager.setSelectedDepositModel(null);
        super.viewFactory.showAddUpdateDeposits();
    }
    
    public AnchorPane getRootPane() {
        return this.rootPane;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }
}
