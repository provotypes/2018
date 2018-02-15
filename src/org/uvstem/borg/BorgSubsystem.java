package org.uvstem.borg;

import java.util.ArrayList;
import java.util.List;

import org.uvstem.borg.logging.Message;
import org.uvstem.borg.logging.MessageLoggable;
import org.uvstem.borg.logging.StateLoggable;

public abstract class BorgSubsystem implements MessageLoggable, StateLoggable {

	protected ArrayList<Message> messageBuffer;
	
	public abstract void update();
	
	@Override
	public List<Message> logMessages() {
		return messageBuffer;
	}
	
	@Override
	public void afterLogMessages() {
		messageBuffer.clear();
	}
	
}
