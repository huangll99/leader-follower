package com.hll.LF;

/**
 * Created by hll on 2016/4/6.
 */
public class Main {
  public static void main(String[] args) throws InterruptedException {
    EventProducer eventProducer = new EventProducer();
    FixedLFThreadPool lfThreadPool = new FixedLFThreadPool(4,eventProducer, new EventHandler() {
      @Override
      public void handleData(String jsonRequest) {
        System.out.println(Thread.currentThread().getName()+" - "+jsonRequest);
      }
    });
    lfThreadPool.start();
    Thread.sleep(100000000);
  }
}
