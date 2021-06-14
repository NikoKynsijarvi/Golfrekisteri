package fxGolfrekisteri;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author Niko Kynsijarvi
 * @version 14.2.2021
 *
 */
public class PelaajanpoistoController implements ModalControllerInterface <String> {
    
    @FXML private Button buttonCancel;
    
    @FXML
    void handleDefaultOK() {
        
       ModalController.closeStage(buttonCancel);
    }

    @FXML
    void handleDefaultCancel() {
        
        ModalController.closeStage(buttonCancel);
    }

    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(String arg0) {
        // TODO Auto-generated method stub
        
    }

    
}