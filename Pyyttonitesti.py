from random import randint
import operator

def etsi_indeksi(etsittava, lause):
    indexsijainti = lause.index(etsittava)
    print(indexsijainti)

def leikkaa_tekstista(indexi, indexi2, leikattava): #Leikkaa tekstistä parametrillä syötetystä tekstistä indexi parametrien välistä tekstin
    leikkausobjecti = slice(indexi, indexi2) 
    leikkuunalku = indexi
    leikkuuloppu = indexi2
    lopputulos = leikattava[leikkausobjecti]
    return lopputulos

def tulosta_tulos(luku, luku2, operaattori): #Tekee laskutoimituksen parametreinä syötetyille numeroille. Operaatio syötetään myös parametrinä
    operaatio_lista = {
        "+": operator.add,
        "-": operator.sub,
        "*": operator.mul,
        "/": operator.truediv
    }

    tulos = operaatio_lista[operaattori](int(luku),int(luku2))
    return tulos

def sanakirja(arvo): #Ottaa parametrin osoittaman numeron mukaan sanakirjasta tekstin ja palauttaa sen.
    teksti = arvo
    monthConversions = {
        1:  "ensimmäisellä",
        2:  "toisella",
        3:  "kolmannella",
        4:  "neljännellä",
        5:  "viidennellä"
    }
    moro = monthConversions[arvo]
    return moro

def arvaus_peli(): #pyytää käyttäjältä numeron 0-10 ja arpoo oikean numeron. Jos syötetty numero vastaa oikeaa numeroa voitit pelin jos ei niin yrityksistä vähennetään yksi.

    yrityksia = 1
    vastauksiajaljella = 5
    oikea_numero = randint(1, 10)

    while yrityksia <= 5:           
            
        try:
                                          
            arvaus = int(input("Arvaa numero väliltä 1 - 10. Sinulla on " + str(vastauksiajaljella) + " yritystä: "))

            if arvaus <= 10 and arvaus >= 0: #Tarkastaa onko arvo väliltä 1-10.

                if oikea_numero == arvaus: # Tarkastaa onko arvaus oikein ja jos arvaus on oikein hakee sanakirja functiosta monennellako arvauksella meni oikein.
                    uudestaan = input("Arvasit oikein " + str(sanakirja(yrityksia)) + " kerralla! Haluatko arvata uudestaan (K/E) ? : ")                  

                    if uudestaan.lower() == "k": #arvasit oikein ja haluat pelata uudestaan. Palautetaan muuttujat function alussa määriteltyyn arvoon ja arvotaan uusi oikea numero sen jälkeen jatketaan while looppia.
                        yrityksia = 1
                        oikea_numero = randint(1, 10)
                        vastauksiajaljella = 5
                        continue

                    elif uudestaan.lower() == "e": #Peli lopetetaan ja palataan takaisin main functioon.
                        print("Lopetit pelin!")
                        main()

                    else: #Syötetty arvo ei vastaa sallittuja arvoja. Vaatii työtä!!!!!!!!!!
                        print("vastaa k tai e")
                        break
                        
                elif oikea_numero != arvaus: #Arvattu numero ei ollut oikein

                    if yrityksia == 5: #Yritykset ovat täynnä ja peli loppuu mikäli et halua pelata uudestaan
                        uudestaan = input("Yritykset loppuivat! Oikea vastaus oli: " + str(oikea_numero) + " Pelataanko uudestaan ? (K/E) : ") 

                        if uudestaan.lower() == "k": #ladataan arvauspeli uudelleen.
                            arvaus_peli()

                        elif uudestaan.lower() == "e":#lopetetaan peli ja palataan main functioon
                            print("Lopetit pelin!")
                            main()

                        else:#Syötetty arvo ei vastaa sallittuja arvoja. Vaatii työtä!!!!!!!!!
                            print("vastaa k tai e")
                            break

                    else:#Arvaus oli väärin, mutta yrityksiä on vielä jäljellä.
                        vastauksiajaljella = vastauksiajaljella - 1
                        print("Väärä vastaus!")
                        yrityksia = yrityksia + 1 
                    
            else: #arvattu numero ei ollu väliltä 1-10. Palataan alkuun
                print("Arvon tulee olla väliltä 1 - 10!")
                    
        except:#otetaan virheet kiinni. Vaatii työtä !!!!!!!!!!!!!!!!!!!!!!!!!
            print("Nyt tuli except")

def laskin_ohjelma():
    laskutoimitus = input("Kirjoita laskutoimitus: ")
    lista = ["+", "-", "*", "/"]
    operaatio = ""

    if "+" in laskutoimitus:
        operaatio = "+"
    elif "-" in laskutoimitus:
        operaatio = "-"
    elif "*" in laskutoimitus:
        operaatio = "*"
    elif "/" in laskutoimitus:
        operaatio = "/"
        
    laskutoimituksenpituus = len(laskutoimitus) #Tarkistaa syötetyn laskutoimituksen pituuden

    merkinerotus = laskutoimitus.index(operaatio) #Etsii laskutoimituksesta operaatio merkin jonka indeksiä käytetään slicen parametrinä
    numeroidenhaku = merkinerotus + 1 #Muuttuja johon lisätään operaatiomerkin indeksi +1 jotta laskin hakee operaatiomerkin jälkeiset numerot. Ilman + 1 slice ottaisi operaatiomerkin mukaan

    alkunumero = leikkaa_tekstista(0, merkinerotus, laskutoimitus) #Syöttää leikkaa_tekstistä functioon aloitus- ja lopetusindexin ja leikattavan lauseen, jolloin saadaan ensimmäiset numerot ennen operaatiomerkkiä
    loppunumero = leikkaa_tekstista(numeroidenhaku, laskutoimituksenpituus, laskutoimitus) #Syöttää leikkaa_tekstistä functioon aloitus- ja lopetusindexin ja leikattavan lauseen, jolloin saadaan jälkimmäiset numerot ennen operaatiomerkkiä
    tulos = (tulosta_tulos(alkunumero, loppunumero, operaatio))
    print((alkunumero) + " " + (operaatio) + " " + (loppunumero) + " = " + (str(tulos)))

       

def main(): #Main functio. Käyttöliittymän aloituspiste josta valitaan haluttu peli/toiminto
    vastaus = input("1. Arvauspeli\n2. Laskin\nValitse toiminto: ")   

    if vastaus == "1":
        arvaus_peli()

    elif vastaus == "2":
        laskin_ohjelma()     
    

main()
