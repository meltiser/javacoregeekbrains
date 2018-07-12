package ru.grigorev.javacore;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitriy Grigorev
 * The first point of the hometask
 */
public class Application {
    private static final String[] WORDS_ARRAY = {
            "London", "is", "the", "capital", "of", "Great", "Britain",
            "make", "Great", "Britain", "great", "again",
            "Is", "London", "the", "capital"};

    private static void printUniqueWordsCount(String[] wordsArray) {
        final Map<String, Integer> MAP = new HashMap<>();
        for (String s : wordsArray) {
            String word = s.toLowerCase();
            if (MAP.containsKey(word)) MAP.put(word, MAP.get(word) + 1); //если содержит слово - увеличиваем счетчик
            else MAP.put(word, 1);
        }
        for (Map.Entry<String, Integer> pair : MAP.entrySet()) {
            System.out.println(String.format("%-8s : %d", pair.getKey(), pair.getValue()));
        }
    }
}
