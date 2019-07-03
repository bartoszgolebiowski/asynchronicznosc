package ttwug.pl.asynchron.synchronous;

import ttwug.pl.asynchron.Model.Product;
import ttwug.pl.asynchron.heavyMethods.Service;

import java.util.List;

public class SynchronousExample {
    private List<Product> products;
    private Service service;

    public SynchronousExample(List<Product> products, Service service) {
        this.products = products;
        this.service = service;
    }

    public void main() {
        long start = System.currentTimeMillis();
        System.out.println("SynchronousExample");
        products.forEach(product -> {
            double price = service.getMethod(product.getName());
            double exchangeRate = service.getExchangeRate();
            System.out.println(String.format("%s costs %f zl", product.getName(), price * exchangeRate));
        });
        System.out.println(String.format("It took us %d ms to calculate this", System.currentTimeMillis() - start));
    }
}
