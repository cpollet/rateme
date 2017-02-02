package net.cpollet.rateme.influxdb;

/**
 * Created by cpollet on 01.02.17.
 */
public class DefaultRateFactory implements RateFactory {
    @Override
    public Rate create(String string) {
        return new DefaultRate(string);
    }
}
