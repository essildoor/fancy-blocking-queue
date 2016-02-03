package com.paprika;

/**
 * Created by Andrey Kapitonov on 2/2/16.
 */
public class FancyBlockingQueue<E> {

    private Object[] storage;
    private int count;
    private int indexToPut;
    private int indexToTake;

    public FancyBlockingQueue(int queueSize) {
        if (queueSize <= 0) throw new IllegalArgumentException("queue size must be greater than zero!");
        this.storage = new Object[queueSize];
        this.count = 0;
        this.indexToPut = 0;
        this.indexToTake = 0;
    }

    @SuppressWarnings("unchecked")
    private E getElementAt(int index) {
        return (E) storage[index];
    }

    /**
     * Adds new element to the queue. If queue is full, waits until free space appears.
     * @param element element to add
     */
    public void put(E element) {
        synchronized (this) {
            if (count >= storage.length) {
                while (count >= storage.length) {
                    try {
                        System.out.println(String.format("%s : waiting...", Thread.currentThread().getName()));
                        this.wait();
                    } catch (InterruptedException e) {
                        System.out.println("interrupted!");
                        e.printStackTrace();
                    }
                }
            }
            storage[indexToPut] = element;
            count++;
            if (count < storage.length) { //if there is at least one free element in array
                //increment indexToPut. if it reaches array bound - point to the 1st el of array.
                indexToPut = indexToPut == storage.length - 1 ? indexToPut = 0 : indexToPut + 1;
            } else { //if array is full at the moment
                indexToPut = indexToTake; //point to next el to be taken
            }
            this.notifyAll();
        }
    }

    /**
     * Takes and removes element from the queue. If queue is empty, waits until new element appears.
     * @return element
     */
    public E take() {
        E result;
        synchronized (this) {
            if (count == 0) {
                while (count == 0) {
                    try {
                        System.out.println(String.format("%s : waiting...", Thread.currentThread().getName()));
                        this.wait();
                    } catch (InterruptedException e) {
                        System.out.println("interrupted!");
                        e.printStackTrace();
                    }
                }
            }
            result = getElementAt(indexToTake);
            count--;
            //increment in cyclic way
            indexToTake = indexToTake == storage.length - 1 ? indexToTake = 0 : indexToTake + 1;

            this.notifyAll();
        }

        return result;
    }
}
