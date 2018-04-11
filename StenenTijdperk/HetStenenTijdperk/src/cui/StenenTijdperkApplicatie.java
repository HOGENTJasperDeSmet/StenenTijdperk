/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cui;

import domein.DomeinController;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author jasperdesmet
 */
public class StenenTijdperkApplicatie {

    public static DomeinController domeinController = new DomeinController();
    public static Scanner input = new Scanner(System.in);

    public StenenTijdperkApplicatie() throws IOException {
        int aantalSpelers;
        int ronde = 1;
        String[] namen;
        System.out.printf("Welkom bij stenen tijdperk. "
                + "%nWat wil je doen:%n"
                + "1. Nieuw spel starten%n"
                + "2. Spel hervaten%n"
                + "3. Highscores bekijken%n"
                + "Typ je keuze: ");
        switch (input.next()) {
            case "1":
                System.out.print("Met hoeveel wil je spelen: ");
                aantalSpelers = input.nextInt();
                while (!(aantalSpelers > 1 && aantalSpelers < 5)) {
                    System.out.print("Geef een aantal spelers tussen 2 en 4: ");
                    aantalSpelers = input.nextInt();
                }

                namen = new String[aantalSpelers];
                for (int i = 0; i < aantalSpelers; i++) {
                    System.out.printf("Geef naam van speler %d: ", i + 1);
                    namen[i] = input.next();
                }
                
                domeinController.startSpel(namen);
                
                System.out.println("druk op enter om te beginnen");
                System.in.read();
                do{
                    System.out.println(domeinController.geefSpelbord());
                    plaatsStamleden();
                    
                    System.out.println("Druk op enter om te beginnen met Acties uitvoeren");
                    System.in.read();
                    doeActies();
                    
                    System.out.println(domeinController.geefSpelbord());
                    
                    System.out.println("Druk op enter om stamleden te voeden");
                    System.in.read();
                    voedStamleden();
                    
                    ronde++;
                    System.out.printf("%nDruk op enter om aan ronde %d te beginnen%n",ronde);
                    System.in.read();
                    
                    domeinController.nieuweRonde();
                    
                }while(!domeinController.eindeSpel());
                System.out.println("Einde spel bereikt");
                domeinController.berekenEindscore();
                System.out.printf("%n%s%n",domeinController.toonEindscore());
                System.out.printf("De winnaar is %s%n",domeinController.bepaalWinnaar());
                break;
            default:
                System.out.println("Dit is geen geldige input");
                break;
        }

    }

    public static void doeActies() throws IOException {
        boolean loop = false;
        int plaats = 0;
        for (int i = 0; i < domeinController.geefAantalSpelers(); i++) {
            while (!domeinController.alleActiesUitgevoerdSpelerAanZet()) {
                System.out.println(domeinController.geefSpelerAanZet());
                System.out.println(domeinController.geefInfoActiesSpelerAanZet());
                do {
                    try {
                        plaats = plaatsCode();
                        loop = domeinController.ActieIsValid(plaats);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } while (!loop);

                doeActie(plaats);
                
                System.out.printf("%n%s%n%n✓ Actie uitgevoerd%n%nDruk op enter om verder te gaan",domeinController.geefInfoSpelerAanZet());
                System.in.read();
                loop = false;

            }
            domeinController.volgendeSpeler();
        }
    }

    public static void doeActie(int plaats) throws IOException{
        Random rand = new Random();
        int geworpenOgen = 0, worp;
        String geworpen;
        switch (plaats) {
            case 0:
            case 1:
            case 3:
            case 8:
            case 9:
            case 10:
            case 11:
                try{
                    domeinController.doeActie(plaats);
                }catch (IllegalArgumentException iae){
                    System.out.println(iae.getMessage());
                }
                break;
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
                System.out.printf("%nJe hebt %d stamleden op deze plaats: %n",
                        domeinController.geefStamledenOpPlaatsSpelerAanZet(plaats));
                geworpen = String.format("Je werpt voor ");
                worp = rand.nextInt(6) + 1;
                geworpenOgen += worp;
                for (int i = 0; i < domeinController.geefStamledenOpPlaatsSpelerAanZet(plaats) - 1; i++) {
                    geworpen += String.format("%d, ", worp);
                    worp = rand.nextInt(6) + 1;
                    geworpenOgen += worp;
                }
                geworpen += String.format("en %d voor een totaal van %d", worp, geworpenOgen);
                System.out.println(geworpen);
                if (domeinController.spelerAanZetHeeftOngebruiktGereedschap()) {
                    String invoer;
                    do {
                        System.out.printf("Wil je een gereedschapsfiche gebruiken?(Ja of Neen): ");
                        invoer = input.next();
                        invoer = invoer.toLowerCase();
                        System.out.println(invoer);
                    } while (!"ja".equals(invoer) && !"neen".equals(invoer));

                    if ("ja".equals(invoer)) {
                        boolean flagGereedschapsfiche = true;
                        boolean flagExtraGereedschap = true;
                        do {
                            System.out.println("Je hebt de volgende gereedschapfiche(s): ");
                            System.out.println(domeinController.geefInfoOngebruikteGereedschapsfichesSpelerAanZet());
                            int gereedschapsfiche = 0;
                            do {
                                try {
                                    System.out.print("Welke gereedschapsfiche wil je gebruiken: ");
                                    gereedschapsfiche = input.nextInt();
                                    domeinController.gereedschapsficheIsValid(gereedschapsfiche);
                                    flagGereedschapsfiche = false;
                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                } catch (Exception e) {
                                    System.out.println("Dit is geen gereedschapsfiche");
                                }
                            } while (flagGereedschapsfiche);
                            System.out.println("Gereedschapsfiche gekozen");
                            geworpenOgen += domeinController.gebruikGereedschapfiche(gereedschapsfiche);
                            if (domeinController.spelerAanZetHeeftOngebruiktGereedschap()) {
                                do {
                                    System.out.printf("Wil je nog een gereedschapsfiche gebruiken?(Ja of Neen): ");
                                    invoer = input.next();
                                    invoer = invoer.toLowerCase();
                                    if ("neen".equals(invoer)) {
                                        flagExtraGereedschap = false;
                                    }
                                } while (!"ja".equals(invoer) && !"neen".equals(invoer));
                            } else {
                                 flagExtraGereedschap = false;
                            }
                        } while (flagExtraGereedschap);

                    }
                }
                domeinController.doeActie(plaats, geworpenOgen);
                break;
        }

    }
    private void voedStamleden() {
        domeinController.voedselProductie();
        for (int i = 0; i < domeinController.geefAantalSpelers(); i++) {
            if(domeinController.HeeftGenoegVoedselspelerAanZet()){
                domeinController.voedStamledenSpelerAanZet();
            } else {
                if(domeinController.heeftGenoegGrondstoffenSpelerAanZet()){
                    System.out.printf("%s je hebt niet genoeg voedsel, Wat wil je doen%n",domeinController.geefSpelerNaamAanZet());
                    System.out.println("1. Betaal met grondsoffen");
                    System.out.println("2. Score min 10");
                    System.out.print("Typ je keuze: ");
                    boolean flagVoedselFout = true;
                    String keuze;
                    do{
                        keuze = input.next();
                        switch(keuze){
                        case "1":
                            int totaalGrondstoffen = 0, aantal;
                            int[] grondstoffenSpelerAanZet = domeinController.geefGrondstoffenSpelerAanZet(),grondstoffen = new int[4];
                            System.out.printf("%nJe hebt de volgende grondstoffen:%n"
                                    + "Hout: %d%n"
                                    + "Leem: %d%n"
                                    + "Steen: %d%n"
                                    + "Goud: %d%n",grondstoffenSpelerAanZet[0],grondstoffenSpelerAanZet[1],grondstoffenSpelerAanZet[2],grondstoffenSpelerAanZet[3]);
                            
                            do{
                                System.out.print("Welke grondstoffen wil je gebruiken?: ");
                                String grondstof = input.next().toLowerCase();
                                switch(grondstof){
                                    case "hout":
                                        System.out.print("Hoeveel hout wil je geven: ");
                                        aantal = input.nextInt();
                                        if(grondstoffenSpelerAanZet[0] >= aantal){
                                            grondstoffen[0] += aantal;
                                            totaalGrondstoffen += aantal;
                                        } else {
                                            System.out.println("Je hebt niet zoveel hout");
                                        }
                                        break;
                                    case "leem":
                                        System.out.print("Hoeveel leem wil je geven: ");
                                        aantal = input.nextInt();
                                        if(grondstoffenSpelerAanZet[1] >= aantal){
                                            grondstoffen[1] += aantal;
                                            totaalGrondstoffen += aantal;
                                        } else {
                                            System.out.println("Je hebt niet zoveel leem");
                                        }
                                        break;
                                    case "steen":
                                        System.out.print("Hoeveel steen wil je geven: ");
                                        aantal = input.nextInt();
                                        if(grondstoffenSpelerAanZet[2] >= aantal){
                                            grondstoffen[2] += aantal;
                                            totaalGrondstoffen += aantal;
                                        } else {
                                            System.out.println("Je hebt niet zoveel steen");
                                        }
                                        break;
                                    case "goud":
                                        System.out.print("Hoeveel goud wil je geven: ");
                                        aantal = input.nextInt();
                                        if(grondstoffenSpelerAanZet[1] >= aantal){
                                            grondstoffen[1] += aantal;
                                            totaalGrondstoffen += aantal;
                                        } else {
                                            System.out.println("Je hebt niet zoveel goud");
                                        }
                                        break;
                                        
                                }
                            }while(totaalGrondstoffen < domeinController.nodigVoedselSpelerAanZet());
                            domeinController.voedStamledenSpelerAanZet(grondstoffen);
                            flagVoedselFout = false;
                            break;
                        case "2":
                            flagVoedselFout = false;
                            domeinController.voedStamledenSpelerAanZet(2);
                            break;
                        default:
                            System.out.println("Dit is geen geldige optie");    
                            break;
                        }
                    }while(flagVoedselFout);
                }else{
                    System.out.printf("%n%s je hebt niet genoeg voedsel of grondstoffen,%n"
                            + "er worden automatisch 10 punten van je score afgetrokken%n", domeinController.geefSpelerNaamAanZet());
                    domeinController.voedStamledenSpelerAanZet(2);
                }
            }
        domeinController.volgendeSpeler();
        }
    }
    
    public static void plaatsStamleden() {
        boolean loop = false;
        int infoPlaats[] = {0, 0}, aantalStamleden = 0;
        while (!domeinController.alleStamledenGeplaatst()) {
            if (!domeinController.alleStamledenGeplaatstSpelerAanZet()) {
                System.out.println(domeinController.geefSpelerAanZetPlaatsen());
                System.out.println(domeinController.geefInfoBeschikbarePlaatsen());
                do {
                    try {
                        infoPlaats = plaatsArray();
                        loop = domeinController.plaatsIsValid(infoPlaats[0], infoPlaats[1]);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } while (!loop);
                System.out.println("✓ Stamlid geplaatst");
                loop = false;
                domeinController.plaatsStamleden(infoPlaats[0], infoPlaats[1]);
            }
            domeinController.volgendeSpeler();
        }
        domeinController.verzetNaarStartSpeler();
    }
    public static int plaatsCode() {
        System.out.print("Welke actie wil je uitvoeren: ");
        String plaats = input.next();

        switch (plaats.toLowerCase()) {
            case "akker":
                return 0;
            case "hut":
                return 1;
            case "gereedschapsmaker":
                return 3;
            case "jacht":
                return 2;
            case "bos":
                return 4;
            case "leemgroeve":
                return 5;
            case "steengroeve":
                return 6;
            case "rivier":
                return 7;
            case "hutkaart":
                System.out.print("Van welke stapel wil je de hutkaart pakken: ");
                switch (input.next()) {
                    case "1":
                        return 8;
                    case "2":
                        return 9;
                    case "3":
                        return 10;
                    case "4":
                        return 11;
                }
        }
        return -1;
    }

    public static int[] plaatsArray() {
        System.out.print("Waar wil je je stamleden plaatsen: ");
        String plaats = input.next();

        switch (plaats.toLowerCase()) {
            case "akker":
                return new int[]{0, 1};
            case "hut":
                return new int[]{1, 2};
            case "gereedschapsmaker":
                return new int[]{3, 1};
            case "hutkaart":
                System.out.print("Op welke stapel wil je je stamlid plaatsen: ");
                switch (input.next()) {
                    case "1":
                        return new int[]{8, 1};
                    case "2":
                        return new int[]{9, 1};
                    case "3":
                        return new int[]{10, 1};
                    case "4":
                        return new int[]{11, 1};
                }
            default:
                switch (plaats.toLowerCase()) {
                    case "jacht":
                        System.out.print("Hoeveel stamleden wil je plaatsen: ");
                        return new int[]{2, input.nextInt()};
                    case "bos":
                        System.out.print("Hoeveel stamleden wil je plaatsen: ");
                        return new int[]{4, input.nextInt()};
                    case "leemgroeve":
                        System.out.print("Hoeveel stamleden wil je plaatsen: ");
                        return new int[]{5, input.nextInt()};
                    case "steengroeve":
                        System.out.print("Hoeveel stamleden wil je plaatsen: ");
                        return new int[]{6, input.nextInt()};
                    case "rivier":
                        System.out.print("Hoeveel stamleden wil je plaatsen: ");
                        return new int[]{7, input.nextInt()};
                }

        }
        return new int[]{-1, 0};
    }

    
}
