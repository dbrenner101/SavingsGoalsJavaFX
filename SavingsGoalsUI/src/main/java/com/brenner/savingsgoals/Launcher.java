package com.brenner.savingsgoals;

import com.brenner.savingsgoals.view.ViewFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Launch point for the application
 */
public class Launcher extends Application {
    
    Stage window;
    Scene child1, child2;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;
    
        ViewFactory viewFactory = new ViewFactory(new SavingsGoalManager());
        viewFactory.showMainWindow();
    }
    
    public static void main(String[] args) {
        launch();
    }
}