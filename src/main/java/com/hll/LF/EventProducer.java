package com.hll.LF;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hll on 2016/4/6.
 */
public class EventProducer {
  AtomicInteger i=new AtomicInteger(0);
  public String fecthRequest(){
   /* try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }*/
    String jsonRequest="{'id':"+i.getAndIncrement()+",'msg','hello body'}";
    return jsonRequest;
  }
}
