package org.usfirst.frc.team6844.robot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.uvstem.borg.BorgSubsystem;
import org.uvstem.borg.logging.Message;
import org.uvstem.borg.logging.Message.Type;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake extends BorgSubsystem {

	private TalonSRX motorLeft = new TalonSRX(4);
	private TalonSRX motorRight = new TalonSRX(6);

	private State state = State.STOP;

	public enum State {
		INTAKE,
		TURN,
		SHOOT,
		STOP
	}

	public Intake() {
		motorLeft.setInverted(true);
	}

	public void setState(State state) {
		if (this.state != state) {
			messageBuffer.add(new Message("Setting intake state to " + getNameForState(state), Type.INFO));
			this.state = state;
		}
	}

	@Override
	public void update() {
		switch(state) {
			case INTAKE:
				motorLeft.set(ControlMode.PercentOutput, .5);
				motorRight.set(ControlMode.PercentOutput, .6);
				break;

			case TURN:
				motorLeft.set(ControlMode.PercentOutput, .4);
				motorRight.set(ControlMode.PercentOutput, -.4);
				break;

			case SHOOT:
				motorLeft.set(ControlMode.PercentOutput, -.4);
				motorRight.set(ControlMode.PercentOutput, -.4);
				break;

			case STOP:
				motorLeft.set(ControlMode.PercentOutput, .1);
				motorRight.set(ControlMode.PercentOutput, .1);
				break;
		}
	}

	@Override
	public Set<String> getStateLogFields() {
		Set<String> fields = new HashSet<>();
		
		fields.add("state");
		fields.add("motorLeftPercentOutput");
		fields.add("motorRightPercentOutput");
		
		return fields;
	}

	@Override
	public Map<String, Object> logState() {
		Map<String, Object> state = new HashMap<>();
		
		state.put("state", getNameForState(this.state));
		state.put("motorLeftPercentOutput", motorLeft.getMotorOutputPercent());
		state.put("motorRightPercentOutput", motorRight.getMotorOutputPercent());
		
		return state;
	}
	
	private String getNameForState(State state) {
		switch (state) {
		case INTAKE:
			return "intake";
		case TURN:
			return "turn";
		case SHOOT:
			return "shoot";
		case STOP:
			return "stop";
		}
		
		return null;
	}
}
