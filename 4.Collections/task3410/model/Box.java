package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Box extends CollisionObject implements Movable {
  public Box(int x, int y) {
    super(x, y);
  }

  @Override
  public void draw(Graphics graphics) {
    graphics.setColor(new Color(192, 192, 192));
    graphics.fillRect(this.x - width / 2, this.y - height / 2, this.width, this.height);
    graphics.setColor(Color.black);
    graphics.drawLine(this.x - width / 2, this.y - height / 2,
            this.x + width / 2, this.y + height / 2);
    graphics.drawLine(this.x - width / 2, this.y + height / 2,
            this.x + width / 2, this.y - height / 2);
    graphics.drawRect(this.x - width / 2, this.y - height / 2, this.width, this.height);
  }

  @Override
  public void move(int x, int y) {
    this.x += x;
    this.y += y;
  }
}
