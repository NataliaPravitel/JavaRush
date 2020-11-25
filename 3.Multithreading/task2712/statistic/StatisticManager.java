package com.javarush.task.task27.task2712.statistic;
//Ресторан(11)
//Мы реализовали первые две фичи из трех. Напомню, первой была автоматизация приготовления заказа, второй - подбор рекламных роликов, а третья - статистика для директора. Собственно, к ней мы и добрались.
//
//Нам нужно будет реализовать следующую функциональность:
//1. подсчет статистики
//2. отображение статистики директору
//
//Попробуем определиться с последовательностью действий. Чтобы отображать данные, сначала нужно их откуда-то получить.
//
//Есть два подхода:
//ПЕРВЫЙ - применяется, когда сложно получить реальные данные, либо когда другой человек работает над получением данных:
//1. захардкодить данные в приложении
//2. отобразить хардкоженную статистику
//3. сделать получение реальных данных - реальную статистику (интеграция с чужим кодом)
//
//ВТОРОЙ:
//1. сделать получение реальных данных - реальную статистику
//2. отобразить статистику
//
//Т.к. мы сами реализовываем данную функциональность, и статистика у нас не сложная, то выберем второй подход.
//Начнем с получения реальных данных - реальной статистики.
//
//Идея такая:
//Есть хранилище событий.
//Когда у нас случается событие, то мы будем регистрировать это событие в хранилище.
//На запрос директора мы будем фильтровать события из хранилища, вычислять необходимые данные и отображать их.
//
//Для начала нам нужно несколько классов.
//1. Создадим пакет statistic, в котором создадим класс StatisticManager. С его помощью будем регистрировать события в хранилище.
//2. У нас должно быть одно хранилище с одной точкой входа. Поэтому сделаем StatisticManager синглтоном.
//3. Внутри пакета statistic создадим вложенный пакет event, в котором будут храниться классы, связанные с событиями.
//4. Создадим в пакете event интерфейс EventDataRow. На данный момент он является интерфейсом-маркером,
// т.к. не содержит методов, и по нему мы определяем, является ли переданный объект событием или нет.
//5. В StatisticManager создадим публичный метод void register(EventDataRow data), который будет регистрировать событие в хранилище.
//Мы вернемся к реализации позднее.
//
//
//Требования:
//1. Класс StatisticManager созданный в пакете statistic должен быть синглтоном.
//2. В классе StatisticManager должен быть создан метод public void register с одним параметром типа EventDataRow.
//3. В классе StatisticManager должен быть создан публичный статический метод getInstance.
//4. В пакете statistic должен быть создан пакет event, а в нем интерфейс маркер EventDataRow.


//Ресторан(13)
//Вернемся к методу register в классе StatisticManager. Он должен регистрировать события в хранилище.
//Создадим хранилище : )
//Хранилище связано 1 к 1 с менеджером, т.е. один менеджер и одно хранилище на приложение.
//К хранилищу может доступиться только StatisticManager. Поэтому...
//Из вышеперечисленного следует, что хранилище должно быть приватным иннер классом.
//Назовем его StatisticStorage.
//
//1. Внутри класса StatisticManager создать приватный иннер класс StatisticStorage.
//
//2. Чтобы менеджер мог получить доступ к хранилищу, нужно в классе StatisticManager
// создать поле statisticStorage типа StatisticStorage.
//Инициализируй его экземпляром класса.
//
//3. StatisticStorage будет хранить данные внутри себя в виде мапы/словаря storage.
//Связь StatisticStorage и Map должна быть has-a
//Типы для мапы - <EventType, List<EventDataRow>>
//
//4. В конструкторе StatisticStorage инициализируй хранилище данными по-умолчанию:
//например используя цикл, для каждого EventType добавь new ArrayList<EventDataRow>()
//
//
//Требования:
//1. Внутри класса StatisticManager должен быть создан приватный класс StatisticStorage.
//2. Внутри класса StatisticManager должно быть создано и инициализировано поле statisticStorage.
//3. Внутри класса StatisticStorage должно быть создано поле storage типа Map<EventType, List<EventDataRow>>.
//4. Поле storage должно быть заполнено некоторыми значениями.


//Ресторан(14)
//1. Внутри StatisticStorage создай метод void put(EventDataRow data).
//
//2. Чтобы методом put(EventDataRow data) добавить объект data в данные карты, нужен тип события - EventType.
//Будет правильно, если событие будет хранить в себе свой тип. Поэтому:
//2.1. В интерфейс EventDataRow добавь метод EventType getType()
//2.2. Реализуй этот метод в каждом классе-событии: CookedOrderEventDataRow, NoAvailableVideoEventDataRow, VideoSelectedEventDataRow
//
//3. Сделай так, чтобы к методу void put(EventDataRow data) нельзя было получить доступ за пределами класса StatisticManager.
//Воспользуйся особенностями вложенных классов.
//
//Теперь остается расставить вызовы StatisticManager в те места, которые генерируют события.
//
//4. Зарегистрируй событие для повара во время приготовления еды.
//Добавь геттер для поля dishes в класс Order, используйте его при создании события.
//
//5. Зарегистрируй событие "видео выбрано" перед отображением рекламы пользователю.
//
//6. Метод register с одним параметром типа EventDataRow должен регистрировать полученное событие в statisticStorage.
//
//
//Требования:
//1. В интерфейсе EventDataRow должен быть объявлен метод EventType getType().
//2. В классах поддерживающих интерфейс EventDataRow должен быть корректно реализован метод getType().
//3. Повар во время приготовления еды должен генерировать соответствующее событие.
//4. Перед отображением видео должно быть зарегистрировано событие "видео выбрано".
//5. Метод put в классе StatisticStorage должен быть реализован в соответствии с условием задачи.
//6. Метод register класса StatisticManager с одним параметром типа EventDataRow должен регистрировать полученное событие в statisticStorage.

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticManager {
  private static StatisticManager instance;
  private StatisticStorage statisticStorage = new StatisticStorage();

  private StatisticManager() {
  }

  public static synchronized StatisticManager getInstance() {
    if (instance == null) {
      instance = new StatisticManager();
    }
    return instance;
  }


  public void register(EventDataRow data) {
    statisticStorage.put(data);
  }

  public TreeMap<Date, Long> getProfitPerDayByAdvertisementMap() {
    List<EventDataRow> eventDataRowList = statisticStorage.get(EventType.SELECTED_VIDEOS);
    TreeMap<String, Long> profitMap = new TreeMap<>();

    for (EventDataRow eventDataRow : eventDataRowList) {
      VideoSelectedEventDataRow videoData = (VideoSelectedEventDataRow) eventDataRow;
      String date = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
              .format(videoData.getDate());
      long amount = videoData.getAmount();
      if (!profitMap.containsKey(date)) {
        profitMap.put(date, amount);
      } else {
        profitMap.put(date, profitMap.get(date) + amount);
      }
    }

    TreeMap<Date, Long> resultProfitMap = new TreeMap<>(Comparator.reverseOrder());
    profitMap.forEach((k, v) -> {
      try {
        resultProfitMap.put(new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
                .parse(k), v);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    });
    return resultProfitMap;
  }

  public TreeMap<Date, TreeMap<String, Integer>> getWorkingTimePerDaysByCooks() {
    TreeMap<Date, CookedOrderEventDataRow> cookDataByDays = getWorkingDateMap();
    Set<String> dates = new TreeSet<>();
    cookDataByDays.forEach((k,v) -> dates.add(new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
            .format(k)));

    TreeMap<String, TreeMap<String, Integer>> preResult = new TreeMap<>();
    dates.forEach(s -> preResult.put(s, null));

    for (Map.Entry<Date, CookedOrderEventDataRow> entry : cookDataByDays.entrySet()) {
      String date = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
              .format(entry.getKey());
      String cookName = entry.getValue().getCookName();
      int time = entry.getValue().getTime();
      TreeMap<String, Integer> cookAndWorkTime = preResult.get(date);
      if (cookAndWorkTime == null) {
        cookAndWorkTime = new TreeMap<>();
        cookAndWorkTime.put(cookName, time);
        preResult.put(date, cookAndWorkTime);
      } else {
        if (!cookAndWorkTime.containsKey(cookName)) {
          cookAndWorkTime.put(cookName, time);
        } else {
          cookAndWorkTime.put(cookName, cookAndWorkTime.get(cookName) + time);
        }
        preResult.put(date, cookAndWorkTime);
      }
    }

    TreeMap<Date, TreeMap<String, Integer>> result = new TreeMap<>(Comparator.reverseOrder());
    preResult.forEach((k, v) -> {
      try {
        result.put(new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
                .parse(k), v);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    });
    return result;
  }

  private TreeMap<Date, CookedOrderEventDataRow> getWorkingDateMap() {
    List<EventDataRow> eventDataRowList = statisticStorage.get(EventType.COOKED_ORDER);
    TreeMap<Date, CookedOrderEventDataRow> cookDataByDays =
            new TreeMap<>(Comparator.reverseOrder());

    for (EventDataRow eventDataRow : eventDataRowList) {
      CookedOrderEventDataRow cookData = (CookedOrderEventDataRow) eventDataRow;
      cookDataByDays.put(cookData.getDate(), cookData);
    }
    return cookDataByDays;
  }

  private class StatisticStorage {
    private Map<EventType, List<EventDataRow>> storage = new HashMap<>();

    public StatisticStorage() {
      Arrays.asList(EventType.values()).forEach(
              type -> storage.put(type, new ArrayList<EventDataRow>()));
    }

    private void put(EventDataRow data) {
      if (storage.containsKey(data.getType())) {
        storage.get(data.getType()).add(data);
      }
    }

    private List<EventDataRow> get(EventType type) {
      if (!this.storage.containsKey(type)) {
        throw new UnsupportedOperationException();
      }
      return this.storage.get(type);
    }
  }
}