package com.javarush.task.task33.task3310.strategy;

import java.util.HashMap;

public class OurHashBiMapStorageStrategy implements StorageStrategy {
  private HashMap<Long, String> k2v = new HashMap<>();
  private HashMap<String, Long> v2k = new HashMap<>();
//Метод containsKey должен проверять содержится ли полученный параметр в k2v.
  @Override
  public boolean containsKey(Long key) {
    return k2v.containsKey(key);
  }
//Метод containsValue должен проверять содержится ли полученный параметр в v2k.
  @Override
  public boolean containsValue(String value) {
    return v2k.containsKey(value);
  }
//Метод put должен добавлять пару (key, value) в k2v и пару (value, key) в v2k.
  @Override
  public void put(Long key, String value) {
    k2v.put(key, value);
    v2k.put(value, key);
  }
//Метод getKey должен возвращать значение полученное из v2k.
  @Override
  public Long getKey(String value) {
    return v2k.get(value);
  }
//Метод getValue должен возвращать значение полученное из k2v.
  @Override
  public String getValue(Long key) {
    return k2v.get(key);
  }
}
