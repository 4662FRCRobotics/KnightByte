package org.usfirst.frc4662.KnightByte.commands;

import org.usfirst.frc4662.KnightByte.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoWait extends Command{
	double m_dWaitDuration;
	
	public AutoWait (double dWaitDuration){
		requires (Robot.driveSubsystem);
		m_dWaitDuration = dWaitDuration;
	}
	
	protected void initialize() {
		setTimeout (m_dWaitDuration);
	}

	protected void execute() {
    	Robot.driveSubsystem.driveTankAutonomous(0, 0);
    }
	
	 protected boolean isFinished() {
	        return isTimedOut();
	    }
	 
	 protected void end() {

	    }
	 
	 protected void interrupted() {
	    }
}
