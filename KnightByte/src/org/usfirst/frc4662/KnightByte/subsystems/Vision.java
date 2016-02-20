// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4662.KnightByte.subsystems;

import java.util.Comparator;
import java.util.Vector;

//import org.usfirst.frc4662.Tank2016.RobotMap;
//import org.usfirst.frc4662.Tank2016.commands.*;

//import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.usfirst.frc.team4662.robot.Robot.Scores;
import org.usfirst.frc4662.KnightByte.subsystems.Vision.ParticleReport;

import com.ni.vision.NIVision;
import com.ni.vision.VisionException;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import static edu.wpi.first.wpilibj.Timer.delay;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class Vision extends Subsystem {

	public static String kDefaultCameraName = "cam0";

	  private static String ATTR_VIDEO_MODE = "AcquisitionAttributes::VideoMode";
	  private static String ATTR_WB_MODE = "CameraAttributes::WhiteBalance::Mode";
	  private static String ATTR_WB_VALUE = "CameraAttributes::WhiteBalance::Value";
	  private static String ATTR_EX_MODE = "CameraAttributes::Exposure::Mode";
	  private static String ATTR_EX_VALUE = "CameraAttributes::Exposure::Value";
	  private static String ATTR_BR_MODE = "CameraAttributes::Brightness::Mode";
	  private static String ATTR_BR_VALUE = "CameraAttributes::Brightness::Value";

	  public class WhiteBalance {
		    public static final int kFixedIndoor = 3000;
		    public static final int kFixedOutdoor1 = 4000;
		    public static final int kFixedOutdoor2 = 5000;
		    public static final int kFixedFluorescent1 = 5100;
		    public static final int kFixedFlourescent2 = 5200;
	  }

	  private Pattern m_reMode =
		      Pattern
		          .compile("(?<width>[0-9]+)\\s*x\\s*(?<height>[0-9]+)\\s+(?<format>.*?)\\s+(?<fps>[0-9.]+)\\s*fps");

	  private String m_name = kDefaultCameraName;
	  private int m_id = -1;
	  private boolean m_active = false;
	  private boolean m_useJpeg = true;
	  private boolean m_targetMode = false;
	  private int m_width = 320;
	  private int m_height = 240;
//	  private int m_fps = 30;
	  private int m_fps = 8; //per 2014 white paper
	  private String m_whiteBalance = "auto";
	  private int m_whiteBalanceValue = -1;
	  private String m_exposure = "auto";
	  private int m_exposureValue = -1;
	  private int m_brightness = 50;
	  private double m_distance = -1;
//	  private boolean m_needSettingsUpdate = true;
	  
	  private Image m_frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
	  
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

  public Vision() {
    openCamera();
  }

  public Vision(String name) {
    m_name = name;
    openCamera();
  }

  public synchronized void openCamera() {
    if (m_id != -1)
      return; // Camera is already open
    for (int i = 0; i < 3; i++) {
      try {
        m_id =
            NIVision.IMAQdxOpenCamera(m_name,
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
      } catch (VisionException e) {
        if (i == 2)
          throw e;
        delay(2.0);
        continue;
      }
      break;
    }
  }
  
  		public synchronized void closeCamera() {
	    if (m_id == -1)
	      return;
	    NIVision.IMAQdxCloseCamera(m_id);
	    m_id = -1;
	  }

	  public synchronized void startCapture() {
	    if (m_id == -1 || m_active)
	      return;
	    NIVision.IMAQdxConfigureGrab(m_id);
	    NIVision.IMAQdxStartAcquisition(m_id);
	    m_active = true;
	  }

	  public synchronized void stopCapture() {
	    if (m_id == -1 || !m_active)
	      return;
	    NIVision.IMAQdxStopAcquisition(m_id);
	    NIVision.IMAQdxUnconfigureAcquisition(m_id);
	    m_active = false;
	  }

	  public synchronized void updateSettings() {
		    boolean wasActive = m_active;
		    // Stop acquistion, close and reopen camera
		    if (wasActive)
		      stopCapture();
		    if (m_id != -1)
		      closeCamera();
		    openCamera();

		    // Video Mode
		    NIVision.dxEnumerateVideoModesResult enumerated = NIVision.IMAQdxEnumerateVideoModes(m_id);
		    NIVision.IMAQdxEnumItem foundMode = null;
		    int foundFps = 1000;
		    for (NIVision.IMAQdxEnumItem mode : enumerated.videoModeArray) {
		      Matcher m = m_reMode.matcher(mode.Name);
		      if (!m.matches())
		        continue;
		      if (Integer.parseInt(m.group("width")) != m_width)
		        continue;
		      if (Integer.parseInt(m.group("height")) != m_height)
		        continue;
		      double fps = Double.parseDouble(m.group("fps"));
		      if (fps < m_fps)
		        continue;
		      if (fps > foundFps)
		        continue;
		      String format = m.group("format");
		      boolean isJpeg = format.equals("jpeg") || format.equals("JPEG");
		      if ((m_useJpeg && !isJpeg) || (!m_useJpeg && isJpeg))
		        continue;
		      foundMode = mode;
		      foundFps = (int) fps;
		    }
		    if (foundMode != null) {
		      System.out.println("found mode " + foundMode.Value + ": " + foundMode.Name);
		      if (foundMode.Value != enumerated.currentMode)
		        NIVision.IMAQdxSetAttributeU32(m_id, ATTR_VIDEO_MODE, foundMode.Value);
		    }

		    // White Balance
		    if (m_whiteBalance == "auto")
		      NIVision.IMAQdxSetAttributeString(m_id, ATTR_WB_MODE, "Auto");
		    else {
		      NIVision.IMAQdxSetAttributeString(m_id, ATTR_WB_MODE, "Manual");
		      if (m_whiteBalanceValue != -1)
		        NIVision.IMAQdxSetAttributeI64(m_id, ATTR_WB_VALUE, m_whiteBalanceValue);
		    }

		    // Exposure
		    if (m_exposure == "auto")
		      NIVision.IMAQdxSetAttributeString(m_id, ATTR_EX_MODE, "AutoAperaturePriority");
		    else {
		      NIVision.IMAQdxSetAttributeString(m_id, ATTR_EX_MODE, "Manual");
		      if (m_exposureValue != -1) {
//		        long minv = NIVision.IMAQdxGetAttributeMinimumI64(m_id, ATTR_EX_VALUE);
//		        long maxv = NIVision.IMAQdxGetAttributeMaximumI64(m_id, ATTR_EX_VALUE);
//		        long val = minv + (long) (((double) (maxv - minv)) * (((double) m_exposureValue) / 100.0));
		        NIVision.IMAQdxSetAttributeI64(m_id, ATTR_EX_VALUE, m_exposureValue);
		      }
		    }

		    // Brightness
		    NIVision.IMAQdxSetAttributeString(m_id, ATTR_BR_MODE, "Manual");
//		    long minv = NIVision.IMAQdxGetAttributeMinimumI64(m_id, ATTR_BR_VALUE);
//		    long maxv = NIVision.IMAQdxGetAttributeMaximumI64(m_id, ATTR_BR_VALUE);
//		    long val = minv + (long) (((double) (maxv - minv)) * (((double) m_brightness) / 100.0));
		    NIVision.IMAQdxSetAttributeI64(m_id, ATTR_BR_VALUE, m_brightness);

		    // Restart acquisition
		    if (wasActive)
		      startCapture();
		  }

    public void initDefaultCommand() {
 
//        setDefaultCommand(new TankDrive());

    }
 
//  set up driver viewable camera
    public void driverCamera(){
    	m_whiteBalance = "auto";
  	  	m_whiteBalanceValue = -1;
  	  	m_exposure = "auto";
  	  	m_exposureValue = -1;
  	  	m_brightness = 80;	
        updateSettings();
		m_targetMode = false;
    }
    
//  set up target viewable camera
    public void targetCamera(){
   		m_whiteBalance = "manual";
  		m_whiteBalanceValue = 2800;
   		m_exposure = "manual";
   		m_exposureValue = 10;
   		m_brightness = 30;	
   		updateSettings();
		m_targetMode = true;
     }
//  set up target viewable camera
    public void toggleTargetMode(){
    	if (m_targetMode == false) {
    		targetCamera();
    	} else {
    		driverCamera();
    	}
    }    
    public Image getImage() {
         NIVision.IMAQdxGrab(m_id, m_frame, 1);
         
         if (m_targetMode){
        	 FilterImage(20, 12, 240, 80);
         }
         
  
        return m_frame;
    }
    
// need toggle cameras to display - or can this???  NO this is a camera.
// need target calculations
	   
//End CAMERA SETTINGS
//Begin IMAGE MANIPULATION
    public class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport>{
			double PercentAreaToImageArea;
			double Area;
			double BoundingRectLeft;
			double BoundingRectTop;
			double BoundingRectRight;
			double BoundingRectBottom;
			  
			   public int compareTo(ParticleReport r)
			{
				return (int)(r.Area - this.Area);
			}
			
			public int compare(ParticleReport r1, ParticleReport r2)
			{
				return (int)(r1.Area - r2.Area);
			}
		};
		
		//Structure to represent the scores for the various tests used for target identification
		public class Scores {
			double Area;
			double Aspect;
		};

		//Images
		//first frame is m_frame		
		//int cameraSession; is m_id in this class 
		
		Image modFrame;
		int imaqError;

		//Constants
		NIVision.Range TOTE_HUE_RANGE = new NIVision.Range(101, 64);	//Default hue range for yellow tote
		NIVision.Range TOTE_SAT_RANGE = new NIVision.Range(88, 255);	//Default saturation range for yellow tote
		NIVision.Range TOTE_VAL_RANGE = new NIVision.Range(134, 255);	//Default value range for yellow tote
		double AREA_MINIMUM = 0.5; //Default Area minimum for particle as a percentage of total image area
//		double LONG_RATIO = 2.22; //Tote long side = 26.9 / Tote height = 12.1 = 2.22
//		double SHORT_RATIO = 1.4; //Tote short side = 16.9 / Tote height = 12.1 = 1.4
		double SCORE_MIN = 75.0;  //Minimum score to be considered a tote
		double VIEW_ANGLE = 50.0; //View angle for camera, set to Axis m1011 by default, 64 for m1013, 51.7 for 206, 52 for HD3000 square, 60 for HD3000 640x480
		NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
		NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
		Scores scores = new Scores();

		public void InitImaging() {
			modFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
			criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM, 100.0, 0, 0);
			
			/*SmartDashboard.putNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
			SmartDashboard.putNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
			SmartDashboard.putNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
			SmartDashboard.putNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
			SmartDashboard.putNumber("Tote val min", TOTE_VAL_RANGE.minValue);
			SmartDashboard.putNumber("Tote val max", TOTE_VAL_RANGE.maxValue);
			SmartDashboard.putNumber("Area min %", AREA_MINIMUM);*/
		}
		
		public boolean FilterImage(double width, double height, double imageArea, double particleArea) {
			//Update threshold values from SmartDashboard. For performance reasons it is recommended to remove this after calibration is finished.
			boolean isTarget = false;
			/*TOTE_HUE_RANGE.minValue = (int)SmartDashboard.getNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
			TOTE_HUE_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
			TOTE_SAT_RANGE.minValue = (int)SmartDashboard.getNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
			TOTE_SAT_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
			TOTE_VAL_RANGE.minValue = (int)SmartDashboard.getNumber("Tote val min", TOTE_VAL_RANGE.minValue);
			TOTE_VAL_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote val max", TOTE_VAL_RANGE.maxValue);*/

			//Threshold the image looking for Retro-tape
			NIVision.imaqColorThreshold(modFrame, m_frame, 255, NIVision.ColorMode.HSV, TOTE_HUE_RANGE, TOTE_SAT_RANGE, TOTE_VAL_RANGE);

			//Send particle count to dashboard
			int numParticles = NIVision.imaqCountParticles(modFrame, 1);
			SmartDashboard.putNumber("Masked particles", numParticles);

			//filter out small particles
			float areaMin = (float)SmartDashboard.getNumber("Area min %", AREA_MINIMUM);
			criteria[0].lower = areaMin;
			imaqError = NIVision.imaqParticleFilter4(modFrame, modFrame, criteria, filterOptions, null);

			//Send particle count after filtering to dashboard
			numParticles = NIVision.imaqCountParticles(modFrame, 1);
			SmartDashboard.putNumber("Filtered particles", numParticles);
			
			if(numParticles > 0) {
				//Measure particles and sort by particle size
				Vector<ParticleReport> particles = new Vector<ParticleReport>();
				for(int particleIndex = 0; particleIndex < numParticles; particleIndex++) {
					ParticleReport par = new ParticleReport();
					par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(modFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
					par.Area = NIVision.imaqMeasureParticle(modFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
					par.BoundingRectTop = NIVision.imaqMeasureParticle(modFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
					par.BoundingRectLeft = NIVision.imaqMeasureParticle(modFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
					par.BoundingRectBottom = NIVision.imaqMeasureParticle(modFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
					par.BoundingRectRight = NIVision.imaqMeasureParticle(modFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
					particles.add(par);
				}
				particles.sort(null);
				
				for(int particleIndex = 0; particleIndex < numParticles && isTarget == false; particleIndex++) {
					//This example only scores the largest particle. Extending to score all particles and choosing the desired one is left as an exercise
					//for the reader. Note that this scores and reports information about a single particle (single L shaped target). To get accurate information 
					//about the location of the tape (not just the distance) you will need to correlate two adjacent targets in order to find the true center of the tote.
					scores.Aspect = AspectScore(particles.elementAt(particleIndex), width, height);
					SmartDashboard.putNumber("Aspect", scores.Aspect);
					scores.Area = AreaScore(particles.elementAt(particleIndex), imageArea, particleArea);
					SmartDashboard.putNumber("Area", scores.Area);
					isTarget = scores.Aspect > SCORE_MIN && scores.Area > SCORE_MIN;
				SmartDashboard.putBoolean("isTarget", isTarget);
					if (isTarget == true){
						m_distance = computeDistance(modFrame,particles.elementAt(particleIndex));
						SmartDashboard.putNumber("distance" ,m_distance);
					}
					return isTarget;
				}
			}
			return isTarget;
		}
		
		static boolean CompareParticleSizes(ParticleReport particle1, ParticleReport particle2) {
			//we want descending sort order
			return particle1.PercentAreaToImageArea > particle2.PercentAreaToImageArea;
		}

		/**
		 * Converts a ratio with ideal value of 1 to a score. The resulting function is piecewise
		 * linear going from (0,0) to (1,100) to (2,0) and is 0 for all inputs outside the range 0-2
		 */
		double ratioToScore(double ratio) {
			return (Math.max(0, Math.min(100*(1-Math.abs(1-ratio)), 100)));
		}

		double AreaScore(ParticleReport report, double imageArea, double particleArea) {
			double boundingArea = (report.BoundingRectBottom - report.BoundingRectTop) * (report.BoundingRectRight - report.BoundingRectLeft);
			//Tape is 7" edge so 49" bounding rect. With 2" wide tape it covers 24" of the rect.
			//modified for a piece of tape 2x15 for test
			return ratioToScore((imageArea/particleArea)*report.Area/boundingArea);
		}

		/**
		 * Method to score if the aspect ratio of the particle appears to match the retro-reflective target. 
		 * OLD Target is 7"x7" so aspect should be 1
		 * Target is 20*12 
		 */
		double AspectScore(ParticleReport report, double width, double height) {
			return ratioToScore( width / height / ((report.BoundingRectRight-report.BoundingRectLeft)/(report.BoundingRectBottom-report.BoundingRectTop)));
		}

		/**
		 * Computes the estimated distance to a target using the width of the particle in the image. For more information and graphics
		 * showing the math behind this approach see the Vision Processing section of the ScreenStepsLive documentation.
		 *
		 * @param image The image to use for measuring the particle estimated rectangle
		 * @param report The Particle Analysis Report for the particle
		 * @param isLong Boolean indicating if the target is believed to be the long side of the tape
		 * @return The estimated distance to the target in feet.
		 */
		double computeDistance (Image image, ParticleReport report) {
			double normalizedWidth, targetWidth;
			//NIVision.GetImageSizeResult size;

			//size = NIVision.imaqGetImageSize(image);
			//normalizedWidth = 2*(report.BoundingRectRight - report.BoundingRectLeft)/size.width;
			normalizedWidth = 2*(report.BoundingRectRight - report.BoundingRectLeft)/m_width;
			targetWidth = 20.0;

			return  targetWidth/(normalizedWidth*12*Math.tan(VIEW_ANGLE*Math.PI/(180*2)));
		}
		
//Target Center = (Target Right - Target Left) / 2 + Target Left
//Pixels/Inch = (Target Right - Target left) / Target Width [20 inches]
//opposite = Target Center - Image Center [160] * Pixels/Inch
//Sin(Target Angle) = opposite / Distance
//Angle to turn = arcSin(Target Angle)
		double computeAngle (Image image, ParticleReport report) {
		
			double targetWidth;
			targetWidth = 20.0;
			double opposite = ((((report.BoundingRectRight - report.BoundingRectLeft)/ 2) + report.BoundingRectLeft) - (m_width / 2)) * (report.BoundingRectRight - report.BoundingRectLeft) / targetWidth;

			return  Math.asin(opposite/ m_distance);
		}
}

