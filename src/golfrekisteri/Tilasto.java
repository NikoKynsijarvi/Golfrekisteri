package golfrekisteri;

import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Niko Kynsijarvi
 * @version 10.3.2021
 * Ei tiedä rekisteristä tai käyttöliittymästä
 * Tietää tilastojen kentät
 * Osaa muuttaa 1|1|1|314.00 merkkojonon tilastoksi ja hakea sille oikea paikka ja nimi
 * Osaa antaa merkkijonona i:nnen paikan tiedot
 * Osaa laittaa merkkijonon i:neksi kentäksi 
 */
public class Tilasto {
    
     private String arvo;
     private int tunnusNro;
     private int pelaajaNro;
     private String tilastoNimi;
     private String yksikko;
     
     
     private static int seuraavaNro = 1;
     
     /** Asetetaan tunnusnumero
      * @param nro tunnusnumero
      */
     private void setTunnusNro(int nro) {
         tunnusNro = nro;
         if(tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro +1;
     }
     
     /** Asetetaan tilastolle nimi
     * @param s tilaston nimi
     * @return null
     */
    public String setNimi(String s) {
         this.tilastoNimi = s;
         return null;
     }
     /** Asetetaan tilastolle yksikko
     * @param s tilastoyksikko
     * @return null
     */
    public String setYksikko(String s) {
         this.yksikko = s;
         return null;
     }
     /** Asetetaan tilasolle arvo
     * @param s tilastonarvo
     * @return null
     */
    public String setArvo(String s) {
         this.arvo = s;
         return null;
     }
     
     /** Antaa tilastolle seuraavan tunnusnumeron
     * @return tunnusnumero
     * **@example
     * <pre name="test">
     * Tilasto tilasto = new Tilasto();
     * tilasto.getTunnusNro() === 0;
     * tilasto.rekisteroi();
     * Tilasto tilasto2 = new Tilasto();
     * tilasto2.rekisteroi();
     * int n1 = tilasto.getTunnusNro();
     * int n2 = tilasto2.getTunnusNro();
     * n1 === n2 - 1;
     * </pre>
     */
    public int rekisteroi() {
          tunnusNro = seuraavaNro;
          seuraavaNro++;
          return tunnusNro;
     }
     
    /** Muodostaja
     */
    public Tilasto() {
        //
    }
    
    
    
     /** Muodostaja tilastolle
     * @param pelaajaNro pelaajan tunnusnumero
     */
    public Tilasto(int pelaajaNro) {
         this.pelaajaNro = pelaajaNro;     
     }
     
     
     /** Tulostaa tilaston tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
         out.println(this.tilastoNimi + " " + this.arvo + " " + this.yksikko );
     }
    
    /** Asettaa tilaston tiedot merkkijonosta
     * @param rivi merkkojono jossa tiedot
     *<pre name="test">
     * Tilasto t = new Tilasto();
     * t.parse(" 1 | 1 | Driving distance");
     * t.getTunnusNro() === 1;
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        pelaajaNro = (Mjonot.erota(sb, '|', pelaajaNro));
        tilastoNimi = (Mjonot.erota(sb, '|', tilastoNimi));
        arvo = (Mjonot.erota(sb, '|', arvo));
        yksikko = (Mjonot.erota(sb, '|', yksikko));
    }
    
    @Override
    public String toString() {
        return "" +
                getTunnusNro() + "|" +
                pelaajaNro + "|" +
                tilastoNimi + "|" +
                arvo + "|" +
                yksikko;
    }
     
     /** Palautetaan tilaston oma id numero
     * @return tilaston tunnusnumero
     * **@example
     * <pre name="test">
     * Tilasto a = new Tilasto();
     * a.getTunnusNro() === 0;
     * </pre>
     */
    public int getTunnusNro() {
         return this.tunnusNro;
     }
     
    /** Haetaan tilaston nimi
     * @return tilastoNimis
     */
    public String getNimi() {
        if(tilastoNimi==null) tilastoNimi="";
        return this.tilastoNimi;
    }
    /** Haetaan tilaston arvo
     * @return tilaston arvo
     */
    public String getArvo() {
        if(this.arvo == null) this.arvo= "0";
        return this.arvo;
    }
    /** Haetaan tilaston yksikko
     * @return tilason yksikko
     */
    public String getYksikko() {
        if(this.yksikko == null) this.yksikko="";
        return this.yksikko;
    }
    
     /** Palautetaan kenelle pelaajalle tilasto kuuluu
     * @return pelaajan tunnusnumero
     */
    public int getPelaajaNro() {
         return this.pelaajaNro;
     }

    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Tilasto tilasto= new Tilasto();
        tilasto.rekisteroi();
       
        tilasto.tulosta(System.out);

    }

}
