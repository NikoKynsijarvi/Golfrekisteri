package golfrekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Comparator;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * ei tiedä rekisteristä eikä käyttöliittymästä
 * tietää pelaajan kentät (nimi, ikä, kansalaisuus yms)
 * osaa antaa merkkijonona i:n kentän tiedot
 * osaa laittaa merkkijonon i:neksi kentäksi
 * 
 * @author Niko Kynsijarvi
 * @version 23.2.2021
 *
 */
public class Pelaaja {

    private int tunnusNro;
    private String nimi = "";
    private String kansalaisuus = "";
    private String pituus;
    private String maailmanranking;
    private String ika;
    
    private static int seuraavaNro    = 1;
    
    
    /**
     * @author Niko Kynsijarvi
     * @version 12.4.2021
     *Pelaajien vertailija
     */
    public static class Vertailija implements Comparator <Pelaaja>{
        private String hakuehto;

        /** Vertailijan muodostaja
         * @param he hakuehto
         */
        public Vertailija(String he) {
            this.hakuehto = he;
        }
        
        /** Verrataan pelaajia hakuehdon mukaan
         */
        @Override
        public int compare(Pelaaja o1, Pelaaja o2) {
            return o1.getVertaus(hakuehto).compareToIgnoreCase(o2.getVertaus(hakuehto)); 
        }
        
    }
    
    /** Palauttaa kentan jolla verrataan
     * @param he hakuehto
     * @return kentta jolla verrataan
     */
    public String getVertaus(String he) {
        switch(he) {
        case "nimi": return ""+this.nimi;
        case "ika": return ""+ this.ika;
        case "pituus": return ""+ this.pituus;
        case "ranking": return ""+ this.maailmanranking;
        default: return "joo";
        }
    }
    
    /** Asetetaan tunnusnumero pelaajalle
     * @param nr tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if(tunnusNro >= seuraavaNro)  seuraavaNro = tunnusNro + 1;
        
    }
    
    /** Haetaan pelaajan tunnusnumero ja palautetaan se
     * @return palauttaa pelaajan tunnusnumeron
     */
    public int getTunnusNro() {
        return this.tunnusNro;
    }
    
    /** Haetaan pelaajan nimi ja palautetaan se
     * @return palauttaa pelaajan nimen
     * **@example
     * <pre name="test">
     * Pelaaja a = new Pelaaja();
     * a.taytaPeluri();
     * a.getNimi() === "Rory McIlroy";
     * </pre>
     */
    public String getNimi() {
        return this.nimi;
    }
    
    /** Palauttaa pelaajan ian
     * @return pelaajan ika
     */
    public String getIka() {
        if(this.ika == null) ika="";
        return this.ika;
    }
    
    /** Palauttaa pelaajan pituuden
     * @return pelaajan pituus
     */
    public String getPituus() {
        if(this.pituus == null) this.pituus = "";
        return this.pituus;
    }
    /** Palauttaa pelaajan kansalaisuuden
     * @return pelaajan kansalaisuus
     */
    public String getKansalaisuus() {
        if(this.kansalaisuus == null) this.kansalaisuus= "";
        return this.kansalaisuus;
    }
    /** Palauttaa pelaajan rankingin
     * @return pelaajan ranking
     */
    public String getRanking() {
        if(this.maailmanranking == null) this.maailmanranking = "";
        return this.maailmanranking;
    }
    
    /** Asetetaan pelaajan nimi ja tarkastetaan oikeellisuus
     * @param s Nimi 
     * @return null
     */
    public String setNimi(String s) {
        if ( s.matches("[0-9]*") ) return "Nimi oltava kirjaimia";
        nimi = s;
        return null;
    }
    
    /** Asetetaan pelaajan ika ja tarkistetaan oikeellisuus
     * @param s ika
     * @return null
     */
    public String setIka(String s) {
        if ( !s.matches("[0-9]*") ) return "Ika oltava numeroita";
        ika = s;
        return null;
    }
    /** Asetetaan pelaajan pituus ja tarkistetaan oikeellisuus
     * @param s pituus
     * @return null
     */
    public String setPituus(String s) {
        if ( !s.matches("[0-9]*") ) return "Pituus oltava numeroita";
        pituus = s;
        return null;
    }
    /** Asetetaan pelaajan kansalaisuus ja tarkistetaan oikeellisuus
     * @param s kansalaisuus
     * @return null
     */
    public String setKansalaisuus(String s) {
        if ( s.matches("[0-9]*") ) return "Kansalaisuus oltava kirjaimia";
        kansalaisuus = s;
        return null;
    }
    /** Asetetaan pelaajan ranking ja tarkistetaan oikeellisuus
     * @param s ranking 
     * @return null
     */
    public String setRanking(String s) {
        if ( !s.matches("[0-9]*") ) return "Ranking oltava numeroita";
        maailmanranking = s;
        return null;
    }
    
    
    /**Antaa pelaajalle tunnusnumeron.
     * @return palauttaa rekisteröidyn pelaajan tunnusnumeron
     * 
     * **@example
     * <pre name="test">
     * Pelaaja peluri3 = new Pelaaja();
     * peluri3.getTunnusNro() === 0;
     * peluri3.rekisteroi();
     * Pelaaja peluri4 = new Pelaaja();
     * peluri4.rekisteroi();
     * int i = peluri3.getTunnusNro();
     * int i2 = peluri4.getTunnusNro();
     * i === i2 - 1;
     * </pre>
     */
    public int rekisteroi() {
        this.tunnusNro =  seuraavaNro;
        seuraavaNro++;
        return this.tunnusNro;
    }
    
    /** Tulostetaan pelaajan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
         out.println(String.format("%03d", tunnusNro, 3) + "  " + nimi + "  " + ika +"vuotta");
         out.println("  " + kansalaisuus + "  " + pituus + "ft " + maailmanranking);
                  
              }
    
    /**
     * Tulostetaan pelaajan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /** Luodaan satunnainen numero pelaajalle
     * @param ala arvonnan alaraja
     * @param yla arvonna ylaraja
     * @return random numero
     */
    public static int rand(int ala, int yla) {
        double n = (yla - ala) *Math.random() + ala;
        return (int)Math.round(n);
    }
    
    @Override
    public String toString() {
        return "" + 
                getTunnusNro() +"|"+
                nimi + "|" +
                ika + "|" +
                pituus + "|" +
                kansalaisuus + "|" +
                maailmanranking;
    }
    
    /**
     * Apumetodi, jotta saadaan alustettua arvot pelaajalle
     */
    public void taytaPeluri() {
        this.nimi="Rory McIlroy";
        this.kansalaisuus = "Pohjois Irlanti";
        this.pituus = 6.0+"";
        this.maailmanranking = 4+"";
        this.ika = 31+"";
    }
    
    /** Asettaa pelaajan tiedot merkkijonosta
     * @param rivi merkkijono jossa tiedot
     * **@example
     * <pre name="test">
     * Pelaaja pelaaja = new Pelaaja();
     * pelaaja.parse(" 1 | Niko Koo | 22");
     * pelaaja.getTunnusNro() === 1;
     * </pre>
     */
    public void parse(String rivi) {
        var sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        nimi = Mjonot.erota(sb, '|', nimi);
        ika = Mjonot.erota(sb , '|', ika);
        pituus = Mjonot.erota(sb, '|', pituus);
        kansalaisuus = Mjonot.erota(sb, '|', kansalaisuus);
        maailmanranking = Mjonot.erota(sb, '|', maailmanranking);
    }
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
       Pelaaja peluri = new Pelaaja();
       Pelaaja peluri2 = new Pelaaja();
       
       peluri.rekisteroi();
       peluri2.rekisteroi();
       
       peluri.taytaPeluri();
       peluri2.taytaPeluri();
       
       peluri.tulosta(System.out);
       peluri2.tulosta(System.out);

    }

}
