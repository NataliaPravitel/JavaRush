package com.javarush.task.task32.task3209.actions;
//HTML Editor (8)
//Пришло время добавить все необходимые пункты меню и написать классы действий, выполняемые при нажатии.
//Это довольно трудоемкая и рутинная работа, а ты отличный ученик и не хотелось бы тебя огорчать.
// Поэтому, в качестве бонуса ты получаешь готовый класс MenuHelper! А ты:
//8.1. Напиши в пакете actions заглушки для следующих классов:
//8.1.1. Класс отмены действия UndoAction. Он должен наследоваться от AbstractAction и содержать конструктор UndoAction(View view).
//8.1.2. Класс возврата действия RedoAction. Требования аналогичны требованиям к UndoAction.
//8.1.3. Класс StrikeThroughAction, который отвечает за стиль текста "Зачеркнутый". Унаследуй его от StyledEditorKit.StyledTextAction.
//8.1.4. Класс SubscriptAction, который отвечает за стиль текста "Подстрочный знак". Его также унаследуй его от StyledEditorKit.StyledTextAction.
//8.1.5. Класс SuperscriptAction. Он будет отвечать за стиль "Надстрочный знак". Добавь ему правильный родительский класс.
//8.2. Напиши в пакете listeners заглушки для классов:
//8.2.1. UndoMenuListener, он должен реализовывать интерфейс MenuListener и иметь конструктор
// UndoMenuListener(View view, JMenuItem undoMenuItem, JMenuItem redoMenuItem).
//Чем он будет заниматься узнаешь чуть позже.
//8.2.2. TextEditMenuListener. Этот класс также должен реализовывать интерфейс MenuListener.
//Добавь ему конструктор TextEditMenuListener(View view). В следующих заданиях мы рассмотрим его детальнее.
//
//
//Требования:
//1. Класс UndoAction должен быть создан в отдельном файле.
//2. Класс RedoAction должен быть создан в отдельном файле.
//3. Класс StrikeThroughAction должен быть создан в отдельном файле.
//4. Класс SubscriptAction должен быть создан в отдельном файле.
//5. Класс SuperscriptAction должен быть создан в отдельном файле.
//6. Класс UndoMenuListener должен быть создан в отдельном файле.
//7. Класс TextEditMenuListener должен быть создан в отдельном файле.

//HTML Editor (12)
//12.1. Напиши реализацию класса RedoAction:
//12.1.1. Добавь в класс поле View. Добавь его инициализацию в конструкторе.
//12.1.2. Реализуй метод actionPerformed(ActionEvent actionEvent), он должен вызывать метод redo() у представления.
//12.2. Напиши реализацию класса UndoAction по аналогии с RedoAction.
//12.3. Изучи реализацию класса StrikeThroughAction, которую ты получил вместе с заданием и реализуй аналогичным образом классы:
//12.3.1. SubscriptAction
//12.3.2. SuperscriptAction
//Запусти программу и убедись, что пункты меню Подстрочный знак, Надстрочный знак и Зачеркнутый работают.
//Пункты, отвечающие за отмену и возврат действия пока не подключены к контроллеру и мы не сможем их проверить.
//
//
//Требования:
//1. Класс RedoAction должен содержать поле View view.
//2. Конструктор класса RedoAction(View view) должен инициализировать поле view.
//3. Метод actionPerformed(ActionEvent actionEvent) класса RedoAction должен вызывать метод redo() у представления.
//4. Класс UndoAction должен содержать поле View view.
//5. Конструктор класса UndoAction(View view) должен инициализировать поле view.
//6. Метод actionPerformed(ActionEvent actionEvent) класса UndoAction должен вызывать метод undo() у представления.
//7. Конструктор без параметров класса SubscriptAction должен вызывать конструктор суперкласса с параметром StyleConstants.Subscript.
//8. Метод actionPerformed(ActionEvent actionEvent) класса SubscriptAction должен использовать метод setSubscript у StyleConstants с параметрами:
// SimpleAttributeSet и !StyleConstants.isSubscript(mutableAttributeSet).
//9. Конструктор без параметров класса SuperscriptAction должен вызывать конструктор суперкласса с параметром StyleConstants.Superscript.
//10. Метод actionPerformed(ActionEvent actionEvent) класса SuperscriptAction должен использовать метод setSuperscript у StyleConstants с параметрами:
// SimpleAttributeSet и !StyleConstants.isSuperscript(mutableAttributeSet).

import com.javarush.task.task32.task3209.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UndoAction extends AbstractAction {
  private View view;

  public UndoAction(View view) {
    this.view = view;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    view.undo();
  }
}
