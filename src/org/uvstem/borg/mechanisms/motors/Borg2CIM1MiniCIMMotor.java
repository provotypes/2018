package org.uvstem.borg.mechanisms.motors;

public class Borg2CIM1MiniCIMMotor extends BorgMotor {

	@Override
	public double getFreeSpeedRPMs() {
		return 5437;
	}

	@Override
	public double getStallTorqueNMs() {
		return 6.23;
	}

	@Override
	public double getStallCurrentAmps() {
		return 351;
	}

	@Override
	public double getFreeCurrentAmps() {
		return 9.15;
	}

}
