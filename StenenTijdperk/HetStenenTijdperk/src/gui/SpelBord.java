/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.Random;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SpelBord extends StackPane {

    private DomeinController dc;
    private ImageView kaartView, karakterView, grondstoffenView[], grondstofIconView[], gereedschappenView[],
            infoAndereSpelersKader, plaatsViews[][], infoAndereSpeler[], confirm, kost[][];
    private StackPane infoAndereSpelers, hutkaart[], grondstof[];
    private Pane bos, leemgroeve, steengroeve, rivier, akker, hut, gereedschapsmaker, jacht, stapel1, stapel2, stapel3, stapel4;
    private Text grondstofText[], waarde[];
    private BorderPane uiLayout;
    private VBox infoSpelerAanZetDetail, hutkaarten, infoAndereSpelersVbox;
    private HBox gereedschappen, grondstoffen, infoSpelerAanZet, hutkaartenInhoud[];
    private Image uiSpriteSheet = new Image(getClass().getResourceAsStream("/assets/gui.png"));
    private Image goku = new ImageCharacter(getClass().getResourceAsStream("/assets/GokuStaan.png"), "goku");
    private Image krillin = new ImageCharacter(getClass().getResourceAsStream("/assets/krillinStaan.png"), "krillin");
    private Image chiaotzu = new ImageCharacter(getClass().getResourceAsStream("/assets/ChaiotzuStaan.png"), "chiaotzu");
    private Image jackieChun = new ImageCharacter(getClass().getResourceAsStream("/assets/jackieChunStaan.png"), "jackieChun");
    private Image characterFacesSmall = new Image(getClass().getResourceAsStream("/assets/characterFacesSmall.png"));
    private int[] bezettePlaatsen;
    private boolean[] gereedschap = {false, false, false};
    private int[] bezettePlaatsenVorigeBeurt;
    private int SpelerAanZetNummer, laatstGezettePlaats = -1, spelersDieActiesHebbenVoltooid = 0, spelersDieStamledenHebbenGevoed = 0, geworpenOgen;
    private meldingPopup melding;
    private HoofdScherm hs;

    public SpelBord(DomeinController dc,HoofdScherm hs) {
        this.dc = dc;
        this.hs = hs;
        buildGui();
    }

    private void buildGui() {
        //spelerAanZetNummer
        SpelerAanZetNummer = dc.geefSpelerAanZetSpelerNummer();
        //kaart
        kaartView = new ImageView(new Image(getClass().getResourceAsStream("/assets/map.png")));
        kaartView.setImage(new Image(getClass().getResourceAsStream("/assets/map.png")));
        this.getChildren().add(kaartView);

        //karakterInfo ui
        gereedschappen = new HBox();
        grondstoffen = new HBox();
        infoSpelerAanZet = new HBox();
        infoSpelerAanZetDetail = new VBox();

        karakterView = new ImageView(uiSpriteSheet);
        switch (dc.geefSpelerKarakter(SpelerAanZetNummer - 1)) {
            case "goku":
                karakterView.setViewport(new Rectangle2D(0, 194, 125, 90));
                break;
            case "krillin":
                karakterView.setViewport(new Rectangle2D(125, 194, 125, 90));
                break;
            case "chiaotzu":
                karakterView.setViewport(new Rectangle2D(125, 287, 125, 90));
                break;
            case "jackieChun":
                karakterView.setViewport(new Rectangle2D(0, 287, 125, 90));
                break;
        }

        gereedschappenView = new ImageView[3];
        int[] gereedschap = dc.geefSpelerGereedschap(SpelerAanZetNummer - 1);
        for (int i = 0; i < gereedschappenView.length; i++) {
            gereedschappenView[i] = new ImageView(uiSpriteSheet);
            switch (gereedschap[i]) {
                case 0:
                    gereedschappenView[i].setViewport(new Rectangle2D(0, 162, 133, 22));
                    break;
                case 1:
                    gereedschappenView[i].setViewport(new Rectangle2D(0, 96, 133, 22));
                    break;
                case 2:
                    gereedschappenView[i].setViewport(new Rectangle2D(0, 118, 133, 22));
                    break;
                case 3:
                    gereedschappenView[i].setViewport(new Rectangle2D(0, 140, 133, 22));
                    break;
            }

            gereedschappen.getChildren().add(gereedschappenView[i]);
        }
        grondstof = new StackPane[5];
        grondstoffenView = new ImageView[5];
        grondstofText = new Text[5];

        int[] grondstoffenSpeler = dc.geefGrondstoffenSpelerAanZet();
        for (int i = 0; i < grondstoffenView.length; i++) {
            grondstof[i] = new StackPane();
            grondstofText[i] = new Text("" + grondstoffenSpeler[i]);
            grondstofText[i].setFont(Font.font("Karma future", 16));
            grondstofText[i].setTranslateX(10);
            grondstofText[i].setTranslateY(-2);
            grondstoffenView[i] = new ImageView(uiSpriteSheet);
            grondstoffenView[i].setViewport(new Rectangle2D(0, 26, 80, 26));
            grondstof[i].getChildren().add(grondstoffenView[i]);
            grondstof[i].getChildren().add(grondstofText[i]);
            grondstoffen.getChildren().add(grondstof[i]);
        }
        grondstofIconView = new ImageView[5];
        grondstofIconView[0] = new ImageView(new Image(getClass().getResourceAsStream("/assets/hout.png")));
        grondstofIconView[1] = new ImageView(new Image(getClass().getResourceAsStream("/assets/leem.png")));
        grondstofIconView[2] = new ImageView(new Image(getClass().getResourceAsStream("/assets/steen.png")));
        grondstofIconView[3] = new ImageView(new Image(getClass().getResourceAsStream("/assets/goud.png")));
        grondstofIconView[4] = new ImageView(new Image(getClass().getResourceAsStream("/assets/voedsel.png")));
        for (int i = 0; i < grondstofIconView.length; i++) {
            grondstofIconView[i].setTranslateX(-15);
            grondstofIconView[i].setTranslateY(-2);
        }
        grondstof[0].getChildren().add(grondstofIconView[0]);
        grondstof[1].getChildren().add(grondstofIconView[1]);
        grondstof[2].getChildren().add(grondstofIconView[2]);
        grondstof[3].getChildren().add(grondstofIconView[3]);
        grondstof[4].getChildren().add(grondstofIconView[4]);

        gereedschappen.setTranslateY(8);
        infoSpelerAanZetDetail.getChildren().add(gereedschappen);
        infoSpelerAanZetDetail.getChildren().add(grondstoffen);
        infoSpelerAanZetDetail.setAlignment(Pos.BOTTOM_CENTER);

        infoSpelerAanZet.getChildren().add(karakterView);
        infoSpelerAanZet.getChildren().add(infoSpelerAanZetDetail);
        infoSpelerAanZet.setAlignment(Pos.BOTTOM_CENTER);

        //infoAndereSpelers
        infoAndereSpelers = new StackPane();
        infoAndereSpeler = new ImageView[dc.geefAantalSpelers() - 1];
        infoAndereSpelersVbox = new VBox(13);
        infoAndereSpelersVbox.setMaxHeight(0);
        infoAndereSpelersVbox.setMaxWidth(0);
        for (int i = 0; i < infoAndereSpeler.length; i++) {
            infoAndereSpeler[i] = new ImageView();
            infoAndereSpeler[i].setImage(characterFacesSmall);
            switch (dc.geefSpelerKarakter((SpelerAanZetNummer + i) % dc.geefAantalSpelers())) {
                case "goku":
                    infoAndereSpeler[i].setViewport(new Rectangle2D(0, 0, 37, 38));
                    break;
                case "krillin":
                    infoAndereSpeler[i].setViewport(new Rectangle2D(37, 0, 37, 38));
                    break;
                case "jackieChun":
                    infoAndereSpeler[i].setViewport(new Rectangle2D(0, 38, 37, 38));
                    break;
                case "chiaotzu":
                    infoAndereSpeler[i].setViewport(new Rectangle2D(37, 38, 37, 38));
                    break;
            }

            infoAndereSpelersVbox.getChildren().add(infoAndereSpeler[i]);
        }
        infoAndereSpelersKader = new ImageView(uiSpriteSheet);
        switch (dc.geefAantalSpelers()) {
            case 2:
                infoAndereSpelersKader.setViewport(new Rectangle2D(147, 0, 42, 79));
                break;
            case 3:
                infoAndereSpelersKader.setViewport(new Rectangle2D(194, 2, 42, 131));
                break;
            case 4:
                infoAndereSpelersKader.setViewport(new Rectangle2D(241, 2, 42, 183));
                break;
        }
        infoAndereSpelers.getChildren().addAll(infoAndereSpelersVbox, infoAndereSpelersKader);

        plaatsViews = new ImageView[12][];
        //jacht plaatsen
        plaatsViews[2] = new ImageView[40];
        int rij = 0;
        Random rand = new Random();

        for (int i = 0; i < plaatsViews[2].length; i++) {
            if (i % 10 == 0) {
                rij++;
            }
            plaatsViews[2][i] = new ImageView();
            //plaatsViews[2][i].setTranslateX(30 + 10 * (i % 10));
            //plaatsViews[2][i].setTranslateY(140 + (20 * rij));
            plaatsViews[2][i].setTranslateX(30 + rand.nextInt(100) + 1);
            plaatsViews[2][i].setTranslateY(140 + rand.nextInt(60) + 1);
            this.getChildren().add(plaatsViews[2][i]);
        }

        //hutkaart plaatsen
        plaatsViews[8] = new ImageView[1];
        plaatsViews[8][0] = new ImageView();
        plaatsViews[8][0].setTranslateX(-325);
        plaatsViews[8][0].setTranslateY(168);

        plaatsViews[9] = new ImageView[1];
        plaatsViews[9][0] = new ImageView();
        plaatsViews[9][0].setTranslateX(-325);
        plaatsViews[9][0].setTranslateY(195);

        plaatsViews[10] = new ImageView[1];
        plaatsViews[10][0] = new ImageView();
        plaatsViews[10][0].setTranslateX(-325);
        plaatsViews[10][0].setTranslateY(222);

        plaatsViews[11] = new ImageView[1];
        plaatsViews[11][0] = new ImageView();
        plaatsViews[11][0].setTranslateX(-325);
        plaatsViews[11][0].setTranslateY(249);

        //bos plaatsen
        plaatsViews[4] = new ImageView[7];
        for (int i = 0; i < plaatsViews[4].length; i++) {
            plaatsViews[4][i] = new ImageView();
        }
        plaatsViews[4][0].setTranslateX(-82);
        plaatsViews[4][0].setTranslateY(-172);
        plaatsViews[4][1].setTranslateX(-59);
        plaatsViews[4][1].setTranslateY(-152);
        plaatsViews[4][2].setTranslateX(-18);
        plaatsViews[4][2].setTranslateY(-152);
        plaatsViews[4][3].setTranslateX(47);
        plaatsViews[4][3].setTranslateY(-182);
        plaatsViews[4][3].setScaleX(-1);
        plaatsViews[4][4].setTranslateX(95);
        plaatsViews[4][4].setTranslateY(-162);
        plaatsViews[4][4].setScaleX(-1);
        plaatsViews[4][5].setTranslateX(118);
        plaatsViews[4][5].setTranslateY(-182);
        plaatsViews[4][5].setScaleX(-1);
        plaatsViews[4][6].setTranslateX(160);
        plaatsViews[4][6].setTranslateY(-182);
        plaatsViews[4][6].setScaleX(-1);

        //steengroeve plaatsen
        plaatsViews[6] = new ImageView[7];
        for (int i = 0; i < plaatsViews[6].length; i++) {
            plaatsViews[6][i] = new ImageView();
        }
        plaatsViews[6][0].setTranslateX(165);
        plaatsViews[6][0].setTranslateY(-140);
        plaatsViews[6][1].setTranslateX(185);
        plaatsViews[6][1].setTranslateY(-130);
        plaatsViews[6][2].setTranslateX(195);
        plaatsViews[6][2].setTranslateY(-120);
        plaatsViews[6][3].setTranslateX(200);
        plaatsViews[6][3].setTranslateY(-110);
        plaatsViews[6][4].setTranslateX(205);
        plaatsViews[6][4].setTranslateY(-100);
        plaatsViews[6][5].setTranslateX(225);
        plaatsViews[6][5].setTranslateY(-90);
        plaatsViews[6][6].setTranslateX(250);
        plaatsViews[6][6].setTranslateY(-80);

        //meer plaatsem
        plaatsViews[7] = new ImageView[7];
        for (int i = 0; i < plaatsViews[7].length; i++) {
            plaatsViews[7][i] = new ImageView();
        }
        plaatsViews[7][0].setTranslateX(-40);
        plaatsViews[7][0].setTranslateY(-40);
        plaatsViews[7][1].setTranslateX(-35);
        plaatsViews[7][1].setTranslateY(-30);
        plaatsViews[7][2].setTranslateX(-25);
        plaatsViews[7][2].setTranslateY(-20);
        plaatsViews[7][3].setTranslateX(-10);
        plaatsViews[7][3].setTranslateY(-15);
        plaatsViews[7][4].setTranslateX(165);
        plaatsViews[7][4].setTranslateY(-70);
        plaatsViews[7][4].setScaleX(-1);
        plaatsViews[7][5].setTranslateX(165);
        plaatsViews[7][5].setTranslateY(-50);
        plaatsViews[7][5].setScaleX(-1);
        plaatsViews[7][6].setTranslateX(163);
        plaatsViews[7][6].setTranslateY(-35);
        plaatsViews[7][6].setScaleX(-1);

        //leemgroeve plaatsen
        plaatsViews[5] = new ImageView[7];
        for (int i = 0; i < plaatsViews[5].length; i++) {
            plaatsViews[5][i] = new ImageView();
        }
        plaatsViews[5][0].setTranslateX(-200);
        plaatsViews[5][0].setTranslateY(60);
        plaatsViews[5][1].setTranslateX(-160);
        plaatsViews[5][1].setTranslateY(65);
        plaatsViews[5][1].setScaleX(-1);
        plaatsViews[5][2].setTranslateX(-185);
        plaatsViews[5][2].setTranslateY(80);
        plaatsViews[5][3].setTranslateX(-125);
        plaatsViews[5][3].setTranslateY(80);
        plaatsViews[5][3].setScaleX(-1);
        plaatsViews[5][4].setTranslateX(-175);
        plaatsViews[5][4].setTranslateY(100);
        plaatsViews[5][5].setTranslateX(-145);
        plaatsViews[5][5].setTranslateY(120);
        plaatsViews[5][6].setTranslateX(-120);
        plaatsViews[5][6].setTranslateY(100);
        plaatsViews[5][6].setScaleX(-1);

        //hut plaatsen
        plaatsViews[1] = new ImageView[2];
        for (int i = 0; i < plaatsViews[1].length; i++) {
            plaatsViews[1][i] = new ImageView();
        }
        plaatsViews[1][0].setTranslateX(-270);
        plaatsViews[1][0].setTranslateY(20);
        plaatsViews[1][1].setTranslateX(-240);
        plaatsViews[1][1].setTranslateY(10);
        plaatsViews[1][1].setScaleX(-1);

        //akker
        plaatsViews[0] = new ImageView[1];
        plaatsViews[0][0] = new ImageView();
        plaatsViews[0][0].setTranslateX(270);
        plaatsViews[0][0].setTranslateY(80);

        //gereedschap
        plaatsViews[3] = new ImageView[1];
        plaatsViews[3][0] = new ImageView();
        plaatsViews[3][0].setTranslateX(-270);
        plaatsViews[3][0].setTranslateY(-110);
        plaatsViews[3][0].setScaleX(-1);

        this.getChildren().addAll(plaatsViews[4][0], plaatsViews[4][1], plaatsViews[4][2], plaatsViews[4][3], plaatsViews[4][4], plaatsViews[4][5], plaatsViews[4][6],
                plaatsViews[6][0], plaatsViews[6][1], plaatsViews[6][2], plaatsViews[6][3], plaatsViews[6][4], plaatsViews[6][5], plaatsViews[6][6],
                plaatsViews[7][0], plaatsViews[7][1], plaatsViews[7][2], plaatsViews[7][3], plaatsViews[7][4], plaatsViews[7][5], plaatsViews[7][6],
                plaatsViews[5][0], plaatsViews[5][1], plaatsViews[5][2], plaatsViews[5][3], plaatsViews[5][4], plaatsViews[5][5], plaatsViews[5][6],
                plaatsViews[1][0], plaatsViews[1][1], plaatsViews[3][0], plaatsViews[0][0], plaatsViews[8][0], plaatsViews[9][0], plaatsViews[10][0], plaatsViews[11][0]);

        //hutkaarten aanmaken
        hutkaart = new StackPane[4];
        hutkaartenInhoud = new HBox[4];
        hutkaarten = new VBox();
        String[][] info = dc.geefInfoKaarten();
        kost = new ImageView[4][4];
        waarde = new Text[4];
        for (int i = 0; i < hutkaart.length; i++) {
            hutkaart[i] = new StackPane();
            hutkaartenInhoud[i] = new HBox(1);

            ImageView kader = new ImageView(uiSpriteSheet);
            kader.setViewport(new Rectangle2D(0, 0, 138, 26));
            kost[i][0] = new ImageView(uiSpriteSheet);
            kost[i][0].setViewport(new Rectangle2D(0, 74, 21, 22));
            kost[i][1] = new ImageView(new Image(getClass().getResourceAsStream("/assets/" + info[i][1].toLowerCase() + ".png")));
            kost[i][2] = new ImageView(new Image(getClass().getResourceAsStream("/assets/" + info[i][2].toLowerCase() + ".png")));
            kost[i][3] = new ImageView(new Image(getClass().getResourceAsStream("/assets/" + info[i][3].toLowerCase() + ".png")));

            waarde[i] = new Text(info[i][0]);
            waarde[i].setFont(Font.font("Karma future", 16));

            hutkaartenInhoud[i].setMaxWidth(0);
            hutkaartenInhoud[i].setMaxHeight(0);
            hutkaartenInhoud[i].getChildren().addAll(kost[i][0], kost[i][1], kost[i][2], kost[i][3], waarde[i]);

            hutkaart[i].getChildren().addAll(kader, hutkaartenInhoud[i]);
            hutkaarten.getChildren().addAll(hutkaart[i]);
        }
        hutkaarten.setTranslateX(-405);
        hutkaarten.setTranslateY(430);

        this.getChildren().add(hutkaarten);

        bezettePlaatsen = new int[12];
        bezettePlaatsenVorigeBeurt = bezettePlaatsen.clone();

        bos = new Pane();
        bos.setMaxWidth(262);
        bos.setMaxHeight(125);
        bos.setTranslateY(-190);
        bos.setTranslateX(25);
        bos.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
        });
        bos.setOnMouseEntered((MouseEvent event) -> {
            this.setCursor(Cursor.HAND);
        });
        bos.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 4);
        });
        steengroeve = new Pane();
        steengroeve.setMaxWidth(154);
        steengroeve.setMaxHeight(124);
        steengroeve.setTranslateY(-120);
        steengroeve.setTranslateX(250);
        steengroeve.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
        });
        steengroeve.setOnMouseEntered((MouseEvent event) -> {
            this.setCursor(Cursor.HAND);
        });
        steengroeve.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 6);
        });
        leemgroeve = new Pane();
        leemgroeve.setMaxWidth(151);
        leemgroeve.setMaxHeight(121);
        leemgroeve.setTranslateY(90);
        leemgroeve.setTranslateX(-150);
        leemgroeve.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
        });
        leemgroeve.setOnMouseEntered((MouseEvent event) -> {
            this.setCursor(Cursor.HAND);
        });
        leemgroeve.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 5);
        });
        rivier = new Pane();
        rivier.setMaxWidth(207);
        rivier.setMaxHeight(130);
        rivier.setTranslateY(-35);
        rivier.setTranslateX(60);
        rivier.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
        });
        rivier.setOnMouseEntered((MouseEvent event) -> {
            this.setCursor(Cursor.HAND);
        });
        rivier.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 7);
        });
        gereedschapsmaker = new Pane();
        gereedschapsmaker.setMaxWidth(90);
        gereedschapsmaker.setMaxHeight(178);
        gereedschapsmaker.setTranslateY(-180);
        gereedschapsmaker.setTranslateX(-300);
        gereedschapsmaker.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
        });
        gereedschapsmaker.setOnMouseEntered((MouseEvent event) -> {
            this.setCursor(Cursor.HAND);
        });
        gereedschapsmaker.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 3);
        });
        hut = new Pane();
        hut.setMaxWidth(109);
        hut.setMaxHeight(95);
        hut.setTranslateY(-10);
        hut.setTranslateX(-285);
        hut.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
        });
        hut.setOnMouseEntered((MouseEvent event) -> {
            this.setCursor(Cursor.HAND);
        });
        hut.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 1);
        });
        akker = new Pane();
        akker.setMaxWidth(212);
        akker.setMaxHeight(126);
        akker.setTranslateY(110);
        akker.setTranslateX(285);
        akker.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
        });
        akker.setOnMouseEntered((MouseEvent event) -> {
            this.setCursor(Cursor.HAND);
        });
        akker.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 0);
        });

        jacht = new Pane();
        jacht.setMaxWidth(153);
        jacht.setMaxHeight(134);
        jacht.setTranslateY(160);
        jacht.setTranslateX(80);
        jacht.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
        });
        jacht.setOnMouseEntered((MouseEvent event) -> {
            this.setCursor(Cursor.HAND);
        });
        jacht.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 2);
        });

        stapel1 = new Pane();
        stapel1.setTranslateX(-405);
        stapel1.setTranslateY(172);
        stapel1.setMaxWidth(138);
        stapel1.setMaxHeight(26);
        stapel1.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
        });
        stapel1.setOnMouseEntered((MouseEvent event) -> {
            this.setCursor(Cursor.HAND);
        });
        stapel1.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 8);
        });

        stapel2 = new Pane();
        stapel2.setTranslateX(-405);
        stapel2.setTranslateY(198);
        stapel2.setMaxWidth(138);
        stapel2.setMaxHeight(26);
        stapel2.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
        });
        stapel2.setOnMouseEntered((MouseEvent event) -> {
            this.setCursor(Cursor.HAND);
        });
        stapel2.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 9);
        });

        stapel3 = new Pane();
        stapel3.setTranslateX(-405);
        stapel3.setTranslateY(224);
        stapel3.setMaxWidth(138);
        stapel3.setMaxHeight(26);
        stapel3.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
        });
        stapel3.setOnMouseEntered((MouseEvent event) -> {
            this.setCursor(Cursor.HAND);
        });
        stapel3.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 10);
        });

        stapel4 = new Pane();
        stapel4.setTranslateX(-405);
        stapel4.setTranslateY(250);
        stapel4.setMaxWidth(138);
        stapel4.setMaxHeight(26);
        stapel4.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
        });
        stapel4.setOnMouseEntered((MouseEvent event) -> {
            this.setCursor(Cursor.HAND);
        });
        stapel4.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 11);
        });

        confirm = new ImageView(uiSpriteSheet);
        BorderPane.setAlignment(confirm, Pos.CENTER);
        confirm.setViewport(new Rectangle2D(139, 131, 100, 63));
        confirm.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
        });
        confirm.setOnMouseEntered((MouseEvent event) -> {
            this.setCursor(Cursor.HAND);
        });
        confirm.setOnMouseClicked((MouseEvent event) -> {
            volgendeSpeler();
        });

        uiLayout = new BorderPane();
        uiLayout.setBottom(infoSpelerAanZet);
        uiLayout.setLeft(infoAndereSpelers);

        uiLayout.setPickOnBounds(false);

        this.getChildren().add(uiLayout);
        this.getChildren().addAll(bos, steengroeve, leemgroeve, rivier, gereedschapsmaker, hut, akker, jacht, stapel1, stapel2, stapel3, stapel4);
    }

    public void volgendeSpeler() {
        dc.plaatsStamleden(laatstGezettePlaats, bezettePlaatsen[laatstGezettePlaats] - bezettePlaatsenVorigeBeurt[laatstGezettePlaats]);
        bezettePlaatsenVorigeBeurt = bezettePlaatsen.clone();
        dc.volgendeSpeler();
        if (dc.alleStamledenGeplaatst()) {
            dc.verzetNaarStartSpeler();
            SpelerAanZetNummer = dc.geefSpelerAanZetSpelerNummer();
            uiUpdate();
            uiLayout.getChildren().remove(confirm);
            actieFase();
            return;
        }
        if (dc.alleStamledenGeplaatstSpelerAanZet()) {
            volgendeSpeler();
        } else {
            SpelerAanZetNummer = dc.geefSpelerAanZetSpelerNummer();
            uiUpdate();
            laatstGezettePlaats = -1;
            uiLayout.getChildren().remove(confirm);
        }
    }

    public void gereedSchapKlikken(int view, int power, MouseEvent event) {
        if (gereedschap[view]) {
            geworpenOgen -= power;

            gereedschappenView[view].setViewport(new Rectangle2D(0, 96 + ((power - 1) * 22), 133, 22));
        } else {
            geworpenOgen += power;
            gereedschappenView[view].setViewport(new Rectangle2D(0, 377 + ((power - 1) * 22), 133, 22));
        }
        gereedschap[view] = !gereedschap[view];
    }

    public void uiUpdate() {
        switch (dc.geefSpelerKarakter(SpelerAanZetNummer - 1)) {
            case "goku":
                karakterView.setViewport(new Rectangle2D(0, 194, 125, 90));
                break;
            case "krillin":
                karakterView.setViewport(new Rectangle2D(125, 194, 125, 90));
                break;
            case "chiaotzu":
                karakterView.setViewport(new Rectangle2D(125, 287, 125, 90));
                break;
            case "jackieChun":
                karakterView.setViewport(new Rectangle2D(0, 287, 125, 90));
                break;
        }

        int[] gereedschap = dc.geefSpelerGereedschap(SpelerAanZetNummer - 1);
        boolean[] gereedschapGebruikt = dc.geefSpelerGereedschapGebruikt(SpelerAanZetNummer - 1);

        for (int i = 0; i < gereedschappenView.length; i++) {
            if (!gereedschapGebruikt[i]) {
                switch (gereedschap[i]) {
                    case 0:
                        gereedschappenView[i].setViewport(new Rectangle2D(0, 162, 133, 22));
                        break;
                    case 1:
                        gereedschappenView[i].setViewport(new Rectangle2D(0, 96, 133, 22));
                        break;
                    case 2:
                        gereedschappenView[i].setViewport(new Rectangle2D(0, 118, 133, 22));
                        break;
                    case 3:
                        gereedschappenView[i].setViewport(new Rectangle2D(0, 140, 133, 22));
                        break;
                }
            } else {
                gereedschappenView[i].setViewport(new Rectangle2D(0, 162, 133, 22));
            }

        }

        int[] grondstoffenSpeler = dc.geefGrondstoffenSpelerAanZet();
        for (int i = 0; i < grondstoffenView.length; i++) {
            grondstofText[i].setText("" + grondstoffenSpeler[i]);
        }
        for (int i = 0; i < infoAndereSpeler.length; i++) {
            switch (dc.geefSpelerKarakter((SpelerAanZetNummer + i) % dc.geefAantalSpelers())) {
                case "goku":
                    infoAndereSpeler[i].setViewport(new Rectangle2D(0, 0, 37, 38));
                    break;
                case "krillin":
                    infoAndereSpeler[i].setViewport(new Rectangle2D(37, 0, 37, 38));
                    break;
                case "jackieChun":
                    infoAndereSpeler[i].setViewport(new Rectangle2D(0, 38, 37, 38));
                    break;
                case "chiaotzu":
                    infoAndereSpeler[i].setViewport(new Rectangle2D(37, 38, 37, 38));
                    break;
            }
        }
        String[][] info = dc.geefInfoKaarten();
        for (int i = 0; i < hutkaart.length; i++) {
            if (info[i][0] == "einde") {
                kost[i][0].setImage(null);
                kost[i][1].setImage(null);
                kost[i][2].setImage(null);
                kost[i][3].setImage(null);
                waarde[i].setText("");
            } else {
                kost[i][0].setViewport(new Rectangle2D(0 + (21 * dc.geefActieveKaartVanStapel(i)), 74, 21, 22));
                kost[i][1].setImage(new Image(getClass().getResourceAsStream("/assets/" + info[i][1].toLowerCase() + ".png")));
                kost[i][2].setImage(new Image(getClass().getResourceAsStream("/assets/" + info[i][2].toLowerCase() + ".png")));
                kost[i][3].setImage(new Image(getClass().getResourceAsStream("/assets/" + info[i][3].toLowerCase() + ".png")));
                waarde[i].setText(info[i][0]);
            }

        }
    }

    public void plaatsStamledenBord(MouseEvent event, int plaats) {
        if (laatstGezettePlaats == plaats || laatstGezettePlaats == -1) {
            if (event.getButton() == MouseButton.SECONDARY) {
                if (bezettePlaatsen[plaats] - bezettePlaatsenVorigeBeurt[plaats] != 0) {
                    laatstGezettePlaats = plaats;
                    bezettePlaatsen[plaats] -= 1;
                    if (plaats == 1) {
                        uiLayout.getChildren().remove(confirm);
                    }
                    if (bezettePlaatsen[plaats] - bezettePlaatsenVorigeBeurt[plaats] == 0) {
                        laatstGezettePlaats = -1;
                        uiLayout.getChildren().remove(confirm);
                    }

                    plaatsViews[plaats][bezettePlaatsen[plaats]].setImage(null);
                }
            } else {
                try {
                    if (dc.plaatsIsValid(plaats, bezettePlaatsen[plaats] - bezettePlaatsenVorigeBeurt[plaats] + 1)) {
                        laatstGezettePlaats = plaats;
                        bezettePlaatsen[plaats] += 1;
                        if (plaats == 1) {
                            if (bezettePlaatsen[plaats] == 2) {
                                uiLayout.setRight(confirm);
                            }
                        } else {
                            uiLayout.setRight(confirm);
                        }

                        switch (dc.geefSpelerKarakter(SpelerAanZetNummer - 1)) {
                            case "krillin":
                                plaatsViews[plaats][bezettePlaatsen[plaats] - 1].setImage(krillin);
                                plaatsViews[plaats][bezettePlaatsen[plaats] - 1].setViewport(new Rectangle2D(0, 0, 25, 30));
                                break;
                            case "goku":
                                plaatsViews[plaats][bezettePlaatsen[plaats] - 1].setImage(goku);
                                plaatsViews[plaats][bezettePlaatsen[plaats] - 1].setViewport(new Rectangle2D(0, 0, 20, 35));
                                break;
                            case "jackieChun":
                                plaatsViews[plaats][bezettePlaatsen[plaats] - 1].setImage(jackieChun);
                                plaatsViews[plaats][bezettePlaatsen[plaats] - 1].setViewport(new Rectangle2D(0, 0, 30, 40));
                                break;
                            case "chiaotzu":
                                plaatsViews[plaats][bezettePlaatsen[plaats] - 1].setImage(chiaotzu);
                                plaatsViews[plaats][bezettePlaatsen[plaats] - 1].setViewport(new Rectangle2D(0, 0, 20, 30));
                                break;
                        }
                    }
                } catch (IllegalArgumentException iae) {
                    melding = new meldingPopup(iae.getMessage(), this);
                    this.getChildren().add(melding);
                    StackPane.setAlignment(melding, Pos.TOP_LEFT);
                }

            }
        }
    }

    public void verwijderVanPlaats(int plaats) {
        for (ImageView plaatsView : plaatsViews[plaats]) {
            if (plaatsView.getImage() != null) {
                String character = ((ImageCharacter) plaatsView.getImage()).getCharacter();
                if (character.equals(dc.geefSpelerKarakter(SpelerAanZetNummer - 1))) {
                    plaatsView.setImage(null);
                }
            }

        }
    }

    public void doeActie(int plaats, MouseEvent ev) {
        Random rand = new Random();
        int worp;
        geworpenOgen = 0;
        String geworpen;
        boolean actieIsValid = false;
        try {
            actieIsValid = dc.ActieIsValid(plaats);
        } catch (IllegalArgumentException iae) {
            melding = new meldingPopup(iae.getMessage(), this);
            this.getChildren().add(melding);
            StackPane.setAlignment(melding, Pos.TOP_LEFT);
        }
        if (actieIsValid) {
            switch (plaats) {
                case 0:
                case 1:
                case 3:
                    try {
                        verwijderVanPlaats(plaats);
                        dc.doeActie(plaats);
                        if (dc.alleActiesUitgevoerdSpelerAanZet()) {
                            verhoogSpelersDieActiesHebbenVoltooid();
                            if (spelersDieActiesHebbenVoltooid == dc.geefAantalSpelers()) {
                                voedselFase();
                            } else {
                                dc.volgendeSpeler();
                                SpelerAanZetNummer = dc.geefSpelerAanZetSpelerNummer();
                            }

                        }
                        uiUpdate();
                    } catch (IllegalArgumentException iae) {
                        if (dc.alleActiesUitgevoerdSpelerAanZet()) {
                            verhoogSpelersDieActiesHebbenVoltooid();
                            if (spelersDieActiesHebbenVoltooid == dc.geefAantalSpelers()) {
                                voedselFase();
                            } else {
                                dc.volgendeSpeler();
                                SpelerAanZetNummer = dc.geefSpelerAanZetSpelerNummer();
                            }
                        }
                        melding = new meldingPopup(iae.getMessage(), this);
                        this.getChildren().add(melding);
                        StackPane.setAlignment(melding, Pos.TOP_LEFT);
                    }
                    break;
                case 8:
                case 9:
                case 10:
                case 11:
                    if (ev.getButton() == MouseButton.SECONDARY) {
                        verwijderVanPlaats(plaats);
                        dc.verwijderVanPlaats(plaats);
                        if (dc.alleActiesUitgevoerdSpelerAanZet()) {
                            verhoogSpelersDieActiesHebbenVoltooid();
                            if (spelersDieActiesHebbenVoltooid == dc.geefAantalSpelers()) {
                                voedselFase();
                            } else {
                                dc.volgendeSpeler();
                                SpelerAanZetNummer = dc.geefSpelerAanZetSpelerNummer();
                            }
                        }
                        melding = new meldingPopup("Actie niet uigevoerd", this);
                        this.getChildren().add(melding);
                        StackPane.setAlignment(melding, Pos.TOP_LEFT);
                        uiUpdate();
                    } else {
                        try {
                            verwijderVanPlaats(plaats);
                            dc.doeActie(plaats);
                            if (dc.alleActiesUitgevoerdSpelerAanZet()) {
                                verhoogSpelersDieActiesHebbenVoltooid();
                                if (spelersDieActiesHebbenVoltooid == dc.geefAantalSpelers()) {
                                    voedselFase();
                                } else {
                                    dc.volgendeSpeler();
                                    SpelerAanZetNummer = dc.geefSpelerAanZetSpelerNummer();
                                }

                            }
                            uiUpdate();
                        } catch (IllegalArgumentException iae) {
                            if (dc.alleActiesUitgevoerdSpelerAanZet()) {
                                verhoogSpelersDieActiesHebbenVoltooid();
                                if (spelersDieActiesHebbenVoltooid == dc.geefAantalSpelers()) {
                                    voedselFase();
                                } else {
                                    dc.volgendeSpeler();
                                    SpelerAanZetNummer = dc.geefSpelerAanZetSpelerNummer();
                                }
                            }
                            melding = new meldingPopup(iae.getMessage(), this);
                            this.getChildren().add(melding);
                            StackPane.setAlignment(melding, Pos.TOP_LEFT);
                        }
                    }
                break;
                default:
                    geworpen = String.format("Je werpt voor ");
                    worp = rand.nextInt(6) + 1;
                    geworpenOgen += worp;
                    for (int i = 0; i < dc.geefStamledenOpPlaatsSpelerAanZet(plaats) - 1; i++) {
                        geworpen += String.format("%d, ", worp);
                        worp = rand.nextInt(6) + 1;
                        geworpenOgen += worp;
                    }
                    if (dc.spelerAanZetHeeftOngebruiktGereedschap()) {
                        geworpen += String.format("en %d.%nVoor een totaal van %d.%nSelecteer de training die je wil gebruiken door er op te klikken", worp, geworpenOgen);
                        melding = new meldingPopup(geworpen, this, true, true);
                        this.getChildren().add(melding);
                        StackPane.setAlignment(melding, Pos.TOP_LEFT);
                        int[] gereedschap = dc.geefSpelerGereedschap(SpelerAanZetNummer - 1);

                        for (int i = 0; i < gereedschappenView.length; i++) {
                            if (gereedschap[i] > 0) {
                                int o = i, power = gereedschap[i];
                                gereedschappenView[i].setOnMouseClicked((MouseEvent event) -> {
                                    gereedSchapKlikken(o, power, event);
                                });
                            }
                        }

                        switch (plaats) {
                            case 2:
                                jacht.setOnMouseClicked((MouseEvent event) -> {
                                    doeActieOgen(plaats);
                                });
                                break;
                            case 4:
                                bos.setOnMouseClicked((MouseEvent event) -> {
                                    doeActieOgen(plaats);
                                });
                                break;
                            case 5:
                                leemgroeve.setOnMouseClicked((MouseEvent event) -> {
                                    doeActieOgen(plaats);
                                });
                                break;
                            case 6:
                                steengroeve.setOnMouseClicked((MouseEvent event) -> {
                                    doeActieOgen(plaats);
                                });
                                break;
                            case 7:
                                rivier.setOnMouseClicked((MouseEvent event) -> {
                                    doeActieOgen(plaats);
                                });
                                break;
                        }
                    } else {
                        geworpen += String.format("en %d.%nVoor een totaal van %d.", worp, geworpenOgen);
                        melding = new meldingPopup(geworpen, this, true);
                        uiLayout.setTop(melding);
                        this.getChildren().add(melding);
                        StackPane.setAlignment(melding, Pos.TOP_LEFT);
                        doeActieOgen(plaats);
                    }
            }
        }

    }

    public void doeActieOgen(int plaats) {
        dc.doeActie(plaats, geworpenOgen);
        verwijderVanPlaats(plaats);
        for (int i = 0; i < gereedschap.length; i++) {
            if (gereedschap[i]) {
                dc.gebruikGereedschapfiche(i + 1);
                gereedschap[i] = !gereedschap[i];
            }
            gereedschappenView[i].setOnMouseClicked(null);
        }
        if (dc.alleActiesUitgevoerdSpelerAanZet()) {
            verhoogSpelersDieActiesHebbenVoltooid();
            if (getspelersDieActiesHebbenVoltooid() == dc.geefAantalSpelers()) {
                voedselFase();
            } else {
                dc.volgendeSpeler();
                setSpelerAanZetNummer(dc.geefSpelerAanZetSpelerNummer());
            }
        }
        if (this.getChildren().contains(melding)) {
            if (melding.getDuration()) {
                this.getChildren().remove(melding);
            }
        }
        uiUpdate();
    }

    public void voedselFase() {
        dc.voedselProductie();
        uiUpdate();
        if (spelersDieStamledenHebbenGevoed != dc.geefAantalSpelers()) {
            spelersDieStamledenHebbenGevoed++;
            if (dc.HeeftGenoegVoedselspelerAanZet()) {
                dc.voedStamledenSpelerAanZet();
                dc.volgendeSpeler();
                SpelerAanZetNummer = dc.geefSpelerAanZetSpelerNummer();
                voedselFase();
            } else {
                PopUpVoedsel voedsel = new PopUpVoedsel(dc, this);
                this.getChildren().add(voedsel);
            }
        } else if (dc.eindeSpel()) {
            dc.berekenEindscore();
            hs.toonEindScore();
        } else {
            dc.nieuweRonde();
            uiUpdate();
            plaatsFase();
            spelersDieActiesHebbenVoltooid = 0;
            laatstGezettePlaats = -1;
            spelersDieStamledenHebbenGevoed = 0;
            bezettePlaatsen = new int[12];
            bezettePlaatsenVorigeBeurt = bezettePlaatsen.clone();
        }

    }

    public void plaatsFase() {
        bos.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 4);
        });
        leemgroeve.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 5);
        });
        steengroeve.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 6);
        });
        rivier.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 7);
        });
        hut.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 1);
        });
        akker.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 0);
        });
        gereedschapsmaker.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 3);
        });
        jacht.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 2);
        });
        stapel1.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 8);
        });
        stapel2.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 9);
        });
        stapel3.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 10);
        });
        stapel4.setOnMouseClicked((MouseEvent event) -> {
            plaatsStamledenBord(event, 11);
        });
    }

    public void setSpelerAanZetNummer(int spelernummer) {
        this.SpelerAanZetNummer = spelernummer;
    }

    public void verhoogSpelersDieActiesHebbenVoltooid() {
        spelersDieActiesHebbenVoltooid++;
    }

    public int getspelersDieActiesHebbenVoltooid() {
        return spelersDieActiesHebbenVoltooid;
    }

    public void actieFase() {
        melding = new meldingPopup("Actiefase begonnen", this, true);
        this.getChildren().add(melding);
        StackPane.setAlignment(melding, Pos.TOP_LEFT);
        bos.setOnMouseClicked((MouseEvent event) -> {
            doeActie(4, event);
        });
        leemgroeve.setOnMouseClicked((MouseEvent event) -> {
            doeActie(5, event);
        });
        steengroeve.setOnMouseClicked((MouseEvent event) -> {
            doeActie(6, event);
        });
        rivier.setOnMouseClicked((MouseEvent event) -> {
            doeActie(7, event);
        });
        hut.setOnMouseClicked((MouseEvent event) -> {
            doeActie(1, event);
        });
        akker.setOnMouseClicked((MouseEvent event) -> {
            doeActie(0, event);
        });
        gereedschapsmaker.setOnMouseClicked((MouseEvent event) -> {
            doeActie(3, event);
        });
        jacht.setOnMouseClicked((MouseEvent event) -> {
            doeActie(2, event);
        });
        stapel1.setOnMouseClicked((MouseEvent event) -> {
            doeActie(8, event);
        });
        stapel2.setOnMouseClicked((MouseEvent event) -> {
            doeActie(9, event);
        });
        stapel3.setOnMouseClicked((MouseEvent event) -> {
            doeActie(10, event);
        });
        stapel4.setOnMouseClicked((MouseEvent event) -> {
            doeActie(11, event);
        });
    }
}
