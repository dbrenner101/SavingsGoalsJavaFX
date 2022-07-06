package com.brenner.savingsgoals.controller;

import com.brenner.savingsgoals.SavingsGoalManager;
import com.brenner.savingsgoals.model.DepositModel;
import com.brenner.savingsgoals.model.SavingsGoalModel;
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
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class MainWindowControllerOld extends BaseController implements Initializable {
    
    private static final SimpleDateFormat COL_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    
    private Control visibleControl;
    
    @FXML
    private TableColumn<DepositModel, Float> depositAmountCol;
    
    @FXML
    private TableColumn<DepositModel, Date> depositDateCol;
    
    @FXML
    private TableView<DepositModel> depositsView;
    
    @FXML
    private TableView<SavingsGoalModel> savingsGoalsTable;
    
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
    
    
    public MainWindowControllerOld(SavingsGoalManager savingsGoalManager, ViewFactory viewFactory, String fxmlName) {
        super(savingsGoalManager, viewFactory, fxmlName);
    }
    
    private class ColumnFormatter<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
        private final Format format;
        
        public ColumnFormatter(Format format) {
            super();
            this.format = format;
        }
        @Override
        public TableCell<S, T> call(TableColumn<S, T> arg0) {
            return new TableCell<S, T>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(new Label(format.format(item)));
                    }
                }
            };
        }
    }
    
    private void setUpDepositTableView() {
        depositAmountCol.setCellValueFactory(new PropertyValueFactory<DepositModel, Float>("depositAmountProp"));
        depositDateCol.setCellValueFactory(new PropertyValueFactory<DepositModel, Date>("depositDateProp"));
        
        depositDateCol.setCellFactory(new ColumnFormatter<DepositModel, Date>(MainWindowControllerOld.COL_DATE_FORMAT));
    
        //this.depositsView.setItems(null);
        this.savingsGoalManager.retrieveDeposits();;
        this.depositsView.setItems(this.savingsGoalManager.getDepositsList());
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
    
        startDateCol.setCellFactory(new ColumnFormatter<SavingsGoalModel, Date>(MainWindowControllerOld.COL_DATE_FORMAT));
        endDateCol.setCellFactory(new ColumnFormatter<SavingsGoalModel, Date>(MainWindowControllerOld.COL_DATE_FORMAT));
    }
    
    private void populateSavingsGoalTableView() {
        this.savingsGoalsTable.setItems(null);
        this.savingsGoalManager.retrieveSavingsGoals();
        this.savingsGoalsTable.setItems(this.savingsGoalManager.getSavingsGoalsList());
        
        this.savingsGoalsTable.setRowFactory(new Callback<TableView<SavingsGoalModel>, TableRow<SavingsGoalModel>>() {
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
        });
    }
    
    
    /* MENU ACTIONS */
    @FXML
    void addSavingsGoalMenuAction(ActionEvent event) {
        viewFactory.showAddUpdateSavingsGoals();
    }
    
    @FXML
    void listSavingsGoalMenuItemAction(ActionEvent event) {
        manageControlVisibility(this.savingsGoalsTable);
    }
    
    @FXML
    void ManageTransactionMenuItemAction(ActionEvent event) {
        System.out.println("Manage Transactions Menu Item Selected");
    }
    
    @FXML
    void addDepositMenuItemAction(ActionEvent event) {
        viewFactory.showAddUpdateDeposits();
    }
    
    @FXML
    void manageDepositsMenuItemAction(ActionEvent event) {
        setUpDepositTableView();
        manageControlVisibility(this.depositsView);
    }
    
    private void manageControlVisibility(Control controlToView) {
        if (this.visibleControl != null) {
            if (this.visibleControl.equals(this.savingsGoalsTable)) {
                this.visibleControl.setDisable(true);
            }
            else {
                this.visibleControl.setVisible(false);
            }
            if (controlToView.equals(this.savingsGoalsTable)) {
                controlToView.setDisable(false);
            }
            else {
                controlToView.setVisible(true);
            }
            this.visibleControl = controlToView;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpSavingsGoalsTableView();
        populateSavingsGoalTableView();
    }
    
    @Override
    public void postInitialization() {
        this.visibleControl = this.savingsGoalsTable;
        this.savingsGoalsTable.setVisible(true);
    }
}
