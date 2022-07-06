package com.brenner.savingsgoals.controller;

import com.brenner.savingsgoals.SavingsGoalManager;
import com.brenner.savingsgoals.view.ViewFactory;
import javafx.fxml.Initializable;

public abstract class BaseController implements Initializable {
    
    protected SavingsGoalManager savingsGoalManager;
    protected ViewFactory viewFactory;
    
    private final String fxmlName;
    
    public BaseController(SavingsGoalManager savingsGoalManager, ViewFactory viewFactory, String fxmlName) {
        this.savingsGoalManager = savingsGoalManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }
    
    public String getFxmlName() {
        return fxmlName;
    }
    
    public void postInitialization() {}
}
