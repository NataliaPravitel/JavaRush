package com.javarush.task.task22.task2212;

/*
Проверка номера телефона
Метод checkTelNumber должен проверять, является ли аргумент telNumber валидным номером телефона.

Критерии валидности:
1) если номер начинается с '+', то он содержит 12 цифр
2) если номер начинается с цифры или открывающей скобки, то он содержит 10 цифр
3) может содержать 0-2 знаков '-', которые не могут идти подряд
4) может содержать 1 пару скобок '(' и ')' , причем если она есть, то она расположена левее знаков '-'
5) скобки внутри содержат четко 3 цифры
6) номер не содержит букв
7) номер заканчивается на цифру

Примеры:
+380501234567 - true
+38(050)1234567 - true
+38050123-45-67 - true
050123-4567 - true
+38)050(1234567 - false
+38(050)1-23-45-6-7 - false
050ххх4567 - false
050123456 - false
(0)501234567 - false


Требования:
1. Метод checkTelNumber должен возвращать значение типа boolean.
2. Метод checkTelNumber должен быть публичным.
3. Метод checkTelNumber должен принимать один параметр типа String.
4. Метод checkTelNumber должен корректно проверять валидность номера телефона переданного ему в качестве параметра.
*/
public class Solution {
    public static boolean checkTelNumber(String telNumber) {
        if(telNumber == null || telNumber.length() == 0) {
            return false;
        }
        int countNumbers = telNumber.replaceAll("\\D", "").length();
        int countDefis = telNumber.replaceAll("[^-]", "").length();
        String endSymb = telNumber.substring(telNumber.length()-1);
        int countLettersInTel = telNumber.replaceAll("[^a-zA-Z]", "").length();

        if(countDefis >= 0 && countDefis <=2 && !telNumber.contains("--")     //может содержать 0-2 знаков '-', которые не могут идти подряд
                && countLettersInTel == 0 && endSymb.matches("[\\d]")) {// номер не содержит букв, номер заканчивается на цифру
            if (telNumber.matches("\\+.*") && countNumbers == 12) {//если номер начинается с '+', то он содержит 12 цифр
                if(telNumber.contains("(") || telNumber.contains(")")) {
                    return checkBracket(telNumber);
                } else {
                    return true;
                }

            } else if ((telNumber.matches("\\d.*") || telNumber.matches("\\(.*"))
                    && countNumbers == 10) { //если номер начинается с цифры или открывающей скобки, то он содержит 10 цифр
                if(telNumber.contains("(") || telNumber.contains(")")) {
                    return checkBracket(telNumber);
                } else {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean checkBracket(String telNumber) {
        if(telNumber.contains("(") && telNumber.contains(")")) {//может содержать 1 пару скобок '(' и ')' ,
            if(telNumber.indexOf("(") < telNumber.indexOf(")")) {
                if(telNumber.contains("-")) {// причем если она есть, то она расположена левее знаков '-'
                    String telWithoutDef = telNumber.substring(0, telNumber.indexOf("-"));
                    int countNumbersIn = telWithoutDef.substring(telNumber.indexOf("(")+1, telNumber.indexOf(")")).length();
                    if(countNumbersIn == 3) {            //скобки внутри содержат четко 3 цифры
                        return true;
                    }
                } else {
                    int countNumbersIn = telNumber.substring(telNumber.indexOf("(")+1, telNumber.indexOf(")")).length();
                    if(countNumbersIn == 3) {            //скобки внутри содержат четко 3 цифры
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(checkTelNumber("+380501234567"));
        System.out.println(checkTelNumber("+38(050)1234567"));
        System.out.println(checkTelNumber("+38050123-45-67"));
        System.out.println(checkTelNumber("050123-4567"));
        System.out.println(checkTelNumber("+38)050(1234567"));
        System.out.println(checkTelNumber("+38(050)1-23-45-6-7"));
        System.out.println(checkTelNumber("050ххх4567"));
        System.out.println(checkTelNumber("050123456"));
        System.out.println(checkTelNumber("(0)501234567"));
    }
}