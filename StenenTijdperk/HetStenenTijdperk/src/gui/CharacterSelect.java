/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.Arrays;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class CharacterSelect extends StackPane{
    private ImageView krillinGezicht, gokuGezicht, jackieChunGezicht, chiaotzuGezicht,
            infoTekst, startenTekst;
    private Image characterSelectSpriteSheet = new Image(getClass().getResourceAsStream("/assets/characterSelectSprites.png"));
    private VBox goku, krillin, jackieChun, chiaotzu;
    private GridPane gp = new GridPane();
    private int aantalSpelers;
    private TextField naamKrillin,naamGoku,naamJackieChun, naamChiaotzu;
    private boolean characters[] = new boolean[4], startFlag = false;
    private SpelBord spelbord;
    private DomeinController dc;
    private HoofdScherm hs;
    public CharacterSelect(DomeinController dc,HoofdScherm hs) {
        this.dc = dc;
        this.hs = hs;
        buildGui();
    }
    
    private void buildGui() {
        gp.setHgap(30);
        
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500));
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
        
        //sprites gezichten
        krillinGezicht = new ImageView(characterSelectSpriteSheet);
        krillinGezicht.setViewport(new Rectangle2D(283, 0, 283, 201));
        gokuGezicht = new ImageView(characterSelectSpriteSheet);
        gokuGezicht.setViewport(new Rectangle2D(0, 0, 283, 201));
        gokuGezicht.setScaleX(-1);
        jackieChunGezicht = new ImageView(characterSelectSpriteSheet);
        jackieChunGezicht.setViewport(new Rectangle2D(0, 201, 283, 201));
        jackieChunGezicht.setScaleX(-1);
        chiaotzuGezicht = new ImageView(characterSelectSpriteSheet);
        chiaotzuGezicht.setViewport(new Rectangle2D(283, 201, 283, 201));
        
        //sprites texten
        infoTekst = new ImageView(characterSelectSpriteSheet);
        infoTekst.setViewport(new Rectangle2D(0, 402, 468, 43));
        startenTekst = new ImageView(characterSelectSpriteSheet);
        startenTekst.setViewport(new Rectangle2D(1, 446, 123, 43));
        infoTekst.setTranslateY(20); 
        startenTekst.setTranslateY(20); 
        
        startenTekst.setOnMouseEntered((MouseEvent event) -> {
            this.setCursor(Cursor.HAND);
        });
        startenTekst.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT);
        });
        startenTekst.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT);
        });
        startenTekst.setOnMouseClicked((MouseEvent event) -> {
            int index = 0;
            String[][] namen = new String[aantalSpelers][2];
            if(!naamKrillin.getText().equals("")){
                namen[index][0] = naamKrillin.getText();
                namen[index][1] = "krillin";
                index++;
            }
            if(!naamGoku.getText().equals("")){
                namen[index][0] = naamGoku.getText();
                namen[index][1] = "goku";
                index++;
            }
            if(!naamChiaotzu.getText().equals("")){
                namen[index][0] = naamChiaotzu.getText();
                namen[index][1] = "chiaotzu";
                index++;
            }
            if(!naamJackieChun.getText().equals("")){
                namen[index][0] = naamJackieChun.getText();
                namen[index][1] = "jackieChun";
                index++;
            }
            dc.startSpel(namen);
            
            hs.startSpel();
            
        });
        startenTekst.setPickOnBounds(true);
        
        //textbox
        naamKrillin = new TextField();
        naamGoku = new TextField();
        naamJackieChun= new TextField();
        naamChiaotzu= new TextField();
        naamKrillin.setMaxWidth(284);
        naamGoku.setMaxWidth(284);
        naamJackieChun.setMaxWidth(284);
        naamChiaotzu.setMaxWidth(284);
        naamKrillin.setAlignment(Pos.CENTER);
        naamGoku.setAlignment(Pos.CENTER);
        naamJackieChun.setAlignment(Pos.CENTER);
        naamChiaotzu.setAlignment(Pos.CENTER);
        naamKrillin.setFont(Font.font("Karma future",28));
        naamGoku.setFont(Font.font("Karma future",28));
        naamJackieChun.setFont(Font.font("Karma future",28));
        naamChiaotzu.setFont(Font.font("Karma future",28));
        //Hboxes
        goku = new VBox(gokuGezicht,naamGoku);
        krillin = new VBox(krillinGezicht,naamKrillin);
        jackieChun = new VBox(jackieChunGezicht,naamJackieChun);
        chiaotzu = new VBox(chiaotzuGezicht,naamChiaotzu);
        goku.setAlignment(Pos.CENTER);
        krillin.setAlignment(Pos.CENTER);
        jackieChun.setAlignment(Pos.TOP_CENTER);
        chiaotzu.setAlignment(Pos.TOP_CENTER);
        
        GridPane.setHgrow(goku, Priority.ALWAYS);
        GridPane.setVgrow(goku, Priority.ALWAYS);
        GridPane.setHgrow(krillin, Priority.ALWAYS);
        GridPane.setVgrow(krillin, Priority.ALWAYS);
        GridPane.setHgrow(jackieChun, Priority.ALWAYS);
        GridPane.setVgrow(jackieChun, Priority.ALWAYS);
        GridPane.setHgrow(chiaotzu, Priority.ALWAYS);
        GridPane.setVgrow(chiaotzu, Priority.ALWAYS);
        naamGoku.textProperty().addListener((obs,oldText, newText) -> {
            if(!"".equals(newText)){
                characters[0] = true;
            } else {
                characters[0] = false;
            }
            startKnop();
        });
        naamKrillin.textProperty().addListener((obs,oldText, newText) -> {
            if(!"".equals(newText)){
                characters[1] = true;
            } else {
                characters[1] = false;
            }
            startKnop();
        });
        naamChiaotzu.textProperty().addListener((obs,oldText, newText) -> {
            if(!"".equals(newText)){
                characters[2] = true;
            } else {
                characters[2] = false;
            }
            startKnop();
        });
        naamJackieChun.textProperty().addListener((obs,oldText, newText) -> {
            if(!"".equals(newText)){
                characters[3] = true;
            } else {
                characters[3] = false;
            }
            startKnop();
        });
        gp.add(krillin, 0, 0);
        gp.add(goku, 1, 0);
        gp.add(chiaotzu, 0, 1);
        gp.add(jackieChun, 1, 1);
        getChildren().add(gp);
        getChildren().add(infoTekst);
        
        
    }
    public void startKnop(){
        boolean oudeFlag = startFlag;
        aantalSpelers = 0;
        for (int i = 0;i < characters.length; i++){
            if(characters[i]){
                aantalSpelers++;
            }
        }
        if(aantalSpelers > 1){
            startFlag = true;
        } else {
            startFlag = false;
        }
        
        if(startFlag != oudeFlag && startFlag){
            getChildren().add(startenTekst);
            getChildren().remove(infoTekst);
        } else if(startFlag != oudeFlag && !startFlag){
            getChildren().add(infoTekst);
            getChildren().remove(startenTekst);
            infoTekst.setDisable(false);
        }
    }
}