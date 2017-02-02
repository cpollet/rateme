package net.cpollet.rateme.generator;

import lombok.extern.log4j.Log4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Random;

/**
 * Created by cpollet on 02.02.17.
 */
@Log4j
public class RateGenerator implements CommandLineRunner {
    private final RatePublisher publisher;
    private final int min;
    private final int max;
    private final List<String> usernames;
    private final List<String> contexts;
    private final ConfigurableApplicationContext applicationContext;
    private final Random random;

    public RateGenerator(ConfigurableApplicationContext applicationContext, RatePublisher publisher,
                         int min, int max, List<String> usernames, List<String> contexts) {
        this.applicationContext = applicationContext;
        this.publisher = publisher;
        this.min = min;
        this.max = max;
        this.usernames = usernames;
        this.contexts = contexts;
        this.random = new Random();
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            String username = usernames.get(random.nextInt(usernames.size()));
            String context = contexts.get(random.nextInt(contexts.size()));
            int rateValue = random.nextInt(max - min + 1) + min;

            Rate rate = new DefaultRate(context, username, rateValue);

            log.info("Sending rate " + rate);

            publisher.publish(rate);

            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                applicationContext.close();
                return;
            }
        }
    }
}
