package cz.martinbayer.analyser.processors.types;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martinbayer.analyser.processors.exception.ProcessorFailedException;
import cz.martinbayer.analyser.processors.model.E4LogsisLogData;
import cz.martinbayer.analyser.processors.model.IE4LogsisLog;
import cz.martinbayer.utils.model.ObservableModelObject;

/**
 * @author Martin
 * @version 1.0
 * @created 03-Dec-2013 12:28:40 AM
 */
public abstract class LogProcessor<T extends IE4LogsisLog> extends
		ObservableModelObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3038532642480687700L;
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_ENABLED = "enabled";
	public static final String PROPERTY_NEXT_PROCESSOR_REMOVED = "nextProcessorRemoved";
	public static final String PROPERTY_NEXT_PROCESSOR_ADDED = "nextProcessorAdded";

	protected static Logger logger = LoggerFactory
			.getLogger(LogProcessor.class);

	protected transient E4LogsisLogData<T> logData;
	protected ArrayList<LogProcessor<T>> nextProcessors = new ArrayList<>();

	private boolean enabled = true;

	private String name;

	/**
	 * @param logData
	 */
	public LogProcessor(E4LogsisLogData<T> logData) {
		this.logData = logData;
	}

	public LogProcessor() {

	}

	/**
	 * method calls process method
	 */
	protected abstract void process() throws ProcessorFailedException;

	public void run() throws ProcessorFailedException {
		process();
	}

	/**
	 * @param logData
	 */
	public void setLogData(E4LogsisLogData<T> logData) {
		this.logData = logData;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		firePropertyChange(PROPERTY_NAME, this.name, this.name = name);
	}

	public void addNextProcessor(LogProcessor<T> nextProcessor) {
		if (getEnabledProcs().size() < getMaxOutputs()) {
			this.nextProcessors.add(nextProcessor);
			logger.info("Processor {} added to {}.", nextProcessor, this);
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
		return getEnabledProcs().size() < getMaxOutputs();
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
		if (logData != null && getEnabledProcs().size() > 0
				&& getEnabledProcs().size() <= getMaxOutputs()) {
			for (LogProcessor<T> processor : getEnabledProcs()) {
				processor.setLogData(logData);
			}
		}
	}

	protected void runNextProcessor() throws ProcessorFailedException {
		if (getEnabledProcs().size() >= 0
				&& getEnabledProcs().size() <= getMaxOutputs()) {
			for (LogProcessor<T> processor : getEnabledProcs()) {
				processor.setLogData(logData);
				processor.run();
			}
		} else {
			throw new ProcessorFailedException(
					String.format(
							"Incorrect count of output processors. Actual count: %d. Max count: %d.",
							getEnabledProcs().size(), getMaxOutputs()));
		}
	}

	public boolean removeProcessor(LogProcessor<T> destinationProcessor) {
		boolean removed = false;
		logger.info("Removing processor {} from {}", destinationProcessor, this);
		if (destinationProcessor == null) {
			removed = this.nextProcessors.size() > 0;
			this.nextProcessors.clear();
		} else {
			removed = this.nextProcessors.remove(destinationProcessor);
		}
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

	/**
	 * Provide basic validation functions which handles only the count of max
	 * output processors which are enabled
	 * 
	 * @return - some StringBuffer value if processor configuration is not
	 *         valid. If the StringBuffer is NULL then it is said to be valid
	 */
	public final StringBuffer isValid() {
		List<LogProcessor<T>> enabledProcs = getEnabledProcs();
		if (enabledProcs.size() <= getMaxOutputs()) {
			/* check processor specific validity */
			return isSubProcessorValid();
		}
		StringBuffer message = new StringBuffer();
		message.append("Too much output processors enabled for ")
				.append(getName())
				.append(". Disable or delete one of the following processor(s):");
		message.append(LogProcessor.getProcessorNames(enabledProcs));
		/* check processor specific validity */
		message.append(isSubProcessorValid());
		return message;
	}

	public ArrayList<LogProcessor<T>> getEnabledProcs() {
		if (nextProcessors == null || nextProcessors.size() <= 0) {
			return new ArrayList<>();
		}
		ArrayList<LogProcessor<T>> enabledProcs = new ArrayList<>();
		for (LogProcessor<T> proc : nextProcessors) {
			if (proc.isEnabled()) {
				enabledProcs.add(proc);
			}
		}
		return enabledProcs;
	}

	/**
	 * Method can be overrided to get some specific processor's validation.
	 * Default implementation returns null which means that processor is valid.
	 * Both {@link #isValid()} and {@link #isSubProcessorValid()} must return
	 * null to
	 * 
	 * @return - some StringBuffer value if processor configuration is not
	 *         valid. If the StringBuffer is NULL then it is said to be valid
	 */
	protected StringBuffer isSubProcessorValid() {
		return null;
	}

	public abstract void init();

	@Override
	public String toString() {
		return String.format("%s [%s]", getClass().getName(), getName());
	}

	/**
	 * Returns names of processors separated by ';'
	 * 
	 * @param procs
	 * @return
	 */
	public static <T extends IE4LogsisLog> StringBuffer getProcessorNames(
			List<LogProcessor<T>> procs) {
		StringBuffer sb = new StringBuffer();
		for (LogProcessor<T> proc : procs) {
			sb.append(proc.getName()).append("; ");
		}
		return sb;
	}
}