package org.usfirst.frc.team6844.robot;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.uvstem.borg.BorgSubsystem;
import org.uvstem.borg.logging.Message;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake extends BorgSubsystem{

	TalonSRX talon_intake_left, talon_intake_right;

	public Intake() {
		talon_intake_left = new TalonSRX(4);
		talon_intake_right = new TalonSRX(6);
		talon_intake_right.setInverted(true);
	}

	public void intakeIn() {
		talon_intake_left.set(ControlMode.PercentOutput, 1);
		talon_intake_right.set(ControlMode.PercentOutput, 1);
	}

	public void intakeOut() {
		talon_intake_left.set(ControlMode.PercentOutput, -1);
		talon_intake_right.set(ControlMode.PercentOutput, -1);
	}

	public void stopIntake() {
		talon_intake_left.set(ControlMode.PercentOutput, 0);
		talon_intake_right.set(ControlMode.PercentOutput, 0);
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
