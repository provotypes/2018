package org.usfirst.frc.team6844.robot;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.uvstem.borg.BorgSubsystem;
import org.uvstem.borg.logging.Message;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

public class Arm extends BorgSubsystem{

	private TalonSRX armMotor = new TalonSRX(5);
	private DigitalInput bottomSwitch = new DigitalInput(0);
	private DigitalInput topSwitch = new DigitalInput(1);

	private int counter = 0;

	private Position position = Position.BOTTOM;
	private Position previousPosition = Position.BOTTOM;

	private final int TICKS_UP = 25;
	private final int TICKS_DOWN = 50 - TICKS_UP;

	private final double TRAVEL_UP_OUTPUT = .6;
	private final double TRAVEL_DOWN_OUTPUT = -.4;

	private final double TOP_HOLDING_OUTPUT = .2;
	private final double MIDDLE_HOLDING_OUTPUT = .1;
	private final double BOTTOM_HOLDING_OUTPUT = -.2;

	enum Position {
			TOP,
			MIDDLE,
			BOTTOM
	}

	public void setPosition(Position position) {
		if (position != this.position) {
			this.previousPosition = this.position;
			this.position = position;
		}
	}

	public void update() {
			switch (position) {
				case TOP:
					counter = 0;
					
					if (!topSwitch.get()) {
						armMotor.set(ControlMode.PercentOutput, TRAVEL_UP_OUTPUT);
					} else {
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
						break;
					} else {
						if (counter < TICKS_DOWN) {
							armMotor.set(ControlMode.PercentOutput, TRAVEL_DOWN_OUTPUT);
						} else {
							armMotor.set(ControlMode.PercentOutput, MIDDLE_HOLDING_OUTPUT);
						}
					}
					
					counter++;
					break;

				case BOTTOM:
					counter = 0;
					
					if (!bottomSwitch.get()) {
						armMotor.set(ControlMode.PercentOutput, TRAVEL_DOWN_OUTPUT);
					} else {
						armMotor.set(ControlMode.PercentOutput, BOTTOM_HOLDING_OUTPUT);
					}
					break;
				}
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
