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
import javafx.util.Callback;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller to handle management and interactions with the list of deposits. A context menu on each row in the table
 * allows access to the allocation view.
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
            
                row.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
                        int index = depositsView.getSelectionModel().getSelectedIndex();
                        if (index >= 0) {
                            /*savingsGoalManager.setSelectedSavingsGoalIndex(index);
                            viewFactory.showAddUpdateSavingsGoals();*/
                            System.out.println("Double click - edit");
                        }
                    }
                });
            
                tableRowMenu.getItems().addAll(allocateDepositMenuItem);
            
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
