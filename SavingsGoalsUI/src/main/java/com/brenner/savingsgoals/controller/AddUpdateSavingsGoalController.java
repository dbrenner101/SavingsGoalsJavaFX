package com.brenner.savingsgoals.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

import com.brenner.savingsgoals.SavingsGoalManager;
import com.brenner.savingsgoals.model.SavingsGoalModel;
import com.brenner.savingsgoals.service.model.SavingsGoal;
import com.brenner.savingsgoals.util.CommonUtils;
import com.brenner.savingsgoals.view.ViewFactory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import net.synedra.validatorfx.Validator;

/**
 * Controller to handle the add/update scene and behavior. Includes a validator implementation that enforces required data
 * and data type.
 *
 * Relies on the addSavingsGoal.fxml
 */
public class AddUpdateSavingsGoalController extends BaseController implements Initializable {
    
    Validator validator = new Validator();
    
    @FXML
    private Label currentBalanceErrorLabel;
    
    @FXML
    private TextField currentBalanceTextField;
    
    @FXML
    private Label endDateErrorLabel;
    
    @FXML
    private DatePicker endDatePicker;
    
    @FXML
    private Label goalNameErrorLabel;
    
    @FXML
    private TextField goalNameTextField;
    
    @FXML
    private Label initialAmountErrorLabel;
    
    @FXML
    private TextField initialAmountTextField;
    
    @FXML
    private TextArea notesTextArea;
    
    @FXML
    private Button saveButton;
    
    @FXML
    private Label startDateErrorLabel;
    
    @FXML
    private DatePicker startDatePicker;
    
    @FXML
    private Label targetAmountErrorLabel;
    
    @FXML
    private TextField targetAmountTextField;
    
    public AddUpdateSavingsGoalController(SavingsGoalManager savingsGoalManager, ViewFactory viewFactory, String fxmlName) {
        super(savingsGoalManager, viewFactory, fxmlName);
    }
    
    private void buildValidator() {
        validator.createCheck().dependsOn("goalName", goalNameTextField.textProperty())
                .withMethod(c -> {
                    String goalName = c.get("goalName");
                    if (goalName.trim().length() == 0) {
                        c.error("A goal name is required.");
                    }
                })
                .decorates(goalNameTextField);
        
        
        validator.createCheck().dependsOn("startDate", startDatePicker.getEditor().textProperty())
                .withMethod(c -> {
                    String startDateStr = c.get("startDate");
                    if (startDateStr.trim().length() == 0) {
                        c.error("A start date is required.");
                    }
                    else {
                        try {
                            CommonUtils.parseStdFormatDateString(startDateStr);
                        } catch (ParseException e) {
                            c.error("Please enter a valid start date (MM/dd/yyyy).");
                        }
                    }
                })
                .decorates(startDatePicker.getEditor());
    
        validator.createCheck().dependsOn("endDate", endDatePicker.getEditor().textProperty())
                .withMethod(c -> {
                    String endDateStr = c.get("endDate");
                    if (endDateStr.trim().length() == 0) {
                        c.error("An end date is required.");
                    }
                    else {
                        try {
                            CommonUtils.parseStdFormatDateString(endDateStr);
                        } catch (ParseException e) {
                            c.error("Please enter a valid end date (MM/dd/yyyy).");
                        }
                    }
                })
                .decorates(endDatePicker.getEditor());
        
        validator.createCheck().dependsOn("targetAmount", targetAmountTextField.textProperty())
                .withMethod(c -> {
                    String targetAmountStr = c.get("targetAmount");
                    if (targetAmountStr.trim().length() == 0) {
                        c.error("Target amount is required");
                    } else {
                        try {
                            Float.parseFloat(targetAmountStr);
                        } catch (NumberFormatException nfe) {
                            c.error("Target amount must be a number.");
                        }
                    }
                })
                .decorates(targetAmountTextField);
        
        validator.createCheck().dependsOn("initialAmount", initialAmountTextField.textProperty())
                .withMethod(c -> {
                    String initialAmountStr = c.get("initialAmount");
                    if (initialAmountStr.trim().length() > 0) {
                        try {
                            Float.parseFloat(initialAmountStr);
                        } catch (NumberFormatException nfe) {
                            c.error("Initial amount must be a number.");
                        }
                    }
                })
                .decorates(initialAmountTextField);
    
        validator.createCheck().dependsOn("currentAmount", currentBalanceTextField.textProperty())
                .withMethod(c -> {
                    String currentAmountStr = c.get("currentAmount");
                    if (currentAmountStr.trim().length() > 0) {
                        try {
                            Float.parseFloat(currentAmountStr);
                        } catch (NumberFormatException nfe) {
                            c.error("Current amount must be a number.");
                        }
                    }
                })
                .decorates(currentBalanceTextField);
        
        
    }
    
    @FXML
    void saveButtonClicked(ActionEvent event) {
        validator.validate();
        submitForm();
    }
    
    private void submitForm() {
        this.goalNameErrorLabel.setText("");
        this.startDateErrorLabel.setText("");
        this.endDateErrorLabel.setText("");
        this.targetAmountErrorLabel.setText("");
        this.initialAmountErrorLabel.setText("");
        this.currentBalanceErrorLabel.setText("");
    
        if (! validator.containsErrors()) {
            SavingsGoal savingsGoal = buildDataObject();
            savingsGoalManager.saveSavingsGoal(savingsGoal);
            viewFactory.showSavingsGoalList();
        }
    }
    
    private SavingsGoal buildDataObject() {
        SavingsGoal savingsGoal = null;
        if (this.savingsGoalManager.getSelectedSavingsGoalIndex() != null) {
            savingsGoal = this.savingsGoalManager.getSavingsGoalsList(true).get(this.savingsGoalManager.getSelectedSavingsGoalIndex()).getSavingsGoal();
        }
        else {
            savingsGoal = new SavingsGoal();
        }
    
        try {
            savingsGoal.setSavingsStartDate(CommonUtils.parseStdFormatDateString(this.startDatePicker.getEditor().getText()));
            savingsGoal.setSavingsEndDate(CommonUtils.parseStdFormatDateString(this.endDatePicker.getEditor().getText()));
        } catch (ParseException pe) {
            //check for in validation step
        }
        savingsGoal.setGoalName(this.goalNameTextField.getText());
        savingsGoal.setTargetAmount(new BigDecimal(this.targetAmountTextField.getText()));
        savingsGoal.setNotes(this.notesTextArea.getText());
    
        if (this.currentBalanceTextField.getText() != null && this.currentBalanceTextField.getText().trim().length() > 0) {
            savingsGoal.setCurrentBalance(new BigDecimal(this.currentBalanceTextField.getText()));
        }
        if (this.initialAmountTextField.getText() != null && this.initialAmountTextField.getText().trim().length() > 0) {
            savingsGoal.setInitialBalance(new BigDecimal(this.initialAmountTextField.getText()));
        }
        
        return savingsGoal;
    }
    
    @Override
    public void postInitialization() {
        super.postInitialization();
        Scene scene = this.endDateErrorLabel.getScene();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    submitForm();
                }
            }
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        if (this.savingsGoalManager.getSelectedSavingsGoalIndex() != null) {
            SavingsGoalModel selectedItem = this.savingsGoalManager.getSavingsGoalsList(true).get(this.savingsGoalManager.getSelectedSavingsGoalIndex());
            SavingsGoal goal = selectedItem.getSavingsGoal();
            this.goalNameTextField.setText(goal.getGoalName());
            this.initialAmountTextField.setText(goal.getInitialBalance() != null ? goal.getInitialBalance().toString() : "");
            this.currentBalanceTextField.setText(goal.getCurrentBalance() != null ? goal.getCurrentBalance().toString(): "");
            this.targetAmountTextField.setText(goal.getTargetAmount() != null ? goal.getTargetAmount().toString() : "");
            this.notesTextArea.setText(selectedItem.getNotes());
            this.startDatePicker.getEditor().setText(CommonUtils.formatDateToStdString(goal.getSavingsStartDate()));
            this.endDatePicker.getEditor().setText(CommonUtils.formatDateToStdString(goal.getSavingsEndDate()));
        }
        
        buildValidator();
    }
}
