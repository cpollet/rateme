package net.cpollet.rateme.influxdb.configuration;

import net.cpollet.rateme.influxdb.DefaultRateFactory;
import net.cpollet.rateme.influxdb.DefaultRatingService;
import net.cpollet.rateme.influxdb.Store;
import net.cpollet.rateme.influxdb.messaging.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

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

    @Bean(name = "rateme.configuration")
    public Properties configuration() {
        Resource resource = new ClassPathResource("/rateme.properties");
        try {
            return PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            throw new RuntimeException("Unable to start application, missing rateme.properties file");
        }
    }

    @Bean
    public DefaultRateFactory rateConverter() {
        return new DefaultRateFactory();
    }

    @Bean
    public RatingService ratingService() {
        int min = Integer.parseInt(configuration().getProperty("rateme.rate.min"));
        int max = Integer.parseInt(configuration().getProperty("rateme.rate.max"));
        return new DefaultRatingService(rateConverter(), store, min, max);
    }
}
