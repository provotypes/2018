package org.uvstem.borg.mechanisms.motors;

public class Borg775ProMotor extends BorgMotor {

	@Override
	public double getFreeSpeedRPMs() {
		return 18730;
	}

	@Override
	public double getStallTorqueNMs() {
		return .71;
	}

	@Override
	public double getStallCurrentAmps() {
		return 134;
	}

	@Override
	public double getFreeCurrentAmps() {
		return .7;
	}

}
