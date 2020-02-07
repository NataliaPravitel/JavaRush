package com.javarush.task.task22.task2209;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader file = new BufferedReader(new FileReader(reader.readLine()));
        reader.close();
        String line = file.readLine();
        file.close();
        String[] words = line.split(" ");
        StringBuilder result = getLine(words);
        System.out.println(result.toString());
    }

    public static StringBuilder getLine(String... words) {
        if(words.length == 0) {
            return new StringBuilder();
        }

        StringBuilder builder = new StringBuilder();
        LinkedList<String> line = new LinkedList<>();
        int i = 0;
        while (i < words.length) {
            line.add(words[i]);
            line = searching(line, words);
            if(line.size() != words.length) {
                line.clear();
                i++;
                continue;
            } else {
                break;
            }
        }

        for(String word : line) {
            builder.append(word).append(" ");
        }
        return new StringBuilder(builder.toString().trim());
    }

    public static LinkedList searching(LinkedList<String> line, String[] words) {
        for(int i = 0; i < words.length; i++) {
            if(!line.contains(words[i])) {
                String lastWord = line.getLast();
                if (words[i].substring(words[i].length() - 1).equalsIgnoreCase(line.get(0).
                        substring(0,1))) {
                    line.add(0, words[i]);
                } else if (lastWord.substring(lastWord.length() - 1).equalsIgnoreCase(words[i].
                        substring(0,1))) {
                    line.add(words[i]);
                }
            }
        }
        if(words.length != line.size()) {
            searching(line, words);
        }
        return line;
    }

}