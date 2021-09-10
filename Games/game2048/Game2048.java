package com.javarush.games.game2048;

import com.javarush.engine.cell.*;

public class Game2048 extends Game {
  private static final int SIDE = 4;
  private int[][] gameField = new int[SIDE][SIDE];
  private boolean isGameStopped = false;
  private int score = 0;

  @Override
  public void initialize() {
    setScreenSize(SIDE, SIDE);
    createGame();
    drawScene();
  }

  private void createGame() {
    gameField = new int[SIDE][SIDE];
    createNewNumber();
    createNewNumber();
  }

  private void drawScene() {
    for (int x = 0; x < gameField.length; x++) {
      for (int y = 0; y < gameField[x].length; y++) {
        setCellColoredNumber(x, y, gameField[y][x]);
      }
    }
  }

  private void createNewNumber() {
    if (getMaxTileValue() == 2048) {
      win();
    } else {
      int x = getRandomNumber(SIDE);
      int y = getRandomNumber(SIDE);
      if (gameField[y][x] != 0) {
        createNewNumber();
      } else {
        int random = getRandomNumber(10);
        if (random == 9) {
          gameField[y][x] = 4;
        } else {
          gameField[y][x] = 2;
        }
      }
    }
  }

  private void win() {
    isGameStopped = true;
    showMessageDialog(Color.YELLOW, " YOU WIN\nscore = " + score, Color.MAGENTA, 50);
  }

  private void gameOver() {
    isGameStopped = true;
    showMessageDialog(Color.MAGENTA, " YOU LOOSE\nscore = " + score, Color.YELLOW, 50);
  }

  private boolean canUserMove() {
    for (int y = 0; y < SIDE; y++) {
      for (int x = 0; x < SIDE; x++) {
        if (gameField[y][x] == 0
                || y < SIDE - 1 && gameField[y][x] == gameField[y + 1][x]
                || (x < SIDE - 1) && gameField[y][x] == gameField[y][x + 1]) {
          return true;
        }
      }
    }
    return false;
  }

  private void setCellColoredNumber(int x, int y, int value) {
    if (value != 0) {
      setCellValueEx(x, y, getColorByValue(value), String.valueOf(value));
    } else {
      setCellValueEx(x, y, Color.LIGHTSEAGREEN, "");
    }
  }

  private Color getColorByValue(int value) {
    switch (value) {
      case 2:
        return Color.LIGHTPINK;
      case 4:
        return Color.CHARTREUSE;
      case 8:
        return Color.AQUA;
      case 16:
        return Color.YELLOW;
      case 32:
        return Color.MAGENTA;
      case 64:
        return Color.LIMEGREEN;
      case 128:
        return Color.GOLD;
      case 256:
        return Color.DODGERBLUE;
      case 512:
        return Color.SPRINGGREEN;
      case 1024:
        return Color.DARKORCHID;
      case 2048:
        return Color.ORANGERED;
      default:
        return Color.LIGHTSEAGREEN;
    }
  }

  private boolean compressRow(int[] row) {
    boolean isCompress = false;
    for (int x = 0; x < SIDE - 1; x++) {
      if (row[x] == 0 && row[x + 1] != 0) {
        row[x] = row [x + 1];
        row[x + 1] = 0;
        isCompress = true;
        x = -1;
      }
    }
    return isCompress;
  }

  private boolean mergeRow(int[] row) {
    boolean isMerge = false;
    for (int x = 0; x < SIDE - 1; x++) {
      if (row[x] != 0 && (row[x] == row[x + 1])) {
        row[x] *= 2;
        row[x + 1] = 0;
        isMerge = true;
        score += row[x];
        setScore(score);
      }
    }
    return isMerge;
  }

  @Override
  public void onKeyPress(Key key) {
    if (isGameStopped) {
      if (key == Key.SPACE) {
        isGameStopped = false;
        score = 0;
        setScore(score);
        createGame();
        drawScene();
      }
    } else if (!canUserMove()){
      gameOver();
    } else {
      if (key == Key.LEFT) {
        moveLeft();
        drawScene();
      } else if (key == Key.RIGHT) {
        moveRight();
        drawScene();
      } else if (key == Key.UP) {
        moveUp();
        drawScene();
      } else if (key == Key.DOWN) {
        moveDown();
        drawScene();
      }
    }
  }

  private void moveLeft() {
    boolean needNewNumber = false;
    for (int y = 0; y < gameField.length; y++) {
      boolean wasCompress = compressRow(gameField[y]);
      boolean wasMerge = mergeRow(gameField[y]);
      if (wasMerge) {
        compressRow(gameField[y]);
      }
      if (wasCompress || wasMerge) {
        needNewNumber = true;
      }
    }
    if (needNewNumber) {
      createNewNumber();
    }
  }

  private void moveRight() {
    rotateClockwise();
    rotateClockwise();
    moveLeft();
    rotateClockwise();
    rotateClockwise();
  }

  private void moveUp() {
    rotateClockwise();
    rotateClockwise();
    rotateClockwise();
    moveLeft();
    rotateClockwise();
  }

  private void moveDown() {
    rotateClockwise();
    moveLeft();
    rotateClockwise();
    rotateClockwise();
    rotateClockwise();
  }

  private void rotateClockwise() {
    int[][] rotateMatrix = new int[SIDE][SIDE];
    for (int y = 0; y < gameField.length; y++) {
      int[] row = gameField[y];
      for (int x = 0; x < row.length; x++) {
        rotateMatrix[x][SIDE - 1 - y] = row[x];
      }
    }
    gameField = rotateMatrix;
  }

  private int getMaxTileValue() {
    int maxTileValue = Integer.MIN_VALUE;
    for (int x = 0; x < gameField.length; x++) {
      for (int y = 0; y < gameField[x].length; y++) {
        if (gameField[y][x] > maxTileValue) {
          maxTileValue = gameField[y][x];
        }
      }
    }
    return maxTileValue;
  }
}
