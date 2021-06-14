package fxGolfrekisteri;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import golfrekisteri.Pelaaja;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/** Controlleri pelaajan lisäämistä varten
 * @author Niko Kynsijarvi
 * @version 14.2.2021
 *
 */
public class PelaajanlisäysController implements ModalControllerInterface<Pelaaja>,Initializable {
    
    @FXML private Button buttonOk;
    @FXML private TextField editNimi;
    @FXML private TextField editIka;
    @FXML private TextField editPituus;
    @FXML private TextField editKansalaisuus;
    @FXML private TextField editRanking;
    @FXML private Label virheLabel;
    
    /**
     * @param url ;
     * @param bundle ;
     */
    @Override
    public void initialize (URL url, ResourceBundle bundle) {
        alusta();
    }
    
    @FXML
    void handleDefaultOK() {
        if(pelaajaKohdalla != null && pelaajaKohdalla.getNimi().trim().equals("")) {
            Dialogs.showMessageDialog("Täytyy antaa nimi");
            return;
        }
        
        if(pelaajaKohdalla.getNimi().trim().indexOf('|') > -1) {
            Dialogs.showMessageDialog("Ei saa sisaltaa | merkkia");
            return;
        }
        ModalController.closeStage(buttonOk);
    }

    @FXML
    void handleDefaultCancel() {
        pelaajaKohdalla = null;
        ModalController.closeStage(buttonOk);
    }


    @Override
    public void setDefault(Pelaaja oletus) {
        pelaajaKohdalla = oletus;
        naytaPelaaja(edits, pelaajaKohdalla);
        
    }

    @Override
    public Pelaaja getResult() {
        return pelaajaKohdalla;
    }
    
    @Override
    public void handleShown() {
        editNimi.requestFocus();
    }
    
    
    //-----------------------------------------------
    private TextField edits[];
    private Pelaaja pelaajaKohdalla;
    
    /** Tyhjentaa teksikentat
     * @param edits tekstikentat
     */
    public static void tyhjenna(TextField [] edits) {
        for(TextField edit:edits) {
            edit.setText("");
           
        }
    }
    
    /** Nayttaa mahdollisen virheen
     * @param virhe virheteksti
     */
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
             virheLabel.setText("");
             virheLabel.getStyleClass().removeAll("virhe");
            return;
        }
         virheLabel.setText(virhe);
         virheLabel.getStyleClass().add("virhe");
     }
    
    /** Tekee tarvittavat alustukset. Laittaa edit kentasta tulevan tapahtuman menemaan kasitteMuutosPelaaja
     * metodiin ja vie kentan parametrina
     */
    protected void alusta() {
        this.edits = new TextField [] {editNimi, editIka, editPituus, editKansalaisuus, editRanking};
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
            naytaVirhe(virhe);
        }else {
            Dialogs.setToolTipText(edit, virhe);
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        }
    }
    
    /** Nayttaa pelaajan tiedot textfieldiin
     * @param edits taulukko jossa tekstikentat
     * @param pelaaja naytettava pelaaja
     */
    public static void naytaPelaaja(TextField[] edits,Pelaaja pelaaja ) {
        if(pelaaja == null) return;
        edits[0].setText(pelaaja.getNimi());
        edits[1].setText(pelaaja.getIka());
        edits[2].setText(pelaaja.getPituus());
        edits[3].setText(pelaaja.getKansalaisuus());
        edits[4].setText(pelaaja.getRanking());
    }
    
    /** Luodaan pelaajan kysymyis dialogi
     * @param modalityStage .
     * @param oletus .
     * @return .
     */
    public static Pelaaja kysyPelaaja(Stage modalityStage, Pelaaja oletus) {
                return ModalController.<Pelaaja, PelaajanlisäysController>showModal(
                            PelaajanlisäysController.class.getResource("PelaajanlisäysGUIView.fxml"),
                            "Rekisteri",
                            modalityStage, oletus, null 
                        );
            }
}
