package golfrekisteri;

import java.util.Collection;
import java.util.List;

/**
 * Huolehtii pelaajat ja tilastot luokkien yhteistyosta
 * Lukee ja kirjoittaa rekisterin tiedostoon pyytamalla apua avustajilta
 * Hakee tilastojen keskiarvot
 * @author Niko Kynsijarvi
 * @version 24.2.2021
 *
 */
public class Rekisteri {

    private Pelaajat pelaajat = new Pelaajat();
    private Tilastot tilastot = new Tilastot();
    
    
    /** Luetaan tiedostot
     * @param tiedosto tiedoston nimi
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedosto(String tiedosto) throws SailoException {
        pelaajat = new Pelaajat();
        pelaajat.lueTiedosto(tiedosto);
        tilastot = new Tilastot();
        tilastot.lueTiedosto("tilastot");
        
    }
    
    /** Etsii ja laskee tilaston keskiarvon
     * @param ha hakuehto
     * @return keskiarvo
     */
    public double getKA(String ha) {
        double summa = 0;
        int i = 0;
        for(Tilasto t : tilastot) {
            if(t.getNimi().equals(ha)) {
                summa += Double.parseDouble(t.getArvo());
                i++;
            }
        }
        return summa/i;
    }
    
    /** Tallentaa rekisterin tiedot tiedostoon
     * @throws SailoException jos tallentaminen epäonnistuu
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            pelaajat.tallenna();
        } catch (SailoException e) {
            virhe = e.getMessage();
        }
        
        try {
            tilastot.tallenna();
        } catch (SailoException e) {
            virhe = e.getMessage();
        }
        
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }
    
    /** Etsitaan ja lajitellaan pelaajat
     * @param hakuehto jolla lajitellaan
     * @return lajitellut pelaajat
     */
    public Collection <Pelaaja> etsi(String hakuehto){
        return pelaajat.etsi(hakuehto);
    }
    
    /**Lisataan uusi pelaaja
     * @param pelaaja lisattava pelaaja
     * @throws SailoException  jos lisaaminen epaonnistuu
     */
    public void lisaa (Pelaaja pelaaja) throws SailoException {
        pelaajat.lisaa(pelaaja);
    }
    
    /** Lisataan uusi tilasto
     * @param tilasto lisattava tilasto
     */
    public void lisaa(Tilasto tilasto) {
        tilastot.lisaa(tilasto);
    }
    
    /** Pelaajien maara
     * @return palauttaa pelaajien lukumaaran
     */
    public int getPelaajia() {
        return this.pelaajat.getLkm();
    }
    
    /**Antaa rekisterin paikassa i olevan pelaajan
     * @param i monesko pelaaja palautetaan 
     * @return viite pelaajaan
     */
    public Pelaaja annaPelaaja(int i) {
        return pelaajat.anna(i);
    }
    
    /** Haetaan pelaajan tilastot
     * @param pelaaja kenen tilastoja haetaan
     * @return lista pelaajan tilastoista
     */
    public List<Tilasto> annaTilastot(Pelaaja pelaaja){
        return tilastot.annaTilastot(pelaaja.getTunnusNro());
    }
    
    /** Haetaan pelaajan tilastot
     * @param pelaaja kenen tilastoja haetaan
     * @param ha a
     * @return lista pelaajan tilastoista
     */
    public String annaTilastoKa(Pelaaja pelaaja, String ha){
        return tilastot.annaTilastoKa(pelaaja.getTunnusNro(), ha);
    }
    
    /** Poistaa pelaajan rekisteristä ja pelaajan tilastot
     * @param pelaaja joka halutaan poistaa
     * @return poistettujen pelaajien maara
     */
    public int poista(Pelaaja pelaaja) {
        if(pelaaja == null) return 0;
        int num = pelaajat.poista(pelaaja.getTunnusNro());
        tilastot.poistaTilastotPelaajalta(pelaaja.getTunnusNro());
        return num;
    }
    
    
    /**
     * @param args ei kayteta
     */
    public static void main(String[] args) {
       Rekisteri rekisteri = new Rekisteri();
       
       Pelaaja peluri = new Pelaaja();
       Pelaaja peluri2 = new Pelaaja();
       
       peluri.rekisteroi();
       peluri2.rekisteroi();
       
       peluri.taytaPeluri();
       peluri2.taytaPeluri();

       try {
        rekisteri.lisaa(peluri);
        rekisteri.lisaa(peluri2);
    } catch (SailoException e) {
        
        System.out.println(e.getMessage());
    }
     
       for(int i = 0; i<rekisteri.getPelaajia(); i++) {
           Pelaaja pelaaja = rekisteri.annaPelaaja(i);
          pelaaja.tulosta(System.out);
       }
    }

}
