package org.usfirst.frc.team6844.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

public class Arm {

	private TalonSRX armMotor = new TalonSRX(5);
	private DigitalInput bottomSwitch = new DigitalInput(1);
	private DigitalInput topSwitch = new DigitalInput(0);

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

	enum Position {
		TOP,
		MIDDLE,
		BOTTOM
	}

	public void setTargetPosition(Position position) {
		if (position != this.position) {
			counter = 0;
			this.previousPosition = this.position;
			this.position = position;
		}
	}

	public void update() {
		counter++; //At 50Hz, the counter will overflow in about 1.3 years.

		switch (position) {
			case TOP:
				if (topSwitch.get() && counter < TICKS_UP_DOWN) {
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
				} else  if (previousPosition == Position.TOP){
					if (counter < TICKS_DOWN) {
						armMotor.set(ControlMode.PercentOutput, TRAVEL_DOWN_OUTPUT);
					} else {
						armMotor.set(ControlMode.PercentOutput, MIDDLE_HOLDING_OUTPUT);
					}
				}

				break;

			case BOTTOM:
				if (bottomSwitch.get() && counter < TICKS_UP_DOWN) {
					armMotor.set(ControlMode.PercentOutput, TRAVEL_DOWN_OUTPUT);
				} else {
					armMotor.set(ControlMode.PercentOutput, BOTTOM_HOLDING_OUTPUT);
				}

				break;
		}
	}
}
