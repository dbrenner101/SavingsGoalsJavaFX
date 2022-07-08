package com.brenner.savingsgoals.controller;

import com.brenner.savingsgoals.SavingsGoalManager;
import com.brenner.savingsgoals.model.SavingsGoalModel;
import com.brenner.savingsgoals.service.model.SavingsGoal;
import com.brenner.savingsgoals.service.model.Transaction;
import com.brenner.savingsgoals.util.CommonUtils;
import com.brenner.savingsgoals.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import net.synedra.validatorfx.Validator;

import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller to handle the add (edit) transaction.
 *
 * Relies on the addEditTransaction.fxml
 */
public class AddEditTransactionController extends BaseController implements Initializable {
    
    Validator validator = new Validator();
    
    @FXML private TextField amountTextField;
    
    @FXML private DatePicker datePicker;
    
    @FXML private ChoiceBox<SavingsGoalModel> fromGoalSelection;
    
    @FXML private TextArea notesTextArea;
    
    @FXML private Button saveButton;
    
    @FXML private ChoiceBox<SavingsGoalModel> toGoalSelection;
    
    public AddEditTransactionController(SavingsGoalManager savingsGoalManager, ViewFactory viewFactory, String fxmlName) {
        super(savingsGoalManager, viewFactory, fxmlName);
    }
    
    /**
     * Save button action checks the validity of the input and if valid sends the data to the SavingsGoalManager
     * for processing. The view is directed back to the transactions list after submission.
     *
     * @param event The action event
     * @throws ParseException
     */
    @FXML
    void saveButtonAction(ActionEvent event) throws ParseException {
        validator.validate();
        
        if (! validator.containsErrors()) {
            Date transactionDate = CommonUtils.parseStdFormatDateString(this.datePicker.getEditor().getText());
            SavingsGoal fromGoal = fromGoalSelection.getSelectionModel().getSelectedItem().getSavingsGoal();
            SavingsGoal toGoal = toGoalSelection.getSelectionModel().getSelectedItem().getSavingsGoal();
            BigDecimal amount = new BigDecimal(amountTextField.getText());
            String notes = notesTextArea.getText();
            
            Transaction t = new Transaction(null, transactionDate, fromGoal, toGoal, amount, notes);
            this.savingsGoalManager.addTransaction(t);
            
            viewFactory.showTransactionList();
        }
    }
    
    /**
     * Method initializes the choiceboxes with the SavingsGoalsList held on the SavingsGoalManager.
     */
    private void initChoiceBoxes() {
        this.fromGoalSelection.setItems(super.savingsGoalManager.getSavingsGoalsList(true));
        this.toGoalSelection.setItems(super.savingsGoalManager.getSavingsGoalsList(true));
        
        this.fromGoalSelection.setConverter(new StringConverter<SavingsGoalModel>() {
            @Override
            public String toString(SavingsGoalModel object) {
                return object != null ? object.getSavingsGoal().getGoalName() : "";
            }
    
            @Override
            public SavingsGoalModel fromString(String string) {
                return null;
            }
        });
    
        this.toGoalSelection.setConverter(new StringConverter<SavingsGoalModel>() {
            @Override
            public String toString(SavingsGoalModel object) {
                return object != null ? object.getSavingsGoal().getGoalName() : "";
            }
        
            @Override
            public SavingsGoalModel fromString(String string) {
                return null;
            }
        });
    }
    
    /**
     * Builds the validation rules using ValidatorFX
     */
    private void buildValidation() {
        validator.createCheck().dependsOn("fromGoal", fromGoalSelection.valueProperty())
                .withMethod(c -> {
                    SavingsGoalModel selectedItem = c.get("fromGoal");
                    if (selectedItem == null) {
                        c.error("From goal is required.");
                    }
                }).decorates(fromGoalSelection);
    
        validator.createCheck().dependsOn("toGoal", toGoalSelection.valueProperty())
                .withMethod(c -> {
                    SavingsGoalModel selectedItem = c.get("toGoal");
                    if (selectedItem == null) {
                        c.error("To goal is required.");
                    }
                }).decorates(toGoalSelection);
        
        validator.createCheck().dependsOn("date", datePicker.getEditor().textProperty())
                .withMethod(c -> {
                    String date = c.get("date");
                    if (date.trim().length() == 0) {
                        c.error("Transaction date is required.");
                    }
                    try {
                        CommonUtils.parseStdFormatDateString(date);
                    } catch (ParseException pe) {
                        c.error("A valid transaction date is required.");
                    }
                }).decorates(datePicker.getEditor());
        
        validator.createCheck().dependsOn("amount", amountTextField.textProperty())
                .withMethod(c -> {
                    String amount = c.get("amount");
                    if (amount.trim().length() == 0) {
                        c.error("Amount is required.");
                    }
                    try {
                        Float.parseFloat(amount);
                    } catch (NumberFormatException nfe) {
                        c.error("A valid number is required for amount.");
                    }
                }).decorates(amountTextField);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initChoiceBoxes();
        buildValidation();
    }
}
