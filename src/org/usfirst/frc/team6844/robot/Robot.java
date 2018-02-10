/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6844.robot;

import java.io.File;

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
	LogitechGamepadController gamepad_driver;
	LogitechGamepadController gamepad_operator;

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

		gamepad_driver = new LogitechGamepadController(1);
		gamepad_operator = new LogitechGamepadController(2);

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
		drivetrain.encoder.setDistancePerPulse(DISTANCE_PER_PULSE);
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
		drivetrain.arcadeDrive(gamepad_driver.getRightY(), gamepad_driver.getLeftX(), true);

		//System.out.println(drivetrain.gyro.getAngle());

		//Operator right stick, controls arm
		//if bottom limit switch is pressed and arm is going down, stop arm
		if (arm.getBottomSwitchPressed() && (gamepad_operator.getRightY() * -1) < 0) {
		    arm.operateArm(0);

		//if bottom limit switch is pressed and arm is going down, stop arm
		} else if (arm.getTopSwitchPressed() && (gamepad_operator.getRightY() * -1) > 0){
		    arm.operateArm(0);

		//if bottom limit switch is not pressed, or arm is going up, allow the input
		} else {
		    arm.operateArm(-1 * gamepad_operator.getRightY());
		}

		//Driver start button, nerfs the speed
		if (gamepad_driver.getRawButtonPressed(gamepad_driver.START_BUTTON)) {
			drivetrain.nerfSpeed();
		}

		//Driver A button, switches forwards and backwards
		if (gamepad_driver.getRawButtonPressed(gamepad_driver.A_BUTTON)) {
			drivetrain.reverseDriveDirection();
		}

		//Operator left bumper, intake out
		//Operator right bumper, intake in
		if (gamepad_operator.getRightBumper()) {
			intake.intakeIn();
		} else if (gamepad_operator.getLeftBumper()){
		    intake.intakeOut();
		} else {
		    intake.stopIntake();
		}
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
