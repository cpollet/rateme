package net.cpollet.rateme.influxdb;

/**
 * Created by cpollet on 01.02.17.
 */
public interface Rate {
    String context();

    Number rate();

    String username();
}
