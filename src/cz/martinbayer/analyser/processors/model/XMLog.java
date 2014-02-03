package cz.martinbayer.analyser.processors.model;

import java.util.Date;

/**
 * @author Martin
 * @version 1.0
 * @created 03-Dec-2013 12:28:43 AM
 */
public class XMLog implements IXMLog {

	public XMLog() {

	}

	@Override
	public void finalize() throws Throwable {

	}

	@Override
	public ELogLevel getLogLevel() {
		return null;
	}

	public Date getLogTime() {
		return null;
	}

	@Override
	public String getText() {
		return "";
	}

	/**
	 * @param filtered
	 */
	@Override
	public void setRemoved(boolean filtered) {

	}

	/**
	 * @param logTime
	 */
	public void setLogTime(Date logTime) {

	}

	/**
	 * @param text
	 */
	@Override
	public void setText(String text) {

	}

	@Override
	public boolean isRemoved() {
		return false;
	}

	@Override
	public void setLogLevel(ELogLevel logLevel) {
		// TODO Auto-generated method stub

	}

}