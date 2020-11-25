package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet {
  private final int number;
  private static Logger logger = Logger.getLogger(Tablet.class.getName());
  private LinkedBlockingQueue<Order> queue;

  public Tablet(int number) {
    this.number = number;
  }

  public void createOrder() {
    Order order = null;
    try {
      order = new Order(this);
      processOrder(order);
    } catch (NoVideoAvailableException e) {
      logger.log(Level.INFO, "No video is available for the order " + order);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Console is unavailable.");
    }
  }

  public void setQueue(LinkedBlockingQueue<Order> queue) {
    this.queue = queue;
  }

  public void createTestOrder() {
    TestOrder testOrder = null;
    try {
      testOrder = new TestOrder(this);
      processOrder(testOrder);
    } catch (NoVideoAvailableException e) {
      logger.log(Level.INFO, "No video is available for the order " + testOrder);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Console is unavailable.");
    }
  }

  private void processOrder(Order order) {
    if (!order.isEmpty()) {
//      setChanged();
//      notifyObservers(order);
      try {
        queue.put(order);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      new AdvertisementManager(order.getTotalCookingTime() * 60).processVideos();
    }
  }

  @Override
  public String toString() {
    return "Tablet{number=" + number + "}";
  }
}
