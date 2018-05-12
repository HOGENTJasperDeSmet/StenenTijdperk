/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import static java.lang.Float.MAX_VALUE;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author jasperdesmet
 */
public class PopupOpslaan extends StackPane{
    private DomeinController dc;
    private VBox vbox;
    private Button opslaan, nietOpslaan;
    private TextField spelNaam;
    private Text tekst;
    public PopupOpslaan(DomeinController dc) {
        vbox = new VBox(8);
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setStyle("-fx-background-color: #ffffff");
        vbox.setMaxHeight(0);
        vbox.setMaxWidth(0);
        tekst = new Text("Geef een naam aan je spel voor het opteslaan.");
        tekst.setWrappingWidth(200);
        tekst.setTextAlignment(TextAlignment.CENTER);
        spelNaam = new TextField();
        opslaan = new Button("Opslaan");
        opslaan.setMaxWidth(MAX_VALUE);
        nietOpslaan = new Button("Niet opslaan");
        nietOpslaan.setMaxWidth(MAX_VALUE);
        vbox.getChildren().addAll(tekst,spelNaam,opslaan,nietOpslaan);
        opslaan.setOnMouseClicked((MouseEvent event) -> {
            dc.slaSpelOp(spelNaam.getText());
            Platform.exit();
        });
        nietOpslaan.setOnMouseClicked((MouseEvent event) -> {
            Platform.exit();
        });
        this.getChildren().add(vbox);
    }
}
