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
public class Akker extends Actie{ 
    public Akker(int maximumStamleden) {
        super(maximumStamleden);
    }
    @Override
    public String toString(){
        int beschikbaar = this.geefBeschikbarePlaatsen();
        return String.format("Akker met %d vrije %s%n",beschikbaar, beschikbaar == 1 ? "plaats" : "plaatsen");
    }

    public void doeActie(Speler speler) {
        speler.verhoogVoedselProductie();
        speler.verwijderStamledenVanPlaats(this);
    }

    @Override
    public void doeActie(Speler speler, int aantalDobbelStenen) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
