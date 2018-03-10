package org.uvstem.borg.mechanisms.motors;

public class BorgMiniCIMMotor extends BorgMotor {

	@Override
	public double getFreeSpeedRPMs() {
		return 5840;
	}

	@Override
	public double getStallTorqueNMs() {
		return 1.41;
	}

	@Override
	public double getStallCurrentAmps() {
		return 89;
	}

	@Override
	public double getFreeCurrentAmps() {
		return 3;
	}

}
