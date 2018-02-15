package org.uvstem.borg.logging;

import java.util.HashMap;
import java.util.Map;

/**
 * A StateLogger is an abstract class that specifies an interface for
 * classes that log robot state.  Each object to be logged is registered
 * and then the log function is called periodically.
 *
 */
public abstract class StateLogger {

	/**
	 * The set of registered StateLoggables.
	 */
	protected Map<String, StateLoggable> registeredLoggables = new HashMap<>();

	/**
	 * Register the loggable for later logging.
	 * @param String name the name that identifies the StateLoggable
	 * @param StateLoggable loggable the object to register
	 */
	public void register(String name, StateLoggable loggable) {
		registeredLoggables.put(name, loggable);
	}

	/**
	 * Log the registered loggables.
	 */
	public abstract void log();
}
