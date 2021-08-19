package com.javarush.task.task36.task3606;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* 
Осваиваем ClassLoader и ReflectionАргументом для класса Solution является абсолютный путь к пакету.
Имя пакета может содержать File.separator.
В этом пакете кроме скомпилированных классов (.class) могут находиться и другие файлы (например: .java).
Известно, что каждый класс имеет конструктор без параметров и реализует интерфейс HiddenClass.
Считай все классы с файловой системы, создай фабрику - реализуй метод getHiddenClassObjectByKey.
Примечание: в пакете может быть только один класс, простое имя которого начинается с String key без учета регистра.


Requirements:
1. Реализуй метод scanFileSystem, он должен добавлять в поле hiddenClasses найденные классы.
2. Реализуй метод getHiddenClassObjectByKey, он должен создавать объект класса согласно условию задачи.
3. Метод main не изменяй.
4. Метод getHiddenClassObjectByKey не должен кидать исключений.
*/

public class Solution {
    private List<Class> hiddenClasses = new ArrayList<>();
    private String packageName;

    public Solution(String packageName) {
        this.packageName = packageName;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Solution solution = new Solution(Solution.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath()
                + "com/javarush/task/task36/task3606/data/second");
        solution.scanFileSystem();
        System.out.println(solution.getHiddenClassObjectByKey("secondhiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("firsthiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("packa"));
    }

    public void scanFileSystem() throws ClassNotFoundException {
        File directory = new File(packageName);
        ClassLoader classLoader = Solution.class.getClassLoader();
        for (String fileName : directory.list()) {
            if (fileName.endsWith(".class")) {
                String className = packageName.replaceAll("[/\\\\]", ".")
                        .substring(packageName.lastIndexOf("com/")) + "."
                        + fileName.substring(0, fileName.length() - 6);
                hiddenClasses.add(classLoader.loadClass(className));
            }
        }
    }

    public HiddenClass getHiddenClassObjectByKey(String key) {
        try {
            for (Class aClass : hiddenClasses) {
                if (aClass.getSimpleName().toLowerCase().startsWith(key.toLowerCase())) {
                    Constructor<?> declaredConstructor = aClass.getDeclaredConstructor();
                    declaredConstructor.setAccessible(true);
                    return (HiddenClass) declaredConstructor.newInstance();
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}

