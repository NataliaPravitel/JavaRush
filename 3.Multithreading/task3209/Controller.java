package com.javarush.task.task32.task3209;
//HTML Editor (1)
//Сегодня мы напишем HTML редактор с графическим интерфейсом.
//В качестве библиотеки для создания графического интерфейса воспользуемся Swing.
//А в качестве архитектурного каркаса нашего приложения будем использовать MVC модель.
//1.1. Объяви класс Controller и класс View.
//Класс View должен быть унаследован от JFrame и реализовывать интерфейс ActionListener.
//1.2. Добавь в класс Controller поля, отвечающие за представление View view и модель HTMLDocument document.
// Здесь и далее, классы, которые мы не объявляли, но используем в коде скорее всего все находятся в библиотеке swing.
//Например, класс HTMLDocument реализован в пакете javax.swing.text.html.
//1.3. Добавь в класс Controller поле, которое будет отвечать за файл, который сейчас открыт в нашем редакторе (текущий файл) File currentFile.
//1.4. Добавь конструктор класса Controller. Он должен принимать в качестве параметра представление и инициализировать соответствующее поле класса.
//1.5. Добавь в Controller пустой метод main.
//1.6. Добавь в класс View поле Controller controller.
//1.7. Добавь в класс View сеттер и геттер для поля controller.
//1.8. Добавь пустую реализацию метода, который объявлен в интерфейсе ActionListener.
//
//
//Требования:
//1. Класс Controller должен быть создан в отдельном файле.
//2. Класс View должен быть создан в отдельном файле.
//3. Класс View должен быть унаследован от JFrame и реализовывать интерфейс ActionListener.
//4. В классе Controller должны быть приватные поля: View view, HTMLDocument document, File currentFile.
//5. В классе Controller должен быть создан метод public static void main (String[] args).
//6. Класс Controller должен содержать конструктор с одним параметром, инициализирующий поле view.
//7. В классе View должно быть приватное поле Controller controller, а также сеттер и геттер к нему.
//8. В классе View должен быть метод public void actionPerformed(ActionEvent e).


//HTML Editor (2)
//2.1. Добавь в контроллер и представление по методу init(), пока без реализаций.
// Они будут отвечать за инициализацию контроллера и представления.
//2.2. Теперь напишем в классе Controller метод main().
//Он должен:
//2.2.1. Создавать объект представления.
//2.2.2. Создавать контроллер, используя представление.
//2.2.3. Устанавливать у представления контроллер.
//2.2.4. Инициализировать представление.
//2.2.5. Инициализировать контроллер. Контроллер должен инициализироваться после представления.
//2.3. Добавь в контроллер метод exit(), он должен вызывать статический метод exit у класса System.
//2.3.1. Метод exit в классе Controller не должен быть статическим.
//2.4. Добавь в представление метод exit(), он должен вызывать exit() у контроллера.
//
//
//Требования:
//1. Класс Controller должен содержать метод public void init().
//2. Класс View должен содержать метод public void init().
//3. Класс Controller должен реализовывать метод public void exit().
//4. Класс View должен реализовывать метод public void exit().
//5. Реализуй, согласно условию, метод public static void main(String[] args) у класса Controller.

//HTML Editor (15)
//Добавь в контроллер метод resetDocument(), который будет сбрасывать текущий документ. Он должен:
//15.1. Удалять у текущего документа document слушателя правок которые можно отменить/вернуть
// (найди подходящий для этого метод, унаследованный от AbstractDocument).
//Слушателя нужно запросить у представления (метод getUndoListener()).
//Не забудь проверить, что текущий документ существует (не null).
//15.2. Создавать новый документ по умолчанию и присваивать его полю document.
//
//Подсказка: воспользуйся подходящим методом класса HTMLEditorKit.
//
//15.3. Добавлять новому документу слушателя правок.
//15.4. Вызывать у представления метод update().
//
//
//Требования:
//1. Класс Controller должен содержать публичный метод resetDocument(), который будет сбрасывать текущий документ.
//2. Метод resetDocument() должен удалять у текущего документа document слушателя правок через метод removeUndoableEditListener().
//3. Метод resetDocument() должен создавать новый документ по умолчанию через метод createDefaultDocument().
//4. Метод resetDocument() должен добавлять новому документу слушателя правок через метод addUndoableEditListener().
//5. Метод resetDocument() должен вызывать у представления метод update().

//HTML Editor (16)
//Добавь метод setPlainText(String text) в контроллер.
//Он будет записывать переданный текст с html тегами в документ document. При его реализации:
//16.1. Сбрось документ.
//16.2. Создай новый реадер StringReader на базе переданного текста.
//16.3. Вызови метод read() из класса HTMLEditorKit, который вычитает данные из реадера в документ document.
//16.4. Проследи, чтобы метод не кидал исключения. Их необходимо просто логировать.
//
//
//Требования:
//1. Класс Controller должен содержать публичный метод setPlainText(String text), который будет записывать переданный текст с html тегами в документ document.
//2. Метод setPlainText(String text) должен сбрасывать документ через метод resetDocument().
//3. Метод setPlainText(String text) должен создавать новый StringReader на базе переданного параметра.
//4. Метод setPlainText(String text) должен вызывать метод read() у объекта HTMLEditorKit.
//5. Если в методе setPlainText(String text) возникнет исключительная ситуация, то исключение должно логироваться через метод log у класса ExceptionHandler.

//HTML Editor (17)
//Добавь метод String getPlainText() в контроллер. Он должен получать текст из документа со всеми html тегами.
//17.1. Создай объект StringWriter.
//17.2. Перепиши все содержимое из документа document в созданный объект с помощью метода write класса HTMLEditorKit.
//17.3. Как обычно, метод не должен кидать исключений.
//
//
//Требования:
//1. Класс Controller должен содержать публичный метод String getPlainText().
//2. В методе getPlainText() должен создаваться объект класса StringWriter.
//3. Метод getPlainText() должен вызывать метод write() у объекта HTMLEditorKit.
//4. Если в методе getPlainText() возникнет исключительная ситуация, то исключение должно логироваться через метод log у класса ExceptionHandler.
//5. Метод getPlainText() должен возвращать текст из документа со всеми html тегами.

//HTML Editor (20)
//20.1. Реализуй метод создания нового документа createNewDocument() в контроллере. Он должен:
//20.1.1. Выбирать html вкладку у представления.
//20.1.2. Сбрасывать текущий документ.
//20.1.3. Устанавливать новый заголовок окна, например: "HTML редактор". Воспользуйся методом setTitle(), который унаследован в нашем представлении.
//20.1.4. Сбрасывать правки в Undo менеджере. Используй метод resetUndo представления.
//20.1.5. Обнулить переменную currentFile.
//20.2. Реализуй метод инициализации init() контроллера.
//Он должен просто вызывать метод создания нового документа.
//Проверь работу пункта меню Новый.
//
//
//Требования:
//1. Метод createNewDocument() в контроллере должен выбирать html вкладку у представления.
//2. Метод createNewDocument() в контроллере должен сбрасывать текущий документ.
//3. Метод createNewDocument() в контроллере должен устанавливать новый заголовок окна.
//4. Метод createNewDocument() в контроллере должен сбрасывать правки в Undo менеджере.
//5. Метод createNewDocument() в контроллере должен обнулить currentFile.
//6. Метод init() в контроллере должен вызывать метод создания нового документа.

//HTML Editor (22)
//Реализуем в контроллере метод для сохранения файла под новым именем saveDocumentAs().
//Реализация должна:
//22.1. Переключать представление на html вкладку.
//22.2. Создавать новый объект для выбора файла JFileChooser.
//22.3. Устанавливать ему в качестве фильтра объект HTMLFileFilter.
//22.4. Показывать диалоговое окно "Save File" для выбора файла.
//22.5. Если пользователь подтвердит выбор файла:
//22.5.1. Сохранять выбранный файл в поле currentFile.
//22.5.2. Устанавливать имя файла в качестве заголовка окна представления.
//22.5.3. Создавать FileWriter на базе currentFile.
//22.5.4. Переписывать данные из документа document в объекта FileWriter-а аналогично тому, как мы это делали в методе getPlainText().
//22.6. Метод не должен кидать исключения.
//Проверь работу пункта меню Сохранить как...
//
//
//Требования:
//1. Метод saveDocumentAs() в контроллере должен переключать представление на html вкладку.
//2. Метод saveDocumentAs() в контроллере должен создавать новый объект для выбора файла JFileChooser.
//3. Метод saveDocumentAs() в контроллере должен устанавливать объекту класса JFileChooser в качестве фильтра объект HTMLFileFilter.
//4. Метод saveDocumentAs() в контроллере должен, используя метод showSaveDialog() у JFileChooser показывать диалоговое окно "Save File" для выбора файла.
//5. Метод saveDocumentAs() в контроллере должен сохранять выбранный файл в поле currentFile, если пользователь подтвердит выбор файла.
//6. Метод saveDocumentAs() в контроллере должен устанавливать имя файла в качестве заголовка окна представления, если пользователь подтвердит выбор файла.
//7. Метод saveDocumentAs() в контроллере должен создавать FileWriter на базе currentFile, если пользователь подтвердит выбор файла.
//8. Метод saveDocumentAs() в контроллере должен используя HTMLEditorKit переписывать данные из документа document в объект FileWriter-а, если пользователь подтвердит выбор файла.
//9. Метод saveDocumentAs() в контроллере не должен кидать исключения, а логировать через ExceptionHandler.

//HTML Editor (23)
//23.1. Напишем метод для сохранения открытого файла saveDocument().
//Метод должен работать также, как saveDocumentAs(), но не запрашивать файл у пользователя, а использовать currentFile.
//Если currentFile равен null, то вызывать метод saveDocumentAs().
//23.2. Пришло время реализовать метод openDocument().
//Метод должен работать аналогично методу saveDocumentAs(), в той части, которая отвечает за выбор файла.
//
//Подсказка: Обрати внимание на имя метода для показа диалогового окна.
//
//Когда файл выбран, необходимо:
//23.2.1. Установить новое значение currentFile.
//23.2.2. Сбросить документ.
//23.2.3. Установить имя файла в заголовок у представления.
//23.2.4. Создать FileReader, используя currentFile.
//23.2.5. Вычитать данные из FileReader-а в документ document с помощью объекта класса HTMLEditorKit.
//23.2.6. Сбросить правки (вызвать метод resetUndo представления).
//23.2.7. Если возникнут исключения - залогируй их и не пробрасывай наружу.
//Проверь работу пунктов меню Сохранить и Открыть.
//
//
//Требования:
//1. Метод saveDocument() в контроллере должен переключать представление на html вкладку.
//2. Метод saveDocument() в контроллере должен создавать FileWriter на базе currentFile, если currentFile != null.
//3. Метод saveDocument() в контроллере должен используя HTMLEditorKit переписывать данные из документа document в объект FileWriter-а, если currentFile != null.
//4. Метод saveDocument() в контроллере должен вызывать метод saveDocumentAs(), если currentFile == null.
//5. Метод saveDocument() в контроллере не должен кидать исключения, а логировать через ExceptionHandler.
//6. Метод openDocument() в контроллере должен переключать представление на html вкладку.
//7. Метод openDocument() в контроллере должен создавать новый объект для выбора файла JFileChooser.
//8. Метод openDocument() в контроллере должен устанавливать объекту класса JFileChooser в качестве фильтра объект HTMLFileFilter.
//9. Метод openDocument() в контроллере должен, используя метод showOpenDialog() у JFileChooser показывать диалоговое окно "Open File" для выбора файла.
//10. Метод openDocument() в контроллере должен установить новое значение currentFile, если пользователь подтвердит выбор файла.
//11. Метод openDocument() в контроллере должен сбросить документ, если пользователь подтвердит выбор файла.
//12. Метод openDocument() в контроллере должен устанавливать имя файла в качестве заголовка окна представления, если пользователь подтвердит выбор файла.
//13. Метод openDocument() в контроллере должен создавать FileReader на базе currentFile, если пользователь подтвердит выбор файла.
//14. Метод openDocument() в контроллере должен используя HTMLEditorKit вычитать данные из FileReader-а в документ document, если пользователь подтвердит выбор файла.
//15. Метод openDocument() в контроллере должен сбросить правки, если пользователь подтвердит выбор файла.
//16. Метод openDocument() в контроллере не должен кидать исключения, а логировать через ExceptionHandler.

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;

public class Controller {
  private View view;
  private HTMLDocument document;
  private File currentFile;

  public Controller(View view) {
    this.view = view;
  }

  public HTMLDocument getDocument() {
    return document;
  }

  public void init() {
    createNewDocument();
  }

  public void exit() {
    System.exit(0);
  }

  public void resetDocument() {
    if (document != null) {
      document.removeUndoableEditListener(view.getUndoListener());
    }
    document = (HTMLDocument) new HTMLEditorKit().createDefaultDocument();
    document.addUndoableEditListener(view.getUndoListener());
    view.update();
  }

  public void setPlainText(String text) {
    resetDocument();
    StringReader stringReader = new StringReader(text);
    try {
      new HTMLEditorKit().read(stringReader, document,0);
    } catch (Exception e) {
      ExceptionHandler.log(e);
    }
  }

  public String getPlainText() {
    StringWriter stringWriter = new StringWriter();
    try {
      new HTMLEditorKit().write(stringWriter, document, 0, document.getLength());
    } catch (Exception e) {
      ExceptionHandler.log(e);
    }
    return stringWriter.toString();
  }
  
  public void createNewDocument() {
    view.selectHtmlTab();
    resetDocument();
    view.setTitle("HTML редактор");
    view.resetUndo();
    currentFile = null;
  }

  public void openDocument() {
    view.selectHtmlTab();
    JFileChooser chooser = new JFileChooser();
    chooser.setFileFilter(new HTMLFileFilter());
    if (chooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
      currentFile = chooser.getSelectedFile();
      resetDocument();
      view.setTitle(currentFile.getName());
      try {
        FileReader reader = new FileReader(currentFile);
        new HTMLEditorKit().read(reader, document, 0);
      } catch (Exception e) {
        ExceptionHandler.log(e);
      }
      view.resetUndo();
    }
  }

  public void saveDocument() {
    view.selectHtmlTab();
    if (currentFile != null) {
      try {
        FileWriter writer = new FileWriter(currentFile);
        new HTMLEditorKit().write(writer, document, 0, document.getLength());
      } catch (Exception e) {
        ExceptionHandler.log(e);
      }
    } else {
      saveDocumentAs();
    }
  }

  public void saveDocumentAs() {
    view.selectHtmlTab();
    JFileChooser chooser = new JFileChooser();
    chooser.setFileFilter(new HTMLFileFilter());
    if (chooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
      currentFile = chooser.getSelectedFile();
      view.setTitle(currentFile.getName());
      try {
        FileWriter writer = new FileWriter(currentFile);
        new HTMLEditorKit().write(writer, document, 0, document.getLength());
      } catch (Exception e) {
        ExceptionHandler.log(e);
      }
    }
  }

  public static void main(String[] args) {
    View view = new View();
    Controller controller = new Controller(view);
    view.setController(controller);
    view.init();
    controller.init();
  }
}
