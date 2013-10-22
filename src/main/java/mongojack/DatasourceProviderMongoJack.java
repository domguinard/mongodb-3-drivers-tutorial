/*
 * (c) Dominique Guinard (www.guinard.org)
 * 
 */
package mongojack;

import org.guinard.mongodb.drivers.tutorial.AbstractDatasourceProvider;
import jongo.PutOrRemoveLog;
import jongo.Fridge;
import jongo.Product;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mongoNative.DatasourceProviderMongoNative;
import org.guinard.mongodb.drivers.tutorial.InvalidJsonException;
import org.guinard.mongodb.drivers.tutorial.JsonJacksonTransformerRoute;
import org.mongojack.DBQuery;
import org.mongojack.DBUpdate;
import org.mongojack.JacksonDBCollection;

/**
 * A DB provider AND a DAO (don't try this at home/work! :-))
 *
 * @author domguinard
 */
public class DatasourceProviderMongoJack implements AbstractDatasourceProvider {

    private DB db;

    public DatasourceProviderMongoJack() {
	try {
	    db = this.getDb();
	} catch (UnknownHostException ex) {
	    Logger.getLogger(DatasourceProviderMongoNative.class.getName()).log(Level.SEVERE, null, ex);
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

    @Override
    public void createFridge(String json) throws InvalidJsonException {
	// Could be done automagically by a REST framework such a Jersey...
	Fridge newRes = JsonJacksonTransformerRoute.jsonToJavaObject(json, Fridge.class);

	// Validate...
	if (newRes.getName() == null) {
	    throw new InvalidJsonException();
	}

	DBCollection collec = db.getCollection("Fridges");
	JacksonDBCollection<Fridge, String> collecMj = JacksonDBCollection.wrap(collec,
		Fridge.class, String.class);
	collecMj.save(newRes);
    }

    @Override
    public void createProduct(String json) throws InvalidJsonException {
	// Could be done automagically by a REST framework such a Jersey...
	Product newRes = JsonJacksonTransformerRoute.jsonToJavaObject(json, Product.class);

	// Validate...
	if (newRes.getName() == null) {
	    throw new InvalidJsonException();
	}

	DBCollection collec = db.getCollection("Products");
	JacksonDBCollection<Product, String> collecMj = JacksonDBCollection.wrap(collec,
		Product.class, String.class);
	collecMj.save(newRes);
    }

    @Override
    public void putOrRemoveProductFromFridge(String fridgeName, String json) throws InvalidJsonException {
	// Could be done automagically by a REST framework such a Jersey...
	PutOrRemoveLog newRes = JsonJacksonTransformerRoute.jsonToJavaObject(json, PutOrRemoveLog.class);

	// Validate...
	if (newRes.getProduct() == null) {
	    throw new InvalidJsonException();
	}

	//Shell query:
	//db.Fridges.update({...}, {"$push" : {"putOrRemoveLog" : {...}}});
	DBCollection collec = db.getCollection("Fridges");
	JacksonDBCollection<Product, String> collecMj = JacksonDBCollection.wrap(collec,
		Product.class, String.class);

	//Shell query:
	//db.Fridges.update({...}, {"$push" : {"putOrRemoveLog" : {...}}});
	collecMj.update(DBQuery.is("name", fridgeName), DBUpdate.push("putOrRemoveLog", newRes));
    }

    @Override
    public <T> T loadResource(String name, String collection, Class<T> cl) {
	// We query "by example"
	DBCollection collec = db.getCollection("Fridges");
	JacksonDBCollection<T, String> collecMj = JacksonDBCollection.wrap(collec,
		cl, String.class);

	return collecMj.findOne(DBQuery.is("name", name));
    }

    @Override
    public <T> List<T> loadResources(String collection, Class<T> cl) {
	List<T> results = new ArrayList<T>();
	DBCollection collec = db.getCollection("Fridges");
	try {
	    JacksonDBCollection<T, String> collecMj = JacksonDBCollection.wrap(collec,
		    cl, String.class);
	    Iterable<T> all = collecMj.find();
	    for (T current : all) {
		results.add(current);
	    }
	} catch (Exception ex) {
	    ex.printStackTrace();
	    System.out.println(ex);
	}
	return results;
    }

    private BasicDBObject queryByName(String name) {
	return new BasicDBObject("name", name);
    }
}
