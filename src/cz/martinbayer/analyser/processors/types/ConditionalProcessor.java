package cz.martinbayer.analyser.processors.types;

import java.util.ArrayList;

import cz.martinbayer.analyser.processors.exception.ProcessorFailedException;
import cz.martinbayer.analyser.processors.model.IE4LogsisLog;

/**
 * @author Martin
 * @version 1.0
 * @created 03-Dec-2013 12:28:37 AM
 */
public abstract class ConditionalProcessor<T extends IE4LogsisLog> extends
		LogProcessor<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1781470438282424250L;
	protected LogProcessor<T> nextSelectedProcessor = null;

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
	 * 
	 * @throws ProcessorFailedException
	 */
	@Override
	public final void run() throws ProcessorFailedException {
		/*
		 * set nextSelectedProcessor to null to avoid running the same processor
		 * even if no condition passed
		 */
		nextSelectedProcessor = null;
		process();
		runNextProcessor();
	}

	/**
	 * @param processor
	 */
	@Override
	public void addNextProcessor(LogProcessor<T> processor) {
		nextProcessors.add(processor);
	}

	@Override
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

	@Override
	protected void runNextProcessor() throws ProcessorFailedException {
		if (nextSelectedProcessor != null) {
			nextSelectedProcessor.setLogData(logData);
			nextSelectedProcessor.run();
		} else {
			throw new ProcessorFailedException(
					String.format(
							"No next processor selected or no condition passed on processor %s",
							getName()));
		}
	}
}