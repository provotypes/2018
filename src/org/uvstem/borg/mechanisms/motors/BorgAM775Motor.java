package org.uvstem.borg.mechanisms.motors;

public class BorgAM775Motor extends BorgMotor {

	@Override
	public double getFreeSpeedRPMs() {
		return 5800;
	}

	@Override
	public double getStallTorqueNMs() {
		return .28;
	}

	@Override
	public double getStallCurrentAmps() {
		return 18;
	}

	@Override
	public double getFreeCurrentAmps() {
		return 1.6;
	}

}
