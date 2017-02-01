package net.cpollet.rateme.influxdb.configuration;

import net.cpollet.rateme.influxdb.DefaultRateConverter;
import net.cpollet.rateme.influxdb.DefaultRatingService;
import net.cpollet.rateme.influxdb.Store;
import net.cpollet.rateme.influxdb.messaging.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * Created by cpollet on 01.02.17.
 */
@SpringBootApplication
@Import({RabbitMQ.class, InfluxDB.class})
public class Application {
    @Autowired
    private Store store;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public DefaultRateConverter rateConverter() {
        return new DefaultRateConverter();
    }

    @Bean
    public RatingService ratingService() {
        return new DefaultRatingService(rateConverter(), store);
    }
}
