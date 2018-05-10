/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import persistentie.ScoreMapper;

/**
 *
 * @author jasperdesmet
 */
public class ScoreRepository {
    private ScoreMapper sm;
    public ScoreRepository(){
        sm = new ScoreMapper();
    }
    public boolean voegToe(Speler speler){
        return sm.voegScoreToe(speler);
    }
}
