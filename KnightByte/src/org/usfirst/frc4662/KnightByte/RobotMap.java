// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4662.KnightByte;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.VictorSP;
// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    //BEGIN DECLARATIONS
    public static SpeedController driveSubsystemLeftFront;
    public static SpeedController driveSubsystemRightFront;
    public static SpeedController driveSubsystemLeftRear;
    public static SpeedController driveSubsystemRightRear;
    public static RobotDrive driveSubsystemRobotDrive41;
    public static SpeedController shooterLeftShooterMotor;
    public static SpeedController shooterRightShooterMotor;
    public static SpeedController loaderLoaderWheels;
    public static SpeedController loaderLoaderArm;
    public static SpeedController shooterAngleMotor;
    public static AnalogGyro rotationalGyro;
    public static DigitalInput ShooterPark;
    public static DigitalInput ShooterTop;
    public static DigitalInput LoaderPark;
    public static DigitalInput LoaderLaunch;
    public static DigitalInput LoaderOut;
    public static DigitalInput BallLaunch;


    public static Image cameraFrame;
    public static Encoder driveEncoder;
    public static double[] shooterAngleArray;
/*  public static int cameraSessionLoader;
    public static int cameraSessionShooter;
    public static int cameraSessionCurrent;
    public static Image frame;
    public static NIVision.Rect cameraRect = new NIVision.Rect();
*/
    //END DECLARATIONS
  //Ensure motor controllers are correct.
    
    private static String readFile( String file ) throws IOException {
		BufferedReader reader = new BufferedReader( new FileReader (file));
		String	       line = null;
		StringBuilder   stringBuilder = new StringBuilder();
		boolean bFirstTime = true;
		while( ( line = reader.readLine())!= null){
			line = line.trim();
			if (bFirstTime == true) {
				bFirstTime = false;
			} else {
				stringBuilder.append( "|" );
			}
			stringBuilder.append( line );
		}
		reader.close();
		return stringBuilder.toString();
	}
    
    public static void init() {
    // BEGIN CONSTRUCTORS	
    	
        String strRobotINI;
        
        try {
        	strRobotINI = readFile("/home/lvuser/RobotINI.txt");
        	
        } catch (IOException e) {
        	strRobotINI = "comp";
        }
        if(strRobotINI == "comp"){
        	Robot.bIsCompetition = true;
        }
	    if(Robot.bIsCompetition){ 	
	        driveSubsystemLeftFront = new TalonSRX(0);
	        LiveWindow.addActuator("DriveSubsystem", "LeftFront", (TalonSRX) driveSubsystemLeftFront);
	        
	        driveSubsystemRightFront = new TalonSRX(1);
	        LiveWindow.addActuator("DriveSubsystem", "RightFront", (TalonSRX) driveSubsystemRightFront);
	        
	        driveSubsystemLeftRear = new TalonSRX(2);
	        LiveWindow.addActuator("DriveSubsystem", "LeftRear", (TalonSRX) driveSubsystemLeftRear);
	        
	        driveSubsystemRightRear = new TalonSRX(3);
	        LiveWindow.addActuator("DriveSubsystem", "RightRear", (TalonSRX) driveSubsystemRightRear);
	        
	        driveSubsystemRobotDrive41 = new RobotDrive(driveSubsystemLeftFront, driveSubsystemLeftRear,
	              driveSubsystemRightFront, driveSubsystemRightRear);
	        
	        driveSubsystemRobotDrive41.setSafetyEnabled(true);
	        driveSubsystemRobotDrive41.setExpiration(0.2);
	        driveSubsystemRobotDrive41.setSensitivity(0.5);
	        driveSubsystemRobotDrive41.setMaxOutput(1.0);
	 //       driveSubsystemRobotDrive41.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
	   //     driveSubsystemRobotDrive41.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
	        
	        shooterLeftShooterMotor = new VictorSP(4);
	        LiveWindow.addActuator("Shooter", "LeftShooterMotor", (VictorSP) shooterLeftShooterMotor);
	        shooterLeftShooterMotor.setInverted(true);
	        
	        shooterRightShooterMotor = new VictorSP(5);
	        LiveWindow.addActuator("Shooter", "RightShooterMotor", (VictorSP) shooterRightShooterMotor);
	        shooterRightShooterMotor.setInverted(true);
	        
	        shooterAngleMotor = new TalonSRX(6);
	        LiveWindow.addActuator("Shooter", "ShooterAngleMotor", (TalonSRX) shooterAngleMotor);
//	        shooterAngleMotor.setInverted(true);
	        
	        loaderLoaderWheels = new VictorSP(8);
	        LiveWindow.addActuator("Loader", "LoaderWheels", (VictorSP) loaderLoaderWheels);
//	        loaderLoaderWheels.setInverted(true);
	        
	        loaderLoaderArm = new VictorSP(7);
	        LiveWindow.addActuator("Loader", "LoaderArm", (VictorSP) loaderLoaderArm);
//	        loaderLoaderArm.setInverted(true);
	        
	        rotationalGyro = new AnalogGyro(0);
	        LiveWindow.addSensor("DriveSubsystem", "RotationalGyro)", rotationalGyro);
	        rotationalGyro.setSensitivity(0.007);
	        
	        driveEncoder = new Encoder(1,0);
	        driveEncoder.setDistancePerPulse(8 * Math.PI/2048);
	        driveEncoder.setReverseDirection(true);
//	        ShooterPark = new DigitalInput (6); //used to be 3
//	        ShooterTop = new DigitalInput (7); //used to be 4
	        LoaderPark = new DigitalInput (4);
	        LoaderOut = new DigitalInput (3);
//	        LoaderLaunch = new DigitalInput ();
	        BallLaunch = new DigitalInput (2);
	        shooterAngleArray = new double[30];
	    } else {
	    	driveSubsystemLeftFront = new TalonSRX(0);
	        LiveWindow.addActuator("DriveSubsystem", "LeftFront", (TalonSRX) driveSubsystemLeftFront);
	        
	        driveSubsystemRightFront = new TalonSRX(1);
	        LiveWindow.addActuator("DriveSubsystem", "RightFront", (TalonSRX) driveSubsystemRightFront);
	        
	        driveSubsystemLeftRear = new TalonSRX(2);
	        LiveWindow.addActuator("DriveSubsystem", "LeftRear", (TalonSRX) driveSubsystemLeftRear);
	        
	        driveSubsystemRightRear = new VictorSP(3);
	        LiveWindow.addActuator("DriveSubsystem", "RightRear", (TalonSRX) driveSubsystemRightRear);
	        
	        driveSubsystemRobotDrive41 = new RobotDrive(driveSubsystemLeftFront, driveSubsystemLeftRear,
	              driveSubsystemRightFront, driveSubsystemRightRear);
	        
	        driveSubsystemRobotDrive41.setSafetyEnabled(true);
	        driveSubsystemRobotDrive41.setExpiration(0.2);
	        driveSubsystemRobotDrive41.setSensitivity(0.5);
	        driveSubsystemRobotDrive41.setMaxOutput(1.0);
	 //       driveSubsystemRobotDrive41.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
	   //     driveSubsystemRobotDrive41.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
	        
	        shooterLeftShooterMotor = new Talon(4);
	        LiveWindow.addActuator("Shooter", "LeftShooterMotor", (Talon) shooterLeftShooterMotor);
	        shooterLeftShooterMotor.setInverted(true);
	        
	        shooterRightShooterMotor = new Talon(5);
	        LiveWindow.addActuator("Shooter", "RightShooterMotor", (Talon) shooterRightShooterMotor);
	        
	        shooterAngleMotor = new Talon(6);
	        LiveWindow.addActuator("Shooter", "ShooterAngleMotor", (Talon) shooterAngleMotor); 
	        
	        loaderLoaderWheels = new VictorSP(7);
	        LiveWindow.addActuator("Loader", "LoaderWheels", (VictorSP) loaderLoaderWheels);
	        
	        loaderLoaderArm = new VictorSP(8);
	        LiveWindow.addActuator("Loader", "LoaderArm", (VictorSP) loaderLoaderArm);
	        
	        rotationalGyro = new AnalogGyro(0);
	        LiveWindow.addSensor("DriveSubsystem", "RotationalGyro)", rotationalGyro);
	        rotationalGyro.setSensitivity(0.007);
	        
	        driveEncoder = new Encoder(1,0);
	        driveEncoder.setDistancePerPulse(7 * Math.PI/2048);
	        driveEncoder.setReverseDirection(true);
	        ShooterPark = new DigitalInput (3); 
	        ShooterTop = new DigitalInput (4);
	        LoaderPark = new DigitalInput (5);
	        LoaderOut = new DigitalInput (6);
	        LoaderLaunch = new DigitalInput (7);
	        BallLaunch = new DigitalInput (8);
	        shooterAngleArray = new double[30];
	    	
	    }
	   
        
        
        

    // END CONSTRUCTORS
        
        //frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_U8, 0); worse than RGB for bandwidth
/*   	frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        
        cameraSessionLoader = NIVision.IMAQdxOpenCamera("cam1", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        cameraSessionShooter = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        cameraSessionCurrent = cameraSessionShooter;
        NIVision.IMAQdxConfigureGrab(cameraSessionCurrent);
*/
        try {
			readShooterAngles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        SmartDashboard.putNumber("Shooter Array Length", shooterAngleArray.length);
    }    
    //Shooter Angle Table Read
    //dSpeed = Double.valueOf(autoArray[i + 1]);
    public static void readShooterAngles() throws IOException {
		BufferedReader reader = new BufferedReader( new FileReader ("/home/lvuser/ShooterAngles.txt"));
		String	       line = null;
		int shooterIndex = -1;
		while( ( line = reader.readLine())!= null){
			shooterIndex = shooterIndex + 1;
			shooterAngleArray[shooterIndex] = Double.valueOf(line.trim());
//			shooterAngleArray[shooterIndex] = line.trim();
		}
		reader.close();
		
	}
}
