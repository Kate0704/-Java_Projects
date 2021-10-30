package kate.zhevniak;

import kate.zhevniak.domain.Building;
import kate.zhevniak.domain.Floor;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.Arrays;

public class Runner {
    @SneakyThrows
    public static void main(String[] args) {
//        Building building = new Building(5, 2, 200);
//        building.run();
        try {

        }
        catch (Exception e) {

        }
        finally {
            throw new RuntimeException("h");
        }
    }

     /* ToDo
             + проверить Lombok аннотации
             + почистить код алгоритма
             + алгоритм подбора лифта
             + проверить при большом потоке
             + проверить, что лифты не улетают в ±∞ этажей
             + задержка перед повторным вызовом
             + определение текущего направления лифта
             + поведение при перевесе
             + статистика
             + тесты
             + логи
             + документация
      */

}
