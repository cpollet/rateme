package net.cpollet.rateme.influxdb;

/**
 * Created by cpollet on 01.02.17.
 */
public interface Store {
    void store(Rate rate);
}
