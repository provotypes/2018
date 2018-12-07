/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6844.robot;

import java.io.File;

import org.usfirst.frc.team6844.robot.auto.PublicKey;
import org.uvstem.borg.BorgRobot;
import org.uvstem.borg.joysticks.LogitechGamepadController;
import org.uvstem.borg.logging.CSVStateLogger;
import org.uvstem.borg.logging.TextFileMessageLogger;

import easypath.EasyPath;
import easypath.EasyPathConfig;
import easypath.FollowPath;
import easypath.Path;
import easypath.PathUtil;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends BorgRobot {
	
	private Command m_autonomousCommand;
	
	//50 ticks in a second
	public static final int TICKS_PER_SEC = 50;
	
	int delay = 5 * TICKS_PER_SEC;
	
	//counter for autos
	int counter = 0;

	Drivetrain drivetrain;
	Arm arm;

	LogitechGamepadController gamepadDriver;
	LogitechGamepadController gamepadOperator;

	@Override
	public void robotInit() {
		super.robotInit();

		long initTime = System.currentTimeMillis();

		CameraServer.getInstance().startAutomaticCapture();

		drivetrain = new Drivetrain();

		gamepadDriver = new LogitechGamepadController(1);
		gamepadOperator = new LogitechGamepadController(2);

		registerSubsystem("drivetrain", drivetrain);

		// Set up logging warnings, information, etc. to text file.
		try {
			File textFile = new File("/U/logs/messages/" + initTime + ".txt");
			textFile.createNewFile();

			setMessageLogger(new TextFileMessageLogger(textFile));
		} catch (Exception e) {
			System.err.println("Unable to log messages to text file!");
			e.printStackTrace();
		}

		// Set up logging robot state to a CSV file.
		try {
			File csvFile = new File("/U/logs/state/" + initTime + ".csv");
			csvFile.createNewFile();

			setStateLogger(new CSVStateLogger(csvFile));
		} catch (Exception e) {
			System.err.println("Unable to log state to csv!");
			e.printStackTrace();
		}

		// Initialize autonomous scripting system.
		try {
			initAutoScripts(PublicKey.keyBytes, new File("/U/autos"));
		} catch (Exception e) {
			System.err.println("Unable to initalize auto scripts!");
			e.printStackTrace();
		}
		
		//Using both left and right encoder
		EasyPathConfig config = new EasyPathConfig(
		        drivetrain, // the subsystem itself
		        drivetrain::setLeftRightSpeeds, // function to set left/right speeds
		        // function to give EasyPath the length driven
		        () -> PathUtil.defaultLengthDrivenEstimator(drivetrain::getLeftEncoderDistance, drivetrain::getRightEncoderDistance),
		        drivetrain.gyro::getAngle, // function to give EasyPath the heading of your robot
		        drivetrain::resetMeasurements, // function to reset your encoders to 0
		        0.07 // kP value for P loop
		    );
		
/*		//Only using left encoder, not left and right
		EasyPathConfig config = new EasyPathConfig(
		        drivetrain, // the subsystem itself
		        drivetrain::setLeftRightSpeeds, // function to set left/right speeds
		        drivetrain.encoderLeft::getDistance, // function to give EasyPath the length driven
		        drivetrain.gyro::getAngle, // function to give EasyPath the heading of your robot
		        drivetrain::resetMeasurements, // function to reset your encoders to 0
		        0.07 // kP value for P loop
		    );
*/			
		EasyPath.configure(config);
		
		
	}

	/* 
	 * AUTONOMOUS 
	 */
	
	@Override
	public void autonomousInit() {
		super.autonomousInit();
		
		try {
			initAutoScripts(PublicKey.keyBytes, new File("/U/autos"));
		} catch (Exception e) {
			System.err.println("Unable to initalize auto scripts!");
			e.printStackTrace();
		}
		
		drivetrain.resetMeasurements();
		
		// This drives 36 inches in a straight line, driving at 25% speed the first 50% of the path,
		// and 75% speed in the remainder.
		// x is the percentage completion of the path, between 0 and 1.
//	     m_autonomousCommand = new FollowPath( //had to add FollowPath to the beginning, wasn't in the readme
//	        PathUtil.createStraightPath(36.0), x -> {
//	          if (x < 0.5) return 0.25;
//	          else return 0.75;
//	        });
		
		Path curvePath = new Path(t -> 
		/* {"start":{"x":0,"y":165},"mid1":{"x":72,"y":164},"mid2":{"x":39,"y":54},"end":{"x":121,"y":53}} */
		(654 * Math.pow(t, 2) + -654 * t + -3) / (660 * Math.pow(t, 2) + -630 * t + 216),
		178.247);
	    
	    m_autonomousCommand = new FollowPath(
	    		//PathUtil.createStraightPath(60.0), 
	    		curvePath,
	    		//x -> (x < 0.25) ? 0.5 : (x < 0.75 ? 0.75 : 0.25)
	    		x -> (0.5)
	    );
	    
	    m_autonomousCommand.start();
	    
	}
	
	
	@Override
	public void autonomousPeriodic() {
		
		Scheduler.getInstance().run();
		
		//System.out.println("Is running: " + m_autonomousCommand.isRunning());
		//straightAuto();
		//rightSwitchAuto();
		//leftSwitchAuto();
		//System.out.println("LEFT: " + drivetrain.getLeftEncoderDistance());
		//System.out.println("RIGHT: " + drivetrain.getRightEncoderDistance());
		System.out.println("GYRO: " + drivetrain.gyro.getAngle());
	}

	/* 
	 * TELEOP 
	 */

	@Override
	public void teleopInit() {
		super.teleopInit();
		
	}

	@Override
	public void teleopPeriodic() {
		super.teleopPeriodic();

		operateDrivetrain();
		operateArm();
		
		System.out.println("Left encoder: " + drivetrain.encoderLeft.get());
		System.out.println("Right encoder: " + drivetrain.encoderRight.get());
	}

	private void operateDrivetrain() {
		drivetrain.arcadeDrive(gamepadDriver.getLeftY() * .7, gamepadDriver.getRightX() * .7);

		//Driver start button, nerfs the speed
		if (gamepadDriver.getRawButtonPressed(gamepadDriver.START_BUTTON)) {
			drivetrain.nerfSpeed();
		}

		//Driver A button, switches forwards and backwards
		if (gamepadDriver.getRawButtonPressed(gamepadDriver.A_BUTTON)) {
			drivetrain.reverseDriveDirection();
		}

		drivetrain.update();
	}

	private void operateArm() {
		arm.set(gamepadOperator.getRightY());
		arm.update();
	}

	/* 
	 * TESTING 
	 */

	@Override
	public void testInit() {
		super.testInit();

		try {
			initAutoScripts(PublicKey.keyBytes, new File("/U/autos"));
		} catch (Exception e) {
			System.err.println("Unable to initalize auto scripts!");
			e.printStackTrace();
		}
	}

	@Override
	public void testPeriodic() {
		super.testPeriodic();
		
		System.out.println("RIGHT: " + drivetrain.encoderRight.getDistance());
		System.out.println("LEFT: " + drivetrain.encoderLeft.getDistance());
	}

	/* 
	 * AUTONOMOUS FUNCTIONS
	 */

	public void straightAuto() {
		if (counter < 50) {
			drivetrain.tankDrive(.8, .8);
		} else if (counter < 200) {
			drivetrain.tankDrive(.3, .3);
		} else {
			drivetrain.tankDrive(0, 0);
			//System.out.println(drivetrain.encoderLeft.getDistance());
		}
		counter++;
		
		drivetrain.update();
		//arm.update();
	}

	public void rightSwitchAuto() {
		if (counter < 50) {
			drivetrain.tankDrive(.8, .8);
		} else if (counter < 150) {
			drivetrain.tankDrive(.3, .4);
		} else {
			if (gameData.charAt(0) == 'R') {
				if (counter < 200) {
					drivetrain.tankDrive(.5, -.5);
				} else if (counter < 300){
					drivetrain.tankDrive(.3, .3);
				} else if (counter < 450){
					drivetrain.tankDrive(0, 0);
					arm.set(.6);
				} else {
					arm.set(-.3);
					drivetrain.tankDrive(0, 0);
				}
			} else {
				drivetrain.tankDrive(0, 0);
			}
		} 
		counter += 1;
		
		drivetrain.update();
		arm.update();
	}

	public void leftSwitchAuto() {
		if (counter < 50) {
			drivetrain.tankDrive(.8, .8);
		} else if (counter < 150) {
			drivetrain.tankDrive(.3, .4);
		} else {
			if (gameData.charAt(0) == 'L') {
				if (counter < 200) {
					drivetrain.tankDrive(-.5, .5);
				} else if (counter < 300){
					drivetrain.tankDrive(.3, .3);
				} else if (counter < 450){
					drivetrain.tankDrive(0, 0);
					arm.set(.6);
				} else {
					arm.set(-.3);
					drivetrain.tankDrive(0, 0);
				}
			} else {
				drivetrain.tankDrive(0, 0);
			}
		} 
		counter += 1;
		
		drivetrain.update();
		arm.update();
	}
}
