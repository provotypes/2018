package org.usfirst.frc.team6844.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.Spark;

public class Drivetrain {

	Spark sparkLeft1, sparkLeft2, sparkRight1, sparkRight2;
	ADXRS450_Gyro gyro;

	private double driveScalingFactor = 1;

	public Drivetrain() {
		sparkLeft1 = new Spark(0);
		sparkLeft2 = new Spark(1);
		sparkRight1 = new Spark(2);
		sparkRight2 = new Spark(3);

		sparkLeft1.setInverted(true);
		sparkLeft2.setInverted(true);

		gyro = new ADXRS450_Gyro(Port.kOnboardCS0);
	}

	public void tankDrive(double left, double right) {
		left *= driveScalingFactor;
		right *= driveScalingFactor;

		sparkLeft1.set(left);
		sparkLeft2.set(left);

		sparkRight1.set(right);
		sparkRight2.set(right);
	}

	public void arcadeDrive(double speed, double turn) {
		tankDrive(speed - turn, speed + turn);
	}

	public void arcadeDrive(double speed, double turn, boolean squareInputs) {
		arcadeDrive(sign(speed) * Math.pow(speed, 2), sign(turn) * Math.pow(turn, 2));
	}

	private double sign(double magnitude) {
		if (magnitude > 0) {
			return 1;
		} else if (magnitude < 0) {
			return -1;
		} else {
			return 0;
		}
	}

	public void resetGyro() {
		gyro.reset();
	}

	public void calibrateGyro() {
		gyro.calibrate();
	}

	public void reverseDriveDirection() {
		sparkLeft1.setInverted(!sparkLeft1.getInverted());
		sparkLeft2.setInverted(!sparkLeft2.getInverted());

		sparkRight1.setInverted(!sparkRight1.getInverted());
		sparkRight2.setInverted(!sparkRight2.getInverted());
	}

	public void setScalingFactor(double scale) {
		driveScalingFactor = scale;
	}

	public double getScalingFactor() {
		return driveScalingFactor;
	}

	public void nerfSpeed() {
		if (driveScalingFactor == 1) {
			driveScalingFactor = .5;
		} else if (driveScalingFactor == .5){
			driveScalingFactor = 1;
		}
	}
}
