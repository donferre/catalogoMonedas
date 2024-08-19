package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ClassLogger {
	protected final Logger log = LogManager.getLogger(getClass());

}