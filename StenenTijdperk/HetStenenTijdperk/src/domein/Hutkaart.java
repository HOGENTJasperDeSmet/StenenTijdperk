/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.ArrayList;

/**
 *
 * @author jasperdesmet
 */
public class Hutkaart extends Actie{
    private Grondstof[] kosten = new Grondstof[3];
    private int stapel;
    boolean actief;
    public Hutkaart(ArrayList<Grondstof> kosten, int stapel, boolean actief){
        this(kosten, stapel);
        this.actief = actief;
    }
    public Hutkaart(ArrayList<Grondstof> kosten, int stapel) {
        super(1);
        this.kosten[0] = kosten.get(0);
        this.kosten[1] = kosten.get(1);
        this.kosten[2] = kosten.get(2);
        this.stapel = stapel;
        actief = false;
    }
    public void Actief(){
        actief = true;
    }
    public boolean getActief(){
        return actief;
    }
    @Override
    public String toString(){
        int beschikbaar = this.geefBeschikbarePlaatsen();
        return String.format("Hutkaart van stapel %d waarde: %d kost 1: %s 2: %s 3: %s%n",stapel,berekenWaarde(),kosten[0].getNaam(),kosten[1].getNaam(),kosten[2].getNaam(),beschikbaar,beschikbaar == 0 ? "plaats" : "plaatsen");
    }
    public int berekenWaarde(){
        return kosten[0].getWaarde() + kosten[1].getWaarde() + kosten[2].getWaarde();
    }
    public String geefKost1naam(){
        return kosten[0].getNaam();  
    }
    public String geefKost2naam(){
        return kosten[1].getNaam();  
    }
    public String geefKost3naam(){
        return kosten[2].getNaam();  
    }
    @Override
    public void doeActie(Speler speler) {
        int goudKost = 0,steenKost = 0,leemKost = 0,houtKost = 0;
        for(Grondstof g : kosten){
            switch(g.getNaam()){
                case "Hout":
                    houtKost++;
                    break;
                case "Steen":
                    steenKost++;
                    break;
                case "Leem":
                    leemKost++;
                    break;
                case "Goud":
                    goudKost++;
                    break;
            }
        }
        speler.verwijderStamledenVanPlaats(this);
        if(speler.getAantalGoud() >= goudKost){
            if(speler.getAantalHout() >= houtKost){
                if(speler.getAantalLeem() >= leemKost){
                    if(speler.getAantalSteen() >= steenKost){
                        speler.verhoogScore(berekenWaarde());
                        speler.gebruikGrondstoffen(goudKost, houtKost, leemKost, steenKost);
                        actief = false;
                        return;
                    }
                }
            }
        }
        throw new IllegalArgumentException("Je hebt de juiste grondstoffen niet");
    }
    @Override
    public String geefNaam(){
        return "Hutkaart van stapel " + stapel; 
    }
    @Override
    public void doeActie(Speler speler, int aantalDobbelStenen) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
