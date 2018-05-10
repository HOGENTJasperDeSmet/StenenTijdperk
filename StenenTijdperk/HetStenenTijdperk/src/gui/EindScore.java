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
    private Image gokuWint = new Image(getClass().getResourceAsStream("/assets/gokuWint.png")),
            gokuVerliest = new Image(getClass().getResourceAsStream("/assets/DefeatGoku.png")),
            krillinWint = new Image(getClass().getResourceAsStream("/assets/gokuWint.png")),
            krillinVerliest = new Image(getClass().getResourceAsStream("/assets/DefeatKrillin.png")),
            chiaotzuWint = new Image(getClass().getResourceAsStream("/assets/ChaiotzuWint.png")),
            chiaotzuVerliest = new Image(getClass().getResourceAsStream("/assets/DefeatChaiotzu.png")),
            jackieChunWint = new Image(getClass().getResourceAsStream("/assets/gokuWint.png")),
            jackieChunVerliest = new Image(getClass().getResourceAsStream("/assets/DefeatJackie.png"));
    private VBox characters[] = new VBox[4];
    private DomeinController dc;
    private Animation winningAnimation,loseAnimation[];
    private String[][] infoEindscore;
    private HoofdScherm hs;

    public EindScore(DomeinController dc, String eindwinnaarNaam, HoofdScherm hs) {
        this.dc = dc;
        this.hs = hs;
        infoEindscore = dc.geefInfoEindscore();
        this.setStyle("-fx-background-color: #ffffff");
        this.setOpacity(0);

        eindwinnaar = new Text(eindwinnaarNaam);
        eindwinnaar.setFont(Font.font("Karma future", 60));
        eindwinnaar.setOpacity(0);
        eindwinnaar.setTranslateY(10);

        victory = new ImageView(spriteSheet);
        victory.setViewport(new Rectangle2D(0, 0, 704, 170));
        victory.setTranslateY(-100);
        victory.setOpacity(0);

        japanseText1 = new ImageView(spriteSheet);
        japanseText1.setViewport(new Rectangle2D(0, 170, 787, 87));
        japanseText1.setTranslateY(-320);

        doorgaan = new ImageView(spriteSheet);
        doorgaan.setViewport(new Rectangle2D(0, 257, 28, 57));
        doorgaan.setTranslateY(230);
        doorgaan.setTranslateX(450);

        doorgaan.setOnMouseClicked((MouseEvent event) -> {
            hs.startScherm();
        });
        loseAnimation = new Animation[infoEindscore.length - 1];
        for (int i = 1; i < infoEindscore.length; i++) {
            characters[i] = new VBox(-4);
            characters[i].setMaxWidth(0);
            characters[i].setMaxHeight(0);
            characters[i].setTranslateY(120);
            characters[i].setTranslateX(0 + ((i - 1) * 140));
            switch(infoEindscore[i][1]){
                case "goku":
                    characterView[i] = new ImageView(gokuVerliest);
                    characterView[i].setViewport(new Rectangle2D(0, 0, 120, 165));
                    loseAnimation[i - 1] = new SpriteAnimation(
                        characterView[i],
                        Duration.millis(800),
                        8, 8,
                        0, 0,
                        120, 165
                    );
                    break;
                case "krillin":
                    characterView[i] = new ImageView(krillinVerliest);
                    characterView[i].setViewport(new Rectangle2D(0, 0, 105, 165));
                    loseAnimation[i  - 1] = new SpriteAnimation(
                        characterView[i],
                        Duration.millis(800),
                        7, 7,
                        0, 0,
                        105, 165
                    );
                    break;
                case "jackieChun":
                    characterView[i] = new ImageView(jackieChunVerliest);
                    characterView[i].setViewport(new Rectangle2D(0, 0, 164, 165));
                    loseAnimation[i - 1] = new SpriteAnimation(
                        characterView[i],
                        Duration.millis(800),
                        8, 8,
                        0, 0,
                        164, 165
                    );
                    break;
                case "chiaotzu":
                    characterView[i] = new ImageView(chiaotzuVerliest);
                    characterView[i].setViewport(new Rectangle2D(0, 0, 120, 165));
                    loseAnimation[i - 1] = new SpriteAnimation(
                        characterView[i],
                        Duration.millis(800),
                        8, 8,
                        0, 0,
                        120, 165
                    );
                    break;
            }

            characterView[i].setScaleX(-1);
            characters[i].setOpacity(0);
            punten[i] = new Text(infoEindscore[i][2]);
            punten[i].setFont(Font.font("Karma future", 32));
            characters[i].setAlignment(Pos.CENTER);
            characters[i].getChildren().addAll(characterView[i], punten[i]);
            this.getChildren().add(characters[i]);
        }
        characters[0] = new VBox(-4);
        characters[0].setMaxWidth(0);
        characters[0].setMaxHeight(0);
        switch(infoEindscore[0][1]){
                case "goku":
                    characterView[0] = new ImageView(gokuWint);
                    characterView[0].setViewport(new Rectangle2D(0, 0, 120, 165));
                    winningAnimation = new SpriteAnimation(
                        characterView[0],
                        Duration.millis(800),
                        7, 7,
                        0, 0,
                        120, 165
                    );
                    winningAnimation.setCycleCount(Animation.INDEFINITE);
                    break;
                case "krillin":
                    characterView[0] = new ImageView(krillinVerliest);
                    characterView[0].setViewport(new Rectangle2D(0, 0, 105, 165));
                    break;
                case "jackieChun":
                    characterView[0] = new ImageView(jackieChunVerliest);
                    characterView[0].setViewport(new Rectangle2D(0, 0, 164, 165));
                    break;
                case "chiaotzu":
                    characterView[0] = new ImageView(chiaotzuWint);
                    characterView[0].setViewport(new Rectangle2D(0, 0, 96, 165));
                    winningAnimation = new SpriteAnimation(
                        characterView[0],
                        Duration.millis(400),
                        4, 4,
                        0, 0,
                        96, 165
                    );
                    break;
            }
        
        characters[0].setTranslateX(-280);
        characters[0].setTranslateY(120);
        characters[0].setOpacity(0);
        punten[0] = new Text(infoEindscore[0][2]);
        punten[0].setFont(Font.font("Karma future", 32));
        characters[0].setAlignment(Pos.CENTER);
        characters[0].getChildren().addAll(characterView[0],punten[0]);
        this.getChildren().add(characters[0]);
        
        
        
        

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

        FadeTransition speler1FadeIn = new FadeTransition(Duration.millis(400), characters[0]);
        speler1FadeIn.setFromValue(0);
        speler1FadeIn.setToValue(1);
        FadeTransition speler2FadeIn = new FadeTransition(Duration.millis(400), characters[1]);
        speler2FadeIn.setFromValue(0);
        speler2FadeIn.setToValue(1);
        FadeTransition speler3FadeIn = new FadeTransition(Duration.millis(400), characters[2]);
        speler3FadeIn.setFromValue(0);
        speler3FadeIn.setToValue(1);
        FadeTransition speler4FadeIn = new FadeTransition(Duration.millis(400), characters[3]);
        speler4FadeIn.setFromValue(0);
        speler4FadeIn.setToValue(1);

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
            speler1FadeIn.play();
            speler2FadeIn.play();
            speler3FadeIn.play();
            speler4FadeIn.play();
        });
        speler1FadeIn.setOnFinished(actionEvent -> {
            for (Animation loseAnimation1 : loseAnimation) {
                loseAnimation1.play();
                winningAnimation.play();
            }
        });

        getChildren().addAll(japanseText1, victory, eindwinnaar, doorgaan);
    }
}
