package cz.martinbayer.analyser.processors.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin
 * @version 1.0
 * @created 03-Dec-2013 12:28:44 AM
 */
public class XMLogData<T extends IXMLog> {

	private List<T> logRecords = new ArrayList<>();

	public XMLogData() {

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
}