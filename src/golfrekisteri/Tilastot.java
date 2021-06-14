package golfrekisteri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**Pitaa ylla rekisteria tilastoista
 * Lukee ja kirjoittaa tilastot tiedostoon
 * Etsii ja lajittelee tiedostoja
 * @author Niko Kynsijarvi
 * @version 10.3.2021
 *
 */
public class Tilastot implements Iterable<Tilasto> {

    private final Collection <Tilasto> alkiot = new ArrayList <Tilasto> ();
    
    /** Poistaa pelaajan tilastot tilastoista
     * @param tunnus pelaajan tunnus jonka tilastot poistetaan
     */
    public void poistaTilastotPelaajalta(int tunnus) {
        for(Iterator <Tilasto> til = alkiot.iterator(); til.hasNext();) {
            Tilasto tilasto = til.next();
            if(tilasto.getPelaajaNro() == tunnus) {
                til.remove();
            }
        }
    }
    
    /** Haetaan pelaajan tietyn tilaston keskiarvo
     * @param nro pelaajan numero
     * @param ha hakuehto
     * @return summa
     */
    public String annaTilastoKa(int nro, String ha) {
        double summa = 0;
        int i = 0;
        for(Tilasto t: alkiot) {
            if(t.getPelaajaNro() == nro && t.getNimi() == ha) {
                summa += Double.parseDouble(t.getArvo());
                i++;
            }
        }
        return (summa/i)+"";
    }
    
    /**Alustaminen
     */
    public Tilastot() {
        //
    }
    
    /** lisaa tilaston alkioihin
     * @param ti lisattava tilasto
     */
    public void lisaa(Tilasto ti) {
        alkiot.add(ti);
    }
    
    
    /** Hae kaikki pelaajan tilastot
     * @param tunnusnro pelaajan numero jolla etsitaan
     * @return loydetyt tilastot
     * **@example
     * <pre name="test">
     * #import java.util.*;
     * Tilastot t = new Tilastot();
     * Tilasto ti = new Tilasto(1); t.lisaa(ti);
     * Tilasto ti2 = new Tilasto(2); t.lisaa(ti2);
     * Tilasto ti3 = new Tilasto(1); t.lisaa(ti3);
     * List<Tilasto> loytyneet;
     * loytyneet = t.annaTilastot(1);
     * loytyneet.size() === 2;
     * loytyneet.get(0) == ti === true;
     * loytyneet.get(1) == ti3 === true;
     * </pre>
     */
    public List<Tilasto> annaTilastot(int tunnusnro){
        List<Tilasto> loydetyt = new ArrayList <Tilasto>();
        for(Tilasto t : alkiot) {
            if(t.getPelaajaNro() == tunnusnro) loydetyt.add(t);
        }
        return loydetyt;
    }
    
    /** Luetaan tiedosto
     * @param tiedosto joka luetaan
     * @throws SailoException jos lukeminen epaonnistuu
     */
    public void lueTiedosto(String tiedosto) throws SailoException {
        File tied = new File(tiedosto);
        try (Scanner fi = new Scanner(new FileInputStream(tied))) {
            while ( fi.hasNext() ) {
                String s = "";
                s = fi.nextLine();
               
               Tilasto tilasto = new Tilasto();              
                tilasto.parse(s);          
                lisaa(tilasto);
            }
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Ei saa luettua tiedostoa " + tiedosto);
       
        }
        
    }
    
    /** Tallennetaan tiedostoon
     * @throws SailoException jos tallennus epaonnistuu
     */
    public void tallenna() throws SailoException {
        File fil = new File("tilastot");
        try(PrintStream fo = new PrintStream(new FileOutputStream(fil, false))){
            for(Tilasto ti : alkiot) {              
                fo.println(ti.toString());
            }

         } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + fil.getAbsolutePath() + " ei aukea");
        }        

    }
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Tilastot tila = new Tilastot();
        try {
            tila.lueTiedosto("tilastot");
        }catch (SailoException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
      
        
       
      
       
       
        
        System.out.println("============= Tilastot testi =================");
        
       
        for(var t : tila) {
            System.out.println(t.getPelaajaNro());
            t.tulosta(System.out);
        }
        
        try {
            tila.tallenna();
        } catch (SailoException e1) {
        
            e1.printStackTrace();
        }
        
        
    }

    
    /** Iteraattori kaikkien tilastojen läpikäymiseen
     * @return tilastoiteraattori
     * **@example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * Tilastot t = new Tilastot();
     * Tilasto ti = new Tilasto(1); t.lisaa(ti);
     * Tilasto ti2 = new Tilasto(2); t.lisaa(ti2);
     * Tilasto ti3 = new Tilasto(1); t.lisaa(ti3);
     * 
     * Iterator<Tilasto> i2=t.iterator();
     * i2.next() === ti;
     * i2.next() === ti2;
     * i2.next() === ti3;
     *
     * </pre>
     */
    @Override
    public Iterator<Tilasto> iterator() {
        
        return alkiot.iterator();
    }

}
