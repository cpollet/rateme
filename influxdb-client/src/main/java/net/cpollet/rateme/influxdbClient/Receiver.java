package net.cpollet.rateme.influxdbClient;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by cpollet on 01.02.17.
 */
@Component
@Log4j
public class Receiver {
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(byte[] message) throws UnsupportedEncodingException {
        String body = new String(message, "UTF-8");
        log.info("Received <" + body + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
