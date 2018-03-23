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
public class Stamlid {
    private Actie plaats;

    public Actie getPlaats() {
        return plaats;
    }
    public void plaatsOp(Actie plaats){
        this.plaats = plaats;
    }
}
