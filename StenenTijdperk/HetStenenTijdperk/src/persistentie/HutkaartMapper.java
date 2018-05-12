/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Grondstof;
import domein.Hutkaart;
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
public class HutkaartMapper {

    public Hutkaart[][] geefStapels(int spelId) {
        Grondstof grondstoffen[] = new Grondstof[4];
        grondstoffen[0] = new Grondstof(3, "Hout");
        grondstoffen[1] = new Grondstof(4, "Leem");
        grondstoffen[2] = new Grondstof(5, "Steen");
        grondstoffen[3] = new Grondstof(6, "Goud");
        Hutkaart[][] stapels = new Hutkaart[4][7];
        try (Connection conn = DriverManager.getConnection(MapperConfig.JBDC_URL)) {
            PreparedStatement queryGeefSpeler = conn.prepareStatement("SELECT * FROM hutkaart WHERE spelId = ?;");
            queryGeefSpeler.setInt(1, spelId);
            try (ResultSet rs = queryGeefSpeler.executeQuery()) {
                while (rs.next()) {
                    String[] grondstofStrings = new String[3];
                    ArrayList<Grondstof> grondstoffenList = new ArrayList<>();
                    grondstofStrings[0] = rs.getString("kost1");
                    grondstofStrings[1] = rs.getString("kost2");
                    grondstofStrings[2] = rs.getString("kost3");
                    for (int i = 0; i < grondstofStrings.length; i++) {
                        switch (grondstofStrings[i]) {
                            case "Hout":
                                grondstoffenList.add(grondstoffen[0]);
                                break;
                            case "Leem":
                                grondstoffenList.add(grondstoffen[1]);
                                break;
                            case "Steen":
                                grondstoffenList.add(grondstoffen[2]);
                                break;
                            case "Goud":
                                grondstoffenList.add(grondstoffen[3]);
                                break;
                        }
                    }
                    stapels[rs.getInt("stapel")][rs.getInt("kaartNummer")] = new Hutkaart(grondstoffenList, rs.getInt("stapel"),rs.getBoolean("actief"));
                }
            }
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        return stapels;
    }

    public boolean slaStapelsOp(Hutkaart[][] stapels, int spelId) {
        try (Connection conn = DriverManager.getConnection(MapperConfig.JBDC_URL)) {
            int stapelNummer = 0;
            int kaartNummer = 0;
            for (Hutkaart[] stapel : stapels) {
                for (Hutkaart hutkaart : stapel) {
                    PreparedStatement queryVoegkaartToe = conn.prepareStatement("INSERT INTO `dbstenentijdperk`.`hutkaart`"
                            + " (`spelId`,`stapel`,`kaartNummer`,`actief`,`kost1`,`kost2`,`kost3`) VALUES (?,?,?,?,?,?,?);");
                    queryVoegkaartToe.setInt(1, spelId);
                    queryVoegkaartToe.setInt(2, stapelNummer);
                    queryVoegkaartToe.setInt(3, kaartNummer);
                    queryVoegkaartToe.setBoolean(4, hutkaart.getActief());
                    queryVoegkaartToe.setString(5, hutkaart.geefKost1naam());
                    queryVoegkaartToe.setString(6, hutkaart.geefKost2naam());
                    queryVoegkaartToe.setString(7, hutkaart.geefKost3naam());
                    queryVoegkaartToe.executeUpdate();
                    kaartNummer++;
                }
                kaartNummer = 0;
                stapelNummer++;
            }
            return true;
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        }
    }

    public void updateKaarten(Hutkaart[][] stapels, int spelId) {
        try (Connection conn = DriverManager.getConnection(MapperConfig.JBDC_URL)) {
            int stapelNummer = 0;
            int kaartNummer = 0;
            for (Hutkaart[] stapel : stapels) {
                for (Hutkaart hutkaart : stapel) {
                    
                    PreparedStatement queryVoegkaartToe = conn.prepareStatement("UPDATE `hutkaart` SET `actief`=?"
                            + " WHERE `spelId`=? and`kaartNummer`=? and`stapel`=?;");
                    queryVoegkaartToe.setBoolean(1, hutkaart.getActief());
                    queryVoegkaartToe.setInt(2, spelId);
                    queryVoegkaartToe.setInt(3, kaartNummer);
                    queryVoegkaartToe.setInt(4, stapelNummer);
                    queryVoegkaartToe.executeUpdate();
                    kaartNummer++;
                }
                kaartNummer = 0;
                stapelNummer++;
            }
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }

    }
}
