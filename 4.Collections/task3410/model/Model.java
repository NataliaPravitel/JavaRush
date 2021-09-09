package com.javarush.task.task34.task3410.model;

import com.javarush.task.task34.task3410.controller.EventListener;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class Model {
  public static final int FIELD_CELL_SIZE = 20;
  private EventListener eventListener;
  private GameObjects gameObjects;
  private int currentLevel = 1;
  private LevelLoader levelLoader;

  public Model() {
    try {
      this.levelLoader = new LevelLoader(Paths.get(
              getClass().getResource("../res/levels.txt").toURI()));
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  public void setEventListener(EventListener eventListener) {
    this.eventListener = eventListener;
  }

  public void move(Direction direction) {
    if (checkWallCollision(gameObjects.getPlayer(), direction)) {
      return;
    }

    if (checkBoxCollisionAndMoveIfAvailable(direction)) {
      return;
    }
    moveToDirection(gameObjects.getPlayer(), direction);
    checkCompletion();
  }

  public void restart() {
    restartLevel(currentLevel);
  }

  public void startNextLevel() {
    currentLevel++;
    restartLevel(currentLevel);
  }

  public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {
    for (Wall wall : gameObjects.getWalls()) {
      if (gameObject.isCollision(wall, direction)) {
        return true;
      }
    }
    return false;
  }

  public boolean checkBoxCollisionAndMoveIfAvailable(Direction direction) {
    for (Box currentBox : gameObjects.getBoxes()) {
      if (gameObjects.getPlayer().isCollision(currentBox, direction)) {

        for (Box box : gameObjects.getBoxes()) {
          if (!box.equals(currentBox)) {
            if (currentBox.isCollision(box, direction)) {
              return true;
            }
          }

          if (checkWallCollision(currentBox, direction)) {
            return true;
          }
        }
        moveToDirection(currentBox, direction);
      }
    }
    return false;
  }

  public void checkCompletion() {
    int countHomes = gameObjects.getHomes().size();
    AtomicInteger countBoxOnHome = new AtomicInteger();
    gameObjects.getHomes().forEach(home ->
            gameObjects.getBoxes().forEach(box ->  {
              if(home.x == box.x && home.y == box.y) {
                countBoxOnHome.getAndIncrement();
              }
            })
    );
    if (countBoxOnHome.get() == countHomes) {
      eventListener.levelCompleted(currentLevel);
    }
  }

  public void restartLevel(int level) {
    gameObjects = levelLoader.getLevel(level);
  }

  public GameObjects getGameObjects() {
    return gameObjects;
  }

  private void moveToDirection(Movable movable, Direction direction) {
    switch (direction) {
      case LEFT:
        movable.move(-FIELD_CELL_SIZE, 0);
        break;
      case RIGHT:
        movable.move(FIELD_CELL_SIZE, 0);
        break;
      case UP:
        movable.move(0, -FIELD_CELL_SIZE);
        break;
      case DOWN:
        movable.move(0, FIELD_CELL_SIZE);
        break;
    }
  }
}
