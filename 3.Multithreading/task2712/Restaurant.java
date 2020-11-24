package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Waiter;
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

public class Restaurant {
  private static final int ORDER_CREATING_INTERVAL = 100;

  public static void main(String[] args) {
    Tablet tablet = new Tablet(5);
    Tablet tablet1 = new Tablet(1);/////////////
    Cook firstCook = new Cook("Amigo");
    Cook secondCook = new Cook("Alex");/////////////
    Waiter waiter = new Waiter();
    firstCook.addObserver(waiter);
    secondCook.addObserver(waiter);////////
    tablet.addObserver(firstCook);
    tablet1.addObserver(secondCook);////////////
    tablet.createOrder();
    tablet.createOrder();///////////////////////
    tablet1.createOrder();//////////


    DirectorTablet directorTablet = new DirectorTablet();
    directorTablet.printAdvertisementProfit();
    directorTablet.printCookWorkloading();
    directorTablet.printActiveVideoSet();
    directorTablet.printArchivedVideoSet();
  }
}
