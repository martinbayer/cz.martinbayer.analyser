package cz.martinbayer.analyser.processors.types;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martinbayer.analyser.processors.exception.ProcessorFailedException;
import cz.martinbayer.analyser.processors.model.IXMLog;
import cz.martinbayer.analyser.processors.model.XMLogData;
import cz.martinbayer.utils.model.ObservableModelObject;

/**
 * @author Martin
 * @version 1.0
 * @created 03-Dec-2013 12:28:40 AM
 */
public abstract class LogProcessor<T extends IXMLog> extends
		ObservableModelObject {

	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_ENABLED = "enabled";
	public static final String PROPERTY_NEXT_PROCESSOR_REMOVED = "nextProcessorRemoved";
	public static final String PROPERTY_NEXT_PROCESSOR_ADDED = "nextProcessorAdded";

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected XMLogData<T> logData;
	protected ArrayList<LogProcessor<T>> nextProcessors = new ArrayList<>();

	private boolean enabled = true;

	private String name;

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
	protected abstract void process() throws ProcessorFailedException;

	public void run() {
		try {
			process();
		} catch (ProcessorFailedException e) {
			logger.error("Unable to process", e);
		}
	}

	/**
	 * @param logData
	 */
	public void setLogData(XMLogData<T> logData) {
		this.logData = logData;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		firePropertyChange(PROPERTY_NAME, this.name, this.name = name);
	}

	public void addNextProcessor(LogProcessor<T> nextProcessor) {
		if (this.nextProcessors.size() < getMaxOutputs()) {
			this.nextProcessors.add(nextProcessor);
			firePropertyChange(PROPERTY_NEXT_PROCESSOR_ADDED, null,
					nextProcessor);
		} else {
			throw new InvalidParameterException(
					"Max count of output processors exceeded");
		}
	}

	protected int getMaxOutputs() {
		return 1;
	}

	protected int getMaxInputs() {
		return Integer.MAX_VALUE;
	}

	public boolean canAddOutProc() {
		return nextProcessors.size() < getMaxOutputs();
	}

	/*
	 * if there are any input processors available then you can use how much you
	 * need (exception is for input processors which have no input processor(s)
	 * define)
	 */
	public boolean canAddInProc() {
		return getMaxInputs() > 0;
	}

	/*
	 * it should differ in conditional processor which should have one of the
	 * output processors selected only
	 */
	protected void setDataForProcessing() {
		if (logData != null && nextProcessors.size() > 0
				&& nextProcessors.size() <= getMaxOutputs()) {
			for (LogProcessor<T> processor : nextProcessors) {
				processor.setLogData(logData);
			}
		}
	}

	protected void runNextProcessor() throws ProcessorFailedException {
		if (nextProcessors.size() >= 0
				&& nextProcessors.size() <= getMaxOutputs()) {
			for (LogProcessor<T> processor : nextProcessors) {
				processor.run();
			}
		} else {
			throw new ProcessorFailedException(
					String.format(
							"Incorrect count of output processors. Actual count: %d. Max count: %d.",
							nextProcessors.size(), getMaxOutputs()));
		}
	}

	public boolean removeProcessor(LogProcessor<T> destinationProcessor) {
		boolean removed = this.nextProcessors.remove(destinationProcessor);
		if (removed) {
			firePropertyChange(PROPERTY_NEXT_PROCESSOR_REMOVED, null,
					destinationProcessor);
		}
		return removed;
	}

	public int getNextProcsCount() {
		return this.nextProcessors.size();
	}

	public ArrayList<LogProcessor<T>> getNextProcessors() {
		return this.nextProcessors;
	}

	public void setEnabled(boolean enabled) {
		firePropertyChange(PROPERTY_ENABLED, this.enabled,
				this.enabled = enabled);
	}

	public boolean isEnabled() {
		return this.enabled;
	}
}