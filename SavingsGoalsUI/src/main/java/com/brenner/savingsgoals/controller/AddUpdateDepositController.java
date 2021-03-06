package com.brenner.savingsgoals.controller;

import com.brenner.savingsgoals.SavingsGoalManager;
import com.brenner.savingsgoals.model.DepositModel;
import com.brenner.savingsgoals.service.model.Deposit;
import com.brenner.savingsgoals.util.CommonUtils;
import com.brenner.savingsgoals.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Validator;

import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller to manage the add/update deposit scene. Includes a validator implementation that enforces required data
 * and data type.
 *
 * Relies on the addDeposit.fxml
 */
public class AddUpdateDepositController extends BaseController implements Initializable {
    
    Validator validator = new Validator();
    
    @FXML
    private DatePicker depositDatePicker;
    
    @FXML
    private TextField depositAmountField;
    
    @FXML
    private Button saveDepositButton;
    
    public AddUpdateDepositController(SavingsGoalManager savingsGoalManager, ViewFactory viewFactory, String fxmlName) {
        super(savingsGoalManager, viewFactory, fxmlName);
    }
    
    @FXML
    void saveDepositButtonAction(ActionEvent event) {
        validator.validate();
        
        if (! validator.containsErrors()) {
    
            Date depositDate = null;
            try {
                depositDate = CommonUtils.parseStdFormatDateString(depositDatePicker.getEditor().getText());
            } catch (ParseException e) {
                // eat it
            }
    
            BigDecimal depositAmount = new BigDecimal(depositAmountField.getText());
    
            Deposit deposit = new Deposit(depositAmount, depositDate);
            DepositModel selectedDepositModel = this.savingsGoalManager.getSelectedDepositModel();
            if (selectedDepositModel != null) {
                deposit.setDepositId(selectedDepositModel.getDeposit().getDepositId());
            }
            this.savingsGoalManager.saveDeposit(deposit);
    
            super.viewFactory.showDepositsList();
        }
    }
    
    private void buildValidator() {
    
        validator.createCheck()
                .withMethod(c -> {
                    String depositAmtStr = c.get("depositAmount");
                    if (depositAmtStr.trim().length() == 0) {
                        System.out.println("No DEPOSIT AMOUNT");
                        c.error("Deposit amount is required.");
                    } else {
                        try {
                            Float.valueOf(depositAmtStr);
                        } catch (NumberFormatException nfe) {
                            c.error("Deposit amount must be a number.");
                        }
                    }
                })
                .dependsOn("depositAmount", depositAmountField.textProperty())
                .decorates(depositAmountField);
    
        validator.createCheck()
                .withMethod(c -> {
                    String depositDate = c.get("depositDate");
                    if (depositDate.trim().length() == 0) {
                        c.error("Deposit date is required.");
                    }
                })
                .dependsOn("depositDate", depositDatePicker.getEditor().textProperty())
                .decorates(depositDatePicker.getEditor());
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (savingsGoalManager.getSelectedDepositModel() != null) {
            Deposit deposit = savingsGoalManager.getSelectedDepositModel().getDeposit();
            this.depositDatePicker.getEditor().setText(CommonUtils.formatDateToStdString(deposit.getDate()));
            this.depositAmountField.setText(deposit.getAmount().toString());
        }
        buildValidator();
    }
}
