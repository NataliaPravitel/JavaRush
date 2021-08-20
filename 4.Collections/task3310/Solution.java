package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {
  public static void main(String[] args) {
    long elementsNumber = 10000;
    testStrategy(new HashMapStorageStrategy(), elementsNumber);
    testStrategy(new OurHashMapStorageStrategy(), elementsNumber);
    testStrategy(new FileStorageStrategy(), 100);
    testStrategy(new OurHashBiMapStorageStrategy(), elementsNumber);
    testStrategy(new HashBiMapStorageStrategy(), elementsNumber);
    testStrategy(new DualHashBidiMapStorageStrategy(), elementsNumber);
  }

  public static void testStrategy(StorageStrategy strategy, long elementsNumber) {
    Helper.printMessage(strategy.getClass().getSimpleName());
    Set<String> generateStrings = new HashSet<>();
    for (int i = 0; i < elementsNumber; i++) {
      generateStrings.add(Helper.generateRandomString());
    }
    Shortener shortener = new Shortener(strategy);

    Date startTimeGetIds = new Date();
    Set<Long> keys = getIds(shortener, generateStrings);
    Date finishTimeGetIds = new Date();
    Helper.printMessage("Время получения идентификаторов для " + elementsNumber + " строк: "
            + (finishTimeGetIds.getTime() - startTimeGetIds.getTime()));

    Date startTimeGetStrings = new Date();
    Set<String> strings = getStrings(shortener, keys);
    Date finishTimeGetStrings = new Date();
    Helper.printMessage("Время получения строк для " + elementsNumber + " идентификаторов: "
            + (finishTimeGetStrings.getTime() - startTimeGetStrings.getTime()));

    if (generateStrings.equals(strings)) {
      Helper.printMessage("Тест пройден.");
    } else {
      Helper.printMessage("Тест не пройден.");
    }
    Helper.printMessage("");
  }

  public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
    Set<Long> keys = new HashSet<>();
    strings.forEach(s -> keys.add(shortener.getId(s)));
    return keys;
  }

  public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
    Set<String> strings = new HashSet<>();
    keys.forEach(aLong -> strings.add(shortener.getString(aLong)));
    return strings;
  }
}
