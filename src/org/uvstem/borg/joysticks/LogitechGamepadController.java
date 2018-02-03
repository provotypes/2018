package org.uvstem.borg.joysticks;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Describes a Logitech Gamepad Controller.  This type of controller is used
 * for driver control, and it simplifies and abstracts the raw joystick.
 */
public class LogitechGamepadController extends Joystick {
    
    //Buttons
    public final int A_BUTTON = 1;
    public final int B_BUTTON = 2;
    public final int X_BUTTON = 3;
    public final int Y_BUTTON = 4;
    public final int LEFT_BUMPER = 5;
    public final int RIGHT_BUMPER = 6;
    public final int START_BUTTON = 8;
    
    //Axes
    public final int LEFT_X_AXIS = 0;
    public final int LEFT_Y_AXIS = 1;
    public final int LEFT_TRIGGER = 2;
    public final int RIGHT_X_AXIS = 4;
    public final int RIGHT_Y_AXIS = 5;
    public final int RIGHT_TRIGGER = 3;
    
    public LogitechGamepadController(int pos) {
        super(pos);
    }
    
    public boolean getAButton() {
        return super.getRawButton(A_BUTTON);
    }
    
    public boolean getBButton() {
        return super.getRawButton(B_BUTTON);
    }
    
    public boolean getXButton() {
        return super.getRawButton(X_BUTTON);
    }
    
    public boolean getYButton() {
        return super.getRawButton(Y_BUTTON);
    }
    
    public boolean getLeftBumper() {
        return super.getRawButton(LEFT_BUMPER);
    }
    
    public boolean getRightBumper() {
        return super.getRawButton(RIGHT_BUMPER);
    }
    
    public boolean getStart() {
        return super.getRawButton(START_BUTTON);
    }
    
    public double getLeftX() {
        return super.getRawAxis(LEFT_X_AXIS);
    }
    
    public double getLeftY() {
        return super.getRawAxis(LEFT_Y_AXIS);
    }
    
    public double getLeftTrigger() {
        return super.getRawAxis(LEFT_TRIGGER);
    }
    
    public double getRightX() {
        return super.getRawAxis(RIGHT_X_AXIS);
    }
    
    public double getRightY() {
        return super.getRawAxis(RIGHT_Y_AXIS);
    }
    
    public double getRightTrigger() {
        return super.getRawAxis(RIGHT_TRIGGER);
    }
}
