package com.javarush.task.task36.task3602;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* 
Найти класс по описанию Ӏ Java Collections: 6 уровень, 6 лекция
Описание класса:
1. Реализует интерфейс List;
//2. Является приватным статическим классом внутри популярного утилитного класса;
3. Доступ по индексу запрещен - кидается исключение IndexOutOfBoundsException.
Используя рефлекшн (метод getDeclaredClasses), верни подходящий тип в методе getExpectedClass.


Requirements:
1. Метод getExpectedClass должен использовать метод getDeclaredClasses подходящего утилитного класса.
2. Метод getExpectedClass должен вернуть правильный тип.
3. Метод main должен вызывать метод getExpectedClass.
4. Метод main должен вывести полученный класс на экран.
*/

public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
        for (Class clazz : Collections.class.getDeclaredClasses()) {
            if (List.class.isAssignableFrom(clazz)
                    && Modifier.isPrivate(clazz.getModifiers())
                    && Modifier.isStatic(clazz.getModifiers())) {

                List<Constructor<?>> constructors = Arrays.asList(clazz.getDeclaredConstructors());

                try {
                    Constructor constructorWithoutParameters;
                    for (Constructor constructor : constructors) {
                        if (constructor.getParameterCount() == 0) {
                            constructorWithoutParameters = constructor;
                            constructorWithoutParameters.setAccessible(true);
                            Method methodGet = clazz.getDeclaredMethod("get", int.class);
                            methodGet.setAccessible(true);
                            methodGet.invoke(constructorWithoutParameters.newInstance(), 7);
                        }
                    }

                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    if (e.getCause().toString().contains("IndexOutOfBoundsException")) {
                        return clazz;
                    }
                }
            }
        }
        return null;
    }
}
