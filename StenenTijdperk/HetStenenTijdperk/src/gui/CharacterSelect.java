/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jasper
 */
public class CharacterSelect extends GridPane{
    private ImageView krillinGezicht, gokuGezicht, jackieChunGezicht, chiaotzuGezicht,
            naamKrillin,naamGoku,naamJackieChun, naamChiaotzu,
            vs, doorgaan, terug;
    private Image characterSelectSpriteSheet = new Image(getClass().getResourceAsStream("/assets/characterSelectSprites.png"));
    private VBox goku, krillin, jackieChun, chiaotzu;
    public CharacterSelect() {
        buildGui();
    }
    
    private void buildGui() {
        this.setHgap(30);
        
        
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
        
        //textbox
        naamKrillin = new ImageView(characterSelectSpriteSheet);
        naamKrillin.setViewport(new Rectangle2D(283, 402, 283, 48));
        naamGoku = new ImageView(characterSelectSpriteSheet);
        naamGoku.setViewport(new Rectangle2D(283, 402, 283, 48));
        naamJackieChun = new ImageView(characterSelectSpriteSheet);
        naamJackieChun.setViewport(new Rectangle2D(283, 402, 283, 48));
        naamChiaotzu = new ImageView(characterSelectSpriteSheet);
        naamChiaotzu.setViewport(new Rectangle2D(283, 402, 283, 48));
        
        
        //Hboxes
        goku = new VBox(gokuGezicht,naamGoku);
        krillin = new VBox(krillinGezicht,naamKrillin);
        jackieChun = new VBox(jackieChunGezicht,naamJackieChun);
        chiaotzu = new VBox(chiaotzuGezicht,naamChiaotzu);
        goku.setAlignment(Pos.CENTER);
        krillin.setAlignment(Pos.CENTER);
        jackieChun.setAlignment(Pos.CENTER);
        chiaotzu.setAlignment(Pos.CENTER);
        
        setHgrow(goku, Priority.ALWAYS);
        setVgrow(goku, Priority.ALWAYS);
        setHgrow(krillin, Priority.ALWAYS);
        setVgrow(krillin, Priority.ALWAYS);
        setHgrow(jackieChun, Priority.ALWAYS);
        setVgrow(jackieChun, Priority.ALWAYS);
        setHgrow(chiaotzu, Priority.ALWAYS);
        setVgrow(chiaotzu, Priority.ALWAYS);
        
        this.add(goku, 1, 0);
        this.add(krillin, 0, 0);
        this.add(jackieChun, 1, 1);
        this.add(chiaotzu, 0, 1);
    }
}