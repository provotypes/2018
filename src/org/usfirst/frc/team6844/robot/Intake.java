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

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Intake extends BorgSubsystem {

	private TalonSRX motorLeft = new TalonSRX(4);
	private TalonSRX motorRight = new TalonSRX(6);
	private DoubleSolenoid extenderLeft = new DoubleSolenoid(0, 1);
	private DoubleSolenoid extenderRight = new DoubleSolenoid(4, 5);

	private State state = State.STOP;
	private ArmState armLeft = ArmState.RETRACTED;
	private ArmState armRight = ArmState.RETRACTED;

	public enum State {
		INTAKE,
		SHOOT,
		STOP
	}
	
	public enum ArmState {
		EXTENDED,
		RETRACTED
	}

	public Intake() {
		super();
		motorLeft.setInverted(true);
	}

	public void setState(State state) {
		if (this.state != state) {
			messageBuffer.add(new Message("Setting intake state to " + getNameForState(state), Type.INFO));
			this.state = state;
		}
	}

	public void intake() {
		setState(State.INTAKE);
	}

	public void shoot() {
		setState(State.SHOOT);
	}

	public void stop() {
		setState(State.STOP);
	}

	@Override
	public void update() {
		switch(state) {
			case INTAKE:
				motorLeft.set(ControlMode.PercentOutput, .5);
				motorRight.set(ControlMode.PercentOutput, .6);
				break;

			case SHOOT:
				motorLeft.set(ControlMode.PercentOutput, -1);
				motorRight.set(ControlMode.PercentOutput, -1);
				break;

			case STOP:
				armLeft = ArmState.RETRACTED;
				armRight = ArmState.RETRACTED;
				motorLeft.set(ControlMode.PercentOutput, .1);
				motorRight.set(ControlMode.PercentOutput, .1);
				break;
		}
		
		switch (armLeft) {
			case EXTENDED:
				extenderLeft.set(Value.kForward);
				break;
			
			case RETRACTED: 
				extenderLeft.set(Value.kReverse);
				break;
		}
		
		switch (armRight) {
			case EXTENDED:
				extenderRight.set(Value.kForward);
				break;
			
			case RETRACTED: 
				extenderRight.set(Value.kReverse);
				break;
		}
	}

	@Override
	public Set<String> getStateLogFields() {
		Set<String> fields = new HashSet<>();

		fields.add("state");
		fields.add("motorLeftPercentOutput");
		fields.add("motorRightPercentOutput");
		fields.add("extenderValue");

		return fields;
	}

	@Override
	public Map<String, Object> logState() {
		Map<String, Object> state = new HashMap<>();

		state.put("state", getNameForState(this.state));
		state.put("armLeft", armLeft.toString());
		state.put("armRight", armRight.toString());
		state.put("motorLeftPercentOutput", motorLeft.getMotorOutputPercent());
		state.put("motorRightPercentOutput", motorRight.getMotorOutputPercent());
		state.put("extenderValue", extenderLeft.get());

		return state;
	}

	private String getNameForState(State state) {
		switch (state) {
		case INTAKE:
			return "intake";
		case SHOOT:
			return "shoot";
		case STOP:
			return "stop";
		}

		return null;
	}

	public void setLeftArm(ArmState state) {
		armLeft = state;
	}

	public void setRightArm(ArmState state) {
		armRight = state;
	}
}
