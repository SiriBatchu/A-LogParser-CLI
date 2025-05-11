package logparser;

import java.util.Map;

public class RequestLogEntry extends LogEntry {
  public String url;
  public int responseStatus;
  public int responseTimeMs;

  public RequestLogEntry(String url, int responseStatus, int responseTimeMs) {
    this.url = url;
    this.responseStatus = responseStatus;
    this.responseTimeMs = responseTimeMs;
  }

  public static RequestLogEntry fromMap(Map<String, String> map) {
    String url = map.get("request_url");
    int status = Integer.parseInt(map.get("response_status"));
    int time = Integer.parseInt(map.get("response_time_ms"));
    return new RequestLogEntry(url, status, time);
  }
}