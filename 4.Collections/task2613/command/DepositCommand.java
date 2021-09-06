package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

class DepositCommand implements Command {
  private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + ".deposit");
  @Override
  public void execute() throws InterruptOperationException {
    ConsoleHelper.writeMessage(res.getString("before"));
    String currencyCode = ConsoleHelper.askCurrencyCode();
    String[] digits = ConsoleHelper.getValidTwoDigits(currencyCode);

    CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
    try {
      int denomination = Integer.parseInt(digits[0]);
      int count = Integer.parseInt(digits[1]);
      manipulator.addAmount(denomination, count);
      ConsoleHelper.writeMessage(String.format(res
              .getString("success.format"), denomination * count,currencyCode));
    } catch (NumberFormatException e) {
      ConsoleHelper.writeMessage(res.getString("invalid.data"));
    }
  }
}
