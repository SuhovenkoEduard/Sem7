package com.uni.lab3.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ProductSearchByPrice implements Serializable {
    private String name;
    private int maxPrice;

    public ProductSearchByPrice(String name, int maxPrice) {
        this.name = name;
        this.maxPrice = maxPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    @NonNull
    @Override
    public String toString() {
        return "ProductSearchByPrice{" +
                "name='" + name + '\'' +
                ", maxPrice=" + maxPrice +
                '}';
    }
}
