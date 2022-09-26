package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * Starts social distancing simulation
 * 
 * @author kai
 *
 */
public class SocialSimApp extends Application {
	  @Override
	  public void start(Stage primaryStage) {
	    try {
	      FXMLLoader loader = new FXMLLoader();
	      BorderPane root =
	        (BorderPane)loader.load(getClass().getResource("SocialSimGui.fxml").openStream());
	      primaryStage.setScene(new Scene(root));
	      primaryStage.show();
	      root.requestFocus();
	    } catch (Exception e) {
	      e.printStackTrace();
	      System.exit(1);
	    }
	  }
	  
	  public static void main(String[] args) {
	    launch(args);
	  }
}
