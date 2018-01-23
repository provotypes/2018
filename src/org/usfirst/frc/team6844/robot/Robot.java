/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6844.robot;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import org.uvstem.borg.BorgRobot;
import org.uvstem.borg.joysticks.LogitechGamepadController;
import org.uvstem.borg.logging.CSVStateLogger;
import org.uvstem.borg.logging.TextFileMessageLogger;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Robot extends BorgRobot {
	
	public static double AUTO_SPEED = 0.5;
	public static double DISTANCE_TO_AUTOLINE = 10;
	
	Drivetrain drivetrain;
	LogitechGamepadController gamepad;

	@Override
	public void robotInit() {
		super.robotInit();
		//setPowerDistributionPanel(new PowerDistributionPanel(0));
		
		try {
			setStateLogger(new CSVStateLogger(new File("/home/lvuser/log.csv")));
			setMessageLogger(new TextFileMessageLogger(new File("/home/lvuser/log.txt")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		drivetrain = new Drivetrain();
		registerSubsystem("drivetrain", drivetrain);
		
		gamepad = new LogitechGamepadController(1);
		
		try {
			initPublicKey(new File("/home/lvuser/key.pub"));
			initAutoScripts(new File("/home/lvuser/auto"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void autonomousInit() {
		super.autonomousInit();
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
		drivetrain.tankDrive(gamepad.getLeftY(), gamepad.getRightY());
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
	
	public void driveIfNotOverLine() {
		//get info from sensor, not drivetrain
		if (drivetrain.getDistance() < DISTANCE_TO_AUTOLINE){
			drivetrain.tankDrive(AUTO_SPEED, AUTO_SPEED);
		} else {
			drivetrain.tankDrive(0, 0);
		}
	}
}
