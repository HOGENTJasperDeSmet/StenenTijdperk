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
public class Gereedschapsmaker extends Actie{
    
    public Gereedschapsmaker(int maximumStamleden) {
        super(maximumStamleden);
    }
    @Override
    public String toString(){
        int beschikbaar = this.geefBeschikbarePlaatsen();
        return String.format("Gereedschapsmaker met %d vrije %s%n",beschikbaar, beschikbaar == 1 ? "plaats" : "plaatsen");
    }
    @Override
    public void doeActie(Speler speler) {
        speler.verhoogGereedschapsfiche();
        speler.verwijderStamledenVanPlaats(this);
    }

    @Override
    public void doeActie(Speler speler, int aantalDobbelStenen) {
        throw new UnsupportedOperationException();
    }
}
