package cz.martinbayer.analyser.processors.types;

import cz.martinbayer.analyser.processors.model.IE4LogsisLog;

/**
 * @author Martin
 * @version 1.0
 * @created 03-Dec-2013 12:28:41 AM
 */
public abstract class OutputProcessor<T extends IE4LogsisLog> extends
		LogProcessor<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6526219085435019172L;

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
	 * method calls process and createOutput methods
	 */
	@Override
	public final void run() {
		process();
		createOutput();
		/* show results in separate thread and finish processing */
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				showResult();
			}
		});
		t.setDaemon(true);
		t.start();
	}

	protected abstract void createOutput();

	protected abstract void showResult();

	@Override
	protected int getMaxInputs() {
		return Integer.MAX_VALUE;
	}

	@Override
	protected int getMaxOutputs() {
		// it is last processor in the chain
		return 0;
	}
}