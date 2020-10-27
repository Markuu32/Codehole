/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package työvuoro;

import java.util.Scanner;

/**
 *
 * @author Markus
 */
public class Työvuoro {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { // While loopit pitävät niinsanottua käyttöliittymää päällä niin kauan kunnes lopetetaan. Kysytään mitä käyttäjä haluaa tehdä ja kutsutaan metodeja luokasta Tyovuorotiedot käyttäjän syötteen mukaan.
        Scanner sc = new Scanner(System.in);
        int toiminto;
        Boolean jatka, haeuusi, uusivuoro = true;
        Tyovuorotiedot ff = new Tyovuorotiedot();

        while (jatka = true) {
            System.out.print("1. Syötä työvuoron tiedot. \n2. Hae yöhyvityksen määrä tunnisteella.\n3. Lopeta.\nValitse(1-3): ");
            toiminto = sc.nextInt();

            if (toiminto == 1) {
                while (uusivuoro = true) {
                    ff.syotaTyovuorotiedot();

                    System.out.print("Syötetäänkö uusi?(K/E): ");
                    String valinta = sc.next();

                    if (valinta.equals("e") || valinta.equals("E")) {
                        break;

                    } else if (valinta.equals("k") || valinta.equals("K")) {
                        continue;
                    }
                }
            } else if (toiminto == 2) {
                while (haeuusi = true) {

                    ff.haeyohyvitys();
                    System.out.print("Haetaanko toinen?(K/E)");
                    String valinta = sc.next();
                    if (valinta.equals("e") || valinta.equals("E")) {
                        break;
                    } else if (valinta.equals("k") || valinta.equals("E")) {
                        continue;
                    }
                }
            } else if (toiminto == 3) {
                break;
            }

        }

    }

}
