/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guinard.mongodb.drivers.tutorial;

import java.util.List;

/**
 *
 * @author misterdom
 */
public interface AbstractDatasourceProvider {

    void createFridge(String json) throws InvalidJsonException;

    void createProduct(String json) throws InvalidJsonException;
    
    List getProductsFromFridge(String fridgeName);

    <T> T loadResource(String name, String collection, Class<T> cl);

    <T> List<T> loadResources(String collection, Class<T> cl);

    void putOrRemoveProductFromFridge(String fridgeName, String json) throws InvalidJsonException;
    
}
