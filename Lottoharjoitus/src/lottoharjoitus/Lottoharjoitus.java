
package lottoharjoitus;
import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.text.DecimalFormat;



//Markus Leino
public class Lottoharjoitus {
private static final int VALINTALUVUT = 7; //Määrätään ennakkoon montako numeroa voidaan valita
private static final int MAXNUMERO = 40; //Arvonnan ylin numero


    public static void main(String[] args) {
        
        //Todennakoisyydet();       
        
        
        
        Scanner lukija = new Scanner(System.in);
        List<Integer> OmatNumerot = new ArrayList<Integer>();
        List<Integer> TulosRivinNumerot = new ArrayList<Integer>();
        List<Integer> Vertailu = new ArrayList<Integer>();
        
        int lisaluku = LisaNumero();
        int alku = 0;
        int loppu = 7;
        int[] tulosrivi = new int[VALINTALUVUT];
        int[] valitutnumerot = new int[VALINTALUVUT];
        
        System.out.print("Anna pelattavien rivien määrä: ");
        int rivimaara = lukija.nextInt();
        
        for(int i = 1; i < rivimaara + 1; i++){ //Kutsuu rivinnumerot metodia rivimaaran lukumäärän mukaan, järjestää numerot järjestykseen ja tallentaa sen jälkeen numerot omatnumerot listaan.
            System.out.print("Rivi " + i + "\n");
            valitutnumerot = RivinNumerot(); 
            JarjestaRivi(valitutnumerot);
            
            for(int j = 0; j < VALINTALUVUT; j++){
                OmatNumerot.add(valitutnumerot[j]);
            }
        }
        
        tulosrivi = TulosRivinArvonta(); //Arpoo tuloksen numerot ja tallentaa ne tulosnumerolistaan.
        for(int i = 0; i < VALINTALUVUT; i++){
            TulosRivinNumerot.add(tulosrivi[i]);
        }
        
        for(int p = 1; p < rivimaara + 1; p++){//Ottaa omasta listasta 7 numeroa ja siirtää ne listaan joka verrataan voittoriviin. Useamman rivin numerot tallennetaan samaan listaan josta otetaan 7 ensimmäistä numeroa vertailu listaan.
            for(int a = alku; a < loppu; a++){//Lopuksi tyhjennetään vertailu lista jotta loopin alkaessa alusta vertailu lista on tyhjä seuraaville numeroille.
                Vertailu.add(OmatNumerot.get(a));           
            }
            alku = alku + 7;
            loppu = loppu + 7;
            System.out.print(p + ". Rivi" + Vertailu);
            vertaaRiveja(TulosRivinNumerot, Vertailu, lisaluku);
            Vertailu.clear();
        }
        
        TulosRivinTulostus(tulosrivi); //Tulostetaan voittorivi ja lisänumero
        System.out.print("\nLisänumero: " + lisaluku + " ");
    }
    
    
    //Metodit
 
    public static int[] RivinNumerot(){// Metodi jossa valitaan rivin numerot
        Scanner lukija = new Scanner(System.in);
        int omatnumerot[] = new int[VALINTALUVUT];
        
        for(int i = 0; i < VALINTALUVUT; i++){
            System.out.print("Anna " + (i + 1) + ". numero: ");
            int luku = lukija.nextInt();
            
            for(;luku < 1 || luku > MAXNUMERO;){
                System.out.print("Anna luku väliltä 1-" + MAXNUMERO + ": "); //Tarkistetaan että annettu luku on sallittujen lukujen välissä.
                luku = lukija.nextInt();
            }
            
            for(int j = 0; j < VALINTALUVUT; j++){
                
                for(;luku == omatnumerot[j];){
                    System.out.print("Luku on jo valittu, anna uusi: ");//Tarkistetaan ettei sama numero tule samaan riviin.
                    luku = lukija.nextInt();
                }
            }
            
            omatnumerot[i] = luku;
        }
        
        return omatnumerot;
    }
    
    
    public static int[] TulosRivinArvonta(){//Metodi jossa arvotaan voittorivi
        Random rand = new Random();
        int lottorivi[] = new int[VALINTALUVUT];
        for(int i = 0; i < VALINTALUVUT; i++){
            int luku = rand.nextInt(MAXNUMERO);
            
            for(int j = 0; j < lottorivi.length; j++){
                
                for(;lottorivi[j] == luku + 1;){
                    luku = rand.nextInt(MAXNUMERO);
                }
            }
            
            lottorivi[i] = luku + 1;
        }
        
        JarjestaRivi(lottorivi);
        return lottorivi;
    }
    
    
    public static int LisaNumero(){
        Random rand = new Random();
        int luku = rand.nextInt(MAXNUMERO);
        return luku;
    }
    
    
    public static void TulosRivinTulostus(int lottorivi[]){//Tulostaa rivejä.
        JarjestaRivi(lottorivi);
        System.out.print("Oikea rivi: ");
        
        for(int i = 0; i < lottorivi.length; i++){
            System.out.print(lottorivi[i] + " ");
        }
    }
    
    
    public static void JarjestaRivi(int lottorivi[]){//Järjestää rivien luvut suuruusjärjestykseen.
        int koko = lottorivi.length;
        
        for (int i = 1; i < koko; i++){
            
            for(int j = i; j > 0 && lottorivi[j - 1] > lottorivi[j]; j--){
                int apu = lottorivi[j - 1];
                lottorivi[j - 1] = lottorivi[j];
                lottorivi[j] = apu;
            }
        }
    }
    
    
    public static int Todennakoisyys(List<Integer> lottorivi, List<Integer> voittonumerot, int lisanumero){//Tarkistaa kuinka monta numeroa oli oikein.
        int lkm = 0;
        int lisalkm = 0;
        for(int i = 0; i < 7; i++){
            
            for(int j = 0; j < 7; j++){
                if(lottorivi.get(j) == voittonumerot.get(i)){                    
                    lkm++;
                    break;
                } 
            }
            
            
            for(int j = 0; j < 7; j++){
                if(lisanumero == voittonumerot.get(i)){                    
                    lisalkm++;
                    break;
                } 
            }
        }
        
        return lkm;
    }
    
    public static void vertaaRiveja(List<Integer> lottorivi, List<Integer> voittonumerot, int lisanumero){//Tarkistaa kuinka monta numeroa oli oikein.
        int lkm = 0;
        int lisalkm = 0;
        for(int i = 0; i < 7; i++){
            
            for(int j = 0; j < 7; j++){
                if(lottorivi.get(j) == voittonumerot.get(i)){                    
                    lkm++;
                    break;
                } 
            }
            
            
            for(int j = 0; j < 7; j++){
                if(lisanumero == voittonumerot.get(i)){                    
                    lisalkm++;
                    break;
                } 
            }
        }
        System.out.println("\nOikeita numeroita: " + lkm + "\nLisänumerot " + lisalkm + "\n");
    }
    
}
