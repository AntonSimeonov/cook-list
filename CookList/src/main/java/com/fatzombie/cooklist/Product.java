package com.fatzombie.cooklist;

/**
 * Created with IntelliJ IDEA.
 * User: NTS Two
 * Date: 11/19/13
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Product {

    private String category;
    private String product;
    private String productName;
    private float price;
    private String currency;
    private float quantity;
    private String unit;

    public Product(String c, String p,float pr, String pN, float q, String u){

        category = c;
        product = p;
        productName = pN;
        price = pr;
        quantity = q;
        unit = u;

    }

    public Product(String c, String p,float pr, String pN, float q, String u, String curr){

        category = c;
        product = p;
        productName = pN;
        price = pr;
        currency = curr;
        quantity = q;
        unit = u;

    }

    public float getProductSum(){
        return price * quantity;
    }

    public String getCurrency(){
        return currency;
    }

    public void setCurrency(String c){
        currency = c;
    }

    public String getCategory(){
        return category;
    }

    public String getProduct(){
        return product;
    }

    public String getProductName(){
        return productName;
    }

    public float getQuantity(){
        return quantity;
    }

    public String getUnit(){
        return unit;
    }

    public float getPrice(){
        return price;
    }

    public void setCategory(String c){
        category = c;
    }

    public void setProduct(String p){
        product = p;
    }

    public void setProductName(String pN ){
        productName = pN;
    }

    public void setPrice(float p){
        price = p;
    }

    public void setQuantity(float q){
        quantity = q;
    }

    public void setUnit(String u){
        unit = u;
    }
}
