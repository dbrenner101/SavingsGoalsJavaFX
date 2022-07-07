package com.brenner.savingsgoals.controller;

import com.brenner.savingsgoals.SavingsGoalManager;
import com.brenner.savingsgoals.model.DepositModel;
import com.brenner.savingsgoals.util.CommonUtils;
import com.brenner.savingsgoals.view.TableColumnFormatter;
import com.brenner.savingsgoals.view.ViewFactory;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.util.Callback;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller to handle management and interactions with the list of deposits. A context menu on each row in the table
 * allows access to the allocation view.
 *
 * Relies on teh despositsList.fxml
 *
 * @TODO Add delete/edit deposit capability
 */
public class DepositsListController extends BaseController implements Initializable {
    
    
    @FXML
    private TableColumn<DepositModel, Float> depositAmountCol;
    
    @FXML
    private TableColumn<DepositModel, Date> depositDateCol;
    
    @FXML
    private TableView<DepositModel> depositsView;
    
    public DepositsListController(SavingsGoalManager savingsGoalManager, ViewFactory viewFactory, String fxmlName) {
        super(savingsGoalManager, viewFactory, fxmlName);
    }
    
    private void setUpDepositTableView() {
        depositAmountCol.setCellValueFactory(new PropertyValueFactory<DepositModel, Float>("depositAmountProp"));
        depositDateCol.setCellValueFactory(new PropertyValueFactory<DepositModel, Date>("depositDateProp"));
        
        depositDateCol.setCellFactory(new TableColumnFormatter<DepositModel, Date>(CommonUtils.STD_FORMAT));
        
        this.depositsView.setItems(this.savingsGoalManager.getDepositsList());
    
        this.depositsView.setRowFactory(new Callback<TableView<DepositModel>, TableRow<DepositModel>>() {
            @Override
            public TableRow<DepositModel> call(TableView<DepositModel> param) {
                final TableRow<DepositModel> row = new TableRow<>();
                final ContextMenu tableRowMenu = new ContextMenu();
                final MenuItem allocateDepositMenuItem = new MenuItem("Allocate Deposit");
                allocateDepositMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        DepositModel selectedModel = depositsView.getSelectionModel().getSelectedItem();
                        savingsGoalManager.setSelectedDepositModel(selectedModel);
                        viewFactory.showDepositAllocation();
                    }
                });
                
                final MenuItem deleteDepositItem = new MenuItem("Delete");
                deleteDepositItem.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.showAndWait().ifPresent(type -> {
                        System.out.println(type.getButtonData().getTypeCode());
                        if (type.getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
                                DepositModel selectedModel = depositsView.getSelectionModel().getSelectedItem();
                                savingsGoalManager.deleteDeposit(selectedModel.getDeposit());
                        }
                    });
                });
            
                row.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
                        int index = depositsView.getSelectionModel().getSelectedIndex();
                        if (index >= 0) {
                            savingsGoalManager.setSelectedDepositModel(depositsView.getSelectionModel().getSelectedItem());
                            viewFactory.showAddUpdateDeposits();
                        }
                    }
                });
            
                tableRowMenu.getItems().addAll(allocateDepositMenuItem, deleteDepositItem);
            
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
        setUpDepositTableView();
    }
}
