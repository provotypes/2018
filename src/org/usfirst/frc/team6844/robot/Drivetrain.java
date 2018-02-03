package org.usfirst.frc.team6844.robot;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.uvstem.borg.BorgSubsystem;
import org.uvstem.borg.logging.Message;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.Spark;

public class Drivetrain extends BorgSubsystem {
	
	Spark spark_left1, spark_left2, spark_right1, spark_right2;
	TalonSRX talon_arm;
	ADXRS450_Gyro gyro;
	Encoder encoder;
	
	private double driveScalingFactor = 1;
	
	public Drivetrain() {
		spark_left1 = new Spark(0);
		spark_left2 = new Spark(1);
		spark_right1 = new Spark(2);
		spark_right2 = new Spark(3);
		
		spark_right1.setInverted(true);
		spark_right2.setInverted(true);
		
		talon_arm = new TalonSRX(-1); //port unknown
		
		gyro = new ADXRS450_Gyro(Port.kOnboardCS0);	
		encoder = new Encoder(0, 1);
	}
	
	public void tankDrive(double left, double right) {
		left *= driveScalingFactor;
		right *= driveScalingFactor;
		
		spark_left1.set(left);
		spark_left2.set(left);
		
		spark_right1.set(right);
		spark_right2.set(right);
	}
	
	public void arcadeDrive(double speed, double turn) {
		tankDrive(speed + turn, speed - turn);
	}
	
	public void arcadeDrive(double speed, double turn, boolean squareInputs) {
		arcadeDrive(sign(speed) * Math.pow(speed, 2), sign(turn) * Math.pow(turn, 2));
	}

	@Override
	public List<Message> logMessages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getStateLogFields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> logState() {
		// TODO Auto-generated method stub
		return null;
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
		spark_left1.setInverted(!spark_left1.getInverted());
		spark_left2.setInverted(!spark_left2.getInverted());
		
		spark_right1.setInverted(!spark_right1.getInverted());
		spark_right2.setInverted(!spark_right2.getInverted());
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
