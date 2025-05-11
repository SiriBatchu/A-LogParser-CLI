package logparser;

import java.util.*;

public class ApplicationAggregator implements LogAggregator {
  private Map<String, Integer> counts = new HashMap<>();

  @Override
  public void add(LogEntry entry) {
    if (!(entry instanceof ApplicationLogEntry))
      return;
    ApplicationLogEntry app = (ApplicationLogEntry) entry;
    counts.put(app.level, counts.getOrDefault(app.level, 0) + 1);
  }

  @Override
  public Object getResult() {
    return counts;
  }
}
