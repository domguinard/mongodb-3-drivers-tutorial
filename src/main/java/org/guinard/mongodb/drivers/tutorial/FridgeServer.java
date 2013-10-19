/*
 * (c) Dominique Guinard (www.guinard.org)
 * 
 */
package org.guinard.mongodb.drivers.tutorial;

import javax.xml.ws.Response;
import spark.Request;
import spark.Route;
import spark.Spark;

/**
 *
 * @author domguinard
 */
public class FridgeServer {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Spark.get(new Route("/hello") {
			@Override
			public Object handle(Request rqst, spark.Response rspns) {
				return "Hello World!";
			}
		});
	}
}
