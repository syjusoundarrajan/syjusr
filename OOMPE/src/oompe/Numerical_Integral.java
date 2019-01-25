package oompe;

import inf.math.*; 
import inf.v3d.view.*;
import inf.v3d.obj.*;

public class Numerical_Integral {

	private double lowerlimit;
	private double upperlimit;
	private UserFunction function;
	private Polyline polyline1=new Polyline();
	private Polyline polyline2=new Polyline();
	private Polyline polyline3=new Polyline();

	
	Numerical_Integral(double lowerlimit, double upperlimit, UserFunction function){
		this.lowerlimit=lowerlimit;
		this.upperlimit=upperlimit;
		this.function=function;
		
		this.polyline1.setColor("green");
		this.polyline1.setLinewidth(5);
		
		this.polyline2.setColor("yellow");
		this.polyline2.setLinewidth(5);
		
		this.polyline3.setColor("blue");
		this.polyline3.setLinewidth(5);

	}
	void plot(Viewer v) {
		FunctionPlotter1D funplot=new FunctionPlotter1D(this.lowerlimit, this.upperlimit, this.function);
		
		funplot.plot(v);
		
		v.addObject3D(this.polyline1);
		v.addObject3D(this.polyline2);
		v.addObject3D(this.polyline3);

	}

	public double y0AsValue(int inter) {
		double deltax=(this.upperlimit-this.lowerlimit)/inter;
		double boxarea=0;

		for(double x=0;x<upperlimit-lowerlimit;x=x+deltax) {
			double y=function.valueAt(x);
			double length=0.1;
			double height=y;
			double area=length*height;
			boxarea+=area;
			this.polyline1.addVertex(x, 0, 0);
			this.polyline1.addVertex(x, y, 0);
			this.polyline1.addVertex(x+deltax, y, 0);
			this.polyline1.addVertex(x+deltax, 0, 0);
			this.polyline1.startNew();
			
		}
		return boxarea;
		
	}
	public double yMeanValue(int inter) {
		double deltax=(this.upperlimit-this.lowerlimit)/inter;
		double boxarea=0;
		for(double x0=0;x0<upperlimit-lowerlimit;x0=x0+deltax) {
			for(double x1=x0+deltax;x1<=x0+deltax;x1=x1+deltax) {
				double y0=function.valueAt(x0);
				double y1=function.valueAt(x1);
				double length=0.1;
				double height=(y0+y1)/2;
				double area=length*height;
				boxarea+=area;
				this.polyline2.addVertex(x0, 0, 0);
				this.polyline2.addVertex(x0, height, 0);
				this.polyline2.addVertex(x1, height, 0);
				this.polyline2.addVertex(x1, 0, 0);
				this.polyline2.startNew();
				
			}
		}
		return boxarea;
	}
	public double linearInterpolation(int inter) {
		double deltax=(this.upperlimit-this.lowerlimit)/inter;
		double boxarea=0;
		for(double x0=0;x0<upperlimit-lowerlimit;x0=x0+deltax) {
			for(double x1=x0+deltax;x1<=x0+deltax;x1=x1+deltax) {
				double y0=function.valueAt(x0);
				double y1=function.valueAt(x1);
				double length=0.1;
				double x=(x0+x1)/1.998;
				double height=(((y1-y0)*(x-x0))/(x1-x0))+y0;
				double area=length*height;
				boxarea+=area;
				this.polyline3.addVertex(x0, 0, 0);
				this.polyline3.addVertex(x0, height, 0);
				this.polyline3.addVertex(x1, height, 0);
				this.polyline3.addVertex(x1, 0, 0);
				this.polyline3.startNew();
				
			}
		
	    }
	    return boxarea;
	
	
     }
	
	
}
