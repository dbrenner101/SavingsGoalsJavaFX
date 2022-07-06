package com.brenner.savingsgoals.controller;

import com.brenner.savingsgoals.SavingsGoalManager;
import com.brenner.savingsgoals.model.SavingsGoalModel;
import com.brenner.savingsgoals.util.CommonUtils;
import com.brenner.savingsgoals.view.TableColumnFormatter;
import com.brenner.savingsgoals.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class DepositAllocationController extends BaseController implements Initializable {
    
    @FXML
    private TableView<SavingsGoalModel> savingsGoalsTable;
    
    @FXML
    private TableColumn<SavingsGoalModel, String> allocatedAmountCol;
    
    @FXML
    private TableColumn<SavingsGoalModel, Float> currentBalanceCol;
    
    @FXML
    private TableColumn<SavingsGoalModel, Integer> daysCol;
    
    @FXML
    private TableColumn<SavingsGoalModel, Date> endDateCol;
    
    @FXML
    private TableColumn<SavingsGoalModel, Float> initialBalanceCol;
    
    @FXML
    private TableColumn<SavingsGoalModel, String> savingsGoalCol;
    
    @FXML
    private TableColumn<SavingsGoalModel, Float> savingsPerWeekCol;
    
    @FXML
    private TableColumn<SavingsGoalModel, Date> startDateCol;
    
    @FXML
    private TableColumn<SavingsGoalModel, Float> targetAmountCol;
    
    @FXML
    private TableColumn<SavingsGoalModel, Integer> weeksCol;
    
    @FXML
    private Label unplannedAmount;
    
    public DepositAllocationController(SavingsGoalManager savingsGoalManager, ViewFactory viewFactory, String fxmlName) {
        super(savingsGoalManager, viewFactory, fxmlName);
    }
    
    @FXML
    void allocatedAmountUpdated(ActionEvent event) {
        System.out.println("Allocation amount changed");
    }
    
    @FXML
    void saveAllocationAction(ActionEvent event) {
        System.out.println("Save allocations");
    }
    
    private void setUpSavingsGoalsTableView() {
        currentBalanceCol.setCellValueFactory(new PropertyValueFactory<SavingsGoalModel, Float>("currentBalanceProp"));
        daysCol.setCellValueFactory(new PropertyValueFactory<SavingsGoalModel, Integer>("daysTillPaymentProp"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<SavingsGoalModel, Date>("savingsEndDateProp"));
        initialBalanceCol.setCellValueFactory(new PropertyValueFactory<SavingsGoalModel, Float>("initialBalanceProp"));
        savingsGoalCol.setCellValueFactory(new PropertyValueFactory<SavingsGoalModel, String>("goalNameProp"));
        savingsPerWeekCol.setCellValueFactory(new PropertyValueFactory<SavingsGoalModel, Float>("savingsPerWeekProp"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<SavingsGoalModel, Date>("savingsStartDateProp"));
        targetAmountCol.setCellValueFactory(new PropertyValueFactory<SavingsGoalModel, Float>("targetAmountProp"));
        weeksCol.setCellValueFactory(new PropertyValueFactory<SavingsGoalModel, Integer>("weeksTillPaymentProp"));
        allocatedAmountCol.setCellValueFactory(new PropertyValueFactory<SavingsGoalModel, String>("allocatedAmount"));
        
        startDateCol.setCellFactory(new TableColumnFormatter<SavingsGoalModel, Date>(CommonUtils.STD_FORMAT));
        endDateCol.setCellFactory(new TableColumnFormatter<SavingsGoalModel, Date>(CommonUtils.STD_FORMAT));
        allocatedAmountCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        allocatedAmountCol.setCellFactory(new Callback<TableColumn<SavingsGoalModel, String>, TableCell<SavingsGoalModel, String>>() {
//            @Override
//            public TableCell<SavingsGoalModel, String> call(TableColumn<SavingsGoalModel, String> param) {
//                return new TextFieldTableCell();
//            }
//        });
        allocatedAmountCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SavingsGoalModel, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SavingsGoalModel, String> event) {
                
                SavingsGoalModel model = event.getRowValue();
                model.setAllocatedAmount(event.getNewValue());
            }
        });
    }
    
    private void populateSavingsGoalTableView() {
        this.savingsGoalsTable.setItems(null);
        this.savingsGoalsTable.setItems(this.savingsGoalManager.getSavingsGoalsList());
        
        /*this.savingsGoalsTable.setRowFactory(new Callback<TableView<SavingsGoalModel>, TableRow<SavingsGoalModel>>() {
            @Override
            public TableRow<SavingsGoalModel> call(TableView<SavingsGoalModel> param) {
                final TableRow<SavingsGoalModel> row = new TableRow<>();
                final ContextMenu tableRowMenu = new ContextMenu();
                final MenuItem editRowItem = new MenuItem("Edit Goal");
                editRowItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        savingsGoalManager.setSelectedSavingsGoalIndex(savingsGoalsTable.getSelectionModel().getSelectedIndex());
                        viewFactory.showAddUpdateSavingsGoals();
                    }
                });
                
                final MenuItem deleteRowItem = new MenuItem("Delete Goal");
                deleteRowItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        savingsGoalManager.setSelectedSavingsGoalIndex(savingsGoalsTable.getSelectionModel().getSelectedIndex());
                        savingsGoalManager.deleteSavingsGoal(row.getItem().getSavingsGoal());
                    }
                });
                
                row.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
                        int index = savingsGoalsTable.getSelectionModel().getSelectedIndex();
                        if (index >= 0) {
                            savingsGoalManager.setSelectedSavingsGoalIndex(index);
                            viewFactory.showAddUpdateSavingsGoals();
                        }
                    }
                });
                
                tableRowMenu.getItems().addAll(editRowItem, deleteRowItem);
                
                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(tableRowMenu)
                                .otherwise((ContextMenu)null));
                return row;
            }
        });*/
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpSavingsGoalsTableView();
        populateSavingsGoalTableView();
    }
}
