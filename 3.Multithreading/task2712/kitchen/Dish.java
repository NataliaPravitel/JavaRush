package com.javarush.task.task27.task2712.kitchen;
//Ресторан(5)
//Сейчас повар готовит блюда моментально. На самом деле приготовление занимает какое-то время.
//Нам нужно вычислить время приготовления всего заказа, чтобы потом подбирать рекламные ролики для этого времени.
//
//1. Предположим, что нам известно время приготовления каждого блюда в минутах. Захардкодим его в классе Dish.
//1.1. Измени создание элементов enum - Fish(25), Steak(30), Soup(15), Juice(5), Water(3);
//1.2. Создай поле private int duration с геттером.
//Чтобы создать геттер, нажмите Alt+Insert и выберите пункт Getter. Далее выберите имя поля и нажмите OK(Enter).
//
//2. Добавим возможность посчитать продолжительность приготовления всего заказа.
//Куда его добавить???
//2.1. Если в класс Cook, то повар сможет считать продолжительность приготовления заказа.
//Чтобы другие классы могли получить эту информацию, необходимо будет получить доступ к объекту Cook.
//2.2. Если в класс Order, то имея доступ к заказу всегда можно узнать продолжительность приготовления.
//2.3. Выбери правильное место из п.2.1. и п.2.2. и добавьте метод public int getTotalCookingTime(),
// который посчитает суммарное время приготовления всех блюд в заказе.
//2.4. Добавим нашему повару вывод в консоль этой информации. Пусть теперь выводится аналогичное сообщение:
//Start cooking - Your order: [Soup, Juice, Water] of Tablet{number=5}, cooking time 23min
//
//Наведем красоту:
//3. Запустим приложение и сразу введем 'exit'. Вывод получился не очень красивым.
//Сделай так, чтобы если в заказе нет блюд, он не отправлялся повару. Найди это место и реализуйте логику.
//В классе Order создай вспомогательный метод public boolean isEmpty(), который будет определять, есть ли какие либо блюда в заказе.
//
//
//Требования:
//1. Время приготовления блюд должно соответствовать условию задачи.
//2. Поле duration в enum Dish должно быть приватным и иметь публичный геттер.
//3. В классе Order должен быть создан метод public int getTotalCookingTime
// возвращающий количество времени требующееся на приготовление текущего заказа.
//4. Помимо списка блюд в заказе, на экран также должно быть выведено время приготовления заказа.
//5. Если в заказе отсутствуют блюда, он не должен быть отправлен повару.
//6. Метод isEmpty в классе Order должен возвращать true, если заказ пуст.

public enum Dish {
  Fish(25),
  Steak(30),
  Soup(15),
  Juice(5),
  Water(3);

  private int duration;

  Dish(int duration) {
    this.duration = duration;
  }

  public int getDuration() {
    return duration;
  }

  public static String allDishesToString() {
    StringBuilder builder = new StringBuilder();
    for (Dish dish : Dish.values()) {
      builder.append(dish).append(", ");
    }
    return builder.substring(0, builder.lastIndexOf(","));
  }
}
