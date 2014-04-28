package cz.martinbayer.analyser.processors.model;

/**
 * @author Martin
 * @version 1.0
 * @created 03-Dec-2013 12:28:43 AM
 */
public interface IE4LogsisLog {

	ELogLevel getLogLevel();

	String getMessage();

	boolean isRemoved();

	void setRemoved(boolean removed);

	void setLogLevel(ELogLevel logLevel);

	void setMessage(String message);

	boolean addLogRemovedListener(ILogRemovedListener listener);

	boolean removeLogRemovedListener(ILogRemovedListener listener);
}