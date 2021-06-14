package golfrekisteri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Pitää yllä pelaajarekisteriä, osaa lisätä ja poistaa pelaajan
 * Lukea ja kirjottaa pelaajat tiedostoon
 * Osaa etsiä ja lajitella pelaajia
 * 
 * @author Niko Kynsijarvi
 * @version 23.2.2021
 *
 */
public class Pelaajat {

    private static final int MAX_PELAAJIA = 5;
    private int lkm = 0;
    private Pelaaja alkiot[] = new Pelaaja[MAX_PELAAJIA];
    
    
    /**Oletusmuodostaja
     */
    public Pelaajat () {
        alkiot = new Pelaaja[MAX_PELAAJIA];
    }
    
    /**Palauttaa pelaajien lukumaaran
     * @return pelaajien lukumaara
     */
    public int getLkm() {
        return lkm;
    }
    /** Lisaa pelaajan tietorakenteeseen
     * @param pelaaja joka lisataan tietorakenteeseen
     * @throws SailoException jos tietorakenne jo taynna;
     * **@example
     * <pre name="test">
     * #THROWS SailoException 
     * Pelaajat pelaajat = new Pelaajat();
     * Pelaaja a = new Pelaaja(); Pelaaja a2 = new Pelaaja();
     * pelaajat.getLkm() === 0;
     * pelaajat.lisaa(a); pelaajat.getLkm() === 1;
     * pelaajat.lisaa(a2); pelaajat.getLkm() === 2;
     * pelaajat.lisaa(a); pelaajat.getLkm() === 3;
     * pelaajat.anna(0) === a;
     * pelaajat.anna(1) === a2;
     * pelaajat.anna(6) === a; #THROWS IndexOutOfBoundsException 
     * pelaajat.lisaa(a);
     * pelaajat.lisaa(a);
     * pelaajat.lisaa(a);
     * pelaajat.lisaa(a);
     * pelaajat.lisaa(a);
     * pelaajat.lisaa(a);
     * pelaajat.lisaa(a);
     * </pre>
     */
    public void lisaa(Pelaaja pelaaja) throws SailoException {
        if(lkm >= alkiot.length)  {
                Pelaaja [] uusi = new Pelaaja[lkm+5];
                for(int i = 0; i < lkm; i++) {
                    uusi[i] = this.alkiot[i];
                }
                this.alkiot = uusi;
                this.alkiot[this.lkm] = pelaaja; 
                lkm++;
        }else {
            this.alkiot[this.lkm] = pelaaja; 
            lkm++;
        }  
    }
    
    /** Poistaa valitun pelaajan alkioista
     * @param tunnus poistettavan pelaajan tunnusnumero
     * @return jos poistettavaa ei löydy 0, muuten 1
     */
    public int poista(int tunnus) {
        int nro = etsiTunnus(tunnus);
        if(nro < 0) return 0;
        lkm--;
        for(int i = nro; i<lkm; i++) {
            alkiot[i] = alkiot[i+1];
        }
        alkiot[lkm] = null;
        return 1;
    }
    
    /** Etsitään valittu pelaaja alkioista
     * @param nro pelaajan tunnusnumero
     * @return pelaajan indeksi jos löytyy, muuten -1
     */
    public int etsiTunnus(int nro) {
        for(int i = 0; i < lkm; i++) {
            if(nro == alkiot[i].getTunnusNro()) return i;
        }
        return -1;
    }
    
    /** Etsitaan ja lajitellaan pelaajat hakuehdon mukaan
     * @param hakuehto jolla halutaan lajitella
     * @return lajitellut pelaajat
     */
    public Collection <Pelaaja> etsi(String hakuehto){
        List<Pelaaja> lajiteltu = new ArrayList<Pelaaja>();
        for(int i = 0; i < lkm; i++) {
            lajiteltu.add(alkiot[i]);
        }
        Collections.sort(lajiteltu, new Pelaaja.Vertailija(hakuehto));
        return lajiteltu;
    }
    
    /** Haetaan tietorakenteesta paikassa i olevan pelaajan viite
     * @param i indeksi josta etsitaan pelaaja
     * @return viite pelaajaan, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Pelaaja anna (int i) throws IndexOutOfBoundsException {
        if(i<0 || this.lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    /** Tallenetaan tiedostoon
     * @throws SailoException jos tallennus epäonnistuu
     */
    public void tallenna() throws SailoException {
        File fil = new File("pelaajat");
        try(PrintStream fo = new PrintStream(new FileOutputStream(fil, false))){
            for (int i=0; i<getLkm(); i++) {
                Pelaaja pelaaja = anna(i);
                fo.println(pelaaja.toString());
            }

         } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + fil.getAbsolutePath() + " ei aukea");
        }        

    }
    
    /** Luetaan tiedostosta
     * @param tiednimi tiedosto joka luetaan
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedosto(String tiednimi) throws SailoException {
        String nimi = tiednimi;
        File tied = new File(nimi);
        
        try (Scanner fi = new Scanner(new FileInputStream(tied))) {
            while ( fi.hasNext() ) {
                String s = "";
                s = fi.nextLine();
                Pelaaja pelaaja = new Pelaaja();
                pelaaja.parse(s); 
                lisaa(pelaaja);
            }
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Ei saa luettua tiedostoa " + nimi);
       
        }
    }
    
    /**
     * @param args ei kayteta
     */
    public static void main(String[] args) {
       Pelaajat pelaajat = new Pelaajat();
       
       try {
        pelaajat.lueTiedosto("pelaajat");
    } catch (SailoException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
    }
       
       Pelaaja peluri = new Pelaaja();
      
       
       peluri.rekisteroi();
     
      
       
      
      try {
          pelaajat.lisaa(peluri);
          System.out.println(peluri.getTunnusNro());
          
          
         
      } catch (SailoException e) {
          System.out.println(e.getMessage());
      }

      System.out.println("============= Pelaajat testi =================");
      
      for(int i = 0; i < pelaajat.getLkm(); i++) {
          Pelaaja pelaaja = pelaajat.anna(i);
          System.out.println("Pelaaja nro: " + i);
          pelaaja.tulosta(System.out);
      }
     
      try {
        pelaajat.tallenna();
    } catch (SailoException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    }

}
