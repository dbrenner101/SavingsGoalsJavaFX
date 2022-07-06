package com.brenner.savingsgoals;

import com.brenner.savingsgoals.view.ViewFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
    
    Stage window;
    Scene child1, child2;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;
    
        ViewFactory viewFactory = new ViewFactory(new SavingsGoalManager());
        viewFactory.showMainWindow();
        
    
        /*URL file = getClass().getResource("Parent.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(file);
        fxmlLoader.setController(new ParentController(window));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        window.setScene(scene);
        window.show();*/
    }
    
    public static void main(String[] args) {
        launch();
    }
}