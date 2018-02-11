package org.usfirst.frc.team6844.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake {

	private TalonSRX motorLeft = new TalonSRX(4);
	private TalonSRX motorRight = new TalonSRX(6);
		
	public enum State {
		INTAKE,
		TURN,
		SHOOT,
		STOP
	}

	public Intake() {
		motorLeft.setInverted(true);
	}
	
	public void update(State state) {
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
}
