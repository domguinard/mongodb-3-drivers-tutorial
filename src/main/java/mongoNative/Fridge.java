/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mongoNative;

import com.mongodb.BasicDBObject;
import java.util.ArrayList;

/**
 *
 * @author misterdom
 */
public class Fridge extends BasicDBObject {

    // key value maps
    
    public static String NAMEKEY = "name";
    public static String PRODLOGKEY = "putOrRemoveLog";
    public static String DESCRIPTIONKEY = "description";

    public Fridge() {
    }

    public Fridge(String name, String description) {
	put(NAMEKEY, name);
	put(DESCRIPTIONKEY, description);
	put(PRODLOGKEY, new ArrayList());
    }

    public void addOrRemoveProduct(PutOrRemoveLog logEntry) {
	ArrayList<PutOrRemoveLog> log = (ArrayList<PutOrRemoveLog>) this.get(PRODLOGKEY);
	log.add(logEntry);
    }
    
    public String name() {
	return (String) get("name");
    }
}