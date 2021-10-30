package kate.zhevniak.randomizer;

import java.util.Random;

import static java.lang.Math.*;

public abstract class Randomizer<T> {
    final int DEFAULT_FROM = 0;

    public abstract T next();

    protected int randomizeFromTo(int from, int to, boolean normalDistribution) {
        Random random = new Random();
        // Gaussian ("normally") distributed double value with mean 0.0 and standard deviation 1.0
        double gaussianValue = random.nextGaussian();
        // absolute value of gaussianValue fractional part (as gaussianValue may be < 0 or > 1)
        double gaussianValueFromZeroToOne = abs(gaussianValue - floor(gaussianValue));
        double randomValue = normalDistribution ? gaussianValueFromZeroToOne : random();
        return (int) (randomValue * (to - from) + from);
    }

    protected int randomizeFromTo(int to) {
        return randomizeFromTo(DEFAULT_FROM, to, false);
    }
}
