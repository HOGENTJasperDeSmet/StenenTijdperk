/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import static java.lang.Float.MAX_VALUE;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author jasperdesmet
 */
public class PopUpVoedsel extends StackPane{
    private DomeinController dc;
    private SpelBord sp;
    private VBox vbox;
    private Button grondstoffen, punten;
    private Text melding;
    public PopUpVoedsel(DomeinController dc, SpelBord sp){
        this.dc = dc;
        this.sp = sp;
        
        vbox = new VBox(8);
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setStyle("-fx-background-color: #ffffff");
        vbox.setMaxHeight(0);
        vbox.setMaxWidth(0);
        melding = new Text("Je hebt niet genoeg Voedsel om je stamleden te voeden");
        melding.setWrappingWidth(200);
        melding.setTextAlignment(TextAlignment.CENTER);
        punten = new Button("betalen met punten");
        punten.setMaxWidth(MAX_VALUE);
        grondstoffen = new Button("betalen met grondstoffen");
        grondstoffen.setMaxWidth(MAX_VALUE);
        vbox.getChildren().addAll(melding,punten,grondstoffen);
        this.getChildren().add(vbox);
        punten.setOnMouseClicked((MouseEvent event) -> {
           dc.voedStamledenSpelerAanZet(2);
           dc.volgendeSpeler();
           sp.setSpelerAanZetNummer(dc.geefSpelerAanZetSpelerNummer());
           sp.getChildren().remove(this);
           sp.voedselFase();
        });
        grondstoffen.setOnMouseClicked((MouseEvent event) -> {
           dc.voedStamledenSpelerAanZet(0);
        });
    }
}
