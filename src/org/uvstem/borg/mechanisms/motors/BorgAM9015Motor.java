package org.uvstem.borg.mechanisms.motors;

public class BorgAM9015Motor extends BorgMotor {

	@Override
	public double getFreeSpeedRPMs() {
		return 14270;
	}

	@Override
	public double getStallTorqueNMs() {
		return .36;
	}

	@Override
	public double getStallCurrentAmps() {
		return 71;
	}

	@Override
	public double getFreeCurrentAmps() {
		return 3.7;
	}

}
