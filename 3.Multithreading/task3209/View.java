package com.javarush.task.task32.task3209;
//HTML Editor (3)
//Графический интерфейс будет представлять собой окно, в котором будет меню и панель с двумя вкладками.
//На первой вкладке будет располагаться текстовая панель, которая будет отрисовывать html страницу.
// На ней можно будет форматировать и редактировать текст страницы.
//На второй вкладке будет редактор, который будет отображать код html страницы,
// в нем будут видны все используемые html теги. В нем также можно будет менять текст страницы, добавлять и удалять различные теги.
//3.1. Добавь и проинициализируй поля в классе представления:
//3.1.1. JTabbedPane tabbedPane - это будет панель с двумя вкладками.
//3.1.2. JTextPane htmlTextPane - это будет компонент для визуального редактирования html.
//Он будет размещен на первой вкладке.
//3.1.3. JEditorPane plainTextPane - это будет компонент для редактирования html в виде текста,
// он будет отображать код html (теги и их содержимое).
//3.2. Добавь класс FrameListener в пакет listeners. Он должен:
//3.2.1. Быть унаследован от WindowAdapter.
//3.2.2. Иметь поле View view.
//3.2.3. В конструкторе принимать View и инициализировать внутреннее поле.
//3.2.4. Иметь переопределенный метод windowClosing(WindowEvent windowEvent), который должен вызывать exit() у представления.
//
//
//Требования:
//1. Класс View должен содержать приватное проинициализированое поле JTabbedPane tabbedPane.
//2. Класс View должен содержать приватное проинициализированое поле JTextPane htmlTextPane.
//3. Класс View должен содержать приватное проинициализированое поле JEditorPane plainTextPane.
//4. Класс FrameListener должен быть создан в отдельном файле.
//5. Класс FrameListener должен содержать приватное поле View view.
//6. Класс FrameListener должен содержать конструктор с одним параметром, инициализирующий поле view.
//7. В классе FrameListenerМетод должен быть метод windowClosing(WindowEvent windowEvent).

//HTML Editor (4)
//4.1. Объяви методы initMenuBar() и initEditor() в классе View. Они будут отвечать за инициализацию меню и панелей редактора.
//4.2. Объяви в представлении метод initGui(). Он будет инициализировать графический интерфейс.
//Вызови из него инициализацию меню initMenuBar(), инициализацию редактора initEditor() и метод pack(), реализацию которого мы унаследовали от класса JFrame.
//Разберись что делает метод pack().
//4.3. Реализуй метод init() представления. Он должен:
//4.3.1. Вызывать инициализацию графического интерфейса initGui().
//4.3.2. Добавлять слушателя событий нашего окна. В качестве подписчика создай и используй объект класса FrameListener.
//В качестве метода для добавления подписчика используй подходящий метод из класса Window от которого наследуется и наш класс через классы JFrame и Frame.
//4.3.3. Показывать наше окно. Используй метод setVisible с правильным параметром.
//На этом этапе приложение при запуске должно показывать окно, которое можно растягивать, разворачивать, закрыть и т.д.
//
//
//Требования:
//1. Класс View должен содержать метод public void initMenuBar().
//2. Класс View должен содержать метод public void initEditor().
//3. Класс View должен реализовывать метод public void initGui().
//4. Реализуй метод init() класса View, согласно задания.

//HTML Editor (6)
//Реализуй метод инициализации панелей редактора initEditor(). Он должен:
//6.1. Устанавливать значение "text/html" в качестве типа контента для компонента htmlTextPane.
//Найди и используй подходящий метод.
//6.2. Создавать новый локальный компонент JScrollPane на базе htmlTextPane.
//6.3. Добавлять вкладку в панель tabbedPane с именем "HTML" и компонентом из предыдущего пункта.
//6.4. Создавать новый локальный компонент JScrollPane на базе plainTextPane.
//6.5. Добавлять еще одну вкладку в tabbedPane с именем "Текст" и компонентом из предыдущего пункта.
//6.6. Устанавливать предпочтительный размер панели tabbedPane.
//6.7. Создавать объект класса TabbedPaneChangeListener и устанавливать его в качестве слушателя изменений в tabbedPane.
//6.8. Добавлять по центру панели контента текущего фрейма нашу панель с вкладками.
//Получить панель контента текущего фрейма можно с помощью метода getContentPane(), его реализация унаследовалась от JFrame.
//Подумай, метод с какими параметрами необходимо вызвать, чтобы панель с вкладками отображалась по центру панели контента текущего фрейма.
//После запуска приложения можно будет увидеть текущие результаты: две независимые закладки (HTML и Текст), в каждой из которых можно набирать свой текст.
//
//
//Требования:
//1. В методе initEditor() для компонента htmlTextPane должен устанавливаться тип контента "text/html" через сеттер setContentType.
//2. В методе initEditor() должен создаваться новый локальный компонент JScrollPane через конструктор принимающий htmlTextPane.
//3. В методе initEditor() для компонента tabbedPane должна добавляться вкладка с именем "HTML" и созданным компонентом JScrollPane на базе htmlTextPane.
//4. В методе initEditor() должен создаваться новый локальный компонент JScrollPane через конструктор принимающий plainTextPane.
//5. В методе initEditor() для компонента tabbedPane должна добавляться вкладка с именем "Текст" и созданным компонентом JScrollPane на базе plainTextPane.
//6. В методе initEditor() для компонента tabbedPane должен устанавливаться предпочтительный размер панели, через сеттер setPreferredSize.
//7. В методе initEditor() для компонента tabbedPane должен добавляться слушатель TabbedPaneChangeListener через метод addChangeListener.
//8. Метод initEditor() должен добавлять по центру панели контента текущего фрейма нашу панель с вкладками, через getContentPane().add().

import com.javarush.task.task32.task3209.listeners.FrameListener;
import com.javarush.task.task32.task3209.listeners.TabbedPaneChangeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {
  private Controller controller;
  private JTabbedPane tabbedPane = new JTabbedPane();
  private JTextPane htmlTextPane = new JTextPane();
  private JEditorPane plainTextPane = new JEditorPane();

  public Controller getController() {
    return controller;
  }

  public void setController(Controller controller) {
    this.controller = controller;
  }

  public void init() {
    initGui();
    addWindowListener(new FrameListener(this));
    setVisible(true);
  }

  public void initMenuBar() {

  }

  public void initEditor() {
    htmlTextPane.setContentType("text/html");
    tabbedPane.addTab("HTML", new JScrollPane(htmlTextPane));
    tabbedPane.addTab("Текст", new JScrollPane(plainTextPane));
    tabbedPane.setPreferredSize(new Dimension(600, 600));
    tabbedPane.addChangeListener(new TabbedPaneChangeListener(this));
    this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
  }

  public void initGui() {
    initMenuBar();
    initEditor();
    pack();
  }

  public void exit() {
    controller.exit();
  }

  public void selectedTabChanged() {
  }

  @Override
  public void actionPerformed(ActionEvent e) {
  }


}
