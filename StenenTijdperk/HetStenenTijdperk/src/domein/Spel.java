/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author jasperdesmet
 */
public class Spel {
    //private ArrayList<Grondstof> grondstoffen = new ArrayList<>();
    private Grondstof[] grondstoffen = new Grondstof[5];
    private ArrayList<Speler> spelers = new ArrayList<>();
    private Speler spelerAanZet;
    private ArrayList<Actie> acties = new ArrayList<>();
    private Hutkaart[][] stapels = new Hutkaart[4][7];
    private Speler startSpeler;
    private boolean eindeSpel;
    private ScoreRepository sr;
    private int spelId;
    private String spelNaam;
    //Constructor
    public Spel(ArrayList<Speler> spelers,Hutkaart[][] stapels,int spelerAanZet,int spelId,String spelnaam){
        this.spelers = spelers;
        this.stapels = stapels;
        this.spelerAanZet = spelers.get(spelerAanZet - 1);
        this.startSpeler = this.spelerAanZet;
        this.spelId = spelId;
        this.spelNaam = spelnaam;
        this.sr = new ScoreRepository();
        StartOpgeslagenSpel();
    }
    public Spel(String[] namen){
        int aantalSpelers = namen.length;
        //Spelers toevoegen
        for(int i = 1;i <= aantalSpelers; i++){
            spelers.add(new Speler(i,namen[i - 1]));
        }
        bepaalSpelerAanZet(aantalSpelers);
        
        StartSpel();
        
    }
    public Spel(String[][] namen,ScoreRepository sr){
        this.sr = sr;
        int aantalSpelers = namen.length;
        
        //Spelers toevoegen
        for(int i = 1;i <= aantalSpelers; i++){
            spelers.add(new Speler(i,namen[i - 1][0],namen[i - 1][1]));
        }
        bepaalSpelerAanZet(aantalSpelers);
        
        StartSpel();
    }
    private void StartOpgeslagenSpel(){
        //Grondstoffen toevoegen
        grondstoffen[0] = new Grondstof(2, "Voedsel");
        grondstoffen[1] = new Grondstof(3, "Hout");
        grondstoffen[2] = new Grondstof(4, "Leem");
        grondstoffen[3] = new Grondstof(5, "Steen");
        grondstoffen[4] = new Grondstof(6, "Goud");
        
        acties.add(new Akker(1));
        acties.add(new Hut(2));
        acties.add(new Jacht(100));
        acties.add(new Gereedschapsmaker(1));
        acties.add(new Grondstofproductie(7, grondstoffen[1]));
        acties.add(new Grondstofproductie(7, grondstoffen[2]));
        acties.add(new Grondstofproductie(7, grondstoffen[3]));
        acties.add(new Grondstofproductie(7, grondstoffen[4]));
        acties.add(stapels[0][geefIndexActieveHutkaart(0)]);
        acties.add(stapels[1][geefIndexActieveHutkaart(1)]);
        acties.add(stapels[2][geefIndexActieveHutkaart(2)]);
        acties.add(stapels[3][geefIndexActieveHutkaart(3)]);
        eindeSpel = false;
    }
    private void StartSpel(){
        //Grondstoffen toevoegen
        grondstoffen[0] = new Grondstof(2, "Voedsel");
        grondstoffen[1] = new Grondstof(3, "Hout");
        grondstoffen[2] = new Grondstof(4, "Leem");
        grondstoffen[3] = new Grondstof(5, "Steen");
        grondstoffen[4] = new Grondstof(6, "Goud");
        
        acties.add(new Akker(1));
        acties.add(new Hut(2));
        acties.add(new Jacht(100));
        acties.add(new Gereedschapsmaker(1));
        acties.add(new Grondstofproductie(7, grondstoffen[1]));
        acties.add(new Grondstofproductie(7, grondstoffen[2]));
        acties.add(new Grondstofproductie(7, grondstoffen[3]));
        acties.add(new Grondstofproductie(7, grondstoffen[4]));
        
        generateHutkaarten();
        eindeSpel = false;
    }
    public void generateHutkaarten(){
        //genereren hutkaarten
        Random rand = new Random();
        ArrayList<Grondstof> grondstoffenList = new ArrayList<>();
        int i = 1;
        for(Hutkaart[] stapel : stapels){
            for(int t = 0; t < stapel.length; t++){
                
                grondstoffenList.add(grondstoffen[rand.nextInt(4) + 1]);
                grondstoffenList.add(grondstoffen[rand.nextInt(4) + 1]);
                grondstoffenList.add(grondstoffen[rand.nextInt(4) + 1]);
                
                grondstoffenList.sort((Grondstof o1, Grondstof o2) -> o1.getWaarde()-o2.getWaarde());
                stapel[t] = new Hutkaart(grondstoffenList, i);
                grondstoffenList.clear();
            }
            i++;
            stapel[0].Actief();
            acties.add(stapel[0]);
        }
    }
    private void bepaalSpelerAanZet(int aantalSpelers){
        Random rand = new Random();
        spelerAanZet = spelers.get(rand.nextInt(aantalSpelers));
        startSpeler = spelerAanZet;
    }
    
    @Override
    public String toString(){
        String toString = String.format("%nSpel met %d spelers.%n",spelers.size());;
        for(Speler speler : spelers)
            toString += String.format("%s%n", speler.toString()); 
        return toString;
    }
    public String geefInfoBeschikbarePlaatsen(){
        String uitvoer = "";
        for(Actie plaats : acties){
            if(plaats.geefBeschikbarePlaatsen() > 0){
               uitvoer += plaats.toString(); 
            }     
        }
        return uitvoer;
    }
   
    public boolean alleStamledenGeplaatst(){
        for(Speler speler : spelers){
            if(!alleStamledenGeplaatstSpeler(speler)){
                return false;
            }
        }
        return true;
    }
    public String geefSpelerAanZet(){
        return String.format("%nDe speler aan zet is %s",spelerAanZet.getNaam());
    }
    public String SpelerKarakter(int index){
        return spelers.get(index).getCharacter();
    }
    public String geefSpelerAanZetPlaatsen(){
        return String.format("%s, je hebt nog %d stamleden over%n",geefSpelerAanZet(),spelerAanZet.geefBeschikbareStamleden());
    }
    public void plaatsStamleden(int plaats, int aantalStamleden){
        acties.get(plaats).verhoogBezettePlaatsen(aantalStamleden);
        spelerAanZet.plaatsStamleden(acties.get(plaats),aantalStamleden);
    }
    public int geefIndexActieveHutkaart(int stapel){
        int i = 0;
        for (Hutkaart kaart : stapels[stapel]){
            if (kaart.getActief()){
               return i;
            }
            i++;
        }
        return -1;
    }
    public boolean getEindeSpel() {
        return eindeSpel;
    }
    public void doeActie(int plaats) {
        int nieuweHutkaart; 
        switch(plaats){
            case 8: 
                nieuweHutkaart = geefIndexActieveHutkaart(0) + 1;
                acties.get(plaats).doeActie(spelerAanZet);
                if(nieuweHutkaart == 7){
                    eindeSpel = true;
                } else {
                    acties.set(8, stapels[0][nieuweHutkaart]);
                    stapels[0][nieuweHutkaart].Actief();
                }
                break;
            case 9:
                nieuweHutkaart = geefIndexActieveHutkaart(1) + 1;
                acties.get(plaats).doeActie(spelerAanZet);
                if(nieuweHutkaart == 7){
                    eindeSpel = true;
                } else {
                    acties.set(9, stapels[1][nieuweHutkaart]);
                    stapels[1][nieuweHutkaart].Actief();
                }
                break;
            case 10:
                nieuweHutkaart = geefIndexActieveHutkaart(2) + 1;
                acties.get(plaats).doeActie(spelerAanZet);
                if(nieuweHutkaart == 7){
                    eindeSpel = true;
                } else {
                    acties.set(10, stapels[2][nieuweHutkaart]);
                    stapels[2][nieuweHutkaart].Actief();
                }
                break;
            case 11:
                nieuweHutkaart = geefIndexActieveHutkaart(3) + 1;
                acties.get(plaats).doeActie(spelerAanZet);
                if(nieuweHutkaart == 7){
                    eindeSpel = true;
                } else {
                    acties.set(11, stapels[3][nieuweHutkaart]);
                    stapels[3][nieuweHutkaart].Actief();
                }
                break;
            default: 
                acties.get(plaats).doeActie(spelerAanZet);
                break;
        }
        
        
    }
    public void doeActie(int plaats, int geworpenOgen) {
        acties.get(plaats).doeActie(spelerAanZet, geworpenOgen);
    }
    public Speler getSpelerAanZet() {
        return spelerAanZet;
    }

    public boolean plaatsIsValid(int plaats, int aantalStamleden) {
        if (plaats == -1)
            throw new IllegalArgumentException("Fout: Dit is geen geldige plaats");
        if(!(acties.get(plaats).geefBeschikbarePlaatsen() >= aantalStamleden))
            throw new IllegalArgumentException("Fout: Er zijn niet genoeg plaatsen voor het aantal stamleden");
        if(!(spelerAanZet.geefBeschikbareStamleden() >= aantalStamleden))
            throw new IllegalArgumentException("Fout: je hebt niet zoveel stamleden");
        if(!(spelerAanZet.heeftGeenStamledenOpPlaats(acties.get(plaats))))
            throw new IllegalArgumentException("Fout: je hebt in een vorige beurt al stamleden geplaats op deze plaats");
        
        return true;
    }
    public boolean actieIsValid(int plaats) {
        if (plaats == -1)
            throw new IllegalArgumentException("Fout: Dit is geen geldige plaats");
        for (String geefInfoActie : spelerAanZet.geefInfoActies()) {
            if (geefInfoActie.equals(acties.get(plaats).geefNaam()))
                return true;
        }
        throw new IllegalArgumentException("Fout: je hebt geen stamleden op die plaats"); 
    }

    public boolean alleStamledenGeplaatstSpeler(Speler speler) {
        return speler.geefBeschikbareStamleden() == 0;
    }

    public void volgendeSpeler() {
        spelerAanZet = spelers.get(spelerAanZet.getSpelerNummer() % spelers.size());
    }

    public boolean alleStamledenGeplaatstSpelerAanZet() {
        return alleStamledenGeplaatstSpeler(spelerAanZet);
    }

    public int geefAantalSpelers() {
        return spelers.size();
    }

    public boolean alleActiesUitgevoerdSpelerAanZet() {
        return spelerAanZet.alleActiesUitgevoerd();
    }

    public String geefInfoActiesSpelerAanZet() {
        String temp = String.format("Je hebt stamleden op: %n");
        for(String info : spelerAanZet.geefInfoActies()){
            temp += String.format("%s%n",info);
        }
        return temp;
                
    }

    public int geefStamledenOpPlaatsSpelerAanZet(int plaats) {
        return spelerAanZet.geefAantalStamledenOpPlaats(acties.get(plaats));
    }

    public String geefInfoSpelerAanZet() {
        return spelerAanZet.toString();
    }

    public void verzetNaarStartSpeler() {
        spelerAanZet = startSpeler;
    }

    public boolean spelerAanZetHeeftOngebruiktGereedschap() {
        return spelerAanZet.heeftOngebruiktGereedschap();
    }

    public String geefInfoOngebruikteGereedschapsfichesSpelerAanZet() {
        return spelerAanZet.geefInfoOngebruikteGereedschapsfiches();
    }

    public void gereedschapsficheIsValid(int gereedschapsfiche) {
        spelerAanZet.gereedschapsficheIsValid(gereedschapsfiche);
    }

    public int gebruikGereedschapfiche(int gereedschapsfiche) {
        return spelerAanZet.gebruikGereedschapfiche(gereedschapsfiche);
    }

    public void nieuweRonde() {
        startSpeler = spelers.get(startSpeler.getSpelerNummer() % spelers.size());
        for(Speler s : spelers){
            s.nieuweRonde();
        }
        for(Actie a : acties){
            a.resetGebruiktePlaatsen();
        }
    }

    public void voedselProductie() {
        for(Speler s : spelers){
            s.produceerVoedsel();
        }
    }

    public boolean HeeftGenoegVoedselspelerAanZet() {
        return spelerAanZet.heeftGenoegVoedsel();
    }

    public void voedStamledenSpelerAanZet() {
        spelerAanZet.voedStamleden();
    }

    public void voedStamledenSpelerAanZet(int methode) {
        spelerAanZet.voedStamleden(methode);
    }

    public boolean heeftGenoegGrondstoffenSpelerAanZet() {
        return spelerAanZet.heeftGenoegGrondstoffen();
    }

    public int[] geefGrondstoffenSpelerAanZet() {
        return spelerAanZet.geefGrondstoffenSpelerAanZet();
    }

    public int nodigVoedselSpelerAanZet() {
        return spelerAanZet.nodigVoedsel();
    }

    public void voedStamledenSpelerAanZet(int[] grondstoffen) {
        spelerAanZet.voedStamledenSpelerAanZet(grondstoffen);
    }

    String bepaalWinnaar() {
        Speler winnaar = spelers.get(0);
        for (Speler s : spelers){
            if (s.getScore() > winnaar.getScore()){
                winnaar = s;
            }
        }
        sr.voegToe(winnaar);
        return winnaar.getNaam();
    }

    public String toonEindscore() {
        String Eindscore = String.format("%-4s%-10s%-4s%n","Nr.","Spelers","Score");
        for(Speler s : spelers){
            Eindscore += String.format("%-4s%-10s%-4s%n",s.getSpelerNummer(),s.getNaam(),s.getScore());
        }
        return Eindscore;
    }
    public void sortbyColumn(String arr[][], int col)
    {
        Arrays.sort(arr, (final String[] entry1, final String[] entry2) -> {
            if (Integer.parseInt(entry1[col]) < Integer.parseInt(entry2[col]) )
                return 1;
            else
                return -1;
        });
    }
     
    public String[][] geefInfoEindscore(){
        
        String[][] infoEindscore = new String[spelers.size()][3];
        int i = 0;
        for(Speler s : spelers){
            infoEindscore[i][0] = s.getNaam();
            infoEindscore[i][1] = s.getCharacter();
            infoEindscore[i][2] = "" + s.getScore();
            i++;
        }
        sortbyColumn(infoEindscore,2);
        return infoEindscore;
        
    }
    public void berekenEindscore() {
        for(Speler s : spelers){
            s.BerekenEindscore();
        }
    }

    public String geefSpelerNaamAanZet() {
        return spelerAanZet.getNaam();
    }

    String[][] geefInfoKaarten() {
        String[][] infoKaarten = new String[4][4];
       
           for(int i = 0;i < 4; i ++){
                if(geefIndexActieveHutkaart(i) == -1){
                    infoKaarten[i][0] = "einde";
                    infoKaarten[i][1] = "";
                    infoKaarten[i][2] = "";
                    infoKaarten[i][3] = "";
                } else {
                    infoKaarten[i][0] = "" + stapels[i][geefIndexActieveHutkaart(i)].berekenWaarde();
                    infoKaarten[i][1] = "" + stapels[i][geefIndexActieveHutkaart(i)].geefKost1naam();
                    infoKaarten[i][2] = "" + stapels[i][geefIndexActieveHutkaart(i)].geefKost2naam();
                    infoKaarten[i][3] = "" + stapels[i][geefIndexActieveHutkaart(i)].geefKost3naam();
            } 
        }
        
        
        return infoKaarten;
    }

    public int geefSpelerAanZetSpelerNummer() {
        return spelerAanZet.getSpelerNummer();
    }

    public int[] geefSpelerGereedschap(int i) {
        return spelers.get(i).geefGereedschap();
    }
    public boolean[] geefSpelerGereedschapGebruikt(int i) {
        return spelers.get(i).geefSpelerGereedschapGebruikt(i);
    }

    public void verwijderVanPlaats(int plaats) {
        spelerAanZet.verwijderStamledenVanPlaats(acties.get(plaats));
    }
    public void setId(int spelId){
        this.spelId = spelId;
    }
    public void setNaam(String spelNaam){
        this.spelNaam = spelNaam;
    }
    public ArrayList<Speler> getSpelers(){
        return spelers;
    }
     public Hutkaart[][] getStapels(){
        return stapels;
    }

    public int getSpelId() {
        return spelId;
    }
    public String getSpelNaam(){
        return spelNaam;
    }
    public boolean heeftId() {
        return spelId != 0;
    }


}
