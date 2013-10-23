/*
 * (c) Dominique Guinard (www.guinard.org)
 * 
 */
package org.guinard.mongodb.drivers.tutorial;

import java.util.logging.Level;
import java.util.logging.Logger;
import jongo.DatasourceProviderJongo;
//import mongoNative.Fridge;
//import mongoNative.Product;
import mongojack.DatasourceProviderMongoJack;
import mongojack.Fridge;
import mongojack.Product;
import static spark.Spark.*;
import spark.*;

/**
 *
 * @author domguinard
 */
public class FridgeServer {

    // Mongo Native:
    //private static DatasourceProviderMongoNative provider = new DatasourceProviderMongoNative();
    // Jongo:
    private static AbstractDatasourceProvider provider = new DatasourceProviderJongo();
    // MongoJack:
    //private static AbstractDatasourceProvider provider = new DatasourceProviderMongoJack();

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

	get(new JsonJacksonTransformerRoute("/fridges") {
	    @Override
	    public Object handle(Request request, Response response) {
		return provider.loadResources("Fridges", Fridge.class);
		//return provider.loadResources("Fridges");
	    }
	});

	get(new JsonJacksonTransformerRoute("/fridges/:name") {
	    @Override
	    public Object handle(Request request, Response response) {
		return provider.loadResource(request.params(":name"), "Fridges", Fridge.class);
		//return provider.loadResources("Fridges");
	    }
	});

	put(new JsonJacksonTransformerRoute("/fridges/:name/products") {
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

	get(new JsonJacksonTransformerRoute("/fridges/:name/products") {
	    @Override
	    public Object handle(Request request, Response response) {
		return provider.getProductsFromFridge(request.params(":name"));
	    }

	});

	post(new JsonJacksonTransformerRoute("/products") {
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

	get(new JsonJacksonTransformerRoute("/products") {
	    @Override
	    public Object handle(Request request, Response response) {
		//return provider.loadResources("Products");
		return provider.loadResources("Products", Product.class);
	    }
	});

	get(new JsonJacksonTransformerRoute("/products/:name") {
	    @Override
	    public Object handle(Request request, Response response) {
		//return provider.loadResource(request.params(":name"), "Products");
		return provider.loadResource(request.params(":name"), "Products", Product.class);
	    }
	});

    }

    public static String reply(Response response, String message, int code) {
	response.status(code);
	String reply = "{'status' : '" + message + "'}";
	return reply;
    }
}
