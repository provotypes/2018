package org.usfirst.frc.team6844.robot;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.uvstem.borg.BorgSubsystem;
import org.uvstem.borg.logging.Message;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Victor;

public class Drivetrain extends BorgSubsystem {
	
	Victor victor_right1, victor_right2;
	TalonSRX ct_left1, ct_left2;
	Spark spark_left1, spark_left2, spark_right1, spark_right2;
	AnalogGyro gyro;
	
	public Drivetrain() {
		spark_left1 = new Spark(0);
		spark_left2 = new Spark(1);
		spark_right1 = new Spark(2);
		spark_right2 = new Spark(3);
		
		spark_right1.setInverted(true);
		spark_right2.setInverted(true);
		
		gyro = new AnalogGyro(0);
		
	}
	
	public void tankDrive(double left, double right) {
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

}
