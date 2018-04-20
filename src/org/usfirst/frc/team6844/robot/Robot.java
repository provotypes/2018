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

import edu.wpi.first.wpilibj.CameraServer;

public class Robot extends BorgRobot {

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
		arm = new Arm();

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
	}

	@Override
	public void autonomousInit() {
		super.autonomousInit();

		try {
			initAutoScripts(PublicKey.keyBytes, new File("/U/autos"));
		} catch (Exception e) {
			System.err.println("Unable to initalize auto scripts!");
			e.printStackTrace();
		}

		drivetrain.resetGyro();
		drivetrain.resetEncoders();
	}

	int counter = 0;
	
	@Override
	public void autonomousPeriodic() {
		
		straightAuto();
		
		//rightSwitchAuto();
	}

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
	}
	
	public void straightAuto() {
		if (counter < 50) {
			drivetrain.tankDrive(.8, .8);
		} else if (counter < 200) {
			drivetrain.tankDrive(.3, .3);
		} else {
			drivetrain.tankDrive(0, 0);
			System.out.println(drivetrain.encoderLeft.getDistance());
		}
		counter += 1;
		
		drivetrain.update();
		arm.update();
	}
	
	public void rightSwitchAuto() {
		if (counter < 50) {
			drivetrain.tankDrive(.8, .8);
		} else if (counter < 150) {
			drivetrain.tankDrive(.3, .4);
		} else if (counter < 200){
			drivetrain.tankDrive(.5, -.5);
		} else if (counter < 300){
			drivetrain.tankDrive(.3, .3);
		} else if (counter < 400){
			drivetrain.tankDrive(0, 0);
			if (gameData.charAt(0) == 'R') {
				arm.set(1);
			}
		} else {
			arm.set(0.0);
			drivetrain.tankDrive(0, 0);
		}
		counter += 1;
		
		drivetrain.update();
		arm.update();
	}
}
