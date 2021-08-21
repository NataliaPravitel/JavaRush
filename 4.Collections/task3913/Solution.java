package com.javarush.task.task39.task3913;

import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("D:\\Repository\\JavaProjects\\src\\JavaRushTasks\\4.JavaCollections\\src\\com\\javarush\\task\\task39\\task3913\\logs"));

        System.out.println(logParser.getIPsForStatus(Status.OK,null, null));
        System.out.println(logParser.getIPsForEvent(Event.DONE_TASK,null, null));
        System.out.println(logParser.getIPsForUser("Amigo",null, null));
        System.out.println(logParser.getUniqueIPs(null, null));
        System.out.println(logParser.getNumberOfUniqueIPs(null, null));
    }
}