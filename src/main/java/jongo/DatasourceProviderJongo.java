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
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.guinard.mongodb.drivers.tutorial.InvalidJsonException;
import org.guinard.mongodb.drivers.tutorial.JsonJacksonTransformerRoute;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

/**
 * A DB provider AND a DAO (don't try this at home/work! :-))
 *
 * @author domguinard
 */
public class DatasourceProviderJongo {

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
	DB db = mongoClient.getDB("fridges");
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
	fridges.save(newRes);
    }

    public void createProduct(String json) throws InvalidJsonException {
	// Could be done automagically by a REST framework such a Jersey...
	Product newRes = JsonJacksonTransformerRoute.jsonToJavaObject(json, Product.class);

	// Validate...
	if (newRes.getName() == null) {
	    throw new InvalidJsonException();
	}
	
	MongoCollection fridges = db.getCollection("Fridges");
	fridges.save(newRes);
    }

    public void putOrRemoveProductFromFridge(String fridgeName, String json) throws InvalidJsonException {
	// Could be done automagically by a REST framework such a Jersey...
	PutOrRemoveLog newRes = JsonJacksonTransformerRoute.jsonToJavaObject(json, PutOrRemoveLog.class);

	// Validate...
	if (newRes.getProduct() == null) {
	    throw new InvalidJsonException();
	}

	MongoCollection fridges = db.getCollection("Fridges");    
	//db.Fridges.update({...}, {"$push" : {"putOrRemoveLog" : {...}}});
	fridges.update("{name: #}", fridgeName).with("{$set: {age: 1}}");
	
	friends.update("{name: 'Joe'}").with("{$set: {address: #}}", new Address(..));
	friends.update("{name: 'Joe'}").upsert().multi().with("{$inc: {age: 1}}");

	BasicDBObject log = new BasicDBObject(Fridge.PRODLOGKEY, putOrRemoveLog);
	BasicDBObject push = new BasicDBObject("$push", log);
	collec.update(queryByName(fridgeName), push);
    }

    public DBObject loadResource(String name, String collection) {
	DBCollection collec = db.getCollection(collection);
	// We query "by example"
	BasicDBObject query = new BasicDBObject("name", name);
	DBObject resource = collec.findOne(query);

	// We can query by Mongo id:
	// DBObject resource = collec.findOne(new ObjectId(id));
	return resource;
    }
//
//    public List loadResources(String collection) {
//	List results = new ArrayList();
//	DBCollection collec = db.getCollection(collection);
//	DBCursor cursor = collec.find();
//	try {
//	    while (cursor.hasNext()) {
//		results.add(cursor.next());
//	    }
//	} finally {
//	    cursor.close();
//	}
//	return results;
//    }

    private BasicDBObject queryByName(String name) {
	return new BasicDBObject("name", name);
    }
}
