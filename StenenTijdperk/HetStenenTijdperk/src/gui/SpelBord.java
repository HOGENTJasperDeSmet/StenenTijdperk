/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ArrayList;
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

public class SpelBord extends StackPane{
    private DomeinController dc;
    private ImageView kaartView, karakterView, grondstoffenView[],grondstofIconView[],gereedschappenView[], dragonballView[], kleinKarakterView[],
            infoAndereSpelersKader,infoAndereSpeler[],bosView[],steengroeveView[],meerView[],leemgroeveView[],hutView[],akkerView,gereedschapView;
    private ArrayList<ImageView> jachtView = new ArrayList<>();
    private StackPane infoAndereSpelers, hutkaart[],grondstof[];
    private Pane bos,leemgroeve,steengroeve,rivier,akker,hut,gereedschapsmaker,jacht;
    private Text grondstofText[];
    private BorderPane uiLayout;
    private VBox infoSpelerAanZetDetail, hutkaarten,infoAndereSpelersVbox;
    private HBox gereedschappen,grondstoffen, infoSpelerAanZet,hutkaartenInhoud[];
    private Image uiSpriteSheet = new Image(getClass().getResourceAsStream("/assets/gui.gif"));
    private Image goku = new Image(getClass().getResourceAsStream("/assets/GokuStaan.png"));
    private Image characterFacesSmall = new Image(getClass().getResourceAsStream("/assets/characterFacesSmall.png"));
    private int[] bezettePlaatsen;
    private int SpelerAanZetNummer;
    public SpelBord(DomeinController dc){
        this.dc = dc;
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
        switch(dc.geefSpelerKarakter(SpelerAanZetNummer - 1)){
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
        for(int i = 0; i < gereedschappenView.length; i++){
            gereedschappenView[i] = new ImageView(uiSpriteSheet);
            gereedschappenView[i].setViewport(new Rectangle2D(0, 119, 133, 23));
            gereedschappen.getChildren().add(gereedschappenView[i]);
        }
        grondstof = new StackPane[5];
        grondstoffenView = new ImageView[5];
        grondstofText = new Text[5];
        
        int[] grondstoffenSpeler = dc.geefGrondstoffenSpelerAanZet();
        for(int i = 0; i < grondstoffenView.length; i++){
            grondstof[i] = new StackPane();
            grondstofText[i] = new Text("" + grondstoffenSpeler[i]);
            grondstofText[i].setFont(Font.font("Karma future",16));
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
        grondstofIconView[3] = new ImageView(new Image(getClass().getResourceAsStream("/assets/leem.png")));
        grondstofIconView[4] = new ImageView(new Image(getClass().getResourceAsStream("/assets/voedsel.png")));
        for(int i = 0; i < grondstofIconView.length; i++){
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
        for(int i = 0; i < infoAndereSpeler.length; i++){
            infoAndereSpeler[i] = new ImageView();
            infoAndereSpeler[i].setImage(characterFacesSmall);
            switch(dc.geefSpelerKarakter((SpelerAanZetNummer + i)  % dc.geefAantalSpelers())){
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
        infoAndereSpelersKader.setViewport(new Rectangle2D(147, 2, 43, 182));
        infoAndereSpelers.getChildren().addAll(infoAndereSpelersVbox,infoAndereSpelersKader);
        
        //bos plaatsen
        bosView = new ImageView[7];
        for(int i = 0; i < bosView.length; i++){
            bosView[i] = new ImageView();
        }
        bosView[0].setTranslateX(-82);
        bosView[0].setTranslateY(-172);
        bosView[1].setTranslateX(-59);
        bosView[1].setTranslateY(-152);
        bosView[2].setTranslateX(-18);
        bosView[2].setTranslateY(-152);
        bosView[3].setTranslateX(47);
        bosView[3].setTranslateY(-182);
        bosView[3].setScaleX(-1);
        bosView[4].setTranslateX(95);
        bosView[4].setTranslateY(-162);
        bosView[4].setScaleX(-1);
        bosView[5].setTranslateX(118);
        bosView[5].setTranslateY(-182);
        bosView[5].setScaleX(-1);
        bosView[6].setTranslateX(160);
        bosView[6].setTranslateY(-182);
        bosView[6].setScaleX(-1);
        
        //steengroeve plaatsen
        steengroeveView = new ImageView[7];
        for(int i = 0; i < bosView.length; i++){
            steengroeveView[i] = new ImageView();
        }
        steengroeveView[0].setTranslateX(165);
        steengroeveView[0].setTranslateY(-140);
        steengroeveView[1].setTranslateX(185);
        steengroeveView[1].setTranslateY(-130);
        steengroeveView[2].setTranslateX(195);
        steengroeveView[2].setTranslateY(-120);
        steengroeveView[3].setTranslateX(200);
        steengroeveView[3].setTranslateY(-110);
        steengroeveView[4].setTranslateX(205);
        steengroeveView[4].setTranslateY(-100);
        steengroeveView[5].setTranslateX(225);
        steengroeveView[5].setTranslateY(-90);
        steengroeveView[6].setTranslateX(250);
        steengroeveView[6].setTranslateY(-80);
        
        //meer plaatsem
        meerView = new ImageView[7];
        for(int i = 0; i < meerView.length; i++){
            meerView[i] = new ImageView();
        }
        meerView[0].setTranslateX(-40);
        meerView[0].setTranslateY(-40);
        meerView[1].setTranslateX(-35);
        meerView[1].setTranslateY(-30);
        meerView[2].setTranslateX(-25);
        meerView[2].setTranslateY(-20);
        meerView[3].setTranslateX(-10);
        meerView[3].setTranslateY(-15);
        meerView[4].setTranslateX(165);
        meerView[4].setTranslateY(-70);
        meerView[4].setScaleX(-1);
        meerView[5].setTranslateX(165);
        meerView[5].setTranslateY(-50);
        meerView[5].setScaleX(-1);
        meerView[6].setTranslateX(163);
        meerView[6].setTranslateY(-35);
        meerView[6].setScaleX(-1);
        
        //leemgroeve plaatsen
        leemgroeveView = new ImageView[7];
        for(int i = 0; i < leemgroeveView.length; i++){
            leemgroeveView[i] = new ImageView();
        }
        leemgroeveView[0].setTranslateX(-200);
        leemgroeveView[0].setTranslateY(60);
        leemgroeveView[1].setTranslateX(-160);
        leemgroeveView[1].setTranslateY(65);
        leemgroeveView[1].setScaleX(-1);
        leemgroeveView[2].setTranslateX(-185);
        leemgroeveView[2].setTranslateY(80);
        leemgroeveView[3].setTranslateX(-125);
        leemgroeveView[3].setTranslateY(80);
        leemgroeveView[3].setScaleX(-1);
        leemgroeveView[4].setTranslateX(-175);
        leemgroeveView[4].setTranslateY(100);
        leemgroeveView[5].setTranslateX(-145);
        leemgroeveView[5].setTranslateY(120);
        leemgroeveView[6].setTranslateX(-120);
        leemgroeveView[6].setTranslateY(100);
        leemgroeveView[6].setScaleX(-1);
      
        //hut plaatsen
        hutView = new ImageView[2];
        for(int i = 0; i < hutView.length; i++){
            hutView[i] = new ImageView();
        }
        hutView[0].setTranslateX(-270);
        hutView[0].setTranslateY(20);
        hutView[1].setTranslateX(-240);
        hutView[1].setTranslateY(10);
        hutView[1].setScaleX(-1);
        
        //akker
        akkerView = new ImageView();
        akkerView.setTranslateX(270);
        akkerView.setTranslateY(80);
        
        //gereedschap
        gereedschapView = new ImageView();
        gereedschapView.setTranslateX(-270);
        gereedschapView.setTranslateY(-110);
        gereedschapView.setScaleX(-1);
        
        this.getChildren().addAll(bosView[0],bosView[1],bosView[2],bosView[3],bosView[4],bosView[5],bosView[6],
                steengroeveView[0],steengroeveView[1],steengroeveView[2],steengroeveView[3],steengroeveView[4],steengroeveView[5],steengroeveView[6],
                meerView[0],meerView[1],meerView[2],meerView[3],meerView[4],meerView[5],meerView[6],
                leemgroeveView[0],leemgroeveView[1],leemgroeveView[2],leemgroeveView[3],leemgroeveView[4],leemgroeveView[5],leemgroeveView[6],
                hutView[0],hutView[1], akkerView,gereedschapView);
        
        //hutkaarten aanmaken
        hutkaart = new StackPane[4];
        hutkaartenInhoud = new HBox[4];
        hutkaarten = new VBox();
        String[][] info = dc.geefInfoKaarten();
        for(int i = 0; i < hutkaart.length; i++){
            hutkaart[i] = new StackPane();
            hutkaartenInhoud[i] = new HBox(1);
            
            ImageView kader = new ImageView(uiSpriteSheet);
            kader.setViewport(new Rectangle2D(0, 0, 138, 26));
            ImageView dragonball = new ImageView(uiSpriteSheet);
            dragonball.setViewport(new Rectangle2D(2, 74, 21, 22));
            
            ImageView[] kost = new ImageView[3];
            kost[0] = new ImageView(new Image(getClass().getResourceAsStream("/assets/"+ info[i][1].toLowerCase() +".png")));
            kost[1] = new ImageView(new Image(getClass().getResourceAsStream("/assets/"+ info[i][2].toLowerCase() +".png")));
            kost[2] = new ImageView(new Image(getClass().getResourceAsStream("/assets/"+ info[i][3].toLowerCase() +".png")));
            dragonball.setViewport(new Rectangle2D(2, 74, 21, 22));
            Text waarde = new Text(info[i][0]);
            waarde.setFont(Font.font("Karma future",16));
            
            hutkaartenInhoud[i].setMaxWidth(0);
            hutkaartenInhoud[i].setMaxHeight(0);
            hutkaartenInhoud[i].getChildren().addAll(dragonball,kost[0],kost[1],kost[2],waarde);
            
            hutkaart[i].getChildren().addAll(kader,hutkaartenInhoud[i]);
            hutkaarten.getChildren().addAll(hutkaart[i]);
        }
        hutkaarten.setTranslateX(-405);
        hutkaarten.setTranslateY(430);
        
        
        this.getChildren().add(hutkaarten);
        
        bezettePlaatsen = new int[12];
        
        bos = new Pane();
        bos.setMaxWidth(262);
        bos.setMaxHeight(125);
        bos.setStyle("-fx-background-color: #09fd5ec4");
        bos.setTranslateY(-190);
        bos.setTranslateX(25);
        bos.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
        });
        bos.setOnMouseEntered((MouseEvent event) -> {
            this.setCursor(Cursor.HAND);
        });
        bos.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                if(bezettePlaatsen[4] != 0){
                     bezettePlaatsen[4] -= 1;
                     bosView[bezettePlaatsen[4]].setImage(null);
                }
            } else {
                bezettePlaatsen[4] += 1;
                if(dc.plaatsIsValid(4,bezettePlaatsen[4])){
                    switch(dc.geefSpelerKarakter(SpelerAanZetNummer - 1)){
                    case "krillin":
                        bosView[bezettePlaatsen[4] - 1].setImage(goku);
                        bosView[bezettePlaatsen[4] - 1].setViewport(new Rectangle2D(0,0,20,35));
                    case "goku":
                        bosView[bezettePlaatsen[4] - 1].setImage(goku);
                        bosView[bezettePlaatsen[4] - 1].setViewport(new Rectangle2D(0,0,20,35));
                    case "jackieChun":
                        bosView[bezettePlaatsen[4] - 1].setImage(goku);
                        bosView[bezettePlaatsen[4] - 1].setViewport(new Rectangle2D(0,0,20,35));
                    case "chiaotzu":
                        bosView[bezettePlaatsen[4] - 1].setImage(goku);
                        bosView[bezettePlaatsen[4] - 1].setViewport(new Rectangle2D(0,0,20,35));
                    }
                }
            }
            
            
        });
        steengroeve = new Pane();
        steengroeve.setMaxWidth(154);
        steengroeve.setMaxHeight(124);
        steengroeve.setStyle("-fx-background-color: #09fd5ec4");
        steengroeve.setTranslateY(-120);
        steengroeve.setTranslateX(250);
        steengroeve.setOnMouseExited((MouseEvent event) -> {
            this.setCursor(Cursor.DEFAULT); //Change cursor to hand
        });
        steengroeve.setOnMouseEntered((MouseEvent event) -> {
            this.setCursor(Cursor.HAND);
        });
        steengroeve.setOnMouseClicked((MouseEvent event) -> {
            this.setCursor(Cursor.HAND);
        });
        leemgroeve = new Pane();
        leemgroeve.setMaxWidth(151);
        leemgroeve.setMaxHeight(121);
        leemgroeve.setStyle("-fx-background-color: #09fd5ec4");
        leemgroeve.setTranslateY(90);
        leemgroeve.setTranslateX(-150);
        rivier = new Pane();
        rivier.setMaxWidth(207);
        rivier.setMaxHeight(130);
        rivier.setStyle("-fx-background-color: #09fd5ec4");
        rivier.setTranslateY(-35);
        rivier.setTranslateX(60);
        gereedschapsmaker = new Pane();
        gereedschapsmaker.setMaxWidth(90);
        gereedschapsmaker.setMaxHeight(178);
        gereedschapsmaker.setStyle("-fx-background-color: #09fd5ec4");
        gereedschapsmaker.setTranslateY(-180);
        gereedschapsmaker.setTranslateX(-300);
        hut = new Pane();
        hut.setMaxWidth(109);
        hut.setMaxHeight(95);
        hut.setStyle("-fx-background-color: #09fd5ec4");
        hut.setTranslateY(-10);
        hut.setTranslateX(-285);
        akker = new Pane();
        akker.setMaxWidth(212);
        akker.setMaxHeight(126);
        akker.setStyle("-fx-background-color: #09fd5ec4");
        akker.setTranslateY(110);
        akker.setTranslateX(285);
        jacht = new Pane();
        jacht.setMaxWidth(153);
        jacht.setMaxHeight(134);
        jacht.setStyle("-fx-background-color: #09fd5ec4");
        jacht.setTranslateY(160);
        jacht.setTranslateX(80);
        
        this.getChildren().addAll(bos,steengroeve, leemgroeve,rivier,gereedschapsmaker,hut,akker,jacht);
        
        uiLayout = new BorderPane();
        uiLayout.setBottom(infoSpelerAanZet);
        uiLayout.setLeft(infoAndereSpelers);
        uiLayout.setPickOnBounds(false);
        
        this.getChildren().add(uiLayout);
    }
}
