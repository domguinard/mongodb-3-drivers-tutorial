/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jongo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author domguinard
 */
public class Fridge {
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