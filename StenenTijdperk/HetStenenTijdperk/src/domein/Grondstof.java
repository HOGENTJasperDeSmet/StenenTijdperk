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
public class Grondstof {
    private int waarde;
    private String naam;
    public Grondstof(int waarde, String naam){
        setWaarde(waarde);
        setNaam(naam);
    }

    private void setWaarde(int waarde) {
        this.waarde = waarde;
    }

    private void setNaam(String naam) {
        this.naam = naam;
    }

    public int getWaarde() {
        return waarde;
    }

    public String getNaam() {
        return naam;
    }
}
