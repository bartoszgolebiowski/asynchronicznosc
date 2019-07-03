package ttwug.pl.asynchron.threadsAndRunnable;

import java.util.concurrent.*;

public class ThreadAndRunnableExample {

    public void main(String[] args) {
        createThread();
        createRunnable();
        createRunnableLambda();
        createCallableLambda();
        createCallableMethodRef();
    }

    private void createCallableMethodRef() {
        FutureTask<String> futureTask = new FutureTask<>(this::heavyMethod);
        futureTask.run();

        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void createCallableLambda() {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            Thread.sleep(1500);
            return "HELLO FROM CALLABLE";
        });
        futureTask.run();

        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    public String heavyMethod() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello from function";
    }

    private void createRunnableLambda() {
        Runnable runnable = () -> System.out.println("Nowy watek utworzyony przy pomocy Lambdy");
        runnable.run();
    }

    private void createRunnable() {
        Runnable runnable = new ExampleRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void createThread() {
        ExampleThread thread = new ExampleThread();
        thread.start();
        System.out.println(thread.getFirstValue());
        System.out.println(thread.getSecondValue());
    }

    public static class ExampleThread extends Thread {
        String firstValue;
        private String secondValue;

        @Override
        public void run() {
            System.out.println("Nowy watek utworzyony przy pomocy klasy ExampleThread");
        }

        public String getFirstValue() {
            return firstValue;
        }

        public String getSecondValue() {
            return secondValue;
        }
    }

    public class ExampleRunnable implements Runnable {
        private String firstValue;
        private String secondValue;

        @Override
        public void run() {
            System.out.println("Nowy watek utworzyony przy pomocy klasy ExampleRunnable");
        }

        public String getFirstValue() {
            return firstValue;
        }

        public String getSecondValue() {
            return secondValue;
        }
    }
}
