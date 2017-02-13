package com.fatzombie.cooklist;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: anton
 * Date: 12/24/13
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductComparator implements Comparator<Product> {
    @Override
    public int compare(Product product, Product product2) {
        return product.getCategory().compareTo(product2.getCategory());  //To change body of implemented methods use File | Settings | File Templates.
    }
}
