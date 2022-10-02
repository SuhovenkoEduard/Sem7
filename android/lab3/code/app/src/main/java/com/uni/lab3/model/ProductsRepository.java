package com.uni.lab3.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsRepository implements Serializable {
    private List<Product> products;

    public ProductsRepository(Product[] products) {
        this.products = Arrays.asList(products);
    }

    public Product[] getAll() {
        return products.stream().toArray(Product[]::new);
    }

    public Product getByIndex(int index) {
        return products.get(index).clone();
    }

    public Product getById(int id) {
        return products.stream().filter(product -> product.getId() == id).findFirst().get().clone();
    }

    public void add(Product product) {
        product.setId(getNextId());
        products.add(product);
    }

    public void addByIndex(int index, Product product) {
        products.add(index, product);
    }

    public void update(Product product) {
        int index = products.indexOf(product);
        remove(product);
        addByIndex(index, product);
    }

    public void remove(@NonNull Product product) {
        removeById(product.getId());
    }

    public void removeById(int id) {
        products = products.stream().filter(product -> product.getId() != id).collect(Collectors.toList());
    }

    public int getNextId() {
        return Math.max(0, products.stream().map(Product::getId).mapToInt(i -> i).max().orElse(0)) + 1;
    }
}
