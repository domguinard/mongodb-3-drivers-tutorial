/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jongo;

/**
 *
 * @author misterdom
 */
public class Product {

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
