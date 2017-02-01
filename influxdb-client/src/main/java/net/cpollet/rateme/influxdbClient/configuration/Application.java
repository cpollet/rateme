package net.cpollet.rateme.influxdbClient.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Created by cpollet on 01.02.17.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"net.cpollet.rateme.influxdbClient"})
@Import({RabbitMQ.class})
public class Application {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }
}
