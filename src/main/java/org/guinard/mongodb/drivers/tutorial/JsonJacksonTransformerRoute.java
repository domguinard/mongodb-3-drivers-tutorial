/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guinard.mongodb.drivers.tutorial;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jongo.Fridge;
import spark.ResponseTransformerRoute;

/**
 *
 * @author misterdom
 */
public abstract class JsonJacksonTransformerRoute extends ResponseTransformerRoute {

    //private Gson mapper = new Gson();
    private static ObjectMapper mapper = new ObjectMapper();

    protected JsonJacksonTransformerRoute(String path) {
	super(path, "application/json");
    }

    @Override
    public String render(Object model) {
	try {
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    return ow.writeValueAsString(model);
	} catch (JsonProcessingException ex) {
	    Logger.getLogger(JsonJacksonTransformerRoute.class.getName()).log(Level.SEVERE, null, ex);
	}
	return null;
    }

    // This would be automatically done with a REST framework such as Jersey!
    public static <T> T jsonToJavaObject(String json, Class<T> cl) {
	try {
	    return mapper.readValue(json, cl);
	} catch (IOException ex) {
	    Logger.getLogger(JsonJacksonTransformerRoute.class.getName()).log(Level.SEVERE, null, ex);
	}
	return null;
    }
}
