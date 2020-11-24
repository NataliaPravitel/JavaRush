package com.javarush.task.task27.task2712.ad;
//Ресторан(6)
//Заказ создается, потом готовится поваром, а после этого относится посетителю.
// К тому же считается время выполнения заказа.
//Будем считать, что первая часть задания выполнена.
//Перейдем ко второй - пока заказ готовится, на планшете должна показываться реклама.
//Определим, что такое реклама.
//
//Реклама - это видео определенной продолжительности. Также известно, что кто-то оплатил количество показов.
//Будем считать, что у нас известно количество оплаченных показов, общая стоимость всех показов и сам рекламный ролик.
//
//1. Для связанного с рекламой функционала создадим пакет ad.
//
//2. Создадим класс Advertisement(Рекламное объявление) в пакете ad, у которого будут следующие поля:
//Object content - видео
//String name - имя/название
//long initialAmount - начальная сумма, стоимость рекламы в копейках. Используем long, чтобы избежать проблем с округлением
//int hits - количество оплаченных показов
//int duration - продолжительность в секундах
//Модификаторы доступа расставь самостоятельно.
//
//3. В классе Advertisement создадим конструктор с порядком аргументов, соответствующим перечисленной последовательности всех полей класса
//
//4. В этом же пакете создадим еще два класса:
//AdvertisementStorage - хранилище рекламных роликов.
//AdvertisementManager - у каждого планшета будет свой объект менеджера,
// который будет подбирать оптимальный набор роликов и их последовательность для каждого заказа.
//Он также будет взаимодействовать с плеером и отображать ролики.
//
//5. Так как хранилище рекламных роликов AdvertisementStorage единственное для всего ресторана, то сделаем его синглтоном.
//
//
//Требования:
//1. В пакете ad должен быть создан класс Advertisement.
//2. В пакете ad должен быть создан класс AdvertisementManager.
//3. В пакете ad должен быть создан класс AdvertisementStorage.
//4. В классе Advertisement в пакете ad должны быть объявлены все поля перечисленные в условии задачи.
//5. В классе Advertisement в пакете ad должен быть создан корректный конструктор в соответствии с условием задачи.
//6. Класс AdvertisementStorage должен быть синглтоном и иметь публичный статический метод getInstance.

import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.NoAvailableVideoEventDataRow;

public class Advertisement {
  private Object content;
  private String name;
  private long initialAmount;//начальная сумма, стоимость рекламы в копейках
  private int hits;//количество оплаченных показов
  private int duration; //продолжительность в секундах
  private long amountPerOneDisplaying;//стоимости одного показа рекламного объявления в копейках

  public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
    this.content = content;
    this.name = name;
    this.initialAmount = initialAmount;
    this.hits = hits;
    this.duration = duration;
    this.amountPerOneDisplaying = hits > 0 ? initialAmount / hits : 0;
  }

  public String getName() {
    return name;
  }

  public int getDuration() {
    return duration;
  }

  public long getAmountPerOneDisplaying() {
    return amountPerOneDisplaying;
  }

  public void revalidate() {
    if (hits > 0) {
      hits--;
    } else {
      throw new NoVideoAvailableException();
    }
  }
  public int getHits() {
    return hits;
  }

  public boolean isActive() {
    return hits > 0;
  }

  @Override
  public String toString() {
    return String.format("%s is displaying... %d, %d", name, amountPerOneDisplaying,
            amountPerOneDisplaying * 1000 / duration);
  }
}
