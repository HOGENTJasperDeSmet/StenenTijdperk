/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import persistentie.SpelMapper;

/**
 *
 * @author jasperdesmet
 */
public class SpelRepository {
    private SpelMapper sm;
    public SpelRepository(){
        this.sm = new SpelMapper();
    }
    public boolean slaSpelOp(Spel spel){
        return sm.slaSpelOp(spel);
    }

    public Spel geefOpgeslagenSpel(int spelId) {
        return sm.geefOpgeslagenSpel(spelId);
    }

    public boolean slaNieuwSpelOp(Spel spel,String naam) {
        return sm.slaNieuwSpelOp(spel,naam);
    }

    public String[][] geefAlleSpellen() {
        return sm.geefSpellenInfo();
    }
}
