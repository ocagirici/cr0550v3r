package com.crossover.trial.awa.airport;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.*;

/**
 * A simple airport loader which reads a file from disk and sends entries to the webservice
 *
 * TODO: Implement the Airport Loader
 * 
 * @author code test administrator
 */
public class AirportLoader {

    /** end point for read queries */
    private WebTarget query; // CR: Needs setter and getter
    

    /** end point to supply updates */
    private WebTarget collect; // CR: Needs setter and getter

    public AirportLoader() {
        Client client = ClientBuilder.newClient();
        setQuery(client.target("http://localhost:8080/query"));
        setCollect(client.target("http://localhost:8080/collect"));
    }

    public void upload(InputStream airportDataStream) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(airportDataStream));
        String l = null;
        while ((l = reader.readLine()) != null) {
            break;
        }
    }

    public static void main(String args[]) throws IOException{
        File airportDataFile = null;
		try {
			airportDataFile = new File(args[0]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Should be surrounded with try-catch
      

        AirportLoader al = new AirportLoader();
        al.upload(new FileInputStream(airportDataFile));
        System.exit(0);
    }

	public WebTarget getQuery() {
		return query;
	}

	public void setQuery(WebTarget query) {
		this.query = query;
	}

	public WebTarget getCollect() {
		return collect;
	}

	public void setCollect(WebTarget collect) {
		this.collect = collect;
	}
}
