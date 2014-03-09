package cz.martinbayer.analyser.processors.types;

import cz.martinbayer.analyser.processors.model.IXMLog;

/**
 * @author Martin
 * @version 1.0
 * @created 03-Dec-2013 12:28:39 AM
 */
public abstract class InputProcessor<T extends IXMLog> extends LogProcessor<T> {

	public InputProcessor() {

	}

	@Override
	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * method calls process method
	 */
	@Override
	protected abstract void process();

	/**
	 * read data from source and create XMLogData collection from it
	 */
	protected abstract void read();

	/**
	 * method call read() and process() then - result is XMLogData collection
	 * created
	 */
	@Override
	public void run() {
		read();
		process();
	}

	@Override
	protected int getMaxInputs() {
		// it is first processor in the chain
		return 0;
	}

	@Override
	protected int getMaxOutputs() {
		// only conditions can have more outputs
		return 1;
	}
}