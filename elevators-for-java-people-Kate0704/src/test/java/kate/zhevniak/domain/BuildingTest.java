package kate.zhevniak.domain;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {
    Building building;

    @BeforeEach
    void before() {
        building = new Building(3, 1, 5);
    }

    @SneakyThrows
    @Test
    void run() {
        Executor executor = Executors.newSingleThreadExecutor();

        assertDoesNotThrow(() -> executor.execute(building::run));

        TimeUnit.MILLISECONDS.sleep(1000);

        Floor[] f = building.getFloors();

        assertThat(building.getElevators(), notNullValue());
        assertThat(building.getElevators().length, equalTo(1));
        assertThat(building.getFloors(), notNullValue());
        assertThat(building.getFloors().length, equalTo(3));
        assertThat(building.getWaitingCalls(), notNullValue());
        assertThat(building.getNumberOfElevators(), equalTo(1));
        assertThat(building.getNumberOfFloors(), equalTo(3));
        assertThat(building.getPeopleSpawnFrequency(), equalTo(5));
        assertThat(building.isRunning(), is(true));
        building.setRunning(false);

        Thread.currentThread().interrupt();
        assertThat(building.isRunning(), is(false));
    }
}