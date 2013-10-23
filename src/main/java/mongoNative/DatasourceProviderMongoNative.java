/*
 * (c) Dominique Guinard (www.guinard.org)
 * 
 */
package mongoNative;

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

/**
 * A DB provider AND a DAO (don't try this at home/work! :-))
 *
 * @author domguinard
 */
public class DatasourceProviderMongoNative {

    private DB db;

    public DatasourceProviderMongoNative() {
	try {
	    db = this.getDb();
	} catch (UnknownHostException ex) {
	    Logger.getLogger(DatasourceProviderMongoNative.class.getName()).log(Level.SEVERE, null, ex);
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
	DBCollection collec = db.getCollection("Fridges");

	// Do stuff, like validation:
	DBObject fridge = dbOjbFromJson(json);
	if (fridge.get(Fridge.NAMEKEY) == null || fridge.get(Fridge.NAMEKEY) == null) {
	    throw new InvalidJsonException();
	};

	collec.insert(dbOjbFromJson(json));
    }

    public void createProduct(String json) throws InvalidJsonException {
	// Do stuff, like validation:
	DBObject fridge = dbOjbFromJson(json);
	if (fridge.get(Product.PRODNAMEKEY) == null || fridge.get(Product.LASTSKEY) == null) {
	    throw new InvalidJsonException();
	};

	DBCollection collec = db.getCollection("Products");
	collec.insert(dbOjbFromJson(json));
    }

    public void putOrRemoveProductFromFridge(String fridgeName, String json) throws InvalidJsonException {
	// Validate...
	DBObject putOrRemoveLog = dbOjbFromJson(json);
	if (putOrRemoveLog.get(PutOrRemoveLog.PRODKEY) == null) {
	    throw new InvalidJsonException();
	}

	//Shell query:
	//db.Fridges.update({...}, {"$push" : {"putOrRemoveLog" : {...}}});
	DBCollection collec = db.getCollection("Fridges");
	BasicDBObject log = new BasicDBObject(Fridge.PRODLOGKEY, putOrRemoveLog);
	BasicDBObject push = new BasicDBObject("$push", log);
	collec.update(queryByName(fridgeName), push);
    }

    public DBObject loadResource(String name, String collection) {
	DBCollection collec = db.getCollection(collection);

	// We query "by example"
	// Shell query: db.xxx.find({"name": name});
	BasicDBObject query = new BasicDBObject("name", name);
	DBObject resource = collec.findOne(query);

	// We can query by Mongo id:
	// DBObject resource = collec.findOne(new ObjectId(id));
	return resource;
    }

    public List loadResources(String collection) {
	// Shell query: db.xxx.find();
	List results = new ArrayList();
	DBCollection collec = db.getCollection(collection);
	DBCursor cursor = collec.find();

	try {
	    for (DBObject current : cursor) {
		results.add(current);
	    }
	} finally {
	    cursor.close();
	}
	return results;
    }

    private BasicDBObject queryByName(String name) {
	return new BasicDBObject("name", name);
    }
}
