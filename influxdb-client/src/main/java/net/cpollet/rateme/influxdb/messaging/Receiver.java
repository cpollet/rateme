package net.cpollet.rateme.influxdb.messaging;

import lombok.extern.log4j.Log4j;

import java.io.UnsupportedEncodingException;

/**
 * Created by cpollet on 01.02.17.
 */
@Log4j
public class Receiver {
    private final RatingService ratingService;

    public Receiver(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    public void receiveMessage(byte[] message) throws UnsupportedEncodingException {
        String body = new String(message, "UTF-8");
        log.info("Received <" + body + ">");

        ratingService.handle(body);
    }
}
