/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.nio.file.Paths;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Jasper
 */
public class StartScherm extends StackPane {

    private double xOffset = 0;
    private double yOffset = 0;
    private ImageView backgroundImage = new ImageView(new Image(getClass().getResourceAsStream("/assets/backdropSky.gif")));
    private ImageView korinTower = new ImageView(new Image(getClass().getResourceAsStream("/assets/korinTower.png")));
    private Image textSprites = new Image(getClass().getResourceAsStream("/assets/text.png"));
    private ImageView sluiten, logo, start, hervat, score;
    private Stage primaryStage;
    private BorderPane bp = new BorderPane();

    public StartScherm(Stage primaryStage) {
        buildGui();
        this.primaryStage = primaryStage;
    }

    private void buildGui() {
        String mainMenu = "src/assets/mainMenu.mp3";
        Media mainMenuSong = new Media(Paths.get(mainMenu).toUri().toString());
        MediaPlayer mainMenuPlayer = new MediaPlayer(mainMenuSong);
        mainMenuPlayer.setAutoPlay(true);
        mainMenuPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mainMenuPlayer.setVolume(0.02);
        //animations
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
        StackPane menubar = new StackPane();
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

        //rechterkant scherm
        HBox right = new HBox(10);
        right.setMaxHeight(0);
        right.setMaxWidth(0);
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
        //mediaplayer 
        String bip = "src/assets/bmm.wav";
        Media hit = new Media(Paths.get(bip).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.setVolume(0.05);
        
        
        //start knop
        start = new ImageView(textSprites);
        start.setViewport(new Rectangle2D(2, 122, 300, 46));
        start.setPickOnBounds(true);
        start.setOnMouseEntered((MouseEvent event) -> {
            mediaPlayer.play();
            this.setCursor(Cursor.HAND);
            start.setViewport(new Rectangle2D(323, 122, 300, 46));
        });
        start.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
            start.setViewport(new Rectangle2D(2, 122, 300, 46));
            mediaPlayer.stop();
        });
        menu.getChildren().add(start);
        //hervat knop
        hervat = new ImageView(textSprites);
        hervat.setViewport(new Rectangle2D(2, 168, 300, 46));
        hervat.setPickOnBounds(true);
        hervat.setOnMouseEntered((MouseEvent event) -> {
            mediaPlayer.play();
            this.setCursor(Cursor.HAND);
            hervat.setViewport(new Rectangle2D(323, 168, 300, 46));
        });
        hervat.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
            hervat.setViewport(new Rectangle2D(2, 168, 300, 46));
            mediaPlayer.stop();
        });
        menu.getChildren().add(hervat);
        //score knop
        score = new ImageView(textSprites);
        score.setViewport(new Rectangle2D(2, 216, 300, 46));
        score.setPickOnBounds(true);
        score.setOnMouseEntered((MouseEvent event) -> {
            mediaPlayer.play();
            this.setCursor(Cursor.HAND);
            score.setViewport(new Rectangle2D(323, 216, 300, 46));
        });
        score.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
            score.setViewport(new Rectangle2D(2, 216, 300, 46));
            mediaPlayer.stop();
        });
        menu.getChildren().add(score);

        //elementen toevoegen aan borderpane
        bp.setLeft(left);
        bp.setRight(right);
        bp.setMargin(right, new Insets(0, 80, 0, 0));

        this.setAlignment(Pos.TOP_LEFT);
        this.getChildren().add(backgroundImage);
        this.getChildren().add(bp);
        this.getChildren().add(menubar);

    }
}
