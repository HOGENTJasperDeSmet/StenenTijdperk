/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author Jasper
 */
public class CharacterSelect extends StackPane{
    private ImageView krillinGezicht, gokuGezicht, jackieChunGezicht, chiaotzuGezicht,
            vs, doorgaan, infoTekst;
    private Image characterSelectSpriteSheet = new Image(getClass().getResourceAsStream("/assets/characterSelectSprites.png"));
    private VBox goku, krillin, jackieChun, chiaotzu, midden;
    private GridPane gp = new GridPane();
    private BorderPane bp = new BorderPane();
    private TextField naamKrillin,naamGoku,naamJackieChun, naamChiaotzu;
    private int aantalSpelers;
    private String[][] namen;
    public CharacterSelect() {
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
        naamKrillin.setOnKeyTyped(InputMethodEvent -> {
            
        });
        naamGoku.setOnKeyTyped(InputMethodEvent -> {
            
        });
        gp.add(krillin, 0, 0);
        gp.add(goku, 1, 0);
        gp.add(chiaotzu, 0, 1);
        gp.add(jackieChun, 1, 1);
        getChildren().add(gp);
        getChildren().add(infoTekst);
        infoTekst.setTranslateY(20); 
    }
    public void spelerAantalUpdate(int indexCh){
        
        infoTekst.setViewport(new Rectangle2D(0, 445, 124, 43));
    }
}