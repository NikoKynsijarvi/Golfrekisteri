package fxGolfrekisteri;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import golfrekisteri.Tilasto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/** Controller tilaston lisäämistä varten
 * @author Niko Kynsijarvi
 * @version 11.3.2021
 *
 */
public class TilastonlisaysController implements ModalControllerInterface<Tilasto>,Initializable {
    
  
    @FXML private TextField editYksikko;
    @FXML private TextField editArvo;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private Button buttonCancel;
    
    private Tilasto palautettava;
    
    @FXML
    void handleCancel() {
        ModalController.closeStage(buttonCancel);
    }

    @FXML
    void handleOk() {
        palautettava = tilastoKohdalla;
        ModalController.closeStage(buttonCancel);
    }
    

//---------------------------------------------------
    private TextField edits[];
    private Tilasto tilastoKohdalla;
    

    
    @Override
    public void handleShown() {
        editArvo.requestFocus();
        
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       alusta();
        
    }

    @Override
    public Tilasto getResult() {
        // TODO Auto-generated method stub
        return palautettava;
    }
    
    /** Tekee tarvittavat alustukset. Laittaa edit kentasta tulevan tapahtuman menemaan kasitteMuutosTilastoon
     * metodiin ja vie kentan parametrina
     */
    protected void alusta() {
        cbKentat.clear();
        cbKentat.add("Driving distance");
        cbKentat.add("Sand save");
        cbKentat.add("Average score");
        cbKentat.add("Greens in regulation");
        this.edits = new TextField [] {editYksikko, editArvo};
        int i = 0;
        for(TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased(e -> kasitteleMuutosTilastoon(k, (TextField)(e.getSource())));
        }
    }
    
    /** Kasitellaan muutos tilastoon ja taytetaan automaattisesti tilaston yksikko
     * @param k teksikentan indeksi
     * @param edit kasiteltava tekstikentta
     */
    private void kasitteleMuutosTilastoon(int k, TextField edit) {
        if(tilastoKohdalla == null) return;
        String s = edit.getText();
        String st = cbKentat.getSelectedObject().toString(); 
        String virhe = null;
        tilastoKohdalla.setNimi(st);
        if(tilastoKohdalla.getNimi() == "Driving distance") {
            editYksikko.setText("ft");
            tilastoKohdalla.setYksikko("ft");
            tilastoKohdalla.setArvo(s);
        }
        if(tilastoKohdalla.getNimi() == "Average score") {
            editYksikko.setText("shots");
            tilastoKohdalla.setYksikko("shots");
            tilastoKohdalla.setArvo(s);
        }
        if(tilastoKohdalla.getNimi() == "Greens in regulation") {
            editYksikko.setText("%");
            tilastoKohdalla.setYksikko("%");
            tilastoKohdalla.setArvo(s);
        }
        if(tilastoKohdalla.getNimi() == "Sand save") {
            editYksikko.setText("%");
            tilastoKohdalla.setYksikko("%");
            tilastoKohdalla.setArvo(s);
        }else {
            switch(k) {
            case 1 : virhe = tilastoKohdalla.setYksikko(s); break;
            case 2 : virhe = tilastoKohdalla.setArvo(s); break;
            default:
        }   
        }
       
        if(virhe == null) {
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
          
        }else {
            Dialogs.setToolTipText(edit, virhe);
            edit.getStyleClass().removeAll("virhe");
          
        }
    }


    @Override
    public void setDefault(Tilasto oletus) {
        tilastoKohdalla = oletus;
       
    }
    
    /** Luodaan tilaston kysymys dialogi
     * @param modalityStage .
     * @param oletus .
     * @return .
     */
    public static Tilasto kysyTilasto(Stage modalityStage, Tilasto oletus) {
        return ModalController.<Tilasto, TilastonlisaysController>showModal(
                    TilastonlisaysController.class.getResource("TilastonlisaysGUIView.fxml"),
                    "Rekisteri",
                    modalityStage, oletus, null 
                );
    }


  
    
}
