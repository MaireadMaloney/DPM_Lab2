package ca.mcgill.ecse211.lab2;

import static ca.mcgill.ecse211.lab2.Resources.*;
import lejos.hardware.Sound;
import lejos.hardware.sensor.EV3ColorSensor;

public class OdometryCorrection implements Runnable {
  private static final long CORRECTION_PERIOD = 10;
  private Odometer odometer;
  private EV3ColorSensor colorSensor;
  
  //Array to store color detected by light sensor
  private float[] RGBValues = new float[3];
  private boolean atLine = false;
  
  //RGB value minimum for white
  private static double WHITEVAL = 0.20;
  
  //variable for overall brightness
  private double brightnessLevel;
  
  //Robot direction variables
  private Boolean up = true;
  private Boolean down = false;
  private Boolean right =false;
  private Boolean left = false;
  
//  //constructor to create the odometer and color sensor objects
//  public OdometryCorrection(Odometer odometer, EV3ColorSensor colorSense) {
//    this.odometer = odometer;
//    colorSensor = colorSense;
//}
  /*
   * Here is where the odometer correction code should be run.
   */
  public void run() {
    long correctionStart, correctionEnd;

    while (true) {
      correctionStart = System.currentTimeMillis();
      
      //Get reading from sensor and store the values in appropriate RGB array cell
      colorSensor.getRGBMode().fetchSample(RGBValues, 0); 
      
      //use RGB to get brightness level
      brightnessLevel = (RGBValues[0] + RGBValues[1] + RGBValues[2]);
      
      //get current position of robot and define in terms of x, y, theta
      double currPosition[] = odometer.getXYT();
//      double currX = currPosition[0];
//      double currY = currPosition[1];
      double currTheta = currPosition[2];
      
      //define accurate position in terms of x, y
//      double correctX;
//      double correctY;
      
      findDirection(currTheta);
      if(up) { //going up
        int i = 1;
        if (brightnessLevel < WHITEVAL) {
         // Sound.beep();
          odometer.setY(TILE_SIZE * i);
          if (i > 3) {
            up = false;
            right = true;
          }
          i++;
        }
      }
      if (right) { //going across to the right, X should be increasing
          int i = 1;
          if (brightnessLevel < WHITEVAL) { 
            Sound.beep(); //to test if updating
            odometer.setX(TILE_SIZE * i);
            if (i > 3) {
              right = false;
              down = true;
            }
            i++;
          }
        }
      if(down) { // going down
         int i = 3;
         if (brightnessLevel < WHITEVAL) {
           Sound.beep();
           odometer.setY(TILE_SIZE * i);
           if (i < 3) {
             down = false;
             right = true;
           }
           i--;
         }
      }
       if(left) { //going left
           int i = 3;
           if (brightnessLevel < WHITEVAL) {
             Sound.beep();
             odometer.setX(TILE_SIZE * i);
             if (i < 3) {
               down = false;
               right = true;
             }
             i--;
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
      int result = 0;
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
