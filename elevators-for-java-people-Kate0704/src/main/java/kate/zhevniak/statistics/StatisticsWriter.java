package kate.zhevniak.statistics;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;

@Slf4j
public class StatisticsWriter implements Runnable, StatisticsWritable {
    private final Statistics[] statistics;

    public Statistics[] getStatistics() {
        return Arrays.copyOf(statistics, statistics.length);
    }

    public StatisticsWriter(int numberOfFloors, int numberOfElevators) {
        statistics = IntStream.range(0, numberOfElevators)
                .mapToObj(i -> new Statistics(numberOfFloors))
                .toArray(Statistics[]::new);
    }

    public Statistics getStatisticsOfElevator(int index) {
        return statistics[index];
    }

    @SuppressWarnings({"BusyWait"})
    @SneakyThrows
    public void run() {
        while (!Thread.interrupted()) {
            sleep(WRITING_FREQUENCY);
            writeStatisticsToFile();
        }
    }

    @SneakyThrows
    public void writeStatisticsToFile() {
        FileWriter fileWriter = new FileWriter(FILE_NAME, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        log.debug("writing statistics");
        printWriter.printf(TIMESTAMP_PATTERN, LocalDateTime.now());
        printWriter.print(HEADER_PATTERN);
        for (int i = 0; i < statistics.length; i++) {
            printWriter.printf(WRITING_PATTERN,
                    i,
                    statistics[i].getNumberOfCalls(),
                    statistics[i].getTransportedPassengersAmount(),
                    statistics[i].getFromFloorTop(),
                    statistics[i].getToFloorTop(),
                    statistics[i].getPassedFloorsAmount());
        }
        printWriter.close();
        fileWriter.close();
    }
}


