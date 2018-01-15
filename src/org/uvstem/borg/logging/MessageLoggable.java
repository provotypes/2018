package org.uvstem.borg.logging;

import java.util.List;

/**
 * A MessageLoggable represents something on the robot (such as a
 * subsystem) that can generate log messages, such as warnings.
 */
public interface MessageLoggable {

	/**
	 * Gather log messages from this MessageLoggable.  This method
	 * is typically called by a MessageLogger.
	 * @return list of messages to log for this class.
	 */
	public List<Message> logMessages();
}
