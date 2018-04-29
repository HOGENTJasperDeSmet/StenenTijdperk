/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author jasperdesmet
 */
public class EindScore extends StackPane{
    private Text eindwinnaar;
    private DomeinController dc;
    public EindScore(DomeinController dc, String eindwinnaarNaam){
        eindwinnaar = new Text("De eindwinnaar is " + eindwinnaarNaam);
        eindwinnaar.setFont(Font.font("Karma future", 36));
        this.setStyle("-fx-background-color: #ffffff");
        this.getChildren().add(eindwinnaar);
    }
}
