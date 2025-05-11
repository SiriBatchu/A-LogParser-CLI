package logparser;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationAggregatorTest {
  @Test
  void testAggregation() {
    ApplicationAggregator aggregator = new ApplicationAggregator();
    aggregator.add(new ApplicationLogEntry("ERROR"));
    aggregator.add(new ApplicationLogEntry("INFO"));
    aggregator.add(new ApplicationLogEntry("ERROR"));
    aggregator.add(new ApplicationLogEntry("DEBUG"));

    Map<String, Integer> result = (Map<String, Integer>) aggregator.getResult();

    assertEquals(2, result.get("ERROR"));
    assertEquals(1, result.get("INFO"));
    assertEquals(1, result.get("DEBUG"));
  }
}
