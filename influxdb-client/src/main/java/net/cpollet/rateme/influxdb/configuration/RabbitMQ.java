package net.cpollet.rateme.influxdb.configuration;

import net.cpollet.rateme.influxdb.DefaultRatingService;
import net.cpollet.rateme.influxdb.messaging.RatingService;
import net.cpollet.rateme.influxdb.messaging.Receiver;
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
    private RatingService ratingService;

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

    @Bean(name = "rabbitmq.queue")
    public Queue queue() {
        return new Queue(configuration().getProperty("rabbitmq.queueName"), false);
    }

    @Bean(name = "rabbitmq.exchange")
    public FanoutExchange exchange() {
        return new FanoutExchange(configuration().getProperty("rabbitmq.exchangeName"));
    }

    @Bean(name = "rabbitmq.binding")
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange());
    }

    @Bean(name = "rabbitmq.container")
    public SimpleMessageListenerContainer container() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames(configuration().getProperty("rabbitmq.queueName"));
        container.setMessageListener(listenerAdapter());
        return container;
    }

    @Bean(name = "rabbitmq.listenerAdalter")
    public MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(receiver(), "receiveMessage");
    }

    @Bean(name = "rabbitmq.receiver")
    public Object receiver() {
        return new Receiver(ratingService);
    }
}
