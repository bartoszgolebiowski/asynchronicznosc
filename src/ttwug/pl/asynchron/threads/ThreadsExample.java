package ttwug.pl.asynchron.threads;

import ttwug.pl.asynchron.Model.Product;
import ttwug.pl.asynchron.heavyMethods.Service;

import java.util.List;
import java.util.function.Supplier;

public class ThreadsExample {
    private List<Product> products;
    private Service service;

    public ThreadsExample(List<Product> products, Service service) {
        this.products = products;
        this.service = service;
    }

    public void main() {
        long start = System.currentTimeMillis();
        System.out.println("ThreadsExample");
        ExchangeRate exchangeRunnable = new ExchangeRate(service::getExchangeRate);
        Thread exchangeThread = new Thread(exchangeRunnable);
        exchangeThread.start();

        products.forEach(o1 -> {
            ProductPrice priceRunnable = new ProductPrice(() -> service.getMethod(o1.getName()));
            Thread priceThread = new Thread(priceRunnable);
            priceThread.start();

            try {
                priceThread.join();
                exchangeThread.join();

                System.out.println(
                        String.format("%s costs %f zl",
                                o1.getName(), priceRunnable.getPrice() * exchangeRunnable.getExchangeRate()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(String.format("It took us %d ms to calculate this", System.currentTimeMillis() - start));
    }

    private class ProductPrice implements Runnable {
        private double price;
        private Supplier<Double> supplier;

        public ProductPrice(Supplier<Double> supplier) {
            this.supplier = supplier;
        }

        @Override
        public void run() {
            this.price = supplier.get();
        }

        public double getPrice() {
            return price;
        }
    }

    private class ExchangeRate implements Runnable {
        private double exchangeRate;
        private Supplier<Double> supplier;

        public ExchangeRate(Supplier<Double> supplier) {
            this.supplier = supplier;
        }

        @Override
        public void run() {
            this.exchangeRate = supplier.get();
        }

        public double getExchangeRate() {
            return exchangeRate;
        }
    }
}
