package application;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * 
 */
public class QueuingSystem extends Application {
    
    private SimulatorGuiController controller;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/simulatorGui.fxml"));
        Parent root = loader.load();
        controller = (SimulatorGuiController)loader.getController();
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setTitle("MM1 Queueing System Simulator");
        primaryStage.sizeToScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}