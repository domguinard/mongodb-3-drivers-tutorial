/*
 * (c) Dominique Guinard (www.guinard.org)
 * 
 */
package org.guinard.mongodb.drivers.tutorial;

import mongoNative.DatasourceProviderMongoNative;
import java.util.logging.Level;
import java.util.logging.Logger;
import jongo.DatasourceProviderJongo;
import mongoNative.Fridge;
import static spark.Spark.*;
import spark.*;

/**
 *
 * @author domguinard
 */
public class FridgeServer {

    //private static DatasourceProviderMongoNative provider = new DatasourceProviderMongoNative();
    private static DatasourceProviderJongo provider = new DatasourceProviderJongo();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	post(new Route("/fridges", "application/json") {
	    @Override
	    public Object handle(Request request, Response response) {
		try {
		    provider.createFridge(request.body());
		    return reply(response, "Created", 200);
		} catch (InvalidJsonException ex) {
		    Logger.getLogger(FridgeServer.class.getName()).log(Level.SEVERE, null, ex);
		}
		return reply(response, "An error occured! Check your JSON!", 400);
	    }
	});

	get(new JsonGsonTransformerRoute("/fridges") {
	    @Override
	    public Object handle(Request request, Response response) {
		return provider.loadResources("Fridges");
	    }
	});

	put(new Route("/fridges/:name/products", "application/json") {
	    @Override
	    public Object handle(Request request, Response response) {
		try {
		    provider.putOrRemoveProductFromFridge(request.params(":name"), request.body());
		    return reply(response, "Updated", 200);
		} catch (InvalidJsonException ex) {
		    Logger.getLogger(FridgeServer.class.getName()).log(Level.SEVERE, null, ex);
		}
		return reply(response, "An error occured! Check your JSON!", 400);
	    }
	});
	
	put(new JsonGsonTransformerRoute("/fridges/:name/products") {
	    @Override
	    public Object handle(Request request, Response response) {
		try {
		    provider.putOrRemoveProductFromFridge(request.params(":name"), request.body());
		    return reply(response, "Updated", 200);
		} catch (InvalidJsonException ex) {
		    Logger.getLogger(FridgeServer.class.getName()).log(Level.SEVERE, null, ex);
		}
		return reply(response, "An error occured! Check your JSON!", 400);
	    }
	});
	
//	get(new JsonGsonTransformerRoute("/fridges/:name/products") {
//	    @Override
//	    public Object handle(Request request, Response response) {
//		try {
//		    //TODO!
//		    return reply(response, "Updated", 200);
//		} catch (InvalidJsonException ex) {
//		    Logger.getLogger(FridgeServer.class.getName()).log(Level.SEVERE, null, ex);
//		}
//		return reply(response, "An error occured! Check your JSON!", 400);
//	    }
//	});

	post(new JsonGsonTransformerRoute("/products") {
	    @Override
	    public Object handle(Request request, Response response) {
		try {
		    provider.createProduct(request.body());
		    return reply(response, "Created", 201);
		} catch (InvalidJsonException ex) {
		    Logger.getLogger(FridgeServer.class.getName()).log(Level.SEVERE, null, ex);
		}
		return reply(response, "An error occured! Check your JSON!", 400);
	    }
	});

	get(new JsonGsonTransformerRoute("/products") {
	    @Override
	    public Object handle(Request request, Response response) {
		return provider.loadResources("Products");
	    }
	});

	get(new JsonGsonTransformerRoute("/products/:name") {
	    @Override
	    public Object handle(Request request, Response response) {
		return provider.loadResource(request.params(":name"), "Products");
	    }
	});

    }

    public static String reply(Response response, String message, int code) {
	response.status(code);
	String reply = "{'status' : '" + message + "'}";
	return reply;
    }
}
