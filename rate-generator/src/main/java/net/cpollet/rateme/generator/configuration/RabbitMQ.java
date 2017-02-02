package net.cpollet.rateme.generator.configuration;

import net.cpollet.rateme.generator.rabbitmq.RabbitMQRatePublisher;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * Created by cpollet on 02.02.17.
 */
@Configuration
public class RabbitMQ {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean(name = "rabbitmq.configuration")
    public Properties configuration() {
        Resource resource = new ClassPathResource("/rabbitmq.properties");
        try {
            return PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            throw new RuntimeException("Unable to start application, missing rabbitmq.properties file");
        }
    }

    @Bean(name = "rabbitmq.connectionFactory")
    public ConnectionFactory connectionFactory() {
        try {
            return new CachingConnectionFactory(new URI(configuration().getProperty("rabbitmq.uri")));
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unable to start application, invalid rabbitmq.uri");
        }
    }

    @Bean(name = "rabbitmq.ratePublisher")
    public RabbitMQRatePublisher ratePublisher() {
        return new RabbitMQRatePublisher(rabbitTemplate, configuration().getProperty("rabbitmq.exchangeName"));
    }
}
