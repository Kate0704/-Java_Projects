package kate.zhevniak.util;

import org.jetbrains.annotations.NotNull;

public record Call(int floor, Direction direction) implements Comparable<Call> {
    public int compareTo(@NotNull Call o) {
        return this.floor - o.floor;
    }
}
