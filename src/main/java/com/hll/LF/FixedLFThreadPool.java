package com.hll.LF;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hll on 2016/4/6.
 */
public class FixedLFThreadPool {

  final private int THREAD_COUNT;
  final private Lock lock = new ReentrantLock(true);
  final private LFThread[] threads;

  private EventProducer eventProducer;

  public FixedLFThreadPool(int threadCount,EventProducer eventProducer, EventHandler eventHandler) {
    this.THREAD_COUNT = threadCount;
    this.eventProducer=eventProducer;
    this.threads = new LFThread[THREAD_COUNT];
    for (int i=0;i<THREAD_COUNT;i++){
      LFThread lfThread = new LFThread(eventHandler);
      lfThread.setLfstate(LFThread.LF_STATUS_FOLLOWER);
      lfThread.setLock(lock);
      lfThread.setPool(this);
      threads[i]=lfThread;
    }
  }

  public void start() {
    for (LFThread lfThread:threads){
      lfThread.start();
    }
  }

  public int getProcessingCount(){
    return 0;
  }

  public EventProducer getEventProducer() {
    return eventProducer;
  }

  public void setEventProducer(EventProducer eventProducer) {
    this.eventProducer = eventProducer;
  }
}
