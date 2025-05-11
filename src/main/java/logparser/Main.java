package logparser;

public class Main {
  public static void main(String[] args) {
    String inputFile = null;
    for (int i = 0; i < args.length; i++) {
      if ("--file".equals(args[i]) && i + 1 < args.length) {
        inputFile = args[i + 1];
      }
    }
    if (inputFile == null) {
      System.err.println("Usage: java Main --file <filename.txt>");
      System.exit(1);
    }

    LogParser parser = new LogParser();
    try {
      parser.parseFile(inputFile);
      JsonWriter.writeJson("apm.json", parser.getApmAggregator().getResult());
      JsonWriter.writeJson("application.json", parser.getAppAggregator().getResult());
      JsonWriter.writeJson("request.json", parser.getReqAggregator().getResult());
      System.out.println("Aggregation complete. Output files: apm.json, application.json, request.json");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
