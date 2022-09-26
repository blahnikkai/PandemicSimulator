package gui;

import java.util.ArrayList;
import java.util.EnumMap;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import model.Person;
import model.Simulation;
import model.State;


/**
 * Handles bar, pie, and line chart in JavaFX given infection data
 * 
 * @author kai
 *
 */
public class Charts {

	
	private BarChart<String, Number> _bc;
	private NumberAxis _bcy;
	
	private PieChart _pc;
	
	private StackedAreaChart<Number, Number> _sac;
	private NumberAxis _sacx;
	private EnumMap<State,XYChart.Series<Number, Number>> _sacData;

	
	private Simulation _sim;
	
	private MovementTimer _clock;
	
	private VBox _infoBox;
	private ArrayList<Label> _infoLabels;
	
	/**
	 * Construct Charts object
	 * 
	 * @param bc		JavaFX BarChart
	 * @param pc		JavaFX PieChart
	 * @param sac		JavaFX StackedAreaChart
	 * @param bcy		y-axis for bar chart
	 * @param sacx		x-axis for stacked area chart
	 * @param sim		Simulation object
	 * @param clock		MoventTimer
	 * @param infoBox	JavaFX VBox containing all charts
	 */
	public Charts(BarChart<String, Number> bc, PieChart pc, StackedAreaChart<Number, Number> sac, NumberAxis bcy, NumberAxis sacx, Simulation sim, MovementTimer clock, VBox infoBox) {
		_bc = bc;
		_pc = pc;
		_sac = sac;
		_bcy = bcy;
		_sacx = sacx;
		_sim = sim;
		_clock = clock;
		
		_infoBox = infoBox;
		_infoLabels = new ArrayList<Label>();
		
		_sacData = new EnumMap<State, XYChart.Series<Number, Number>>(State.class);
		
		initInfoBox();
	}
	
	private void initInfoBox() {
		for(State s: State.values()) {
			HBox statBox = new HBox();
			statBox.setAlignment(Pos.CENTER_LEFT);
			statBox.setSpacing(5);
			statBox.setTranslateX(20);
			
			Rectangle statRect = new Rectangle(10, 10);
			statRect.setFill(s.getColor());
			
			Label statLabel = new Label(s.getName() + ": ");
			statLabel.setTextFill(Color.WHITE);
			statLabel.setFont(Font.font("Verdana", 12));
			
			statBox.getChildren().addAll(statRect, statLabel);
			_infoBox.getChildren().add(s.getInd(), statBox);
			_infoLabels.add(statLabel);
		}
	}
	
	/**
	 * Initialize charts
	 */
	public void initCharts() {
		_sacData.clear();
		_sac.getData().clear();
		for(State s: State.values()) {
			XYChart.Series<Number, Number> pop = new XYChart.Series<Number, Number>();
			pop.setName(s.getName());
			_sacData.put(s, pop);
			_sac.getData().add(pop);	
		}
	}
	
	/**
	 * Update charts
	 */
	public void drawCharts() {
		EnumMap<State, Integer> currentPop = new EnumMap<State, Integer>(State.class);
		_bc.getData().clear();
		_pc.getData().clear();
		
		for(Person p: _sim.getPeople()) {
			if(!currentPop.containsKey(p.getState())) {
				currentPop.put(p.getState(), 0);
			}
			currentPop.put(p.getState(), 1 + currentPop.get(p.getState()));
		}
		
		int count = 0;
		for(State s: State.values()) {
			XYChart.Series<String, Number> barCategory = new Series<String, Number>();
			
			
			if(!currentPop.containsKey(s)) {
				currentPop.put(s, 0);
			}
			
			
			_pc.getData().add(new PieChart.Data(s.getName(), currentPop.get(s)));
			barCategory.setName(s.getName());
			barCategory.getData().add(new Data<String, Number>(s.getName(), currentPop.get(s)));
			
			
			_bc.getData().add(barCategory);
			
			_sacData.get(s).getData().add(new XYChart.Data(_clock.getTicks(), currentPop.get(s)));
			
			_infoLabels.get(count).setText(s.getName() + ": " + currentPop.get(s));
			count++;
		}
		
		
		count = 0;
		for(Node n: _bc.lookupAll(".chart-bar")) {
			 n.setStyle("-fx-background-color: " + State.getState(count).getStrColor() + "; -fx-border-color: Black;");
			 count++;
		}
		count = 0;
		for(Node n: _bc.lookupAll(".bar-legend-symbol.default-color0")) {
			 n.setStyle("-fx-background-color: " + State.getState(count).getStrColor() + ";");
			 count++;
			 
		}
		
		count = 0;
		for(Node n: _pc.lookupAll(".bar-legend-symbol.default-color0")) {
			 n.setStyle("-fx-background-color: " + State.getState(count).getStrColor() + ";");
			 count++;
			 
		}
		count = 0;
		for (PieChart.Data data : _pc.getData()) {
		    data.getNode().setStyle(
		      "-fx-pie-color: " + State.getState(count).getStrColor() + ";"
		    );
		    count++;
		}
		count = 0;
		for(Node n: _sac.lookupAll(".chart-series-area-fill")) {
			 n.setStyle("-fx-fill: " + State.getState(count).getStrColor() + "; -fx-opacity: .5;");
			 count++;
			 
		}
		//_sac.lookup(".series0.chart-series-area-fill")
		//.setStyle("-fx-fill: linear-gradient(to top, #0984df, #00d8ff 20%, #fff000 60%, #ff0000);");
		
		
		_bc.setAnimated(false);
		_pc.setAnimated(false);
		
		_bcy.setAutoRanging(false);
		_bcy.setUpperBound((int)(_sim.getPeople().size() * 1.1));
		_bcy.setTickUnit(20);
		
		
		
		if(_clock.getTicks() >= 1000) {
			_sacx.setAutoRanging(true);
		}
		else {
			_sacx.setAutoRanging(false);
			_sacx.setUpperBound(1000);
		}
	}
}
