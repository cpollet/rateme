package net.cpollet.rateme.influxdb;

import net.cpollet.rateme.influxdb.messaging.RateConverter;

/**
 * Created by cpollet on 01.02.17.
 */
public class DefaultRateConverter implements RateConverter {
    @Override
    public Rate convert(String string) {
        return new DefaultRate(string);
    }
}
