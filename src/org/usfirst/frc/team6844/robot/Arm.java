package org.usfirst.frc.team6844.robot;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.uvstem.borg.BorgSubsystem;
import org.uvstem.borg.logging.Message;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;



public class Arm extends BorgSubsystem{
    
    TalonSRX talon_arm;
    DigitalInput limitswitch_bottom;
    DigitalInput limitswitch_top;
    
    public Arm() {
        talon_arm = new TalonSRX(5);
        
        limitswitch_bottom = new DigitalInput(0);
        limitswitch_top = new DigitalInput(1);
    }
    
    public void operateArm(double val) {
        talon_arm.set(ControlMode.PercentOutput, val + .104878);
    }
    
    public boolean bottom_limitswitch_pressed() {
        return limitswitch_bottom.get();
    }
    
    public boolean top_limitswitch_pressed() {
        return limitswitch_top.get();
    }
    
    @Override
    public List<Message> logMessages() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> getStateLogFields() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> logState() {
        // TODO Auto-generated method stub
        return null;
    }

}
