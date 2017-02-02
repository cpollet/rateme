package net.cpollet.rateme.influxdb.configuration;

import net.cpollet.rateme.influxdb.Store;
import net.cpollet.rateme.influxdb.storage.InfluxStore;
import org.influxdb.dto.Point;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.influxdb.InfluxDBConnectionFactory;
import org.springframework.data.influxdb.InfluxDBProperties;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.data.influxdb.converter.PointConverter;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by cpollet on 01.02.17.
 */
@Configuration
public class InfluxDB {
    @Bean(name = "influxdb.configuration")
    public InfluxDBProperties configuration() {
        Resource resource = new ClassPathResource("/influxdb.properties");
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            InfluxDBProperties influxDBProperties = new InfluxDBProperties();

            influxDBProperties.setUrl(properties.getProperty("influxdb.url"));
            influxDBProperties.setUsername(properties.getProperty("influxdb.username"));
            influxDBProperties.setPassword(properties.getProperty("influxdb.password"));
            influxDBProperties.setDatabase(properties.getProperty("influxdb.database"));
            influxDBProperties.setRetentionPolicy(properties.getProperty("influxdb.retention-policy"));

            return influxDBProperties;
        } catch (IOException e) {
            throw new RuntimeException("Unable to start application, missing influxdb.properties file");
        }
    }

    @Bean(name = "influxdb.connectionFactory")
    public InfluxDBConnectionFactory connectionFactory() {
        return new InfluxDBConnectionFactory(configuration());
    }

    @Bean(name = "influxdb.template")
    public InfluxDBTemplate<Point> influxDBTemplate() {
        return new InfluxDBTemplate<>(connectionFactory(), new PointConverter());
    }

    @Bean(name = "influxdb.store")
    public Store store() {
        return new InfluxStore(influxDBTemplate());
    }
}
