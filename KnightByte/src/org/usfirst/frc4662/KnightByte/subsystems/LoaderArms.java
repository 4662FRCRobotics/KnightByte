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

import org.usfirst.frc4662.KnightByte.RobotMap;
import org.usfirst.frc4662.KnightByte.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class LoaderArms extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final SpeedController loaderArm = RobotMap.loaderLoaderArm;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS


    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

       setDefaultCommand(new LoaderArmInOut());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    // Spin loader wheels
/*    public void spinLoaderWheels(double speed){
    	
    	RobotMap.loaderLoaderWheels.set(speed);
    	
    }
*/
    public void moveArmInOut(Joystick operatorJoystick){
    double loaderArmSpeed = (-operatorJoystick.getY() * (-operatorJoystick.getZ() + 3) * 0.25);
//    double loaderArmSpeed = (operatorJoystick.getY() * 0.5);
     if (loaderArmSpeed > 0 && (RobotMap.LoaderPark.get() == false)) {
    	 loaderArmSpeed = 0;
     } else if (loaderArmSpeed < 0 && (RobotMap.LoaderOut.get() == false)) {
    	 loaderArmSpeed = 0;
     }
     SmartDashboard.putNumber("Loader Arm Speed", loaderArmSpeed);
	 RobotMap.loaderLoaderArm.set(loaderArmSpeed);
    }
    
   public void parkLoaderArm(double armSpeed){
	   armSpeed = Math.abs(armSpeed);
	   RobotMap.loaderLoaderArm.set(armSpeed);
   }
 }
    


