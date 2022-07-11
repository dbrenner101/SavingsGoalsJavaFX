package com.brenner.savingsgoals.controller;

import com.brenner.savingsgoals.SavingsGoalManager;
import com.brenner.savingsgoals.model.DepositModel;
import com.brenner.savingsgoals.model.SavingsGoalModel;
import com.brenner.savingsgoals.service.model.Deposit;
import com.brenner.savingsgoals.service.model.SavingsGoalAllocation;
import com.brenner.savingsgoals.service.model.SavingsGoalDepositAllocation;
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

import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller to handle management of the allocation to each goal. When the scene is initialized each goal is allocated
 * it's expected weekly target amount. The default goal is incremented/decremented by the balance. The allocation column
 * is editable allowing for the default allocation amount to be changed. The default goal amount will update dynamically
 * as the allocation amounts are changed. Nothing is persisted until the save button is clicked.
 *
 * Relies on the allocationDepositView.fxml
 */
public class DepositAllocationController extends BaseController implements Initializable {
    
    @FXML private TableView<SavingsGoalModel> savingsGoalsTable;
    
    @FXML private TableColumn<SavingsGoalModel, String> allocatedAmountCol;
    
    @FXML private TableColumn<SavingsGoalModel, Float> currentBalanceCol;
    
    @FXML private TableColumn<SavingsGoalModel, Integer> daysCol;
    
    @FXML private TableColumn<SavingsGoalModel, Date> endDateCol;
    
    @FXML private TableColumn<SavingsGoalModel, Float> initialBalanceCol;
    
    @FXML private TableColumn<SavingsGoalModel, String> savingsGoalCol;
    
    @FXML private TableColumn<SavingsGoalModel, Float> savingsPerWeekCol;
    
    @FXML private TableColumn<SavingsGoalModel, Date> startDateCol;
    
    @FXML private TableColumn<SavingsGoalModel, Float> targetAmountCol;
    
    @FXML private TableColumn<SavingsGoalModel, Integer> weeksCol;
    
    @FXML private Label defaultGoalName;
    
    @FXML private Label defaultGoalAmount;
    
    @FXML private Label amountToAllocateLabel;
    
    @FXML private Label totalAllocatedToDefaultGoal;
    
    public DepositAllocationController(SavingsGoalManager savingsGoalManager, ViewFactory viewFactory, String fxmlName) {
        super(savingsGoalManager, viewFactory, fxmlName);
    }
    
    @FXML
    void allocatedAmountUpdated(ActionEvent event) {
        System.out.println("Allocation amount changed");
    }
    
    @FXML
    void saveAllocationAction(ActionEvent event) {
        List<SavingsGoalModel> modelList = this.savingsGoalsTable.getItems();
        List<SavingsGoalAllocation> allocations = new ArrayList<>(modelList.size());
        for(SavingsGoalModel modelGoal : modelList) {
            SavingsGoalAllocation allocation = new SavingsGoalAllocation(
                    modelGoal.getSavingsGoalId(),
                    new BigDecimal(modelGoal.getAllocatedAmount()));
            allocations.add(allocation);
        }
        SavingsGoalDepositAllocation depositAllocation = new SavingsGoalDepositAllocation();
        depositAllocation.setSavingsGoalAllocations(allocations);
        List<Deposit> depositsInAllocation = savingsGoalManager.getSelectedDepositsForAllocation()
                        .stream().map(d -> d.getDeposit()).collect(Collectors.toList());
        depositAllocation.setDeposits(depositsInAllocation);
        this.savingsGoalManager.allocateDepositToGoals(depositAllocation);
        
        this.viewFactory.showDepositsList();
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
        allocatedAmountCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SavingsGoalModel, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SavingsGoalModel, String> event) {
                SavingsGoalModel model = event.getRowValue();
                try {
                    updateDefaultGoalAmount(model.getAllocatedAmount(), event.getNewValue());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                model.setAllocatedAmount(event.getNewValue());
            }
        });
    }
    
    private void updateDefaultGoalAmount(String oldAmountStr, String newAmountStr) throws ParseException {
        BigDecimal oldAmount = new BigDecimal(oldAmountStr);
        BigDecimal newAmount = new BigDecimal(newAmountStr);
        BigDecimal diff = oldAmount.subtract(newAmount);
        String currentAmountStringCurrency = this.defaultGoalAmount.getText();
        Number currentAmountNumber = NumberFormat.getCurrencyInstance(Locale.US).parse(currentAmountStringCurrency).floatValue();
        BigDecimal currentDefaultAmount = BigDecimal.valueOf(currentAmountNumber.floatValue()).add(diff);
        this.defaultGoalAmount.setText(CommonUtils.formatAsCurrency(currentDefaultAmount.floatValue()));
    }
    
    private void populateSavingsGoalTableView() {
        this.savingsGoalsTable.setItems(null);
        List<SavingsGoalModel> savingsGoals = this.savingsGoalManager.getSavingsGoalsList(false);
        List<DepositModel> selectedDeposits = this.savingsGoalManager.getSelectedDepositsForAllocation();
        BigDecimal depositAmount = BigDecimal.valueOf(0);
        for (DepositModel depositModel : selectedDeposits){
            depositAmount = depositAmount.add(depositModel.getDeposit().getAmount());
        }
        this.amountToAllocateLabel.setText(CommonUtils.formatAsCurrency(depositAmount.floatValue()));
        BigDecimal remainingDeposit = depositAmount;
        for (SavingsGoalModel savingsGoal : savingsGoals) {
            BigDecimal goalWeeklyAmount = savingsGoal.getSavingsGoal().getSavingsPerWeek();
            if (goalWeeklyAmount == null) {
                goalWeeklyAmount = BigDecimal.valueOf(0);
            }
            savingsGoal.setAllocatedAmount(goalWeeklyAmount.toString());
            remainingDeposit = remainingDeposit.subtract(goalWeeklyAmount);
        }
        this.savingsGoalsTable.setItems(this.savingsGoalManager.getSavingsGoalsList(true));
        
        this.totalAllocatedToDefaultGoal.setText(CommonUtils.formatAsCurrency(remainingDeposit.floatValue()));
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpSavingsGoalsTableView();
        populateSavingsGoalTableView();
        SavingsGoalModel defaultGoal = this.savingsGoalManager.getDefaultGoal();
        this.defaultGoalName.setText(defaultGoal.getSavingsGoal().getGoalName());
        this.defaultGoalAmount.setText(CommonUtils.formatAsCurrency(defaultGoal.getSavingsGoal().getCurrentBalance().floatValue()));
    }
}
