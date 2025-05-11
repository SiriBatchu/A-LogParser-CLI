package logparser;

import java.util.HashMap;
import java.util.Map;

public class APMLogEntry extends LogEntry {
  public String metric;
  public double value;

  public APMLogEntry(String metric, double value) {
    this.metric = metric;
    this.value = value;
  }

  public static APMLogEntry fromMap(Map<String, String> map) {
    String metric = map.get("metric");
    double value = Double.parseDouble(map.get("value"));
    return new APMLogEntry(metric, value);
  }
}