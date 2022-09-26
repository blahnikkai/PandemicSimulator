
package gui;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
 
/**
 * Handles file saving and loading of simulations 
 * 
 * @author kai
 *
 */
public class FileWork {
 
	/**
	 * Convert JavaFX GUI to a file
	 * 
	 * @param controller 	Controller object to be saved
	 * @param path			TextField containg filepath to write to
	 */
    public static void writeObjectToFile(SocialSimController controller, TextField path) {
 
        try {
        	File save = new File(path.getText());
        	FileWriter writer = new FileWriter(save);
        	String fileString = "";
        	fileString += controller.get_distanceSlider().getValue();
        	fileString += "\n";
        	fileString += controller.get_distanceInfectedSlider().getValue();
        	fileString += "\n";
        	fileString += controller.get_infectionChanceSpinner().getValue();
        	fileString += "\n";
        	fileString += controller.get_deathChanceSpinner().getValue();
        	fileString += "\n";
        	fileString += controller.get_recoverySlider().getValue();
        	fileString += "\n";
        	fileString += controller.get_sizeSlider().getValue();
        	fileString += "\n";
        	fileString += controller.get_multiplierSlider().getValue();
        	fileString += "\n";
        	fileString += controller.get_infectionRadiusSlider().getValue();
        	fileString += "\n";
        	fileString += controller.get_maskEffectivenessSlider().getValue();
        	fileString += "\n";
        	fileString += controller.get_maskPercentageSlider().getValue();
        	fileString += "\n";
        	fileString += controller.get_vaccineEffectivenessSlider().getValue();
        	fileString += "\n";
        	fileString += controller.get_vaccineSpeedSpinner().getValue();
        	fileString += "\n";
        	fileString += controller.get_vaccineDistributionSpinner().getValue();
        	fileString += "\n";
        	fileString += controller.get_totalPopulationSpinner().getValue();
        	fileString += "\n";
        	fileString += controller.get_infectedPopulationSpinner().getValue();
        	fileString += "\n";
        	fileString += controller.get_speedSlider().getValue();
        	fileString += "\n";
        	
        	
        	writer.write(fileString);
        	writer.close();
            System.out.println("The Object  was succesfully written to a file");
            path.clear();
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Load file and change GUI accordingly
     * 
     * @param controller 	Controller object to be changed
     * @param path			Path of file to read from
     */
    public static void readFileToObject(SocialSimController controller, TextField path) {
    	 
        try {
        	Scanner sc = new Scanner(new File(path.getText()));
        	controller.get_distanceSlider().setValue(sc.nextDouble());
        	controller.get_distanceInfectedSlider().setValue(sc.nextDouble());
        	controller.get_infectionChanceSpinner().setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, sc.nextDouble()));
        	controller.get_deathChanceSpinner().setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, sc.nextDouble()));
        	controller.get_recoverySlider().setValue(sc.nextDouble());
        	controller.get_sizeSlider().setValue(sc.nextDouble());
        	
        	controller.get_multiplierSlider().setValue(sc.nextDouble());   	

        	controller.get_infectionRadiusSlider().setValue(sc.nextDouble());
        	
        	controller.get_maskEffectivenessSlider().setValue(sc.nextDouble());
        	
        	controller.get_maskPercentageSlider().setValue(sc.nextDouble());
        	
        	controller.get_vaccineEffectivenessSlider().setValue(sc.nextDouble());
        	
        	controller.get_vaccineSpeedSpinner().setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, sc.nextInt()));
        	
        	controller.get_vaccineDistributionSpinner().setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, sc.nextDouble()));
        	
        	controller.get_totalPopulationSpinner().setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, sc.nextInt()));

        	controller.get_infectedPopulationSpinner().setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, sc.nextInt()));
        	
        	controller.get_speedSlider().setValue(sc.nextDouble());
        	
        	path.clear();
        	
        	controller.setMenu(false, false, false, true, false);
        	controller.getWorld().getChildren().clear();
        	
            System.out.println("The Object was succesfully read from a file");
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


