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

	private double motorSpeed = 0;

	public Arm() {
		super();
	}

	public void set(double speed) {
		motorSpeed = speed;
	}

	@Override
	public void update() {
		armMotor.set(ControlMode.PercentOutput, motorSpeed);
	}

	@Override
	public Set<String> getStateLogFields() {
		Set<String> fields = new HashSet<>();

		fields.add("armMotorPercentOutput");

		return fields;
	}

	@Override
	public Map<String, Object> logState() {
		Map<String, Object> state = new HashMap<>();

		state.put("armMotorPercentOutput", armMotor.getMotorOutputPercent());

		return state;
	}
	
	public void initDefaultCommand() {}

}
