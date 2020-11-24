package com.javarush.task.task27.task2712;

import java.util.List;

public class RandomOrderGeneratorTask implements Runnable {
  private List<Tablet> tablets;
  private final int ORDER_CREATING_INTERVAL;

  public RandomOrderGeneratorTask(List<Tablet> tablets, int ORDER_CREATING_INTERVAL) {
    this.tablets = tablets;
    this.ORDER_CREATING_INTERVAL = ORDER_CREATING_INTERVAL;
  }

  @Override
  public void run() {
    try {
      while (true) {
        int randomIndex = (int) Math.random() * tablets.size();
        Tablet tablet = tablets.get(randomIndex);
        tablet.createTestOrder();
        Thread.sleep(ORDER_CREATING_INTERVAL);
      }

    } catch (InterruptedException e) {

    }
  }
}
