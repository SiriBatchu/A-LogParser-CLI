package logparser;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RequestAggregatorTest {
  @Test
  void testAggregation() {
    RequestAggregator aggregator = new RequestAggregator();
    aggregator.add(new RequestLogEntry("/api/test", 200, 100));
    aggregator.add(new RequestLogEntry("/api/test", 404, 200));
    aggregator.add(new RequestLogEntry("/api/test", 500, 300));
    aggregator.add(new RequestLogEntry("/api/test", 200, 400));

    Map<String, Object> result = (Map<String, Object>) aggregator.getResult();
    assertTrue(result.containsKey("/api/test"));

    Map<String, Object> route = (Map<String, Object>) result.get("/api/test");
    Map<String, Object> responseTimes = (Map<String, Object>) route.get("response_times");
    Map<String, Integer> statusCodes = (Map<String, Integer>) route.get("status_codes");

    assertEquals(100, responseTimes.get("min"));
    assertEquals(400, responseTimes.get("max"));
    assertEquals(2, statusCodes.get("2XX"));
    assertEquals(1, statusCodes.get("4XX"));
    assertEquals(1, statusCodes.get("5XX"));
  }
}
