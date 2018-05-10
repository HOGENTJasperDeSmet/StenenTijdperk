/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author jasperdesmet
 */
public class Highscores extends BorderPane{
    private DomeinController dc;
    private HoofdScherm hs;
    private ImageView highscoreText;
    private GridPane scoreView;
    private String scores[][];
            //{{"jasper","99"},{"jasper","99"},{"jasper","99"},{"jasper","99"},{"jasper","99"},{"jasper","99"},{"jasper","99"},{"jasper","99"},{"jasper","99"},{"jasper","99"},{"jasper","99"},{"jasper","99"}};
    private Text waarde;
    private ScrollPane sp;
    public Highscores(DomeinController dc, HoofdScherm hs){
        
        scores = dc.geefHighScores();
        highscoreText = new ImageView(new Image(getClass().getResourceAsStream("/assets/text.png")));
        highscoreText.setViewport(new Rectangle2D(0,267,509,95));
        BorderPane.setAlignment(highscoreText, Pos.CENTER);
        BorderPane.setMargin(highscoreText, new Insets(20, 0, 0, 0));
        
        
        scoreView = new GridPane();
        
        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        ColumnConstraints column3 = new ColumnConstraints();
        column2.setHgrow(Priority.ALWAYS);
        scoreView.getColumnConstraints().addAll(column1,column2,column3);
        scoreView.setStyle("-fx-background-color: #ffffff");
        int i = 1;
        int c = 1;
        for(String score[]:scores){
            waarde = new Text(i+".");
            waarde.setFont(Font.font("Karma future", 32));
            scoreView.add(waarde, 0, i-1);
            GridPane.setMargin(waarde,new Insets(2, 20, 2, 20));
            for(String value:score){
                waarde = new Text(value);
                waarde.setFont(Font.font("Karma future", 32));
                scoreView.add(waarde, c, i-1);
                GridPane.setMargin(waarde,new Insets(2, 20, 2, 20));
                c++;
            }
            c = 1;
            i++;
        }
        this.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                hs.startScherm(this);
            }
        });
        sp = new ScrollPane();
        sp.setMaxWidth(650);
        sp.setMaxHeight(350);
        sp.setFitToWidth(true);
        sp.setContent(scoreView);
        BorderPane.setAlignment(scoreView, Pos.TOP_CENTER);
        BorderPane.setMargin(scoreView, new Insets(20, 0, 0, 0));
        this.setCenter(sp);
        this.setTop(highscoreText);
    }
}
