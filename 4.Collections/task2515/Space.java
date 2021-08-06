package com.javarush.task.task25.task2515;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Главный класс игры - Космос (Space)
 */
public class Space {
    //Ширина и высота игрового поля
    private int width;
    private int height;

    //Космический корабль
    private SpaceShip ship;
    //Список НЛО
    private List<Ufo> ufos = new ArrayList<Ufo>();
    //Список бомб
    private List<Bomb> bombs = new ArrayList<Bomb>();
    //Список ракет
    private List<Rocket> rockets = new ArrayList<Rocket>();

    public Space(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Основной цикл программы.
     * Тут происходят все важные действия
     */
    public void run() {
        //Создаем холст для отрисовки.
        Canvas canvas = new Canvas(width, height);

        //Создаем объект "наблюдатель за клавиатурой" и стартуем его.
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        //Игра работает, пока корабль жив
        while (ship.isAlive()) {
            //"наблюдатель" содержит события о нажатии клавиш?
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                //Если "стрелка влево" - сдвинуть фигурку влево
                System.out.print(event.getKeyCode());
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    ship.moveLeft();
                    //Если "стрелка вправо" - сдвинуть фигурку вправо
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    ship.moveRight();
                    //Если "пробел" - стреляем
                else if (event.getKeyCode() == KeyEvent.VK_SPACE)
                    ship.fire();
            }

            //двигаем все объекты игры
            moveAllItems();

            //проверяем столкновения
            checkBombs();
            checkRockets();
            //удаляем умершие объекты из списков
            removeDead();

            //Создаем НЛО (1 раз в 10 ходов)
            createUfo();

            //Отрисовываем все объекты на холст, а холст выводим на экран
            canvas.clear();
            draw(canvas);
            canvas.print();

            //Пауза 300 миллисекунд
            Space.sleep(300);
        }

        //Выводим сообщение "Game Over"
        System.out.println("Game Over!");
    }

    /**
     * Двигаем все объекты игры
     */
    public void moveAllItems() {
        List<BaseObject> baseObjects = getAllItems();
        baseObjects.forEach(BaseObject::move);
    }

    /**
     * Метод возвращает общий список, который содержит все объекты игры
     */
    public List<BaseObject> getAllItems() {
        List<BaseObject> baseObjects = new ArrayList<>(ufos);
        baseObjects.add(ship);
        baseObjects.addAll(bombs);
        baseObjects.addAll(rockets);
        return baseObjects;
    }

    /**
     * Создаем новый НЛО. 1 раз на 10 вызовов.
     */
    public void createUfo() {
        if (ufos.isEmpty()) {
            ufos.add(new Ufo(width / 2, 0));
        }
    }

    /**
     * Проверяем бомбы.
     * - нужно проверить не пересеклись ли между собой какая-нибудь бомба и корабль;
     * - если пересеклись - корабль и бомба умирают: die();
     * - если бомба упала за границу экрана y > height, бомба тоже умирает.
     */
    public void checkBombs() {
        for (Bomb bomb : bombs) {
            if (bomb.isIntersect(ship)) {
                bomb.die();
                ship.die();
            } else if (bomb.y > height) {
                bomb.die();
            }
        }

    }

    /**
     * Проверяем рокеты.
     * - нужно проверить не пересеклись ли между собой какая-нибудь ракета и НЛО;
     * - если пересеклись - ракета и НЛО умирают: die();
     * - если ракета улетела за границу экрана y < 0, ракета тоже умирает.
     */
    public void checkRockets() {
        for (Rocket rocket : rockets) {
            for (Ufo ufo : ufos) {
                if (rocket.isIntersect(ufo)) {
                    rocket.die();
                    ufo.die();
                }
            }
            if (rocket.y < 0) {
                rocket.die();
            }
        }
    }

    /**
     * Удаляем умершие объекты (бомбы, ракеты, НЛО) из списков
     */
    public void removeDead() {
        for (int i = 0; i < bombs.size(); i++) {
            if (!bombs.get(i).isAlive()) {
                bombs.remove(bombs.get(i));
                i--;
            }
        }
        for (int i = 0; i < ufos.size(); i++) {
            if (!ufos.get(i).isAlive()) {
                ufos.remove(ufos.get(i));
                i--;
            }
        }
        for (int i = 0; i < rockets.size(); i++) {
            if (!rockets.get(i).isAlive()) {
                rockets.remove(rockets.get(i));
                i--;
            }
        }
    }

    /**
     * Отрисовка всех объектов игры:
     * а) заполняем весь холст точками.
     * б) отрисовываем все объекты на холст.
     */
    public void draw(Canvas canvas) {
        //тут нужно отрисовать все объекты игры
    }


    public SpaceShip getShip() {
        return ship;
    }

    public void setShip(SpaceShip ship) {
        this.ship = ship;
    }

    public List<Ufo> getUfos() {
        return ufos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    public List<Rocket> getRockets() {
        return rockets;
    }

    public static Space game;

    public static void main(String[] args) throws Exception {
        game = new Space(20, 20);
        game.setShip(new SpaceShip(10, 18));
        game.run();
    }

    /**
     * Метод делает паузу длинной delay миллисекунд.
     */
    public static void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ignored) {
        }
    }
}
