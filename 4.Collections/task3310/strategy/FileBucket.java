package com.javarush.task.task33.task3310.strategy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileBucket {
  private Path path;

  public FileBucket() {
    try {
      this.path = Files.createTempFile(Integer.toHexString(hashCode()), ".tmp");
      path.toFile().deleteOnExit();
      Files.deleteIfExists(path);
      Files.createFile(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public long getFileSize() {
    long size = 0;
    try {
      size = Files.size(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return size;
  }

  public void putEntry(Entry entry) {
    try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
      oos.writeObject(entry);
    } catch (Exception e) {

    }
  }

  public Entry getEntry() {
    if (getFileSize() > 0) {
      try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
        return (Entry) ois.readObject();
      } catch (Exception e) {

      }
    }
    return null;
  }

  public void remove() {
    try {
      Files.delete(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
