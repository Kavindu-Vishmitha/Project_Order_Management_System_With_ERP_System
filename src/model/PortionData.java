package model;

public class PortionData {

    private String foodId;
    private String foodName;
    private String portion;
    private double price;
    private int quantity;

    public PortionData(String foodId, String foodName, String portion, double price, int quantity) {
        
        this.foodId = foodId;
        this.foodName = foodName;
        this.portion = portion;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters
    public String getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getPortion() {
        return portion;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
