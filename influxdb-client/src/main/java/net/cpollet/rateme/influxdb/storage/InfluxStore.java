package net.cpollet.rateme.influxdb.storage;

import lombok.extern.log4j.Log4j;
import net.cpollet.rateme.influxdb.Rate;
import net.cpollet.rateme.influxdb.Store;
import org.influxdb.dto.Point;
import org.springframework.data.influxdb.InfluxDBTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created by cpollet on 01.02.17.
 */
@Log4j
public class InfluxStore implements Store {
    private final InfluxDBTemplate<Point> influxDBTemplate;

    public InfluxStore(InfluxDBTemplate<Point> influxDBTemplate) {
        this.influxDBTemplate = influxDBTemplate;
        influxDBTemplate.createDatabase();
    }

    @Override
    public void store(Rate rate) {
        Point p = Point.measurement("rates")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("context", rate.context())
                .tag("username", rate.username())
                .addField("rate", rate.rate())
                .build();
        influxDBTemplate.write(p);

        log.info("Wrote " + p + " in influxDB");
    }
}
