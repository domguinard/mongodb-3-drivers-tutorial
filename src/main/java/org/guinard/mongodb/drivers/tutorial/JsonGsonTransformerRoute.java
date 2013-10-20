/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guinard.mongodb.drivers.tutorial;

import com.google.gson.Gson;
import spark.ResponseTransformerRoute;

/**
 *
 * @author misterdom
 */
public abstract class JsonGsonTransformerRoute extends ResponseTransformerRoute {

    private Gson gson = new Gson();

    protected JsonGsonTransformerRoute(String path) {
	super(path, "application/json");
    }

    @Override
    public String render(Object model) {
	return gson.toJson(model);
    }
}
