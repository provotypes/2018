package org.uvstem.borg.logging;

import java.util.Map;
import java.util.Set;

/**
 * A StateLoggable represents something on the robot (such as a
 * subsystem) with state that changes over time.  It is the
 * responsibility of the StateLoggable to provide string representations
 * of its fields and valid data types for the StateLogger used.
 */
public interface StateLoggable {

	/**
	 * Provide the fields that this StateLoggable uses.
	 * @return Set containing string representations of the fields.
	 */
	public Set<String> getStateLogFields();

	/**
	 * Called periodically to log this StateLoggable's state.
	 * @return Set containing valid representations of field data.
	 */
	public Map<String, Object> logState();
}
