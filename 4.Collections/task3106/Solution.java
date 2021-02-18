package com.javarush.task.task31.task3106;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* 
Разархивируем файл
В метод main приходит список аргументов.
Первый аргумент - имя результирующего файла resultFileName, остальные аргументы - имена файлов fileNamePart.
Каждый файл (fileNamePart) - это кусочек zip архива. Нужно разархивировать целый файл, собрав его из кусочков.
Записать разархивированный файл в resultFileName.
Архив внутри может содержать файл большой длины, например, 50Mb.
Внутри архива может содержаться файл с любым именем.

Пример входных данных. Внутри архива находится один файл с именем abc.mp3:
C:/result.mp3
C:/pathToTest/test.zip.003
C:/pathToTest/test.zip.001
C:/pathToTest/test.zip.004
C:/pathToTest/test.zip.002


Требования:
1. В методе main нужно создать ZipInputStream для архива, собранного из кусочков файлов.
Файлы приходят аргументами в main, начиная со второго.
2. Создай поток для записи в файл, который приходит первым аргументом в main.
Запиши туда содержимое файла из архива.
3. Поток для чтения из архива должен быть закрыт.
4. Поток для записи в файл должен быть закрыт.
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        List<String> archiveParts = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            archiveParts.add(args[i]);
        }
        Collections.sort(archiveParts);

        List<FileInputStream> streamsArrayList = new ArrayList<>();
        for (String fileName : archiveParts) {
            streamsArrayList.add(new FileInputStream(fileName));
        }

        ZipInputStream zis = new ZipInputStream(new SequenceInputStream(
                Collections.enumeration(streamsArrayList)));
        FileOutputStream fos = new FileOutputStream(args[0]);

        byte[] buffer = new byte[2048];
        int len;
        while (zis.getNextEntry() != null) {
            while ((len = zis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                fos.flush();
            }
            zis.closeEntry();
        }
        zis.close();
        fos.close();
    }
}
