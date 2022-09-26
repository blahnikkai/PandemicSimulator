package gui;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Heading;
import model.Person;
import model.Simulation;


/**
 * Converts user inputs to simulation settings
 * 
 * @author kai
 *
 */
public class SocialSimController implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final String NO_BACKGROUND = "-fx-border-color: Black; -fx-border-radius: 10; -fx-background-radius: 10";
	
	public static final String LIGHT_GRAY_BACKGROUND = "-fx-background-color: LightGray";
	public static final String CREAM_BACKGROUND = "-fx-background-color: MintCream";
	
	@FXML
	private Pane _world;
	public Pane getWorld() {return _world;}
	
	private Simulation _sim;
	
	private MovementTimer _clock;
	
	private Charts _charts;
	
	@FXML
	private Button _startButton;
	public Slider get_recoverySlider() {
		return _recoverySlider;
	}
	public Slider get_distanceSlider() {
		return _distanceSlider;
	}
	public Slider get_distanceInfectedSlider() {
		return _distanceInfectedSlider;
	}

	public Slider get_multiplierSlider() {
		return _multiplierSlider;
	}

	public Slider get_infectionRadiusSlider() {
		return _infectionRadiusSlider;
	}

	public Slider get_deathChanceSlider() {
		return _deathChanceSlider;
	}

	public Slider get_maskEffectivenessSlider() {
		return _maskEffectivenessSlider;
	}

	public Slider get_maskPercentageSlider() {
		return _maskPercentageSlider;
	}

	public Slider get_vaccineEffectivenessSlider() {
		return _vaccineEffectivenessSlider;
	}
	public Spinner<Double> get_infectionChanceSpinner() {
		return _infectionChanceSpinner;
	}
	public Spinner<Double> get_deathChanceSpinner() {
		return _deathChanceSpinner;
	}
	public Spinner<Integer> get_infectedPopulationSpinner() {
		return _infectedPopulationSpinner;
	}
	public Spinner<Integer> get_totalPopulationSpinner() {
		return _totalPopulationSpinner;
	}
	public Spinner<Integer> get_vaccineSpeedSpinner() {
		return _vaccineSpeedSpinner;
	}
	public Spinner<Double> get_vaccineDistributionSpinner() {
		return _vaccineDistributionSpinner;
	}
	public Slider get_speedSlider() {
		return _speedSlider;
	}
	
	public RadioMenuItem get_draw() {
		return _draw;
	}
	
	public RadioMenuItem get_simulate() {
		return _simulate;
	}
	
	public ColorPicker get_colorPicker() {
		return _colorPicker;
	}
	public Slider get_sizeSlider() {
		return _sizeSlider;
	}


	@FXML
	private Button _stopButton;
	@FXML
	private Button _stepButton;
	@FXML
	private Button _resetButton;
	@FXML
	private Button _clearButton;
	
	@FXML
	private Button _quitButton;
	
	@FXML
	private Slider _sizeSlider;
	@FXML
	private Slider _recoverySlider;
	@FXML
	private Slider _distanceSlider;
	@FXML
	private Slider _distanceInfectedSlider;
	@FXML
	private Slider _multiplierSlider;
	@FXML
	private Slider _infectionRadiusSlider;
	@FXML
	private Slider _infectionChanceSlider;
	@FXML
	private Slider _deathChanceSlider;
	@FXML
	private Slider _maskEffectivenessSlider;
	@FXML
	private Slider _maskPercentageSlider;
	@FXML
	private Slider _vaccineEffectivenessSlider;
	@FXML
	private Slider _speedSlider;
	
	@FXML
	private Spinner<Double> _infectionChanceSpinner;
	@FXML
	private Spinner<Double> _deathChanceSpinner;	
	@FXML
	private Spinner<Integer> _infectedPopulationSpinner;
	@FXML
	private Spinner<Integer> _totalPopulationSpinner;
	@FXML
	private Spinner<Integer> _vaccineSpeedSpinner;
	@FXML
	private Spinner<Double> _vaccineDistributionSpinner;
	
	@FXML
	private Label _tickLabel;
	@FXML
	private StackedAreaChart<Number, Number> _stackedAreaChart;
	@FXML
	private BarChart<String, Number> _barChart;
	@FXML
	private PieChart _pieChart;
	
	@FXML
	private NumberAxis _barChartY;
	@FXML
	private NumberAxis _stackedAreaChartX;
	@FXML
	private VBox _chartBox;
	@FXML
	private RadioMenuItem _draw;
	@FXML
	private RadioMenuItem _simulate;
	@FXML
	private RadioMenuItem _play;
	@FXML
	private RadioMenuItem _file;
	@FXML
	private RadioMenuItem _help;
	@FXML
	private VBox _simulateControls1;
	@FXML
	private VBox _simulateControls2;
	@FXML
	private HBox _simulateControls3;
	@FXML
	private VBox _drawControls;
	@FXML
	private VBox _fileControls;
	@FXML
	private TextArea _helpTextArea;
	@FXML
	private ProgressBar _vaccineProgressBar;
	@FXML
	private MenuButton _mode;
	@FXML
	private ColorPicker _colorPicker;
	@FXML
	private Button _saveButton;
	@FXML
	private Button _loadButton;
	@FXML
	private TextField _fileTextField;
	
	/**
	 * Initialize JavaFX elements like spinners and pane
	 */
	@FXML
	private void initialize() {
		_clock = new MovementTimer(this, _tickLabel);
		_sim = new Simulation(new Pane(), 100, 1);
		_sim.draw();
		
		_charts = new Charts(_barChart, _pieChart, _stackedAreaChart, _barChartY, _stackedAreaChartX,_sim, _clock, _chartBox);
		_charts.initCharts();
		_charts.drawCharts();

		_infectionChanceSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 25));
		_deathChanceSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 10));
		
		_infectedPopulationSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, 1));
		_totalPopulationSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, 100));
		
		_vaccineSpeedSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, 500));
		_vaccineDistributionSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 25));

		_deathChanceSpinner.valueProperty().addListener(e -> {setDeathChance();});
		_infectionChanceSpinner.valueProperty().addListener(e -> {setInfectionChance();});
		
		_infectedPopulationSpinner.valueProperty().addListener(e -> {setInfectedPopulation();});
		_totalPopulationSpinner.valueProperty().addListener(e -> {setTotalPopulation();});
		
		_vaccineSpeedSpinner.valueProperty().addListener(e -> {setVaccineSpeed();});
		_vaccineDistributionSpinner.valueProperty().addListener(e -> {setVaccineDistribution();});
		
		new DrawingRectangles(_world, _colorPicker, _draw);
	
		_colorPicker.setValue(Color.BLACK);
		
		_helpTextArea.setText(
				"Use the menu at the top center to switch between modes"
				+ "\n\nSimulation: "
				+ "\nUse the sliders and spinners to control the simulation specifications"
				+ "\nAll percent chances are calculated per tick"
				+ "\nFor example, mortality rate is much higher than death chance per tick"
				+ "\nQuarantine distance is not distance to other people, just a minimum"
				+ "\ndistance from starting point"
				+ "\n\nDrawing: "
				+ "\nLeft click and drag to draw"
				+ "\nRight click to delete"
				+ "\nPress the clear button to clear"
				+ "\n\nPlaying: "
				+ "\nYou are the flashing circle"
				+ "\nMove with WASD, try not to get infected"
				+ "\nIncrease the speed for more of a challenge"
				+ "\n\nSave/Load"
				+ "\nTo save or load, type the full file path, file name, and txt file extension"
				+ "\nWindows Example: C:\\\\Users\\\\<YourName>\\\\Desktop\\savedata1.txt"
				+ "\n(two backslashes are needed for escapement in java)"
				+ "\nMac Example: /Users/<YourName>/Desktop/savedata1.txt"
		);
		
	}
	
	
	private boolean wPressed;
	private boolean aPressed;
	private boolean sPressed;
	private boolean dPressed;
	
	/**
	 * When key pressed, set corresponding key pressed boolean to true
	 * 
	 * @param e KeyEvent of key press
	 */
	@FXML
	public void keyPressed(KeyEvent e) {
		KeyCode keyCode = e.getCode();
		System.out.println("pressed");
		if(keyCode == KeyCode.W) {
			wPressed = true;
		}
		if(keyCode == KeyCode.A) {
			aPressed = true;
		}
		if(keyCode == KeyCode.S) {
			sPressed = true;
		}
		if(keyCode == KeyCode.D) {
			dPressed = true;
		}
		move();
	}
	/**
	 * When key released, set corresponding key pressed boolean to false
	 * 
	 * @param e KeyEvent of key release
	 */
	@FXML
	public void keyReleased(KeyEvent e) {
		KeyCode keyCode = e.getCode();
		System.out.println("released");
		if(keyCode == KeyCode.W) {
			wPressed = false;
		}
		if(keyCode == KeyCode.A) {
			aPressed = false;
		}
		if(keyCode == KeyCode.S) {
			sPressed = false;
		}
		if(keyCode == KeyCode.D) {
			dPressed = false;
		}
		move();
	}
	/**
	 * Take WASD keyboard input and translate to player movement
	 */
	private void move() {
		if(dPressed && wPressed)
			_sim.movePlayer(45);
		else if(wPressed && aPressed)
			_sim.movePlayer(135);
		else if(aPressed && sPressed)
			_sim.movePlayer(225);
		else if(sPressed && dPressed)
			_sim.movePlayer(315);
		else if(wPressed)
			_sim.movePlayer(90);
		else if(aPressed)
			_sim.movePlayer(180);
		else if(sPressed)
			_sim.movePlayer(270);
		else if(dPressed)
			_sim.movePlayer(360);
		else
			_sim.stopPlayer();
	}
	
	@FXML
	public void quitPressed() {
		_quitButton.setStyle(NO_BACKGROUND + "; " + LIGHT_GRAY_BACKGROUND);
	}
	@FXML
	public void quit() {
		_quitButton.setStyle(NO_BACKGROUND + "; " + CREAM_BACKGROUND);
		System.exit(0);
	}
	
	@FXML
	public void resetPressed() {
		_resetButton.setStyle(NO_BACKGROUND + "; " + LIGHT_GRAY_BACKGROUND);
	}

	/**
	 * Reset simulation, put GUI selection into effect
	 */
	@FXML
	public void reset() {
		_mode.setDisable(false);
		_world.getChildren().clear();
		
		setMaskPercentage();
		
		_resetButton.setStyle(NO_BACKGROUND + "; " + CREAM_BACKGROUND);
		_simulateControls2.setVisible(true);
		_chartBox.setVisible(false);
		
		_clock.resetTicks();
		
		
		_sim.changePop(_world, _play.isSelected());
		
		setSize();
		setRecovery();
		setDistance();
		setInfectedDistance();
		setMultiplier();
		setInfectionRadius();
		setInfectionChance();
		setDeathChance();
		setMaskEffectiveness();
		setMaskPercentage();
		setVaccineSpeed();
		setVaccineEffectiveness();
		setVaccineDistribution();
		vaccineUpdate();
		setSpeed();
		
		
		
		
		_sim.drawWalls();
		
		_charts.initCharts();
		_charts.drawCharts();
		
		stop();
	}
	/**
	 * Change button styles when pressed and released to respond to user input, act as feedback
	 */
	@FXML
	public void loadPressed() {
		_loadButton.setStyle(NO_BACKGROUND + "; " + LIGHT_GRAY_BACKGROUND);
	}
	@FXML
	public void load() {
		_loadButton.setStyle(NO_BACKGROUND + "; " + CREAM_BACKGROUND);
		FileWork.readFileToObject(this, _fileTextField);
		
	}
	@FXML
	public void savePressed() {
		_saveButton.setStyle(NO_BACKGROUND + "; " + LIGHT_GRAY_BACKGROUND);
	}
	@FXML
	public void save() {
		_saveButton.setStyle(NO_BACKGROUND + "; " + CREAM_BACKGROUND);
		FileWork.writeObjectToFile(this, _fileTextField);
	}
	@FXML
	public void stepPressed() {
		_stepButton.setStyle(NO_BACKGROUND + "; " + LIGHT_GRAY_BACKGROUND);
	}
	@FXML
	public void step() {
		_stepButton.setStyle(NO_BACKGROUND + "; " + CREAM_BACKGROUND);
		_sim.fullStep();
		_clock.tick();
		
		_charts.drawCharts();
		
		vaccineUpdate();
		_tickLabel.setText("Ticks: " + _clock.getTicks());
	}
	
	@FXML
	public void startPressed() {
		_startButton.setStyle(NO_BACKGROUND + "; " + LIGHT_GRAY_BACKGROUND);
	}
	
	/**
	 * Start simulation when start pressed
	 */
	@FXML
	public void start() {
		
		_startButton.setStyle(NO_BACKGROUND + "; " + CREAM_BACKGROUND);
		_clock.start();
		_sim.setPlayerFlash(false);
		
		_mode.setDisable(true);
		_simulateControls2.setVisible(false);
		_chartBox.setVisible(true);
		disableButtons(true, false, true);
	}
	
	@FXML
	public void stopPressed() {
		_stopButton.setStyle(NO_BACKGROUND + "; " + LIGHT_GRAY_BACKGROUND);
	}
	/**
	 * Pause simulation when stop pressed
	 */
	@FXML
	public void stop() {
		_stopButton.setStyle(NO_BACKGROUND + "; " + CREAM_BACKGROUND);
		_clock.stop();
		_sim.setPlayerFlash(true);
		disableButtons(false, true, false);
	}
	@FXML
	public void clearPressed() {
		_clearButton.setStyle(NO_BACKGROUND + "; " + LIGHT_GRAY_BACKGROUND);
	}
	/**
	 * Clear all walls from pane and list of walls
	 */
	@FXML
	public void clear() {
		_clearButton.setStyle(NO_BACKGROUND + "; " + CREAM_BACKGROUND);
		Person._walls.clear();
		_world.getChildren().clear();
	}
	
	/**
	 * Setters take values of JavaFX elements and set simulation parameters accordingly
	 */
	@FXML
	public void setSize() {
		Person.setRadius(_sizeSlider.getValue());
		_infectionRadiusSlider.setValue(Person.getInfectionRadius());
		_sim.draw();
		_sim.update();
	}
	
	@FXML
	public void setSizeReleased() {
		Person.setRadius(_sizeSlider.getValue());
		_infectionRadiusSlider.setValue(Person.getInfectionRadius());
		reset();
		_sim.draw();
	}
	
	
	
	@FXML
	public void setRecovery() {
		Person.healTime = (int)_recoverySlider.getValue() * 50;
	}
	
	@FXML
	public void setMaskEffectiveness() {
		Person.maskFactor = 1.0 / _maskEffectivenessSlider.getValue();
		_sim.update();
		_sim.draw();
	}
	@FXML
	public void setMaskPercentage() {
		Person.maskPercent = _maskPercentageSlider.getValue() / 100.0;
	}
	@FXML
	public void setMaskPercentageReleased() {
		Person.maskPercent = _maskPercentageSlider.getValue() / 100.0;
		reset();
	}
	@FXML
	public void setDistance() {
		Person.universalDistance = (int)_distanceSlider.getValue();
		System.out.println(Person.universalDistance);
		_sim.update();
		_sim.resetOrigins();
	}
	@FXML
	public void setInfectedDistance() {
		Person.infectedDistance = (int)_distanceInfectedSlider.getValue();
		System.out.println(Person.infectedDistance);
		_sim.update();
		_sim.resetOrigins();
	}
	@FXML
	public void setMultiplier() {
		Person.setMultiplier(_multiplierSlider.getValue());
		_infectionRadiusSlider.setValue(Person.getInfectionRadius());
		_sim.update();
		_sim.draw();
	}
	
	@FXML
	public void setInfectionRadius() {
		if(_infectionRadiusSlider.getValue() < _multiplierSlider.getMin() * Person.getRadius()) {
			_infectionRadiusSlider.setValue(Person.getMultiplier() * Person.getRadius());
			return;
		}
		if (_infectionRadiusSlider.getValue() > _multiplierSlider.getMax() * Person.getRadius()) {
			_infectionRadiusSlider.setValue(Person.getMultiplier() * Person.getRadius());
			return;
		}
		else {
		
		
			Person.setInfectionRadius(_infectionRadiusSlider.getValue());
			_multiplierSlider.setValue(Person.getMultiplier());
			_sim.update();
			_sim.draw();
		}
	}
	@FXML
	public void setVaccineEffectiveness() {
		Person.vaccineFactor = 1.0 / _vaccineEffectivenessSlider.getValue();
	}
	
	@FXML
	private void setInfectionChance() {
		try {
			_sim.setInfectionChance(_infectionChanceSpinner.getValue() / 100.0);
		}
		catch(Exception m) {
			m.printStackTrace();
		}	
		
	}
	@FXML
	private void setDeathChance() {
		try {
			_sim.setDeathChance(_deathChanceSpinner.getValue() / (100.0 * 100.0));
		}
		catch(Exception m) {
			m.printStackTrace();
		}	
	}
	
	@FXML
	private void setTotalPopulation() {
		if(_infectedPopulationSpinner.getValue() > _totalPopulationSpinner.getValue()) {
			_infectedPopulationSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, _totalPopulationSpinner.getValue().intValue()));
		}
		if(_totalPopulationSpinner.getValue() > 500 || _totalPopulationSpinner.getValue() < 0) {
			return;
		}
		_sim.setTotalPopCount(_totalPopulationSpinner.getValue());
		reset();
	}
	
	@FXML
	private void setInfectedPopulation() {
		if(_infectedPopulationSpinner.getValue() > _totalPopulationSpinner.getValue()) {
			_totalPopulationSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, _infectedPopulationSpinner.getValue().intValue()));
		}
		if(_infectedPopulationSpinner.getValue() > 500 || _infectedPopulationSpinner.getValue() < 0) {
			return;
		}
		_sim.setInfectedPopCount(_infectedPopulationSpinner.getValue());
		reset();
	}
	@FXML
	public void setSpeed() {
		Heading.SPEED = _speedSlider.getValue();
	}
	
	private void setVaccineSpeed() {
		try {
			Person.vaccineSpeed = _vaccineSpeedSpinner.getValue();
		}
		catch(Exception m) {
			m.printStackTrace();
		}	
	}
	private void setVaccineDistribution() {
		try {
			_sim.setVaccinationChance(_vaccineDistributionSpinner.getValue() / 100.0);
		}
		catch(Exception m) {
			m.printStackTrace();
		}	
	}
	
	/**
	 * Once enought time has passed, start vaccinating people
	 */
	private void vaccineUpdate() {
		_vaccineProgressBar.setProgress(_clock.getTicks()/Person.vaccineSpeed);
		
		if(_clock.getTicks() >= Person.vaccineSpeed) {
			_sim.vaccinate();
		}
	}
	
	public void disableButtons(boolean start, boolean stop, boolean step) {
		_startButton.setDisable(start);
		_stopButton.setDisable(stop);
		_stepButton.setDisable(step);
	}
	
	@FXML
	public void selectSimulate() {
		setMenu(true, false, false, false, false);
		reset();
	}
	
	@FXML
	public void selectDraw() {
		setMenu(false, true, false, false, false);
		_world.getChildren().clear();
		_sim.drawWalls();
	}
	@FXML
	public void selectPlay() {
		setMenu(false, false, true, false, false);
		reset();
	}
	@FXML
	public void selectFile() {
		setMenu(false, false, false, true, false);
		_world.getChildren().clear();
	}
	@FXML
	public void selectHelp() {
		_world.getChildren().clear();
		setMenu(false, false, false, false, true);
	}
	
	/**
	 * Disable buttons and other GUI elements depending on state of simulation
	 * @param simulate  simulation mode selected
	 * @param draw		draw mode selected
	 * @param play		play mode selected
	 * @param file		file mode selected
	 * @param help		help mode selected
	 */
	public void setMenu(boolean simulate, boolean draw, boolean play, boolean file, boolean help) {
		_simulate.setSelected(simulate);
		_simulate.setDisable(simulate);
		_simulateControls1.setVisible(simulate || play);
		_simulateControls2.setVisible(simulate || play);
		_simulateControls3.setVisible(simulate || play);
		
		_draw.setSelected(draw);
		_draw.setDisable(draw);
		_drawControls.setVisible(draw);
		
		_play.setSelected(play);
		_play.setDisable(play);
		
		_file.setSelected(file);
		_file.setDisable(file);
		_fileControls.setVisible(file);
		
		_help.setSelected(help);
		if(help && !_world.getChildren().contains(_helpTextArea)) {
			_world.getChildren().add(_helpTextArea);
			System.out.print("showing help text area");
		}
		_helpTextArea.setVisible(help);
	}	
}
