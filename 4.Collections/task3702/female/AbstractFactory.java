package com.javarush.task.task37.task3702.female;

import com.javarush.task.task37.task3702.Human;

public interface AbstractFactory {
  public Human getPerson(int age);
}
