package model;

import java.util.ArrayList;

public class Sale {
	Client client;
	ArrayList<Product> products;
	Amount amount;

    public Sale(Client client, ArrayList<Product> products, Amount amount) {
        this.client = client;
        this.products = products;
        this.amount = amount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }



    @Override
    public String toString() {
        return "Sale{" + "client=" + client + ", products=" + products + ", amount=" + amount + '}';
    }
}