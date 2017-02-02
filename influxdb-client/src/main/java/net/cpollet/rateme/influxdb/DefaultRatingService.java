package net.cpollet.rateme.influxdb;

import lombok.extern.log4j.Log4j;
import net.cpollet.rateme.influxdb.messaging.RatingService;

/**
 * Created by cpollet on 01.02.17.
 */
@Log4j
public class DefaultRatingService implements RatingService {
    private final RateFactory rateFactory;
    private final Store store;
    private final long min;
    private final long max;

    public DefaultRatingService(RateFactory rateFactory, Store store, long min, long max) {
        this.rateFactory = rateFactory;
        this.store = store;
        this.min = min;
        this.max = max;
    }

    @Override
    public void handle(String string) {
        Rate rate = rateFactory.create(string);

        log.info(rate);

        store.store(new ScaledRate(rate, min, max));
    }
}
