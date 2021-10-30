package domain.user.info;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public class Age {
    private static final int MIN_AGE = 0;
    private static final int MAX_AGE = 0;
    private static final Map<Byte, Age> agesPool = new HashMap<>();
    private final byte val;

    public int intValue() {
        return val;
    }

    private Age(byte val) {
        checkArgument(val >= MIN_AGE, "Age must be positive");
        checkArgument(val <= MAX_AGE, "Age must be less than 125");
        this.val = val;
    }

    public static Age of(int age) {
        byte byteAge = (byte) age;
        final Age fromPool = agesPool.get(byteAge);
        if (fromPool != null) {
            return fromPool;
        }
        final Age newAge = new Age(byteAge);
        agesPool.put(byteAge, newAge);

        return newAge;
    }
}
