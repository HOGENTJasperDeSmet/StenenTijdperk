/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 *
 * @author jasperdesmet
 */
public class MeldingPopup extends StackPane{
    private Text melding;
    private SpelBord sp;
    private PauseTransition delay;
    private boolean duration;
    public MeldingPopup(String text,SpelBord sp){
        melding = new Text(text);
        this.sp = sp;
        
        this.getChildren().add(melding);
        this.setStyle("-fx-background-color: #e33244;-fx-border-radius: 4; -fx-background-radius: 4;");
        this.setPadding(new Insets(5));
        this.setMaxWidth(0);
        this.setMaxHeight(0);
        this.setAlignment(Pos.CENTER);
        melding.setWrappingWidth(250);
        melding.setTextAlignment(TextAlignment.CENTER);
        melding.setStyle("-fx-fill: white; -fx-font-size: 16px;");
        
        
        
        FadeTransition ft = new FadeTransition(Duration.millis(200), this);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
        
        delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> {
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.play();
            ft.setOnFinished( event2 ->  sp.getChildren().remove(this));
        });
        ft.setOnFinished( event ->  delay.play());
    }
    public MeldingPopup(String text,SpelBord sp,boolean kleur){
        this(text,sp);
        this.setStyle("-fx-background-color: #28a745;-fx-border-radius: 4; -fx-background-radius: 4;");
    }

    public MeldingPopup(String text, SpelBord sp, boolean kleur, boolean indefinte) {
        this(text,sp,kleur);
        delay.setOnFinished(null);
        duration = indefinte;
    }
    public boolean getDuration(){
        return duration;
    }
}
