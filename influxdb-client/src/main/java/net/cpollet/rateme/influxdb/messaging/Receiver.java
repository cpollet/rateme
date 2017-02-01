package net.cpollet.rateme.influxdb.messaging;

import lombok.extern.log4j.Log4j;
import net.cpollet.rateme.influxdb.RatingService;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by cpollet on 01.02.17.
 */
@Log4j
public class Receiver {
    private final RatingService ratingService;
    private CountDownLatch latch = new CountDownLatch(1);

    public Receiver(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    public void receiveMessage(byte[] message) throws UnsupportedEncodingException {
        String body = new String(message, "UTF-8");
        log.info("Received <" + body + ">");

        ratingService.handle(body);

        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
