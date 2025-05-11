package logparser;

import java.util.HashMap;
import java.util.Map;

public class LogEntryFactory {
  public static LogEntry createLogEntry(String line) {
    Map<String, String> map = parseKeyValuePairs(line);
    if (map.containsKey("metric") && map.containsKey("value")) {
      try {
        return APMLogEntry.fromMap(map);
      } catch (Exception e) {
        return null;
      }
    } else if (map.containsKey("level")) {
      try {
        return ApplicationLogEntry.fromMap(map);
      } catch (Exception e) {
        return null;
      }
    } else if (map.containsKey("request_url") && map.containsKey("response_status")
        && map.containsKey("response_time_ms")) {
      try {
        return RequestLogEntry.fromMap(map);
      } catch (Exception e) {
        return null;
      }
    }
    return null; // Unknown or corrupted line
  }

  private static Map<String, String> parseKeyValuePairs(String line) {
    Map<String, String> map = new HashMap<>();
    String[] parts = line.split(" (?=\\w+\\=)");
    for (String part : parts) {
      int idx = part.indexOf('=');
      if (idx > 0) {
        String key = part.substring(0, idx);
        String value = part.substring(idx + 1).replaceAll("^\"|\"$", "");
        map.put(key, value);
      }
    }
    return map;
  }
}
