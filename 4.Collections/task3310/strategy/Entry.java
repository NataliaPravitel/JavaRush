package com.javarush.task.task33.task3310.strategy;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class Entry implements Serializable {
  Long key;
  String value;
  Entry next;
  int hash;

  public Entry(int hash, Long key, String value, Entry next) {
    this.key = key;
    this.value = value;
    this.next = next;
    this.hash = hash;
  }

  public Long getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  public int hashCode() {
    return Objects.hashCode(key) ^ Objects.hashCode(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Entry entry = (Entry) o;
    if (key != null ? !key.equals(entry.key) : entry.key != null) {
      return false;
    }
    return value != null ? value.equals(entry.value) : entry.value == null;
  }

  public String toString() {
    return key + "=" + value;
  }
}
