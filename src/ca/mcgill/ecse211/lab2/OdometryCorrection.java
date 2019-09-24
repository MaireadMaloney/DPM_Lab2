package ca.mcgill.ecse211.lab2;

import static ca.mcgill.ecse211.lab2.Resources.*;
import lejos.hardware.sensor.EV3ColorSensor;

public class OdometryCorrection implements Runnable {
  /*
   * Variable that determines how long to sleep for correction in ms.
   */
  private static final long CORRECTION_PERIOD = 10;
  private Odometer odometer = Resources.odometer;
  private EV3ColorSensor colorSensor = Resources.colorSensor;

  // Array to store color detected by light sensor
  private float sampleData[] = new float[1];

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

      while (up) { // going up, Y is increasing
        colorSensor.getRedMode().fetchSample(sampleData, 0);
        brightness = sampleData[0];
        if (brightness * 100 < 30.00) {
          odometer.setY(TILE_SIZE * lineCounter);
          if (lineCounter > 2) {
            up = false;
            right = true;
          }
          lineCounter++;
        }
      }
      lineCounter = 1;
      while (right) { // going right, X is increasing
        colorSensor.getRedMode().fetchSample(sampleData, 0);
        brightness = sampleData[0];
        // System.out.println(sampleData[0]);
        if (brightness * 100 < 30.00) {
          odometer.setX(TILE_SIZE * lineCounter);
          if (lineCounter > 2) {
            right = false;
            down = true;
          }
          lineCounter++;
        }
      }
      lineCounter = 3;
      while (down) { // going right, X is increasing
        colorSensor.getRedMode().fetchSample(sampleData, 0);
        brightness = sampleData[0];
        // System.out.println(sampleData[0]);
        if (brightness * 100 < 30.00) {
          odometer.setY(TILE_SIZE * lineCounter);
          if (lineCounter < 2) {
            down = false;
            left = true;
          }
          lineCounter--;
        }

      }
      lineCounter = 3;
      while (left) { // going right, X is increasing
        colorSensor.getRedMode().fetchSample(sampleData, 0);
        brightness = sampleData[0];
        // System.out.println(sampleData[0]);
        if (brightness * 100 < 30.00) {
          odometer.setX(TILE_SIZE * lineCounter);
          if (lineCounter < 2) {
            left = false;
            up = true;
          }
          lineCounter--;
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
