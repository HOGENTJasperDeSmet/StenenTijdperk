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
import java.util.ArrayList;

/**
 *
 * @author jasperdesmet
 */
public class SpelerMapper {

    public ArrayList<Speler> geefSpelers(int spelId) {
        ArrayList<Speler> spelers = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(MapperConfig.JBDC_URL)) {
            PreparedStatement queryGeefSpeler = conn.prepareStatement("SELECT * FROM speler WHERE spelId = ?;");
            queryGeefSpeler.setInt(1, spelId);
            try(ResultSet rs = queryGeefSpeler.executeQuery()){
                while(rs.next()){
                    spelers.add(new Speler(rs.getInt("spelerNummer"),rs.getString("naam"),rs.getString("character")
                            ,rs.getInt("aantalVoedsel"),rs.getInt("aantalHout"),rs.getInt("aantalLeem"),rs.getInt("aantalSteen"),rs.getInt("aantalGoud")
                            ,rs.getInt("score"),rs.getInt("voedselProductie"),rs.getInt("aantalStamleden"),rs.getInt("gereedschap1Kracht"),rs.getInt("gereedschap2Kracht"),rs.getInt("gereedschap3Kracht")));
                }
            }
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        
        return spelers;
    }

    public boolean slaSpelersOp(ArrayList<Speler> spelers, int spelId) {
        try (Connection conn = DriverManager.getConnection(MapperConfig.JBDC_URL)) {
            for (Speler speler : spelers) {
                PreparedStatement queryVoegSpelToe = conn.prepareStatement("INSERT INTO `dbstenentijdperk`.`speler`\n"
                        + "(`spelerNummer`,\n"
                        + "`spelId`,\n"
                        + "`naam`,\n"
                        + "`character`,\n"
                        + "`aantalVoedsel`,\n"
                        + "`aantalHout`,\n"
                        + "`aantalLeem`,\n"
                        + "`aantalSteen`,\n"
                        + "`aantalGoud`,\n"
                        + "`score`,\n"
                        + "`voedselProductie`,\n"
                        + "`aantalStamleden`,\n"
                        + "`gereedschap1Kracht`,\n"
                        + "`gereedschap2Kracht`,\n"
                        + "`gereedschap3Kracht`)\n"
                        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                queryVoegSpelToe.setInt(1, speler.getSpelerNummer());
                queryVoegSpelToe.setInt(2, spelId);
                queryVoegSpelToe.setString(3, speler.getNaam());
                queryVoegSpelToe.setString(4, speler.getCharacter());
                queryVoegSpelToe.setInt(5, speler.getAantalVoedsel());
                queryVoegSpelToe.setInt(6, speler.getAantalHout());
                queryVoegSpelToe.setInt(7, speler.getAantalLeem());
                queryVoegSpelToe.setInt(8, speler.getAantalSteen());
                queryVoegSpelToe.setInt(9, speler.getAantalGoud());
                queryVoegSpelToe.setInt(10, speler.getScore());
                queryVoegSpelToe.setInt(11, speler.getVoedselProductie());
                queryVoegSpelToe.setInt(12, speler.geefAantalStamleden());
                queryVoegSpelToe.setInt(13, speler.geefGereedschap()[0]);
                queryVoegSpelToe.setInt(14, speler.geefGereedschap()[1]);
                queryVoegSpelToe.setInt(15, speler.geefGereedschap()[2]);
                queryVoegSpelToe.executeUpdate();
            }

            return true;
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        }
    }

    void updateSpelers(ArrayList<Speler> spelers, int spelId) {
        try (Connection conn = DriverManager.getConnection(MapperConfig.JBDC_URL)) {
            for (Speler speler : spelers) {
                PreparedStatement queryVoegSpelToe = conn.prepareStatement("UPDATE `dbstenentijdperk`.`speler`\n"
                        + "SET\n"
                        + "`aantalVoedsel` = ?,\n"
                        + "`aantalHout` = ?,\n"
                        + "`aantalLeem` = ?,\n"
                        + "`aantalSteen` = ?,\n"
                        + "`aantalGoud` = ?,\n"
                        + "`score` = ?,\n"
                        + "`voedselProductie` = ?,\n"
                        + "`aantalStamleden` = ?,\n"
                        + "`gereedschap1Kracht` = ?,\n"
                        + "`gereedschap2Kracht` = ?,\n"
                        + "`gereedschap3Kracht` = ?\n"
                        + "WHERE `spelerNummer` = ? AND `spelId` = ?;");
                
                queryVoegSpelToe.setInt(1, speler.getAantalVoedsel());
                queryVoegSpelToe.setInt(2, speler.getAantalHout());
                queryVoegSpelToe.setInt(3, speler.getAantalLeem());
                queryVoegSpelToe.setInt(4, speler.getAantalSteen());
                queryVoegSpelToe.setInt(5, speler.getAantalGoud());
                queryVoegSpelToe.setInt(6, speler.getScore());
                queryVoegSpelToe.setInt(7, speler.getVoedselProductie());
                queryVoegSpelToe.setInt(8, speler.geefAantalStamleden());
                queryVoegSpelToe.setInt(9, speler.geefGereedschap()[0]);
                queryVoegSpelToe.setInt(10, speler.geefGereedschap()[1]);
                queryVoegSpelToe.setInt(11, speler.geefGereedschap()[2]);
                queryVoegSpelToe.setInt(12, speler.getSpelerNummer());
                queryVoegSpelToe.setInt(13, spelId);
                queryVoegSpelToe.executeUpdate();
            }
            
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
    }

}
