package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.IPQuery;

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
import java.util.stream.Collectors;

public class LogParser implements IPQuery {
  private Path logDir;
  private List<EventRecord> eventRecords = new ArrayList<>();

  public LogParser(Path logDir) {
    this.logDir = logDir;
    parseEvent();
  }

  @Override
  public int getNumberOfUniqueIPs(Date after, Date before) {
    return getUniqueIPs(after, before).size();
  }

  @Override
  public Set<String> getUniqueIPs(Date after, Date before) {
    Set<String> uniqueIPs = new HashSet<>();
    for (EventRecord userEvent : eventRecords) {
      if (isDateBetweenDates(userEvent.dateLogEvent, after, before)) {
        uniqueIPs.add(userEvent.ip);
      }
    }
    return uniqueIPs;
  }

  @Override
  public Set<String> getIPsForUser(String user, Date after, Date before) {
    Set<String> ipsForUser = new HashSet<>();
    for (EventRecord userEvent : eventRecords) {
      if (isDateBetweenDates(userEvent.dateLogEvent, after, before)
              && userEvent.userName.equalsIgnoreCase(user)) {
        ipsForUser.add(userEvent.ip);
      }
    }
    return ipsForUser;
  }

  @Override
  public Set<String> getIPsForEvent(Event event, Date after, Date before) {
    Set<String> ipsForEvent = new HashSet<>();
    for (EventRecord userEvent : eventRecords) {
      if (isDateBetweenDates(userEvent.dateLogEvent, after, before)
              && userEvent.event.equals(event)) {
        ipsForEvent.add(userEvent.ip);
      }
    }
    return ipsForEvent;
  }

  @Override
  public Set<String> getIPsForStatus(Status status, Date after, Date before) {
    Set<String> ipsForStatus = new HashSet<>();
    for (EventRecord userEvent : eventRecords) {
      if (isDateBetweenDates(userEvent.dateLogEvent, after, before)
              && userEvent.status.equals(status)) {
        ipsForStatus.add(userEvent.ip);
      }
    }
    return ipsForStatus;
  }

  private void parseEvent() {
    //мой изначальный код
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
      eventRecords.add(new EventRecord(ip, userName, date, logEvent, eventAdditionalParameter, status));
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
    private int eventAdditionalParameter;
    private Status status;

    public EventRecord(String ip, String userName, Date dateLogEvent, Event event,
                       int eventAdditionalParameter, Status status) {
      this.ip = ip;
      this.userName = userName;
      this.dateLogEvent = dateLogEvent;
      this.event = event;
      this.eventAdditionalParameter = eventAdditionalParameter;
      this.status = status;
    }

    @Override
    public String toString() {
      return "EventRecord{" +
              "userName='" + userName + '\'' +
              ", ip='" + ip + '\'' +
              ", dateLogEvent=" + dateLogEvent +
              ", event=" + event +
              ", eventAdditionalParameter=" + eventAdditionalParameter +
              ", status=" + status +
              '}';
    }

  }

}