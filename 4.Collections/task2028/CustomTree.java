package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.*;

/* 
Построй дерево(1)
Амиго, похоже ты уже достаточно окреп. Самое время проверить свои навыки в большой задаче!
Сегодня реализуем свое дерево немного нестандартным способом(на базе AbstractList).
Вводную информацию можешь получить используя свой любимый поисковик и текст ниже.
Элементы дерева должны следовать так как показано на картинке:
Для начала сделаем наше дерево потомком класса AbstractList с параметром типа String,
а также реализуем интерфейсы Cloneable и Serializable.
Реализацию методов get(int index) и size() пока оставь стандартной.

Требования:
1. Класс CustomTree должен поддерживать интерфейс Cloneable.
2. Класс CustomTree должен поддерживать интерфейс Serializable.
3. Класс CustomTree должен быть потомком класса AbstractList<String>.


Построй дерево(2)
Несмотря на то что наше дерево является потомком класса AbstractList, это не список в привычном понимании.
В частности нам недоступны принимающие в качестве параметра индекс элемента.
Такие методы необходимо переопределить и бросить новое исключение типа UnsupportedOperationException.
Вот их список:
public String get(int index)
public String set(int index, String element)
public void add(int index, String element)
public String remove(int index)
public List<String> subList(int fromIndex, int toIndex)
protected void removeRange(int fromIndex, int toIndex)
public boolean addAll(int index, Collection<? extends String> c)

Требования:
1. При попытке вызвать метод get(int index) должно возникать исключение типа
UnsupportedOperationException.
2. При попытке вызвать метод set(int index, String element) должно возникать исключение типа
UnsupportedOperationException.
3. При попытке вызвать метод add(int index, String element) должно возникать исключение типа
UnsupportedOperationException.
4. При попытке вызвать метод String remove(int index) должно возникать исключение типа
UnsupportedOperationException.
5. При попытке вызвать метод subList(int fromIndex, int toIndex) должно возникать исключение типа
UnsupportedOperationException.
6. При попытке вызвать метод removeRange(int fromIndex, int toIndex) должно возникать исключение
типа UnsupportedOperationException.
7. При попытке вызвать метод addAll(int index, Collection<? extends String> c) должно возникать
исключение типа UnsupportedOperationException.


Построй дерево(3)
Класс описывающий дерево мы создали, теперь нужен класс описывающий тип элементов дерева:
1.  В классе CustomTree создай вложенный статический параметризированный класс Entry<T>
с модификатором доступа по умолчанию.
2. Обеспечь поддержку этим классом интерфейса Serializable.
3. Создай такие поля (модификатор доступа по умолчанию):
- String elementName;
- boolean availableToAddLeftChildren, availableToAddRightChildren;
- Entry<T> parent, leftChild, rightChild;
- при необходимости, можешь создавать и другие поля;
4. Реализуй публичный конструктор с одним параметром типа String:
- установи поле elementName равным полученному параметру;
- установи поле availableToAddLeftChildren равным true;
- установи поле availableToAddRightChildren равным true;
5. Реализуй публичный метод boolean isAvailableToAddChildren, возвращающий дизъюнкцию полей
availableToAddLeftChildren и availableToAddRightChildren.

Требования:
1. Класс CustomTree.Entry должен быть объявлен с модификатором доступа по умолчанию.
2. Класс CustomTree.Entry должен поддерживать интерфейс Serializable.
3. В классе CustomTree.Entry должно существовать поле elementName типа String.
4. В классе CustomTree.Entry должно существовать поле availableToAddLeftChildren типа boolean.
5. В классе CustomTree.Entry должно существовать поле availableToAddRightChildren типа boolean.
6. В классе CustomTree.Entry должно существовать поле parent типа Entry.
7. В классе CustomTree.Entry должно существовать поле leftChild типа Entry.
8. В классе CustomTree.Entry должно существовать поле rightChild типа Entry.
9. В классе CustomTree.Entry должен быть корректно реализован конструктор с одним параметром
типа String (смотри условие).
10. В классе CustomTree.Entry должен корректно реализован метод isAvailableToAddChildren
(смотри условие).


Построй дерево(4)
Любое дерево начинается с корня, поэтому не забудь в класс CustomTree добавить
поле root типа Entry<String> c модификатором доступа по умолчанию.
Инициируй его в конструкторе CustomTree, имя (elementName) не важно.
Итак, основа дерева создана, пора тебе поработать немного самому.
Вспомним как должно выглядеть наше дерево.
Элементы дерева должны следовать так как показано на картинке:
Необходимо написать методы, которые бы позволили создать такую структуру дерева
и проводить операции над ней.

Тебе необходимо:
1) переопределить метод add(String s) - добавляет элементы дерева,
в качестве параметра принимает имя элемента (elementName),
искать место для вставки начинаем слева направо.
2) переопределить метод size() - возвращает текущее количество элементов в дереве.
3) реализовать метод getParent(String s) - возвращает имя родителя элемента дерева,
имя которого было полученного в качестве параметра.

Если необходимо, можешь вводить дополнительные методы и поля, не указанные в задании.

Требования:
1. В классе CustomTree должно существовать поле root типа Entry.
2. В классе CustomTree должны быть переопределены методы add(String s) и size().
3. После добавления N элементов в дерево с помощью метода add, метод size должен возвращать N.
4. Метод getParent должен возвращать имя родителя для любого элемента дерева.
*/

public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {
  Entry<String> root;
  private List<Entry<String>> tree = new LinkedList<>();
  private int countElements;

  public CustomTree() {
    this.root = new Entry<>(null);
    tree.add(root);
  }

  @Override
  public String get(int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return countElements;
  }

  @Override
  public boolean add(String s) {
    Queue<Entry<String>> tempTree = new LinkedList<>();
    tempTree.add(root);

    do{
      Entry<String> topElementInBranch = tempTree.poll();

      if (topElementInBranch.availableToAddLeftChildren) {
        Entry<String> leftChild = new Entry<>(s);
        topElementInBranch.leftChild = leftChild;
        leftChild.parent = topElementInBranch;
        topElementInBranch.availableToAddLeftChildren = false;
        countElements++;
        return tree.add(leftChild);

      } else if (topElementInBranch.availableToAddRightChildren) {
        Entry<String> rightChild = new Entry<>(s);
        topElementInBranch.rightChild = rightChild;
        rightChild.parent = topElementInBranch;
        topElementInBranch.availableToAddRightChildren = false;
        countElements++;
        return tree.add(rightChild);

      } else if (!topElementInBranch.isAvailableToAddChildren()) {
        tempTree.add(topElementInBranch.leftChild);
        tempTree.add(topElementInBranch.rightChild);
      }
    } while (!tempTree.isEmpty());
    return false;
  }

  public String getParent(String s) {
    String parent = null;
    for (Entry<String> element : tree) {
      if (s.equals(element.elementName) && element.parent != null) {
        parent = element.parent.elementName;
      }
    }
    return parent;
  }

  @Override
  public String set(int index, String element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(int index, String element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String remove(int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<String> subList(int fromIndex, int toIndex) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected void removeRange(int fromIndex, int toIndex) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(int index, Collection<? extends String> c) {
    throw new UnsupportedOperationException();
  }

  static class Entry<T> implements Serializable {
    String elementName;
    boolean availableToAddLeftChildren;
    boolean availableToAddRightChildren;
    Entry<T> parent;
    Entry<T> leftChild;
    Entry<T> rightChild;

    public Entry(String elementName) {
      this.elementName = elementName;
      this.availableToAddLeftChildren = true;
      this.availableToAddRightChildren = true;
    }

    public boolean isAvailableToAddChildren() {
      return availableToAddLeftChildren || availableToAddRightChildren;
    }
  }
}
