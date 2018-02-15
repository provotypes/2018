package org.uvstem.borg.sensors;

import edu.wpi.first.wpilibj.DigitalInput;

public class BorgDigitalInput extends DigitalInput {

	boolean inverted = false;

	public BorgDigitalInput(int channel) {
		super(channel);
	}

	public void invert() {
		inverted = !inverted;
	}

	@Override
	public boolean get() {
		if (inverted) {
			return !super.get();
		} else {
			return super.get();
		}
	}
}
