package com.example.service;

import com.example.model.NotifiableProduct;
import com.example.model.Product;
import com.example.model.ProductBundle;
import com.example.model.ProductType;
import com.example.repository.ProductRepository;
import com.example.util.ProductUtil;

import java.util.List;
import java.util.Random;

public class ProductService {
    private final ProductRepository repository;

    private static ProductService instance;

    private ProductService(ProductRepository repository){
        this.repository = repository;
    }

    public static ProductService getInstance(){
        if(instance == null) {
            instance = new ProductService(ProductRepository.getInstance());
        }
        return instance;
    }


    public Product generateRandomProduct() {
        Random random = new Random();
        long id = random.nextLong();
        boolean available = random.nextBoolean();
        String title = random.nextFloat() + "" + random.nextDouble();
        double price = random.nextDouble();

        if (random.nextBoolean()) {
            int amount = random.nextInt(15);
            return new ProductBundle(id, available,title, price, amount);
        } else {
            String channel = ProductUtil.generateAddress();
            return new NotifiableProduct(id, available, title, price, channel);
        }
    }

    public List<Product> getAll() {
        return repository.getAll();
    }

    public void saveProduct(Product product){
        repository.save(product);
    }

    private List<Product> notifiableProducts(){
        return getAll().stream()
                .filter(p -> p.getType() == ProductType.NOTIFIABLE)
                .toList();
    }

    public int quantityOfNotifiable(){
        return notifiableProducts().size();
    }

    public void sendNotification(){
        notifiableProducts().forEach(p -> System.out.println(((NotifiableProduct)p).getChannel()));
    }

    public void showAll() {
        getAll().stream().forEach(p -> System.out.println("Title: " + p.getTitle()
                + ", type: " + p.getType()
                + ", price: " + p.getPrice()
        ));
    }
}
