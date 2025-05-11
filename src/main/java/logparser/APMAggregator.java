package logparser;

import java.util.*;

public class APMAggregator implements LogAggregator {
  private Map<String, List<Double>> metrics = new HashMap<>();

  @Override
  public void add(LogEntry entry) {
    if (!(entry instanceof APMLogEntry))
      return;
    APMLogEntry apm = (APMLogEntry) entry;
    metrics.computeIfAbsent(apm.metric, k -> new ArrayList<>()).add(apm.value);
  }

  @Override
  public Object getResult() {
    Map<String, Map<String, Object>> result = new HashMap<>();
    for (String metric : metrics.keySet()) {
      List<Double> values = metrics.get(metric);
      Collections.sort(values);
      double min = values.get(0);
      double max = values.get(values.size() - 1);
      double avg = values.stream().mapToDouble(Double::doubleValue).average().orElse(0);
      double median = values.size() % 2 == 0 ? (values.get(values.size() / 2 - 1) + values.get(values.size() / 2)) / 2.0
          : values.get(values.size() / 2);
      Map<String, Object> agg = new HashMap<>();
      agg.put("minimum", min);
      agg.put("median", median);
      agg.put("average", avg);
      agg.put("max", max);
      result.put(metric, agg);
    }
    return result;
  }
}