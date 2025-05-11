package logparser;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class APMAggregatorTest {
  @Test
  void testAggregation() {
    APMAggregator aggregator = new APMAggregator();
    aggregator.add(new APMLogEntry("cpu_usage_percent", 60));
    aggregator.add(new APMLogEntry("cpu_usage_percent", 90));
    aggregator.add(new APMLogEntry("cpu_usage_percent", 78));
    aggregator.add(new APMLogEntry("memory_usage_percent", 5));
    aggregator.add(new APMLogEntry("memory_usage_percent", 10));
    aggregator.add(new APMLogEntry("memory_usage_percent", 78));

    Map<String, Map<String, Object>> result = (Map<String, Map<String, Object>>) aggregator.getResult();

    assertTrue(result.containsKey("cpu_usage_percent"));
    assertEquals(60.0, result.get("cpu_usage_percent").get("minimum"));
    assertEquals(90.0, result.get("cpu_usage_percent").get("max"));
    assertEquals(78.0, result.get("cpu_usage_percent").get("median"));
    assertEquals((60.0 + 90.0 + 78.0) / 3, (double) result.get("cpu_usage_percent").get("average"), 0.01);
  }
}