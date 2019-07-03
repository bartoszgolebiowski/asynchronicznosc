//package ttwug.pl.asynchron.completableFuture;
//
//import javafx.util.Pair;
//import ttwug.pl.asynchron.Model.Product;
//import ttwug.pl.asynchron.heavyMethods.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
//public class CompletableFutureExample {
//    private List<Product> products;
//    private Service service;
//
//    public CompletableFutureExample(List<Product> products, Service service) {
//        this.products = products;
//        this.service = service;
//    }
//
//    public void main() {
//        long start = System.currentTimeMillis();
//        System.out.println("CompletableFuture");
//
//        CompletableFuture<Double> exchangeRateFuture
//                = CompletableFuture.supplyAsync(() -> service.getExchangeRate());
//
//        List<Pair<String, CompletableFuture<Double>>> productsPriceFutures = new ArrayList<>();
//
//        for (Product product : products) {
//            productsPriceFutures.add(
//                    new Pair<>(product.getName(),
//                            CompletableFuture.supplyAsync(() -> service.getMethod(product.getName()))));
//        }
//
//        for (Pair<String, CompletableFuture<Double>> productsPriceFuture : productsPriceFutures) {
//            productsPriceFuture.getValue()
//                    .thenCombine(exchangeRateFuture, (price, exchange) -> new Pair<>(productsPriceFuture.getKey(), price * exchange))
//                    .thenAccept((o1) -> System.out.println(String.format("%s costs %f zl from CompletableFuture", o1.getKey(),o1.getValue())));
//        }
//
//        System.out.println(String.format("It took us %d ms to calculate this", System.currentTimeMillis() - start));
//    }
//
//}

package ttwug.pl.asynchron.completableFuture;

import ttwug.pl.asynchron.Model.Product;
import ttwug.pl.asynchron.heavyMethods.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureExample {
    private ExecutorService executorService;
    private List<Product> products;
    private Service service;

    public CompletableFutureExample(List<Product> products, Service service) {
        this.executorService = Executors.newFixedThreadPool(5);
        this.products = products;
        this.service = service;
    }

    public void main() {
        long start = System.currentTimeMillis();
        System.out.println("CompletableFuture");


        CompletableFuture<Double> exchangeRateFuture
                = CompletableFuture.supplyAsync(() -> service.getExchangeRate(), executorService);

//        CompletableFuture<Void> exchangeRateFutureVoid
//                = CompletableFuture.runAsync(() -> service.getExchangeRate(), executorService);

        List<CompletableFuture<Double>> productsPriceFutures = new ArrayList<>();

        for (Product product : products) {
            productsPriceFutures.add(
                    CompletableFuture.supplyAsync(() -> service.getMethod(product.getName()), executorService));
        }

        for (CompletableFuture<Double> productsPriceFuture : productsPriceFutures) {
            productsPriceFuture
                    .thenCombine(exchangeRateFuture, (price, exchange) -> price * exchange)
                    .thenAccept(o1 -> {
                        System.out.println(String.format("product costs %f zl from CompletableFuture", o1));
                    });
        }

        System.out.println(String.format("It took us %d ms to calculate this", System.currentTimeMillis() - start));

        //useCase();
    }

    private void useCase() {
        CompletableFuture<Double> first
                = CompletableFuture.supplyAsync(() -> service.getExchangeRate(), executorService);

        CompletableFuture<Double> second
                = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 66d;
        }, executorService);

        first
                .thenApply(Double::doubleValue)
                .thenCombine(second, (o1, o2) -> {
                    System.out.println(o1);
                    System.out.println(o2);
                    return o1 * o2;
                })
                .thenAccept(System.out::println);
    }

}

