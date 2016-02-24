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

import org.usfirst.frc4662.KnightByte.OI;
import org.usfirst.frc4662.KnightByte.Robot;
import org.usfirst.frc4662.KnightByte.RobotMap;

/**
 *
 */
public class ShooterUpDown extends Command {
	

    //VARIABLE_DECLARATIONS
    public ShooterUpDown() {

    //Requires
        requires(Robot.shooter);

    
    }
    private double calculateTimeout(double dDistance){
    	//Calculate Timeout at later date 
    	return 0;
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    	double distance = Robot.shooterCam.GetDistance();
    	setTimeout(.5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooter.upDownShooter(.1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.upDownShooter(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
