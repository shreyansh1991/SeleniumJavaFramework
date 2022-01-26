package objects;

import utils.JacksonUtils;

public class Product {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Product() {
    }

    public Product(int id) {
        Product[] products = JacksonUtils.deserializeJson("products.json", Product[].class);
        for (Product product : products) {
            if (product.getId() == id) {
                this.id = id;
                this.name = product.getName();
            }
        }
    }
}
