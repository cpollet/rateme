package net.cpollet.rateme.generator.rabbitmq;

import lombok.extern.log4j.Log4j;
import net.cpollet.rateme.generator.Rate;
import net.cpollet.rateme.generator.RatePublisher;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Created by cpollet on 02.02.17.
 */
@Log4j
public class RabbitMQRatePublisher implements RatePublisher {
    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;

    public RabbitMQRatePublisher(RabbitTemplate rabbitTemplate, String exchangeName) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
    }

    @Override
    public void publish(Rate rate) {
        log.info("Sending bytes " + rate.toString().getBytes() + " to " + exchangeName);
        rabbitTemplate.send(exchangeName, null, new Message(rate.toString().getBytes(), new MessageProperties()));
    }
}