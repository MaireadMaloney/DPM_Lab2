package ca.mcgill.ecse211.lab2;

import static ca.mcgill.ecse211.lab2.Resources.*;
import lejos.hardware.Sound;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class OdometryCorrection implements Runnable {
  private static final long CORRECTION_PERIOD = 10;
  //private static final difference = 
  private Odometer odometer = Resources.odometer ;
  private EV3ColorSensor colorSensor = Resources.colorSensor;
  private SampleProvider intensityVal = colorSensor.getRedMode();
 
  
  //Array to store color detected by light sensor
  private float sampleData[] = new float [intensityVal.sampleSize()];
  
  
  //private float[] RGBValues = new float[3];
  
  //RGB value minimum for white
  //private static double WHITEVAL = 0.20;
  
  //variable for overall brightness
  //private double brightnessLevel;
  
  //Robot direction variables
  private Boolean up = true;
  private Boolean down = false;
  private Boolean right =false;
  private Boolean left = false;
  
  int i = 0;

  /*
   * Here is where the odometer correction code should be run.
   */
  public void run() {
    long correctionStart, correctionEnd;

    while (true) {
      correctionStart = System.currentTimeMillis();

      intensityVal.fetchSample(sampleData, 0);
      
      
        }
      
=======
        }
      }
        
>>>>>>> 7e6276d2dd28176e23bfe49467d1f48650f70865
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      //get current position of robot and define in terms of x, y, theta
      double currPosition[] = odometer.getXYT();
//      double currX = currPosition[0];
//      double currY = currPosition[1];
      double currTheta = currPosition[2];
      int lineCounter  = 0;
      
      
      //define accurate position in terms of x, y
      double correctX;
      
      findDirection(currTheta);
      if(up) { //going up, Y is increasing
        while (lineCounter < 4) {
          if(sampleData[i] * 100 < 30.00) { 
            lineCounter++;
            odometer.setY(TILE_SIZE * lineCounter);
          }
          i++;
        }
      }
      if (right) { //going right, X is increasing
        lineCounter = 0;
        while (lineCounter < 3) {
          if(sampleData[i] * 100 < 30.00) {
            lineCounter++;
            odometer.setX(TILE_SIZE * lineCounter);
          }
         i++;
        }  
      }
         
      if (down) { //going down, Y is decreasing
        lineCounter = 3;
        while (lineCounter > 0) {
          if(sampleData[i] * 100 < 30.00) {
            lineCounter--;
            odometer.setY(TILE_SIZE * lineCounter);
          }
          i++;
        }          
      }
      
      if (left) { //going left, X is decreasing
        lineCounter = 3;
        double oldX = currPosition[0];
        while (lineCounter > 0) {
          if(sampleData[i] * 100 < 30.00) {
            lineCounter--;
            oldX-= TILE_SIZE;
            odometer.setX(TILE_SIZE * lineCounter);
          }
          i++;
        }
     }
    
         
       
       
       

     
     
   
    
      


      // TODO Trigger correction (When do I have information to correct?)
      
      // TODO Calculate new (accurate) robot position
      
      
      

      // TODO Update odometer with new calculated (and more accurate) values, eg:
      //odometer.setXYT(0.3, 19.23, 5.0);

      // this ensures the odometry correction occurs only once every period
      correctionEnd = System.currentTimeMillis();
      if (correctionEnd - correctionStart < CORRECTION_PERIOD) {
        Main.sleepFor(CORRECTION_PERIOD - (correctionEnd - correctionStart));
      }
   }   
}
  /*
   * Finds the nearest right angle (0,90,180,270,etc) to the robots direction
   * to determine if we are moving in the X or the Y. We call X 0 and Y 90.
   */
  private void findDirection(double t){
      int allowedError = 5;
      /*
       * checks to see if we are in the allowed range for any of the right angles.
       */
      if (Math.abs(t) < allowedError ) {
        up = true;
      }
      else if(Math.abs(t-90)< allowedError ) {
        right = true;
      }
      else if(Math.abs(t-180)< allowedError ) {
        left = true;
      }
      else if(Math.abs(t-270)< allowedError ) {
        down = true;
      }
      
      return;
  }
  
  
}
