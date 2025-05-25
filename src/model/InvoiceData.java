package model;



import java.util.List;

public class InvoiceData {

    private String invoice_id;
    private String order_id;
    private double total;
    private double paid;
    private double balance;
    private List<InvoiceItem> items;

    // Constructors
    public InvoiceData() {
    }

    public InvoiceData(String invoice_id, String order_id, double total, double paid, double balance, List<InvoiceItem> items) {
        this.invoice_id = invoice_id;
        this.order_id = order_id;
        this.total = total;
        this.paid = paid;
        this.balance = balance;
        this.items = items;
    }

    // Getters and Setters
    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }
}
