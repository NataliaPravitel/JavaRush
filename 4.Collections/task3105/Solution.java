package com.javarush.task.task31.task3105;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* 
Добавление файла в архив
В метод main приходит список аргументов.
Первый аргумент - полный путь к файлу fileName.
Второй аргумент - путь к zip-архиву.
Добавить файл (fileName) внутрь архива в директорию 'new'.
Если в архиве есть файл с таким именем, то заменить его.

Пример входных данных:
C:/result.mp3
C:/pathToTest/test.zip

Файлы внутри test.zip:
a.txt
b.txt

После запуска Solution.main архив test.zip должен иметь такое содержимое:
new/result.mp3
a.txt
b.txt

Подсказка: нужно сначала куда-то сохранить содержимое всех энтри, а потом записать в архив все энтри вместе с добавленным файлом.
Пользоваться файловой системой нельзя.


Требования:
1. В методе main создай ZipInputStream для архивного файла (второй аргумент main). Нужно вычитать из него все содержимое.
2. В методе main создай ZipOutputStream для архивного файла (второй аргумент main).
3. В ZipOutputStream нужно записать содержимое файла, который приходит первым аргументом в main.
4. В ZipOutputStream нужно записать все остальное содержимое, которое было вычитано из ZipInputStream.
5. Потоки для работы с архивом должны быть закрыты.
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        String newFileName = Paths.get(args[0]).getFileName().toString();
        String newPathInArchive = "new/" + newFileName;
        ZipEntry entry;
        Map<String, ByteArrayOutputStream> filesInArchive = new HashMap<>();

        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(args[1]));
        while ((entry = zipIn.getNextEntry()) != null) {
            if (entry.getName().endsWith(newFileName)) {
                newPathInArchive = entry.getName();
            } else {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int length = 0;
                byte[] buffer = new byte[1024];
                while ((length = zipIn.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }
                filesInArchive.put(entry.getName(), baos);
            }
        }
        zipIn.close();

        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(args[1]));
        zipOut.putNextEntry(new ZipEntry(newPathInArchive));
        Files.copy(Paths.get(args[0]), zipOut);
        for (Map.Entry<String, ByteArrayOutputStream> pair : filesInArchive.entrySet()) {
            zipOut.putNextEntry(new ZipEntry(pair.getKey()));
            pair.getValue().writeTo(zipOut);
        }
        zipOut.close();
    }
}
