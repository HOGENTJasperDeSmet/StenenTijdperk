/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Speler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author jasperdesmet
 */
public class ScoreMapper {
    public boolean voegScoreToe(Speler speler){
        try(Connection conn = DriverManager.getConnection(MapperConfig.JBDC_URL)){
            PreparedStatement queryVoegScoreToe = conn.prepareStatement("INSERT INTO highscores (`speler`, `character`, `score`) VALUES (?,?,?)");
            queryVoegScoreToe.setString(1, speler.getNaam());
            queryVoegScoreToe.setString(2, speler.getCharacter());
            queryVoegScoreToe.setInt(3, speler.getScore());
            queryVoegScoreToe.executeUpdate();
            return true;
        } catch (SQLException ex){
            for (Throwable t : ex){
                t.printStackTrace();
            }
            return false;
        }
    }
}
