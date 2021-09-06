package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

public class LoginCommand implements Command {
  private ResourceBundle validCreditCards = ResourceBundle
          .getBundle(CashMachine.RESOURCE_PATH + ".verifiedCards");
  private ResourceBundle res = ResourceBundle
          .getBundle(CashMachine.RESOURCE_PATH + ".login");

  @Override
  public void execute() throws InterruptOperationException {
    ConsoleHelper.writeMessage(res.getString("before"));
    ConsoleHelper.writeMessage(res.getString("specify.data"));
    String cardNumber = ConsoleHelper.readString();
    String pinCode = ConsoleHelper.readString();
    if (cardNumber.length() != 12 || pinCode.length() != 4) {
      ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
      execute();
    } else {
      if (validCreditCards.containsKey(cardNumber)
              && validCreditCards.getString(cardNumber).equalsIgnoreCase(pinCode)) {
        ConsoleHelper.writeMessage(String.format(res.getString("success.format"), cardNumber));
      } else {
        ConsoleHelper.writeMessage(String.format(
                res.getString("not.verified.format"), cardNumber));
        ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
        execute();
      }
    }
  }
}
