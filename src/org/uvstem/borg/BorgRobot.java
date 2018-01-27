package org.uvstem.borg;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.uvstem.borg.logging.Message;
import org.uvstem.borg.logging.Message.Type;
import org.uvstem.borg.logging.MessageLoggable;
import org.uvstem.borg.logging.MessageLogger;
import org.uvstem.borg.logging.StateLoggable;
import org.uvstem.borg.logging.StateLogger;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class extends the WPILib TimedRobot class to provide several features, such
 * as logging, Nashorn-based autonomous scripting, and the cryptographic verification
 * of those autonomous scripts.
 * 
 * Note that your extension of this class must call super for the robotInit(),
 * autoInit(), autoPeriodic(), teleopInit(), and teleopPeriodic() methods if 
 * overridden.
 */
public class BorgRobot extends TimedRobot implements StateLoggable, MessageLoggable {
	private Map<String, BorgSubsystem> subsystems = new HashMap<>();
	
	private PowerDistributionPanel powerDistributionPanel;
	
	private BuiltInAccelerometer accelerometer;

	//Set to ridiculously high values as defaults; won't record collisions.
	private double collisionAccelThreshold = 100;
	private double collisionJerkThreshold = 100;
	
	private double lastX;
	private double lastY;
	private double lastZ;

	protected MessageLogger messageLogger;
	protected StateLogger stateLogger;
	
	private List<Message> messageBuffer = new ArrayList<>();
	private Map<String, Object> stateBuffer = new HashMap<>();
	
	protected PublicKey publicKey;
	protected SendableChooser<Invocable> autoModes = new SendableChooser<>();
	
	/**
	 * Log an init message.  You *must* call initAutoScripts() yourself with the correct
	 * folder path.
	 */
	@Override
	public void robotInit() {
		messageBuffer.add(new Message("Robot initalizing.", Type.INFO));
	}
	
	/**
	 * Log an init message and run init() from the selected autonomous script.
	 */
	@Override
	public void autonomousInit() {
		messageBuffer.add(new Message("Autonomous initalizing.", Type.INFO));
		
		Invocable i = autoModes.getSelected();
		try {
			i.invokeFunction("init");
		} catch (NullPointerException e) {
			messageBuffer.add(new Message("No auto script selected!", Type.SEVERE));
		} catch (NoSuchMethodException e) {
			messageBuffer.add(new Message("This auto script must have an init() function!", Type.SEVERE));
		} catch (ScriptException e) {
			messageBuffer.add(new Message("Auto script errored during execution of init().", Type.SEVERE));
		}
	}
	
	/**
	 * Log robot state and run periodic() from the selected autonomous script.
	 */
	@Override
	public void autonomousPeriodic() {
		periodic();
		
		Invocable i = autoModes.getSelected();
		try {
			i.invokeFunction("periodic");
		} catch (NullPointerException e) {
			messageBuffer.add(new Message("No auto script selected!", Type.SEVERE));
		} catch (NoSuchMethodException e) {
			messageBuffer.add(new Message("This auto script must have a periodic() function!", Type.SEVERE));
		} catch (ScriptException e) {
			messageBuffer.add(new Message("Auto script errored during execution of periodic().", Type.SEVERE));
		}
	}
	
	/**
	 * Log an init message.
	 */
	@Override
	public void teleopInit() {
		messageBuffer.add(new Message("Telop initalizing.", Type.INFO));
	}
	
	/**
	 * Log robot state.
	 */
	@Override
	public void teleopPeriodic() {
		periodic();
	}
	
	/**
	 * Log an init message.
	 */
	@Override
	public void testInit() {
		messageBuffer.add(new Message("Test initalizing.", Type.INFO));
	}
	
	/**
	 * Log robot state.
	 */
	@Override
	public void testPeriodic() {
		periodic();
	}
	
	@Override
	public void disabledInit() {
		messageBuffer.add(new Message("Disabled initalizing.", Type.INFO));
	}
	
	@Override
	public void disabledPeriodic() {
		periodic();
	}
	
	/**
	 * Method that is called each time autonomousPeriodic(), teleopPeriodic(), and
	 * testPeriodic() is called.
	 */
	protected void periodic() {
		if (accelerometer != null) {
			double accel = Math.sqrt(
					Math.pow(accelerometer.getX(), 2) +
					Math.pow(accelerometer.getY(), 2) +
					Math.pow(accelerometer.getZ(), 2));
			
			double jerk = Math.sqrt(
					Math.pow(accelerometer.getX() - lastX, 2) +
					Math.pow(accelerometer.getY() - lastY, 2) +
					Math.pow(accelerometer.getZ() - lastZ, 2));
			

			if (accel > collisionAccelThreshold) {
				messageBuffer.add(new Message("Collision: acceleration exceeds threshold!", Type.SEVERE));
			}

			if (jerk > collisionJerkThreshold) {
				messageBuffer.add(new Message("Collision: jerk exceeds threshold!", Type.SEVERE));
			}	

			lastX = accelerometer.getX();
			lastY = accelerometer.getY();
			lastZ = accelerometer.getZ();
		}

		log();
	}
	
	/**
	 * Log everything that's been registered.  Clean up buffers.
	 */
	private void log() {
		if (messageLogger != null) {
			messageLogger.log();
			messageBuffer.clear();
		}
		
		if (stateLogger != null) {
			stateLogger.log();
		}
	}
	
	/**
	 * Implement the MessageLoggable interface.
	 */
	@Override
	public List<Message> logMessages() {
		return messageBuffer;
	}

	/**
	 * Provide robot state fields.
	 */
	@Override
	public Set<String> getStateLogFields() {
		Set<String> fields = new HashSet<>();
		
		fields.add("voltage");
		fields.add("current");
		fields.add("brownout");
		fields.add("attached");
		fields.add("fms");
		fields.add("enabled");
		fields.add("game_message");
		
		return fields;
	}

	/**
	 * Log default robot state.
	 */
	@Override
	public Map<String, Object> logState() {
		stateBuffer.put("voltage", DriverStation.getInstance().getBatteryVoltage());
		
		if (powerDistributionPanel != null) {
			stateBuffer.put("current", powerDistributionPanel.getTotalCurrent());
		} else {
			stateBuffer.put("current", null);
		}
		
		stateBuffer.put("brownout", RobotController.isBrownedOut());
		stateBuffer.put("attached", DriverStation.getInstance().isDSAttached());
		stateBuffer.put("fms", DriverStation.getInstance().isFMSAttached());
		stateBuffer.put("enabled", DriverStation.getInstance().isEnabled());
		stateBuffer.put("game_message", DriverStation.getInstance().getGameSpecificMessage());
		
		return stateBuffer;
	}
	
	/**
	 * Register the subsystem with the robot.  This enables the robot to automatically
	 * log the subsystem and use the expose subsystem for use in autonomous scripts.
	 * @param name the name of the subsystem
	 * @param subsystem the subsystem to register
	 */
	protected final void registerSubsystem(String name, BorgSubsystem subsystem) {
		subsystems.put(name, subsystem);
	}
	
	/**
	 * Set the MessageLogger to use for the robot.
	 * @param messageLogger the MessageLogger to use.
	 */
	protected final void setMessageLogger(MessageLogger messageLogger) {
		this.messageLogger = messageLogger;
		this.messageLogger.register("Robot", this);
	}
	
	/**
	 * Set the StateLogger to use for the robot.
	 * @param stateLogger the StateLogger to use.
	 */
	protected final void setStateLogger(StateLogger stateLogger) {
		this.stateLogger = stateLogger;
		this.stateLogger.register("Robot", this);
	}
	
	/**
	 * Set the PowerDistributionPanel to use for logging purposes.  Must be called
	 * as part of robotInit().
	 * @param powerDistributionPanel the power distribution panel reference to use.
	 */
	protected final void setPowerDistributionPanel(PowerDistributionPanel powerDistributionPanel) {
		this.powerDistributionPanel = powerDistributionPanel;
	}	
	
	/**
	 * Initialize the public key for autonomous scripts and load them for use via the SmartDashboard.
	 * @throws IOException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws SignatureException 
	 * @throws InvalidKeyException 
	 */
	protected void initAutoScripts(File publicKey, File scriptDirectory) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeyException, SignatureException {
		initPublicKey(publicKey);
		initAutoScripts(scriptDirectory);
	}
	
	/**
	 * Initialize the public key for autonomous script signature verification.
	 */
	protected void initPublicKey(File publicKey) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] keyFileBytes = Files.readAllBytes(publicKey.toPath());
		
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyFileBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		this.publicKey = kf.generatePublic(spec);
	}
	
	/**
	 * Identify and verify potential scripts to use.
	 * @param scriptDirectory The folder that contains the .sig and .js files.
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws IOException 
	 * @throws SignatureException 
	 */
	protected void initAutoScripts(File scriptDirectory) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
		Set<File> signatures = new HashSet<>(Arrays.asList(scriptDirectory.listFiles((File dir, String name) -> {
			if (name.contains(".sig")) {
				return true;
			}
			
			return false;
		})));
		
		messageBuffer.add(new Message("Found the following signatures: " + signatures, Type.DEBUG));
		
		//Verify scripts
		Signature publicSignature = Signature.getInstance("SHA256withRSA");
		publicSignature.initVerify(this.publicKey);
		
		ScriptEngineManager manager = new ScriptEngineManager();
		
		for (File sig : signatures) {
			String scriptName = sig.getName().replaceFirst("\\.sig", "\\.js");
			
			File[] scriptMatches = scriptDirectory.listFiles((File dir, String name) -> {
				if (name.equals(scriptName)) {
					return true;
				}
				
				return false;
			});
			
			messageBuffer.add(new Message("While looking at signature " + sig + ", found the following scripts: " + scriptMatches, Type.DEBUG));
			
			if (scriptMatches.length < 1) {
				messageBuffer.add(new Message("Couldn't find auto script " + scriptName + ".", Type.WARNING));
			} else if (scriptMatches.length > 1) {
				messageBuffer.add(new Message("Multiple files named " + scriptName + " found for auton script.", Type.SEVERE));
			} else {
				publicSignature.update(Files.readAllBytes(scriptMatches[0].toPath()));
				
				if (publicSignature.verify(Files.readAllBytes(sig.toPath()))) {
					ScriptEngine engine = manager.getEngineByName("nashorn");
					try {
						setUpScriptContext(engine);
						engine.eval(new FileReader(scriptMatches[0]));
						autoModes.addDefault(scriptName, (Invocable) engine);
						messageBuffer.add(new Message("Added " + scriptName + " to the SendableChooser.", Type.DEBUG));
					} catch (ScriptException s) {
						messageBuffer.add(new Message(scriptName + " doesn't parse!", Type.SEVERE));
					}
				} else {
					messageBuffer.add(new Message(scriptName + " failed signature verification.", Type.WARNING));
				}
			}
		}
		
		SmartDashboard.putData("Autonomous modes", autoModes);
		messageBuffer.add(new Message("Added the auto modes chooser, " + autoModes + " to the SmartDashboard.", Type.DEBUG));
	}
	
	/**
	 * Set the acceleration threshold to use for logging purposes.  Should be called as part
	 * as part of robotInit().
	 * @param value the threshold in G's to use for logging collisions.
	 */
	public void setCollisionAccelThreshold(double value) {
		collisionAccelThreshold = value;
	}
	
	/**
	 * Set the jerk threshold to use for logging purposes.  Should be called as part
	 * of robotInit().
	 * @param value the threshold in G's/period to use for logging
	 * collisisons.
	 */
	public void setCollisionJerkThreshold(double value) {
		collisionJerkThreshold = value;
	}
	
	/**
	 * Expose each registered subsystem to the autonomous script engine.
	 * @param engine
	 */
	private void setUpScriptContext(ScriptEngine engine) {
		for (String subsystem : subsystems.keySet()) {
			engine.put(subsystem, subsystems.get(subsystem));
		}
	}
}
