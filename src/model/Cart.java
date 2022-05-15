package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<VinoBean> products;

    public Cart() {
        products = new ArrayList<VinoBean>();
    }

    public void addProduct(VinoBean product) {
        products.add(product);
    }

    public void deleteProduct(VinoBean product) {
        for (VinoBean prod : products) {
            if (prod.getIdProdotto() == product.getIdProdotto()) {
                products.remove(prod);
                break;
            }
        }
    }

    public List<VinoBean> getProducts() {
        return products;
    }
}
