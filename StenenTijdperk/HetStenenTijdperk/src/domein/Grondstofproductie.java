 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

/**
 *
 * @author jasperdesmet
 */
public class Grondstofproductie extends Actie{
    private Grondstof grondstof;
    public Grondstofproductie(int maximumStamleden,Grondstof grondstof) {
        super(maximumStamleden);
        this.grondstof = grondstof;
    }
    
    @Override
    public String geefNaam(){
        switch (grondstof.getNaam()){
            case "Hout":
                return String.format("Bos");
            case "Leem":
                return String.format("Leemgroeve");
            case "Steen":
                return String.format("Steengroeve");
            case "Goud":
                return String.format("Rivier");
        }
        return "Er is een fout";
    }
    @Override
    public String toString(){
        int beschikbaar = this.geefBeschikbarePlaatsen();
        switch (grondstof.getNaam()){
            case "Hout":
                return String.format("Bos met %d vrije %s%n",beschikbaar, beschikbaar == 0 ? "plaats" : "plaatsen");
            case "Leem":
                return String.format("Leemgroeve met %d vrije %s%n",beschikbaar, beschikbaar == 0 ? "plaats" : "plaatsen");
            case "Steen":
                return String.format("Steengroeve met %d vrije %s%n",beschikbaar, beschikbaar == 0 ? "plaats" : "plaatsen");
            case "Goud":
                return String.format("Rivier met %d vrije %s%n",beschikbaar, beschikbaar == 0 ? "plaats" : "plaatsen");
        }            
        return "Er is een fout";
    }
    @Override
    public void doeActie(Speler speler) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void doeActie(Speler speler, int geworpenOgen) {
        speler.voegGrondstofToe(geworpenOgen / grondstof.getWaarde(), grondstof.getNaam());
        speler.verwijderStamledenVanPlaats(this);
    }
}
