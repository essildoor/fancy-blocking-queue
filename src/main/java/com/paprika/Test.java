package com.paprika;

/**
 * Created by Andrey Kapitonov on 2/3/16.
 */
public class Test {

    final static FancyBlockingQueue<String> queue = new FancyBlockingQueue<>(10);

    private static void createProducers(int num) {
        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                while (true) {
                    final String toPut = "" + System.currentTimeMillis();
                    queue.put(toPut);
                    System.out.println(String.format("%s : put  >> %s", Thread.currentThread().getName(), toPut));
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, String.format("producer #%d", i)).start();
        }
    }

    private static void createConsumers(int num) {
        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                while (true) {
                    System.out.println(String.format("%s : take << %s", Thread.currentThread().getName(), queue.take()));

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } , String.format("consumer #%d", i)).start();
        }
    }

    public static void main(String[] args) {
        createProducers(3);
        createConsumers(1);
    }
}
