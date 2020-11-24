package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.ArrayList;

public class TestOrder extends Order {
  public TestOrder(Tablet tablet) throws IOException {
    super(tablet);
  }

  @Override
  protected void initDishes() throws IOException {
    this.dishes = new ArrayList<>();

    Dish[] allDishes = Dish.values();
    int randomLengthDishList = 1 + (int) (Math.random() *  (allDishes.length));

    for (int i = 0; i < randomLengthDishList; i++) {
      int randomIndex = (int) Math.random() * allDishes.length;
      dishes.add(allDishes[randomIndex]);
    }
  }
}
