package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
//Ресторан(18)
//Сейчас у нас один планшет и 1 повар.
//Давай создадим несколько планшетов, которые будут рандомно генерировать заказы, и сделаем два повара.
//
//1. В классе Restaurant создай ПРИВАТНУЮ статическую константу int ORDER_CREATING_INTERVAL = 100.
//
//2. В отдельном классе создай таск (Runnable) RandomOrderGeneratorTask. Этот таск должен:
//2.1. Хранить список всех планшетов
//2.2. Используя Math.random выбирать случайный планшет.
//2.3. У RandomOrderGeneratorTask должен быть только один единственный метод.
//2.4. Генерировать случайный заказ каждые ORDER_CREATING_INTERVAL миллисекунд для планшета из п.2.2. (не печатай стек-трейс)
//Сейчас заказ создается в методе createOrder в классе Tablet.
//В классе Tablet создай метод void createTestOrder() с похожей функциональностью,
//который будет случайным образом генерировать заказ со случайными блюдами не общаясь с реальным человеком.
//Все необходимые данные передай в конструкторе.
//
//Подсказка:
//а) создай класс TestOrder - наследник Order - в пакете родителя.
//б) в классе Order создай protected метод initDishes(), в котором инициализируй dishes. Вызови этот метод в конструкторе
//в) в классе Order сделай поле dishes protected
//г) переопредели initDishes в классе-наследнике TestOrder. Сделай инициализацию случайным набором блюд.
//д) вместо создания объекта Order в методе createTestOrder() класса Tablet, создавай объект класса TestOrder.
//Весь другой функционал метода createTestOrder оставь прежним
//
//3. Отрефакторь методы createTestOrder() и createOrder(): в одном из методов выдели код,
// который повторяется в обоих методах, и нажми Ctrl+Alt+M, введи любое имя метода и нажми ОК.
// IDEA предложит заменить этот код во втором методе, подтверди.
//
//
//Требования:
//1. В классе Restaurant должна быть создана приватная статическая константа ORDER_CREATING_INTERVAL типа int со значением 100.
//2. Класс RandomOrderGeneratorTask должен быть реализован в соответствии с условием задачи.
//3. Класс TestOrder должен быть реализован в соответствии с условием задачи.
//4. В классе Order должен быть создан метод initDishes инициализирующий dishes.
//5. В конструкторе класса Order должен быть вызван метод initDishes.

//Ресторан(19)
//Нам осталось доделать метод main.
//
//Сперва давай сравним параметры конструктора RandomOrderGeneratorTask.
//Пожалуйста, сделай сигнатуру такой же, как у меня:
//public RandomOrderGeneratorTask(List<Tablet> tablets, int interval)
//
//В методе main:
//1. Удали создание хардкоженного планшета и вызова его метода createOrder().
//2. Создай второго повара.
//3. Зарегистрируй поваров используя класс StatisticManager.
//4. Для второго повара и всех планшетов расставь зависимость Observer-Observable.
//5. Создай список объектов-планшетов 5 штук, инициализируйте его в цикле.
//6. Создай и запустим трэд на основе объекта RandomOrderGeneratorTask.
//7. Через секунду прерви его и посмотри на вывод в консоль.
//
//Уупс, два повара готовят один и тот же заказ 8-О
//
//Такой аутпут получился потому, что Observable информирует всех своих Observer-ов, т.е. планшет направляет свой заказ всем известным ему поварам.
//Такое поведение нам не подходит, поэтому будем исправлять его в следующем задании.
//
//P.S. Не забудь остановить тред!
//
//
//Требования:
//1. В методе main класса Restaurant должно быть создано 5 планшетов.
//2. В методе main класса Restaurant должно быть создано 2 повара.
//3. Повара должны быть зарегистрированы в множестве поваров в StatisticManager.
//4. Должен быть создан и запущен тред на основе RandomOrderGeneratorTask.
//5. Конструктор класса RandomOrderGeneratorTask должен соответствовать описанному в условии задачи.
//6. Оба повара должны быть добавлены каждому планшету в качестве наблюдателей.


//Ресторан(22)
//К сожалению, заказы все еще не готовятся параллельно. Вот как работает наш трэд из предыдущего задания.
//Он находит повара, потом находит заказ, отдает заказ повару методом startCookingOrder,
// потом ждет окончания приготовления, и только после этого переходит к следующему заказу.
// Так происходит потому, что все действия внутри одного трэда - последовательные. Мы не можем в пределах одного трэда выполнять параллельные процессы.
//Нам нужна стандартная Producer-Consumer реализация.
//RandomOrderGeneratorTask - у нас Producer, т.к. производит заказы
//Cook - это Consumer, т.к. обрабатывает заказы
//
//1. Перенеси поле-очередь из OrderManager в Restaurant, сделай ее приватной константой.
//2. Добавь поле-очередь и сеттер в класс Cook, сразу после создания повара используя созданный сеттер
// установи ему константу из п.1. в качестве значения для созданного поля.
//3. Tablet - не должен быть Observable. Убери все зависимости.
//4. В Tablet создай сеттер и установи ссылку на очередь (п.1) при создании планшета.
//5. В Tablet часть логики, которая уведомляет Observer-а, замени на такую, которая добавляет заказ в очередь.
//
//6. В методе main создай и запусти трэды на основании тасок Cook.
//7. Из класса StatisticManager удали сет поваров, его геттер и метод register(Cook cook).
//8. Сделай класс Cook - таском (Runnable). Перенеси логику из трэда внутри конструктора OrderManager в метод run класса Cook.
//9. Удали класс OrderManager и в методе main исправь зависимость Observer-Observable.
//
//
//Требования:
//1. В классе Restaurant должно быть создано private final static поле orderQueue типа LinkedBlockingQueue.
//2. В классе Cook должно быть создано private поле queue типа LinkedBlockingQueue и сеттер.
//3. В классе Tablet должно быть создано private поле queue типа LinkedBlockingQueue.
//4. Класс Tablet не должен быть потомком класса Observable.
//5. Класс Cook должен поддерживать интерфейс Runnable.
//6. Общая логика приготовления заказов и показа рекламы должна быть сохранена.
//7. Класс OrderManager должен быть удален.

public class Restaurant {
  private final static int ORDER_CREATING_INTERVAL = 100;
  private final static LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

  public static void main(String[] args) {
    List<Tablet> tablets = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      Tablet tablet = new Tablet(i + 1);
      tablet.setQueue(orderQueue);
      tablets.add(tablet);
    }

    Cook firstCook = new Cook("Amigo");
    firstCook.setQueue(orderQueue);
    Cook secondCook = new Cook("Alex");
    secondCook.setQueue(orderQueue);

    Waiter waiter = new Waiter();
    firstCook.addObserver(waiter);
    secondCook.addObserver(waiter);

    Thread threadFirstCook = new Thread(firstCook);
    threadFirstCook.start();
    Thread threadSecondCook = new Thread(secondCook);
    threadSecondCook.start();

    Thread thread = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
    thread.start();



    try {
      Thread.sleep(1000);
      Thread.interrupted();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    DirectorTablet directorTablet = new DirectorTablet();
    directorTablet.printAdvertisementProfit();
    directorTablet.printCookWorkloading();
    directorTablet.printActiveVideoSet();
    directorTablet.printArchivedVideoSet();
  }
}
