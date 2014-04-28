package cz.martinbayer.analyser.processors.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Martin
 * @version 1.0
 * @created 03-Dec-2013 12:28:44 AM
 */
public class E4LogsisLogData<T extends IE4LogsisLog> {

	private List<T> logRecords = new ArrayList<>();
	private List<T> removedRecords = new ArrayList<>();

	public void addLogRecord(T logRecord) {
		this.logRecords.add(logRecord);
	}

	/**
	 * Remove actual records and replace them with logRecords in parameter
	 * 
	 * @param logRecords
	 */
	public void replaceLogRecords(List<T> logRecords) {
		this.logRecords.clear();
		addLogRecords(logRecords);
	}

	/**
	 * Add more log records
	 */
	public void addLogRecords(List<T> logRecords) {
		this.logRecords.addAll(logRecords);
	}

	public List<T> getLogRecords() {
		applyPreviousRemoves();
		return this.logRecords;
	}

	public void clearAll() {
		logRecords.clear();
		removedRecords.clear();
	}

	/**
	 * All records previously marked as removed are set to not to be removed
	 * anymore
	 */
	public void reset() {
		/*
		 * move all records from removedRecords to logRecords and then set them
		 * all to not to be removed
		 */
		logRecords.addAll(removedRecords);
		removedRecords.clear();
		for (int i = 0; i < logRecords.size(); i++) {
			logRecords.get(i).setRemoved(false);
		}
	}

	/**
	 * If there was some previously removed data, move them to removed data
	 * collection and vice versa
	 */
	private void applyPreviousRemoves() {
		ListIterator<T> logRecsIt = logRecords.listIterator();
		T record = null;
		while (logRecsIt.hasNext()) {
			record = logRecsIt.next();
			if (record.isRemoved()) {
				removedRecords.add(record);
				logRecsIt.remove();
			}
		}
	}

	/**
	 * Total count of logRecords and removedRecords(actually filtered records
	 * which are returned to the collection of all records when the processing
	 * is started again)
	 * 
	 * @return
	 */
	public int getSize() {
		return logRecords.size() + removedRecords.size();
	}
}