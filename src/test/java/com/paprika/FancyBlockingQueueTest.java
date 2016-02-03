package com.paprika;


import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Andrey Kapitonov on 2/4/16.
 */
public class FancyBlockingQueueTest {

    @Test
    public void testWhetherQueueOrNot() throws Exception {
        FancyBlockingQueue<String> queue = new FancyBlockingQueue<>(5);
        queue.put("one");
        queue.put("two");
        queue.put("three");
        queue.put("four");
        queue.put("five");

        Assert.assertEquals("one", queue.take());

        queue.put("six");

        Assert.assertEquals("two", queue.take());
        Assert.assertEquals("three", queue.take());
        Assert.assertEquals("four", queue.take());
        Assert.assertEquals("five", queue.take());
        Assert.assertEquals("six", queue.take());

        queue.put("seven");
        queue.put("eight");

        Assert.assertEquals("seven", queue.take());
        Assert.assertEquals("eight", queue.take());
    }


}
