package com.javarush.task.task27.task2712.ad;
//Ресторан(8)
//Пришло время описать функционал класса AdvertisementManager.
//Но для начала нам понадобятся некоторые методы в классе Advertisement.
//
//1. В классе Advertisement создай поле long amountPerOneDisplaying.
//Оно должно равняться стоимости одного показа рекламного объявления в копейках (initialAmount/hits).
//Присвой значение полю в конструкторе.
//
//2. В классе Advertisement создай геттеры для полей name, duration и amountPerOneDisplaying.
//
//3. Определим необходимые данные для объекта AdvertisementManager - это время выполнения заказа поваром.
//Т.к. продолжительность видео у нас хранится в секундах, то и и время выполнения заказа тоже будем принимать в секундах.
//В классе AdvertisementManager создай конструктор, который принимает один параметр - int timeSeconds.
//Создай соответствующее поле и сохраните это значение в него.
//
//4. AdvertisementManager выполняет только одно единственное действие - обрабатывает рекламное видео.
//Поэтому создайте единственный публичный метод void processVideos(), его функционал опишем в следующем задании.
//А пока выведем в консоль "calling processVideos method"
//
//5. Чтобы тестировать данную функциональность, нужно добавить вызов processVideos метода у AdvertisementManager.
//Очевидно, что этот метод должен вызываться во время создания заказа, а точнее - в параллельном режиме.
//Заказ готовится в то время, как видео смотрится.
//Добавьте вызов метода processVideos() в нужное место.
//
//P.S. Не забудь что время приготовления заказа считается в минутах, а время показа рекламы в секундах!
//
//
//Требования:
//1. В классе Advertisement должны быть созданы корректные геттеры для полей name, duration и amountPerOneDisplaying.
//2. В классе Advertisement должно быть создано поле amountPerOneDisplaying равное частному от деления initialAmount на hits.
//3. В классе AdvertisementManager должен быть создан конструктор с одним параметром типа int инициализирующий поле timeSeconds.
//4. В классе AdvertisementManager должен быть создан метод processVideos, оповещающий о своем вызове выводом строки на консоль.
//5. В методе createOrder класса Tablet должен быть создан новый AdvertisementManager и у него должен быть вызван метод processVideos.

///Ресторан(9)
//Нам понадобится исключение, которое поможет обработать ситуацию,
// если у нас не будет получаться подобрать рекламные ролики.
//
//1. Создадим unchecked исключение NoVideoAvailableException в пакете ad.
//
//2. Разберем подробно метод void processVideos() в AdvertisementManager.
//2.1. Удаляем из него вывод в консоль "calling processVideos method"
//Метод должен:
//2.2. Подобрать список видео из доступных, просмотр которых обеспечивает максимальную выгоду.
// (Пока делать не нужно, сделаем позже).
//2.3. Если нет рекламных видео, которые можно показать посетителю, то бросить NoVideoAvailableException,
// которое перехватить в оптимальном месте (подумать, где это место) и с уровнем Level.INFO
// логировать фразу "No video is available for the order " + order
//2.4. Отобразить все рекламные ролики, отобранные для показа, в порядке уменьшения стоимости показа
// одного рекламного ролика в копейках. Вторичная сортировка - по увеличению стоимости показа одной
// секунды рекламного ролика в тысячных частях копейки.
//Используйте метод Collections.sort
//(Тоже пока делать не нужно, сделаем позже).
//
//Пример для заказа [Water]:
//First Video is displaying... 50, 277
//где First Video - название рекламного ролика
//где 50 - стоимость показа одного рекламного ролика в копейках
//где 277 - стоимость показа одной секунды рекламного ролика в тысячных частях копейки (равно 0.277 коп)
//Используйте методы из класса Advertisement.
//2.5. В классе Advertisement создайте публичный метод void revalidate(). Этот метод должен:
//2.5.1. Бросать UnsupportedOperationException, если количество показов не положительное число.
//2.5.2. Уменьшать количество показов.
//
//
//Требования:
//1. В методе processVideos() не должно быть вывода в консоль.
//2. В случае, если список видео для воспроизведения пуст, должно быть брошено исключение NoVideoAvailableException из метода processVideos().
//3. Исключение NoVideoAvailableException должно быть перехвачено и в лог должна быть записана
// строка об отсутствии видео для данного заказа (пример в условии).
//4. Класс NoVideoAvailableException в пакете ad должен быть потомком класса RuntimeException.
//5. Метод revalidate() должен быть реализован в соответствии с условием задачи.

//Ресторан(10)
//Рекурсию используют тогда, когда алгоритм решения задачи совпадает с алгоритмом решения подзадачи (части).
//У нас как раз такой случай. Нам нужно сделать полный перебор всех вариантов и выбрать из них лучший.
//
//Напомню, рекурсия пишется по следующему принципу:
//а) условие выхода/окончания рекурсии
//б) условие продолжения - вызов самой себя с набором параметров предыдущего шага.
//В любое время ты можешь почитать в инете подробную информацию по написанию рекурсии.
//
//Текущее задание - реализовать п.2.2 предыдущего задания с помощью рекурсии.
//(подобрать список видео из доступных, просмотр которых обеспечивает максимальную выгоду)
//Рекурсивный метод должен выбрать набор рекламных роликов, которые будут показаны посетителю.
//
//Этот набор должен удовлетворять следующим требованиям:
//1. сумма денег, полученная от показов, должна быть максимальной из всех возможных вариантов
//2. общее время показа рекламных роликов НЕ должно превышать время приготовления блюд для текущего заказа;
//3. для одного заказа любой видео-ролик показывается не более одного раза;
//4. если существует несколько вариантов набора видео-роликов с одинаковой суммой денег, полученной от показов, то:
//4.1. выбрать тот вариант, у которого суммарное время максимальное;
//4.2. если суммарное время у этих вариантов одинаковое, то выбрать вариант с минимальным количеством роликов;
//5. количество показов у любого рекламного ролика из набора - положительное число.
//
//Также не забудь реализовать п.2.4 из предыдущего задания (
// вывести на экран все подходящие ролики в порядке уменьшения стоимости показа одного рекламного ролика в копейках.
// Вторичная сортировка - по увеличению стоимости показа одной секунды рекламного ролика в тысячных частях копейки).
//
//Для каждого показанного видео-ролика должен быть вызван метод revalidate().
//
//
//Требования:
//1. Сумма денег, полученная от показов, должна быть максимальной из всех возможных вариантов.
//2. Общее время показа рекламных роликов НЕ должно превышать время приготовления блюд для текущего заказа.
//3. Для одного заказа любой видео-ролик должен показываться не более одного раза.
//4. Если существует несколько вариантов набора видео-роликов с одинаковой суммой денег,
// полученной от показов, то должен быть выбран вариант с максимальным суммарным временем.
//5. Если существует несколько вариантов набора видео-роликов с одинаковой суммой денег
// и одинаковым суммарным временем, то должен быть выбран вариант с минимальным количеством роликов.
//6. В набор должны отбираться только ролики с положительным числом показов.
//7. Для каждого показанного ролика должен быть вызван метод revalidate.


import com.javarush.task.task27.task2712.ConsoleHelper;

import java.util.*;

public class AdvertisementManager {
  private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
  private int timeSeconds;
  private List<Advertisement> advertisements = storage.list();
  private List<Advertisement> optimalVideos = new ArrayList<>();
  private long maxMoney = Long.MIN_VALUE;
  private int maxDuration = Integer.MIN_VALUE;
  private int minSize = advertisements.size();

  public AdvertisementManager(int timeSeconds) {
    this.timeSeconds = timeSeconds;
  }

  public void processVideos() {
    if (!advertisements.isEmpty()) {
      sort();
      addProfitVideos();
      print();
    } else {
      throw new NoVideoAvailableException();
    }
  }

  private void addProfitVideos() {
    List<Advertisement> tempVideoList = new ArrayList<>();
    for (int i = 0; i < advertisements.size(); i++) {
      Advertisement adv = advertisements.get(i);
      if (timeSeconds - adv.getDuration() >= 0 && !tempVideoList.contains(adv) &&
              adv.getAmountPerOneDisplaying() > 0) {
        tempVideoList.add(adv);
        timeSeconds -= adv.getDuration();
      }
    }

    long tempSumMoney = calcMoney(tempVideoList);
    if (maxMoney < tempSumMoney) {
      maxMoney = tempSumMoney;
      optimalVideos = tempVideoList;

    } else if (maxMoney == tempSumMoney) {
      int tempSumDuration = calcDuration(tempVideoList);
      if (maxDuration < tempSumDuration) {
        maxDuration = tempSumDuration;
        optimalVideos = tempVideoList;

      } else if (maxDuration == tempSumDuration) {
        if (minSize > tempVideoList.size()) {
          minSize = tempVideoList.size();
          optimalVideos = tempVideoList;
        }
      }
    } else {
      addProfitVideos();
    }

  }

  private long calcMoney(List<Advertisement> videos) {
    long sumMoney = 0;
    for (Advertisement adv : videos) {
      sumMoney += adv.getAmountPerOneDisplaying();
    }
    return sumMoney;
  }

  private int calcDuration(List<Advertisement> videos) {
    int sumDuration = 0;
    for (Advertisement adv : videos) {
      sumDuration += adv.getDuration();
    }
    return sumDuration;
  }

  private void sort() {
    Collections.sort(advertisements, Comparator.comparingLong(Advertisement::
            getAmountPerOneDisplaying).thenComparingInt(Advertisement::getDuration));
    Collections.reverse(advertisements);
  }

  private void print() {
    for (Advertisement video : advertisements) {
      if (optimalVideos.contains(video)) {
        video.revalidate();
        ConsoleHelper.writeMessage(video.getName() +
                " is displaying... " + video.getAmountPerOneDisplaying() +
                ", " + video.getAmountPerOneDisplaying() * 1000 / video.getDuration());
      }
    }
  }
}
