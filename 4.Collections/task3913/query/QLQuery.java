package com.javarush.task.task39.task3913.query;

import com.javarush.task.task39.task3913.Event;
import com.javarush.task.task39.task3913.Status;

import java.util.Date;
import java.util.Set;

public interface QLQuery {
    Set<Object> execute(String query);
}