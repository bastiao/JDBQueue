package org.koplabs.dbqueue;

/**
 *
 * @author bastiao
 */
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class QueueLogger {
	static private FileHandler fileTxt;
	static private SimpleFormatter formatterTxt;

	static public void setup() throws IOException {
		// Create Logger
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.INFO);
                int limit = 1000000; // 1 Mb

		fileTxt = new FileHandler("queue.log", limit, 1);

		// Create txt Formatter
		formatterTxt = new SimpleFormatter();
		fileTxt.setFormatter(formatterTxt);
		
                Handler ch = new ConsoleHandler();
                ch.setFormatter(formatterTxt);
                logger.addHandler(ch);
                
                logger.addHandler(fileTxt);

	}
} 