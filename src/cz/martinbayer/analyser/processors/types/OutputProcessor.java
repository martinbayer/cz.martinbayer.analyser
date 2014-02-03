package cz.martinbayer.analyser.processors.types;

import cz.martinbayer.analyser.processors.model.IXMLog;

/**
 * @author Martin
 * @version 1.0
 * @created 03-Dec-2013 12:28:41 AM
 */
public abstract class OutputProcessor<T extends IXMLog> extends LogProcessor<T> {

	public OutputProcessor() {

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * method calls process method
	 */
	protected abstract void process();

	/**
	 * method calls process and createOutput methods
	 */
	public void run() {

	}

	protected abstract void createOutput();

}