package com.brenner.savingsgoals.controller;

import com.brenner.savingsgoals.SavingsGoalManager;
import com.brenner.savingsgoals.model.TransactionModel;
import com.brenner.savingsgoals.service.model.Transaction;
import com.brenner.savingsgoals.util.CommonUtils;
import com.brenner.savingsgoals.view.TableColumnFormatter;
import com.brenner.savingsgoals.view.ViewFactory;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller to manage behavior and interactions with the list of transactions. The transaction table is backed by
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
    private TableColumn<TransactionModel, Boolean> appliedCol;
    
    @FXML
    private TableView<TransactionModel> transactionsTable;
    
    public TransactionsListController(SavingsGoalManager savingsGoalManager, ViewFactory viewFactory, String fxmlName) {
        super(savingsGoalManager, viewFactory, fxmlName);
    }
    
    private void buildTransactionsTable() {
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amountProp"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("transactionDateProp"));
        dateCol.setCellFactory(new TableColumnFormatter<>(CommonUtils.STD_FORMAT));
        fromGoalCol.setCellValueFactory(new PropertyValueFactory<>("fromGoalProp"));
        toGoalCol.setCellValueFactory(new PropertyValueFactory<>("toGoalProp"));
        notesCol.setCellValueFactory(new PropertyValueFactory<>("notesProp"));
        appliedCol.setCellValueFactory(new PropertyValueFactory<>("appliedProp"));
    
        ObservableList<TransactionModel> transactionsList = this.savingsGoalManager.getTransactionsList();
        this.transactionsTable.setItems(transactionsList);
        appliedCol.setCellFactory(CheckBoxTableCell.forTableColumn(appliedCol));
    
        transactionsList.addListener((ListChangeListener<TransactionModel>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (TransactionModel model : c.getAddedSubList()) {
                        model.appliedPropProperty().addListener((observable, oldValue, newValue) -> {
                            Transaction t = model.getTransaction();
                            t.setApplied(newValue);
                            savingsGoalManager.updateTransactionAppliedValue(t);
                        });
                    }
                }
            }
        });
    
        this.transactionsTable.setRowFactory(param -> {
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
        });
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildTransactionsTable();
    }
}
