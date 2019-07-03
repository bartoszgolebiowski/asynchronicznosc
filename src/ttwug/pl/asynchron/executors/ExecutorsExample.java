//package ttwug.pl.asynchron.executors;
//
//import javafx.util.Pair;
//import ttwug.pl.asynchron.Model.Product;
//import ttwug.pl.asynchron.heavyMethods.Service;
//
//import java.util.List;
//import java.util.concurrent.*;
//import java.util.stream.Collectors;
//
//public class ExecutorsExample {
//    private ExecutorService executorService;
//    private List<Product> products;
//    private Service service;
//
//    public ExecutorsExample(List<Product> products, Service service) {
//        this.executorService = Executors.newFixedThreadPool(5);
//        this.products = products;
//        this.service = service;
//    }
//
//    public void main() {
//        long start = System.currentTimeMillis();
//        System.out.println("ExecutorsExample");
//
//        Future<Double> exchangeRateFuture
//                = executorService.submit(service::getExchangeRate);
//
//
//        List<Future<Double>> collect = products
//                .stream()
//                .map(product -> executorService.submit(() -> service.getMethod(product.getName())))
//                .collect(Collectors.toList());
//
//        collect.forEach(priceFuture -> {
//            double productPrice = getProductPrice(exchangeRateFuture, priceFuture);
//            System.out.println(String.format("product costs %f zl",productPrice));
//        });
//
//        System.out.println(String.format("It took us %d ms to calculate this", System.currentTimeMillis() - start));
//        executorService.shutdown();
//    }
//
//    private double getProductPrice(Future<Double> exchangeRateFuture, Future<Double> priceFuture) {
//        double price = 0;
//        try {
//            Double exchangeRate = exchangeRateFuture.get();
//            Double productPrice = priceFuture.get();
//            price = exchangeRate * productPrice;
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//        return price;
//    }
//}


package ttwug.pl.asynchron.executors;

import javafx.util.Pair;
import ttwug.pl.asynchron.Model.Product;
import ttwug.pl.asynchron.heavyMethods.Service;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ExecutorsExample {
    private ExecutorService executorService;
    private List<Product> products;
    private Service service;

    public ExecutorsExample(List<Product> products, Service service) {
        this.executorService =
                new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<>());

        this.products = products;
        this.service = service;
    }

    public void main() {
        long start = System.currentTimeMillis();
        System.out.println("ExecutorsExample");

        Future<Double> exchangeRateFuture
                = executorService.submit(service::getExchangeRate);


        List<Pair<String, Future<Double>>> collect = products
                .stream()
                .map(product -> new Pair<>(product.getName(), executorService.submit(() -> service.getMethod(product.getName()))))
                .collect(Collectors.toList());

        collect.forEach(productPair -> {
            double productPrice = getProductPrice(exchangeRateFuture, productPair);
            System.out.println(String.format("%s costs %f zl", productPair.getKey(),productPrice));
        });

        System.out.println(String.format("It took us %d ms to calculate this", System.currentTimeMillis() - start));
        executorService.shutdown();
    }

    private double getProductPrice(Future<Double> exchangeRateFuture, Pair<String, Future<Double>> priceFuture) {
        double price = 0;
        try {
            Double exchangeRate = exchangeRateFuture.get();
            Double productPrice = priceFuture.getValue().get();
            price = exchangeRate * productPrice;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return price;
    }
}

