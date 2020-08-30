package com.javarush.task.task31.task3110.command;

import com.javarush.task.task31.task3110.ConsoleHelper;
import com.javarush.task.task31.task3110.ZipFileManager;
import com.javarush.task.task31.task3110.exception.PathIsNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipExtractCommand extends ZipCommand {
  //4. Реализуй метод execute() класса ZipExtractCommand, по аналогии с таким же методом класса ZipCreateCommand,
  // сделай такой же блок try-catch, только поменяй сообщения выводимые пользователю, чтобы он понял,
// что сейчас мы будем распаковывать архив, и что нужно ввести полное имя архива и директорию, куда будем распаковывать.
//Не забудь вызвать метод extractAll класса ZipFileManager, а не createZip, как это было в ZipCreateCommand
  @Override
  public void execute() throws Exception {
    try {
      ConsoleHelper.writeMessage("Распаковка архива.");

      ZipFileManager zipFileManager = getZipFileManager();

      ConsoleHelper.writeMessage("Введите полное имя директории для распаковки:");
      Path sourcePath = Paths.get(ConsoleHelper.readString());

      zipFileManager.extractAll(sourcePath);
      ConsoleHelper.writeMessage("Архив распакован.");

    } catch (PathIsNotFoundException e) {
      ConsoleHelper.writeMessage("Вы неверно указали имя файла или директории.");
    }
  }
}
