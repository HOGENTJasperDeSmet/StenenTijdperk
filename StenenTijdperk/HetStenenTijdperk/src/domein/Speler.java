/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.ArrayList;

/**
 *
 * @author jasperdesmet
 */
public class Speler {

    private final int spelerNummer;
    private int voedselProductie,
            aantalVoedsel = 12,
            aantalGoud = 100,
            aantalSteen = 100,
            aantalLeem = 100,
            aantalHout = 100,
            score,
            geplaatsteStamleden;
    private String naam, character;
    private ArrayList<Stamlid> stamleden = new ArrayList<>();
    private Gereedschapsfiche[] gereedschappen = new Gereedschapsfiche[3];
    public Speler(int spelerNummer,String naam){
        this.spelerNummer = spelerNummer;
        this.naam = naam;
        stamleden.add(new Stamlid());
        stamleden.add(new Stamlid());
        stamleden.add(new Stamlid());
        stamleden.add(new Stamlid());
        stamleden.add(new Stamlid()); 
        gereedschappen[0] = new Gereedschapsfiche();
        gereedschappen[1] = new Gereedschapsfiche();
        gereedschappen[2] = new Gereedschapsfiche();
    }
    public Speler(int spelerNummer,String naam , String character){
        this.spelerNummer = spelerNummer;
        this.naam = naam;
        this.character = character;
        stamleden.add(new Stamlid());
        stamleden.add(new Stamlid());
        stamleden.add(new Stamlid());
        stamleden.add(new Stamlid());
        stamleden.add(new Stamlid()); 
        gereedschappen[0] = new Gereedschapsfiche();
        gereedschappen[1] = new Gereedschapsfiche();
        gereedschappen[2] = new Gereedschapsfiche();
    }
    @Override
    public String toString(){
        return String.format("Speler %d %s met score %d en %n"
                + "Hout: %d, Leem: %d, Steen: %d, Goud: %d%n"
                + "Voedsel : %d, voedselproductie: %d per beurt%n"
                + "Stamleden: %d%n"
                + "En Gereedschapsfiche: %d, %d, %d%n",
                getSpelerNummer(),getNaam(),getScore(),getAantalHout(),getAantalLeem(),
                getAantalSteen(),getAantalGoud(),getAantalVoedsel(),getVoedselProductie(),stamleden.size(),
                gereedschappen[0].getKracht(),gereedschappen[1].getKracht(),gereedschappen[2].getKracht());
    }
    public void plaatsStamleden(Actie plaats, int aantalStamleden){
        for(int i = geplaatsteStamleden; i < aantalStamleden + geplaatsteStamleden; i++){
            stamleden.get(i).plaatsOp(plaats);   
        }
        geplaatsteStamleden += aantalStamleden;
    }
    public boolean heeftGeenStamledenOpPlaats(Actie plaats){
        for(Stamlid stamlid : stamleden){
            if (stamlid.getPlaats() == plaats){
                return false;
            }
        }
        return true;
    }
    public int geefAantalStamleden() {
        return stamleden.size();
    }
    public int geefBeschikbareStamleden(){
        return geefAantalStamleden() - geplaatsteStamleden;
    }
    boolean alleActiesUitgevoerd() {
        for(Stamlid stamlid : stamleden){
            if (stamlid.getPlaats() != null){
                return false;
            }
        }
        return true;
    }
    public ArrayList<String> geefInfoActies() {
        ArrayList<String> infoActies = new ArrayList<>();
        String temp = "";
        for(Stamlid stamlid : stamleden){
            if (stamlid.getPlaats() != null){
                if (!temp.equals(stamlid.getPlaats().geefNaam())){
                    temp = stamlid.getPlaats().geefNaam();
                    infoActies.add(stamlid.getPlaats().geefNaam());
                }
            }
            
        }
        return infoActies;
    }
    public void verwijderStamledenVanPlaats(Actie plaats){
        
        for(Stamlid stamlid : stamleden){
            if (stamlid.getPlaats() == plaats){
                stamlid.plaatsOp(null);
            }
        }
    }
    public void verhoogVoedselProductie(){
        voedselProductie++;
    }
    public void verhoogStamleden() {
        stamleden.add(new Stamlid());
    }
    public void verhoogGereedschapsfiche(){
        int som = 0;
        for(Gereedschapsfiche g : gereedschappen){
            som += g.getKracht();
        }
        gereedschappen[som % 3].verhoogKracht();
    }
    int geefAantalStamledenOpPlaats(Actie plaats) {
        int i  = 0;
        for(Stamlid stamlid : stamleden){
            if(stamlid.getPlaats() == plaats){
                i++;
            }
        }
        return i;
    }

    void voegGrondstofToe(int i, String grondstof) {
        switch(grondstof){
            case "Hout":
                aantalHout += i;
                break;
            case "Leem":
                aantalLeem += i;
                break;
            case "Steen":
                aantalSteen += i;
                break;
            case "Goud":
                aantalGoud += i;
                break;
        }
    }
    public void voegVoedselToe(int i) {
        aantalVoedsel += i;
    }
    public boolean heeftOngebruiktGereedschap() {
        for(Gereedschapsfiche g : gereedschappen){
            if(!g.getGebruikt() && g.getKracht() > 0){
                return true;
            }
        }
        return false;
    }
    public Gereedschapsfiche[] getGeerdschapsfiche(){
        return gereedschappen;
    }
    public String getCharacter(){
        return character;
    }
    public int getAantalGoud(){
        return aantalGoud;
    }

    public int getSpelerNummer() {
        return spelerNummer;
    }

    public int getVoedselProductie() {
        return voedselProductie;
    }

    public int getAantalVoedsel() {
        return aantalVoedsel;
    }

    public int getAantalSteen() {
        return aantalSteen;
    }

    public int getAantalLeem() {
        return aantalLeem;
    }

    public int getAantalHout() {
        return aantalHout;
    }
    
    public int getScore() {
        return score;
    }
    
    public String getNaam() {
        return naam;
    }

    public String geefInfoOngebruikteGereedschapsfiches() {
        String uitvoer = "";
        int i = 1;
        for(Gereedschapsfiche g : gereedschappen){
            if(g.getKracht() != 0){
                if(!g.getGebruikt()){
                    uitvoer += String.format("Gereedschapsfiche %d met kracht %d%n",i,g.getKracht());
                }
            }
            i++;    
        }    
        return uitvoer;
    }
        

    public void gereedschapsficheIsValid(int gereedschapsfiche) {
        if (gereedschappen[gereedschapsfiche - 1].getGebruikt()){
            throw new IllegalArgumentException("Dit gereedschapsfiche is gebruikt");
        }
        if (gereedschappen[gereedschapsfiche - 1].getKracht() == 0){
            throw new IllegalArgumentException("Dit gereedschapsfiche heeft geen kracht");
        }
    }

    public int gebruikGereedschapfiche(int gereedschapsfiche) {
        gereedschappen[gereedschapsfiche - 1].gebruik();
        return gereedschappen[gereedschapsfiche - 1].getKracht();
    }

    public void verhoogScore(int berekenWaarde) {
        score += berekenWaarde;
    }

    public void gebruikGrondstoffen(int goudKost, int houtKost, int leemKost, int steenKost) {
        aantalGoud -= goudKost;
        aantalHout -= houtKost;
        aantalLeem -= leemKost;
        aantalSteen -= steenKost;                
    }

    public void nieuweRonde() {
        for(Gereedschapsfiche g : gereedschappen){
            g.reset();
        }
        geplaatsteStamleden = 0;
    }

    public void produceerVoedsel() {
        aantalVoedsel += voedselProductie;
    }

    public boolean heeftGenoegVoedsel() {
        return aantalVoedsel >= stamleden.size();
    }

    public void voedStamleden() {
        aantalVoedsel -= stamleden.size();
    }

    public void voedStamleden(int methode) {
        score -= 10;
        aantalVoedsel = 0;
    }

    public boolean heeftGenoegGrondstoffen() {
        return aantalVoedsel + aantalHout + aantalLeem + aantalGoud + aantalSteen >= stamleden.size();
    }

    public int[] geefGrondstoffenSpelerAanZet() {
        return new int[]{aantalHout,aantalLeem,aantalSteen,aantalGoud,aantalVoedsel};
    }

    public int nodigVoedsel() {
        return stamleden.size() - aantalVoedsel;
    }

    public void voedStamledenSpelerAanZet(int[] grondstoffen) {
        aantalHout -= grondstoffen[0];
        aantalLeem -= grondstoffen[1];
        aantalSteen -= grondstoffen[2];
        aantalGoud -= grondstoffen[3];
    }

    public void BerekenEindscore() {
        score += aantalHout + aantalGoud + aantalLeem + aantalSteen;
    }

    public int[] geefGereedschap() {
        return new int[]{gereedschappen[0].getKracht(),gereedschappen[1].getKracht(),gereedschappen[2].getKracht()};
    }
    public boolean[] geefSpelerGereedschapGebruikt(int i) {
        return new boolean[]{gereedschappen[0].getGebruikt(),gereedschappen[1].getGebruikt(),gereedschappen[2].getGebruikt()};
    }



    



    
}
