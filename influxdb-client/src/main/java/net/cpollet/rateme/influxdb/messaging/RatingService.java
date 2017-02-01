package net.cpollet.rateme.influxdb.messaging;

/**
 * Created by cpollet on 01.02.17.
 */
public interface RatingService {
    void handle(String string);
}
