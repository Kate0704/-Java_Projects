package kate.zhevniak.statistics;

import kate.zhevniak.domain.Building;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatisticsWriterTest {
    Building building;

    @BeforeEach
    void before() {
        building = new Building(3, 2, 5);
    }

    @SneakyThrows
    @Test
    void run() {
        Executor executor = Executors.newSingleThreadExecutor();
        assertDoesNotThrow(() -> executor.execute(building::run));
        TimeUnit.MILLISECONDS.sleep(1000);
        building.setRunning(false);
        Thread.currentThread().interrupt();

        StatisticsWriter writer = building.getStatisticsWriter();
        assertThat(writer.getStatistics().length == 2, is(true));
        assertThat(Objects.equals(writer.getStatisticsOfElevator(0),
                building.getElevators()[0].getStatistics()), is(true));
        assertThat(Objects.equals(writer.getStatisticsOfElevator(1),
                building.getElevators()[1].getStatistics()), is(true));
    }

    @SneakyThrows
    @Test
    void writeToFile() {
        StatisticsWriter writer = spy(building.getStatisticsWriter());

        writer.writeStatisticsToFile();

        verify(writer, only()).writeStatisticsToFile();
    }
}