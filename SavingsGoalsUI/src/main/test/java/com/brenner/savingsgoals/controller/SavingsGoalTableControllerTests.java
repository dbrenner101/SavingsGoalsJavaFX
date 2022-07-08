package com.brenner.savingsgoals.controller;

import com.brenner.savingsgoals.SavingsGoalManager;
import com.brenner.savingsgoals.view.ViewFactory;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

public class SavingsGoalTableControllerTests {
    
    @Mock
    private ViewFactory viewFactoryMock;
    
    @Mock
    private SavingsGoalManager savingsGoalManagerMock;
    
    private SavingsGoalTableController savingsGoalTableController;
    
    @BeforeAll
    public static void setUpToolkit() {
        Platform.startup(() -> System.out.println("Toolkit initialized ..."));
    }
    
    @BeforeEach
    public void setUp() {
        initMocks(this);
        savingsGoalTableController = new SavingsGoalTableController(savingsGoalManagerMock, viewFactoryMock, "");
    }
    
}
