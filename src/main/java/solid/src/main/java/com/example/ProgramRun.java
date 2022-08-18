package com.example;

import com.example.service.ProductService;

public class ProgramRun {
    public static void run(){
        ProductService service = ProductService.getInstance();
        for(int i = 0; i < 21; i++){
            service.saveProduct(service.generateRandomProduct());
        }

        service.showAll();
        System.out.println("notification sent for: " + service.quantityOfNotifiable() + " products");
        service.sendNotification();
    }
}
