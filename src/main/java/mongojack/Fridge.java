/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mongojack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import org.mongojack.Id;
import org.mongojack.ObjectId;



/**
 *
 * @author misterdom
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fridge {

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
    
    private String name;
    private String description;
    private List putOrRemoveLog;

    public Fridge() {
    }

    public Fridge(String name, String description) {
	this.name = name;
	this.description = description;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public List getPutOrRemoveLog() {
	return putOrRemoveLog;
    }

    public void setPutOrRemoveLog(List putOrRemoveLog) {
	this.putOrRemoveLog = putOrRemoveLog;
    }
}