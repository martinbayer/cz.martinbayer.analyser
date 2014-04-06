package cz.martinbayer.analyser.processors.types;

import cz.martinbayer.analyser.processors.exception.ProcessorFailedException;
import cz.martinbayer.analyser.processors.model.IXMLog;
import cz.martinbayer.analyser.processors.model.XMLogData;

/**
 * @author Martin
 * @version 1.0
 * @created 03-Dec-2013 12:28:39 AM
 */
public abstract class InputProcessor<T extends IXMLog> extends LogProcessor<T> {

	public InputProcessor() {
		/**
		 * do not initialize this collection in different types of processors.
		 * They must be referenced to not to waste the memory
		 */
		logData = new XMLogData<>();
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
	 * 
	 * @throws ProcessorFailedException
	 */
	@Override
	public void run() {
		init();
		read();
		process();
		try {
			runNextProcessor();
		} catch (ProcessorFailedException e) {
			logger.error("Unable to run processor", e);
		}
	}

	/**
	 * Clear data initialized in the previous run
	 */
	private void init() {
		logData.clearAll();
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