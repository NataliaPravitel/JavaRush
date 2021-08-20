package com.javarush.task.task37.task3701;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* 
Круговой итератор
Класс Solution наследуется от ArrayList.
Перегрузи правильным образом метод iterator в классе Solution.
Напиши свой класс RoundIterator внутри Solution, который будет итератором для списка Solution.
Итератор должен ходить по кругу по всем элементам.
В остальном поведение должно быть идентичным текущему итератору.


Requirements:
1. Круговой итератор должен после последнего элемента переходить на первый и так далее.
2. Метод remove без параметров должен удалять текущий элемент.
3. При некорректной модификации списка из разных потоков должно возникать исключение ConcurrentModificationException.
4. Класс Solution должен быть потомком класса ArrayList.
5. Метод iterator() в классе Solution должен возвращать объект типа RoundIterator.
*/

public class Solution<T> extends ArrayList<T> {
    public static void main(String[] args) {
        Solution<Integer> list = new Solution<>();
        list.add(1);
        list.add(2);
        list.add(3);

        int count = 0;
        for (Integer i : list) {
            //1 2 3 1 2 3 1 2 3 1
            System.out.print(i + " ");
            count++;
            if (count == 10) {
                break;
            }
        }


    }

    @Override
    public Iterator<T> iterator() {
        return new RoundIterator();
    }

    public class RoundIterator implements Iterator<T> {
        int cursor;
        int lastRet = -1;
        int expectedModCount = modCount;

        public RoundIterator() {
        }

        @Override
        public boolean hasNext() {
            return size() > 0;
        }

        @SuppressWarnings("unchecked")
        public T next() {
            checkForComodification();
            int i = cursor;
            if (i >= size()) {
                i = 0;
            }

            Object[] elementData = null;
            try {
                Field elementDataField = ArrayList.class.getDeclaredField("elementData");
                elementDataField.setAccessible(true);
                elementData = (Object[]) elementDataField.get(Solution.this);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            cursor = i + 1;
            return (T) elementData[lastRet = i];
        }

        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                Solution.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
}
