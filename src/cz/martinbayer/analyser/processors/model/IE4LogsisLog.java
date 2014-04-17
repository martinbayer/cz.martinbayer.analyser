package cz.martinbayer.analyser.processors.model;

/**
 * @author Martin
 * @version 1.0
 * @created 03-Dec-2013 12:28:43 AM
 */
public interface IE4LogsisLog {

	public ELogLevel getLogLevel();

	public String getMessage();

	public boolean isRemoved();

	public void setRemoved(boolean removed);

	public void setLogLevel(ELogLevel logLevel);

	public void setMessage(String message);
}