package com.brenner.savingsgoals.controller;

import com.brenner.savingsgoals.SavingsGoalManager;
import com.brenner.savingsgoals.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEditTransactionController extends BaseController implements Initializable {
    
    @FXML
    private TextField amountTextField;
    
    @FXML
    private DatePicker datePicker;
    
    @FXML
    private ChoiceBox<?> fromGoalSelection;
    
    @FXML
    private TextArea notesTextArea;
    
    @FXML
    private Button saveButton;
    
    @FXML
    private ChoiceBox<?> toGoalSelection;
    
    public AddEditTransactionController(SavingsGoalManager savingsGoalManager, ViewFactory viewFactory, String fxmlName) {
        super(savingsGoalManager, viewFactory, fxmlName);
    }
    
    @FXML
    void saveButtonAction(ActionEvent event) {
    
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }
}
