package com.javarush.task.task34.task3410.view;

import com.javarush.task.task34.task3410.controller.EventListener;
import com.javarush.task.task34.task3410.model.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Field extends JPanel {
  View view;
  private EventListener eventListener;

  public Field(View view) {
    this.view = view;
    addKeyListener(new KeyHandler());
    setFocusable(true);
  }

  public void paint(Graphics g) {
    g.setColor(new Color(175, 238, 238));
    g.fillRect(0, 0, getWidth(), getHeight());
    view.getGameObjects().getAll().forEach(gameObject -> gameObject.draw(g));
  }

  public void setEventListener(EventListener eventListener) {
    this.eventListener = eventListener;
  }

  public class KeyHandler extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent keyEvent) {
      switch (keyEvent.getKeyCode()) {
        case KeyEvent.VK_LEFT:
          eventListener.move(Direction.LEFT);
          break;
        case KeyEvent.VK_RIGHT:
          eventListener.move(Direction.RIGHT);
          break;
        case KeyEvent.VK_UP:
          eventListener.move(Direction.UP);
          break;
        case KeyEvent.VK_DOWN:
          eventListener.move(Direction.DOWN);
          break;
        case KeyEvent.VK_R:
          eventListener.restart();
          break;
      }
    }
  }
}
