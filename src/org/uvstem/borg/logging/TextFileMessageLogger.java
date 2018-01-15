package org.uvstem.borg.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TextFileMessageLogger extends MessageLogger {
	
	/**
	 * A writer to write to the given file.
	 */
	private PrintWriter out;
	
	/**
	 * Instantiate a TextFileMessageLogger.
	 * @param fileToLogInto The file to log to.
	 * @throws FileNotFoundException
	 */
	public TextFileMessageLogger(File fileToLogInto) throws FileNotFoundException {
		out = new PrintWriter(fileToLogInto);
	}
	
	/**
	 * Print messages to the file.
	 */
	@Override
	public void log() {
		for(MessageLoggable l : registeredLoggables) {
			for(Message m : l.logMessages()) {
				out.println("[" + m.getTypeAsString() + "][" + m.getTimestamp() + "] " + m.getMessage());
				out.flush();
			}
		}
	}
}
