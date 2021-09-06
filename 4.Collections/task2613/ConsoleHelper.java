package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class ConsoleHelper {
  private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.class.getPackage().getName()
          + ".resources.common");
  private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
  public static void writeMessage(String message) {
    System.out.println(message);
  }

  public static String readString() throws InterruptOperationException {
    String message = "";
    try {
      message = bis.readLine();
      if (message.equalsIgnoreCase("exit")) {
        throw new InterruptOperationException();
      }

    } catch (IOException e) {
    }
    return message;
  }

  public static Operation askOperation() throws InterruptOperationException {
    ConsoleHelper.writeMessage(res.getString("choose.operation"));
    ConsoleHelper.writeMessage("\t 1 - " + res.getString("operation.INFO"));
    ConsoleHelper.writeMessage("\t 2 - " + res.getString("operation.DEPOSIT"));
    ConsoleHelper.writeMessage("\t 3 - " + res.getString("operation.WITHDRAW"));
    ConsoleHelper.writeMessage("\t 4 - " + res.getString("operation.EXIT"));
    Operation operation;
    try {
      int operationNumber = Integer.parseInt(ConsoleHelper.readString());
      if (operationNumber < 1 || operationNumber > 4) {
        ConsoleHelper.writeMessage(res.getString("invalid.data"));
        operation = askOperation();
      } else {
        operation = Operation.getAllowableOperationByOrdinal(operationNumber);
      }
    } catch (NumberFormatException e) {
      ConsoleHelper.writeMessage(res.getString("invalid.data"));
      operation = askOperation();
    }
    return operation;
  }

  public static String askCurrencyCode() throws InterruptOperationException {
    ConsoleHelper.writeMessage(res.getString("choose.currency.code"));
    String currencyCode = ConsoleHelper.readString().trim().toUpperCase();
    if (currencyCode.trim().length() != 3 || currencyCode.isEmpty()) {
      ConsoleHelper.writeMessage(res.getString("invalid.data"));
      currencyCode = askCurrencyCode();
    }
    return currencyCode;
  }

  public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
    ConsoleHelper.writeMessage(
            String.format(res.getString("choose.denomination.and.count.format"), currencyCode));
    String inputData = ConsoleHelper.readString();
    String[] denominationAndCount = inputData.split(" ");

    if (denominationAndCount.length != 2) {
      ConsoleHelper.writeMessage(res.getString("invalid.data"));
      denominationAndCount = getValidTwoDigits(currencyCode);
    }

    try {
      if (Integer.parseInt(denominationAndCount[0]) <= 0
              || Integer.parseInt(denominationAndCount[1]) <= 0) {
        ConsoleHelper.writeMessage(res.getString("invalid.data"));
        denominationAndCount = getValidTwoDigits(currencyCode);
      }
    } catch (NumberFormatException e) {
      ConsoleHelper.writeMessage(res.getString("invalid.data"));
      denominationAndCount = getValidTwoDigits(currencyCode);
    }
    return denominationAndCount;
  }

  public static void printExitMessage() {
    ConsoleHelper.writeMessage(res.getString("the.end"));
  }
}
