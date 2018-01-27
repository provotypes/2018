package org.usfirst.frc.team6844.robot;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.uvstem.borg.BorgSubsystem;
import org.uvstem.borg.logging.Message;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Victor;

public class Drivetrain extends BorgSubsystem {
	
	Victor victor_right1, victor_right2;
	TalonSRX ct_left1, ct_left2;
	
	public Drivetrain() {
		victor_right1 = new Victor(0);
		victor_right2 = new Victor(1);
		
		ct_left1 = new TalonSRX(1);
		ct_left2 = new TalonSRX(2);
		
		victor_right1.setInverted(true);
		victor_right2.setInverted(true);
		
	}
	
	public void tankDrive(double left, double right) {
		ct_left1.set(ControlMode.PercentOutput, left);
		ct_left2.set(ControlMode.PercentOutput, left);
		
		victor_right1.set(right);
		victor_right2.set(right);
		
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

}
