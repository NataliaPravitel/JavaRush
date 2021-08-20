package com.javarush.task.task33.task3310.strategy;

public class OurHashMapStorageStrategy implements StorageStrategy {
  //емкость хеш-таблицы по умолчанию (16)
  static final int DEFAULT_INITIAL_CAPACITY = 16;
  //коэффициент загрузки, используемый по умолчанию
  static final float DEFAULT_LOAD_FACTOR = 0.75f;
  //хеш-таблица, реализованная на основе массива, для хранения пар «ключ-значение» в виде узлов.
  Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
  // количество пар «ключ-значение»;
  int size;
  //предельное количество элементов, при достижении которого размер хэш-таблицы увеличивается вдвое.
  int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
  //отвечает за то, при какой степени загруженности текущей хеш-таблицы необходимо создавать новую хеш-таблицу,
  // т.е. как только хеш-таблица заполнилась на 75%, будет создана новая хеш-таблица с перемещением в неё текущих элементов
  // (затратная операция, так как требуется перехеширование всех элементов)
  float loadFactor = DEFAULT_LOAD_FACTOR;

//  высчитает его хэш-код. Этот хэш-код используется для определения ячейки массива, где будет храниться объект класса Entry.
  int hash(Long k) {
    int h;
    return (k == null) ? 0 : (h = k.hashCode()) ^ (h >>> 16);
  }
//используется для определения конкретной ячейку в массиве table, в которую будет определен для хранения объект класса Entry
  int indexFor(int hash, int length) {
    return hash & (length-1);
  }

  Entry getEntry(Long key) {
    int hash = (key == null) ? 0 : hash((long) key.hashCode());
    for (Entry e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
      Object k;
      if (e.hash == hash &&
              ((k = e.key) == key || (key != null && key.equals(k))))
        return e;
    }
    return null;
  }

  void resize(int newCapacity) {
    Entry[] oldTable = table;
    int oldCapacity = oldTable.length;
    if (oldCapacity == 1 << 30) {
      threshold = Integer.MAX_VALUE;
      return;
    }

    Entry[] newTable = new Entry[newCapacity];
    transfer(newTable);
    table = newTable;
    threshold = (int)(newCapacity * loadFactor);
  }

  void transfer(Entry[] newTable) {
    Entry[] src = table;
    int newCapacity = newTable.length;
    for (int j = 0; j < src.length; j++) {
      Entry e = src[j];
      if (e != null) {
        src[j] = null;
        do {
          Entry next = e.next;
          int i = indexFor(e.hash, newCapacity);
          e.next = newTable[i];
          newTable[i] = e;
          e = next;
        } while (e != null);
      }
    }
  }

  void addEntry(int hash, Long key, String value, int bucketIndex) {
    Entry e = table[bucketIndex];
    table[bucketIndex] = new Entry(hash, key, value, e);
    if (size++ >= threshold)
      resize(2 * table.length);
  }

  void createEntry(int hash, Long key, String value, int bucketIndex) {
    Entry e = table[bucketIndex];
    table[bucketIndex] = new Entry(hash, key, value, e);
    size++;
  }
  
  @Override
  public boolean containsKey(Long key) {
    return getEntry(key) != null;
  }

  @Override
  public boolean containsValue(String value) {
    if (value == null)
      return false;

    Entry[] tab = table;
    for (int i = 0; i < tab.length ; i++)
      for (Entry e = tab[i] ; e != null ; e = e.next)
        if (value.equals(e.value))
          return true;
    return false;
  }
//  1 Вычисляется хеш-значение ключа введенного объекта.
// Хэш ключа вычисляет метод static final int hash(Object key),
// который уже обращается к известному нам методу hashCode() ключа.
// Для этого используется либо переопределенный метод hashCode(), либо его реализация по умолчанию.
// Дополнительные преобразования в методе hash()/
//  придумали различными манипуляциями подмешивать старшие биты хеша в младшие,
//  чтобы улучшить распределение по бакетам (чтобы старшие биты родного хеша объекта начали вносить
//  коррективы в то, в какой бакет попадёт объект) и, как следствие, производительность.
//  2 Вычисляем индекс бакета (ячейки массива), в который будет добавлен наш элемент
//  3 Создается объект Node (Entry) и проверяется table на пустое место путем перебора
//  4 Наш сформированный объект Node будет добавлен в бакет.
//  После добавления будет произведена проверка не превышает ли текущее количество элементов параметр threshold
  @Override
  public void put(Long key, String value) {
    int hash = (key == null) ? 0 : hash((long) key.hashCode());//1
    int i = indexFor(hash, table.length);//2
    for (Entry e = table[i]; e != null; e = e.next) {//3
      Object k;
      if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
        e.value = value;
        return;
      }
    }
    addEntry(hash, key, value, i);//4
  }

  @Override
  public Long getKey(String value) {
    for (Entry tableElement : table)
      for (Entry e = tableElement; e != null; e = e.next)
        if (value.equals(e.value))
          return e.getKey();
    return null;
  }
  //работает метод get очень просто. Когда вы передаете методу какой-либо ключ, чтобы получить значение из хеш-карты:
  //Объект Ekey проверяется на равенство null. Если так и есть, то будет возвращено значение объекта, хранящегося в ячейке table[0];
  //У объекта key вызывается метод hashcode(), который высчитывает хэш-код;
  //indexFor(hash,table.length) используется для определения конкретной ячейки массива table, из которой необходимо взять объект класса Entry;
  //После получения номера ячейки массива table будет произведена итерация по списку и сравнение ключей
  // с помощью метода equals(). Если результатом будет истина, то будет возвращено значение объекта Entry,
  // в противном случае — null.
  @Override
  public String getValue(Long key) {
    Entry entry = getEntry(key);
    return (entry != null) ? entry.getValue() : null;
  }
}
