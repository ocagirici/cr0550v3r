package com.crossover.trial.weather;

import com.crossover.trial.awa.data.DataPoint;
import com.crossover.trial.awa.data.DataPointBuilder;
import com.crossover.trial.awa.thread.AtmosphericInformation;
import com.crossover.trial.awa.weather.WeatherCollector;
import com.crossover.trial.awa.weather.rest.AirportDataManager;
import com.crossover.trial.awa.weather.rest.RestWeatherCollectorEndpoint;
import com.crossover.trial.awa.weather.rest.RestWeatherQueryEndpoint;
import com.crossover.trial.awa.weather.rest.WeatherQueryEndpoint;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class WeatherEndpointTest {

	AirportDataManager _manager = new AirportDataManager();
    private RestWeatherQueryEndpoint _query = new RestWeatherQueryEndpoint(_manager);
    private RestWeatherCollectorEndpoint _update = new RestWeatherCollectorEndpoint(_manager);
    private Gson _gson = new Gson();

    private DataPoint _dp;
    @Before
    public void setUp() throws Exception {
        _update.init();
        _dp = DataPointBuilder.build("wind", 10, 20, 30, 22, 10);
        _update.updateWeather("BOS", _gson.toJson(_dp));
        _query.get("BOS", "0").getEntity();
    }

    @Test
    public void testPing() throws Exception {
        String ping = _query.ping();
        JsonElement pingResult = new JsonParser().parse(ping);
        assertEquals(1, pingResult.getAsJsonObject().get("datasize").getAsInt());
        assertEquals(5, pingResult.getAsJsonObject().get("iata_freq").getAsJsonObject().entrySet().size());
    }

    @Test
    public void testGet() throws Exception {
        List<AtmosphericInformation> ais = (List<AtmosphericInformation>) _query.get("BOS", "0").getEntity();
        assertEquals(ais.get(0).getWind(), _dp);
    }

    @Test
    public void testGetNearby() throws Exception {
        // check datasize response
        _update.updateWeather("JFK", _gson.toJson(_dp));
        _update.updateWeather("EWR", _gson.toJson(_dp));
        _update.updateWeather("LGA", _gson.toJson(_dp));

        List<AtmosphericInformation> ais = (List<AtmosphericInformation>) _query.get("JFK", "200").getEntity();
        assertEquals(3, ais.size());
    }

    @Test
    public void testUpdate() throws Exception {

        DataPoint windDp = DataPointBuilder.build("wind", 10,20,30,22,10);
        _update.updateWeather("BOS", _gson.toJson(windDp));
        _query.get("BOS", "0").getEntity();

        String ping = _query.ping();
        JsonElement pingResult = new JsonParser().parse(ping);
        assertEquals(1, pingResult.getAsJsonObject().get("datasize").getAsInt());

        DataPoint cloudCoverDp = DataPointBuilder.build("cloudcover", 10,60,100,50, 4);
        _update.updateWeather("BOS", _gson.toJson(cloudCoverDp));

        List<AtmosphericInformation> ais = (List<AtmosphericInformation>) _query.get("BOS", "0").getEntity();
        assertEquals(ais.get(0).getWind(), windDp);
        assertEquals(ais.get(0).getCloudCover(), cloudCoverDp);
    }

}