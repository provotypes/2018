package org.uvstem.borg.mechanisms.motors;

public class Borg1CIM2MiniCIMMotor extends BorgMotor {

	@Override
	public double getFreeSpeedRPMs() {
		return 5593;
	}

	@Override
	public double getStallTorqueNMs() {
		return 5.23;
	}

	@Override
	public double getStallCurrentAmps() {
		return 309;
	}

	@Override
	public double getFreeCurrentAmps() {
		return 9.62;
	}

}
