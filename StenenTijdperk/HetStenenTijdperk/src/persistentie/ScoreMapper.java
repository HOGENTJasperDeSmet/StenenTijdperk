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
import java.sql.ResultSet;
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

    public String[][] geefAlleScores() {
        String scores[][];
        int rijen = 0;
        try(Connection conn = DriverManager.getConnection(MapperConfig.JBDC_URL)){
            PreparedStatement queryGeefRijen = conn.prepareStatement("SELECT count(*) FROM highscores");
            try(ResultSet rs = queryGeefRijen.executeQuery()){
                if (rs.next()){
                   rijen = rs.getInt(1); 
                }
                
            }
            PreparedStatement queryGeefAlleScores = conn.prepareStatement("SELECT * FROM highscores ORDER BY score DESC");
            try(ResultSet rs = queryGeefAlleScores.executeQuery()){
                scores = new String[rijen][rs.getMetaData().getColumnCount() - 1];
                int rij = 0;
                while(rs.next()){
                    scores[rij][0] = rs.getString("speler");
                    scores[rij][1] = "" + rs.getInt("score");
                    rij++;        
                }
            }
            return scores;
        }catch (SQLException ex){
            for (Throwable t : ex){
                t.printStackTrace();
            }
            return scores = new String[1][1];
        }
    }
}
