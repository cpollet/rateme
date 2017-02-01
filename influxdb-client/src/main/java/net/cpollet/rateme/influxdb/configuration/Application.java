package net.cpollet.rateme.influxdb.configuration;

import net.cpollet.rateme.influxdb.DefaultRateConverter;
import net.cpollet.rateme.influxdb.RatingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Created by cpollet on 01.02.17.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"net.cpollet.rateme.influxdb"})
@Import({RabbitMQ.class})
public class Application {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    DefaultRateConverter rateConverter() {
        return new DefaultRateConverter();
    }

    @Bean
    RatingService ratingService() {
        return new RatingService(rateConverter());
    }
}
