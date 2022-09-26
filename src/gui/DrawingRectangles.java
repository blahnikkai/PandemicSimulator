package gui;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Person;


/**
 * Handles user drawing of rectangles with a mouse
 * 
 * @author kai
 *
 */
public class DrawingRectangles {
	   
	   private Pane p;
	   private Rectangle drawingRectangle;
	   private double startX, startY;
	   private boolean currentlyDrawing = false;
	  
	   /**
	    * Construct DrawingRectangles object
	    * 
	    * @param p		Pane containg rectangles to be drawn
	    * @param cp		ColorPicker to control rectangle colors
	    * @param draw	RadioMenuItem toggling draw mode, don't draw if not selected
	    */
	   public DrawingRectangles(Pane p, ColorPicker cp, RadioMenuItem draw) {
		   this.p = p;
		   drawingRectangle = null;
		   currentlyDrawing = false;
		   
		   p.setOnMousePressed(e -> {
			   if(currentlyDrawing == false && draw.isSelected()) {
				   startX = e.getX() ;
		           startY = e.getY() ;
	
		           drawingRectangle = new Rectangle() ;
	
		           drawingRectangle.setFill(Color.TRANSPARENT);
		           drawingRectangle.setStroke(Color.BLACK);
		           drawingRectangle.getStrokeDashArray().addAll(4d, 6d);
		           p.getChildren().add(drawingRectangle);
		   
		           currentlyDrawing = true;
		       }
		   });
		   p.setOnMouseDragged(e -> {
			   if(currentlyDrawing == true && draw.isSelected()) {
				   double endX = e.getX() ;
				   double endY = e.getY() ;
				   adjust_rectangle_properties(startX, startY, endX, endY, drawingRectangle);
			   }
		   });

		   p.setOnMouseReleased(e -> {
		         if(currentlyDrawing == true && draw.isSelected()) {
		            Rectangle addRectangle = new Rectangle(drawingRectangle.getX(), drawingRectangle.getY(), drawingRectangle.getWidth(), drawingRectangle.getHeight());
		            addRectangle.setFill(cp.getValue());
		            addRectangle.setStroke(drawingRectangle.getStroke());
		            addRectangle.setOnMouseClicked(event -> {
		            	if(event.getButton() == MouseButton.SECONDARY && draw.isSelected()) {
		            		p.getChildren().remove(addRectangle);
		            		Person._walls.remove(addRectangle);
		            	}
		            });
		            Person._walls.add(addRectangle);
		            p.getChildren().add(addRectangle);
		            p.getChildren().remove(drawingRectangle);
		            
		            drawingRectangle = null;
		            currentlyDrawing = false;
		         }
		      });			   
	   }
	   
	   /**
	    * Change height and width of rectangle given mouse movement
	    * 
	    * @param startX x-coordinate of mouse origin
	    * @param startY y-coordinate of mouse origin
	    * @param endX	x-coordinate of mouse finish
	    * @param endY	y-coordinate of mouse finish
	    * @param rect	Rectangle to be modified
	    */
	   private void adjust_rectangle_properties(double startX, double startY, double endX, double endY, Rectangle rect) {
		   rect.setX(startX) ;
		   rect.setY(startY) ;
		   
		   double right = Math.min(endX, p.getWidth());
		   double bottom = Math.min(endY, p.getHeight());
		   
		   right = Math.max(right, 0);
		   bottom = Math.max(bottom, 0);
		   
		   rect.setWidth(right - startX);
		   rect.setHeight(bottom - startY) ;
		   
		   if(rect.getWidth() < 0) {
			   rect.setWidth( - rect.getWidth() ) ;
			   rect.setX(rect.getX() - rect.getWidth());
		   }
		   if(rect.getHeight() < 0) {
			   rect.setHeight( - rect.getHeight() ) ;
			   rect.setY(rect.getY() - rect.getHeight()) ;
		   }
		}
}
