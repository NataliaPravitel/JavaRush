package com.javarush.task.task35.task3513;
//2048 (3)
//Займемся реализацией класса Model. Он будет ответственен за все манипуляции производимые с игровым полем.
//
//Но чтобы как-то манипулировать игровым полем, неплохо было бы для начала его создать!
//
//Нам понадобятся:
//1. Приватная константа FIELD_WIDTH = 4, определяющая ширину игрового поля.
//2. Приватный двумерный массив gameTiles состоящий из объектов класса Tile.
//3. Конструктор без параметров инициализирующий игровое поле и заполняющий его пустыми плитками.
//
//
//Требования:
//1. В классе Model должно быть создано private static final поле FIELD_WIDTH со значением равным четырем.
//2. В классе Model должно быть создано private поле gameTiles типа Tile[][].
//3. Конструктор без параметров класса Model должен заполнять массив gameTiles новыми объектами типа Tile.
//4. Массив gameTiles должен иметь размерность FIELD_WIDTH x FIELD_WIDTH.


//2048 (4)
//Игра 2048 начинается на поле, где две плитки уже имеют какие-то начальные значения. А наше поле пока пусто :(.
//Прежде чем бросаться писать код, давай подумаем как это можно было бы реализовать.
//Предлагаю создать приватный метод addTile, который будет смотреть какие плитки пустуют и, если такие имеются,
//менять вес одной из них, выбранной случайным образом, на 2 или 4 (на 9 двоек должна приходиться 1 четверка).
//Получить случайный объект из списка можешь использовав следующее выражение:
//(размерСписка * случайноеЧислоОтНуляДоЕдиницы).
//Также получение свободных плиток можно вынести в отдельный приватный метод getEmptyTiles, возвращающий список
//свободных плиток в массиве gameTiles.
//После реализации функционала добавления новых плиток, добавим в конструктор два вызова метода addTile,
//выполняя начальное условие задачи.
//P.S. Пожалуй стоит весь код из конструктора переместить в метод resetGameTiles, для того, чтобы при необходимости
//начать новую игру, не приходилось создавать новую модель, а можно было бы просто вернуться в начальное состояние
//вызвав его. Уровень доступа должен быть шире приватного.
//P.P.S. Для вычисления веса новой плитки используй выражение (Math.random() < 0.9 ? 2 : 4).
//
//Требования:
//1. Метод getEmptyTiles должен возвращать список пустых плиток в массиве gameTiles.
//2. Метод addTile должен изменять значение случайной пустой плитки в массиве gameTiles на 2 или 4 с вероятностью 0.9 и 0.1 соответственно.
//3. Метод resetGameTiles должен заполнять массив gameTiles новыми плитками и менять значение двух из них с помощью двух вызовов метода addTile.
//4. В конструкторе класса Model должен содержаться вызов метода resetGameTiles.


//2048 (5)
//Основными возможностями, которые мы должны реализовать, являются перемещения влево, вправо, вверх и вниз.
//Если ты раньше уже играл в 2048, то знаешь, что при перемещении в одну из сторон,
// происходит перемещение плиток со значениями на место пустых, а также объединение плиток одного номинала.
//
//В качестве базового сценария рассмотрим движение влево и подумаем что же происходит, когда мы хотим выполнить это действие.
//
//Для каждого ряда или столбца, происходят на самом деле две вещи:
//а) Сжатие плиток, таким образом, чтобы все пустые плитки были справа, т.е. ряд {4, 2, 0, 4} становится рядом {4, 2, 4, 0}
//б) Слияние плиток одного номинала, т.е. ряд {4, 4, 2, 0} становится рядом {8, 2, 0, 0}.
//Обрати внимание, что ряд {4, 4, 4, 4} превратится в {8, 8, 0, 0}, а {4, 4, 4, 0} в {8, 4, 0, 0}.
//
//Создай методы compressTiles(Tile[] tiles) и mergeTiles(Tile[] tiles),
// которые будут реализовывать пункты а) и б) соответственно.
// Использовать мы их будем только внутри класса Model, поэтому уровень доступа сделай максимально узким.
//
//Также добавь поля score и maxTile типа int, которые должны хранить текущий счет и максимальный вес
// плитки на игровом поле. Счет увеличивается после каждого слияния,
// например если текущий счет 20 и было выполнено слияние ряда {4, 4, 4, 0},
// счет должен увеличиться на 8. Уровень доступа к полям должен быть шире приватного.
//Проще всего организовать обновление значений этих полей в методе mergeTiles, например так:
//1. Если выполняется условие слияния плиток, проверяем является ли новое значения больше максимального
// и при необходимости меняем значение поля maxTile.
//2. Увеличиваем значение поля score на величину веса плитки образовавшейся в результате слияния.
//
//P.S. Когда мы будем реализовывать методы движения, сжатие будет всегда выполнено перед слиянием,
// таким образом можешь считать, что в метод mergeTiles всегда передается массив плиток без пустых в середине.
//
//
//Требования:
//1. Метод mergeTiles должен быть реализован в соответствии с условием задачи.
//2. Метод compressTiles должен быть реализован в соответствии с условием задачи.
//3. Метод compressTiles должен быть приватным.
//4. Метод mergeTiles должен быть приватным.
//5. Метод mergeTiles должен корректно обновлять значение поля score.
//6. Метод mergeTiles должен корректно обновлять значение поля maxTile.
//7. Поля score и maxTile должны быть инициализированы нулем при создании новой модели.


//2048 (6)
//Итак, ты реализовал сжатие и слияние плиток, что в комбинации дает нам возможность осуществить движение влево.
//Отлично! Но нам нужно еще и добавлять новую плитку в случае, если после передвижения игровое поле изменилось.
//
//Давай сделаем так:
//1. Изменим метод compressTiles, чтобы он возвращал true в случае, если он вносил изменения во входящий массив, иначе - false.
//2. То же самое сделаем и для метода mergeTiles.
//3. Реализуем метод left, который будет для каждой строки массива gameTiles вызывать методы compressTiles и
// mergeTiles и добавлять одну плитку с помощью метода addTile в том случае, если это необходимо.
//4. Метод left не должен быть приватным, т.к. вызваться он будет, помимо прочего, из класса Controller.
//
//
//Требования:
//1. Метод compressTiles должен возвращать true в случае, если им были внесены изменения во входящий массив.
//2. Метод compressTiles должен возвращать false в случае, если им НЕ были внесены изменения во входящий массив.
//3. Метод mergeTiles должен возвращать true в случае, если им были внесены изменения во входящий массив.
//4. Метод mergeTiles должен возвращать false в случае, если им НЕ были внесены изменения во входящий массив.
//5. Метод left должен перемещать элементы массива gameTiles влево в соответствии с правилами игры и
// добавлять плитку с помощью метода addTile, если это необходимо.
//6. Метод left НЕ должен изменять массив gameTiles, если перемещение влево невозможно.

//2048 (7)
//Движение влево мы реализовали, теперь необходимо реализовать методы right, up, down.
// Уверен, что ты с этим справишься и без моей помощи, так что дам только одну подсказку.
//
//Что будет, если повернуть двумерный массив на 90 градусов по часовой стрелке, сдвинуть влево,
// а потом еще трижды выполнить поворот?
//
//
//Требования:
//1. Метод up должен перемещать элементы массива gameTiles вверх в соответствии с правилами игры и
// добавлять плитку с помощью метода addTile, если это необходимо.
//2. Метод up НЕ должен изменять массив gameTiles если перемещение вверх невозможно.
//3. Метод down должен перемещать элементы массива gameTiles вниз в соответствии с правилами игры и
// добавлять плитку с помощью метода addTile, если это необходимо.
//4. Метод down НЕ должен изменять массив gameTiles если перемещение вниз невозможно.
//5. Метод right должен перемещать элементы массива gameTiles вправо в соответствии с правилами игры
// и добавлять плитку с помощью метода addTile, если это необходимо.
//6. Метод right НЕ должен изменять массив gameTiles если перемещение вправо невозможно.

//2048 (8)
//Итак, модель почти готова, добавим еще пару простых методов и начнем реализацию контроллера.
//
//В модели нам не хватает способа получить игровое поле, чтобы передать его представлению на отрисовку,
// а также метода, выполнив который, можно было бы определить возможен ли ход в текущей позиции, или нет.
//
//Контроллер, в свою очередь, будет в основном использоваться для обработки пользовательского ввода с клавиатуры,
// поэтому сделаем его наследником класса KeyAdapter.
//
//Нам понадобятся приватные поля model и view соответствующих типов и методы getGameTiles и getScore,
// возвращающие подходящие свойства модели.
//
//По пунктам:
//1. Добавь в класс Model геттер для поля gameTiles.
//2. Добавь в класс Model метод canMove возвращающий true в случае, если в текущей позиции возможно сделать ход так,
// чтобы состояние игрового поля изменилось. Иначе - false.
//3. Сделай класс Controller потомком класса KeyAdapter.
//4. Добавь в класс Controller метод getGameTiles вызывающий такой же метод у модели.
//5. Добавь в класс Controller метод getScore возвращающий текущий счет (model.score).

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model {
  private static final int FIELD_WIDTH = 4;
  private Tile[][] gameTiles;
  int score;
  int maxTile;

  public Model() {
    resetGameTiles();
    this.score = 0;
    this.maxTile = 0;
  }

  public Tile[][] getGameTiles() {
    return gameTiles;
  }

  public boolean canMove() {
    for (int x = 0; x < FIELD_WIDTH - 1; x++) {
      for (int y = 0; y < FIELD_WIDTH - 1; y++) {
        if (gameTiles[x][y].isEmpty()) {
          return true;
        } else {
          if (gameTiles[x][y].value == gameTiles[x][y+1].value ||
              gameTiles[x][y].value == gameTiles[x+1][y].value) {
            return true;
          }
        }
      }
    }
    return false;
  }

  void resetGameTiles() {
    this.gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
    for (int x = 0; x < FIELD_WIDTH; x++) {
      for (int y = 0; y < FIELD_WIDTH; y++) {
        gameTiles[y][x] = new Tile();
      }
    }
    addTile();
    addTile();
  }

  private void addTile() {
    List<Tile> emptyTiles = getEmptyTiles();
    if (!emptyTiles.isEmpty()) {
      emptyTiles.get( (int)(emptyTiles.size() * Math.random()) )
              .value = Math.random() < 0.9 ? 2 : 4;
    }
  }

  private List<Tile> getEmptyTiles() {
    List emptyTiles = new ArrayList();
    for (int x = 0; x < FIELD_WIDTH; x++) {
      for (int y = 0; y < FIELD_WIDTH; y++) {
        if (gameTiles[x][y].isEmpty()) {
          emptyTiles.add(gameTiles[x][y]);
        }
      }
    }
    return emptyTiles;
  }

  void left() {
    boolean needAddTile = false;
    for (int x = 0; x < FIELD_WIDTH; x++) {
      if (compressTiles(gameTiles[x]) | mergeTiles(gameTiles[x])) {
        needAddTile = true;
      }
    }
    if (needAddTile) {
      addTile();
    }
  }

  void up() {
    rotate90Degrees();
    rotate90Degrees();
    rotate90Degrees();
    left();
    rotate90Degrees();
  }

  void right() {
    rotate90Degrees();
    rotate90Degrees();
    left();
    rotate90Degrees();
    rotate90Degrees();
  }

  void down() {
    rotate90Degrees();
    left();
    rotate90Degrees();
    rotate90Degrees();
    rotate90Degrees();
  }

  private void rotate90Degrees() {
    Tile[][] tempTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
    for (int x = 0; x < FIELD_WIDTH; x++) {
      for (int y = 0; y < FIELD_WIDTH; y++) {
        tempTiles[y][FIELD_WIDTH - 1 - x] = gameTiles[x][y];
      }
    }
    gameTiles = tempTiles;
  }

  private boolean compressTiles(Tile[] tiles) {
    int hash = Arrays.hashCode(tiles);
    Arrays.sort(tiles, (o1, o2) -> (o1.value == 0) ? 1 : (o2.value > 0) ? 1 : -1);
    return hash != Arrays.hashCode(tiles);
  }

  private boolean mergeTiles(Tile[] tiles) {
    boolean isMerge = false;
    for (int y = 1; y < FIELD_WIDTH; y++) {
      if (tiles[y-1].value == tiles[y].value && tiles[y-1].value != 0) {
        tiles[y-1].value *= 2;
        tiles[y].value = 0;
        if (tiles[y-1].value > maxTile) {
          maxTile = tiles[y-1].value;
        }
        score += tiles[y-1].value;
        isMerge = true;
      }
    }
    compressTiles(tiles);
    return isMerge;
  }






  public static void print(Tile[] tiles){
    for (int i = 0; i < tiles.length; i++) {
      System.out.print(tiles[i].value + " ");
    }
    System.out.println();
  }
  public static void hash(Tile[] tiles){
    for (int i = 0; i < tiles.length; i++)
      System.out.print(tiles[i].hashCode() + " ");
    System.out.println();
  }

  @Test
  public void test (){
    Tile[] tiles = new Tile[]{new Tile(0), new Tile(0), new Tile(0), new Tile(4)};
    print(tiles);
    hash(tiles);
    System.out.println(new Model().mergeTiles(tiles));
    print(tiles);
    hash(tiles);
    System.out.println("===============================");
    tiles = new Tile[]{new Tile(4), new Tile(4), new Tile(2), new Tile(0)};
    print(tiles);
    hash(tiles);
    System.out.println(new Model().mergeTiles(tiles));
    print(tiles);
    hash(tiles);
    System.out.println("===============================");
    tiles = new Tile[]{new Tile(4), new Tile(2), new Tile(0), new Tile(4)};
    print(tiles);
    hash(tiles);
    System.out.println(new Model().mergeTiles(tiles));
    print(tiles);
    hash(tiles);
    System.out.println("===============================");
    tiles = new Tile[]{new Tile(4), new Tile(4), new Tile(4), new Tile(0)};
    print(tiles);
    hash(tiles);
    System.out.println(new Model().mergeTiles(tiles));
    print(tiles);
    hash(tiles);
    System.out.println("===============================");
    tiles = new Tile[]{new Tile(4), new Tile(4), new Tile(4), new Tile(4)};

    print(tiles);
    hash(tiles);
    System.out.println(new Model().mergeTiles(tiles));
    print(tiles);
    hash(tiles);
    System.out.println("===============================");
  }

}
