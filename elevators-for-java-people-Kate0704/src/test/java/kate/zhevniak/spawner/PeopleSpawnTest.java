package kate.zhevniak.spawner;

import kate.zhevniak.domain.Passenger;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
class PeopleSpawnTest {
    PeopleSpawn spawn;

    @BeforeEach
    void setUp() {
        spawn = new PeopleSpawn(this::acceptPassenger, 5, 20);
    }

    void acceptPassenger(Passenger passenger) {
        assertThat(passenger, notNullValue());
    }

    @SneakyThrows
    @Test
    void run() {
        Thread thread = new Thread(spawn);
        thread.start();
        TimeUnit.MILLISECONDS.sleep(20);
        thread.interrupt();
    }

    @Test
    void ToString() {
        assertThat(spawn.toString(), equalTo("PeopleSpawn(numberOfFloors=5, frequency=20)"));
    }

    @Test
    void getPassengerAcceptor() {
        assertThat(spawn.getPassengerAcceptor(), notNullValue());
    }

    @Test
    void getNumberOfFloors() {
        assertThat(spawn.getNumberOfFloors(), equalTo(5));
    }

    @Test
    void getFrequency() {
        assertThat(spawn.getFrequency(), equalTo(20));
    }

    @Test
    void getRandomizer() {
        assertThat(spawn.getRandomizer(), notNullValue());
    }

}