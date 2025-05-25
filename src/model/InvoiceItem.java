package model;

import model.InvoiceData;

public class InvoiceItem {

    private String food_name;
    private String portion;
    private int qty;
    private double price;

    // Constructors
    public InvoiceItem() {
    }

    public InvoiceItem(String food_name, String portion, int qty, double price) {
        this.food_name = food_name;
        this.portion = portion;
        this.qty = qty;
        this.price = price;
    }

    // Getters and Setters
    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getPortion() {
        return portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
