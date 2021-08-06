package com.javarush.task.task25.task2515;

public class Canvas {
  private int width;
  private int height;
  private char[][] matrix;

  public Canvas(int width, int height) {
    this.width = width;
    this.height = height;
    this.matrix = new char[height][width];
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public char[][] getMatrix() {
    return matrix;
  }

  public void setPoint(double x, double y, char c) {
    int roundX = (int) Math.round(x);
    int roundY = (int) Math.round(y);

    if ( (0 <= roundX && roundX < matrix[0].length) && (0 <= roundY && roundY < matrix.length)) {
      matrix[roundY][roundX] = c;
    }
  }

  public void drawMatrix(double x, double y, int[][] matrix, char c) {
    for (int j = 0; j < matrix.length; j++) {
      for (int i = 0; i < matrix[0].length; i++) {
        if (matrix[j][i] != 0) {
          setPoint(x + i, y + j, c);
        }
      }
    }
  }

  public void clear() {
    this.matrix = new char[height][width];
  }

  public void print() {
    for (int y = 0; y < matrix.length; y++) {
      for (int x = 0; x < matrix[0].length; x++) {
        System.out.print(matrix[y][x]);
      }
      System.out.println();
    }
    System.out.println();
    System.out.println();
  }
}
