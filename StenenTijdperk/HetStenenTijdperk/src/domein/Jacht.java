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
public class Jacht extends Actie{
    
    public Jacht(int maximumStamleden) {
        super(maximumStamleden);
    }
    @Override
    public int geefBeschikbarePlaatsen(){
        return 100;
    }
    @Override
    public String toString(){
        return String.format("Jacht hier mogen zoveel stamleden als men wil%n");
    }
    @Override
    public void doeActie(Speler speler, int geworpenOgen) {
       speler.voegVoedselToe(geworpenOgen / 2);
       speler.verwijderStamledenVanPlaats(this);
    }

    @Override
    public void doeActie(Speler speler) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
