package org.uvstem.borg.mechanisms.motors;

public class BorgBAGMotor extends BorgMotor {

	@Override
	public double getFreeSpeedRPMs() {
		return 13180;
	}

	@Override
	public double getStallTorqueNMs() {
		return .43;
	}

	@Override
	public double getStallCurrentAmps() {
		return 53;
	}

	@Override
	public double getFreeCurrentAmps() {
		return 1.8;
	}

}
