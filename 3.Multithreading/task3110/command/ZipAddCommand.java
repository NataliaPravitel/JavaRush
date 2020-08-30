package com.javarush.task.task31.task3110.command;

import com.javarush.task.task31.task3110.ConsoleHelper;
import com.javarush.task.task31.task3110.ZipFileManager;
import com.javarush.task.task31.task3110.exception.PathIsNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipAddCommand extends ZipCommand {
  @Override
  public void execute() throws Exception {
    try {
      ConsoleHelper.writeMessage("Добавление файлов в архив");

      ZipFileManager zipFileManager = getZipFileManager();

      ConsoleHelper.writeMessage("Введите полный путь к файлу/папке для добавления в архив:");
      Path sourcePath = Paths.get(ConsoleHelper.readString());
      zipFileManager.addFile(sourcePath);

      ConsoleHelper.writeMessage("Файл/папка добавлен(а).");

    } catch (PathIsNotFoundException e) {
      ConsoleHelper.writeMessage("Вы неверно указали имя файла или директории.");
    }
  }
}
