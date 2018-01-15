package org.uvstem.borg.logging;

import java.util.HashSet;
import java.util.Set;

/**
 * A MessageLogger is an abstract class that specifies an interface for
 * classes that log robot messages, typically events.  Each object that
 * generates messages is to be registered and then the log function is 
 * called periodically.
 *
 */
public abstract class MessageLogger {
	
	/**
	 * The set of registered EventLoggables.
	 */
	protected Set<MessageLoggable> registeredLoggables = new HashSet<>();
	
	/**
	 * Register the MessageLoggable for later logging.
	 * @param String name the name that identifies the MessageLoggable
	 * @param MessageLoggable loggable the object to register
	 */
	public void register(String name, MessageLoggable loggable) {
		registeredLoggables.add(loggable);
	}
	
	/**
	 * Log messages from the the registered MessageLoggables.
	 */
	public abstract void log();
}
