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
		
		ct_left1.setInverted(true);
		ct_left2.setInverted(true);
	}
	
	public void tankDrive(double left, double right) {
		ct_left1.set(ControlMode.PercentOutput, left);
		ct_left2.set(ControlMode.PercentOutput, left);
		
		victor_right1.set(right);
		victor_right2.set(right);
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
