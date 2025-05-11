package logparser;

import java.io.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonWriter {
  private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

  public static void writeJson(String filename, Object data) throws IOException {
    mapper.writeValue(new File(filename), data);
  }
}
