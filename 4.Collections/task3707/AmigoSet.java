package com.javarush.task.task37.task3707;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class AmigoSet<E> extends AbstractSet<E> implements Set<E>, Serializable, Cloneable {
  private static final Object PRESENT = new Object();
  private transient HashMap<E,Object> map;

  public AmigoSet() {
    this.map = new HashMap<>();
  }

  public AmigoSet(Collection<? extends E> collection) {
    this.map = new HashMap<>(Math.max((int) (collection.size() / .75f) + 1, 16));
    addAll(collection);
  }

  @Override
  public Iterator<E> iterator() {
    return map.keySet().iterator();
  }

  @Override
  public int size() {
    return map.size();
  }

  @Override
  public boolean add(E e) {
    return map.put(e, PRESENT) == null;
  }

  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return map.containsKey(o);
  }

  @Override
  public boolean remove(Object o) {
    return map.remove(o) == PRESENT;
  }

  @Override
  public void clear() {
    map.clear();
  }
  
  @Override
  public Object clone() {
    try {
      AmigoSet<E> newSet = (AmigoSet<E>) super.clone();// клонируем класс
      newSet.map = (HashMap<E, Object>) map.clone();// клонируем поле map
      return newSet;
    } catch (Exception e) {
      throw new InternalError();
    }
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();
    out.writeInt(HashMapReflectionHelper.<Integer>callHiddenMethod(map, "capacity"));
    out.writeFloat(HashMapReflectionHelper.<Float>callHiddenMethod(map, "loadFactor"));
    out.writeInt(map.size());
    for (E e : map.keySet())
      out.writeObject(e);
  }

  private void readObject (ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    int capacity = in.readInt();
    float loadFactor = in.readFloat();
    map = new HashMap<>(capacity, loadFactor);
    int size = in.readInt();
    for (int i = 0; i < size; i++) {
      E e = (E) in.readObject();
      map.put(e, PRESENT);
    }
  }
}
