package org.uvstem.borg.mechanisms.motors;

public class BorgGearbox extends BorgMotor {
	final BorgMotor motor;
	final double efficiency;
	final double ratio;
	
	public BorgGearbox(BorgMotor motor, double efficiency, double ratio) {
		this.motor = motor;
		this.efficiency = efficiency;
		this.ratio = ratio;
	}
	
	public double getEfficiency() {
		return this.efficiency;
	}
	
	public double getRatio() {
		return this.ratio;
	}
	
	@Override
	public double getFreeSpeedRPMs() {
		return this.motor.getFreeSpeedRPMs() * Math.sqrt(this.efficiency) / this.ratio;
	}
	
	@Override
	public double getStallTorqueNMs() {
		return this.motor.getStallTorqueNMs() * Math.sqrt(this.efficiency) * this.ratio;
	}
	
	@Override
	public double getStallCurrentAmps() {
		return this.motor.getStallCurrentAmps();
	}
	
	@Override
	public double getFreeCurrentAmps() {
		return this.motor.getFreeCurrentAmps();
	}
}
