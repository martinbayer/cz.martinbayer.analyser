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

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * method calls process method
	 */
	protected abstract void process();

	/**
	 * read data from source and create XMLogData collection from it
	 */
	protected abstract void read();

	/**
	 * method call read() and process() then - result is XMLogData collection
	 * created
	 */
	public void run() {
		read();
		process();
	}

}