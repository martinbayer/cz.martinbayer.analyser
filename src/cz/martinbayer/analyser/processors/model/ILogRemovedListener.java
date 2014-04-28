package cz.martinbayer.analyser.processors.model;

public interface ILogRemovedListener<T extends IE4LogsisLog> {

	void logRemoved(LogRemovedEvent<T> event);
}
