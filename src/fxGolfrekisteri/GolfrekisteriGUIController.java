package fxGolfrekisteri;

import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import golfrekisteri.Pelaaja;
import golfrekisteri.Rekisteri;
import golfrekisteri.SailoException;
import golfrekisteri.Tilasto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

/**
 * @author Niko Kynsijarvi
 * @version 16.1.2021
 *
 */
public class GolfrekisteriGUIController  implements Initializable {
  
    @FXML private ListChooser <Pelaaja> chooserPelaajat;
    @FXML private StringGrid<Tilasto> tableTilastot;
    @FXML private ComboBoxChooser<String> hakuKentat;
    @FXML private ScrollPane panelPelaaja;
    @FXML private TextField editNimi;
    @FXML private TextField editIka;
    @FXML private TextField editPituus;
    @FXML private TextField editKansalaisuus;
    @FXML private TextField editRanking;
    @FXML private MenuItem rankingLajittelu;
    @FXML private MenuItem ikaLajittelu;
    @FXML private MenuItem pituusLajittelu;
    @FXML private Label pelaajaDd, kaDd, erotusDd, pelaajaSa, erotusSa, kaSa, pelaajaGir, erotusGir, kaGir, pelaajaSs, erotusSs, kaSs;
    
    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        alusta();
        
    }
              
    @FXML
    void handleTallenna() {  
        tallenna();
    }
    
    @FXML
    void handleTulosta() {
        
        Dialogs.showMessageDialog("Ei osata vielä tulostaa");
    }
    @FXML
    void handleApua() {
        
        Dialogs.showMessageDialog("Ei toimi vielä");
    }
    @FXML
    void handleLajittelu() {
        hae(0);
    }
    @FXML
    void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    @FXML
    void handleUusiPelaaja() {
        uusiPelaaja();
       
    }
    
    @FXML
    void handleUusiTilasto() {
        uusiTilasto();
    }
    

    @FXML
    void handlePelaajanPoisto() {
        
        poistaPelaaja();
   
    }
    
 
    @FXML
    void handleTietoja() {
        
        
        ModalController.showModal(GolfrekisteriGUIController.class.getResource("AloitusnayttoGUIView.fxml"), "Rekisteri", null, "");
    }
    
    
    // ==================================================================================
    
    private Rekisteri rekisteri;
    private Pelaaja pelaajaKohdalla;
    private TextField edits[];

    
    /** Poistaa halutun pelaajan
     */
    private void poistaPelaaja() {
        Pelaaja pelaaja = pelaajaKohdalla;
        if(pelaaja == null) return;
        rekisteri.poista(pelaaja);
        int i = chooserPelaajat.getSelectedIndex();
        hae(0);
        chooserPelaajat.setSelectedIndex(i);
    }
    
    /** Luetaan pelaajat tiedosto
     * @return true kun luettu
     */
    protected String lueTiedosto() {
        String tiedNimi = "pelaajat";
        try {
            rekisteri.lueTiedosto(tiedNimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage();
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
        
    }
    
    /**Tallennetaan suljettaessa ohjelma ilman lopeta nappia
     * @return true kun suljetaan ohjelma
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    /**Tallennetaan tiedot tiedostoihin
     * @return null jos onnistuu, jos epaonnistuu virheviesti
     */
    private String tallenna() {
        try {
            rekisteri.tallenna();
            return null;
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + e.getMessage());
            return e.getMessage();
        }
    }
    
    /**Tekee tarvittavat alustukset ja vaihtaa GridPanen tilalle yhden ison tekstikentan, johon saadaan
     * tulostettua pelaajan tiedot
     */
    private void alusta() {
        hakuKentat.clear();
        hakuKentat.add("Nimi");
        hakuKentat.add("Pituus");
        hakuKentat.add("Ika");
        hakuKentat.add("Ranking");
        chooserPelaajat.clear();
        chooserPelaajat.addSelectionListener(e -> naytaPelaaja());
        edits = new TextField[]{editNimi, editIka, editPituus, editKansalaisuus, editRanking}; 
        int i = 0;
        for(TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased(e -> kasitteleMuutosPelaajaan(k, (TextField)(e.getSource())));
        }
       
    }
    
    /** Kasitellaan muutos pelajaaan
     * @param k teksikentan indeksi
     * @param edit kasiteltava tekstikentta
     */
    private void kasitteleMuutosPelaajaan(int k, TextField edit) {
        if(pelaajaKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        switch(k) {
            case 1 : virhe = pelaajaKohdalla.setNimi(s); break;
            case 2 : virhe = pelaajaKohdalla.setIka(s); break;
            case 3 : virhe = pelaajaKohdalla.setPituus(s); break;
            case 4 : virhe = pelaajaKohdalla.setKansalaisuus(s); break;
            case 5 : virhe = pelaajaKohdalla.setRanking(s); break;
            default:
        }
        if(virhe == null) {
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
          
        }else {
            Dialogs.setToolTipText(edit, virhe);
            edit.getStyleClass().removeAll("virhe");
          
        }
    }
    /** Naytaa pelaajan tiedot alustettuun tekstikenttaan
     */
    private void naytaPelaaja() {
        pelaajaKohdalla = chooserPelaajat.getSelectedObject();
        if(pelaajaKohdalla == null) return;
        PelaajanlisäysController.naytaPelaaja(edits, pelaajaKohdalla);
        naytaTilastot(pelaajaKohdalla);
       
                
    }
    
    /** Asetetaan alustuksessa tilastojen keskiarvot
     */
    private void asetaKeskiarvot() {
        String ka = rekisteri.getKA("Driving distance")+"";
        String scoringKa = rekisteri.getKA("Average score")+"";
        String girKa = rekisteri.getKA("Greens in regulation")+"";
        String ssKa = rekisteri.getKA("Sand save")+"";
        kaSs.setText(ssKa.substring(0,2));
        kaDd.setText(ka.substring(0, 3));
        kaSa.setText(scoringKa.substring(0,2));
        kaGir.setText(girKa.substring(0,2));
    }
    
    /** Asetetaan pelaajan tilastot ja niiden erotus keskiarvoon
     * @param tilastot
     */
    private void asetaTilastot(List<Tilasto> tilastot) {
        double dd = 0;
        double as = 0;
        double gir = 0;
        double ss = 0;
        for(Tilasto t: tilastot) {
            if(t.getNimi().equals("Driving distance")) {
                if(Double.parseDouble(t.getArvo()) > dd) {
                    dd = Double.parseDouble(t.getArvo());
                }
                
            }
            if(t.getNimi().equals("Average score")) {
                if(Double.parseDouble(t.getArvo()) > as) {
                    as = Double.parseDouble(t.getArvo());
                }
                
            }
            if(t.getNimi().equals("Greens in regulation")) {
                if(Double.parseDouble(t.getArvo()) > gir) {
                    gir = Double.parseDouble(t.getArvo());
                }
                
            }
            if(t.getNimi().equals("Sand save")) {
                if(Double.parseDouble(t.getArvo()) > ss) {
                    ss = Double.parseDouble(t.getArvo());
                }
                
            }
        }
        String ka = rekisteri.getKA("Driving distance")+"";
        String scoringKa = rekisteri.getKA("Average score")+"";
        String girKa = rekisteri.getKA("Greens in regulation")+"";
        String ssKa = rekisteri.getKA("Sand save")+"";
        kaSs.setText(ssKa.substring(0,2));
        kaDd.setText(ka.substring(0, 3));
        kaSa.setText(scoringKa.substring(0,2));
        kaGir.setText(girKa.substring(0,2));
        pelaajaDd.setText(""+dd);
        pelaajaSa.setText(""+as);
        pelaajaGir.setText(""+gir);
        pelaajaSs.setText(""+ss);
        erotusDd.setText(""+(dd - Double.parseDouble(ka)));
        if(erotusDd.getText().length() > 4) {
            erotusDd.setText(erotusDd.getText().substring(0,5));
        }
        erotusSa.setText(""+(as - Double.parseDouble(scoringKa)));
        if(erotusSa.getText().length() > 3) {
            erotusSa.setText(erotusSa.getText().substring(0,4));
        }
        erotusGir.setText(""+(gir - Double.parseDouble(girKa)));
        if(erotusGir.getText().length() > 3) {
            erotusGir.setText(erotusGir.getText().substring(0,4));
        }
        erotusSs.setText(""+(ss - Double.parseDouble(ssKa)));
        if(erotusSs.getText().length() > 3) {
            erotusSs.setText(erotusSs.getText().substring(0,4));
        }
    }
   
    /** Naytetaan pelaajan tilastot
     * @param pel pelaaja jonka tilastot naytetaan
     */
    private void naytaTilastot(Pelaaja pel) {
        tableTilastot.clear();
        if(pel == null)return;
        List<Tilasto> tilastot = rekisteri.annaTilastot(pel);
        asetaTilastot(tilastot);
        if(tilastot.size() == 0) return;
        for(Tilasto ti : tilastot) {
            naytaTilasto(ti);
        }
    }
    
    /** 
     * lisaa tilaston tiedot nayttoon
     * @param ti tilasto joka lisataan
     */
    private void naytaTilasto(Tilasto ti) {
        String [] rivi = ti.toString().split("\\|");
        tableTilastot.add(ti, rivi[2], rivi[3], rivi[4]);
    }
    
    /**
     * 
     * @param os tietovirta johon tulostetaan
     * @param pelaaja jonka tietoja tulostetaan
     */
    @SuppressWarnings("unused")
    private void tulosta(PrintStream os, Pelaaja pelaaja) {
        os.println("--------------------------------");
        pelaaja.tulosta(os);
        List <Tilasto> tilastot = rekisteri.annaTilastot(pelaaja);
        for(Tilasto t : tilastot) {
           
            t.tulosta(os);
        }
        os.println("--------------------------------");
    }

   
    /** Avaa aloitusikkunan
     * @return true
     */
    public boolean avaa() {
        lueTiedosto();
        asetaKeskiarvot();
        Label [] l = new Label[] {pelaajaDd, pelaajaSa,pelaajaGir, erotusDd,erotusSa,erotusGir, pelaajaSs, erotusSs};
        for(int i = 0; i < l.length; i++) {
            l[i].setText("0");
        }
        ModalController.showModal(GolfrekisteriGUIController.class.getResource("AloitusnayttoGUIView.fxml"), "Rekisteri", null, "");
        return true;
    }

    /** Hakee pelaajan tiedot listaan
     * @param pnro pelaajanumero
     */
    private void hae(int pnro) {
        chooserPelaajat.clear(); 
        String hakuehto="";
        if(hakuKentat.getSelectedObject() != null) {
            hakuehto = hakuKentat.getSelectedObject().toString().toLowerCase();
        }
        int index = 0;
        Collection<Pelaaja> pelaajat;
        int i = 0;
        pelaajat = rekisteri.etsi(hakuehto);
        for(Pelaaja p : pelaajat) {
            if(p.getTunnusNro() == pnro) index = i;
            chooserPelaajat.add(p.getNimi(), p);
            i++;
        }
          chooserPelaajat.setSelectedIndex(index);
    }
    
    
    /** Lisataan rekisteriin pelaajalle tilasto
     */
    public void uusiTilasto() {
        if(pelaajaKohdalla == null) return;
        
        Tilasto til = new Tilasto(pelaajaKohdalla.getTunnusNro());
        til = TilastonlisaysController.kysyTilasto(null, til);
        if(til == null)return;
        til.rekisteroi();
        rekisteri.lisaa(til);
        hae(pelaajaKohdalla.getTunnusNro());
    }
    
    /**Lisataan rekisteriin uusi pelaaja
     */
    private void uusiPelaaja() {
       
       try {
           Pelaaja pelaaja = new Pelaaja();
           
           pelaaja = PelaajanlisäysController.kysyPelaaja(null, pelaaja);
           if(pelaaja == null) return;
           pelaaja.rekisteroi();
          
           rekisteri.lisaa(pelaaja);
           hae(pelaaja.getTunnusNro());
    } catch (SailoException e) {
        Dialogs.showMessageDialog("Ongelmia uuden lisaamisessa " + e.getMessage());
        return;
    }
       
     
        
    }


   /** Asetetaan kaytettava rekisteri
    * @param rekisteri jota kaytetaan
    */
   public void setRekisteri(Rekisteri rekisteri) {
    this.rekisteri  = rekisteri;
   }


         
}
