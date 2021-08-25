package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
  private Path logDir;
  private List<EventRecord> eventRecords = new ArrayList<>();

  public LogParser(Path logDir) {
    this.logDir = logDir;
    parseEvent();
  }

  @Override
  public Set<Object> execute(String query) {
    Pattern pattern = Pattern.compile("get (ip|user|date|event|status)"
            + "( for (ip|user|date|event|status) = \"(.*?)\")?"
            + "( and date between \"(.*?)\" and \"(.*?)\")?");
    Matcher matcher = pattern.matcher(query);
    matcher.find();
    String field1 = matcher.group(1);
    String field2 = null;
    String value1 = null;
    Date after = null;
    Date before = null;

    if (matcher.group(2) != null) {
      field2 = matcher.group(3);
      value1 = matcher.group(4);
      if (matcher.group(5) != null) {
        after = readDate(matcher.group(6));
        before = readDate(matcher.group(7));
      }
    }

    Set<Object> objects;
    Stream<EventRecord> filterRecordStream;
    if (field2 != null && value1 != null) {
      filterRecordStream = getRecordStreamFilterForValue(field2, value1);
      if (after != null && before != null) {
        Date finalAfter = after;
        Date finalBefore = before;
        filterRecordStream = filterRecordStream.filter(eventRecord ->
                isDateBetweenDates(eventRecord.dateLogEvent, finalAfter, finalBefore));
      }
      objects = getValuesForField(field1, filterRecordStream);
    } else {
      objects = getValuesForField(field1, null);
    }
    return objects;
  }

  private Set<Object> getValuesForField(String field1, Stream<EventRecord> filterRecordStream) {
    Set<Object> objects = new HashSet<>();
    filterRecordStream = filterRecordStream != null ? filterRecordStream : eventRecords.stream();
    switch (field1) {
      case "ip":
        objects = filterRecordStream.map(eventRecord -> eventRecord.ip).collect(Collectors.toSet());
        break;
      case "user":
        objects = filterRecordStream.map(eventRecord -> eventRecord.userName)
                .collect(Collectors.toSet());
        break;
      case "date":
        objects = filterRecordStream.map(eventRecord -> eventRecord.dateLogEvent)
                .collect(Collectors.toSet());
        break;
      case "event":
        objects = filterRecordStream.map(eventRecord -> eventRecord.event)
                .collect(Collectors.toSet());
        break;
      case "status":
        objects = filterRecordStream.map(eventRecord -> eventRecord.status)
                .collect(Collectors.toSet());
        break;
    }
    return objects;
  }

  private Stream<EventRecord> getRecordStreamFilterForValue(String field2, String value1) {
    Stream<EventRecord> recordStream = null;
    switch (field2) {
      case "ip":
        recordStream = filterLogsByParams(null, null, value1, null,
                null, 0, null);
        break;
      case "user":
        recordStream = filterLogsByParams(null, null, null, value1,
                null, 0, null);
        break;
      case "date":
        recordStream = eventRecords.stream().filter(eventRecord ->
                eventRecord.dateLogEvent.getTime() == readDate(value1).getTime());
        break;
      case "event":
        recordStream = filterLogsByParams(null, null, null, null,
                readEvent(value1), 0, null);
        break;
      case "status":
        recordStream = filterLogsByParams(null, null, null, null,
                null, 0, readStatus(value1));
        break;
    }
    return recordStream;
  }

  @Override
  public int getNumberOfUniqueIPs(Date after, Date before) {
    return getUniqueIPs(after, before).size();
  }

  @Override
  public Set<String> getUniqueIPs(Date after, Date before) {
    return filterLogsByParams(after, before, null, null, null, 0, null)
            .map(eventRecord -> eventRecord.ip).collect(Collectors.toSet());
  }

  @Override
  public Set<String> getIPsForUser(String user, Date after, Date before) {
    return filterLogsByParams(after, before, null, user, null, 0, null)
            .map(eventRecord -> eventRecord.ip).collect(Collectors.toSet());
  }

  @Override
  public Set<String> getIPsForEvent(Event event, Date after, Date before) {
    return filterLogsByParams(after, before, null, null, event, 0, null)
            .map(eventRecord -> eventRecord.ip).collect(Collectors.toSet());
  }

  @Override
  public Set<String> getIPsForStatus(Status status, Date after, Date before) {
    return filterLogsByParams(after, before, null, null, null, 0, status)
            .map(eventRecord -> eventRecord.ip).collect(Collectors.toSet());
  }

  @Override
  public Set<String> getAllUsers() {
    return eventRecords.stream().map(eventRecord -> eventRecord.userName)
            .collect(Collectors.toSet());
  }

  @Override
  public int getNumberOfUsers(Date after, Date before) {
    return filterLogsByParams(after, before, null, null, null, 0, null)
            .map(eventRecord -> eventRecord.userName).collect(Collectors.toSet()).size();
  }

  @Override
  public int getNumberOfUserEvents(String user, Date after, Date before) {
    return filterLogsByParams(after, before, null, user, null, 0, null)
            .map(eventRecord -> eventRecord.event).collect(Collectors.toSet()).size();
  }

  @Override
  public Set<String> getUsersForIP(String ip, Date after, Date before) {
    return filterLogsByParams(after, before, ip, null, null, 0, null)
            .map(eventRecord -> eventRecord.userName).collect(Collectors.toSet());
  }

  @Override
  public Set<String> getLoggedUsers(Date after, Date before) {
    return filterLogsByParams(after, before, null, null, Event.LOGIN, 0, null)
            .map(eventRecord -> eventRecord.userName).collect(Collectors.toSet());
  }

  @Override
  public Set<String> getDownloadedPluginUsers(Date after, Date before) {
    return filterLogsByParams(after, before, null, null,
            Event.DOWNLOAD_PLUGIN, 0, null)
            .map(eventRecord -> eventRecord.userName).collect(Collectors.toSet());
  }

  @Override
  public Set<String> getWroteMessageUsers(Date after, Date before) {
    return filterLogsByParams(after, before, null, null,
            Event.WRITE_MESSAGE, 0, null)
            .map(eventRecord -> eventRecord.userName).collect(Collectors.toSet());
  }

  @Override
  public Set<String> getSolvedTaskUsers(Date after, Date before) {
    return filterLogsByParams(after, before, null, null,
            Event.SOLVE_TASK, 0, null)
            .map(eventRecord -> eventRecord.userName).collect(Collectors.toSet());
  }

  @Override
  public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
    return filterLogsByParams(after, before, null, null,
            Event.SOLVE_TASK, task, null)
            .map(eventRecord -> eventRecord.userName).collect(Collectors.toSet());
  }

  @Override
  public Set<String> getDoneTaskUsers(Date after, Date before) {
    return filterLogsByParams(after, before, null, null,
            Event.DONE_TASK, 0, null)
            .map(eventRecord -> eventRecord.userName).collect(Collectors.toSet());
  }

  @Override
  public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
    return filterLogsByParams(after, before, null, null,
            Event.DONE_TASK, task, null)
            .map(eventRecord -> eventRecord.userName).collect(Collectors.toSet());
  }

  @Override
  public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
    return filterLogsByParams(after, before, null, user, event, 0, null)
            .map(eventRecord -> eventRecord.dateLogEvent).collect(Collectors.toSet());
  }

  @Override
  public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
    return filterLogsByParams(after, before, null, null,
            null, 0, Status.FAILED)
            .map(eventRecord -> eventRecord.dateLogEvent).collect(Collectors.toSet());
  }

  @Override
  public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
    return filterLogsByParams(after, before, null, null, null, 0, Status.ERROR)
            .map(eventRecord -> eventRecord.dateLogEvent).collect(Collectors.toSet());
  }

  @Override
  public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
    List<Date> dates = filterLogsByParams(after, before, null, user,
            Event.LOGIN, 0, null)
            .map(eventRecord -> eventRecord.dateLogEvent).sorted().collect(Collectors.toList());
    return dates.isEmpty() ? null : dates.get(0);
  }

  @Override
  public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
    List<Date> dates = filterLogsByParams(after, before, null, user,
            Event.SOLVE_TASK, task, null)
            .map(eventRecord -> eventRecord.dateLogEvent).sorted().collect(Collectors.toList());
    return dates.isEmpty() ? null : dates.get(0);
  }

  @Override
  public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
    List<Date> dates = filterLogsByParams(after, before, null, user,
            Event.DONE_TASK, task, null)
            .map(eventRecord -> eventRecord.dateLogEvent).sorted().collect(Collectors.toList());
    return dates.isEmpty() ? null : dates.get(0);
  }

  @Override
  public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
    return filterLogsByParams(after, before, null, user,
            Event.WRITE_MESSAGE, 0, null)
            .map(eventRecord -> eventRecord.dateLogEvent).collect(Collectors.toSet());
  }

  @Override
  public int getNumberOfAllEvents(Date after, Date before) {
    return getAllEvents(after, before).size();
  }

  @Override
  public Set<Event> getAllEvents(Date after, Date before) {
    return filterLogsByParams(after, before, null, null, null, 0, null)
            .map(eventRecord -> eventRecord.event).collect(Collectors.toSet());
  }

  @Override
  public Set<Event> getEventsForIP(String ip, Date after, Date before) {
    return filterLogsByParams(after, before, ip, null, null, 0, null)
            .map(eventRecord -> eventRecord.event).collect(Collectors.toSet());
  }

  @Override
  public Set<Event> getEventsForUser(String user, Date after, Date before) {
    return filterLogsByParams(after, before, null, user, null, 0, null)
            .map(eventRecord -> eventRecord.event).collect(Collectors.toSet());
  }

  @Override
  public Set<Event> getFailedEvents(Date after, Date before) {
    return filterLogsByParams(after, before, null, null,
            null, 0, Status.FAILED)
            .map(eventRecord -> eventRecord.event).collect(Collectors.toSet());
  }

  @Override
  public Set<Event> getErrorEvents(Date after, Date before) {
    return filterLogsByParams(after, before, null, null, null, 0, Status.ERROR)
            .map(eventRecord -> eventRecord.event).collect(Collectors.toSet());
  }

  @Override
  public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
    return filterLogsByParams(after, before, null, null,
            Event.SOLVE_TASK, task, null)
            .map(eventRecord -> eventRecord.event).collect(Collectors.toList()).size();
  }

  @Override
  public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
    return filterLogsByParams(after, before, null, null,
            Event.DONE_TASK, task, null)
            .map(eventRecord -> eventRecord.event).collect(Collectors.toList()).size();
  }

  @Override
  public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
    List<EventRecord> events = filterLogsByParams(after, before, null, null,
            Event.SOLVE_TASK, 0, null).collect(Collectors.toList());
    Map<Integer, Integer> taskAndTheirNumber = new HashMap<>();
    events.forEach(eventRecord -> taskAndTheirNumber.put(eventRecord.task,
            getNumberOfAttemptToSolveTask(eventRecord.task, after, before)));
    return taskAndTheirNumber;
  }

  @Override
  public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
    List<EventRecord> events = filterLogsByParams(after, before, null, null,
            Event.DONE_TASK, 0, null).collect(Collectors.toList());
    Map<Integer, Integer> taskAndTheirNumber = new HashMap<>();
    events.forEach(eventRecord -> taskAndTheirNumber.put(eventRecord.task,
            getNumberOfSuccessfulAttemptToSolveTask(eventRecord.task, after, before)));
    return taskAndTheirNumber;
  }

  @Override
  public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
    return filterLogsByParams(after, before, null, user,
            Event.DOWNLOAD_PLUGIN, 0, null)
            .map(eventRecord -> eventRecord.dateLogEvent).collect(Collectors.toSet());
  }

  private Stream<EventRecord> filterLogsByParams(Date after, Date before, String ip, String user,
                                                 Event event, int task, Status status) {
    Stream<EventRecord> recordStream = eventRecords.stream()
            .filter(eventRecord -> isDateBetweenDates(eventRecord.dateLogEvent, after, before));
    if (ip != null) {
      recordStream = recordStream.filter(eventRecord -> eventRecord.ip.equals(ip));
    } else if (user != null) {
      recordStream = recordStream
              .filter(eventRecord -> eventRecord.userName.equalsIgnoreCase(user));
      if (event != null) {
        recordStream = recordStream.filter(eventRecord -> eventRecord.event.equals(event));
        if (task != 0) {
          recordStream = recordStream.filter(eventRecord -> eventRecord.task == task);
        }
      }
    } else if (event != null) {
      recordStream = recordStream.filter(eventRecord -> eventRecord.event.equals(event));
      if (task != 0) {
        recordStream = recordStream.filter(eventRecord -> eventRecord.task == task);
      }
    } else if (status != null) {
      recordStream = recordStream.filter(eventRecord -> eventRecord.status.equals(status));
    }
    return recordStream;
  }

  private void parseEvent() {
//    мой изначальный код
    List<File> logFiles = searchLogFiles();
    List<String> events = readLogFile(logFiles);
    for (String event : events) {
      List<String> parseEvents = new ArrayList<>(Arrays.asList(event.split("\t")));
      String ip = parseEvents.get(0);
      String userName = parseEvents.get(1);
      Date date = readDate(parseEvents.get(2));
      Event logEvent = readEvent(parseEvents.get(3));
      int eventAdditionalParameter = readEventAdditionalParameter(parseEvents.get(3));
      Status status = readStatus(parseEvents.get(4));
      eventRecords.add(new EventRecord(ip, userName, date,
              logEvent, eventAdditionalParameter, status));
    }
//код, который пропустил javarush
//    try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(logDir)) {
//      for (Path file : directoryStream) {
//        if (file.toString().toLowerCase().endsWith(".log")) {
//          try (BufferedReader reader = new BufferedReader(new FileReader(file.toFile()))) {
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//              List<String> parseEvents = new ArrayList<>(Arrays.asList(line.split("\t")));
//              if (parseEvents.size() != 5) {
//                continue;
//              }
//
//              String ip = parseEvents.get(0);
//              String userName = parseEvents.get(1);
//              Date date = readDate(parseEvents.get(2));
//              Event logEvent = readEvent(parseEvents.get(3));
//              int eventAdditionalParameter = readEventAdditionalParameter(parseEvents.get(3));
//              Status status = readStatus(parseEvents.get(4));
//              eventRecords.add(new EventRecord(ip, userName, date, logEvent, eventAdditionalParameter, status));
//            }
//          }
//        }
//      }
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
  }

  private List<File> searchLogFiles() {
    return Arrays.stream(logDir.toFile().listFiles())
            .filter(file -> file.getName().toLowerCase().endsWith(".log"))
            .collect(Collectors.toList());
  }

  private List<String> readLogFile(List<File> logFiles) {
    List<String> events = null;
    for (File log : logFiles) {
      try {
        events = Files.readAllLines(log.toPath());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return events;
  }

  private Date readDate(String time) {
    Date date = null;
    try {
      date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(time);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  private Event readEvent(String inputParam) {
    Event event = null;
    for (int i = 0; i < Event.values().length; i++) {
      if (inputParam.startsWith(Event.values()[i].name())) {
        event = Event.values()[i];
      }
    }
    return event;
  }

  private int readEventAdditionalParameter(String inputParam) {
    int result = -1;
    if (inputParam.contains(Event.DONE_TASK.name()) || inputParam.contains(Event.SOLVE_TASK.name())) {
      String s = inputParam.replaceAll(" ", "")
              .replaceAll("\\p{Alpha}", "")
              .replaceAll("\\p{Punct}", "");
      result = Integer.parseInt(s);
    }
    return result;
  }

  private Status readStatus(String inputParam) {
    Status status = null;
    for (int i = 0; i < Status.values().length; i++) {
      if (Status.values()[i].name().equals(inputParam)) {
        status = Status.values()[i];
      }
    }
    return status;
  }

  private boolean isDateBetweenDates(Date date, Date after, Date before) {
    if (after == null && before == null) {
      return true;
    } else {
      if (after == null) {
        return date.before(before);
      } else if (before == null) {
        return date.after(after);
      } else {
        return date.after(after) && date.before(before);
      }
    }
  }

  class EventRecord {
    private String ip;
    private String userName;
    private Date dateLogEvent;
    private Event event;
    private int task;
    private Status status;

    public EventRecord(String ip, String userName, Date dateLogEvent, Event event,
                       int eventAdditionalParameter, Status status) {
      this.ip = ip;
      this.userName = userName;
      this.dateLogEvent = dateLogEvent;
      this.event = event;
      this.task = eventAdditionalParameter;
      this.status = status;
    }

    @Override
    public String toString() {
      return "EventRecord{" +
              "userName='" + userName + '\'' +
              ", ip='" + ip + '\'' +
              ", dateLogEvent=" + dateLogEvent +
              ", event=" + event +
              ", eventAdditionalParameter=" + task +
              ", status=" + status +
              '}';
    }

  }

}