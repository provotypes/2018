package org.uvstem.borg.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Log robot state to a text file in CSV format.
 */
public class CSVStateLogger extends StateLogger {
	PrintWriter out;
	boolean printedHeader = false;
	
	/**
	 * Instantiate a CSVStateLogger.
	 * @param fileToLogInto The file to log into.
	 * @throws FileNotFoundException
	 */
	public CSVStateLogger(File fileToLogInto) throws FileNotFoundException {
		this.out = new PrintWriter(fileToLogInto);
	}
	
	/**
	 * Print the field header if necessary, then print out CSV line.
	 */
	@Override
	public void log() {
		if (!printedHeader) {
			for (String l : registeredLoggables.keySet()) {
				for (String s : registeredLoggables.get(l).getStateLogFields()) {
					out.print(l + "/" + s + ",");
				}
			}
			
			out.println();
			out.flush();
			
			printedHeader = true;
		}
		
		for (String l : registeredLoggables.keySet()) {
			for (String f : registeredLoggables.get(l).logState().keySet()) {
				out.print(registeredLoggables.get(l).logState().get(f) + ",");
			}
		}
		
		out.println();
		out.flush();
	}

}
