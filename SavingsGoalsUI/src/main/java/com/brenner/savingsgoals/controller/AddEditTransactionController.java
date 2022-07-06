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

import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;

public class AddEditTransactionController extends BaseController implements Initializable {
    
    @FXML
    private TextField amountTextField;
    
    @FXML
    private DatePicker datePicker;
    
    @FXML
    private ChoiceBox<SavingsGoalModel> fromGoalSelection;
    
    @FXML
    private TextArea notesTextArea;
    
    @FXML
    private Button saveButton;
    
    @FXML
    private ChoiceBox<SavingsGoalModel> toGoalSelection;
    
    public AddEditTransactionController(SavingsGoalManager savingsGoalManager, ViewFactory viewFactory, String fxmlName) {
        super(savingsGoalManager, viewFactory, fxmlName);
    }
    
    @FXML
    void saveButtonAction(ActionEvent event) throws ParseException {
        Date transactionDate = CommonUtils.parseStdFormatDateString(this.datePicker.getEditor().getText());
        SavingsGoal fromGoal = fromGoalSelection.getSelectionModel().getSelectedItem().getSavingsGoal();
        SavingsGoal toGoal = toGoalSelection.getSelectionModel().getSelectedItem().getSavingsGoal();
        Float amount = Float.valueOf(amountTextField.getText());
        String notes = notesTextArea.getText();
        
        Transaction t = new Transaction(null, transactionDate, fromGoal, toGoal, amount, notes);
        this.savingsGoalManager.addTransaction(t);
        
        viewFactory.showTransactionList();
    }
    
    private void initChoiceBoxes() {
        this.fromGoalSelection.setItems(super.savingsGoalManager.getSavingsGoalsList());
        this.toGoalSelection.setItems(super.savingsGoalManager.getSavingsGoalsList());
        
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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initChoiceBoxes();
    }
}
