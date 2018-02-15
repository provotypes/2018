/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6844.robot;

import java.io.File;
import java.io.FileNotFoundException;

import org.usfirst.frc.team6844.robot.Arm.Position;
import org.usfirst.frc.team6844.robot.Intake.State;
import org.uvstem.borg.BorgRobot;
import org.uvstem.borg.joysticks.LogitechGamepadController;
import org.uvstem.borg.logging.CSVStateLogger;
import org.uvstem.borg.logging.TextFileMessageLogger;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Robot extends BorgRobot {

	Drivetrain drivetrain;
	Intake intake;
	Arm arm;

	LogitechGamepadController gamepadDriver;
	LogitechGamepadController gamepadOperator;

	@Override
	public void robotInit() {
		super.robotInit();
		
		long initTime = System.currentTimeMillis();
		
		CameraServer.getInstance().startAutomaticCapture();

		drivetrain = new Drivetrain();
		intake = new Intake();
		arm = new Arm();
		
		gamepadDriver = new LogitechGamepadController(1);
		gamepadOperator = new LogitechGamepadController(2);
		
		registerSubsystem("drivetrain", drivetrain);
		registerSubsystem("intake", intake);
		registerSubsystem("arm", arm);
		
		setPowerDistributionPanel(new PowerDistributionPanel());
		
		// Set up logging warnings, information, etc. to text file.
		try {
			File textFile = new File("/logs/messages/" + initTime + ".txt");
			textFile.createNewFile();
			
			setMessageLogger(new TextFileMessageLogger(textFile));
		} catch (Exception e) {
			System.err.println("Unable to log messages to text file!");
			e.printStackTrace();
		}
		
		// Set up logging robot state to a CSV file.
		try {
			File csvFile = new File("/logs/state/" + initTime + ".csv");
			csvFile.createNewFile();
			
			setStateLogger(new CSVStateLogger(csvFile));
		} catch (Exception e) {
			System.err.println("Unable to log state to csv!");
			e.printStackTrace();
		}
		
		// Initialize autonomous scripting system.
		try {
			initAutoScripts(new File("/key/pub.key"), new File("/scripts"));
		} catch (Exception e) {
			System.err.println("Unable to initalize auto script!s");
			e.printStackTrace();
		}
	}

	@Override
	public void autonomousInit() {
		super.autonomousInit();
		drivetrain.resetGyro();
	}

	@Override
	public void autonomousPeriodic() {
		super.autonomousPeriodic();
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
		operateIntake();
	}

	private void operateDrivetrain() {
		drivetrain.arcadeDrive(gamepadDriver.getLeftY(), gamepadDriver.getRightX());

		//Driver start button, nerfs the speed
		if (gamepadDriver.getRawButtonPressed(gamepadDriver.START_BUTTON)) {
			drivetrain.nerfSpeed();
		}

		//Driver A button, switches forwards and backwards
		if (gamepadDriver.getRawButtonPressed(gamepadDriver.A_BUTTON)) {
			drivetrain.reverseDriveDirection();
		}
	}

	private void operateArm() {
		// Operator Y button, sets position of switch to top
		if (gamepadOperator.getRawButtonPressed(gamepadOperator.Y_BUTTON)) {
			arm.setTargetPosition(Position.TOP);
		}
		// Operator X/B button, sets position of switch to middle
		else if (gamepadOperator.getRawButtonPressed(gamepadOperator.X_BUTTON) ||
				gamepadOperator.getRawButtonPressed(gamepadOperator.B_BUTTON)) {
			arm.setTargetPosition(Position.MIDDLE);
		}
		// Operator A button, sets position of switch to bottom
		else if (gamepadOperator.getRawButtonPressed(gamepadOperator.A_BUTTON)) {
			arm.setTargetPosition(Position.BOTTOM);
		}

		arm.update();
	}

	private void operateIntake() {
		// Bind intake state to operator buttons
		if (gamepadOperator.getRawButtonPressed(gamepadOperator.LEFT_STICK_IN)) {
			intake.setState(State.TURN);
		} else if (Math.abs(gamepadOperator.getLeftX()) > .5) {
			intake.setState(State.STOP);
		} else if (gamepadOperator.getLeftY() < -.5) {
			intake.setState(State.SHOOT);
		} else if (gamepadOperator.getLeftY() > .5) {
			intake.setState(State.INTAKE);
		}

		intake.update();
	}

	@Override
	public void testInit() {
		super.testInit();
	}

	@Override
	public void testPeriodic() {
		super.testPeriodic();
	}
}
