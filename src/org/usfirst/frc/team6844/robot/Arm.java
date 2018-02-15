package org.usfirst.frc.team6844.robot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.uvstem.borg.BorgSubsystem;
import org.uvstem.borg.logging.Message;
import org.uvstem.borg.logging.Message.Type;
import org.uvstem.borg.sensors.BorgDigitalInput;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Arm extends BorgSubsystem {

	private TalonSRX armMotor = new TalonSRX(5);
	private BorgDigitalInput bottomSwitch = new BorgDigitalInput(1);
	private BorgDigitalInput topSwitch = new BorgDigitalInput(0);

	private int counter = 0;

	private Position position = Position.BOTTOM;
	private Position previousPosition = Position.BOTTOM;

	private final int TICKS_UP = 60;
	private final int TICKS_DOWN = 150 - TICKS_UP;
	private final int TICKS_UP_DOWN = 3 * (TICKS_UP + TICKS_DOWN);

	private final double TRAVEL_UP_OUTPUT = .6;
	private final double TRAVEL_DOWN_OUTPUT = -.4;

	private final double TOP_HOLDING_OUTPUT = .2;
	private final double MIDDLE_HOLDING_OUTPUT = .1;
	private final double BOTTOM_HOLDING_OUTPUT = -.2;
	
	private boolean timedOut = false;
	
	public Arm() {
		bottomSwitch.invert();
		topSwitch.invert();
	}

	enum Position {
		TOP,
		MIDDLE,
		BOTTOM
	}

	public void setTargetPosition(Position position) {
		if (position != this.position) {
			messageBuffer.add(new Message("Setting arm position to " + getNameForPosition(position), Type.INFO));
			
			counter = 0;
			
			this.previousPosition = this.position;
			this.position = position;
			this.timedOut = false;
		}
	}

	@Override
	public void update() {
		counter++; //At 50Hz, the counter will overflow in about 1.3 years.

		switch (position) {
			case TOP:
				if (!topSwitch.get() && counter < TICKS_UP_DOWN) {
					armMotor.set(ControlMode.PercentOutput, TRAVEL_UP_OUTPUT);
				} else {
					if (!topSwitch.get() && counter >= TICKS_UP_DOWN && !timedOut) {
						timedOut = true;
						messageBuffer.add(new Message("Moving arm to top timed out.", Type.WARNING));
					}
					armMotor.set(ControlMode.PercentOutput, TOP_HOLDING_OUTPUT);
				}

				break;

			case MIDDLE:
				if (previousPosition == Position.BOTTOM) {
					if (counter < TICKS_UP) {
						armMotor.set(ControlMode.PercentOutput, TRAVEL_UP_OUTPUT);
					} else {
						armMotor.set(ControlMode.PercentOutput, MIDDLE_HOLDING_OUTPUT);
					}
				} else  if (previousPosition == Position.TOP){
					if (counter < TICKS_DOWN) {
						armMotor.set(ControlMode.PercentOutput, TRAVEL_DOWN_OUTPUT);
					} else {
						armMotor.set(ControlMode.PercentOutput, MIDDLE_HOLDING_OUTPUT);
					}
				}

				break;

			case BOTTOM:
				if (!bottomSwitch.get() && counter < TICKS_UP_DOWN) {
					armMotor.set(ControlMode.PercentOutput, TRAVEL_DOWN_OUTPUT);
				} else {
					if (!bottomSwitch.get() && counter >= TICKS_UP_DOWN && !timedOut) {
						timedOut = true;
						messageBuffer.add(new Message("Moving arm to bottom timed out.", Type.WARNING));
					}
					armMotor.set(ControlMode.PercentOutput, BOTTOM_HOLDING_OUTPUT);
				}

				break;
		}
	}

	@Override
	public Set<String> getStateLogFields() {
		Set<String> fields = new HashSet<>();
		
		fields.add("position");
		fields.add("armMotorPercentOutput");
		fields.add("topSwitch");
		fields.add("bottomSwitch");
		fields.add("counterValue");
		
		return fields;
	}

	@Override
	public Map<String, Object> logState() {
		Map<String, Object> state = new HashMap<>();
		
		state.put("position", getNameForPosition(position));
		state.put("armMotorPercentOutput", armMotor.getMotorOutputPercent());
		state.put("topSwitch", topSwitch.get());
		state.put("bottomSwitch", bottomSwitch.get());
		state.put("counterValue", counter);
		
		return state;
	}
	
	private String getNameForPosition(Position position) {
		switch(position) {
			case TOP:
				return "top";
			case MIDDLE:
				return "middle";
			case BOTTOM:
				return "bottom";
		}
		
		return null;
	}
}
