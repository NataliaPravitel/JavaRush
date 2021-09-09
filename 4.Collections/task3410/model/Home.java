package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Home extends GameObject {
  private static final int SIZE = 4;

  public Home(int x, int y) {
    super(x, y, SIZE, SIZE);
  }

  @Override
  public void draw(Graphics graphics) {
    graphics.setColor(Color.BLUE);
    graphics.fillRect(this.x - width / 2, this.y - height / 2, this.width, this.height);
  }
}
