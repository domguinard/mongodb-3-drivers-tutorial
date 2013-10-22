/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mongojack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mongojack.Id;
import org.mongojack.ObjectId;

/**
 *
 * @author misterdom
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PutOrRemoveLog {

    @Id
    @ObjectId
    private String id;

    @ObjectId
    public String getId() {
	return id;
    }

    @ObjectId
    public void setId(String id) {
	this.id = id;
    }
    
    private String product;
    private Long timestamp;
    private Boolean isPut;

    public PutOrRemoveLog() {
    }

    public PutOrRemoveLog(String product, Long timestamp, Boolean isPut) {
	this.product = product;
	this.timestamp = timestamp;
	this.isPut = isPut;
    }

    
    public String getProduct() {
	return product;
    }

    public void setProduct(String product) {
	this.product = product;
    }

    public Long getTimestamp() {
	return timestamp;
    }

    public void setTimestamp(Long timestamp) {
	this.timestamp = timestamp;
    }

    public Boolean getIsPut() {
	return isPut;
    }

    public void setIsPut(Boolean isPut) {
	this.isPut = isPut;
    }
}
