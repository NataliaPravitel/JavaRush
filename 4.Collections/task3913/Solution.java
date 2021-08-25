package com.javarush.task.task39.task3913;

import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("D:\\Repository\\JavaProjects\\src\\JavaRushTasks\\4.JavaCollections\\src\\com\\javarush\\task\\task39\\task3913\\logs"));

        System.out.println("Number of unique ips: " + logParser.getNumberOfUniqueIPs(null, null));
        System.out.println("Unique IPs: " + logParser.getUniqueIPs(null, null));
        System.out.println("IPs for users: " + logParser.getIPsForUser("Amigo",null, null));
        System.out.println("IPs for event: " + logParser.getIPsForEvent(Event.DONE_TASK,null, null));
        System.out.println("IPs for status: " + logParser.getIPsForStatus(Status.OK,null, null));

        System.out.println("=====================================================================");

        System.out.println("All users: " + logParser.getAllUsers());
        System.out.println("Number of all users: " + logParser.getNumberOfUsers(null, null));
        System.out.println("Number of user events: " + logParser.getNumberOfUserEvents("Amigo", null, null));
        System.out.println("Users for IP: " + logParser.getUsersForIP("127.0.0.1", null, null));
        System.out.println("Logged users: " + logParser.getLoggedUsers(null, null));
        System.out.println("Downloaded plugin users: " + logParser.getDownloadedPluginUsers(null, null));
        System.out.println("Wrote message users: " + logParser.getWroteMessageUsers(null, null));
        System.out.println("Solved task users: " + logParser.getSolvedTaskUsers(null, null));
        System.out.println("Solved the specific task users: " + logParser.getSolvedTaskUsers(null,null, 1));
        System.out.println("Done task users: " + logParser.getDoneTaskUsers(null, null));
        System.out.println("Done the specific task users: " + logParser.getDoneTaskUsers(null, null, 48));

        System.out.println("=====================================================================");

        System.out.println("Dates for user and event: " + logParser.getDatesForUserAndEvent("Amigo", Event.SOLVE_TASK, null, null));
        System.out.println("Dates when something failed: " + logParser.getDatesWhenSomethingFailed(null, null));
        System.out.println("Dates when error happened: " + logParser.getDatesWhenErrorHappened(null, null));
        System.out.println("Date when user logged first time: " + logParser.getDateWhenUserLoggedFirstTime("Amigo", null, null));
        System.out.println("Date when user solved task: " + logParser.getDateWhenUserSolvedTask("Amigo", 18, null, null));
        System.out.println("Date when user done task: " + logParser.getDateWhenUserDoneTask("Eduard Petrovich Morozko", 48, null, null));
        System.out.println("Dates when user wrote message: " + logParser.getDatesWhenUserWroteMessage("Eduard Petrovich Morozko", null, null));
        System.out.println("Dates when user downloaded plugin: " + logParser.getDatesWhenUserDownloadedPlugin("Eduard Petrovich Morozko", null, null));

        System.out.println("=====================================================================");

        System.out.println("Number of all events: " + logParser.getNumberOfAllEvents(null, null));
        System.out.println("All events: " + logParser.getAllEvents(null, null));
        System.out.println("Events for IP: " + logParser.getEventsForIP("127.0.0.1", null, null));
        System.out.println("Events for user: " + logParser.getEventsForUser("Amigo", null, null));
        System.out.println("Falied events: " + logParser.getFailedEvents(null, null));
        System.out.println("Error events: " + logParser.getErrorEvents(null, null));
        System.out.println("Number of attempt to solve task: " + logParser.getNumberOfAttemptToSolveTask(18,null, null));
        System.out.println("Number of successful to solve task: " + logParser.getNumberOfSuccessfulAttemptToSolveTask(48,null, null));
        System.out.println("AllSolvedTasksAndTheirNumber: " + logParser.getAllSolvedTasksAndTheirNumber(null, null));
        System.out.println("AllDoneTasksAndTheirNumber: " + logParser.getAllDoneTasksAndTheirNumber(null, null));

        System.out.println("=====================================================================");

        System.out.println("get ip " + logParser.execute("get ip"));
        System.out.println("get user " + logParser.execute("get user"));
        System.out.println("get date " + logParser.execute("get date"));
        System.out.println("get event " + logParser.execute("get event"));
        System.out.println("get status " + logParser.execute("get status"));

        System.out.println("get ip for user = \"Amigo\" " + logParser.execute("get ip for user = \"Amigo\""));
        System.out.println("get event for date = \"30.01.2014 12:56:22\" " + logParser.execute("get event for date = \"30.01.2014 12:56:22\""));
        System.out.println("get ip for user = \"Eduard Petrovich Morozko\"" + logParser.execute("get ip for user = \"Eduard Petrovich Morozko\""));
        System.out.println("get ip for user = \"Eduard Petrovich Morozko\" and date between \"11.12.2013 0:00:00\" and \"11.12.2013 23:59:59\"" + logParser.execute("get ip for user = \"Eduard Petrovich Morozko\" and date between \"11.12.2013 0:00:00\" and \"11.12.2013 23:59:59\""));
        System.out.println("get event for status = \"Eduard Petrovich Morozko\" and date between \"11.12.2013 0:00:00\" and \"11.12.2013 23:59:59\"" + logParser.execute("get event for user = \"Eduard Petrovich Morozko\" and date between \"11.12.2013 0:00:00\" and \"11.12.2013 23:59:59\""));
    }
}