package oompe;
import inf.v3d.obj.*;
import inf.v3d.view.*;


import inf.math.*;

public class TestFunction {

	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		Viewer v=new Viewer();
//		UserFunction uf=new UserFunction("1-cos(8*(22/7)*x)+0.75(1+cos((11.948*(22/7)*x)+(0.2*(22/7)))", "x");
//		UserFunction uf=new UserFunction("2*pow(sin(4*3.14*x,2)+1.5*(pow(cos((5.974*")
        UserFunction uf=new UserFunction("2*pow(sin(2*(PI)*2*t),2)+1.5*pow(cos(2*(PI)*2.987*t+0.314),2)","t");    
             Numerical_Integral ni=new Numerical_Integral(0, 20, uf);
             
//             double x=ni.approximationValue(10);
//             System.out.println(x);
             double x1=ni.y0AsValue(200);
             System.out.println("numerical integration from yo as height: "+x1);
//            
             Numerical_Integral nii=new Numerical_Integral(0, 20, uf);

             double x2=nii.yMeanValue(200);
            System.out.println("numerical integration from average of the y0 and y1: "+x2);
          
            Numerical_Integral niii=new Numerical_Integral(0, 20, uf);

             double x3=niii.linearInterpolation(200);
             System.out.println("numerical integration from the linear interpolation: "+x3);
//            
//             
             ni.plot(v);
             nii.plot(v);
             niii.plot(v);

             v.setVisible(true);
//          
             double exactresult=34.986;
             double approxi=x2;
             System.out.println("Asolut Error is: "+(exactresult-approxi));
             System.out.println("Relative Error is: "+Math.abs((exactresult-approxi)*100/exactresult)+"%");
	}
     		
}
