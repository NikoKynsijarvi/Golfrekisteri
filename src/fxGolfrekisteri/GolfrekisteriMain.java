package fxGolfrekisteri;
	
import golfrekisteri.Rekisteri;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


/**
 * @author Niko Kynsijarvi
 * @version 16.1.2021
 *
 */
public class GolfrekisteriMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("GolfrekisteriGUIView.fxml"));
			final FXMLLoader ldr = new FXMLLoader(getClass().getResource("GolfrekisteriGUIView.fxml"));
			final BorderPane root = (BorderPane)ldr.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("golfrekisteri.css").toExternalForm());
			final GolfrekisteriGUIController rekisteriCtrl = (GolfrekisteriGUIController)ldr.getController();
			primaryStage.setScene(scene);
			primaryStage.setTitle("Golfrekisteri");
			
			Rekisteri rekisteri = new Rekisteri();
			rekisteriCtrl.setRekisteri(rekisteri);
			
			 primaryStage.setOnCloseRequest((event) -> {
		            
		            if ( !rekisteriCtrl.voikoSulkea() ) event.consume();
		        });
			
			
			primaryStage.show();
			Application.Parameters params = getParameters();
			if(params.getRaw().size() > 0)
			    rekisteriCtrl.lueTiedosto();
			else
			    if(!rekisteriCtrl.avaa() ) Platform.exit();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args ei
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
