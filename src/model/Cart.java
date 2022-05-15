package model;

import java.util.HashMap;

public class Cart {
	private HashMap<VinoBean, Integer> products;

	public Cart() {
		products = new HashMap<VinoBean, Integer>();
	}

	public void addProduct(VinoBean product) {
		Integer value = products.containsKey(product) ? products.get(product) : 0;
		products.put(product, value + 1);
	}

	public void deleteProduct(VinoBean product) {
		products.remove(product);
	}

	public void removeOneProduct(VinoBean product) {
		if (!products.containsKey(product)) {
			return;
		}

		Integer value = products.get(product);
		if (value == 1) {
			deleteProduct(product);
			return;
		}

		products.put(product, value - 1);
	}

	public HashMap<VinoBean, Integer> getProducts() {
		return products;
	}

	public void emptyCart() {
		this.products.clear();
	}

	public boolean contains(VinoBean product) {
		return products.containsKey(product);
	}
}
