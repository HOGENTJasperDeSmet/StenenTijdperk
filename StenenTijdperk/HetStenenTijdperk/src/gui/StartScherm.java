/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
/**
 *
 * @author Jasper
 */
public class StartScherm extends StackPane {

    private double xOffset = 0;
    private double yOffset = 0;
    private ImageView korinTower = new ImageView(new Image(getClass().getResourceAsStream("/assets/korinTower.png")));
    private Image textSprites = new Image(getClass().getResourceAsStream("/assets/text.png"));
    private Image gokuHelicopter = new Image(getClass().getResourceAsStream("/assets/gokuHelicopter1.png"));
    private ImageView sluiten, logo, start, hervat, score;
    private DomeinController dc;
    private BorderPane bp = new BorderPane();
    private CharacterSelect cs; 
    private HoofdScherm hs;
    public StartScherm(DomeinController dc, HoofdScherm hs) {
        this.dc = dc;
        this.hs = hs;
        buildGui();
    }
    
    private void buildGui() {

        //gokuHelicopter
        final ImageView gokuHelicopterIv = new ImageView(gokuHelicopter);
        gokuHelicopterIv.setViewport(new Rectangle2D(1,0,102,80));
        final Animation gokuHelicopterAnimation = new SpriteAnimation(
                gokuHelicopterIv,
                Duration.millis(300),
                4, 4,
                -1, 0,
                102, 80
        );
        gokuHelicopterAnimation.setCycleCount(Animation.INDEFINITE);
        gokuHelicopterAnimation.play();
        TranslateTransition gokuHelicopterAnimationTranslation = new TranslateTransition();
        gokuHelicopterAnimationTranslation.setNode(gokuHelicopterIv);
        gokuHelicopterAnimationTranslation.setFromY(540+54);
        gokuHelicopterAnimationTranslation.setToY(-80);
        gokuHelicopterAnimationTranslation.setDuration(Duration.seconds(10));
        gokuHelicopterAnimationTranslation.setCycleCount(1);
        gokuHelicopterAnimationTranslation.setInterpolator(Interpolator.LINEAR);
        gokuHelicopterAnimationTranslation.play();
        gokuHelicopterAnimationTranslation.setOnFinished(actionEvent -> {
                gokuHelicopterAnimationTranslation.play();
        });
        
        //rechterkant scherm
        HBox right = new HBox(10);
        right.setMaxHeight(0);
        right.setMaxWidth(0);
        right.getChildren().add(gokuHelicopterIv);
        right.getChildren().add(korinTower);
        

        //linker kant scherm
        VBox left = new VBox(30);
        left.setMaxHeight(0);
        left.setMaxWidth(0);
        left.setPadding(new Insets(70, 0, 0, 67));
        logo = new ImageView(textSprites);
        logo.setViewport(new Rectangle2D(0, 0, 408, 120));
        //menu maken
        VBox menu = new VBox(5);
        menu.setMaxHeight(0);
        menu.setMaxWidth(0);
        left.getChildren().add(logo);
        left.getChildren().add(menu);
        
        //start knop
        start = new ImageView(textSprites);
        start.setViewport(new Rectangle2D(2, 122, 300, 46));
        start.setPickOnBounds(true);
        start.setOnMouseEntered((MouseEvent event) -> {
            //mediaPlayer.play();
            this.setCursor(Cursor.HAND);
            start.setViewport(new Rectangle2D(323, 122, 300, 46));
        });
        start.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
            start.setViewport(new Rectangle2D(2, 122, 300, 46));
            //mediaPlayer.stop();
        });
        start.setOnMouseClicked((MouseEvent event) -> {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(500),left);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.play();
            left.setDisable(true);
            TranslateTransition translation = new TranslateTransition();
            translation.setNode(korinTower);
            translation.setFromX(korinTower.getTranslateX());
            translation.setToX(-362);
            translation.setDuration(Duration.seconds(1));
            translation.setInterpolator(Interpolator.LINEAR);
            translation.play();
            translation.setOnFinished(ActionEvent -> {
                this.getChildren().add(cs);
            });
            gokuHelicopterAnimationTranslation.setOnFinished(null);
            cs = new CharacterSelect(dc,hs);
            
        });
        menu.getChildren().add(start);
        //hervat knop
        hervat = new ImageView(textSprites);
        hervat.setViewport(new Rectangle2D(2, 168, 300, 46));
        hervat.setPickOnBounds(true);
        hervat.setOnMouseEntered((MouseEvent event) -> {
            //mediaPlayer.play();
            this.setCursor(Cursor.HAND);
            hervat.setViewport(new Rectangle2D(323, 168, 300, 46));
        });
        hervat.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
            hervat.setViewport(new Rectangle2D(2, 168, 300, 46));
            //mediaPlayer.stop();
        });
        hervat.setOnMouseClicked((MouseEvent event) -> {
            hs.opgeslagenSpellen();
//            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
//            hervat.setViewport(new Rectangle2D(2, 168, 300, 46));
//            Image test = new Image(getClass().getResourceAsStream("/assets/kamehameha.png"));
//        ImageView testView = new ImageView(test);
//        testView.setViewport(new Rectangle2D(1,0,960,80));
//        final Animation testAnimation = new SpriteAnimation(
//                testView,
//                Duration.millis(500),
//                18, 1,
//                0, 0,
//                960, 80
//        );
//        testAnimation.setCycleCount(1);
//        testAnimation.play();
//        TranslateTransition tt = new TranslateTransition(Duration.millis(100), testView);
//        tt.setByX(4);
//        tt.setFromY(-1);
//        tt.setToY(1);
//        tt.setCycleCount(Animation.INDEFINITE);
//        tt.setAutoReverse(true);
//        tt.playFromStart();
//        this.getChildren().add(testView);
        });
           menu.getChildren().add(hervat);     
        //score knop
        score = new ImageView(textSprites);
        score.setViewport(new Rectangle2D(2, 216, 300, 46));
        score.setPickOnBounds(true);
        score.setOnMouseEntered((MouseEvent event) -> {
            this.setCursor(Cursor.HAND);
            score.setViewport(new Rectangle2D(323, 216, 300, 46));
        });
        score.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
            score.setViewport(new Rectangle2D(2, 216, 300, 46));
        });
        score.setOnMouseClicked((MouseEvent event) -> {
            //EindScore daskl = new EindScore(dc,"Goku",hs);
            //this.getChildren().add(daskl);
            hs.toonHighScores();
        });
        menu.getChildren().add(score);

        //elementen toevoegen aan borderpane
        bp.setLeft(left);
        bp.setRight(right);
        bp.setMargin(right, new Insets(0, 80, 0, 0));
        this.getChildren().add(bp);
    }
}
