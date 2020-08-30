package com.javarush.task.task31.task3110.command;

import com.javarush.task.task31.task3110.ConsoleHelper;
import com.javarush.task.task31.task3110.ZipFileManager;

import java.nio.file.Paths;

public class ZipRemoveCommand extends ZipCommand {
  @Override
  public void execute() throws Exception {
    ConsoleHelper.writeMessage("Удаление файлов из архива.");
    ZipFileManager zipFileManager = getZipFileManager();
    ConsoleHelper.writeMessage("Введите относительный путь к удаляемому файлу или папке");
    zipFileManager.removeFile(Paths.get(ConsoleHelper.readString()));
    ConsoleHelper.writeMessage("Файл/папка удален(а)");
  }
}
