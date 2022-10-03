package com.uni.lab3.IO.productsReader;

import com.uni.lab3.model.Product;

public interface ProductsHandlerCallback {
    void handleProducts(Product[] products);
}
