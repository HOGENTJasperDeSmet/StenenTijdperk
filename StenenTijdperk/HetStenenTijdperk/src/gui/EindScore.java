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
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author jasperdesmet
 */
public class EindScore extends StackPane {

    private Text eindwinnaar, punten[] = new Text[4];
    private ImageView victory, japanseText1, doorgaan, characterView[] = new ImageView[4], kamehamehaTest;
    private Image spriteSheet = new Image(getClass().getResourceAsStream("/assets/spritesheetEindscore.png"));
    private Image gokuKameHameHa = new Image(getClass().getResourceAsStream("/assets/gokuKameHameHa.png"));
    private Image gokuWint = new Image(getClass().getResourceAsStream("/assets/gokuWint.png"));
    private VBox characters[] = new VBox[4];
    private DomeinController dc;
    private Animation winningAnimation;
    private String[][] infoEindscore;
    private HoofdScherm hs;
    
    public EindScore(DomeinController dc, String eindwinnaarNaam,HoofdScherm hs) {
        this.dc = dc;
        this.hs = hs;
        
        this.setStyle("-fx-background-color: #ffffff");
        this.setOpacity(0);
        
        eindwinnaar = new Text(eindwinnaarNaam);
        eindwinnaar.setFont(Font.font("Karma future", 60));
        eindwinnaar.setOpacity(0);
        eindwinnaar.setTranslateY(10);
        
        characters[0] = new VBox(-4);
        characters[0].setMaxWidth(0);
        characters[0].setMaxHeight(0);
        characterView[0] = new ImageView(gokuWint);
        characterView[0].setViewport(new Rectangle2D(0, 0, 120, 144));
        characters[0].setTranslateX(-280);
        characters[0].setTranslateY(120);
        characters[0].setOpacity(0);
        punten[0] = new Text("999");
        punten[0].setFont(Font.font("Karma future", 32));
        characters[0].setAlignment(Pos.CENTER);
        characters[0].getChildren().addAll(characterView[0],punten[0]);
        
        characters[1] = new VBox(-4);
        characters[1].setMaxWidth(0);
        characters[1].setMaxHeight(0);
        characters[1].setTranslateX(0);
        characters[1].setTranslateY(120);
        characters[1].setOpacity(1);
        characterView[1] = new ImageView(gokuWint);
        characterView[1].setViewport(new Rectangle2D(0, 0, 120, 144));
        characterView[1].setScaleX(-1);
        punten[1] = new Text("999");
        punten[1].setFont(Font.font("Karma future", 32));
        characters[1].setAlignment(Pos.CENTER);
        characters[1].getChildren().addAll(characterView[1],punten[1]);
        
        characters[2] = new VBox(-4);
        characters[2].setMaxWidth(0);
        characters[2].setMaxHeight(0);
        
        characters[2].setTranslateX(140);
        characters[2].setTranslateY(120);
        characters[2].setOpacity(2);
        characterView[2] = new ImageView(gokuWint);
        characterView[2].setViewport(new Rectangle2D(0, 0, 120, 144));
        characterView[2].setScaleX(-1);
        punten[2] = new Text("999");
        punten[2].setFont(Font.font("Karma future", 32));
        characters[2].setAlignment(Pos.CENTER);
        characters[2].getChildren().addAll(characterView[2],punten[2]);
        
        characters[3] = new VBox(-4);
        characters[3].setMaxWidth(0);
        characters[3].setMaxHeight(0);
        characters[3].setTranslateX(280);
        characters[3].setTranslateY(120);
        characters[3].setOpacity(3);
        characterView[3] = new ImageView(gokuWint);
        characterView[3].setViewport(new Rectangle2D(0, 0, 120, 144));
        characterView[3].setScaleX(-1);
        punten[3] = new Text("999");
        punten[3].setFont(Font.font("Karma future", 32));
        characters[3].setAlignment(Pos.CENTER);
        characters[3].getChildren().addAll(characterView[3],punten[3]);
        
        victory = new ImageView(spriteSheet);
        victory.setViewport(new Rectangle2D(0, 0, 704, 170));
        victory.setTranslateY(-100);
        victory.setOpacity(0);

        japanseText1 = new ImageView(spriteSheet);
        japanseText1.setViewport(new Rectangle2D(0, 170, 787, 87));
        japanseText1.setTranslateY(-320);

        doorgaan = new ImageView(spriteSheet);
        doorgaan.setViewport(new Rectangle2D(0,257,28,57));
        doorgaan.setTranslateY(230);
        doorgaan.setTranslateX(450);
        
        doorgaan.setOnMouseClicked((MouseEvent event) -> {
            hs.startScherm();
        });
        
        winningAnimation = new SpriteAnimation(
                characterView[0],
                Duration.millis(800),
                7, 7,
                0, 0,
                120, 144
        );
        winningAnimation.setCycleCount(Animation.INDEFINITE);
        winningAnimation.play();
        
        FadeTransition schermFadeIn = new FadeTransition(Duration.millis(600), this);
        schermFadeIn.setFromValue(0);
        schermFadeIn.setToValue(1);
        schermFadeIn.play();

        TranslateTransition slideInTekst = new TranslateTransition(Duration.millis(500), japanseText1);
        slideInTekst.setByY(120);
        slideInTekst.setInterpolator(Interpolator.EASE_BOTH);

        FadeTransition victoryFadeIn = new FadeTransition(Duration.millis(400), victory);
        victoryFadeIn.setFromValue(0);
        victoryFadeIn.setToValue(1);

        FadeTransition naamFadeIn = new FadeTransition(Duration.millis(400), eindwinnaar);
        naamFadeIn.setFromValue(0);
        naamFadeIn.setToValue(1);

        FadeTransition gokuFadeIn = new FadeTransition(Duration.millis(400), characters[0]);
        gokuFadeIn.setFromValue(0);
        gokuFadeIn.setToValue(1);

        ScaleTransition naamScale = new ScaleTransition(Duration.millis(200), eindwinnaar);
        naamScale.setFromX(1.0);
        naamScale.setFromY(1.0);
        naamScale.setToX(1.05);
        naamScale.setToY(1.05);
        naamScale.setAutoReverse(true);
        naamScale.setCycleCount(Animation.INDEFINITE);

        schermFadeIn.setOnFinished(actionEvent -> {
            slideInTekst.play();
        });
        slideInTekst.setOnFinished(actionEvent -> {
            victoryFadeIn.play();
        });
        victoryFadeIn.setOnFinished(actionEvent -> {
            naamFadeIn.play();
        });
        naamFadeIn.setOnFinished(actionEvent -> {
            naamScale.play();
            gokuFadeIn.play();
        });

        
        getChildren().addAll(japanseText1, victory,eindwinnaar,characters[0],characters[1],characters[2],characters[3],doorgaan);
    }
}
