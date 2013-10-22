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
public class Product {

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
    
    private int lasts;
    private String name;
    private String imageUrl;
    private String description;

    public Product() {
    }

    public Product(int lasts, String name, String imageUrl, String description) {
	this.lasts = lasts;
	this.name = name;
	this.imageUrl = imageUrl;
	this.description = description;
    }


    public int getLasts() {
	return lasts;
    }

    public void setLasts(int lasts) {
	this.lasts = lasts;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getImageUrl() {
	return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }
}
