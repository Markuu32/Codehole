/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package työvuoro;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Markus
 */

public class Tyovuorotiedot {

    public String Tunniste;
    public String Vuorotyyppi;
    public String Paiva;
    public int Viikko;
    public String Aloitusaika;
    public String Lopetusaika;
    public String Hyvitysalkukello;
    public String Hyvitysloppukello;
    public long hyvitys;
    public int tarkistus = 0;
    public ArrayList<Tyovuorotiedot> tiedot = new ArrayList<Tyovuorotiedot>();
    public ArrayList<Tyovuorotiedot> yohyvitys = new ArrayList<Tyovuorotiedot>();

    public void haeyohyvitys() { //Haetaan syötetyn työvuoron hyvityksen määrä
        Scanner sc = new Scanner(System.in);
        ArrayList<Tyovuorotiedot> kk = new ArrayList<Tyovuorotiedot>();
        kk = haeKaikki();

        System.out.print("Anna tunniste jolla haetaan: ");
        String pl = sc.next();
        haeTunnisteella(pl, kk);
    }

    public void syotaTyovuorotiedot() { //Syötetään työvuoron tiedot ja tallennetaan ne listaan
        Scanner sc = new Scanner(System.in);
        String tunniste, vuorotyyppi;
        String paiva;
        int viikko;
        String alkamisaika;
        String lopetusaika;

        System.out.println("Syötä työvuoron tiedot");
        System.out.print("Anna tunniste: ");
        tunniste = sc.next();

        System.out.print("Anna vuorotyyppi: ");
        vuorotyyppi = sc.next();

        paiva = syotaPaiva();
        while (paiva.equals("vaara")) {
            System.out.println("Paivan tulee olla ma-su");
            paiva = syotaPaiva();
        }
        viikko = syotaViikko();
        while (viikko == 0) {
            System.out.println("Viikon tulee olla väliltä 1 - 52 ");
            viikko = syotaViikko();
        }

        alkamisaika = syotaAloitusaika();
        while (tarkistus == 0) {
            System.out.println("Kellonaika on virheellinen");
            alkamisaika = syotaAloitusaika();
        }

        lopetusaika = syotaLopetusaika();
        while (tarkistus == 0) {
            System.out.println("Kellonaika on virheellinen");
            alkamisaika = syotaLopetusaika();
        }

        talletalistaan(tunniste, vuorotyyppi, paiva, viikko, alkamisaika, lopetusaika);
    }

    public String syotaPaiva() { //syötetään viikonpäivä ja tarkistaa että syöte on oikea viikonpäivä
        Scanner sc = new Scanner(System.in);
        String paiva;
        System.out.print("Anna paiva (ma-su): ");
        paiva = sc.next();

        if (paiva.equals("ma") || paiva.equals("ti") || paiva.equals("ke") || paiva.equals("to") || paiva.equals("pe") || paiva.equals("la") || paiva.equals("su")) {
            return paiva;
        } else {
            paiva = "vaara";
            return paiva;
        }

    }

    public int syotaViikko() { //Syötetään viikko ja tarkistetaan että se on 1-52 väliltä
        Scanner sc = new Scanner(System.in);
        System.out.print("Anna viikko: ");
        int viikko = sc.nextInt();
        if (viikko < 1 || viikko > 52) {
            return 0;
        } else {
            return viikko;
        }
    }

    public String syotaAloitusaika() { //Syötetään työvuoron aloitusaika ja tarkistetaan että syöte on oikein

        Scanner sc = new Scanner(System.in);
        final String aikamuoto = "HH.mm";
        DateTimeFormatter aikaformatointi = new DateTimeFormatterBuilder().appendPattern(aikamuoto)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .toFormatter();
        System.out.print("Anna aloitusaika(hh.mm): ");
        String aloitus = sc.next();

        try {

            LocalTime t1 = LocalTime.parse(aloitus, aikaformatointi);
            tarkistus = 1;
            return aloitus;

        } catch (Exception e) {
        }

        return aloitus;
    }

    public String syotaLopetusaika() { //Syötetään työvuoron lopetusaika ja tarkistetaan että syöte on oikein
        Scanner sc = new Scanner(System.in);
        final String aikamuoto = "HH.mm";
        DateTimeFormatter aikaformatointi = new DateTimeFormatterBuilder().appendPattern(aikamuoto)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .toFormatter();
        System.out.print("Anna lopetusaika(hh.mm): ");
        String aloitus = sc.next();

        try {

            LocalTime t1 = LocalTime.parse(aloitus, aikaformatointi);
            tarkistus = 1;
            return aloitus;

        } catch (Exception e) {
        }

        return aloitus;
    }

    public String tulosta(String tunniste) { //Tulostetaan työvuoron tiedot    
        return ("Tunniste: " + Tunniste + "\nVuorotyyppi: " + Vuorotyyppi + "\nPaiva: " + Paiva + "\nViikko: " + Viikko + "\nTyöaika: " + Aloitusaika + " - " + Lopetusaika + "\n");
    }

    public ArrayList<Tyovuorotiedot> haeKaikki() { //Hakee kaikki tiedot arraylistissä olevat tiedot ja palauttaa ne
        ArrayList<Tyovuorotiedot> info = new ArrayList<Tyovuorotiedot>();
        for (int j = 0; j < tiedot.size(); j++) {
            info.add(tiedot.get(j));

        }
        return info;
    }

    public void haeTunnisteella(String haku, ArrayList<Tyovuorotiedot> info) { //Hakee tiedot tunnisteen perusteella ja tulostaa siihen liittyvät tiedot. Tarkistaa syntyykö vuorosta hyvitystä ja palauttaa vastauksen sen mukaan.
        String t = "";
        String yoalkuhyvitys, yoloppuhyvitys;
        long hyvitys;
        for (int i = 0; i < info.size(); i++) {
            if (haku.equals(info.get(i).Tunniste)) {

                System.out.print(info.get(i).tulosta(Tunniste));
            }

        }
        for (int j = 0; j < yohyvitys.size(); j++) {
            if (haku.equals(yohyvitys.get(j).Tunniste)) {

                yoalkuhyvitys = yohyvitys.get(j).Hyvitysalkukello;
                yoloppuhyvitys = yohyvitys.get(j).Hyvitysloppukello;
                hyvitys = yohyvitys.get(j).hyvitys;
                if (yohyvitys.get(j).Hyvitysalkukello == null) {
                    System.out.println("Työvuorosta ei kerry yöhyvitystä");
                } else {
                    System.out.println("Työvuorosta kertyy yohyvitystä " + yoalkuhyvitys + " - " + yoloppuhyvitys + " väliseltä ajalta yhteensä " + hyvitys + " minuuttia");
                }
            }
        }

    }

    public void talletalistaan(String tunniste, String vuorotyyppi, String paiva, int viikko, String aloitusaika, String lopetusaika) { //Tallentaa syötetyt työvuoron tiedot listaan.
        Tyovuorotiedot tallennus = new Tyovuorotiedot();
        tallennus.Tunniste = tunniste;
        tallennus.Vuorotyyppi = vuorotyyppi;
        tallennus.Paiva = paiva;
        tallennus.Viikko = viikko;
        tallennus.Aloitusaika = aloitusaika;
        tallennus.Lopetusaika = lopetusaika;
        laskeHyvitys(tallennus.Aloitusaika, tallennus.Lopetusaika, tallennus.Tunniste);
        tiedot.add(tallennus);
    }

    public void laskeHyvitys(String aloitus, String lopetus, String tunniste) { //Tarkistetaan syntyykö annetusta työvuorosta yöhyvitystä ja tallennetaan saadut tiedot listaan.

        Tyovuorotiedot tallennus = new Tyovuorotiedot();

        long diffminute = 0;
        long diffminute2 = 0;
        final String aikamuoto = "HH.mm";
        DateTimeFormatter aikaformatointi = new DateTimeFormatterBuilder().appendPattern(aikamuoto)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .toFormatter();

        String aamukuusi = "06.00";
        String iltakymmenen = "22.00";

        LocalTime t1 = LocalTime.parse(aloitus, aikaformatointi);
        LocalTime t2 = LocalTime.parse(lopetus, aikaformatointi);
        LocalTime t3 = LocalTime.parse(iltakymmenen, aikaformatointi);
        LocalTime t4 = LocalTime.parse(aamukuusi, aikaformatointi);

        if ((t1.isAfter(t3) && t2.isBefore(t4)) || (t1.isAfter(t3) && t2.equals(t4) || (t1.equals(t3) && t2.isBefore(t4) || (t1.equals(t3) && t2.equals(t4))))) {//Jos aloitusaika on 22.00 jälkeen ja lopetusaika on ennen 06.00 niin tulostetaan hyvitystä aloitusaika - lopetusaika väliltä.

            //t2 = LocalTime.parse(lopetus, pmformat);
            Duration erotus = Duration.between(t2, t1);
            diffminute = erotus.toMinutes();
            diffminute = 24 * 60 - diffminute;//worksssss
            tallennus.Hyvitysalkukello = aloitus;
            tallennus.Hyvitysloppukello = lopetus;

        } else if (t1.isAfter(t3) && t2.isAfter(t4)) {//Jos aloitusaika on 22.00 jälkeen ja lopetusaika on 06.00 jälkeen niin siirrytään sisempään if lauseeseen
            if (t1.isAfter(t3) && t2.isAfter(t3) && t1.isBefore(t2)) {//Jos aloitusaika on 22.00 jälkeen ja lopetusaika on aloitusajan jälkeen mutta klo 22.00 jälkeen ja ennen keskiyötä niin tulostaa hyvitystä aloitusaika - lopetusaika väliltä
                Duration erotus = Duration.between(t1, t2);
                diffminute = erotus.toMinutes();
                tallennus.Hyvitysalkukello = aloitus;
                tallennus.Hyvitysloppukello = lopetus;
                //System.out.print(diffminute + "lullled");//works
            } else {
                //t4 = LocalTime.parse(aamukuusi, pmformat);
                Duration erotus = Duration.between(t4, t1);
                diffminute = erotus.toMinutes();
                diffminute = 24 * 60 - diffminute;
                tallennus.Hyvitysalkukello = aloitus;
                tallennus.Hyvitysloppukello = aamukuusi;
                //System.out.print(diffminute + "watefackj"); //works
            }

        } else if ((t1.isBefore(t4) && t2.isBefore(t4) && t1.isBefore(t2)) || (t1.isBefore(t4) && t2.equals(t4) && t1.isBefore(t2))) {//Jos aloitusaika on ennen 06.00 ja lopetusaika on aloitusajan jälkeen mutta ennen 06.00 niin tulostaa hyvitystä aloitusaika - lopetusaika väliltä.
            //t2 = LocalTime.parse(lopetus, pmformat);
            Duration erotus = Duration.between(t1, t2);
            diffminute = erotus.toMinutes();
            tallennus.Hyvitysalkukello = aloitus;
            tallennus.Hyvitysloppukello = lopetus;
            //diffminute = 24*60- diffminute;
            //System.out.print(diffminute);//works
        } else if (t1.isBefore(t4) && t2.isBefore(t4) && t2.isBefore(t1)) { //Jos aloituskellonaika on ennen 06.00 ja lopetusaika on ennen aloitusaikaa mutta ennen klo 06.00 niin tulostaa hyvitystä aloitusaika - 06.00 + 22.00 + lopetusaika väliltä.
            //t2 = LocalTime.parse(lopetus, pmformat);
            Duration erotus2 = Duration.between(t1, t4);
            Duration erotus = Duration.between(t2, t3);
            diffminute = erotus.toMinutes();
            diffminute = 24 * 60 - diffminute;
            diffminute2 = erotus2.toMinutes();
            //diffminute2 = 24*60 - diffminute2;
            diffminute = diffminute2 + diffminute;

            tallennus.Hyvitysalkukello = aloitus + " - " + aamukuusi;
            tallennus.Hyvitysloppukello = iltakymmenen + " - " + lopetus;
            //System.out.print(diffminute);//works
        } else if (t1.isBefore(t3) && t2.isAfter(t3)) {//Jos aloitusaika on ennen 22.00 ja lopetusaika on aloitusajan jälkeen ennen keskiyötä tulostaa hyvitystä 22.00 - lopetusaika väliltä.
            Duration erotus = Duration.between(t3, t2);
            diffminute = erotus.toMinutes();
            tallennus.Hyvitysalkukello = iltakymmenen;
            tallennus.Hyvitysloppukello = lopetus;
            //System.out.print(diffminute);//toimii
        } else if (t1.isBefore(t3) && t2.isBefore(t4)) { //Jos aloitusaika on ennen 22.00 ja lopetusaika on ennen 06.00 tulostaa hyvitystä 22.00 - lopetusaika väliltä.
            //t2 = LocalTime.parse(lopetus, pmformat);
            Duration erotus = Duration.between(t2, t3);
            diffminute = erotus.toMinutes();
            diffminute = 24 * 60 - diffminute;
            tallennus.Hyvitysalkukello = iltakymmenen;
            tallennus.Hyvitysloppukello = lopetus;
            //System.out.print(diffminute);//works
        } else if (t1.isBefore(t3) && t2.isAfter(t4) && t1.isAfter(t2)) { //Jos annettu aloituskellonaika on ennen klo 22.00 ja lopetus kellonaika on yli 06.00 mutta ennen aloitusaikaa niin tulostaa hyvitystä 22.00-06.00 väliltä.
            //t2 = LocalTime.parse(lopetus, pmformat);
            Duration erotus = Duration.between(t4, t3);
            diffminute = erotus.toMinutes();
            diffminute = 24 * 60 - diffminute;
            tallennus.Hyvitysalkukello = iltakymmenen;
            tallennus.Hyvitysloppukello = aamukuusi;

        }

        tallennus.Tunniste = tunniste;
        tallennus.Aloitusaika = aloitus;
        tallennus.Lopetusaika = lopetus;
        tallennus.hyvitys = diffminute;
        //tallennus.tulosta();
        yohyvitys.add(tallennus);

    }

}
