package com.javarush.task.task22.task2213;

import java.util.ArrayList;
import java.util.List;

//Тетрис(6)
//Теперь перейдем к классу Field.
//Он будет отвечать за хранение данных о текущих занятых и свободных клетках на поле игры.
//Добавь в класс Field два поля поля: width (ширина) типа int, height(высота) типа int.
//Так же нам понадобится матрица - двумерный массив: matrix(матрица) типа int[][];
//Там же добавь getter'ы для созданных переменных.
//Добавь конструктор с двумя параметрами width и height. И не забудь про матрицу.
//
//ВАЖНО!
//Двумерный массив можно представить как массив массивов или как прямоугольную матрицу.
//При этом первой координатой в массиве у нас будет номер строки, а второй - столбца.
//Другими словами ячейка с координатами x, y - это matrix[y][x].
//
//
//Requirements:
//1. В классе Field должно быть создано приватное поле width типа int.
//2. В классе Field должно быть создано приватное поле height типа int.
//3. В классе Field должно быть создано приватное поле matrix типа int[][](целочисленный двумерный массив).
//4. В классе Field должен быть создан корректный геттер для поля height.
//5. В классе Field должен быть создан корректный геттер для поля width.
//6. В классе Field должен быть создан корректный геттер для поля matrix.
//7. В классе Field должен быть создан корректно работающий конструктор с параметрами int, int.
// Поле matrix должно быть инициализировано новым массивом размерностью [y][x] (height и width).

//Тетрис(7)
//Нам понадобится еще 4 метода в классе Field:
//а) void print() - объект будет отрисовывать на экран свое текущее состояние;
//б) void removeFullLines() - будет удалять из матрицы полностью заполненные строки и сдвигать вышележащие строки вниз;
//в) Integer getValue(int x, int y) - возвращает значение которое находится в матрице с координатами x и y;
// Реализацию метода мы напишем позже, а сейчас для того, чтобы программа скомпилировалась, верни в методе какое-нибудь значение, например, null.
//г) void setValue(int x, int y, int value) - устанавливает переданное значение в ячейку массива (матрицы) с координатами x, y.
//
//
//Requirements:
//1. В классе Field должен быть создан метод print без параметров.
//2. В классе Field должен быть создан метод removeFullLines без параметров.
//3. В классе Field должен быть создан метод getValue с двумя параметрами типа int.
//4. В классе Field должен быть создан метод setValue c тремя параметрами типа int.
//5. Метод print не должен возвращать значение.
//6. Метод removeFullLines не должен возвращать значение.
//7. Метод getValue должен возвращать значение типа Integer.
//8. Метод setValue не должен возвращать значение.

//Тетрис(13)
//Теперь приступим к реализации созданных методов.
//Напиши реализацию метода print в классе Field
//а) Метод должен выводить на экран прямоугольник состоящий из символов '.' и 'X'.
//б) Высота прямоугольника равна height, ширина - width
//в) Если данная клетка пустая - вывести точку, если не пустая - английский X
//
//Подсказка:
//if (matrix[y][x]==0) ...
//
//
//Requirements:
//1. Метод print должен выводить данные на экран.
//2. Метод print должен выводить на экран количество строк равное height.
//3. Количество символов в каждой строке выведенной на экран должно быть равно width.
//4. В случае, если элемент массива равен 0 - на экран должна быть выведена точка, иначе - английский X.

//Тетрис(14)
//Напиши реализацию метода removeFullLines в классе Field.
//Надо:
//а) удалить все строки из матрицы, которые полностью заполнены (состоят из одних единиц);
//б) сместить оставшиеся строки вниз;
//в) создать новые строки взамен отсутствующих.
//
//ВАЖНО!
//matrix[y][x] содержит элемент с координатами (x,y).
//matrix[i] содержит i-ю строку.
//Мы можем:
//а) удалить стоку:
//matrix[i] = null
//б) скопировать [ссылку на] строку:
//matrix[i+1] = matrix[i];
//в) создать новую строку:
//matrix[i] = new int[width];
//
//
//Requirements:
//1. После выполнения метода removeFullLines в массиве matrix не должно остаться строк состоящих из одних единиц.
//2. Оставшиеся строки должны быть корректно перемещены, а вместо отсутствующих добавлены новые.

/**
 * Класс Field описывает "поле клеток" игры Тетрис
 */
public class Field {
    //ширина и высота
    private int width;
    private int height;

    //матрица поля: 1 - клетка занята, 0 - свободна
    private int[][] matrix;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        matrix = new int[height][width];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    /**
     * Метод возвращает значение, которое содержится в матрице с координатами (x,y)
     * Если координаты за пределами матрицы, метод возвращает null.
     */
    public Integer getValue(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height)
            return matrix[y][x];

        return null;
    }

    /**
     * Метод устанавливает переданное значение(value) в ячейку матрицы с координатами (x,y)
     */
    public void setValue(int x, int y, int value) {
        if (x >= 0 && x < width && y >= 0 && y < height)
            matrix[y][x] = value;
    }

    /**
     * Метод печатает на экран текущее содержание матрицы
     */
    public void print() {
        //Создаем массив, куда будем "рисовать" текущее состояние игры
        int[][] canvas = new int[height][width];

        //Копируем "матрицу поля" в массив
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                canvas[i][j] = matrix[i][j];
            }
        }

        //Копируем фигурку в массив, только непустые клетки
        int left = Tetris.game.getFigure().getX();
        int top = Tetris.game.getFigure().getY();
        int[][] brickMatrix = Tetris.game.getFigure().getMatrix();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (top + i >= height || left + j >= width) continue;
                if (brickMatrix[i][j] == 1)
                    canvas[top + i][left + j] = 2;
            }
        }


        //Выводим "нарисованное" на экран, но начинаем с "границы кадра".
        System.out.println("--------------------------------------------------------------------" +
                "-------\n");

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int index = canvas[i][j];
                if (index == 0)
                    System.out.print(" . ");
                else if (index == 1)
                    System.out.print(" X ");
                else if (index == 2)
                    System.out.print(" X ");
                else
                    System.out.print("???");
            }
            System.out.println();
        }


        System.out.println();
        System.out.println();
    }
    /**
     * Удаляем заполненные линии
     */
    public void removeFullLines() {
        //Например так:
        //Создаем список для хранения линий
        //Копируем все неполные линии в список.
        //Добавляем недостающие строки в начало списка.
        //Преобразуем список обратно в матрицу
        List<int[]> lines = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if (matrix[y][x] == 0) {
                    lines.add(matrix[y]);
                    x = width;
                }
            }
        }

        lines = addClearLines(lines);

        for (int y = 0; y < lines.size(); y++) {
            matrix[y] = lines.get(y);
        }
    }

    private List<int[]> addClearLines(List<int[]> lines) {
        int[] clearLine = new int[width];
        for (int i = 0; i < width; i++) {
            clearLine[i] = 0;
        }

        int addCount = height - lines.size();
        for (int i = 0; i < addCount; i++) {
            lines.add(0, clearLine);
        }
        return lines;
    }
}
