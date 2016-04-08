package com.hll.LF;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Created by hll on 2016/4/6.
 */
public class LFThread extends Thread {
  public static final int LF_STATUS_PROCESSING=1;
  public static final int LF_STATUS_LEADER=2;
  public static final int LF_STATUS_FOLLOWER=3;

  private EventHandler eventHandler;
  private FixedLFThreadPool pool;

  private volatile int lfstate;
  private Lock lock;

  public LFThread(EventHandler eventHandler){
    this.eventHandler=eventHandler;
  }

  @Override
  public void run() {
    while (true){
      //抢占锁，抢到的成为leader
      lock.lock();
      setLfstate(LF_STATUS_LEADER);
      //从事件源fetch事件
      String request = pool.getEventProducer().fecthRequest();
      //将其他follower晋升成leader
      lock.unlock();
      //处理事件
      setLfstate(LF_STATUS_PROCESSING);
      process(request);
      setLfstate(LF_STATUS_FOLLOWER);
    }
  }

  private void process(String request) {
    this.eventHandler.handleData(request);
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public FixedLFThreadPool getPool() {
    return pool;
  }

  public void setPool(FixedLFThreadPool pool) {
    this.pool = pool;
  }

  public int getLfstate() {
    return lfstate;
  }

  public void setLfstate(int lfstate) {
    this.lfstate = lfstate;
  }

  public void setLock(Lock lock) {
    this.lock = lock;
  }
}
