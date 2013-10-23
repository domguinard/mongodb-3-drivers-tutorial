/*
 * (c) Dominique Guinard (www.guinard.org)
 * 
 */
package jongo;

import mongoNative.*;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.guinard.mongodb.drivers.tutorial.AbstractDatasourceProvider;
import org.guinard.mongodb.drivers.tutorial.InvalidJsonException;
import org.guinard.mongodb.drivers.tutorial.JsonJacksonTransformerRoute;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

/**
 * A DB provider AND a DAO (don't try this at home/work! :-))
 *
 * @author domguinard
 */
public class DatasourceProviderJongo implements AbstractDatasourceProvider {

    private Jongo db;

    public DatasourceProviderJongo() {
	try {
	    // "Wrap" the DB around Jongo
	    db = new Jongo(this.getDb());
	} catch (UnknownHostException ex) {
	    Logger.getLogger(DatasourceProviderJongo.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    private DB getDb() throws UnknownHostException {
	MongoClient mongoClient = new MongoClient();
	DB db = mongoClient.getDB("fridgeDb");
	return db;
    }

    private DBObject dbOjbFromJson(String json) {
	try {
	    return (DBObject) com.mongodb.util.JSON.parse(json);
	} catch (Exception ex) {
	    System.out.println(ex);
	}
	return null;
    }

    public void createFridge(String json) throws InvalidJsonException {
	// Could be done automagically by a REST framework such a Jersey...
	Fridge newRes = JsonJacksonTransformerRoute.jsonToJavaObject(json, Fridge.class);

	// Validate...
	if (newRes.getName() == null) {
	    throw new InvalidJsonException();
	}
	MongoCollection fridges = db.getCollection("Fridges");
	fridges.insert(newRes);
    }

    public void createProduct(String json) throws InvalidJsonException {
	// Could be done automagically by a REST framework such a Jersey...
	Product newRes = JsonJacksonTransformerRoute.jsonToJavaObject(json, Product.class);

	// Validate...
	if (newRes.getName() == null) {
	    throw new InvalidJsonException();
	}

	MongoCollection fridges = db.getCollection("Fridges");
	fridges.insert(newRes);
    }

    public void putOrRemoveProductFromFridge(String fridgeName, String json) throws InvalidJsonException {
	// Could be done automagically by a REST framework such a Jersey...
	PutOrRemoveLog newRes = JsonJacksonTransformerRoute.jsonToJavaObject(json, PutOrRemoveLog.class);

	// Validate...
	if (newRes.getProduct() == null) {
	    throw new InvalidJsonException();
	}

	//Shell query:
	//db.Fridges.update({...}, {"$push" : {"putOrRemoveLog" : {...}}});
	MongoCollection fridges = db.getCollection("Fridges");
	fridges.update("{name: #}", fridgeName).with("{$push: {putOrRemoveLog: #}}", newRes);
    }

    public <T> T loadResource(String name, String collection, Class<T> cl) {
	// We query "by example"
	// Shell query: db.xxx.find({"name": name});
	MongoCollection collec = db.getCollection(collection);
	return collec.findOne("{name: #}", name).as(cl);
    }

    public <T> List<T> loadResources(String collection, Class<T> cl) {
	// Shell query: db.xxx.find();
	List<T> results = new ArrayList<T>();
	MongoCollection collec = db.getCollection(collection);
	Iterable<T> all = collec.find().as(cl);
	for (T current : all) {
	    results.add(current);
	}
	return results;
    }

    public List getProductsFromFridge(String fridgeName) {
	MongoCollection fridges = db.getCollection("Fridges");
	return fridges.findOne("{name: #}", fridgeName).projection("{putOrRemoveLog: 1}").as(List.class);
    }

    private BasicDBObject queryByName(String name) {
	return new BasicDBObject("name", name);
    }
}
