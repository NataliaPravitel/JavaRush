package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Player extends CollisionObject implements Movable {
  public Player(int x, int y) {
    super(x, y);
  }

  @Override
  public void draw(Graphics graphics) {
    graphics.setColor(Color.ORANGE);
    graphics.fillOval(this.x - width / 2, this.y - height / 2, this.width, this.height);
  }

  @Override
  public void move(int x, int y) {
    this.x += x;
    this.y += y;
  }
}
