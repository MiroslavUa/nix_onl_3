package com.example.util;

import com.example.model.NotifiableProduct;
import com.example.model.Product;
import com.example.model.ProductBundle;

import java.util.Random;
import java.util.UUID;

public class ProductUtil {
    public static Random random = new Random();

    public static String generateAddress() {
        return UUID.randomUUID().toString().substring(0, 5) + "@gmail.com";
    }

}
