package net.cpollet.rateme.influxdb;

import lombok.extern.log4j.Log4j;
import net.cpollet.rateme.influxdb.messaging.RateConverter;

/**
 * Created by cpollet on 01.02.17.
 */
@Log4j
public class RatingService {
    private final RateConverter rateConverter;

    public RatingService(RateConverter rateConverter) {
        this.rateConverter = rateConverter;
    }

    public void handle(String string) {
        Rate rate = rateConverter.convert(string);

        log.info(rate);

        
    }
}
