package com.javarush.task.task22.task2213;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
//Тетрис(12)
//В тетрисе мы управляем движением фигурки с помощью клавиатуры.
//
//Тут есть 4 действия:
//движение влево (кнопка влево)
//движение вправо (кнопка вправо)
//поворот фигурки (цифра 5 на доп.клавиатуре справа)
//падение вниз (пробел)
//
//Мы будем обрабатывать ввод с клавиатуры в методе run() класса Tetris.
//
//И тут у меня для тебя две новости: хорошая и плохая
//Плохая новость состоит в том, что java не позволяет считать нажатые символы с клавиатуры,
//пока пользователь не нажмет enter.
//Не очень удобно, правда?
//
//Хорошая новость состоит в том, что я написал специальный класс (KeyboardObserver), который позволяет обойти это ограничение.
//Так что ты можешь воспользоваться им.
//
//Есть еще и отличная новость.
//Ты до сих пор отлично справлялся, поэтому я помогу тебе немного.
//Я написал за тебя методы:
//а) createRandomFigure в FigureFactory
//б) run в Tetris
//
//Изучи их внимательно и переходи дальше.
//
//
//Requirements:
//1. Внимательно изучи метод run в классе Tetris.
//2. Внимательно изучи метод createRandomFigure в классе FigureFactory.
//3. Внимательно изучи класс KeyboardObserver.

public class KeyboardObserver extends Thread {
    private Queue<KeyEvent> keyEvents = new ArrayBlockingQueue<KeyEvent>(100);

    private JFrame frame;

    @Override
    public void run() {
        frame = new JFrame("KeyPress Tester");
        frame.setTitle("Transparent JFrame Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setUndecorated(true);
        frame.setSize(400, 400);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new GridBagLayout());

        frame.setOpacity(0.0f);
        frame.setVisible(true);

        frame.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //do nothing
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.exit(0);
            }
        });


        frame.addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e) {
                //do nothing
            }

            public void keyReleased(KeyEvent e) {
                //do nothing
            }

            public void keyPressed(KeyEvent e) {
                keyEvents.add(e);
            }
        });
    }


    public boolean hasKeyEvents() {
        return !keyEvents.isEmpty();
    }

    public KeyEvent getEventFromTop() {
        return keyEvents.poll();
    }
}

