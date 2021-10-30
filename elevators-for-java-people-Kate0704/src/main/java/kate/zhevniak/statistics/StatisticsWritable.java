package kate.zhevniak.statistics;

public interface StatisticsWritable {
    int WRITING_FREQUENCY = 5000;
    String FILE_NAME = "statistics.txt";
    String TIMESTAMP_PATTERN = "\n\n-----> %s";
    String HEADER_PATTERN = """
            
            |----------|-------------|----------------|-----------------------|------------------------|-------------|
            |          |  number of  |   number of    |  most frequent floor  |  most frequent floor   |  number of  |
            | Elevator |    calls    |  transported   | to pick up passengers | to drop off passengers |   passed    |
            |          |             |   passengers   |                       |                        |   floors    |
            |----------|-------------|----------------|-----------------------|------------------------|-------------|
            """;
    String WRITING_PATTERN = """
            | %8d | %11d | %14d | %21s | %22s | %11d |
            |----------|-------------|----------------|-----------------------|------------------------|-------------|
            """;

    void writeStatisticsToFile();
}
