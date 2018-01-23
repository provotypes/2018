package org.uvstem.borg.joysticks;

import edu.wpi.first.wpilibj.Joystick;

public class LogitechExtreme3DProFlightstick extends Joystick {

	public LogitechExtreme3DProFlightstick(int port) {
		super(port);
	}
	
	public double getStickX() {
		return super.getRawAxis(0);
	}
	
	public double getStickY() {
		return super.getRawAxis(1);
	}
	
	public double getStickRotate() {
		return super.getRawAxis(2);
	}
	
	public double getSlider() {
		return -super.getRawAxis(3);
	}
	
	public boolean getTrigger() {
		return super.getRawButton(1);
	}
	
	public boolean getThumbButton() {
		return super.getRawButton(2);
	}
	
	public boolean getButton3() {
		return super.getRawButton(3);
	}
	
	public boolean getButton4() {
		return super.getRawButton(4);
	}

	public boolean getButton5() {
		return super.getRawButton(5);
	}

	public boolean getButton6() {
		return super.getRawButton(6);
	}

	public boolean getButton7() {
		return super.getRawButton(7);
	}

	public boolean getButton8() {
		return super.getRawButton(8);
	}

	public boolean getButton9() {
		return super.getRawButton(9);
	}

	public boolean getButton10() {
		return super.getRawButton(10);
	}
}
