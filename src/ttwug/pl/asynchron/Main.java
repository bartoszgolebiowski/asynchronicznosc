package ttwug.pl.asynchron;

import ttwug.pl.asynchron.Model.Product;
import ttwug.pl.asynchron.completableFuture.CompletableFutureExample;
import ttwug.pl.asynchron.executors.ExecutorsExample;
import ttwug.pl.asynchron.heavyMethods.Service;
import ttwug.pl.asynchron.synchronous.SynchronousExample;
import ttwug.pl.asynchron.threads.ThreadsExample;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Product> products = initializeProducts();
       // new CompletableFutureExample(products, new Service()).main();
       // new SynchronousExample(products, new Service()).main();
        //    new ThreadsExample(products, new Service()).main();
           new ExecutorsExample(products, new Service()).main();

    }

//    private static List<Product> initializeProducts() {
//        List<Product> productList = new ArrayList<>();
//        Product fridge = new Product("Fridge");
//        Product car = new Product("Car");
//        Product rolex = new Product("Rolex");
//        Product iphone = new Product("Iphone");
//        productList.add(fridge);
//        productList.add(car);
//        productList.add(rolex);
//        productList.add(iphone);
//        return productList;
//    }

     private static List<Product> initializeProducts() {
        List<Product> productList = new ArrayList<>();
        Product fridge = new Product("Fridge");
        Product car = new Product("Car");
        Product rolex = new Product("Rolex");
        Product iphone = new Product("Iphone");
       for (int i = 0; i < 10; i++) {
            productList.add(fridge);
            productList.add(car);
            productList.add(rolex);
            productList.add(iphone);
        }
        return productList;
    }

}
