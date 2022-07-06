package com.brenner.savingsgoals.controller;

import com.brenner.savingsgoals.SavingsGoalManager;
import com.brenner.savingsgoals.model.TransactionModel;
import com.brenner.savingsgoals.util.CommonUtils;
import com.brenner.savingsgoals.view.TableColumnFormatter;
import com.brenner.savingsgoals.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class TransactionsListController extends BaseController implements Initializable {
    
    @FXML
    private TableColumn<TransactionModel, Float> amountCol;
    
    @FXML
    private TableColumn<TransactionModel, Date> dateCol;
    
    @FXML
    private TableColumn<TransactionModel, String> fromGoalCol;
    
    @FXML
    private TableColumn<TransactionModel, String> notesCol;
    
    @FXML
    private TableColumn<TransactionModel, String> toGoalCol;
    
    @FXML
    private TableView<TransactionModel> transactionsTable;
    
    public TransactionsListController(SavingsGoalManager savingsGoalManager, ViewFactory viewFactory, String fxmlName) {
        super(savingsGoalManager, viewFactory, fxmlName);
    }
    
    private void buildTransactionsTable() {
        amountCol.setCellValueFactory(new PropertyValueFactory<TransactionModel, Float>("amountProp"));
        dateCol.setCellValueFactory(new PropertyValueFactory<TransactionModel, Date>("dateProp"));
        dateCol.setCellFactory(new TableColumnFormatter<TransactionModel, Date>(CommonUtils.STD_FORMAT));
        fromGoalCol.setCellValueFactory(new PropertyValueFactory<TransactionModel, String>("fromGoalProp"));
        toGoalCol.setCellValueFactory(new PropertyValueFactory<TransactionModel, String>("toGoalProp"));
        notesCol.setCellValueFactory(new PropertyValueFactory<TransactionModel, String>("notesProp"));
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }
}
