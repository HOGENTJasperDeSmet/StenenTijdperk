/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import static java.lang.Float.MAX_VALUE;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author jasperdesmet
 */
public class SpelHervatten extends BorderPane {

    private DomeinController dc;
    private HoofdScherm hs;
    private ImageView spelText;
    private VBox spellenView;
    private String spelen[][];
    private Button spelStarten;
    private ScrollPane sp;

    public SpelHervatten(DomeinController dc, HoofdScherm hs) {

        spelen = dc.geefSpellen();
        spelText = new ImageView(new Image(getClass().getResourceAsStream("/assets/text.png")));
        spelText.setViewport(new Rectangle2D(0, 362, 343, 95));
        BorderPane.setAlignment(spelText, Pos.CENTER);
        BorderPane.setMargin(spelText, new Insets(20, 0, 0, 0));

        spellenView = new VBox();
        for (String[] spel : spelen) {
            spelStarten = new Button(spel[1]);
            spelStarten.setOnMouseClicked((MouseEvent event) -> {
                hs.startOpgeslagenSpel(Integer.parseInt(spel[0]));
            });
            spelStarten.setFont(Font.font("Karma future", 32));
            spelStarten.setMaxWidth(MAX_VALUE);
            spellenView.getChildren().add(spelStarten);
        }
        spellenView.setStyle("-fx-background-color: #ffffff");

        this.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                hs.startScherm(this);
            }
        });
        sp = new ScrollPane();
        sp.setMaxWidth(650);
        sp.setMaxHeight(350);
        sp.setFitToWidth(true);
        sp.setContent(spellenView);
        BorderPane.setAlignment(spellenView, Pos.TOP_CENTER);
        BorderPane.setMargin(spellenView, new Insets(20, 0, 0, 0));
        this.setCenter(sp);
        this.setTop(spelText);
    }
}
