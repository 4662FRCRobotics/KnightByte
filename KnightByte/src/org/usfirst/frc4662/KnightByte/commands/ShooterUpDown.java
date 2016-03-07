// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4662.KnightByte.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc4662.KnightByte.OI;
import org.usfirst.frc4662.KnightByte.Robot;
import org.usfirst.frc4662.KnightByte.RobotMap;

/**
 *
 */
public class ShooterUpDown extends Command {
	

 
    public ShooterUpDown() {

    //Requires
        requires(Robot.shooter);

    
    }
  //BEGIN VARIABLE_DECLARATIONS
    
    
 //END VARIABLE_DECLARATIONS
    private double calculateTimeout(double dDistance){
    	//Calculate Timeout at later date 
    	int iIndex = (int)(dDistance);
    	if (iIndex > 30) {
    		iIndex = 30;
    	}
    	double shooterAngle = RobotMap.shooterAngleArray[iIndex];
    	if (shooterAngle < 0) {
    		shooterAngle = 0;
    	}
    	return shooterAngle;
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	double distance = Robot.shooterCam.GetDistance();
    	double timeout = calculateTimeout(distance);
    	//SmartDashboard.putNumber("ShooterTimeout", timeout);
    	//SmartDashboard.putNumber("Shooter Start Time", System.nanoTime());
    	setTimeout(timeout);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooter.upDownShooter(.1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
  //  	return (isTimedOut() || (RobotMap.ShooterTop.get() == false));
    	boolean isTimedOut = isTimedOut();
    	//SmartDashboard.putBoolean("Is Timed Out", isTimedOut);
    	//SmartDashboard.putNumber("Shooter isFinished Time", System.nanoTime());
    	return isTimedOut();
}

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.upDownShooter(0);
    	//SmartDashboard.putNumber("Shooter End Time", System.nanoTime());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
