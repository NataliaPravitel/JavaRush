package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.Map;
import java.util.ResourceBundle;

class WithdrawCommand implements Command {
  private ResourceBundle res = ResourceBundle
          .getBundle(CashMachine.RESOURCE_PATH + ".withdraw");

  @Override
  public void execute() throws InterruptOperationException {
    ConsoleHelper.writeMessage(res.getString("before"));
    String currencyCode = ConsoleHelper.askCurrencyCode();
    CurrencyManipulator manipulator = CurrencyManipulatorFactory
            .getManipulatorByCurrencyCode(currencyCode);
    withdraw(currencyCode, manipulator);
  }

  private void withdraw(String currencyCode, CurrencyManipulator manipulator)
          throws InterruptOperationException {

    ConsoleHelper.writeMessage(res.getString("specify.amount"));
    try {
      int amount = Integer.parseInt(ConsoleHelper.readString());
      if (amount <= 0) {
        ConsoleHelper.writeMessage("Please specify valid data.");
        withdraw(currencyCode, manipulator);
      } else {
        if (manipulator.isAmountAvailable(amount)){
          Map<Integer, Integer> denominations = manipulator.withdrawAmount(amount);
          for (Integer item : denominations.keySet()) {
            ConsoleHelper.writeMessage("\t" + item + " - " + denominations.get(item));
          }
          ConsoleHelper.writeMessage(String.format(res.getString("success.format"),
                  amount, currencyCode));
        } else {
          ConsoleHelper.writeMessage(res.getString("not.enough.money"));
          withdraw(currencyCode, manipulator);
        }
      }

    } catch (NumberFormatException e) {
      ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
      withdraw(currencyCode, manipulator);
    } catch (NotEnoughMoneyException e) {
      ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
      withdraw(currencyCode, manipulator);
    }
  }
}
