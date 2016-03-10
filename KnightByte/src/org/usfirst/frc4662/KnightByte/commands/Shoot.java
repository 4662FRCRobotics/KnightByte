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
import org.usfirst.frc4662.KnightByte.Robot;

/**
 *
 */
public class Shoot extends Command {
	
//VARIABLE_DECLARATIONS
	long mStartTime;
	long mCurrentTime;
	double mSpinSpeed;
//CONSTRUCTORS
    public Shoot() {
    	
//VARIABLE_SETTING

//REQUIRES
        requires(Robot.shooter);
        requires(Robot.loaderWheelSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(3);
    	mStartTime = System.nanoTime();
    	mSpinSpeed = .9;
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooter.spinShooterWheels(mSpinSpeed);
    	mCurrentTime = System.nanoTime();
//      	if(mCurrentTime - mStartTime > 500000000){
//    		mSpinSpeed = 1;
//    	}
      		if(mCurrentTime - mStartTime > 1500000000){
   
    		Robot.loaderWheelSubsystem.spinLoaderWheels(.5);
      	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.spinShooterWheels(0);
    	Robot.loaderWheelSubsystem.spinLoaderWheels(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
