package net.cpollet.rateme.influxdb;

import net.cpollet.rateme.influxdb.Rate;

/**
 * Created by cpollet on 01.02.17.
 */
public interface RateConverter {
    Rate convert(String string);
}
