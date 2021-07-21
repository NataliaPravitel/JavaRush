package com.javarush.task.task22.task2213;

//Тетрис(11)
//Теперь создай класс FigureFactory.
//С его помощью мы будем создавать фигуры различных форм.
//Пока он будет содержать только один статический метод createRandomFigure:
//static Figure createRandomFigure(int x,int y)
//Реализацию метода мы напишем позже, а сейчас для того, чтобы программа скомпилировалась,
// верни в методе какое-нибудь значение, например, null.
//
//
//Requirements:
//1. Класс FigureFactory должен быть создан в отдельном файле.
//2. В классе FigureFactory должен быть создан метод createRandomFigure с двумя параметрами типа int.
//3. Метод createRandomFigure должен быть статическим.
//4. Должна существовать возможность получить доступ к методу createRandomFigure извне класса FigureFactory.
//5. Метод createRandomFigure должен иметь тип возвращаемого значения Figure.

/**
 * Класс FigureFactory отвечает за создание объектов-фигурок.
 */
public class FigureFactory {
    /**
     * Набор из шести шаблонов для фигурок
     */
    public static final int[][][] BRICKS = {{
            {1, 1, 0},                          //   X X
            {0, 1, 1},                          //     X X
            {0, 0, 0}}, {                       //

            {1, 0, 0},                          //   X
            {1, 1, 0},                          //   X X
            {0, 1, 0}}, {                       //     X

            {0, 1, 0},                          //   X
            {0, 1, 0},                          //   X
            {0, 1, 0}}, {                       //   X

            {1, 1, 0},                          //   X X
            {1, 1, 0},                          //   X X
            {0, 0, 0}}, {                       //

            {1, 1, 1},                          //   X X X
            {0, 1, 0},                          //     X
            {0, 0, 0}}, {                       //

            {1, 1, 1},                          //   X X X
            {1, 1, 1},                          //   X X X
            {0, 0, 0}}                          //
    };

    /**
     * Метод выбирает случайный шаблон и создает с ним новую фигурку.
     */
    public static Figure createRandomFigure(int x, int y) {
        int index = (int) (Math.random() * 6);
        return new Figure(x, y, BRICKS[index]);
    }
}
