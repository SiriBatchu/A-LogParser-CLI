package logparser;

import java.util.Map;

public class ApplicationLogEntry extends LogEntry {
  public String level;

  public ApplicationLogEntry(String level) {
    this.level = level;
  }

  public static ApplicationLogEntry fromMap(Map<String, String> map) {
    String level = map.get("level");
    return new ApplicationLogEntry(level);
  }
}
