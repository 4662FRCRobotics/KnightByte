package org.usfirst.frc4662.KnightByte.commands;

import org.usfirst.frc4662.KnightByte.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public class ShootingCommandGroup extends CommandGroup {
    
    public  ShootingCommandGroup() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	//addSequential(new LocateTarget());
    	addSequential(new RotateChassis());
    	addSequential(new ShooterUpDown());
    	addSequential(new LoaderArmPark());		//Need to break top three into separate command group
    	addSequential(new Shoot());
    	addSequential(new ShooterPark());
    	}
}
