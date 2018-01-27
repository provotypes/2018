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
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Robot extends BorgRobot {
	
	Drivetrain drivetrain;
	LogitechGamepadController gamepad;
	AnalogGyro gyro;

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
		
		gamepad = new LogitechGamepadController(1);
		gyro = new AnalogGyro(0);
		
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
		
		gyro.reset();
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
		drivetrain.arcadeDrive(gamepad.getRightY(), gamepad.getLeftX(), true);
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
