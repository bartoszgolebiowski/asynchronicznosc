package ttwug.pl.asynchron.heavyMethods;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class Service {
    public double getExchangeRate() {
        try {
            TimeUnit.MILLISECONDS.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 4d;
    }

    private double getFridgePrice() {
        return 500d;
    }

    private double getCarPrice() {
        return 10000d;
    }

    private double getRolexPrice() {
        return 20000d;
    }

    private double getIphonePrice() {
        return 2000d;
    }

    public Double getMethod(String product) {
        Map<String,Supplier<Double>> map = new HashMap<>();
        map.put("Fridge", () -> getFridgePrice());
        map.put("Iphone", () -> getIphonePrice());
        map.put("Car", () ->  getCarPrice());
        map.put("Rolex", () ->  getRolexPrice());
        try {
            TimeUnit.MILLISECONDS.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return map.get(product).get();
    }
}
