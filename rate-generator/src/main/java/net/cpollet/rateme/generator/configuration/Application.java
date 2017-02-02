package net.cpollet.rateme.generator.configuration;

import net.cpollet.rateme.generator.RateGenerator;
import net.cpollet.rateme.generator.RatePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


/**
 * Created by cpollet on 02.02.17.
 */
@SpringBootApplication
@Import({RabbitMQ.class})
public class Application {
    @Autowired
    private RatePublisher ratePublisher;

    @Autowired
    private ConfigurableApplicationContext applicationContext;

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

    @Bean(name = "rateme.rateGenerator")
    public RateGenerator rateGenerator() {
        return new RateGenerator(applicationContext, ratePublisher, min(), max(), usernames(), contexts());
    }

    private List<String> contexts() {
        return Arrays.asList(configuration().getProperty("rateme.contexts").split(","));
    }

    private List<String> usernames() {
        return Arrays.asList(configuration().getProperty("rateme.usernames").split(","));
    }

    private int max() {
        return Integer.parseInt(configuration().getProperty("rateme.rate.max"));
    }

    private int min() {
        return Integer.parseInt(configuration().getProperty("rateme.rate.min"));
    }

}
