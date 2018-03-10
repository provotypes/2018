package org.uvstem.borg.mechanisms.motors;

public class BorgCIMMotor extends BorgMotor {

	@Override
	public double getFreeSpeedRPMs() {
		return 5330;
	}

	@Override
	public double getStallTorqueNMs() {
		return 2.41;
	}

	@Override
	public double getStallCurrentAmps() {
		return 131;
	}

	@Override
	public double getFreeCurrentAmps() {
		return 2.7;
	}	
}
