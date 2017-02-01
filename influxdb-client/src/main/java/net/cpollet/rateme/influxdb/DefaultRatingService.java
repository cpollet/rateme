package net.cpollet.rateme.influxdb;

import lombok.extern.log4j.Log4j;
import net.cpollet.rateme.influxdb.messaging.RatingService;

/**
 * Created by cpollet on 01.02.17.
 */
@Log4j
public class DefaultRatingService implements RatingService {
    private final RateConverter rateConverter;
    private final Store store;

    public DefaultRatingService(RateConverter rateConverter, Store store) {
        this.rateConverter = rateConverter;
        this.store = store;
    }

    @Override
    public void handle(String string) {
        Rate rate = rateConverter.convert(string);

        log.info(rate);

        store.store(rate);
    }
}
