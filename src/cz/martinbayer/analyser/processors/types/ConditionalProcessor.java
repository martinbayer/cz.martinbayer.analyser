package cz.martinbayer.analyser.processors.types;

import java.util.ArrayList;

import cz.martinbayer.analyser.processors.model.IXMLog;

/**
 * @author Martin
 * @version 1.0
 * @created 03-Dec-2013 12:28:37 AM
 */
public abstract class ConditionalProcessor<T extends IXMLog> extends
		LogProcessor<T> {

	private LogProcessor<T> nextSelectedProcessor = null;

	public ConditionalProcessor() {

	}

	@Override
	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * conditions are evaluated in this method. nextProcessor variable must be
	 * set in the end of the processing
	 */
	@Override
	protected abstract void process();

	/**
	 * run method calls process which evaluates the conditions and set the
	 * correct nextProcessor attribute
	 */
	@Override
	public void run() {

	}

	/**
	 * @param processor
	 */
	@Override
	public void addNextProcessor(LogProcessor<T> processor) {
		nextProcessors.add(processor);
	}

	public ArrayList<LogProcessor<T>> getNextProcessors() {
		return nextProcessors;
	}

	@Override
	protected int getMaxInputs() {
		return Integer.MAX_VALUE;
	}

	@Override
	protected int getMaxOutputs() {
		// only one output is used at once
		return Integer.MAX_VALUE;
	}

	/**
	 * there should be one processor selected which should gain the actual
	 * processing data
	 */
	@Override
	protected void setDataForProcessing() {
		if (logData != null && nextSelectedProcessor != null) {
			nextSelectedProcessor.setLogData(logData);
		} else {
			logger.warn("No processor is selected for conditional processor. Data cennot be set.");
		}
	}

	@Override
	protected void runNextProcessor() {
		if (nextSelectedProcessor != null) {
			nextSelectedProcessor.run();
		} else {
			logger.error("Next processor is not selected. Process cannot be completed");
		}
	}
}