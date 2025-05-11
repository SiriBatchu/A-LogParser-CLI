package logparser;

public interface LogAggregator {
  void add(LogEntry entry);

  Object getResult();
}