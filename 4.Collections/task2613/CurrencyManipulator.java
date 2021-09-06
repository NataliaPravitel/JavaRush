package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CurrencyManipulator {
  //код валюты, например, USD. Состоит из трех букв.
  private String currencyCode;
  //это Map<номинал, количество>
  private Map<Integer, Integer> denominations = new HashMap<>();

  public CurrencyManipulator(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void addAmount(int denomination, int count) {
    if (denominations.containsKey(denomination)) {
      count += denominations.get(denomination);
    }
    denominations.put(denomination, count);
  }

  public int getTotalAmount() {
    AtomicInteger totalAmount = new AtomicInteger();
    denominations.forEach((integer, integer2) -> totalAmount.addAndGet(integer * integer2));
    return totalAmount.get();
  }

  public boolean hasMoney() {
    return !denominations.isEmpty() && getTotalAmount() != 0;
  }

  public boolean isAmountAvailable(int expectedAmount) {
    return getTotalAmount() >= expectedAmount;
  }

  public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
    Map<Integer, Integer> copyDenominations = new TreeMap<>(Comparator.reverseOrder());
    copyDenominations.putAll(denominations);
    for (Map.Entry<Integer, Integer> entry : copyDenominations.entrySet()) {

    }

    TreeMap<Integer, Integer> resultMap = new TreeMap<>(Comparator.reverseOrder());

    for (Integer denomination : copyDenominations.keySet()) {
      int value = copyDenominations.get(denomination);
      while (true) {
        if (expectedAmount < denomination || value == 0) {
          copyDenominations.put(denomination, value);
          break;
        }
        expectedAmount -= denomination;
        value--;

        if (resultMap.containsKey(denomination))
          resultMap.put(denomination, resultMap.get(denomination) + 1);
        else
          resultMap.put(denomination, 1);
      }
    }

    if (expectedAmount > 0) {
      throw new NotEnoughMoneyException();
    } else {
      this.denominations.clear();
      this.denominations.putAll(copyDenominations);
    }
    return resultMap;
  }
}
