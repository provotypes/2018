package org.uvstem.borg.joysticks;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Describes a Logitech Gamepad Controller.  This type of controller is used
 * for driver control, and it simplifies and abstracts the raw joystick.
 */
public class LogitechGamepadController extends Joystick {
    
    public LogitechGamepadController(int pos) {
        super(pos);
    }
    
    public boolean getAButton() {
        return super.getRawButton(1);
    }
    
    public boolean getBButton() {
        return super.getRawButton(2);
    }
    
    public boolean getXButton() {
        return super.getRawButton(3);
    }
    
    public boolean getYButton() {
        return super.getRawButton(4);
    }
    
    public boolean getLeftBumper() {
        return super.getRawButton(5);
    }
    
    public boolean getRightBumper() {
        return super.getRawButton(6);
    }
    
    public boolean getStart() {
        return super.getRawButton(8);
    }
    
    public double getLeftX() {
        return super.getRawAxis(0);
    }
    
    public double getLeftY() {
        return super.getRawAxis(1);
    }
    
    public double getLeftTrigger() {
        return super.getRawAxis(2);
    }
    
    public double getRightX() {
        return super.getRawAxis(4);
    }
    
    public double getRightY() {
        return super.getRawAxis(5);
    }
    
    public double getRightTrigger() {
        return super.getRawAxis(3);
    }
}