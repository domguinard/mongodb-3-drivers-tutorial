/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mongoNative;

import com.mongodb.BasicDBObject;

/**
 *
 * @author misterdom
 */
public class Product extends BasicDBObject {
    public static String LASTSKEY = "lasts";
    public static String PRODNAMEKEY = "name";
    public static String PRODDESCKEY = "description";
    public static String IMAGEURLKEY = "imageUrl";
    
    public Product(String name, String description, String imageUrl, Integer lastsNDays) {
	this.put(PRODNAMEKEY, name);
	this.put(PRODDESCKEY, description);
	this.put(IMAGEURLKEY, imageUrl);
	this.put(LASTSKEY, lastsNDays);
    }

}
