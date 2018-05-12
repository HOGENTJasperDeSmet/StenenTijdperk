/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Spel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SpelMapper {

    private SpelerMapper sm = new SpelerMapper();
    private HutkaartMapper hm = new HutkaartMapper();

    public boolean slaNieuwSpelOp(Spel spel,String naam) {
        int spelId = 0;
        spel.setNaam(naam);
        try (Connection conn = DriverManager.getConnection(MapperConfig.JBDC_URL)) {
            PreparedStatement queryVoegSpelToe = conn.prepareStatement("INSERT INTO spel (`spelNaam`,`spelerAanzet`) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            queryVoegSpelToe.setString(1, naam);
            queryVoegSpelToe.setInt(2, spel.geefSpelerAanZetSpelerNummer());
            queryVoegSpelToe.executeUpdate();
            try (ResultSet rs = queryVoegSpelToe.getGeneratedKeys()) {
                if (rs.next()) {
                    spelId = rs.getInt(1);
                    spel.setId(spelId);
                }
            }
            sm.slaSpelersOp(spel.getSpelers(), spelId);
            hm.slaStapelsOp(spel.getStapels(), spelId);
            return true;
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        }
    }

    public boolean slaSpelOp(Spel spel) {
        try (Connection conn = DriverManager.getConnection(MapperConfig.JBDC_URL)) {
            PreparedStatement slaSpelOp = conn.prepareStatement("UPDATE spel SET `spelNaam` = ?,`spelerAanZet` = ? WHERE spelId = ?");
            slaSpelOp.setString(1, spel.getSpelNaam());
            slaSpelOp.setInt(2, spel.geefSpelerAanZetSpelerNummer());
            slaSpelOp.setInt(3, spel.getSpelId());
            slaSpelOp.executeUpdate();
            sm.updateSpelers(spel.getSpelers(), spel.getSpelId());
            hm.updateKaarten(spel.getStapels(), spel.getSpelId());
            return true;
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        }
    }
    public String[][] geefSpellenInfo(){
        String[][] spelInfo = null;
        int rijen = 0;
        int i = 0;
        try (Connection conn = DriverManager.getConnection(MapperConfig.JBDC_URL)) {
            PreparedStatement queryGeefRijen = conn.prepareStatement("SELECT count(`spelId`) FROM spel");
            try(ResultSet rs = queryGeefRijen.executeQuery()){
                if (rs.next()){
                   rijen = rs.getInt(1); 
                }
                
            }
            spelInfo = new String[rijen][2];
            PreparedStatement queryGeefAlleSpellen = conn.prepareStatement("SELECT `spelId`,`spelNaam` FROM spel;");
            try (ResultSet rs = queryGeefAlleSpellen.executeQuery()) {
                while(rs.next()) {
                    spelInfo[i][0] = "" + rs.getInt(1);
                    spelInfo[i][1] = rs.getString(2);
                    i++;
                }
            }
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        return spelInfo;
    }
    public Spel geefOpgeslagenSpel(int spelId) {
        Spel spel = null;
        try (Connection conn = DriverManager.getConnection(MapperConfig.JBDC_URL)) {
            PreparedStatement queryGeefSpel = conn.prepareStatement("SELECT * FROM spel WHERE spelId = ?;");
            queryGeefSpel.setInt(1, spelId);
            try (ResultSet rs = queryGeefSpel.executeQuery()) {
                if (rs.next()) {
                    spel = new Spel(sm.geefSpelers(spelId), hm.geefStapels(spelId), rs.getInt("spelerAanZet"), rs.getInt("spelId"), rs.getString("spelNaam"));
                }
            }
            return spel;
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        return spel;
    }
}
