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
	
	Victor victor_right, victor_left;
	TalonSRX ct_right, ct_left;
	
	public Drivetrain() {
		victor_right = new Victor(0);
		victor_left = new Victor(1);
		
		ct_right = new TalonSRX(1);
		ct_left = new TalonSRX(2);
	}
	
	public void tankDrive(double left, double right) {
		victor_right.set(right);
		ct_right.set(ControlMode.PercentOutput, right);
		
		victor_left.set(left);
		ct_left.set(ControlMode.PercentOutput, left);
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

}
