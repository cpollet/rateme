package net.cpollet.rateme.influxdbClient.configuration;

import net.cpollet.rateme.influxdbClient.Receiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
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
 * Created by cpollet on 01.02.17.
 */
@Configuration
public class RabbitMQ {
    @Autowired
    private Receiver receiver;

    @Bean
    public Properties configuration() {
        Resource resource = new ClassPathResource("/rabbitmq.properties");
        try {
            return PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            throw new RuntimeException("Unable to start application, missing rabbitmq.properties file");
        }
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        try {
            return new CachingConnectionFactory(new URI(configuration().getProperty("rabbitmq.uri")));
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unable to start application, invalid rabbitmq.uri");
        }
    }

    @Bean
    public Queue queue() {
        return new Queue(configuration().getProperty("rabbitmq.queueName"), false);
    }

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(configuration().getProperty("rabbitmq.exchangeName"));
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange());
    }

    @Bean
    public SimpleMessageListenerContainer container() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames(configuration().getProperty("rabbitmq.queueName"));
        container.setMessageListener(listenerAdapter());
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
