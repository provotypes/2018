package org.uvstem.borg.logging;

/**
 * This class is designed to represent an arbitrary log message.  Once instantiated,
 * the message is immutable.
 */
public class Message implements Comparable<Message> {
	
	/**
	 * This class refers to the type or severity or log message.
	 */
	public enum Type {
		DEBUG,
		CONFIG,
		INFO,
		WARNING,
		SEVERE
	}
	
	/**
	 * A timestamp that indicates when the message was generated.  Defaults to the
	 * value of System.currentTimeMillis() if not otherwise provided.
	 */
	private long timestamp;
	
	/**
	 * The body of the message.
	 */
	private String message;
	
	/**
	 * The type of the message.
	 */
	private Type type;
	
	/**
	 * Create a new message.
	 * 
	 * @param timestamp The time when the message was generated.
	 * @param message The body of the message.
	 * @param type The message type or severity.
	 */
	public Message(long timestamp, String message, Type type) {
		this.timestamp = timestamp;
		this.message = message;
		this.type = type;
	}
	
	/**
	 * Create a new message, using System.currentTimeMillis() for the timestamp.
	 * 
	 * @param message The body of the message.
	 * @param type The message type or severity.
	 */
	public Message(String message, Type type) {
		this(System.currentTimeMillis(), message, type);
	}

	public long getTimestamp() {
		return timestamp;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getTypeAsString() {
		switch (type) {
			case DEBUG:
				return "DEBUG";
			case CONFIG:
				return "CONFIG";
			case INFO:
				return "INFO";
			case WARNING:
				return "WARNING";
			case SEVERE:
				return "SEVERE";
			default:
				return null;
		}
	}
	
	/**
	 * Sort messages by timestamp.  Not safe against integer overflow, though
	 * it shouldn't be a problem for a typical FRC match.
	 */
	public int compareTo(Message m) {
		return (int) (this.timestamp - timestamp);
	}
}
