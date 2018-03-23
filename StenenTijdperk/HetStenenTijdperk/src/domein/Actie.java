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
public abstract class Actie {
    
    protected int maximumStamleden;
    protected int aantalBezettePlaatsen;
    public Actie(int maximumStamleden){
        this.maximumStamleden = maximumStamleden;
    }
    public abstract void doeActie(Speler speler);
    public abstract void doeActie(Speler speler,int aantalDobbelStenen);
    public int geefBeschikbarePlaatsen(){
      return maximumStamleden - aantalBezettePlaatsen;  
    }
    public void verhoogBezettePlaatsen(int aantalStamleden){
       aantalBezettePlaatsen += aantalStamleden;
    }
    public String geefNaam(){
        return this.getClass().getSimpleName(); 
    }

    public void resetGebruiktePlaatsen() {
        aantalBezettePlaatsen = 0;
    }
}
