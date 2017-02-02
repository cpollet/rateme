package net.cpollet.rateme.generator;

/**
 * Created by cpollet on 02.02.17.
 */
public class DefaultRate implements Rate {
    private final String context;
    private final String username;
    private final Number rate;

    public DefaultRate(String context, String username, int rate) {
        this.context = context;
        this.username = username;
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "context=" + context + ";username=" + username + ";rate=" + rate;
    }
}
