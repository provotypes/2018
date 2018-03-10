package org.uvstem.borg.mechanisms.motors;

public class Borg1CIM1MiniCIMMotor extends BorgMotor {

	@Override
	public double getFreeSpeedRPMs() {
		return 5508;
	}

	@Override
	public double getStallTorqueNMs() {
		return 3.82;
	}

	@Override
	public double getStallCurrentAmps() {
		return 220;
	}

	@Override
	public double getFreeCurrentAmps() {
		return 8.16;
	}

}
