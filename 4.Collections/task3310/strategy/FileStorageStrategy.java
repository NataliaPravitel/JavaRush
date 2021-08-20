package com.javarush.task.task33.task3310.strategy;

public class FileStorageStrategy implements StorageStrategy {
  static final int DEFAULT_INITIAL_CAPACITY = 16;
  static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000;

  FileBucket[] table;
  int size;
  private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
  long maxBucketSize;

  public long getBucketSizeLimit() {
    return bucketSizeLimit;
  }

  public void setBucketSizeLimit(long bucketSizeLimit) {
    this.bucketSizeLimit = bucketSizeLimit;
  }

  public FileStorageStrategy() {
    init();
  }

  private void init() {
    table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    for (int i = 0; i < table.length; i++) {
      table[i] = new FileBucket();
    }
  }

  int hash(Long k) {
    int h;
    return (k == null) ? 0 : (h = k.hashCode()) ^ (h >>> 16);
  }

  int indexFor(int hash, int length) {
    return hash & (length-1);
  }

  Entry getEntry(Long key) {
    int hash = (key == null) ? 0 : hash((long) key.hashCode());
    for (int i = 0; i < table.length; i++) {
      for (Entry e = table[i].getEntry(); e != null; e = e.next) {
        Object k;
        if (e.hash == hash &&
                ((k = e.key) == key || (key != null && key.equals(k))))
          return e;
      }
    }
    return null;
  }

  void resize(int newCapacity) {
    FileBucket[] newTable = new FileBucket[newCapacity];
    for (int i = 0; i < newTable.length; i++) {
      newTable[i] = new FileBucket();
    }
    transfer(newTable);
    table = newTable;
  }

  void transfer(FileBucket[] newTable) {
    FileBucket[] oldTable = table;
    int newCapacity = newTable.length;
    for (int j = 0; j < oldTable.length; j++) {
      Entry e = oldTable[j].getEntry();
      if (e != null) {
        oldTable[j] = null;
        do {
          Entry next = e.next;
          int i = indexFor(e.hash, newCapacity);
          e.next = newTable[i].getEntry();
          newTable[i].putEntry(e);
          e = next;
        } while (e != null);
      }
    }
  }

  void addEntry(int hash, Long key, String value, int bucketIndex) {
    Entry e = table[bucketIndex].getEntry();
    table[bucketIndex].putEntry(new Entry(hash, key, value, e));
    if (size++ >= bucketSizeLimit)
      resize(2 * table.length);
  }

  void createEntry(int hash, Long key, String value, int bucketIndex) {
    Entry e = table[bucketIndex].getEntry();
    table[bucketIndex].putEntry(new Entry(hash, key, value, e));
    size++;
  }

  @Override
  public boolean containsKey(Long key) {
    return getEntry(key) != null;
  }

  @Override
  public boolean containsValue(String value) {
    if (value == null) {
      return false;
    }
    FileBucket[] buckets = table;
    for (int i = 0; i < buckets.length ; i++)
      for (Entry e = buckets[i].getEntry() ; e != null ; e = e.next)
        if (value.equals(e.value))
          return true;
    return false;
  }

  @Override
  public void put(Long key, String value) {
    int hash = (key == null) ? 0 : hash((long) key.hashCode());//1
    int i = indexFor(hash, table.length);//2
    for (Entry e = table[i].getEntry(); e != null; e = e.next) {//3
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
    for (int i = 0; i < table.length; i++) {
      for (Entry e = table[i].getEntry(); e != null; e = e.next)
        if (value.equals(e.value))
          return e.getKey();
    }
    return null;
  }

  @Override
  public String getValue(Long key) {
    Entry entry = getEntry(key);
    return (entry != null) ? entry.getValue() : null;
  }
}
