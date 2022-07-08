package com.brenner.savingsgoals.controller;

import com.brenner.savingsgoals.SavingsGoalManager;
import com.brenner.savingsgoals.model.TransactionModel;
import com.brenner.savingsgoals.util.CommonUtils;
import com.brenner.savingsgoals.view.TableColumnFormatter;
import com.brenner.savingsgoals.view.ViewFactory;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.util.Callback;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller to manage behavior and interactions with the list of transactions. The transactions table is backed by
 * and Observable list managed by the SavingsGoalManager
 *
 * Relies on the transactionList.fxml
 */
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
        dateCol.setCellValueFactory(new PropertyValueFactory<TransactionModel, Date>("transactionDateProp"));
        dateCol.setCellFactory(new TableColumnFormatter<TransactionModel, Date>(CommonUtils.STD_FORMAT));
        fromGoalCol.setCellValueFactory(new PropertyValueFactory<TransactionModel, String>("fromGoalProp"));
        toGoalCol.setCellValueFactory(new PropertyValueFactory<TransactionModel, String>("toGoalProp"));
        notesCol.setCellValueFactory(new PropertyValueFactory<TransactionModel, String>("notesProp"));
        
        this.transactionsTable.setItems(this.savingsGoalManager.getTransactionsList());
    
        this.transactionsTable.setRowFactory(new Callback<TableView<TransactionModel>, TableRow<TransactionModel>>() {
            @Override
            public TableRow<TransactionModel> call(TableView<TransactionModel> param) {
                final TableRow<TransactionModel> row = new TableRow<>();
            
                final ContextMenu tableRowMenu = new ContextMenu();
                final MenuItem deleteTransactionItem = new MenuItem("Delete Transaction");
                deleteTransactionItem.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Confirm delete transaction");
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.showAndWait().ifPresent(type -> {
                        if (type.getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
                            TransactionModel selectedModel = transactionsTable.getSelectionModel().getSelectedItem();
                            savingsGoalManager.deleteTransaction(selectedModel.getTransaction());
                        }
                    });
                });
    
                tableRowMenu.getItems().addAll(deleteTransactionItem);
                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(tableRowMenu)
                                .otherwise((ContextMenu)null));
                return row;
            }
        });
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildTransactionsTable();
    }
}
