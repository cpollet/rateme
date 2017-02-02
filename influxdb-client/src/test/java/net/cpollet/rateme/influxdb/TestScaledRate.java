package net.cpollet.rateme.influxdb;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by cpollet on 02.02.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestScaledRate {
    @Mock
    private Rate rate;

    @Test
    public void scaleMinValueStartingAt0() {
        // GIVEN
        Mockito.when(rate.rate()).thenReturn(0);

        // WHEN
        Rate scaledRate = new ScaledRate(rate, 0, 1);

        // THEN
        Assert.assertThat(scaledRate.rate(), IsEqual.equalTo(0.0));
    }

    @Test
    public void scaleMinValue() {
        // GIVEN
        Mockito.when(rate.rate()).thenReturn(1);

        // WHEN
        Rate scaledRate = new ScaledRate(rate, 1, 2);

        // THEN
        Assert.assertThat(scaledRate.rate(), IsEqual.equalTo(0.0));
    }

    @Test
    public void scaleMaxValue() {
        // GIVEN
        Mockito.when(rate.rate()).thenReturn(2);

        // WHEN
        Rate scaledRate = new ScaledRate(rate, 1, 2);

        // THEN
        Assert.assertThat(scaledRate.rate(), IsEqual.equalTo(1.0));
    }

    @Test
    public void scaleMaxValueStartingAt0() {
        // GIVEN
        Mockito.when(rate.rate()).thenReturn(1);

        // WHEN
        Rate scaledRate = new ScaledRate(rate, 0, 1);

        // THEN
        Assert.assertThat(scaledRate.rate(), IsEqual.equalTo(1.0));
    }

    @Test
    public void scaleMidValue() {
        // GIVEN
        Mockito.when(rate.rate()).thenReturn(2);

        // WHEN
        Rate scaledRate = new ScaledRate(rate, 1, 3);

        // THEN
        Assert.assertThat(scaledRate.rate(), IsEqual.equalTo(0.5));
    }

    @Test
    public void scaleMidValueStartingAt0() {
        // GIVEN
        Mockito.when(rate.rate()).thenReturn(1);

        // WHEN
        Rate scaledRate = new ScaledRate(rate, 0, 2);

        // THEN
        Assert.assertThat(scaledRate.rate(), IsEqual.equalTo(0.5));
    }
}
