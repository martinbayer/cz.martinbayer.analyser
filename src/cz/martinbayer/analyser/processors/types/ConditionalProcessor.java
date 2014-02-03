package cz.martinbayer.analyser.processors.types;

import java.util.HashMap;

import cz.martinbayer.analyser.processors.model.IXMLog;

/**
 * @author Martin
 * @version 1.0
 * @created 03-Dec-2013 12:28:37 AM
 */
public abstract class ConditionalProcessor<T extends IXMLog> extends LogProcessor<T> {

	private HashMap<String, LogProcessor<T>> nextProcessors;

	public ConditionalProcessor() {

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * conditions are evaluated in this method. nextProcessor variable must be
	 * set in
	 * the end of the processing
	 */
	protected abstract void process();

	/**
	 * run method calls process which evaluates the conditions and set the
	 * correct
	 * nextProcessor attribute
	 */
	public void run() {

	}

	/**
	 * @param processor
	 */
	public void addNextProcessor(LogProcessor<T> processor) {
		nextProcessors.put(processor.getName(), processor);
	}

}