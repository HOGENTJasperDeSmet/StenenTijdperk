 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import domein.DomeinController;
import gui.StartScherm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Jasper
 */
public class HetStenenTijdperkFX extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        DomeinController dc = new DomeinController();
        StartScherm root = new StartScherm(primaryStage,dc);
        
        Scene scene = new Scene(root, 960, 540);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Stenen tijdperk"); 
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
