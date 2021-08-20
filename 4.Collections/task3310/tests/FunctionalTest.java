package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.*;
import org.junit.Assert;
import org.junit.Test;

public class FunctionalTest {
  @Test
  public void testHashMapStorageStrategy() {
    Shortener shortener = new Shortener(new HashMapStorageStrategy());
    testStorage(shortener);
  }

  @Test
  public void testOurHashMapStorageStrategy() {
    Shortener shortener = new Shortener(new OurHashMapStorageStrategy());
    testStorage(shortener);
  }

  @Test
  public void testFileStorageStrategy() {
    Shortener shortener = new Shortener(new FileStorageStrategy());
    testStorage(shortener);
  }

  @Test
  public void testHashBiMapStorageStrategy() {
    Shortener shortener = new Shortener(new HashBiMapStorageStrategy());
    testStorage(shortener);
  }

  @Test
  public void testDualHashBidiMapStorageStrategy() {
    Shortener shortener = new Shortener(new DualHashBidiMapStorageStrategy());
    testStorage(shortener);
  }

  @Test
  public void testOurHashBiMapStorageStrategy() {
    Shortener shortener = new Shortener(new OurHashMapStorageStrategy());
    testStorage(shortener);
  }

  public void testStorage(Shortener shortener) {
    String testString1 = "Test string 1";
    String testString2 = "Test string 2";
    String testString3 = "Test string 1";

    Long id1 = shortener.getId(testString1);
    Long id2 = shortener.getId(testString2);
    Long id3 = shortener.getId(testString3);

    Assert.assertNotEquals(id1, id2);
    Assert.assertNotEquals(id3, id2);

    Assert.assertEquals(id1, id3);

    Assert.assertEquals(testString1, shortener.getString(id1));
    Assert.assertEquals(testString2, shortener.getString(id2));
    Assert.assertEquals(testString3, shortener.getString(id3));
  }

}
