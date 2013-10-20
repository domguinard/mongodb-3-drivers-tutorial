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
public class PutOrRemoveLog extends BasicDBObject{
    public static String PRODKEY = "product";
    public static String TSKEY = "timestamp";
    public static String ISPUTKEY = "isPut";
    
    public PutOrRemoveLog(String product, Long timestamp, boolean isPut) {
	put("product", product);
	put("timestamp", timestamp);
	put("isPut", isPut);
    }
    
}
