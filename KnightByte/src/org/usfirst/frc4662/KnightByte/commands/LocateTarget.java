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
import edu.wpi.first.wpilibj.command.Scheduler;

//import org.usfirst.frc4662.KnightByte.OI;
import org.usfirst.frc4662.KnightByte.Robot;
//import org.usfirst.frc4662.KnightByte.RobotMap;

/**
 *
 */
public class LocateTarget extends Command {
	

//VARIABLE_DECLARATIONS
	private boolean m_checkTarget;
//CONSTRUCTORS
    public LocateTarget() {
    	
//VARIABLE_SETTING

//REQUIRES
        requires(Robot.shooterCam);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(1.5);
    	if(Robot.currCam == Robot.loaderCam){
    		Robot.currCam.stopCapture();
    		Robot.currCam = Robot.shooterCam;
    	}
		Robot.shooterCam.targetCamera();
		Robot.shooterCam.startCapture();
		Robot.shooterCam.getImage();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
   
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean bReturnValue = false;
    	m_checkTarget = Robot.shooterCam.FilterImage(20, 12, 240, 80);
    	if (m_checkTarget == true){
    		bReturnValue = true;
    	}
    	else if(isTimedOut() == true) {
    		bReturnValue = true;
    	}
   		return bReturnValue;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if (m_checkTarget == true){
    		Command commandGroup = (Command) new ShootingCommandGroup();
    		commandGroup.start();
    		//Scheduler.getInstance().add(new ShootingCommandGroup());
    	} else {
    		
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
