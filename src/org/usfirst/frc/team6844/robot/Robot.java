/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6844.robot;

import java.io.File;

import org.usfirst.frc.team6844.robot.Arm.Position;
import org.uvstem.borg.BorgRobot;
import org.uvstem.borg.joysticks.LogitechGamepadController;
import org.uvstem.borg.logging.CSVStateLogger;
import org.uvstem.borg.logging.TextFileMessageLogger;

import edu.wpi.cscore.CameraServerJNI;
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
		//setPowerDistributionPanel(new PowerDistributionPanel(0));

		try {
			setStateLogger(new CSVStateLogger(new File("/logs/log.csv")));
			setMessageLogger(new TextFileMessageLogger(new File("/logs/log.txt")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		CameraServer.getInstance().startAutomaticCapture();

		drivetrain = new Drivetrain();
		registerSubsystem("drivetrain", drivetrain);

		intake = new Intake();
		registerSubsystem("intake", intake);

		arm = new Arm();
		registerSubsystem("arm", arm);

		gamepadDriver = new LogitechGamepadController(1);
		gamepadOperator = new LogitechGamepadController(2);

		try {
			initPublicKey(new File("/key/key.pub"));
			initAutoScripts(new File("/home/lvuser/auto"));
		} catch (Exception e) {
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
		super.teleopPeriodic();
	}

	@Override
	public void teleopPeriodic() {
		super.teleopPeriodic();
		drivetrain.arcadeDrive(gamepadDriver.getRightY(), gamepadDriver.getLeftX(), true);
		
		// Operator Y button, sets position of switch to top
		if (gamepadOperator.getRawButtonPressed(gamepadOperator.Y_BUTTON)) {
			arm.setPosition(Position.TOP);
		}
		
		// Operator X/B button, sets position of switch to middle
		if (gamepadOperator.getRawButtonPressed(gamepadOperator.X_BUTTON) || 
				gamepadOperator.getRawButtonPressed(gamepadOperator.B_BUTTON)) {
			arm.setPosition(Position.MIDDLE);
		}
		
		// Operator A button, sets position of switch to bottom
		if (gamepadOperator.getRawButtonPressed(gamepadOperator.A_BUTTON)) {
			arm.setPosition(Position.BOTTOM);
		}
		
		//Driver start button, nerfs the speed
		if (gamepadDriver.getRawButtonPressed(gamepadDriver.START_BUTTON)) {
			drivetrain.nerfSpeed();
		}

		//Driver A button, switches forwards and backwards
		if (gamepadDriver.getRawButtonPressed(gamepadDriver.A_BUTTON)) {
			drivetrain.reverseDriveDirection();
		}

		//Operator left bumper, intake out
		//Operator right bumper, intake in
		if (gamepadOperator.getRightBumper()) {
			intake.intakeIn();
		} else if (gamepadOperator.getLeftBumper()){
		    intake.intakeOut();
		} else {
		    intake.stopIntake();
		}
		
		arm.update();
		intake.update();
	}

	@Override
	public void testInit() {
		super.testInit();

		try {
			initPublicKey(new File("/home/lvuser/key.pub"));
			initAutoScripts(new File("/home/lvuser/auto"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void testPeriodic() {
		super.testPeriodic();
	}
}
