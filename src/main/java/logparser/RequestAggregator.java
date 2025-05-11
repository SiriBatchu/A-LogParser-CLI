package logparser;

import java.util.*;

public class RequestAggregator implements LogAggregator {
  private static class RouteStats {
    List<Integer> responseTimes = new ArrayList<>();
    Map<String, Integer> statusCounts = new HashMap<>();
  }

  private Map<String, RouteStats> routeStats = new HashMap<>();

  @Override
  public void add(LogEntry entry) {
    if (!(entry instanceof RequestLogEntry))
      return;
    RequestLogEntry req = (RequestLogEntry) entry;
    RouteStats stats = routeStats.computeIfAbsent(req.url, k -> new RouteStats());
    stats.responseTimes.add(req.responseTimeMs);
    String codeCat = getStatusCategory(req.responseStatus);
    stats.statusCounts.put(codeCat, stats.statusCounts.getOrDefault(codeCat, 0) + 1);
  }

  private String getStatusCategory(int code) {
    if (code >= 200 && code < 300)
      return "2XX";
    if (code >= 400 && code < 500)
      return "4XX";
    if (code >= 500 && code < 600)
      return "5XX";
    return "OTHER";
  }

  @Override
  public Object getResult() {
    Map<String, Object> result = new HashMap<>();
    for (String route : routeStats.keySet()) {
      RouteStats stats = routeStats.get(route);
      Collections.sort(stats.responseTimes);
      Map<String, Object> routeResult = new HashMap<>();
      if (!stats.responseTimes.isEmpty()) {
        int min = stats.responseTimes.get(0);
        int max = stats.responseTimes.get(stats.responseTimes.size() - 1);
        routeResult.put("min", min);
        routeResult.put("max", max);
        routeResult.put("50_percentile", percentile(stats.responseTimes, 50));
        routeResult.put("90_percentile", percentile(stats.responseTimes, 90));
        routeResult.put("95_percentile", percentile(stats.responseTimes, 95));
        routeResult.put("99_percentile", percentile(stats.responseTimes, 99));
      }
      Map<String, Integer> status = new HashMap<>();
      status.put("2XX", stats.statusCounts.getOrDefault("2XX", 0));
      status.put("4XX", stats.statusCounts.getOrDefault("4XX", 0));
      status.put("5XX", stats.statusCounts.getOrDefault("5XX", 0));
      Map<String, Object> routeObj = new HashMap<>();
      routeObj.put("response_times", routeResult);
      routeObj.put("status_codes", status);
      result.put(route, routeObj);
    }
    return result;
  }

  private int percentile(List<Integer> sorted, int pct) {
    if (sorted.isEmpty())
      return 0;
    int idx = (int) Math.ceil((pct / 100.0) * sorted.size()) - 1;
    idx = Math.max(0, Math.min(idx, sorted.size() - 1));
    return sorted.get(idx);
  }
}