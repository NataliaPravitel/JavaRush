package com.javarush.task.task32.task3209;

//HTML Editor (7)
//Добавь класс MenuHelper. Это будет вспомогательный класс для инициализации и настройки меню.
//
//У меню будет следующая структура:
//- Файл
//- Новый
//- Открыть
//- Сохранить
//- Сохранить как...
//- Выход
//
//- Редактировать
//- Отменить
//- Вернуть
//- Вырезать
//- Копировать
//- Вставить
//
//- Стиль
//- Полужирный
//- Подчеркнутый
//- Курсив
//- Подстрочный знак
//- Надстрочный знак
//- Зачеркнутый
//
//- Выравнивание
//- По левому краю
//- По центру
//- По правому краю
//
//- Цвет
//- Красный
//- Оранжевый
//- Желтый
//- Зеленый
//- Синий
//- Голубой
//- Пурпурный
//- Черный
//
//- Шрифт
//- Шрифт
//- SansSerif, Serif, Monospaced, Dialog, DialogInput,
//- Размер шрифта
//- 6, 8, 10, 12, 14, 16, 20, 24, 32, 36, 48, 72
//
//- Помощь
//- О программе
//
//7.1. Реализуй в MenuHelper статический метод JMenuItem addMenuItem(JMenu parent, String text, ActionListener actionListener),
// где parent - меню в которое мы добавляем пункт, text - текст добавляемого пункта, actionListener - слушатель действий добавляемого пункта меню. Он должен:
//7.1.1. Создавать новый пункт меню JMenuItem, используя text.
//7.1.2. Устанавливать этому пункту слушателя действий с помощью метода addActionListener().
//7.1.3. Добавлять в parent созданный пункт меню.
//7.1.4. Возвращать созданный пункт меню.
//7.2. Реализуй статический метод JMenuItem addMenuItem(JMenu parent, Action action).
// Этот метод аналогичен предыдущему, но в качестве параметра принимает действие action, которое необходимо выполнить при выборе пункта меню.
//
//Подсказка: при создании JMenuItem передай в конструктор action. Разберись откуда возьмется имя пункта меню, если его не устанавливать дополнительно.
//
//7.3. Реализуй статический метод JMenuItem addMenuItem(JMenu parent, String text, Action action),
// который добавляет в parent новый пункт меню с текстом text и действием action при выборе этого метода.
// При реализации используй вызов метода из предыдущего пункта.
//7.4. Добавь в MenuHelper заглушки для следующих статический методов:
//7.4.1. void initHelpMenu(View view, JMenuBar menuBar) - инициализация меню помощи.
//7.4.2. void initFontMenu(View view, JMenuBar menuBar) - инициализация меню выбора шрифта.
//7.4.3. void initColorMenu(View view, JMenuBar menuBar) - инициализация меню выбора цвета.
//7.4.4. void initAlignMenu(View view, JMenuBar menuBar) - инициализация меню выравнивания.
//7.4.5. void initStyleMenu(View view, JMenuBar menuBar) - инициализация меню выбора стиля текста.
//7.4.6. void initEditMenu(View view, JMenuBar menuBar) - инициализация меню редактирования текста.
//7.4.7. void initFileMenu(View view, JMenuBar menuBar) - инициализация меню Файл.
//
//
//Требования:
//1. Класс MenuHelper должен быть создан в отдельном файле.
//2. Класс MenuHelper должен реализовывать публичный статический метод JMenuItem addMenuItem(JMenu parent, String text, ActionListener actionListener).
//3. Класс MenuHelper должен реализовывать публичный статический метод JMenuItem addMenuItem(JMenu parent, Action action).
//4. Класс MenuHelper должен реализовывать публичный статический метод JMenuItem addMenuItem(JMenu parent, String text, Action action).
//5. Класс MenuHelper должен содержать публичный статический метод void initHelpMenu(View view, JMenuBar menuBar).
//6. Класс MenuHelper должен содержать публичный статический метод void initFontMenu(View view, JMenuBar menuBar).
//7. Класс MenuHelper должен содержать публичный статический метод void initColorMenu(View view, JMenuBar menuBar).
//8. Класс MenuHelper должен содержать публичный статический метод void initAlignMenu(View view, JMenuBar menuBar).
//9. Класс MenuHelper должен содержать публичный статический метод void initStyleMenu(View view, JMenuBar menuBar).
//10. Класс MenuHelper должен содержать публичный статический метод void initEditMenu(View view, JMenuBar menuBar).
//11. Класс MenuHelper должен содержать публичный статический метод void initFileMenu(View view, JMenuBar menuBar).

import com.javarush.task.task32.task3209.actions.*;
import com.javarush.task.task32.task3209.listeners.TextEditMenuListener;
import com.javarush.task.task32.task3209.listeners.UndoMenuListener;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuHelper {
    public static JMenuItem addMenuItem(JMenu parent, String text, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(actionListener);
        parent.add(menuItem);
        return menuItem;
    }

    public static JMenuItem addMenuItem(JMenu parent, String text, Action action) {
        JMenuItem menuItem = addMenuItem(parent, action);
        menuItem.setText(text);
        return menuItem;
    }

    public static JMenuItem addMenuItem(JMenu parent, Action action) {
        JMenuItem menuItem = new JMenuItem(action);
        parent.add(menuItem);
        return menuItem;
    }

    public static void initHelpMenu(View view, JMenuBar menuBar) {
        JMenu helpMenu = new JMenu("Помощь");
        menuBar.add(helpMenu);

        addMenuItem(helpMenu, "О программе", view);
    }

    public static void initFontMenu(View view, JMenuBar menuBar) {
        JMenu fontMenu = new JMenu("Шрифт");
        menuBar.add(fontMenu);

        JMenu fontTypeMenu = new JMenu("Шрифт");
        fontMenu.add(fontTypeMenu);

        String[] fontTypes = {Font.SANS_SERIF, Font.SERIF, Font.MONOSPACED, Font.DIALOG, Font.DIALOG_INPUT};
        for (String fontType : fontTypes) {
            addMenuItem(fontTypeMenu, fontType, new StyledEditorKit.FontFamilyAction(fontType, fontType));
        }

        JMenu fontSizeMenu = new JMenu("Размер шрифта");
        fontMenu.add(fontSizeMenu);

        String[] fontSizes = {"6", "8", "10", "12", "14", "16", "20", "24", "32", "36", "48", "72"};
        for (String fontSize : fontSizes) {
            addMenuItem(fontSizeMenu, fontSize, new StyledEditorKit.FontSizeAction(fontSize, Integer.parseInt(fontSize)));
        }

        fontMenu.addMenuListener(new TextEditMenuListener(view));
    }

    public static void initColorMenu(View view, JMenuBar menuBar) {
        JMenu colorMenu = new JMenu("Цвет");
        menuBar.add(colorMenu);

        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Красный", Color.red));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Оранжевый", Color.orange));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Желтый", Color.yellow));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Зеленый", Color.green));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Синий", Color.blue));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Голубой", Color.cyan));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Пурпурный", Color.magenta));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Черный", Color.black));

        colorMenu.addMenuListener(new TextEditMenuListener(view));
    }

    public static void initAlignMenu(View view, JMenuBar menuBar) {
        JMenu alignMenu = new JMenu("Выравнивание");
        menuBar.add(alignMenu);

        addMenuItem(alignMenu, new StyledEditorKit.AlignmentAction("По левому краю", StyleConstants.ALIGN_LEFT));
        addMenuItem(alignMenu, new StyledEditorKit.AlignmentAction("По центру", StyleConstants.ALIGN_CENTER));
        addMenuItem(alignMenu, new StyledEditorKit.AlignmentAction("По правому краю", StyleConstants.ALIGN_RIGHT));

        alignMenu.addMenuListener(new TextEditMenuListener(view));
    }

    public static void initStyleMenu(View view, JMenuBar menuBar) {
        JMenu styleMenu = new JMenu("Стиль");
        menuBar.add(styleMenu);

        addMenuItem(styleMenu, "Полужирный", new StyledEditorKit.BoldAction());
        addMenuItem(styleMenu, "Подчеркнутый", new StyledEditorKit.UnderlineAction());
        addMenuItem(styleMenu, "Курсив", new StyledEditorKit.ItalicAction());

        styleMenu.addSeparator();

        addMenuItem(styleMenu, "Подстрочный знак", new SubscriptAction());
        addMenuItem(styleMenu, "Надстрочный знак", new SuperscriptAction());
        addMenuItem(styleMenu, "Зачеркнутый", new StrikeThroughAction());

        styleMenu.addMenuListener(new TextEditMenuListener(view));
    }

    public static void initEditMenu(View view, JMenuBar menuBar) {
        JMenu editMenu = new JMenu("Редактировать");
        menuBar.add(editMenu);

        JMenuItem undoItem = addMenuItem(editMenu, "Отменить", new UndoAction(view));
        JMenuItem redoItem = addMenuItem(editMenu, "Вернуть", new RedoAction(view));
        addMenuItem(editMenu, "Вырезать", new DefaultEditorKit.CutAction());
        addMenuItem(editMenu, "Копировать", new DefaultEditorKit.CopyAction());
        addMenuItem(editMenu, "Вставить", new DefaultEditorKit.PasteAction());

        editMenu.addMenuListener(new UndoMenuListener(view, undoItem, redoItem));
    }

    public static void initFileMenu(View view, JMenuBar menuBar) {
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);

        addMenuItem(fileMenu, "Новый", view);
        addMenuItem(fileMenu, "Открыть", view);
        addMenuItem(fileMenu, "Сохранить", view);
        addMenuItem(fileMenu, "Сохранить как...", view);
        fileMenu.addSeparator();
        addMenuItem(fileMenu, "Выход", view);
    }
}
