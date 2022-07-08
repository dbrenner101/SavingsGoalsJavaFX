package com.brenner.savingsgoals.controller;

import com.brenner.savingsgoals.SavingsGoalManager;
import com.brenner.savingsgoals.model.DepositModel;
import com.brenner.savingsgoals.view.ViewFactory;
import javafx.application.Platform;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import java.util.Date;

import static org.mockito.MockitoAnnotations.initMocks;

public class DepositsListControllerTests {
    
    @Mock
    private ViewFactory viewFactoryMock;
    
    @Mock
    private SavingsGoalManager savingsGoalManagerMock;
    
    private DepositsListController controller;
    
    private TableColumn<DepositModel, Float> depositAmountCol;
    
    private TableColumn<DepositModel, Date> depositDateCol;
    
    private TableView<DepositModel> depositsView;
    
    @BeforeAll
    public static void setUpToolkit() {
        Platform.startup(() -> System.out.println("Toolkit initialized ..."));
    }
    
    @BeforeEach
    public void setUp() {
        initMocks(this);
        controller = new DepositsListController(savingsGoalManagerMock, viewFactoryMock, "",
                depositAmountCol, depositDateCol, depositsView);
    }
}
