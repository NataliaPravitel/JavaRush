package com.javarush.task.task31.task3110;
//1
//Давай напишем архиватор. Архиватор, как минимум, должен уметь архивировать и разархивировать файлы.
//
//Начнем с первого.
//Нам потребуется менеджер архива. Он будет совершать операции над файлом архива (файлом, который будет храниться на диске и иметь расширение zip).
//Класс, который будет этим заниматься, назовем ZipFileManager.
//А главный класс приложения "Архиватор" будет называться Archiver.
//В программировании и не только, есть понятие полного (абсолютного) и относительного пути.
//Для начала, разберемся что-же такое путь вообще.
//Путь (англ. Path) - это набор символов, который показывает, где в операционной системе находится какой-то файл или папка.
//Полный или абсолютный путь - это путь, начинающийся с корневой директории.
//В операционной системе Windows, корневой директорией принято считать диск.
//Относительный путь - это путь относительно какой-то директории.
//Обрати внимание, что по умолчанию, и полный, и относительный путь к файлу, включают в себя имя файла.
//
//1. Создай класс менеджер ZipFileManager
//2. Добавь в класс приватную переменную Path zipFile.
//В ней мы будем хранить полный путь к архиву, с которым будем работать.
//3. Добавь конструктор ZipFileManager(Path zipFile).
//Проинициализируй поле класса zipFile.
//4. Объяви публичный метод createZip(Path source) throws Exception, пока с пустой реализацией.
//Path source - это путь к чему-то, что мы будем архивировать.
//5. Создай класс Archiver и добавь в него метод main.
//6. В методе main:
//6.1 Запроси пользователя ввести полный путь архива с клавиатуры.
//Не забудь, что имя тоже входит в состав полного пути.
//6.2 Создай объект класса ZipFileManager, передав в него имя файла архива.
//Разберись, как из String получить Path.
//
//Подсказка: изучи метод get() класса Paths.
//
//6.3 Запроси пользователя ввести путь к файлу, который будем архивировать.
//Не путай это с файлом архива, который мы уже ввели.
//На этот раз нам нужен файл, который мы будем сжимать, а не в котором хранить сжатые данные.
//6.4 Вызови метод createZip у объекта ZipFileManager, передав в него путь для архивации.
//
//Требования:
//•	Создай класс ZipFileManager.
//•	Внутри класса ZipFileManager должно быть создано приватное поле Path zipFile.
//•	Внутри класса ZipFileManager должен быть создан конструктор, который будет инициализировать поле zipFile.
//•	Внутри класса ZipFileManager должен быть создан публичный метод void createZip(Path source) throws Exception.
//•	Создай класс Archiver и добавь в него метод main. Реализуй его согласно заданию.

//2
//Сейчас мы напишем реализацию метода createZip(Path source), в котором мы будем архивировать файл, заданный переменной source.
//В Java есть специальный класс ZipOutputStream из пакета java.util.zip, который сжимает (архивирует) переданные в него данные.
//Чтобы несколько файлов, сжимаемые в один архив, не слиплись вместе, для каждого из них создается специальная сущность - элемент архива ZipEntry.
//Т.е. в ZipOutputStream мы сначала кладем ZipEntry, а затем уже записываем содержимое файла.
//При записи файл автоматически сжимается, а при чтении - автоматически восстанавливается.
//ZipEntry может быть не только файлом, но и папкой.
//
//Чтобы заархивировать файл (создать новый архив и добавить в него файл):
//1. Создай новый поток архива ZipOutputStream используя переменную класса zipFile, с помощью метода newOutputStream класса Files.
//2. Создай новый элемент архива ZipEntry.
//В конструктор ZipEntry передай строку, содержащую имя новой записи.
//Имя нужно получить из полного пути source, взять только имя файла и сконвертировать его в String.
//3. Добавь в поток архива созданный элемент архива.
//4. Перепиши данные из файла, который архивируем в поток архива. Для этого:
//4.1. Создай поток InputStream для добавляемого файла source, используя метод newInputStream класса Files
//4.2. Сделай цикл, который будет читать данные из InputStream (созданного в п.4.1), пока они там есть и записывать их в ZipOutputStream (созданный в п.1)
//4.3. Закрой InputStream, сделай это с помощью try-with-resources
//5. Закрой элемент архива у потока архива
//6. Закрой поток архива, сделай это также с помощью try-with-resources
//7. Запусти программу и проверь, что файл архивируется
//
//Требования:
//•	Метод createZip должен создавать ZipOutputStream используя поле zipFile и метод Files.newOutputStream.
//•	Метод createZip должен создавать элемент архива ZipEntry c именем файла, полученным из параметра source.
//•	Созданный ZipEntry нужно добавить в ZipOutputStream.
//•	Для переменной source должен быть создан InputStream с помощью метода Files.newInputStream.
//•	Данные из InputStream нужно переписать в ZipOutputStream.
//•	Закрой текущий Entry у объекта ZipOutputStream.
//•	InputStream для source должен быть закрыт.
//•	ZipOutputStream должен быть закрыт.

//10
//Пришло время отрефакторить класс ZipFileManager.
//В методе createZip есть код, который нам также понадобится в методах, которые будут добавлять или удалять файл в архив, распаковывать его и т.д.
//Эти методы мы будем реализовывать позже, но уже сейчас можем вынести общие части кода в отдельные методы.
//Кроме того, метод createZip мог создавать архив только из одного файла, а хотелось бы уметь архивировать всю папку целиком.
//Создавать отдельный метод для этого не будем, т.к. в createZip(Path source) можно передавать и директорию и обычный файл.
//
//Задания на сегодня:
//1. Реализуй приватный метод void addNewZipEntry(ZipOutputStream zipOutputStream, Path filePath, Path fileName) throws Exception в классе ZipFileManager.
//
//Он должен:
//1.1. Создавать InputStream, для файла с именем fileName, расположенным в директории filePath
//1.2. Создавать новый элемент архива ZipEntry, в качестве имени используй fileName, преобразовав его в String
//1.3. Копировать данные из InputStream (из п.1.1) в переданный zipOutputStream
//1.4. Закрывать элемент архива
//1.5. Закрывать InputStream, сделай это с помощью try-with-resources
//2. Замени часть кода метода createZip вызовом нового метода addNewZipEntry.
//Передай значение source.getParent() в параметр filePath, а source.getFileName() в filename.
//3. Реализуй приватный метод void copyData(InputStream in, OutputStream out) throws Exception.
//Он должен читать данные из in и записывать в out, пока не вычитает все.
//4. Замени часть кода метода addNewZipEntry на вызов метода copyData
//5. Вернемся к createZip:
//5.1. В начале метода проверь, что существует директория (zipFile.getParent()), в которой мы будем создавать zipFile, если ее нет, то создай ее.
//5.2. Если source является обычным файлом (для проверки используй Files.isRegularFile), то оставим просто вызов addNewZipEntry
//5.3. Если source является директорией (для проверки используй Files.isDirectory), то:
//5.3.1. Создай объект класса файловый менеджер FileManager, в конструктор передадим source
//5.3.2. Получи список файлов у файлового менеджера, сохраним его в переменную fileNames
//5.3.3. Для всех элементов fileNames, вызови метод addNewZipEntry(zipOutputStream, source, fileName)
//5.4. Если source не является ни папкой, ни файлом, то кинь исключение PathIsNotFoundException.
//
//Требования:
//•	В классе ZipFileManager добавь приватный метод void addNewZipEntry(ZipOutputStream zipOutputStream, Path filePath, Path fileName) throws Exception.
//•	Метод addNewZipEntry должен быть реализован следуя описанию в задании.
//•	Замени часть кода метода createZip вызовом нового метода addNewZipEntry.
//•	В классе ZipFileManager добавь приватный метод void copyData(InputStream in, OutputStream out) throws Exception.
//•	Метод copyData должен читать данные из in и записывать в out, пока не вычитает все байты.
//•	Замени часть кода метода addNewZipEntry вызовом нового метода copyData.
//•	Метод createZip в классе ZipFileManager должен быть доработан следуя описанию в задании.

//13
//Продолжим наш путь к получению содержимого файла архива. Напишем метод getFilesList() в классе ZipFileManager.
//Он будет возвращать список файлов в архиве, вернее список свойств этих файлов (класс свойств FileProperties мы уже реализовали).
//Итак:
//1. Добавь метод List<FileProperties> getFilesList() throws Exception в класс ZipFileManager
//2. Внутри метода проверь является ли содержимое zipFile обычным файлом с помощью подходящего метода класса Files. Если это не файл, брось исключение WrongZipFileException().
//3. Создай список с элементами типа FileProperties, в него мы будем складывать свойства файлов
//4. Создай входящий поток ZipInputStream, для файла из переменной zipFile.
//Как и в прошлые разы, оберни его создание в try-with-resources
//5. Пройдись по всем элементам ZipEntry потока ZipInputStream
//6. Для каждого элемента ZipEntry вычитай его содержимое, иначе у нас не будет информации о его размере.
//Нельзя узнать размер файла в архиве, не вычитав его. Это очень легко сделать с помощью функции copyData, используя временный буфер типа ByteArrayOutputStream.
//7. Получи имя, размер, сжатый размер и метод сжатия элемента архива. Посмотри, что еще можно узнать о нем.
//8. Создай объект класса FileProperties, используя полученные данные о файле.
//9. Добавь созданный объект из п.8 в список из п.3
//10. После выхода из цикла верни собранную информацию вызвавшему методу.
//
//Требования:
//•	В классе ZipFileManager нужно создать публичный метод List<FileProperties> getFilesList() throws Exception.
//•	Если Path zipFile не является файлом, метод getFilesList должен кинуть исключение WrongZipFileException.
//•	Метод getFilesList должен пройтись по всем файлам архива и вернуть данные о них. Реализация должна быть выполнена согласно описанию в задании.
//•	Поток для чтения из архива должен быть закрыт.

//Archiver (15)
//Пора попробовать что-нибудь распаковать. Для этого добавим публичный метод
// void extractAll(Path outputFolder) throws Exception в класс ZipFileManager.
//Path outputFolder - это путь, куда мы будем распаковывать наш архив. У тебя уже большой опыт работы с элементами архива и потоками.
// Так что, я дам только подсказки по реализации этого метода, а тебе придется хорошенько подумать, как это все сделать:
//1. Проверь, есть ли zip файл вообще
//2. Если директория outputFolder не существует, то ее нужно создать, как и все папки, внутри которых она лежит.
//3. Внутри архива некоторые файлы могут лежат внутри папок, тогда метод getName() элемента архива ZipEntry,
// вернет не совсем имя, как может показаться из названия, а относительный путь внутри архива.
// Этот относительный путь должен сохраниться и после распаковки, но уже относительно той директории, куда мы распаковали архив
//4. Реализуй метод execute() класса ZipExtractCommand, по аналогии с таким же методом класса ZipCreateCommand,
// сделай такой же блок try-catch, только поменяй сообщения выводимые пользователю, чтобы он понял,
// что сейчас мы будем распаковывать архив, и что нужно ввести полное имя архива и директорию, куда будем распаковывать.
//Не забудь вызвать метод extractAll класса ZipFileManager, а не createZip, как это было в ZipCreateCommand
//5. Запускай программу и наслаждайся результатом распаковки
//
//Примечание:
//Для получения потоков чтения и записи используй Files.newInputStream(Path path), Files.newOutputStream(Path path).
//
//
//Требования:
//1. В классе ZipFileManager нужно создать публичный метод void extractAll(Path outputFolder) throws Exception.
//2. Метод extractAll(Path outputFolder) должен бросать исключение WrongZipFileException, если файл архива не существует.
//3. Метод extractAll(Path outputFolder) должен создавать директорию outputFolder, если она не существует.
//4. Метод extractAll(Path outputFolder) должен распаковывать все файлы из архива в директорию outputFolder.
//5. Метод execute() в классе ZipExtractCommand должен получать объект ZipFileManager и разархивировать файлы в указанную пользователем директорию.

//Archiver (16)
//Пришло время что-нибудь удалить из архива. Архив очень хитрая штука, нельзя вот так просто взять
// и удалить какой-то элемент внутри него.
//Почему? Представь, что мы решили сами придумать свой алгоритм сжатия текста. Посмотрев исходный текст,
// мы видим, что в нем часто встречается фраза "быть программистом круто". Мы можем в месте,
// где второй, третий, N-ый раз встречается наша фраза сделать пометку, что тут была фраза,
// как в строке S начиная с символа номер K и длиной N, а саму фразу удалить.
// Когда мы заменим много повторяющихся фраз, текст заметно сократится, но станет нечитаемым для тех, кто не знаком с нашим алгоритмом сжатия.
//Мы же этот текст сможем восстановить (разархивировать).
//А теперь представь, что нам нужно удалить часть текста, на которую ссылались сжатые фрагменты.
// В такой ситуации, весь наш архив перестанет иметь смысл. Вот почему нельзя просто так удалить часть архива.
//Это очень примерное описание варианта архивации, в реальности все намного сложнее.
//Поэтому, чтобы что-то удалить из архива, нужно создать новый архив, переписать в него все,
// кроме удаляемых файлов, а потом заменить старый архив вновь созданным.
//
//1. Добавь публичный метод для удаления файлов из архива void removeFiles(List<Path> pathList) throws Exception в класс ZipFileManager.
//В pathList будет передаваться список относительных путей на файлы внутри архива.
//Он должен:
//1.1. Бросать исключение WrongZipFileException, если файл архива не существует.
//1.2. Создать временный файл архива в директории по умолчанию с помощью метода createTempFile() класса Files.
//1.3. Пройтись по всем файлам оригинального архива, проверить, есть ли текущий файл в списке на удаление.
//- Если файл есть в списке, вывести сообщение, что мы удалили файл с таким-то именем и перейти к следующему файлу.
//- Если файла нет в списке на удаление, переписать его в новый архив
//1.4. Заменить оригинальный файл архива временным, в который мы записали нужные файлы.
//Это нужно сделать с помощью метода move() класса Files
//2. Добавь публичный метод void removeFile(Path path) throws Exception в класс ZipFileManager,
// который будет вызывать метод removeFiles, создавая список из одного элемента.
//Это можно сделать с помощью метода singletonList() класса Collections. Посмотри, как он работает.
//3. Реализуй метод execute() класса ZipRemoveCommand, создав объект класса ZipFileManager,
// спросив пользователя из какого архива и какой файл будем удалять, и вызвав метод removeFile().
//Все остальное, как и в других командах. Исключение PathIsNotFoundException можно не ловить, т.к. метод removeFile() не должен его кидать.
//4. Запусти программу и проверить, что удаление файла из архива работает.
//
//
//Требования:
//1. В классе ZipFileManager нужно создать публичный метод void removeFiles(List<Path> pathList) throws Exception.
//2. Метод removeFiles должен бросать исключение WrongZipFileException, если файл архива не существует.
//3. Метод removeFiles должен создавать временный файл архива с помощью метода Files.createTempFile.
//4. Метод removeFiles должен записывать в новый архив все файлы из старого архива, кроме тех, которые в списке на удаление.
// Затем, заменить старый архив новым.
//5. В классе ZipFileManager нужно создать публичный метод void removeFile(Path path) throws Exception,
// который будет делегировать свое выполнение методу removeFiles(List<Path> pathList).
//6. Метод execute() в классе ZipRemoveCommand должен получить объект ZipFileManager и удалить из архива файл, считанный с консоли.


//Archiver (17)
//Осталась ерунда. Добавить добавление файла в архив. Звучит подозрительно, но именно этим мы и займемся.
//Добавление файлов похоже на удаление, мы создаем временный файл архив, переписываем в него все содержимое
// старого архива и добавляем новые файлы. Потом заменяем старый файл архива новым.
//1. Добавь публичный метод void addFiles(List<Path> absolutePathList) throws Exception в класс ZipFileManager,
// где absolutePathList - список абсолютных путей добавляемых файлов.
//Этот метод должен:
//1.1. Как обычно, бросать исключение WrongZipFileException, если файл архива не существует
//1.2. Создать временный файл архива
//1.3. Пройти по всем файлам оригинального архива, переписать каждый файл в новый архив, добавить имя переписанного файла в какой-нибудь локальный список.
//1.4. Пройтись по списку добавляемых файлов.
//1.5. Для каждого файла проверить, есть ли он на диске и является ли он обычным файлом, если что-то не так, кинь исключение PathIsNotFoundException()
//1.6. Проверить, есть ли добавляемый файл уже в архиве (используй список из п.1.3). Такое возможно, если пользователь уже когда-то добавлял его.
//- Если файла нет в списке, добавь его в новый архив, выведи сообщение, что такой-то файл добавлен в архив
//- Если файл есть в списке, просто сообщи пользователю, что такой файл уже есть в архиве
//1.7. Заменить оригинальный файл архива временным, в котором уже есть добавленные файлы.
//2. Добавь публичный метод void addFile(Path absolutePath) throws Exception в класс ZipFileManager,
// реализуй его с помощью вызова метода addFiles(), аналогично тому, как мы это делали для удаления файла.
//3. Реализуй метод execute() класса ZipAddCommand: все как обычно, но не забудь спросить у пользователя
// в какой архив и какой файл он хочет добавить, обработай исключение PathIsNotFoundException, которое может кинуть метод addFile().
//4. Запусти программу и проверить, что добавление файла теперь работает.
//
//
//Требования:
//1. В классе ZipFileManager нужно создать публичный метод void addFiles(List<Path> absolutePathList) throws Exception.
//2. Метод addFiles должен бросать исключение WrongZipFileException, если файл архива не существует.
//3. Метод addFiles должен создавать временный файл архива с помощью метода Files.createTempFile.
//4. Метод addFiles должен проходить по списку добавляемых файлов и бросить PathIsNotFoundException, если какой-то файл не существует.
//5. Метод addFiles должен записывать в новый архив новые файлы и все файлы из старого архива. Затем, заменить старый архив новым.
//6. В классе ZipFileManager нужно создать публичный метод void addFile(Path absolutePath) throws Exception,
// который будет делегировать свое выполнение методу addFiles(List<Path> absolutePathList).
//7. Метод execute() в классе ZipAddCommand должен получить объект ZipFileManager и добавить в архив файл, считанный с консоли.

import com.javarush.task.task31.task3110.exception.PathIsNotFoundException;
import com.javarush.task.task31.task3110.exception.WrongZipFileException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipFileManager {
    // Полный путь zip файла
    private final Path zipFile;

    public ZipFileManager(Path zipFile) {
        this.zipFile = zipFile;
    }

    public void createZip(Path source) throws Exception {
        // Проверяем, существует ли директория, где будет создаваться архив
        // При необходимости создаем ее
        Path zipDirectory = zipFile.getParent();
        if (Files.notExists(zipDirectory))
            Files.createDirectories(zipDirectory);

        // Создаем zip поток
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile))) {

            if (Files.isDirectory(source)) {
                // Если архивируем директорию, то нужно получить список файлов в ней
                FileManager fileManager = new FileManager(source);
                List<Path> fileNames = fileManager.getFileList();

                // Добавляем каждый файл в архив
                for (Path fileName : fileNames)
                    addNewZipEntry(zipOutputStream, source, fileName);

            } else if (Files.isRegularFile(source)) {

                // Если архивируем отдельный файл, то нужно получить его директорию и имя
                addNewZipEntry(zipOutputStream, source.getParent(), source.getFileName());
            } else {

                // Если переданный source не директория и не файл, бросаем исключение
                throw new PathIsNotFoundException();
            }
        }
    }

    public List<FileProperties> getFilesList() throws Exception {
        // Проверяем существует ли zip файл
        if (!Files.isRegularFile(zipFile)) {
            throw new WrongZipFileException();
        }

        List<FileProperties> files = new ArrayList<>();

        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();

            while (zipEntry != null) {
                // Поля "размер" и "сжатый размер" не известны, пока элемент не будет прочитан
                // Давайте вычитаем его в какой-то выходной поток
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                copyData(zipInputStream, baos);

                FileProperties file = new FileProperties(zipEntry.getName(), zipEntry.getSize(),
                        zipEntry.getCompressedSize(), zipEntry.getMethod());
                files.add(file);
                zipEntry = zipInputStream.getNextEntry();
            }
        }

        return files;
    }

    private void addNewZipEntry(ZipOutputStream zipOutputStream, Path filePath, Path fileName) throws Exception {
        Path fullPath = filePath.resolve(fileName);
        try (InputStream inputStream = Files.newInputStream(fullPath)) {
            ZipEntry entry = new ZipEntry(fileName.toString());

            zipOutputStream.putNextEntry(entry);

            copyData(inputStream, zipOutputStream);

            zipOutputStream.closeEntry();
        }
    }

    public void extractAll(Path outputFolder) throws Exception {
        if (!Files.isRegularFile(zipFile)) {
            throw new WrongZipFileException();
        }

        if (Files.notExists(outputFolder)) {
            Files.createDirectories(outputFolder);
        }

        try (ZipInputStream inputStream = new ZipInputStream(Files.newInputStream(zipFile))) {
            ZipEntry zipEntry = inputStream.getNextEntry();

            while (zipEntry != null) {

                String fileName = zipEntry.getName();
                Path fileFullName = outputFolder.resolve(fileName);

                Path parent = fileFullName.getParent();

                if (Files.notExists(parent)) {
                    Files.createDirectories(parent);
                }

                if (Files.notExists(fileFullName)) {
                    Files.createFile(fileFullName);
                }

                try (OutputStream outputStream = Files.newOutputStream(fileFullName)) {
                    copyData(inputStream, outputStream);
                }

                zipEntry = inputStream.getNextEntry();
            }
        }
    }

    public void removeFile(Path path) throws  Exception {
        removeFiles(Collections.singletonList(path));
    }
    public void removeFiles(List<Path> pathList) throws Exception {
        if (!Files.isRegularFile(zipFile)) {
            throw new WrongZipFileException();
        }
        Path temp = Files.createTempFile(null, null);

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(temp));
             ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))
        ) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();

            while (zipEntry != null) {
                if (pathList.contains(Paths.get(zipEntry.getName()))) {
                    ConsoleHelper.writeMessage("Удален файл с именем: " + zipEntry.getName());
                } else {
                    zipOutputStream.putNextEntry(zipEntry);
                    copyData(zipInputStream, zipOutputStream);
                }
                zipEntry = zipInputStream.getNextEntry();
            }
        }
        Files.move(temp, zipFile, StandardCopyOption.REPLACE_EXISTING);

    }

    public void addFile(Path absolutePath) throws Exception {
        addFiles(Collections.singletonList(absolutePath));
    }

    public void addFiles(List<Path> absolutePathList) throws Exception {
        if (!Files.isRegularFile(zipFile)) {
            throw new WrongZipFileException();
        }
        Path temp = Files.createTempFile(null, null);
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(temp));
             ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))
        ) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            List<Path> filesInOriginalArchive = new ArrayList<>();

            while (zipEntry != null) {
                filesInOriginalArchive.add(Paths.get(zipEntry.getName()));
                zipOutputStream.putNextEntry(zipEntry);
                copyData(zipInputStream, zipOutputStream);
                zipEntry = zipInputStream.getNextEntry();
            }

            for (Path path : absolutePathList) {
                if (Files.notExists(path) && !Files.isRegularFile(path)) {
                    throw new PathIsNotFoundException();
                }
                if (!filesInOriginalArchive.contains(path.getFileName())) {
                    try (InputStream inputStream = Files.newInputStream(path)) {
                        ZipEntry entry = new ZipEntry(path.getFileName().toString());
                        zipOutputStream.putNextEntry(entry);
                        copyData(inputStream, zipOutputStream);
                    }
                } else {
                    ConsoleHelper.writeMessage("Такой файл уже есть в архиве");
                }
            }
        }

        Files.move(temp, zipFile, StandardCopyOption.REPLACE_EXISTING);
    }

    private void copyData(InputStream in, OutputStream out) throws Exception {
        byte[] buffer = new byte[8 * 1024];
        int len;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
    }
}