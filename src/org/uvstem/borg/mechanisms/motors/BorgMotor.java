package org.uvstem.borg.mechanisms.motors;

public abstract class BorgMotor {
	private static final double NEWTON_METERS_TO_FT_LBS = 0.73756;
	
	public abstract double getFreeSpeedRPMs();
	public abstract double getStallTorqueNMs();
	public abstract double getStallCurrentAmps();
	public abstract double getFreeCurrentAmps();
	
	public double getStallTorqueFtLbs() {
		return this.getStallTorqueNMs() * NEWTON_METERS_TO_FT_LBS;
	}
	
	public double getMaxPowerWatts() {
		return Math.PI / 240 * this.getFreeSpeedRPMs() * this.getStallTorqueNMs();
	}
}
