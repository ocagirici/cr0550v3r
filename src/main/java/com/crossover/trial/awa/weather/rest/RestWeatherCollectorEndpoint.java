package com.crossover.trial.awa.weather.rest;

import com.crossover.trial.awa.airport.Airport;
import com.crossover.trial.awa.airport.AirportData;

import com.crossover.trial.awa.data.*;
import com.crossover.trial.awa.exception.IncorrectDataPointName;
import com.crossover.trial.awa.exception.WeatherException;
import com.crossover.trial.awa.thread.AtmosphericInformation;
import com.crossover.trial.awa.thread.AtmosphericInformationCreator;
import com.crossover.trial.awa.thread.AtmosphericInformationUpdater;
import com.crossover.trial.awa.weather.WeatherCollector;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.crossover.trial.awa.weather.rest.RestWeatherQueryEndpoint.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * A REST implementation of the WeatherCollector API. Accessible only to airport weather collection
 * sites via secure VPN.
 *
 * @author code test administrator
 */

@Path("/collect")
public class RestWeatherCollectorEndpoint implements WeatherCollector {
	AirportDataManager airportDataManager;
	public RestWeatherCollectorEndpoint(AirportDataManager airportDataManager) {
		this.airportDataManager = airportDataManager;
	}
	
    public final static Logger LOGGER = Logger.getLogger(RestWeatherCollectorEndpoint.class.getName());

    /** shared gson json to object factory */
    public final static Gson gson = new Gson();


    @GET
    @Path("/ping")
    @Override
    public Response ping() {
        return Response.status(Response.Status.OK).entity("ready").build();
    }
    
    /**
     * Update the airports weather data with the collected data.
     *
     * @param iataCode the 3 letter IATA code
     * @param dp a datapoint object holding pointType data
     * @throws Exception 
     */

    @POST
    @Path("/weather/{iata}/{pointType}")
    @Override
    public Response updateWeather(@PathParam("iata") String iataCode,
                                  String datapointJson) throws JsonSyntaxException, Exception {
        try {
        	AtmosphericInformation info = airportDataManager.get(iataCode).getInfo();
        	if(info == null)
        		info = new AtmosphericInformationCreator(gson.fromJson(datapointJson, DataPoint.class)).create();
            updateAtmosphericInformation(info, gson.fromJson(datapointJson, DataPoint.class));
            airportDataManager.addInfo(info);
        } catch (WeatherException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/airports")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getAirports() {
        Set<String> retval = new HashSet<>();
        for (Airport airport : airportDataManager.getAirports()) {
            retval.add(airport.getData().getIata());
        }
        return Response.status(Response.Status.OK).entity(retval).build();
    }

    @GET
    @Path("/airport/{iata}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getAirport(@PathParam("iata") String iata) {
        AirportData ad = airportDataManager.get(iata).getData();
        return Response.status(Response.Status.OK).entity(ad).build();
    }

    @POST
    @Path("/airport/{iata}/{lat}/{long}")
    @Override
    public Response addAirport(@PathParam("iata") String iata,
                               @PathParam("lat") String latString,
                               @PathParam("long") String longString) {
        addAirport(iata, Double.valueOf(latString), Double.valueOf(longString));
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/airport/{iata}")
    @Override
    public Response deleteAirport(@PathParam("iata") String iata) {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

    //
    // Internal support methods
    //

   
    

    /**
     * update atmospheric information with the given data point for the given point type
     *
     * @param ai the atmospheric information object to update
     * @param pointType the data point type as a string
     * @param dp the actual data point
     * @throws Exception 
     */
    public void updateAtmosphericInformation(AtmosphericInformation ai, DataPoint dp) throws Exception {
    	
        if (dp.getType().compareToIgnoreCase("wind") == 0) {
        	Wind wind = (Wind) DataPointBuilder.build(dp);
            new AtmosphericInformationUpdater(ai).updateWind(wind);
        }
        
        if (dp.getType().equalsIgnoreCase("temperature")) {
        	Temperature temperature = (Temperature) DataPointBuilder.build(dp);
            new AtmosphericInformationUpdater(ai).updateTemperature(temperature);
        }
        
        if (dp.getType().equalsIgnoreCase("humidty")) {
        	Humidty humidty = (Humidty) DataPointBuilder.build(dp);
            new AtmosphericInformationUpdater(ai).updateHumidty(humidty);
        }
        
        if (dp.getType().equalsIgnoreCase("precipitation")) {
        	Precipitation precipitation = (Precipitation) DataPointBuilder.build(dp);
            new AtmosphericInformationUpdater(ai).updatePrecipitation(precipitation);
        }
        
        if (dp.getType().equalsIgnoreCase("cloudcover")) {
        	CloudCover cloudCover = (CloudCover) DataPointBuilder.build(dp);
            new AtmosphericInformationUpdater(ai).updateCloudCover(cloudCover);
        }
        
        throw new IncorrectDataPointName();
    }

    /**
     * Add a new known airport to our list.
     *
     * @param iataCode 3 letter code
     * @param latitude in degrees
     * @param longitude in degrees
     *
     * @return the added airport
     */
    public void addAirport(String iataCode, double latitude, double longitude) { 
        AirportData ad = new AirportData(iataCode, latitude, longitude);
        airportDataManager.addAirport(ad);
    }

    /**
     * A dummy init method that loads hard coded data
     */
    public void init() {
        airportDataManager.clear();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("airports.dat");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String l = null;

        try {
            while ( (l = br.readLine()) != null) {
                String[] split = l.split(",");
                addAirport(split[0],
                        Double.valueOf(split[1]),
                        Double.valueOf(split[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
