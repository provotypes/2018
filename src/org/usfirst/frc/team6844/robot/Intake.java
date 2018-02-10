package org.usfirst.frc.team6844.robot;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.uvstem.borg.BorgSubsystem;
import org.uvstem.borg.logging.Message;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake extends BorgSubsystem {

	private TalonSRX motorLeft = new TalonSRX(4);
	private TalonSRX motorRight = new TalonSRX(6);
	
	private State state = State.STOP;
	
	public enum State {
		INTAKE,
		SHOOT,
		STOP
	}

	public Intake() {
		motorRight.setInverted(true);
	}
	
	public void update() {
		switch(state) {
		case INTAKE:
			motorLeft.set(ControlMode.PercentOutput, .8);
			motorRight.set(ControlMode.PercentOutput, 1);
			break;
		case SHOOT:
			motorLeft.set(ControlMode.PercentOutput, -1);
			motorRight.set(ControlMode.PercentOutput, -1);
			break;
		case STOP:
			motorLeft.set(ControlMode.PercentOutput, 0);
			motorRight.set(ControlMode.PercentOutput, 0);
		}
	}

	public void intakeIn() {
		this.state = State.INTAKE;
	}

	public void intakeOut() {
		this.state = State.SHOOT;
	}

	public void stopIntake() {
		this.state = State.STOP;
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
