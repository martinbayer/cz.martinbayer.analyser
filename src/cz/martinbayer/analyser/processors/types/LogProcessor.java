package cz.martinbayer.analyser.processors.types;

import cz.martinbayer.analyser.processors.model.IXMLog;
import cz.martinbayer.analyser.processors.model.XMLogData;

/**
 * @author Martin
 * @version 1.0
 * @created 03-Dec-2013 12:28:40 AM
 */
public abstract class LogProcessor<T extends IXMLog> {

	protected XMLogData<T> logData;
	protected LogProcessor<T> nextProcessor;

	@Override
	public void finalize() throws Throwable {

	}

	/**
	 * @param logData
	 */
	public LogProcessor(XMLogData<T> logData) {
		this.logData = logData;
	}

	public LogProcessor() {

	}

	/**
	 * method calls process method
	 */
	protected abstract void process();

	public void run() {

	}

	/**
	 * @param logData
	 */
	public void setLogData(XMLogData<T> logData) {
		this.logData = logData;
	}

	public abstract String getName();

	public void setNextProcessor(LogProcessor<T> nextProcessor) {
		this.nextProcessor = nextProcessor;
	}
}