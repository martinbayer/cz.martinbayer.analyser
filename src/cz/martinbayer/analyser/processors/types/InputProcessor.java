package cz.martinbayer.analyser.processors.types;

import cz.martinbayer.analyser.processors.exception.ProcessorFailedException;
import cz.martinbayer.analyser.processors.model.IE4LogsisLog;
import cz.martinbayer.analyser.processors.model.E4LogsisLogData;

/**
 * @author Martin
 * @version 1.0
 * @created 03-Dec-2013 12:28:39 AM
 */
public abstract class InputProcessor<T extends IE4LogsisLog> extends LogProcessor<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3276899117442053090L;

	public InputProcessor() {
		super();
		init();
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
	public final void run() throws ProcessorFailedException {
		/* clear data from previous run */
		logData.clearAll();
		read();
		process();
		runNextProcessor();
	}

	/**
	 * Clear data initialized in the previous run
	 */
	@Override
	public void init() {
		/**
		 * do not initialize this collection in different types of processors.
		 * They must be referenced to not to waste the memory
		 */
		if (logData == null) {
			logData = new E4LogsisLogData<>();
		}
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