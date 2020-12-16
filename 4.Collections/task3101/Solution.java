package com.javarush.task.task31.task3101;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/* 
Проход по дереву файлов
1. На вход метода main() подаются два параметра.
Первый - path - путь к директории, второй - resultFileAbsolutePath - имя (полный путь) существующего файла,
который будет содержать результат.
2. Переименовать resultFileAbsolutePath в allFilesContent.txt (используй метод FileUtils.renameFile(),
и, если понадобится, FileUtils.isExist()).
3. Для каждого файла в директории path и в ее всех вложенных поддиректориях выполнить следующее:
Если у файла длина в байтах НЕ больше 50, то для всех таких файлов:
3.1. Отсортировать их по имени файла в возрастающем порядке, путь не учитывать при сортировке.
3.2. В allFilesContent.txt последовательно записать содержимое всех файлов из п. 3.1.
После каждого тела файла записать "\n".
Все файлы имеют расширение txt.
В качестве разделителя пути используй "/".
Для создания файлов используй конструктор File(String pathname).


Требования:
1. Файл, который приходит вторым параметром в main, должен быть переименован в allFilesContent.txt.
2. Нужно создать поток для записи в переименованный файл.
3. Содержимое всех файлов, размер которых не превышает 50 байт, должно быть записано в
файл allFilesContent.txt в отсортированном по имени файла порядке.
4. Поток для записи в файл нужно закрыть.
5. Не используй статические переменные.
*/

public class Solution {
    public static void main(String[] args) {
        String path = args[0];
        File resultFileAbsolutePath = new File(args[1]);
        File directory = new File(path);
        File allFilesContent = new File(resultFileAbsolutePath.getParent() + "/allFilesContent.txt");
        FileUtils.renameFile(resultFileAbsolutePath, allFilesContent);

        try (FileOutputStream fileOutputStream = new FileOutputStream(allFilesContent)) {
            List<File> fileList = new ArrayList<>();
            getListFiles(directory, fileList);
            Collections.sort(fileList, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });

            for (File file : fileList) {
                byte[] data = Files.readAllBytes(Paths.get(file.getPath()));
                if (data.length > 0) {
                    fileOutputStream.write(Files.readAllBytes(Paths.get(file.getPath())));
                    fileOutputStream.write("\n".getBytes(StandardCharsets.UTF_8));
                }
            }

        } catch (IOException exception) {

        }
    }

    private static void getListFiles(File directory, List<File> listFile)
            throws IOException {

        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                Path path = Paths.get(file.getAbsolutePath());
                if (Files.readAllBytes(path).length <= 50) {
                    listFile.add(file);
                }
            } else {
                getListFiles(file, listFile);
            }
        }
    }
}
