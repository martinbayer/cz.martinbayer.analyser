package cz.martinbayer.analyser.processors.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin
 * @version 1.0
 * @created 03-Dec-2013 12:28:44 AM
 */
public class E4LogsisLogData<T extends IE4LogsisLog> {

	private List<T> logRecords = new ArrayList<>();

	public E4LogsisLogData() {

	}

	public void addLogRecord(T logRecord) {
		this.logRecords.add(logRecord);
	}

	public void replaceLogRecords(List<T> logRecords) {
		this.logRecords.clear();
		this.logRecords.addAll(logRecords);
	}

	public void addLogRecords(List<T> logRecords) {
		this.logRecords.addAll(logRecords);
	}

	public T removeLogRecord(T logRecord) {
		int index = this.logRecords.indexOf(logRecord);
		if (index >= 0) {
			this.logRecords.get(index).setRemoved(true);
			return this.logRecords.get(index);
		}
		return null;
	}

	public List<T> getLogRecords() {
		return this.logRecords;
	}

	public void clearAll() {
		logRecords.clear();
	}

	/**
	 * All records previously marked as removed are set to not to be removed
	 * anymore
	 */
	public void reset() {
		for (T record : logRecords) {
			record.setRemoved(false);
		}
	}
}