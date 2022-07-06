package com.brenner.savingsgoals.controller;

import com.brenner.savingsgoals.SavingsGoalManager;
import com.brenner.savingsgoals.controller.service.model.Deposit;
import com.brenner.savingsgoals.util.CommonUtils;
import com.brenner.savingsgoals.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Validator;

import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;

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
    
            Float depositAmount = Float.valueOf(depositAmountField.getText());
    
            Deposit deposit = new Deposit(depositAmount, depositDate);
            this.savingsGoalManager.addDeposit(deposit);
    
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
        buildValidator();
    }
}
