package net.cpollet.rateme.influxdb;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cpollet on 01.02.17.
 * Handles messages in format context=xxx;rate=yyy;username=zzz
 */
public class DefaultRate implements Rate {
    private final String rate;
    private Map<String, String> result;

    public DefaultRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String context() {
        return parse().get("context");
    }

    private Map<String, String> parse() {
        if (result != null) {
            return result;
        }

        result = new HashMap<>();

        String[] parts = rate.split(";");
        for (String part : parts) {
            String[] keyValue = part.split("=");

            result.put(keyValue[0].trim(), keyValue[1].trim());
        }

        return result;
    }

    @Override
    public String rate() {
        return parse().get("rate");
    }

    @Override
    public String username() {
        return parse().get("username");
    }

    @Override
    public String toString() {
        return "Rate[context=" + context() + "; username=" + username() + "; rate=" + rate() + "]";
    }
}

