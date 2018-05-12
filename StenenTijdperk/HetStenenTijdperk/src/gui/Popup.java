/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static cui.StenenTijdperkApplicatie.domeinController;
import domein.DomeinController;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author jasperdesmet
 */
public class Popup extends StackPane {

    private Text message;
    private ImageView kader, confirm, decline;
    private DomeinController dc;
    private SpelBord sb;

    public Popup(String tekst, Image Spritesheet, boolean keuzeForm, DomeinController dc, SpelBord sb, int plaats, int geworpenOgen) {
        this.sb = sb;
        this.dc = dc;
        kader = new ImageView(Spritesheet);
        kader.setViewport(new Rectangle2D(283, 0, 262, 111));
        this.getChildren().add(kader);
        confirm = new ImageView(Spritesheet);
        confirm.setViewport(new Rectangle2D(283, 111, 24, 24));
        confirm.setTranslateX(95);
        confirm.setTranslateY(45);
        confirm.setOnMouseClicked((MouseEvent event) -> {
            doeActie(geworpenOgen, plaats);
        });
        this.getChildren().add(confirm);
        if (keuzeForm) {
            decline = new ImageView(Spritesheet);
            decline.setViewport(new Rectangle2D(307, 111, 24, 24));
            decline.setTranslateX(65);
            decline.setTranslateY(45);
            decline.setOnMouseClicked((MouseEvent event) -> {
                doeActie(geworpenOgen, plaats);
            });
            this.getChildren().add(decline);
        }

        message = new Text(tekst);
        message.setWrappingWidth(230);
        message.setTranslateX(-8);
        message.setTranslateY(-23);
        message.setFont(Font.font("Karma future", 16));
        this.getChildren().add(message);
    }

    public void doeActie(int geworpenOgen, int plaats) {
        dc.doeActie(plaats, geworpenOgen);
        sb.verwijderVanPlaats(plaats);
        if (dc.alleActiesUitgevoerdSpelerAanZet()) {
            sb.verhoogSpelersDieActiesHebbenVoltooid();
            if (sb.getspelersDieActiesHebbenVoltooid()== dc.geefAantalSpelers()) {
                sb.voedselFase();
            } else {
               dc.volgendeSpeler();
                sb.setSpelerAanZetNummer(dc.geefSpelerAanZetSpelerNummer()); 
            }
            
        }
        sb.uiUpdate();
        sb.getChildren().remove(this);
    }

}
