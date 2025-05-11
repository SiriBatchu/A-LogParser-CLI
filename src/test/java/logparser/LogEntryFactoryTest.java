package logparser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LogEntryFactoryTest {
  @Test
  void testAPMLogEntry() {
    String line = "timestamp=2024-02-24T16:22:15Z metric=cpu_usage_percent host=webserver1 value=72";
    LogEntry entry = LogEntryFactory.createLogEntry(line);
    assertTrue(entry instanceof APMLogEntry);
    assertEquals("cpu_usage_percent", ((APMLogEntry) entry).metric);
    assertEquals(72.0, ((APMLogEntry) entry).value, 0.01);
  }

  @Test
  void testApplicationLogEntry() {
    String line = "timestamp=2024-02-24T16:22:20Z level=INFO message=\"Scheduled maintenance starting\" host=webserver1";
    LogEntry entry = LogEntryFactory.createLogEntry(line);
    assertTrue(entry instanceof ApplicationLogEntry);
    assertEquals("INFO", ((ApplicationLogEntry) entry).level);
  }

  @Test
  void testRequestLogEntry() {
    String line = "timestamp=2024-02-24T16:22:25Z request_method=POST request_url=\"/api/update\" response_status=202 response_time_ms=200 host=webserver1";
    LogEntry entry = LogEntryFactory.createLogEntry(line);
    assertTrue(entry instanceof RequestLogEntry);
    assertEquals("/api/update", ((RequestLogEntry) entry).url);
    assertEquals(202, ((RequestLogEntry) entry).responseStatus);
    assertEquals(200, ((RequestLogEntry) entry).responseTimeMs);
  }

  @Test
  void testCorruptedLine() {
    String line = "this is not a valid log line";
    LogEntry entry = LogEntryFactory.createLogEntry(line);
    assertNull(entry);
  }
}
