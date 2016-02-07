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

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static SpeedController driveSubsystemLeftFront;
    public static SpeedController driveSubsystemRightFront;
    public static SpeedController driveSubsystemLeftRear;
    public static SpeedController driveSubsystemRightRear;
    public static RobotDrive driveSubsystemRobotDrive41;
    public static SpeedController shooterLeftShooterMotor;
    public static SpeedController shooterrightShooterMotor;
    public static SpeedController loaderLoaderWheels;
    public static SpeedController loaderLoaderArm;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static int cameraSessionLoader;
    public static int cameraSessionShooter;
    public static int cameraSessionCurrent;
    public static Image frame;
    public static NIVision.Rect cameraRect = new NIVision.Rect();
    
  //Ensure motor controllers are correct.
    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS	
        driveSubsystemLeftFront = new Talon(0);
        LiveWindow.addActuator("DriveSubsystem", "LeftFront", (Talon) driveSubsystemLeftFront);
        
        driveSubsystemRightFront = new Talon(1);
        LiveWindow.addActuator("DriveSubsystem", "RightFront", (Talon) driveSubsystemRightFront);
        
        driveSubsystemLeftRear = new Talon(2);
        LiveWindow.addActuator("DriveSubsystem", "LeftRear", (Talon) driveSubsystemLeftRear);
        
        driveSubsystemRightRear = new Talon(3);
        LiveWindow.addActuator("DriveSubsystem", "RightRear", (Talon) driveSubsystemRightRear);
        
        driveSubsystemRobotDrive41 = new RobotDrive(driveSubsystemLeftFront, driveSubsystemLeftRear,
              driveSubsystemRightFront, driveSubsystemRightRear);
        
        driveSubsystemRobotDrive41.setSafetyEnabled(true);
        driveSubsystemRobotDrive41.setExpiration(0.1);
        driveSubsystemRobotDrive41.setSensitivity(0.5);
        driveSubsystemRobotDrive41.setMaxOutput(1.0);
        driveSubsystemRobotDrive41.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        driveSubsystemRobotDrive41.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        
        shooterLeftShooterMotor = new Talon(4);
        LiveWindow.addActuator("Shooter", "LeftShooterMotor", (Talon) shooterLeftShooterMotor);
        
        shooterrightShooterMotor = new Talon(5);
        LiveWindow.addActuator("Shooter", "rightShooterMotor", (Talon) shooterrightShooterMotor);
        
        loaderLoaderWheels = new Talon(6);
        LiveWindow.addActuator("Loader", "LoaderWheels", (Talon) loaderLoaderWheels);
        
        loaderLoaderArm = new Talon(7);
        LiveWindow.addActuator("Loader", "LoaderArm", (Talon) loaderLoaderArm);
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        
        //frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_U8, 0); worse than RGB for bandwidth
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        
        cameraSessionLoader = NIVision.IMAQdxOpenCamera("cam1", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        cameraSessionShooter = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        cameraSessionCurrent = cameraSessionShooter;
        NIVision.IMAQdxConfigureGrab(cameraSessionCurrent);
    }
}
