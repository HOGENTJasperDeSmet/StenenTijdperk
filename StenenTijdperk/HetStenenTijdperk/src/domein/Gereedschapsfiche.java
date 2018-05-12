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
public class Gereedschapsfiche {
    private int kracht;
    private boolean gebruikt = false;
    public Gereedschapsfiche(){
        
    }
    public Gereedschapsfiche(int kracht){
        this.kracht = kracht;
    }
    public void verhoogKracht(){
        kracht++;
    }
    public int getKracht(){
        return kracht;
    }
    public boolean getGebruikt(){
        return gebruikt;
    }

    public void gebruik() {
        gebruikt = true;
    }
    public void reset(){
        gebruikt = false;
    }
}
