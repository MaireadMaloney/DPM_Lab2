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

  /*
   * Here is where the odometer correction code should be run.
   */
  public void run() {
    long correctionStart, correctionEnd;

    while (true) {
      int lineCounter = 1;
      correctionStart = System.currentTimeMillis();

      colorSensor.getRedMode().fetchSample(sampleData, 0);
      brightness = sampleData[0];

      int i = 1;
      int j = 1;
      int k = 3;
      int f = 3;

      while (up) { // going up, Y is increasing
        colorSensor.getRedMode().fetchSample(sampleData, 0);
        brightness = sampleData[0];
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
}
