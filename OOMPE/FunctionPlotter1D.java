package plotter;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;
import inf.math.UserFunction;
import inf.v3d.obj.Arrow;
import inf.v3d.obj.Polyline;
import inf.v3d.obj.Tube;
import inf.v3d.view.Viewer;

import java.awt.Color;

/*
 * The function plotter uses the VTK two visualize functions
 */
public class FunctionPlotter1D {

	private double lowerBoundX;
	private double upperBoundX;
	private int stepCount = 500;
	private UserFunction function;
	private boolean useTube = true;
	private boolean inclede0OnXAxis = false;
	private boolean include0OnYAxis = true;

	public FunctionPlotter1D(double lowerBoundX, double upperBoundX, UserFunction function) {
		this.lowerBoundX = lowerBoundX;
		this.upperBoundX = upperBoundX;
		this.function = function;
	}

	public void plot(Viewer viewerObj) {
		//Polyline for drawing the function
		Polyline graph = new Polyline();
		
		//boundary for the window of the plotter
		Polyline boundary = new Polyline();
		
		//The tube which will be shown later in the viewer
		Tube tubeGraph = new Tube(graph);
		
		//initializations
		double steptWidth = (this.upperBoundX - this.lowerBoundX) / (this.stepCount - 1);		//Stepwidth
		double xMinArrow = this.inclede0OnXAxis ? min(0, this.lowerBoundX) : this.lowerBoundX;	//minimum X for the later boundary
		double xMaxArrow = this.inclede0OnXAxis ? max(0, this.upperBoundX) : this.upperBoundX;	//maximum x for the later boundary
		double fMin = this.include0OnYAxis ? 0 : Double.MAX_VALUE;								//min f(x) or y for the later boundary
		double fMax = this.include0OnYAxis ? 0 : Double.MIN_VALUE;								//max f(x) or y for the later boundary

		//Add each point to the graph and define minimum and maximum of f(x) 
		for (int i = 0; i < this.stepCount; i++) 
		{
			double x = this.lowerBoundX + i * steptWidth;	//calculate next step point x
			double fx = this.function.valueAt(x);		//calculate the value f(x) 

			fMin = min(fx, fMin);				//update the minimum f(x)
			fMax = max(fx, fMax);				//update the maximum f(x)
			graph.addVertex(x, fx, 0);
		}

		//Setting up the arrows for the x- and y-axis
		double yArrowPositionX = xMaxArrow < 0 ? xMaxArrow : max(0, xMinArrow);		//If the Function is left of Y Axis put the y-Axis on the right, else 0 or right from it 
		Arrow arrowYAxis = new Arrow(yArrowPositionX, fMin, 0, yArrowPositionX, fMax, 0);	//drawing the y-axis minimum at 0 - not left from 0
		Arrow arrowXAxis = new Arrow(xMinArrow, 0, 0, xMaxArrow, 0, 0);	//Arrow for the x-Axis
		Color c = Color.GRAY;
		arrowYAxis.setColor(c);
		arrowXAxis.setColor(c);

		//Configure the presentation of the graph
		double tubeRadius = sqrt((fMax - fMin) * (this.upperBoundX - this.lowerBoundX));
		arrowYAxis.setRadius(tubeRadius / 250);
		arrowXAxis.setRadius(tubeRadius / 250);
		graph.setColor("red");
		tubeGraph.setColor("red");
		tubeGraph.setRadius(tubeRadius / 400);

		//Generating the outer window for the plot
		boundary.setColor("black");
		boundary.addVertex(xMinArrow, fMin, 0);	//lower left point
		boundary.addVertex(xMaxArrow, fMin, 0);	//lower right point
		boundary.addVertex(xMaxArrow, fMax, 0); //upper right point
		boundary.addVertex(xMinArrow, fMax, 0);	//upper left point
		boundary.addVertex(xMinArrow, fMin, 0);	//lower left point (close the loop)

		//either draw a 2D Graph or tube for the plot
		if (this.useTube) {
			viewerObj.addObject3D(tubeGraph);
		} else {
			viewerObj.addObject3D(graph);
		}
		
		//Adding the boundary and the Axes
		viewerObj.addObject3D(boundary);
		viewerObj.addObject3D(arrowYAxis);
		viewerObj.addObject3D(arrowXAxis);
	}

	/*
	 * Shall 0 be included for X
	 */
	public void setIncludeXO(boolean includeX0) {
		this.inclede0OnXAxis = includeX0;
	}

	/*
	 * Shall 0 be included for Y
	 */
	public void setIncludeYO(boolean includeY0) {
		this.include0OnYAxis = includeY0;
	}

	/*
	 * Set the amount of steps for the plot
	 */
	public void setStepCount(int n) {
		this.stepCount = n;
	}
}

