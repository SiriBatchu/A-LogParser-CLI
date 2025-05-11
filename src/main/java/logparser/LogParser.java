package logparser;

import java.io.*;
import java.util.*;

public class LogParser {
  private LogAggregator apmAggregator = new APMAggregator();
  private LogAggregator appAggregator = new ApplicationAggregator();
  private LogAggregator reqAggregator = new RequestAggregator();

  public void parseFile(String filename) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = br.readLine()) != null) {
        LogEntry entry = LogEntryFactory.createLogEntry(line);
        if (entry instanceof APMLogEntry) {
          apmAggregator.add(entry);
        } else if (entry instanceof ApplicationLogEntry) {
          appAggregator.add(entry);
        } else if (entry instanceof RequestLogEntry) {
          reqAggregator.add(entry);
        }
      }
    }
  }

  public LogAggregator getApmAggregator() {
    return apmAggregator;
  }

  public LogAggregator getAppAggregator() {
    return appAggregator;
  }

  public LogAggregator getReqAggregator() {
    return reqAggregator;
  }
}
