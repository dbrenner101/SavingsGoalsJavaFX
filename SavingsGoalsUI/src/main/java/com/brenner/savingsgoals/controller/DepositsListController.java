package com.brenner.savingsgoals.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.brenner.savingsgoals.SavingsGoalManager;
import com.brenner.savingsgoals.model.DepositModel;
import com.brenner.savingsgoals.util.CommonUtils;
import com.brenner.savingsgoals.view.TableColumnFormatter;
import com.brenner.savingsgoals.view.ViewFactory;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.util.Callback;

/**
 * Controller to handle management and interactions with the list of deposits. A context menu on each row in the table
 * allows access to the allocation view.
 *
 * Relies on the despositsList.fxml
 */
public class DepositsListController extends BaseController implements Initializable {
    
    
    @FXML private TableColumn<DepositModel, Float> depositAmountCol;
    
    @FXML private TableColumn<DepositModel, Date> depositDateCol;
    
    @FXML private TableColumn<DepositModel, Boolean> includeInAllocationCol;
    
    @FXML private TableView<DepositModel> depositsView;
    
    @FXML private Button allocateButton;
    
    public DepositsListController(SavingsGoalManager savingsGoalManager, ViewFactory viewFactory, String fxmlName) {
        super(savingsGoalManager, viewFactory, fxmlName);
    }
    
    public DepositsListController(SavingsGoalManager savingsGoalManager, ViewFactory viewFactory, String fxmlName,
            TableColumn<DepositModel, Float> depositAmountCol, TableColumn<DepositModel, Date> depositDateCol, 
            TableView<DepositModel> depositsView) {
        super(savingsGoalManager, viewFactory, fxmlName);
        this.depositAmountCol = depositAmountCol;
        this.depositDateCol = depositDateCol;
        this.depositsView = depositsView;
    }
    
    @FXML
    void allocateButtonAction(ActionEvent event) {
        List<DepositModel> deposits = Collections.unmodifiableList(this.depositsView.getItems());
        List<DepositModel> selectedDeposits = deposits.stream().filter(t -> t.isSelected()).collect(Collectors.toList());
        
        this.savingsGoalManager.setSelectedDepositsForAllocation(selectedDeposits);
        viewFactory.showDepositAllocation();
        
    }
    
    /**
     * Builds the table view for deposits. Associates the table with the daposits list in the SavingsGoalManager. Cell
     * factory is set for the date column to convert from a Date to a String
     *
     * A row factory is set that attaches a context menu for edit, delete and allocate functions.
     */
    private void setUpDepositTableView() {
        depositAmountCol.setCellValueFactory(new PropertyValueFactory<DepositModel, Float>("depositAmountProp"));
        depositDateCol.setCellValueFactory(new PropertyValueFactory<DepositModel, Date>("depositDateProp"));
        depositDateCol.setCellFactory(new TableColumnFormatter<DepositModel, Date>(CommonUtils.STD_FORMAT));
    
        ObservableList<DepositModel> depositsList = this.savingsGoalManager.getDepositsList();
        this.depositsView.setItems(depositsList);
    
        includeInAllocationCol.setCellFactory(CheckBoxTableCell.forTableColumn(new Callback<Integer, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Integer param) {
                DepositModel depositModel = depositsList.get(param);
                return depositModel.selectedProperty();
            }
        }));
    
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
                        List<DepositModel> selectedDeposits = new ArrayList<>(1);
                        selectedDeposits.add(selectedModel);
                        savingsGoalManager.setSelectedDepositsForAllocation(selectedDeposits);
                        viewFactory.showDepositAllocation();
                    }
                });
                
                final MenuItem deleteDepositItem = new MenuItem("Delete");
                deleteDepositItem.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Confirm delete deposit");
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.showAndWait().ifPresent(type -> {
                        if (type.getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
                                DepositModel selectedModel = depositsView.getSelectionModel().getSelectedItem();
                                savingsGoalManager.deleteDeposit(selectedModel.getDeposit());
                        }
                    });
                });
                
                final MenuItem editDepositItem = new MenuItem("Edit");
                editDepositItem.setOnAction(e -> {
                    savingsGoalManager.setSelectedDepositModel(depositsView.getSelectionModel().getSelectedItem());
                    viewFactory.showAddUpdateDeposits();
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
            
                tableRowMenu.getItems().addAll(allocateDepositMenuItem, deleteDepositItem, editDepositItem);
            
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
