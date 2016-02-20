// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4662.KnightByte.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc4662.KnightByte.Robot;

/**
 *
 */
public class  AutoMoveChassis extends Command {
	
	AutoCardinalDirections m_direction;
	
	double m_dDistance;
    double m_dX;
    double m_dY;
    double m_dZ;
    double m_dSpeed;
    
	public AutoMoveChassis(AutoCardinalDirections direction, double dDistance, double dSpeed) {
    
		m_direction = direction; 
		m_dDistance = dDistance;
		m_dSpeed = dSpeed;
    	// Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
      	//Preferences prefs = Preferences.getInstance();
      	//double dVelocity = 60; //prefs.getDouble("NorthSouthMaxVelocityInchesPerSecond", 60);
    	//setTimeout(dDistance/dVelocity);
        requires(Robot.driveSubsystem);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double dNSVelocity = 113*m_dSpeed;
    	double dEWVelocity = 70*m_dSpeed;
    	switch (m_direction) {
    		case NORTH:
    	    	//Robot.driveSubsystem.setMecanumDrive(0, -1, 0);
    	    	m_dX = 0; m_dY = -1*m_dSpeed; m_dZ = 0;
    			SmartDashboard.putString("Driving", "North");
    			setTimeout(m_dDistance/dNSVelocity);
    			break;
    		case SOUTH:    	
    			//Robot.driveSubsystem.setMecanumDrive(0, 1, 0);
    			m_dX = 0; m_dY = 1*m_dSpeed; m_dZ = 0;
    			SmartDashboard.putString("Driving", "South");
    			setTimeout(m_dDistance/dNSVelocity);
    			break;
    		case EAST:
    	    	//Robot.driveSubsystem.setMecanumDrive(-1, 0, 0);
    			m_dX = 1*m_dSpeed; m_dY = 0; m_dZ = 0;
    			SmartDashboard.putString("Driving", "East");
    			setTimeout(m_dDistance/dEWVelocity);
    			break;
    		case WEST:
    	    	//Robot.driveSubsystem.setMecanumDrive(1, 0, 0);
    			m_dX = -1*m_dSpeed; m_dY = 0; m_dZ = 0;
    			SmartDashboard.putString("Driving", "West");
    			setTimeout(m_dDistance/dEWVelocity);
    			break;
    		
    		default: 		
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Robot.driveSubsystem.setMecanumDrive(m_dX, m_dY, m_dZ);
    	Robot.driveSubsystem.driveTankAutonomous(1, 1);
    	//                                       ^^^^^ this is wrong HL 2.10.16
    }

    // Make  this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.driveTankAutonomous(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
