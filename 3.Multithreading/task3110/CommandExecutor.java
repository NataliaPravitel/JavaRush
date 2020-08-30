package com.javarush.task.task31.task3110;

import com.javarush.task.task31.task3110.command.*;

import java.util.HashMap;
import java.util.Map;
//Archiver (6)
// Представь, что пользователь сообщает нам с помощью переменной Operation operation, что он хочет сделать.
//Тогда мы должны проверить значение этой переменной, создать объект соответствующего класса команды и вызвать у него метод execute().
//Чтобы не создавать объект класса нужной команды каждый раз, его нужно где-то хранить.
//Создадим для этих целей класс CommandExecutor, пусть у него будет открытым только один публичный
// статический метод execute(Operation operation), который найдет нужную команду и вызовет у нее метод execute.
//
//1. Создай класс CommandExecutor.
//Он должен быть в корне задачи, не стоит добавлять его в пакет command
//2. Запрети явный вызов конструктора этого класса
//3. Добавь в класс приватное статическое константное хранилище команд Map<Operation, Command> allKnownCommandsMap
//4. Проинициализируй переменную allKnownCommandsMap так, чтобы каждому значению из Operation соответствовала правильная команда.
//5. Реализуй публичный статический метод execute(Operation operation) throws Exception,
// который должен брать нужную команду из allKnownCommandsMap и вызывать у нее метод execute.
//Догадался ты или нет, но ты только что реализовал паттерн Команда!
//
//Требования:
//•	В корне задачи должен быть создан класс CommandExecutor.
//•	Запрети явный вызов конструктора CommandExecutor.
//•	В классе CommandExecutor нужно создать приватное статическое константное поле Map<Operation, Command> allKnownCommandsMap.
//•	Поле allKnownCommandsMap должно быть проинициализировано всеми значениями Operation и соответствующими Command-классами.
//•	В классе CommandExecutor нужно реализовать метод execute(Operation operation), который будет вызывать execute у нужной команды из allKnownCommandsMap.

public class CommandExecutor {
    private static final Map<Operation, Command> allKnownCommandsMap = new HashMap<>();

    static {
        allKnownCommandsMap.put(Operation.CREATE, new ZipCreateCommand());
        allKnownCommandsMap.put(Operation.ADD, new ZipAddCommand());
        allKnownCommandsMap.put(Operation.REMOVE, new ZipRemoveCommand());
        allKnownCommandsMap.put(Operation.EXTRACT, new ZipExtractCommand());
        allKnownCommandsMap.put(Operation.CONTENT, new ZipContentCommand());
        allKnownCommandsMap.put(Operation.EXIT, new ExitCommand());
    }

    private CommandExecutor() {
    }

    public static void execute(Operation operation) throws Exception {
        allKnownCommandsMap.get(operation).execute();
    }
}