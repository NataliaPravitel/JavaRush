package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;

import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

class InfoCommand implements Command {
  private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + ".info");
  @Override
  public void execute() {
    ConsoleHelper.writeMessage(res.getString("before"));
    AtomicBoolean hasMoney = new AtomicBoolean(false);
    CurrencyManipulatorFactory.getAllCurrencyManipulators().forEach(currencyManipulator -> {
      if (currencyManipulator.hasMoney()) {
        hasMoney.set(true);
        ConsoleHelper.writeMessage(currencyManipulator.getCurrencyCode() +
                " - " + currencyManipulator.getTotalAmount());
      }
    });
    if (!hasMoney.get()) {
      ConsoleHelper.writeMessage(res.getString("no.money"));
    }
  }
}
