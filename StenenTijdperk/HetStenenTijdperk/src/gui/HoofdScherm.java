/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author jasperdesmet
 */
public class HoofdScherm extends StackPane{
    private double xOffset = 0;
    private double yOffset = 0;
    private Image textSprites = new Image(getClass().getResourceAsStream("/assets/text.png"));
    private ImageView sluiten;
    private ImageView backgroundImage = new ImageView(new Image(getClass().getResourceAsStream("/assets/backdropSky.gif")));
    private StartScherm ss;
    private SpelBord sp;
    private EindScore es;
    private Stage primaryStage;
    private DomeinController dc;
    private StackPane menubar;
    
    public HoofdScherm(DomeinController dc,Stage primaryStage){
        this.dc = dc;
        this.primaryStage = primaryStage;
        buildGui();
    }
    private void buildGui(){
        ss = new StartScherm(dc,this);
        
        //
        Path path = new Path();
        path.getElements().add(new MoveTo(0, 270));
        path.getElements().add(new LineTo(2260, 270));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(64000));
        pathTransition.setPath(path);
        pathTransition.setNode(backgroundImage);
        pathTransition.setInterpolator(Interpolator.LINEAR);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.play();
        
        //menubar handmatig maken voor eigen design
        menubar = new StackPane();
        menubar.setMaxHeight(20);
        menubar.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        menubar.setOnMouseDragged((MouseEvent event) -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        //sluiten knop maken
        sluiten = new ImageView(textSprites);
        sluiten.setViewport(new Rectangle2D(451, 0, 22, 31));
        sluiten.setOnMouseClicked((MouseEvent event) -> {
            Platform.exit();

        });
        sluiten.setOnMouseEntered((MouseEvent event) -> {
            this.setCursor(Cursor.HAND); //Change cursor to hand
        });
        sluiten.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
        });
        menubar.setAlignment(Pos.TOP_RIGHT);
        menubar.setPadding(new Insets(20, 20, 20, 20));
        menubar.getChildren().addAll(sluiten);
        
        this.setAlignment(Pos.TOP_LEFT);
        this.getChildren().add(backgroundImage);
        this.getChildren().add(ss);
        this.getChildren().add(menubar);
    }
    public void startSpel(){
        getChildren().remove(ss);
        sp = new SpelBord(dc,this);
        getChildren().add(sp);
        menubar.toFront();
    }
    public void startScherm(){
        getChildren().remove(es);
        getChildren().remove(ss);
        ss = new StartScherm(dc, this);
        getChildren().add(ss);
        menubar.toFront();
    }
    public void toonEindScore(){
         es = new EindScore(dc, dc.bepaalWinnaar(),this);
         getChildren().remove(sp);
         getChildren().add(es);
         menubar.toFront();
    }
}
