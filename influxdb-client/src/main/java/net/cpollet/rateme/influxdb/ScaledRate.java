package net.cpollet.rateme.influxdb;

/**
 * Created by cpollet on 02.02.17.
 */
public class ScaledRate implements Rate {
    private final Rate rate;
    private final long min;
    private final long max;

    public ScaledRate(Rate rate, int min, int max) {
        this.rate = rate;
        this.min = min;
        this.max = max;
    }

    @Override
    public String context() {
        return rate.context();
    }

    @Override
    public Number rate() {
        double localRate = rate.rate().intValue() - min;
        double localMax = max - min;

        return localRate / localMax;
    }

    @Override
    public String username() {
        return rate.username();
    }
}
