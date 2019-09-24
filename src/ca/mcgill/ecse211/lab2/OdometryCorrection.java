package ca.mcgill.ecse211.lab2;

import static ca.mcgill.ecse211.lab2.Resources.*;
import lejos.hardware.Sound;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class OdometryCorrection implements Runnable {
  private static final long CORRECTION_PERIOD = 10;
  // private static final difference =
  private Odometer odometer = Resources.odometer;
  private EV3ColorSensor colorSensor = Resources.colorSensor;
  // private SampleProvider intensityVal = colorSensor.getRedMode();


  // Array to store color detected by light sensor
  private float sampleData[] = new float[1];


  // private float[] RGBValues = new float[3];

  // RGB value minimum for white
  // private static double WHITEVAL = 0.20;

  // variable for overall brightness
  // private double brightnessLevel;

  // Robot direction variables
  private Boolean up = true;
  private Boolean down = false;
  private Boolean right = false;
  private Boolean left = false;

  private float brightness;

  // int i = 0;

  /*
   * Here is where the odometer correction code should be run.
   */
  public void run() {
    long correctionStart, correctionEnd;

    while (true) {
      correctionStart = System.currentTimeMillis();

      colorSensor.getRedMode().fetchSample(sampleData, 0);
      brightness = sampleData[0];

      // get current position of robot and define in terms of x, y, theta
      double currPosition[] = odometer.getXYT();
      // double currX = currPosition[0];
      // double currY = currPosition[1];
      double currTheta = currPosition[2];
      int lineCounter = 0;


      // define accurate position in terms of x, y
      double correctX;


      int i = 1;
      int j = 1;
      int k = 3;
      int f = 3;

      while (up) { // going up, Y is increasing
        colorSensor.getRedMode().fetchSample(sampleData, 0);
        brightness = sampleData[0];
        // System.out.println(sampleData[0]);
        if (brightness * 100 < 30.00) {
          Sound.beep();
          odometer.setY(TILE_SIZE * i);
          if (i > 2) {
            up = false;
            right = true;
          }


          i++;
        }


      }

      while (right) { // going right, X is increasing
        colorSensor.getRedMode().fetchSample(sampleData, 0);
        brightness = sampleData[0];
        // System.out.println(sampleData[0]);
        if (brightness * 100 < 30.00) {
          Sound.beep();
          odometer.setX(TILE_SIZE * j);
          if (j > 2) {
            right = false;
            down = true;
          }


          j++;
        }


      }
      while (down) { // going right, X is increasing
        colorSensor.getRedMode().fetchSample(sampleData, 0);
        brightness = sampleData[0];
        // System.out.println(sampleData[0]);
        if (brightness * 100 < 30.00) {
          Sound.beep();
          odometer.setY(TILE_SIZE * k);
          if (k < 2) {
            down = false;
            left = true;
          }


          k--;
        }

      }

      while (left) { // going right, X is increasing
        colorSensor.getRedMode().fetchSample(sampleData, 0);
        brightness = sampleData[0];
        // System.out.println(sampleData[0]);
        if (brightness * 100 < 30.00) {
          Sound.beep();
          odometer.setX(TILE_SIZE * f);
          if (f < 2) {
            left = false;
            up = true;
          }


          f--;
        }

      }



      // TODO Trigger correction (When do I have information to correct?)

      // TODO Calculate new (accurate) robot position



      // TODO Update odometer with new calculated (and more accurate) values, eg:
      // odometer.setXYT(0.3, 19.23, 5.0);

      // this ensures the odometry correction occurs only once every period
      correctionEnd = System.currentTimeMillis();
      if (correctionEnd - correctionStart < CORRECTION_PERIOD) {
        Main.sleepFor(CORRECTION_PERIOD - (correctionEnd - correctionStart));
      }
    }
  }

  /*
   * Finds the nearest right angle (0,90,180,270,etc) to the robots direction to determine if we are moving in the X or
   * the Y. We call X 0 and Y 90.
   */
  private void findDirection(double t) {
    int allowedError = 5;
    /*
     * checks to see if we are in the allowed range for any of the right angles.
     */
    if (Math.abs(t) < allowedError) {
      up = true;
    } else if (Math.abs(t - 90) < allowedError) {
      right = true;
    } else if (Math.abs(t - 180) < allowedError) {
      left = true;
    } else if (Math.abs(t - 270) < allowedError) {
      down = true;
    }

    return;
  }


}
