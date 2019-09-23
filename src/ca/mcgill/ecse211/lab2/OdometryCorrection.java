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
  

  /*
   * Here is where the odometer correction code should be run.
   */
  public void run() {
    long correctionStart, correctionEnd;

    while (true) {
      correctionStart = System.currentTimeMillis();

      intensityVal.fetchSample(sampleData, 0);
      
      for(int i = 0; i< 10; i++) {
        if(sampleData[i]*100<35.00) {
        System.out.println(sampleData[i]*100);
        
      }}
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      //get current position of robot and define in terms of x, y, theta
      double currPosition[] = odometer.getXYT();
//      double currX = currPosition[0];
//      double currY = currPosition[1];
      double currTheta = currPosition[2];
      
      
      
      //define accurate position in terms of x, y
//      double correctX;

      
      //findDirection(currTheta);
//      if(up) { //going up
//       // int i = 1;
//        if (brightnessLevel < WHITEVAL) {
//          for(int i = 1; i<4; i++) {
//            
//          Sound.beep();
//          odometer.setY(TILE_SIZE * i);
//         
//        }
//      }
//      }
//      findDirection(currTheta);
//      while(right) { //going up
//        // int i = 1;
//         if (brightnessLevel < WHITEVAL) {
//           for(int i = 1; i<4; i++) {
//             
//           Sound.beep();
//           odometer.setX(TILE_SIZE * i);
//          
//         }
//       }
//       }
//      
//      findDirection(currTheta);
//      while(down) { //going up
//        // int i = 1;
//         if (brightnessLevel < WHITEVAL) {
//           for(int i = 1; i<4; i++) {
//             
//           Sound.beep();
//           odometer.setY(TILE_SIZE * i);
//          
//         }
//       }
//       }
//      findDirection(currTheta);
//      while(left) { //going up
//        // int i = 1;
//         if (brightnessLevel < WHITEVAL) {
//           for(int i = 1; i<4; i++) {
//             
//           Sound.beep();
//           odometer.setX(TILE_SIZE * i);
          
         
       
       
       

     
     
   
    
      


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
