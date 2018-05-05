/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import static java.lang.Float.MAX_VALUE;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author jasperdesmet
 */
public class PopUpVoedsel extends StackPane {

    private DomeinController dc;
    private SpelBord sp;
    private VBox vbox;
    private Button grondstoffen, punten, confirm;
    private Text melding, fout;
    private TextField aantalGoud, aantalSteen, aantalHout, aantalLeem;

    public PopUpVoedsel(DomeinController dc, SpelBord sp) {
        this.dc = dc;
        this.sp = sp;

        vbox = new VBox(8);
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setStyle("-fx-background-color: #ffffff");
        vbox.setMaxHeight(0);
        vbox.setMaxWidth(0);
        melding = new Text("Je hebt niet genoeg Voedsel om je stamleden te voeden");
        melding.setWrappingWidth(200);
        melding.setTextAlignment(TextAlignment.CENTER);
        punten = new Button("betalen met punten");
        punten.setMaxWidth(MAX_VALUE);
        grondstoffen = new Button("betalen met grondstoffen");
        grondstoffen.setMaxWidth(MAX_VALUE);
        
        vbox.getChildren().addAll(melding, punten);
        if(dc.heeftGenoegGrondstoffenSpelerAanZet()){
            vbox.getChildren().add(grondstoffen);
        }
        this.getChildren().add(vbox);
        confirm = new Button("Bevestig");
        punten.setOnMouseClicked((MouseEvent event) -> {
            dc.voedStamledenSpelerAanZet(2);
            dc.volgendeSpeler();
            sp.setSpelerAanZetNummer(dc.geefSpelerAanZetSpelerNummer());
            sp.getChildren().remove(this);
            sp.voedselFase();
        });
        grondstoffen.setOnMouseClicked((MouseEvent event) -> {
            melding = new Text(String.format("Je hebt een totaal van %d grondstoffen nodig", dc.nodigVoedselSpelerAanZet()));
            fout = new Text();
            fout.setStyle("-fx-color: red;");
            vbox.getChildren().clear();
            aantalHout = new TextField();
            aantalHout.setPromptText("Aantal hout");
            aantalLeem = new TextField();
            aantalLeem.setPromptText("Aantal leem");
            aantalSteen = new TextField();
            aantalSteen.setPromptText("Aantal steen");
            aantalGoud = new TextField();
            aantalGoud.setPromptText("Aantal goud");

            vbox.getChildren().addAll(melding, aantalHout, aantalLeem, aantalSteen, aantalGoud, fout, confirm);

        });
        confirm.setOnMouseClicked((MouseEvent event2) -> {
            try {
                int[] grondstoffenSpelerAanZet = dc.geefGrondstoffenSpelerAanZet();
                int[] grondstoffen = {
                    !aantalHout.getText().equals("") ? Integer.parseInt(aantalHout.getText()) : 0,
                    !aantalLeem.getText().equals("") ? Integer.parseInt(aantalLeem.getText()) : 0,
                    !aantalSteen.getText().equals("") ? Integer.parseInt(aantalSteen.getText()) : 0,
                    !aantalGoud.getText().equals("") ? Integer.parseInt(aantalGoud.getText()) : 0
                };
                int totaalGrondstoffen = grondstoffen[0] + grondstoffen[1] + grondstoffen[2] + grondstoffen[3];
                if (grondstoffenSpelerAanZet[0] >= grondstoffen[0]) {
                    if (grondstoffenSpelerAanZet[1] >= grondstoffen[1]) {
                        if (grondstoffenSpelerAanZet[2] >= grondstoffen[2]) {
                            if (grondstoffenSpelerAanZet[3] >= grondstoffen[3]) {
                                if (totaalGrondstoffen >= dc.nodigVoedselSpelerAanZet()) {
                                    dc.voedStamledenSpelerAanZet(grondstoffen);
                                    dc.volgendeSpeler();
                                    sp.setSpelerAanZetNummer(dc.geefSpelerAanZetSpelerNummer());
                                    sp.getChildren().remove(this);
                                    sp.voedselFase();
                                } else {
                                    fout.setText("Je hebt meer grondstoffen nodig om alle stamleden te voeden.");
                                }
                            } else {
                                fout.setText("Je hebt niet zoveel goud");
                            }
                        } else {
                            fout.setText("Je hebt niet zoveel steen");
                        }
                    } else {
                        fout.setText("Je hebt niet zoveel leem");
                    }
                } else {
                    fout.setText("Je hebt niet zoveel hout");
                }
            } catch (NumberFormatException nfe) {
                fout.setText("Foute invoer");
            }
        });
    }
}
